package kari.api.login;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;;

public class LoginApiNegativeTest extends LoginApiBaseTest {

    @DisplayName("Логин с пустым email должен возвращать 400")
    @Test
    public void loginWithEmptyEmailShouldReturn400() {
        logger.info("Проверяем логин с пустым email");
        Response response = loginApiSteps.login("", VALID_PASSWORD);
        loginApiSteps.assertBadRequestWithMessage(
                response,
                INVALID_REQUEST_PAYLOAD_MESSAGE
        );
    }

    @DisplayName("Логин с пустым паролем должен возвращать 400")
    @Test
    public void loginWithEmptyPasswordShouldReturn400() {
        logger.info("Проверяем логин с пустым паролем");
        Response response = loginApiSteps.login(VALID_LOGIN, "");
        loginApiSteps.assertBadRequestWithMessage(
                response,
                INVALID_REQUEST_PAYLOAD_MESSAGE
        );
    }

    @DisplayName("Логин с валидным email и неверным паролем должен возвращать 400")
    @Test
    public void loginWithEmailAndWrongPasswordShouldReturn400() {
        logger.info("Проверяем логин с неверным паролем");
        Response response = loginApiSteps.login(VALID_LOGIN, WRONG_PASSWORD);
        loginApiSteps.assertBadRequestWithMessage(
                response,
                WRONG_LOGIN_OR_PASSWORD_MESSAGE
        );
    }

    @DisplayName("Логин с невалидным форматом email должен возвращать 400")
    @Test
    public void loginWithInvalidEmailFormatShouldReturn400() {
        logger.info("Проверяем логин с невалидным форматом email");
        Response response = loginApiSteps.login(INVALID_EMAIL, VALID_PASSWORD);
        loginApiSteps.assertBadRequestWithMessage(
                response,
                WRONG_LOGIN_OR_PASSWORD_MESSAGE
        );
    }

    @DisplayName("Логин с пустым телом запроса должен возвращать 400")
    @Test
    public void loginWithEmptyBodyShouldReturn400AndNotEmptyResponseBody() {
        logger.info("Проверяем логин с пустым телом запроса");
        Response response = loginApiSteps.loginWithEmptyBody();
        loginApiSteps.assertBadRequestWithNotEmptyBody(
                response,
                INVALID_REQUEST_PAYLOAD_MESSAGE
        );
    }
}
