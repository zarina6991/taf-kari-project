package kari.ui.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void open(String url) {
        driver.get(url);
    }

    protected WebElement find(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    protected void click(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    // Умное получение текста у гарантированно загрузившегося элемента
    protected String getText(String xpath) {
        return find(xpath).getText().trim();
    }

    // Умная проверка видимости: возвращает true/false для ассертов в тестах без падения
    protected boolean isElementVisible(String xpath) {
        try {
            return find(xpath).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
