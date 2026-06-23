package kari.api.recovery;

import io.restassured.response.Response;
import kari.api.ApiUtils;
import kari.api.base.BaseApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class RecoveryApiNegativeTest extends BaseApiTest {

    private RecoveryApiSteps recoveryApiSteps;

    @BeforeEach
    public void setUpRecoveryApi() {
        recoveryApiSteps = new RecoveryApiSteps(requestSpec);
    }

    @Tag("api")
    @DisplayName("Восстановление без авторизации должно возвращать 401")
    @Test
    public void recoveryWithoutAuthenticationShouldReturn401() {
        logger.info("Проверяем восстановление без авторизации");
        Response response = recoveryApiSteps.recoveryWithoutAuthentication();
        recoveryApiSteps.assertMissingAuthenticationResponse(response);
    }
    @Tag("api")
    @DisplayName("Позитивный сценарий: Ввод валидного номера телефона переводит на шаг капчи")
    @Test
    public void verifyValidPhoneNumberFormatShouldRequireCaptcha() {
        String randomDigits = ApiUtils.generateRandomNumericString(10);
        String fullPhoneNumber = "+7" + randomDigits;
        logger.info("Запуск позитивной проверки. Отправляем валидный номер: " + fullPhoneNumber);
        Response response = recoveryApiSteps.verifyPhoneNumber(fullPhoneNumber);
        response.then()
                .log().ifValidationFails()
                .statusCode(200)
                .body(org.hamcrest.Matchers.containsString("READY_FOR_CAPTCHA"));
        logger.info("Тест успешно пройден: сервер вернул статус 200 и READY_FOR_CAPTCHA");
    }
}
