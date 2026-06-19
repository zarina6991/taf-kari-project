package kari.api.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static io.restassured.config.RestAssuredConfig.config;

public class BaseApiTest {

    protected final Logger logger = LogManager.getLogger(getClass());

    protected static final String BASE_URL = "https://i.api.kari.com";

    protected RequestSpecification requestSpec;

    protected void assertBadRequestWithMessage(io.restassured.response.Response response, String expectedMessage) {
        response.then()
                .log().ifValidationFails()
                .statusCode(400)
                .body("error", org.hamcrest.Matchers.equalTo("Bad Request"))
                .body("message", org.hamcrest.Matchers.equalTo(expectedMessage));
    }

    protected void assertBadRequestWithNotEmptyBody(io.restassured.response.Response response, String expectedMessage) {
        response.then()
                .log().ifValidationFails()
                .statusCode(400)
                .body(org.hamcrest.Matchers.not(org.hamcrest.Matchers.emptyString()))
                .body("error", org.hamcrest.Matchers.equalTo("Bad Request"))
                .body("message", org.hamcrest.Matchers.equalTo(expectedMessage));
    }

    @BeforeEach
    public void setUpApi() {
        logger.info("Старт теста");
        logger.info("Настраиваем базовую спецификацию для API");

        RestAssured.baseURI = BASE_URL;

        RestAssured.config = config()
                .logConfig(LogConfig.logConfig()
                        .enableLoggingOfRequestAndResponseIfValidationFails());

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("origin", "https://kari.com")
                .addHeader("referer", "https://kari.com/")
                .addHeader("cookie", getDefaultCookie())
                .build();
    }

    private String getDefaultCookie() {
        return "KariLocationId=770000000000; " +
                "KariCountry=ru; " +
                "KariClientCountryConfirmed=true; " +
                "KariClientLocationConfirmed=true";
    }

    @AfterEach
    public void tearDownApi() {
        logger.info("Тест завершен");
    }
}