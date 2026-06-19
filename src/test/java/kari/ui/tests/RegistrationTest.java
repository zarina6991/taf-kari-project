package kari.ui.tests;

import net.datafaker.Faker;
import kari.ui.pages.RegistrationPage;
import org.junit.jupiter.api.*;

public class RegistrationTest extends BaseTest {

    private RegistrationPage registrationPage;
    private Faker faker;

    @BeforeEach
    public void initPage() {
        logger.info("Инициализация объекта RegistrationPage перед стартом теста");
        registrationPage = new RegistrationPage(driver);
        faker = new Faker();
        driver.get(BASE_UI_URL + "auth/reg/?redirection=");
    }

    @Tag("ui")
    @Test
    @DisplayName("Негативный сценарий: Ввод неполного номера телефона")
    public void testRegistrationWithIncompletePhone() {
        logger.info("Начало теста: Ввод неполного номера телефона");
        registrationPage.enterPhoneAndRequestCode("111");
        logger.info("Проверка отображения ошибки 'Введите оставшиеся цифры'");
        String errorText = registrationPage.getValidationErrorText();
        Assertions.assertEquals("Введите оставшиеся цифры", errorText,
                "Ошибка: Текст валидации не совпадает или сообщение не появилось!");
        logger.info("Тест успешно завершен.");
    }

    @Tag("ui")
    @Test
    @DisplayName("Негативный сценарий: Попытка отправки пустой формы")
    public void testRegistrationWithEmptyPhone() {
        logger.info("Начало теста: Клик по кнопке отправки без ввода номера телефона");
        registrationPage.clickGetCodeButtonOnly();
        logger.info("Проверка отображения ошибки для обязательного поля");
        String errorText = registrationPage.getValidationErrorText();
        Assertions.assertFalse(errorText.isEmpty(),
                "Критическая ошибка: При отправке пустой формы сообщение об ошибке не появилось!");
        logger.info("Тест успешно завершен: пустая форма заблокирована. Текст ошибки: " + errorText);
    }

    @Tag("smoke")
    @Tag("ui")
    @Test
    @DisplayName("Позитивный сценарий: Отправка валидного случайного номера через Faker")
    public void testPositiveRegistrationWithValidPhone() {
        String randomPhone = faker.number().digits(10);
        logger.info("Генератор Faker создал номер телефона: " + randomPhone);
        registrationPage.enterPhoneAndRequestCode(randomPhone);
        if (registrationPage.isCaptchaDisplayed()) {
            logger.warn("Внимание: Тест успешно дошел до отправки, но сайт Kari отобразил капчу 'А вы точно не робот?'!");;
        }
    }
}
