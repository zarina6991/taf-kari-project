package kari.ui.pages;

import io.qameta.allure.Step;
import kari.ui.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static kari.ui.tests.BaseTest.BASE_UI_URL;

public class LoginPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    private static final String PHONE_OR_EMAIL_INPUT = "//input[@placeholder='Телефон/E-mail']";
    private static final String PASSWORD_INPUT = "//input[@type='password' or contains(@placeholder, 'Пароль')]";
    private static final String SUBMIT_LOGIN_BUTTON = "//button[@type='submit'][span[text()='Войти']]";
    private static final String ERROR_MESSAGE_FOR_PHONE_NUMBER = "//p[contains(text(), 'Некорректный номер телефона')]";
    public static final  String USER_PROFILE_NAME = "//*[contains(text(), 'Заррина')]";
    private static final String ERROR_MESSAGE = "//p[@color='error' and contains(text(), 'Введите свой телефон или e-mail')]";
    private static final String REQUIRED_FIELD_ERROR = "//*[contains(text(), 'Обязательное поле')]";
    private static final String ERROR_POPUP_MESSAGE = "//li[contains(@class, 'e2g6g1t0')]//p";


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("UI: Открытие страницы авторизации")
    public void openLoginPage() {
        logger.info("Открытие страницы авторизации по URL: {}", BASE_UI_URL + "auth/");
        open(BASE_UI_URL + "auth/");
    }

    @Step("UI: Авторизация пользователя с логином: '{username}' и паролем: '*****'")
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

    @Step("UI: Авторизация с использованием системных свойств окружения")
    public void loginWithSystemProperties() {
        String login = getRequiredSystemProperty("kari.login");
        String password = getRequiredSystemProperty("kari.password");
        this.loginWithCredentials(login, password);
    }

    @Step("UI: Получение текста всплывающего сообщения об ошибке (Popup)")
    public String isErrorMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement errorMessage = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(ERROR_POPUP_MESSAGE)));
            String errorText = errorMessage.getText();
            logger.info("Сообщение об ошибке найдено: {}", errorText);
            return errorText;
        } catch (org.openqa.selenium.TimeoutException e) {
            logger.info("Сообщение об ошибке не появилось на странице");
            return "";
        }
    }

    public String checkUserProfileNameIsDisplayed() {
        logger.info("Ожидание появления имени пользователя 'Заррина'");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement profileNameElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath(USER_PROFILE_NAME))
        );
        String profileText = profileNameElement.getText();
        logger.info("Имя пользователя успешно считано: {}", profileText);
        return profileText;
    }

    @Step("UI: Считывание текста ошибки валидации с формы авторизации")
    public String getAnyFormErrorText() {
        logger.info("Ожидание появления сообщения об ошибке на форме...");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
            String xpath = "//*[contains(text(), 'Неверный логин или пароль')] | "
                    + ERROR_MESSAGE
                    + " | "
                    + REQUIRED_FIELD_ERROR
                    + " | "
                    + ERROR_MESSAGE_FOR_PHONE_NUMBER;
            WebElement errorElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))
            );
            String errorText = errorElement.getText();
            logger.info("На форме зафиксирована ошибка: '{}'", errorText);
            return errorText;
        } catch (org.openqa.selenium.TimeoutException e) {
            logger.warn("Ни одного сообщения об ошибке не появилось на странице");
            return "";
        }
    }
}
