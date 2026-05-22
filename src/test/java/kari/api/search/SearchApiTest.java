package kari.api.search;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

public class SearchApiTest {

    private SearchApiSteps searchApiSteps;

    @BeforeClass
    public void setUpForSearch() {
        searchApiSteps = new SearchApiSteps();
    }

    @Test(description = "Поиск товара по валидному запросу должен возвращать успешный ответ")
    public void searchByValidQueryShouldReturnSuccessResponse() {
        Response response = searchApiSteps.searchProduct("кроссовки");

        assertSuccessfulSearchResponse(response);
    }

    @Test(description = "Поиск с пустым запросом должен возвращать успешный ответ")
    public void searchWithEmptyQueryShouldReturn200() {
        Response response = searchApiSteps.searchProduct("");

        assertSuccessfulSearchResponse(response);
    }

    @Test(description = "Поиск по несуществующему запросу должен возвращать успешный ответ")
    public void searchWithNonExistingQueryShouldReturn200() {
        Response response = searchApiSteps.searchProduct("абракадабра123456789");

        assertSuccessfulSearchResponse(response);
    }

    @Test(description = "Поиск со спецсимволами должен возвращать успешный ответ")
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

        Assert.assertFalse(response.asString().isEmpty(), "Тело ответа не должно быть пустым");
    }
}
