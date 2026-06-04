package kari.ui.pages;

import kari.ui.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import static kari.ui.tests.BaseTest.BASE_UI_URL;

public class LoginPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    private static final String PHONE_OR_EMAIL_INPUT = "//input[@type='tel' or @type='email' or contains(@placeholder, 'телефон') or contains(@placeholder, 'Email')]";
    private static final String PASSWORD_INPUT = "//input[@type='password' or contains(@placeholder, 'Пароль')]";
    private static final String SUBMIT_LOGIN_BUTTON = "//button[@type='submit' or contains(text(), 'Войти')]";
    private static final String ERROR_MESSAGE = "//*[contains(@class, 'error') or contains(text(), 'Неверный') or contains(text(), 'ошибка')]";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void openLoginPage() {
        logger.info("Открытие страницы авторизации по URL: {}", BASE_UI_URL+ "auth/");
        open(BASE_UI_URL+ "auth/");
    }

    public void loginWithCredentials(String username, String password) {
        logger.info("Ввод логина (длина: {} символов)", username.length());
        find(PHONE_OR_EMAIL_INPUT).clear();
        find(PHONE_OR_EMAIL_INPUT).sendKeys(username);

        logger.info("Ввод пароля");
        find(PASSWORD_INPUT).clear();
        find(PASSWORD_INPUT).sendKeys(password);

        logger.info("Клик по кнопке отправки формы авторизации");
        click(SUBMIT_LOGIN_BUTTON);
    }

    public boolean isErrorMessageDisplayed() {
        boolean isVisible = isElementVisible(ERROR_MESSAGE);
        logger.info("Проверка видимости сообщения об ошибке. Результат: {}", isVisible);
        return isVisible;
    }

    public String getErrorMessageText() {
        String text = getText(ERROR_MESSAGE);
        logger.info("Получен текст ошибки: '{}'", text);
        return text;
    }
}
