package kari.ui.tests;

import kari.ui.pages.RecoveryPage;
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class RecoveryTest extends BaseTest {

    private RecoveryPage recoveryPage;

    @BeforeEach
    public void initPage() {
        recoveryPage = new RecoveryPage(driver);
        logger.info("Открытие страницы восстановления доступа");
        recoveryPage.openRecoveryPage();
    }

    @Test
    @DisplayName("Отображение элементов: Проверка доступности поля ввода номера телефона")
    public void testRecoveryPageElementsDisplay() {
        logger.info("Старт теста: Проверка доступности поля ввода телефона");
        Assertions.assertDoesNotThrow(() -> recoveryPage.checkPhoneInputIsVisible(),
                "Поле ввода номера телефона не найдено или скрыто на странице!");
    }



    @Test
    @DisplayName("Валидация: Проверка подсветки поля при ошибке")
    public void testInvalidPhoneNumberErrorText() {
        logger.info("Старт теста: Проверка появления ошибки на поле");
        recoveryPage.clickGetCode();
        Assertions.assertDoesNotThrow(() -> recoveryPage.checkPhoneInputHasError(),
                "Поле ввода номера телефона не подсветилось красным!");
    }


    @Test
    @DisplayName("Форма восстановления: Успешная отправка формы с обходом окна капчи")
    public void testSuccessfulRecoveryRequest() {
        logger.info("Старт теста: Успешная отправка формы восстановления с валидным номером");
        Faker faker = new Faker(new Locale("ru"));
        String randomPhoneNumber = faker.bothify("9#########");
        logger.info("Сгенерирован случайный номер телефона: +7" + randomPhoneNumber);
        logger.info("Вводим корректный номер телефона и нажимаем кнопку");
        recoveryPage.enterPhoneNumber(randomPhoneNumber);
        recoveryPage.clickGetCode();
        logger.info("Закрываем появившееся окно капчи через клик по крестику");
        recoveryPage.closeCaptchaModal();
        Assertions.assertDoesNotThrow(() -> recoveryPage.checkSmsCodeInputIsVisible(),
                "Окно капчи не закрылось или заблокировало дальнейший шаг!");
    }
}
