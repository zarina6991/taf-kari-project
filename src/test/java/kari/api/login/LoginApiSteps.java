package kari.api.login;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;

public class LoginApiSteps {

    private static final String LOGIN_ENDPOINT = "/ecommerce/client/login";

    private final RequestSpecification requestSpec;

    public LoginApiSteps(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response login(String login, String password) {
        return given()
                .spec(requestSpec)
                .body(createLoginBody(login, password))
                .when()
                .post(LOGIN_ENDPOINT);
    }

    public Response loginWithEmptyBody() {
        return given()
                .spec(requestSpec)
                .when()
                .post(LOGIN_ENDPOINT);
    }

    public void assertSuccessfulLoginResponse(Response response) {
        response.then()
                .log().ifValidationFails()
                .statusCode(200)
                .time(lessThan(5000L))
                .body(not(emptyString()));
    }

    public void assertBadRequestWithMessage(Response response, String expectedMessage) {
        response.then()
                .log().ifValidationFails()
                .statusCode(400)
                .body("error", equalTo("Bad Request"))
                .body("message", equalTo(expectedMessage));
    }

    public void assertBadRequestWithNotEmptyBody(Response response, String expectedMessage) {
        response.then()
                .log().ifValidationFails()
                .statusCode(400)
                .body(not(emptyString()))
                .body("error", equalTo("Bad Request"))
                .body("message", equalTo(expectedMessage));
    }

    private String createLoginBody(String login, String password) {
        return String.format("""
                {
                  "login": "%s",
                  "password": "%s"
                }
                """, login, password);
    }
}
