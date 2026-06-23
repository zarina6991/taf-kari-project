package kari.api.search;

import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;

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
    @DisplayName("Поиск товара по валидному запросу должен возвращать успешный ответ")
    @Test
    public void searchByValidQueryShouldReturnSuccessResponse() {
        Response response = searchApiSteps.searchProduct("кроссовки");
        assertSuccessfulSearchResponse(response);
    }

    @Tag("api")
    @DisplayName("Поиск с пустым запросом должен возвращать успешный ответ")
    @Test
    public void searchWithEmptyQueryShouldReturn200() {
        Response response = searchApiSteps.searchProduct("");
        assertSuccessfulSearchResponse(response);
    }

    @Tag("api")
    @DisplayName( "Поиск по несуществующему запросу должен возвращать успешный ответ")
    @Test
    public void searchWithNonExistingQueryShouldReturn200() {
        Response response = searchApiSteps.searchProduct("xyzqweasdf_notfound");
        assertSuccessfulSearchResponse(response);
        response.then().body("search_query_original", org.hamcrest.Matchers.equalTo("xyzqweasdf_notfound"));
    }

    @Tag("api")
    @DisplayName("Поиск со спецсимволами должен возвращать успешный ответ")
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
