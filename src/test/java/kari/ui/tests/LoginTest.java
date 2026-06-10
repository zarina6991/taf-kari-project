package kari.ui.tests;

import kari.ui.pages.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Тестирование функционала авторизации на сайте Kari")
public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    public void initPage() {
        logger.info("Инициализация объекта LoginPage перед стартом теста");
        loginPage = new LoginPage(driver);
        loginPage.open(BASE_UI_URL);
    }

    @Test
    @DisplayName("Позитивный сценарий: Успешный вход в личный кабинет")
    public void testPositiveLoginWithValidCredentials() {
        logger.info("Начало выполнения теста: Позитивный сценарий авторизации");
        loginPage.openLoginPage();
        loginPage.loginWithSystemProperties();
        logger.info("Выполнение проверок перенаправления (Assertions)");
        Assertions.assertTrue(loginPage.checkUserProfileNameIsDisplayed().contains("Заррина"),
                "Имя авторизованного пользователя 'Заррина' не найдено на странице!");
        Assertions.assertEquals("", loginPage.isErrorMessageDisplayed(),
                "В позитивном сценарии отобразилось сообщение об ошибке авторизации!");
        logger.info("Тест успешно завершен. Пользователь Заррина авторизован.");
    }

    @Test
    @DisplayName("Негативный сценарий: Авторизация с неверными учетными данными")
    public void testNegativeLoginWithInvalidCredentials() {
        logger.info("Начало выполнения теста: Негативный сценарий авторизации");
        loginPage.openLoginPage();
        loginPage.loginWithCredentials("+79991112233", "WrongPassword123");
        logger.info("Выполнение проверок для негативного сценария (Assertions)");
        String actualErrorText = loginPage.isErrorMessageDisplayed();
        Assertions.assertFalse(actualErrorText.isEmpty(),
                "Критическая ошибка: Сообщение об ошибке валидации не появилось на экране!");
        Assertions.assertEquals("Неверный логин или пароль", actualErrorText,
                "Отобразился неверный текст сообщения об ошибке!");
        logger.info("Тест успешно завершен. Ошибка валидации обработана корректно.");
    }

    @Test
    @DisplayName("Негативный сценарий: Попытка входа с пустыми полями")
    public void testLoginWithEmptyFields() {
        logger.info("Начало теста: Авторизация с пустыми полями");
        driver.get(BASE_UI_URL + "auth/");
        loginPage.loginWithCredentials("", "");
        logger.info("Выполнение проверок локальной валидации");
        Assertions.assertFalse(loginPage.getAnyFormErrorText().isEmpty(),
                "Ошибка: При отправке пустой формы не появилось уведомление или подсветка полей!");
        logger.info("Тест успешно завершен. Локальная валидация пустых полей работает.");
    }

    @Test
    @DisplayName("Негативный сценарий: Ввод некорректного формата телефона")
    public void testLoginWithInvalidPhoneFormat() {
        logger.info("Начало теста: Ввод некорректного формата телефона");
        driver.get(BASE_UI_URL + "auth/");
        loginPage.loginWithCredentials("123", "AnyPassword123");
        logger.info("Проверка реакции системы на неверный формат ввода");
        Assertions.assertFalse(loginPage.getAnyFormErrorText().isEmpty(),
                "Ошибка: Система не отреагировала на невалидный формат номера телефона!");
    }
}
