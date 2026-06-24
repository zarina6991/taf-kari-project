package kari.api.login;

import io.qameta.allure.Step;
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

    @Step("Выполнение POST запроса на авторизацию. Логин: '{login}', Пароль: '*****'")
    public Response login(String login, String password) {
        return given()
                .spec(requestSpec)
                .body(createLoginBody(login, password))
                .when()
                .post(LOGIN_ENDPOINT);
    }

    @Step("Выполнение POST запроса на авторизацию с пустым телом")
    public Response loginWithEmptyBody() {
        return given()
                .spec(requestSpec)
                .when()
                .post(LOGIN_ENDPOINT);
    }

    @Step("Проверка успешного ответа авторизации: статус 200 OK, тело не пустое, время ответа < 5с")
    public void assertSuccessfulLoginResponse(Response response) {
        response.then()
                .log().ifValidationFails()
                .statusCode(200)
                .time(lessThan(5000L))
                .body(not(emptyString()));
    }

    @Step("Проверка ошибки: ожидается статус 400 'Bad Request' и сообщение: '{expectedMessage}'")
    public void assertBadRequestWithMessage(Response response, String expectedMessage) {
        response.then()
                .log().ifValidationFails()
                .statusCode(400)
                .body("error", equalTo("Bad Request"))
                .body("message", equalTo(expectedMessage));
    }

    @Step("Проверка ошибки на пустое тело: ожидается статус 400, непустой ответ и сообщение: '{expectedMessage}'")
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
