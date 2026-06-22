package kari.api.registration;

import io.restassured.response.Response;
import kari.api.ApiUtils;
import kari.api.base.BaseApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.CompletableFuture.anyOf;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationApiTest extends BaseApiTest {

    private RegistrationApiSteps registrationApiSteps;

    private static final String VALID_TEST_PHONE = "+79538603968";
    private static final String INVALID_REQUEST_PAYLOAD_MESSAGE = "Invalid request payload input";

    @BeforeEach
    public void setUpRegistrationApi() {
        logger.info("Настраиваем шаги для Registration API");
        registrationApiSteps = new RegistrationApiSteps(requestSpec);
    }

    @DisplayName("Отправка кода регистрации с валидным телефоном должна возвращать 200")
    @Test
    public void sendCodeWithValidPhoneShouldReturnSuccess() {
        logger.info("Отправляем код регистрации на тестовый номер: {}", VALID_TEST_PHONE);
        Response response = registrationApiSteps.sendRegistrationCode(VALID_TEST_PHONE);
        logger.info("Проверяем, что статус-код строго 200 и тело ответа не пустое");
        response.then()
                .log().ifValidationFails()
                .statusCode(200)
                .body(not(emptyString()));
        assertEquals(200, response.getStatusCode(), "Ошибка! Сервер вернул неверный статус-код.");
        logger.info("Запрос успешно выполнен. Статус-код: {}", response.getStatusCode());
    }

    @DisplayName("Отправка кода регистрации с пустым телефоном должна возвращать 400")
    @Test
    public void sendCodeWithEmptyPhoneShouldReturn400() {
        logger.info("Проверяем отправку кода регистрации с пустым номером телефона");
        Response response = registrationApiSteps.sendRegistrationCode("");
        logger.info("Ожидаем статус 400 и сообщение об ошибке: '{}'", INVALID_REQUEST_PAYLOAD_MESSAGE);
        assertBadRequestWithMessage(response, INVALID_REQUEST_PAYLOAD_MESSAGE);
    }

    @DisplayName("Отправка кода регистрации со слишком коротким номером должна возвращать 400")
    @Test
    public void sendCodeWithTooShortPhoneShouldReturn400() {
        String invalidShortPhone = ApiUtils.generateRandomNumericString(5);
        logger.info("Проверяем отправку кода регистрации с коротким номером: {}", invalidShortPhone);
        Response response = registrationApiSteps.sendRegistrationCode(invalidShortPhone);
        logger.info("Ожидаем отсечение невалидного номера со статусом 400 и ошибкой: '{}'", INVALID_REQUEST_PAYLOAD_MESSAGE);
        assertBadRequestWithMessage(response, INVALID_REQUEST_PAYLOAD_MESSAGE);
    }

    @DisplayName("Отправка кода регистрации с пустым телом запроса должна возвращать 400")
    @Test
    public void sendCodeWithEmptyBodyShouldReturn400() {
        logger.info("Проверяем отправку кода регистрации с пустым телом запроса");
        Response response = registrationApiSteps.sendRegistrationCodeWithEmptyBody();
        logger.info("Ожидаем ошибку схемы 400 на пустое тело JSON с сообщением: '{}'", INVALID_REQUEST_PAYLOAD_MESSAGE);
        assertBadRequestWithNotEmptyBody(response, INVALID_REQUEST_PAYLOAD_MESSAGE);
    }

    @DisplayName("Ввод кода подтверждения без открытой сессии должен возвращать ошибку payload")
    @Test
    public void confirmRegistrationWithWrongCodeShouldReturnError() {
        String wrongSmsCode = ApiUtils.generateRandomNumericString(4);
        logger.info("Отправляем проверочный код {} для телефона {} без инициализации сессии токена", wrongSmsCode, VALID_TEST_PHONE);
        Response response = registrationApiSteps.confirmRegistrationCode(VALID_TEST_PHONE, wrongSmsCode);
        logger.info("Ожидаем блокировку запроса без сессии со статусом 400 и ошибкой: '{}'", INVALID_REQUEST_PAYLOAD_MESSAGE);
        assertBadRequestWithMessage(response, INVALID_REQUEST_PAYLOAD_MESSAGE);
    }
}
