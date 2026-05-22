package kari.api.login;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;

public class LoginApiPositiveTest extends LoginApiBaseTest {

    private static final String LOGIN_ENDPOINT = "/ecommerce/client/login";

    @Test(description = "Логин с валидными данными должен возвращать 200")
    public void loginWithValidCredentialsShouldReturn200() {
        String login = System.getProperty("kari.login");
        String password = System.getProperty("kari.password");

        Assertions.assertNotNull(login, "Не передан логин. Добавь -Dkari.login=...");
        Assertions.assertNotNull(password, "Не передан пароль. Добавь -Dkari.password=...");

        logger.info("Отправляем запрос на авторизацию пользователя: {}", login);

        given()
                .spec(requestSpec)
                .body(createLoginBody(login, password))
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body(not(emptyString()));

        logger.info("Авторизация успешно выполнена");
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
