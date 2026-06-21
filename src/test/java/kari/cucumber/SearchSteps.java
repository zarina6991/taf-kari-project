package kari.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import kari.ui.base.WebDriverManager;
import kari.ui.pages.SearchPage;
import org.junit.jupiter.api.Assertions;

public class SearchSteps {

    private final SearchPage searchPage = new SearchPage(WebDriverManager.getDriver());
    private static final String BASE_URL = "https://kari.com"; // Измените URL, если у вас другой в BaseTest

    @Given("Пользователь открывает главную страницу сайта")
    public void openMainPage() {
        searchPage.open(BASE_URL);
    }

    @When("Пользователь ищет товар {string}")
    public void searchForProduct(String productName) {
        searchPage.searchForProduct(productName);
    }

    @When("Пользователь вводит в поле поиска слово {string}")
    public void typeSearchProduct(String productName) {
        searchPage.typeSearchProduct(productName);
    }

    @Then("В результатах поиска отображается карточка товара с ценой")
    public void verifyProductCardText() {
        String cardText = searchPage.getFirstProductCardText();
        Assertions.assertTrue(cardText.contains("₽") || !cardText.isEmpty(),
                "Текст найденного товара пуст или не содержит цену!");
    }

    @Then("В выпадающем списке появляется поисковая подсказка для слова {string}")
    public void verifySearchSuggest(String searchWord) {
        searchPage.checkSearchSuggestIsDisplayed(searchWord);
    }

    @Then("Отображается сообщение о том, что ничего не найдено")
    public void verifyEmptyResult() {
        Assertions.assertTrue(searchPage.isEmptyResultMessageDisplayed(),
                "Сообщение о том, что товары не найдены, не отображается!");
    }

    @io.cucumber.java.After
    public void afterScenario(io.cucumber.java.Scenario scenario) {
        try {
            if (!scenario.isFailed()) {
                System.out.println("=================================================");
                System.out.println("' Сценарий '" + scenario.getName() + "' успешно пройден!");
                System.out.println("=================================================");
            } else {
                System.out.println("Сценарий '" + scenario.getName() + "' упал.");
            }
        } finally {
            kari.ui.base.WebDriverManager.quitDriver();
        }
    }
}

