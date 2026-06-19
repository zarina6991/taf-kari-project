package kari.api.search;

import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

public class SearchApiTest {

    private SearchApiSteps searchApiSteps;

    @BeforeEach
    public void setUpForSearch() {
        searchApiSteps = new SearchApiSteps();
    }

    @Tag("api")
    @Description("Поиск товара по валидному запросу должен возвращать успешный ответ")
    @Test
    public void searchByValidQueryShouldReturnSuccessResponse() {
        Response response = searchApiSteps.searchProduct("кроссовки");
        assertSuccessfulSearchResponse(response);
    }

    @Tag("api")
    @Description("Поиск с пустым запросом должен возвращать успешный ответ")
    @Test
    public void searchWithEmptyQueryShouldReturn200() {
        Response response = searchApiSteps.searchProduct("");
        assertSuccessfulSearchResponse(response);
    }

    @Tag("api")
    @Description( "Поиск по несуществующему запросу должен возвращать успешный ответ")
    @Test
    public void searchWithNonExistingQueryShouldReturn200() {
        Response response = searchApiSteps.searchProduct("абракадабра123456789");
        assertSuccessfulSearchResponse(response);
    }

    @Tag("api")
    @Description("Поиск со спецсимволами должен возвращать успешный ответ")
    @Test
    public void searchWithSpecialCharactersShouldReturn200() {
        Response response = searchApiSteps.searchProduct("@#$%^&*");
        assertSuccessfulSearchResponse(response);
    }

    private void assertSuccessfulSearchResponse(Response response) {
        response.then()
                .log().ifValidationFails()
                .statusCode(200)
                .time(lessThan(5000L))
                .contentType(containsString("application/json"))
                .body(notNullValue());
        Assertions.assertFalse(response.asString().isEmpty(), "Тело ответа не должно быть пустым");
    }
}
