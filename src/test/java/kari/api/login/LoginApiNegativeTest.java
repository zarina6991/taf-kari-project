package kari.api.login;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginApiNegativeTest {
    @Test
    public void loginWithEmptyEmailShouldReturn400() {
        given()
                .log().all()
                .baseUri("https://i.api.kari.com")
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("origin", "https://kari.com")
                .header("referer", "https://kari.com/")
                .header("cookie", "KariLocationId=770000000000; KariCountry=ru; KariClientCountryConfirmed=true; KariClientLocationConfirmed=true")
                .body("""
                        {
                          "login": "",
                          "password": "4444444"
                        }
                        """)
                .when()
                .post("/ecommerce/client/login")
                .then()
                .log().all()
                .statusCode(400)
                .body("error", equalTo("Bad Request"))
                .body("message", equalTo("Invalid request payload input"));

    }

    @Test
    public void loginWithEmptyPasswordShouldReturn400() {
        given()
                .log().all()
                .baseUri("https://i.api.kari.com")
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("origin", "https://kari.com")
                .header("referer", "https://kari.com/")
                .header("cookie", "KariLocationId=770000000000; KariCountry=ru; KariClientCountryConfirmed=true; KariClientLocationConfirmed=true")
                .body("""
                        {
                          "login": "parpievazarrina11@gmail.com",
                          "password": ""
                        }
                        """)
                .when()
                .post("/ecommerce/client/login")
                .then()
                .log().all()
                .statusCode(400)
                .body("error", equalTo("Bad Request"))
                .body("message", equalTo("Invalid request payload input"));
    }


    @Test
    public void loginWithEmailAndWrongPasswordShouldReturn400() {
        given()
                .log().all()
                .baseUri("https://i.api.kari.com")
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("origin", "https://kari.com")
                .header("referer", "https://kari.com/")
                .header("cookie", "KariLocationId=770000000000; KariCountry=ru; KariClientCountryConfirmed=true; KariClientLocationConfirmed=true")
                .body("""
                        {
                          "login": "parpievazarrina11@gmail.com",
                          "password": "3333356666666"
                        }
                        """)
                .when()
                .post("/ecommerce/client/login")
                .then()
                .log().all()
                .statusCode(400)
                .body("error", equalTo("Bad Request"))
                .body("message", equalTo("Неверный логин или пароль"));

    }

    @Test
    public void loginWithInvalidEmailFormatShouldReturn400() {
        given()
                .log().all()
                .baseUri("https://i.api.kari.com")
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("origin", "https://kari.com")
                .header("referer", "https://kari.com/")
                .header("cookie", "KariLocationId=770000000000; KariCountry=ru; KariClientCountryConfirmed=true; KariClientLocationConfirmed=true")
                .body("""
                        {
                          "login": "parpievazarrina11gmail.com",
                          "password": "4444444"
                        }
                        """)
                .when()
                .post("/ecommerce/client/login")
                .then()
                .log().all()
                .statusCode(400)
                .body("error", equalTo("Bad Request"))
                .body("message", equalTo("Неверный логин или пароль")); //проверяем валидацию формата email, потому что это критичная проверка
    }

    @Test
    public void loginWithEmptyBodyShouldReturn400AndNotEmptyResponseBody() {
        given()
                .log().all()
                .baseUri("https://i.api.kari.com")
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("origin", "https://kari.com")
                .header("referer", "https://kari.com/")
                .header("cookie", "KariLocationId=770000000000; KariCountry=ru; KariClientCountryConfirmed=true; KariClientLocationConfirmed=true")
                .when()
                .post("/ecommerce/client/login")
                .then()
                .log().all()
                .statusCode(400)
                .body(not(emptyString())) //проверяем, что тело не пустое
                .body("error", equalTo("Bad Request"))
                .body("message", equalTo("Invalid request payload input"));
    }
}
