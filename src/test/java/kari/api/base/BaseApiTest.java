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

    @AfterEach
    public void tearDownApi() {
        logger.info("Тест завершен");
        logger.info("----------------------------------------");
    }

    private String getDefaultCookie() {
        return "KariLocationId=770000000000; " +
                "KariCountry=ru; " +
                "KariClientCountryConfirmed=true; " +
                "KariClientLocationConfirmed=true";
    }
}