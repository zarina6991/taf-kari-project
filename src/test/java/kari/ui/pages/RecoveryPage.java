package kari.ui.pages;

import kari.ui.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static jdk.internal.misc.ThreadFlock.open;
import static kari.ui.tests.BaseTest.BASE_UI_URL;

public class RecoveryPage extends BasePage {

    private static final String RECOVERY_URL = BASE_UI_URL + "auth/recovery/?redirection=";

    private static final String PHONE_INPUT_XPATH = "//input[@name='phone']";
    private static final String GET_CODE_BUTTON_XPATH = "//button[contains(., 'Получить код')]";
    private static final String CLOSE_CAPTCHA_BUTTON_XPATH = "//button[@class='css-1kwkvcv']";

    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    public RecoveryPage(WebDriver driver) {
        super(driver);
    }

    public void openRecoveryPage() {
        open(RECOVERY_URL);
    }

    public void enterPhoneNumber(String phoneWithoutCountryCode) {
        WebElement input = find(PHONE_INPUT_XPATH);
        input.click();
        input.sendKeys(phoneWithoutCountryCode);
    }

    public void clickGetCode() {
        click(GET_CODE_BUTTON_XPATH);
    }

    public void checkPhoneInputHasError() {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
        WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PHONE_INPUT_XPATH)));
        String ariaInvalid = phoneInput.getAttribute("aria-invalid");
        String value = phoneInput.getAttribute("value");

        if (!"true".equals(ariaInvalid) && (value != null && !value.trim().isEmpty())) {
            throw new IllegalStateException("Поле не находится в состоянии ошибки!");
        }
    }

    public void checkPhoneInputIsVisible() {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
        WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PHONE_INPUT_XPATH)));
        if (!phoneInput.isDisplayed()) {
            throw new IllegalStateException("Элемент присутствует в DOM, но скрыт от пользователя");
        }
    }

    public void closeCaptchaModal() {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
        WebElement closeButton = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath(CLOSE_CAPTCHA_BUTTON_XPATH))
        );
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", closeButton);
    }

    public void checkSmsCodeInputIsVisible() {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(By.xpath(CLOSE_CAPTCHA_BUTTON_XPATH))
        );
    }
}
