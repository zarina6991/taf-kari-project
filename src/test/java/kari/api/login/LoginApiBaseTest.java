package kari.api.login;

import kari.api.base.BaseApiTest;
import org.junit.jupiter.api.BeforeEach;

public class LoginApiBaseTest extends BaseApiTest {

    protected LoginApiSteps loginApiSteps;

    protected static final String VALID_LOGIN = "parpievazarrina11@gmail.com";
    protected static final String VALID_PASSWORD = "4444444";
    protected static final String WRONG_PASSWORD = "3333356666666";
    protected static final String INVALID_EMAIL = "parpievazarrina11gmail.com";

    protected static final String INVALID_REQUEST_PAYLOAD_MESSAGE = "Invalid request payload input";
    protected static final String WRONG_LOGIN_OR_PASSWORD_MESSAGE = "Неверный логин или пароль";

    @BeforeEach
    public void setUpLoginApi() {
        logger.info("Настраиваем шаги для Login API");
        loginApiSteps = new LoginApiSteps(requestSpec);
    }

    protected String getRequiredSystemProperty(String propertyName) {
        String value = System.getProperty(propertyName);

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Не передано значение системного свойства: " + propertyName);
        }

        return value;
    }
}
