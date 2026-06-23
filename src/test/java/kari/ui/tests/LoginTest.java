package kari.ui.tests;

import kari.ui.base.WebDriverManager;
import kari.ui.pages.LoginPage;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;

@DisplayName("Тестирование функционала авторизации на сайте Kari")
public class

LoginTest extends BaseTest {

    private LoginPage loginPage;
    private Faker faker;

    @BeforeEach
    public void initPage() {
        logger.info("Инициализация объекта LoginPage перед стартом теста");
        loginPage = new LoginPage(driver);
        faker = new Faker();
        driver.get(BASE_UI_URL + "auth/");
    }

    @Disabled
    @Tag("ui")
    @Test
    @DisplayName("Позитивный сценарий: Успешный вход в личный кабинет")
    public void testPositiveLoginWithValidCredentials() {
        loginPage.loginWithSystemProperties();
        org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(WebDriverManager.getDriver(), java.time.Duration.ofSeconds(10));
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(org.openqa.selenium.By.xpath("//*[contains(text(), 'Заррина')]")));
        Assertions.assertTrue(loginPage.checkUserProfileNameIsDisplayed().contains("Заррина"),
                "Имя авторизованного пользователя 'Заррина' не найдено на странице!");
        Assertions.assertEquals("", loginPage.isErrorMessageDisplayed(),
                "Отобразилось сообщение об ошибке авторизации!");
        logger.info("Тест успешно завершен. Пользователь Заррина авторизован.");
    }


    @Tag("ui")
    @Test
    @DisplayName("Негативный сценарий: Авторизация с неверными учетными данными")
    public void testNegativeLoginWithInvalidCredentials() {
        loginPage.loginWithCredentials("+79991112233", "WrongPassword123");
        String actualErrorText = loginPage.isErrorMessageDisplayed();
        Assertions.assertFalse(actualErrorText.isEmpty(),
                "Критическая ошибка: Сообщение об ошибке валидации не появилось на экране!");
        Assertions.assertEquals("Неверный логин или пароль", actualErrorText,
                "Отобразился неверный текст сообщения об ошибке!");
        logger.info("Тест успешно завершен. Ошибка валидации обработана корректно.");
    }

    @Tag("ui")
    @Test
    @DisplayName("Негативный сценарий: Ввод пробелов вместо логина и пароля")
    public void testLoginWithSpaces() {
        loginPage.loginWithCredentials("   ", "   ");
        Assertions.assertFalse(loginPage.getAnyFormErrorText().isEmpty(),
                "Ошибка: Система посчитала пробелы за валидные данные и не вывела ошибку!");
        logger.info("Тест успешно завершен. Пробелы успешно отсекаются валидацией.");
    }

    @Tag("ui")
    @Test
    @DisplayName("Негативный сценарий: Ввод некорректного формата телефона")
    public void testLoginWithInvalidPhoneFormat() {
        loginPage.loginWithCredentials("123", "AnyPassword123");
        logger.info("Проверка реакции системы на неверный формат ввода");
        Assertions.assertFalse(loginPage.getAnyFormErrorText().isEmpty(),
                "Ошибка: Система не отреагировала на невалидный формат номера телефона!");
    }

    @Tag("ui")
    @Test
    @DisplayName("Позитивный сценарий: Проверка валидации формы со случайным E-mail через Faker")
    public void testPositiveLoginWithFakerEmail() {
        String randomEmail = faker.internet().emailAddress();
        logger.info("Генератор Faker создал email: " + randomEmail);
        loginPage.loginWithCredentials(randomEmail, "ValidPassword123!");
        String errorText = loginPage.getAnyFormErrorText();
        Assertions.assertFalse(errorText.contains("Введите свой телефон или e-mail"),
                "Локальная валидация ошибочно посчитала валидный Faker email некорректным!");
        Assertions.assertFalse(errorText.contains("Обязательное поле"),
                "Локальная валидация ошибочно вывела ошибку 'Обязательное поле' для заполненного пароля!");
        logger.info("Тест успешно завершен: сгенерированный email прошел проверку формата.");
    }

    @Tag("ui")
    @Test
    @DisplayName("Валидация формы: Проверка локальных ошибок при пустых полях")
    public void testFormLocalValidationWithEmptyFields() {
        loginPage.loginWithCredentials("", "");
        String errorText = loginPage.getAnyFormErrorText();
        Assertions.assertTrue(
                errorText.contains("Введите свой телефон или e-mail") || errorText.contains("Обязательное поле"),
                "Локальная валидация пустых полей не сработала! Текст на форме: " + errorText);
        logger.info("Тест успешно завершен: локальная валидация пустых полей подтверждена.");
    }
}
