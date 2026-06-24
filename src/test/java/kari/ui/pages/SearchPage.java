package kari.ui.pages;

import io.qameta.allure.Step;
import kari.ui.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage extends BasePage {

    private final String EMPTY_SEARCH_RESULT_LOCATOR = "//*[contains(text(), 'ничего не найдено')]";
    private final String FIRST_PRODUCT_CARD_LOCATOR = "(//div[contains(@class, 'product-card')]//a | //a[contains(@href, '/product/')])";
    private final String SEARCH_INPUT_XPATH = "//input[contains(@class, 'search-input')]";
    private final String SHOW_ALL_RESULTS_SEARCH_BUTTON = "//a[contains(text(), 'Показать все результаты')]";

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @Step("UI: Полноценный поиск товара: '{productName}' с отправкой формы (Нажатие Enter)")
    public void searchForProduct(String productName) {
        click(SEARCH_INPUT_XPATH);
        find(SEARCH_INPUT_XPATH).clear();
        find(SEARCH_INPUT_XPATH).sendKeys(productName + org.openqa.selenium.Keys.ENTER);
    }

    @Step("UI: Ввод текста '{productName}' в поле поиска для проверки поисковых подсказок")
    public void typeSearchProduct(String productName) {
        click(SEARCH_INPUT_XPATH);
        find(SEARCH_INPUT_XPATH).clear();
        find(SEARCH_INPUT_XPATH).sendKeys(productName);
    }

    @Step("UI: Проверка появления сообщения о том, что ничего не найдено")
    public boolean isEmptyResultMessageDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(EMPTY_SEARCH_RESULT_LOCATOR))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("UI: Валидация выпадающего списка подсказок (Suggest) для слова: '{searchWord}'")
    public void checkSearchSuggestIsDisplayed(String searchWord) {
        String finalXpath = SHOW_ALL_RESULTS_SEARCH_BUTTON + "[contains(text(), '" + searchWord + "')]";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(finalXpath)));
    }

    @Step("UI: Получение текстовой информации из карточки первого найденного товара")
    public String getFirstProductCardText() {
        return getText(FIRST_PRODUCT_CARD_LOCATOR);
    }
}
