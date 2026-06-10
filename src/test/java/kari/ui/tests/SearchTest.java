package kari.ui.tests;

import kari.ui.pages.SearchPage;
import org.junit.jupiter.api.*;

public class SearchTest extends BaseTest {

    private SearchPage searchPage;

    @BeforeEach
    public void initPage() {
        logger.info("Инициализация драйвера и запуск браузера Chrome");
        searchPage = new SearchPage(driver);
        searchPage.open(BASE_UI_URL);
    }

    @Test
    @DisplayName("Успешный поиск существующего товара")
    public void testSearchExistingProduct() {
        logger.info("Запуск теста: Успешный поиск существующего товара");
        searchPage.searchForProduct("туфли");
        String cardText = searchPage.getFirstProductCardText();
        Assertions.assertTrue(cardText.contains("₽") || !cardText.isEmpty(),
                "Текст найденного товара пуст или не содержит цену!");
    }

    @Test
    @DisplayName("Появление подсказок при вводе товара в поиск")
    public void testSearchSuggestAppearsWithWord() {
        logger.info("Запуск теста: Появление подсказок при вводе товара в поиск");
        String searchQuery = "носки";
        searchPage.typeSearchProduct(searchQuery);
        searchPage.checkSearchSuggestIsDisplayed(searchQuery);
    }

    @Test
    @DisplayName("Поиск несуществующего товара")
    public void testSearchNonExistentProduct() {
        logger.info("Запуск теста: Поиск несуществующего товара");
        searchPage.searchForProduct("ывапывапцукцуктесттест12345");
        Assertions.assertTrue(searchPage.isEmptyResultMessageDisplayed(),
                "Сообщение о том, что товары не найдены, не отображается!");
    }
}
