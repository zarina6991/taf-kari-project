package kari.api.login;

import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginApiPositiveTest extends LoginApiBaseTest {

    @Disabled("Позитивный логин требует реальные credentials, запускается отдельно")
    @DisplayName("Логин с валидными данными должен возвращать успешный ответ")
    @Test
    public void loginWithValidCredentialsShouldReturnSuccessResponse() {
        String login = getRequiredSystemProperty("kari.login");
        String password = getRequiredSystemProperty("kari.password");

        logger.info("Отправляем запрос на авторизацию пользователя: {}", login);

        Response response = loginApiSteps.login(login, password);

        loginApiSteps.assertSuccessfulLoginResponse(response);

        logger.info("Авторизация успешно выполнена");
    }
}
