package kari.ui.pages;

import kari.ui.base.BasePage;
import org.openqa.selenium.WebDriver;

public class RegistrationPage extends BasePage {

    private final String PHONE_INPUT = "//input[@name='phone']";
    private final String GET_CODE_BUTTON = "//button[contains(text(), 'Получить код')]";
    private final String ERROR_MESSAGE = "//p[contains(text(), 'Введите оставшиеся цифры')] | //p[contains(text(), 'Обязательное поле')]";
    private final String CAPTCHA_HEADER = "//h3[contains(text(), 'А вы точно не робот?')]";

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public void enterPhoneAndRequestCode(String phone) {
        find(PHONE_INPUT).sendKeys(phone);
        click(GET_CODE_BUTTON);
    }

    public void clickGetCodeButtonOnly() {
        click(GET_CODE_BUTTON);
    }

    public String getValidationErrorText() {
        return getText(ERROR_MESSAGE);
    }

    public boolean isCaptchaDisplayed() {
        return isElementVisible(CAPTCHA_HEADER);
    }
}
