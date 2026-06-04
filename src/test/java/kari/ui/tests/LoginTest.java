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
    @DisplayName("Негативный сценарий: Авторизация с неверными учетными данными")
    public void testNegativeLoginWithInvalidCredentials() {
        logger.info("Начало выполнения теста: Негативный сценарий авторизации");

        loginPage.openLoginPage();
        loginPage.loginWithCredentials("+79991112233", "WrongPassword123");

        logger.info("Выполнение проверок (Assertions)");
        Assertions.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Критическая ошибка: Сообщение об ошибке валидации не появилось на экране!");

        String actualErrorText = loginPage.getErrorMessageText();
        Assertions.assertFalse(actualErrorText.isEmpty(),
                "Ошибка: Текст сообщения об ошибке оказался пустым!");

        logger.info("Тест успешно завершен. Ошибка валидации обработана корректно.");
    }

    @Test
    @DisplayName("Позитивный сценарий: Успешный вход в личный кабинет")
    public void testPositiveLoginWithValidCredentials() {
        logger.info("Начало выполнения теста: Позитивный сценарий авторизации");

        loginPage.openLoginPage();
        // Укажите ваши реальные тестовые данные в проекте
        loginPage.loginWithCredentials("+79000000000", "ValidPassword123");

        logger.info("Выполнение проверок перенаправления (Assertions)");
        String currentUrl = driver.getCurrentUrl();

        Assertions.assertTrue(currentUrl.contains("profile") || currentUrl.contains("account"),
                "Пользователь не был перенаправлен в личный кабинет. Текущий URL: " + currentUrl);

        logger.info("Тест успешно завершен. Пользователь авторизован, URL: {}", currentUrl);
    }

    @Test
    @DisplayName("Негативный сценарий: Попытка входа с пустыми полями")
    public void testLoginWithEmptyFields() {
        logger.info("Начало теста: Авторизация с пустыми полями");
        driver.get(BASE_UI_URL + "auth/");

        // Передаем пустые строки
        loginPage.loginWithCredentials("", "");

        logger.info("Выполнение проверок локальной валидации");
        Assertions.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Ошибка: При отправке пустой формы не появилось уведомление или подсветка полей!");
    }

    @Test
    @DisplayName("Негативный сценарий: Ввод некорректного формата телефона")
    public void testLoginWithInvalidPhoneFormat() {
        logger.info("Начало теста: Ввод некорректного формата телефона");
        driver.get(BASE_UI_URL + "auth/");

        // Вводим слишком короткий номер, который точно не пройдет валидацию
        loginPage.loginWithCredentials("123", "AnyPassword123");

        logger.info("Проверка реакции системы на неверный формат ввода");
        Assertions.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Ошибка: Система не отреагировала на невалидный формат номера телефона!");
    }

}

