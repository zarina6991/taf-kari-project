package kari.api.recovery;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RecoveryApiSteps {

    private static final String RECOVERY_ENDPOINT = "/ecommerce/client/info";

    private final RequestSpecification requestSpec;

    public RecoveryApiSteps(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    @Step("Выполнение POST запроса на восстановление (информация о клиенте) без токена авторизации")
    public Response recoveryWithoutAuthentication() {
        return given()
                .spec(requestSpec)
                .body("")
                .when()
                .post(RECOVERY_ENDPOINT);
    }

    @Step("Проверка ошибки авторизации: статус 401 'Unauthorized' и сообщение 'Missing authentication'")
    public void assertMissingAuthenticationResponse(Response response) {
        response.then()
                .log().ifValidationFails()
                .statusCode(401)
                .body("statusCode", equalTo(401))
                .body("error", equalTo("Unauthorized"))
                .body("message", equalTo("Missing authentication"));
    }

    @Step("Выполнение POST запроса на верификацию номера телефона: '{fullPhoneNumber}'")
    public Response verifyPhoneNumber(String fullPhoneNumber) {
        return given()
                .spec(requestSpec)
                .contentType("application/json") // Явно указываем тип данных JSON
                .body(String.format("""
                    {
                      "phone": "%s"
                    }
                    """, fullPhoneNumber))
                .when()
                .post("/ecommerce/client/v2/phone/verify"); // Эндпоинт верификации со скриншота
    }
}
