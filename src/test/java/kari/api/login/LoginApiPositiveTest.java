package kari.api.login;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginApiPositiveTest {

    private static final String BASE_URL = "https://i.api.kari.com";
    private static final String LOGIN_ENDPOINT = "/ecommerce/client/login";

    @Test
    public void loginWithValidCredentialsShouldReturn200() {
        String login = System.getProperty("kari.login");
        String password = System.getProperty("kari.password");

        given()
                .log().all()
                .baseUri(BASE_URL)
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("origin", "https://kari.com")
                .header("referer", "https://kari.com/")
                .header("cookie", "KariLocationId=770000000000; KariCountry=ru; KariClientCountryConfirmed=true; KariClientLocationConfirmed=true")
                .body(String.format("""
                        {
                          "login": "%s",
                          "password": "%s"
                        }
                        """, login, password))
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                .log().all()
                .statusCode(200)
                .body(not(emptyString()));
    }
}
