package kari.ui.pages;

import kari.ui.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MainPage extends BasePage {

    private static final String MENU_ITEMS_LOCATOR = "//ul[contains(@class, 'e6gdblz1')]/li/a";
    private final String FOOTER_COPIRIGHT = "//*[contains(text(), '© kari. Все права защищены. 2011-')]";
    private final String LOGO_XPATH = "//a[@aria-label='Главная страница']";
    private final String CART_ICON_XPATH = "//*[contains(@href, '/cart') or contains(@class, 'basket') or contains(@class, 'cart')]";

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public WebElement checkMainPageOpened() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FOOTER_COPIRIGHT)));
    }

    public void clickLogo() {
        click(LOGO_XPATH);
    }

    public void clickCartIcon() {
        click(CART_ICON_XPATH);
    }

    public List<String> getActualMenuItems() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(MENU_ITEMS_LOCATOR)));

        List<String> textList = new ArrayList<>();
        for (WebElement element : elements) {
            String text = element.getText().trim();
            if (text.isEmpty()) {
                text = element.getAttribute("textContent").trim();
            }
            if (!text.isEmpty()) {
                textList.add(text);
            }
        }
        return textList;
    }
}
