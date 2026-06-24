package kari.api.search;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.given;

public class SearchApiSteps {

    private static final String BASE_URL = "https://api.r46.kari.com";
    private static final String SEARCH_ENDPOINT = "/search";

    public Response searchProduct(String searchQuery) {
        return searchRequestWithDefaultParams(searchQuery)
                .when()
                .get(SEARCH_ENDPOINT);
    }

    @Step("Выполнение GET запроса на поиск товара по ключевому слову: '{searchQuery}'")
    private RequestSpecification searchRequestWithDefaultParams(String searchQuery) {
        return given()
                .log().ifValidationFails()
                .baseUri(BASE_URL)
                .queryParam("shop_id", "417dfce51c4cc3cc2fe3da5480db10")
                .queryParam("did", "EvWMkoIz4c")
                .queryParam("type", "instant_search")
                .queryParam("search_query", searchQuery)
                .queryParam("categories", "")
                .queryParam("locations", "7700000000000")
                .queryParam("subquery", "false")
                .queryParam("segment", "")
                .queryParam("extended", "")
                .queryParam("excluded_merchants", "")
                .queryParam("exact_field_match", "")
                .queryParam("collapse", "")
                .queryParam("vector", "")
                .queryParam("referer", "https://kari.com/search/?q=" + searchQuery);
    }
}
