package kari.ui.tests;

import kari.ui.pages.MainPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static kari.ui.tests.BaseTest.BASE_UI_URL;
import static kari.ui.tests.BaseTest.logger;

public class MainTest {

    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    public void setUp() {
        logger.info("Инициализация драйвера и запуск браузера Chrome");
        driver = kari.ui.base.WebDriverManager.getDriver();
        mainPage = new MainPage(driver);
        logger.info("Открытие главной страницы сайта: {}", BASE_UI_URL);
        mainPage.open(BASE_UI_URL);
    }

    @Test
    @DisplayName("Проверка успешного открытия главной страницы")
    void testMainPageOpening() {
        logger.info("Проверка актуального URL адреса страницы");
        Assertions.assertTrue(driver.getCurrentUrl().contains("kari.com"),
                "Открылся неверный URL-адрес страницы!");
        WebElement footer = mainPage.checkMainPageOpened();
        logger.info("Проверка отображения элементов футера сайта");
        Assertions.assertTrue(footer.isDisplayed(),
                "Главная страница не открылась: футер 'Все права защищены' не отображается");
    }

    @Test
    @DisplayName("Клик по логотипу бренда возвращает на главную страницу")
    void testClickLogoReturnsToMainPage() {
        logger.info("Запуск теста: Клик по логотипу бренда возвращает на главную страницу");
        mainPage.clickLogo();
        Assertions.assertEquals(BASE_UI_URL, driver.getCurrentUrl(),
                "Клик по логотипу не вернул пользователя на главную страницу!");
    }

    @Test
    @DisplayName("Переход в корзину через шапку главной страницы")
    void testNavigateToCartFromHeader() {
        logger.info("Запуск теста: Переход в корзину через шапку главной страницы");
        mainPage.clickCartIcon();
        WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5));
        boolean isCartUrl = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("cart"));
        Assertions.assertTrue(isCartUrl, "Переход в корзину не произошел!");
    }

    @Test
    @DisplayName("Проверка отображения и состава пунктов главного меню")
    void testMainPageMenuItems() {
        logger.info("Запуск теста: Проверка отображения и состава пунктов главного меню");
        List<String> expectedItems = List.of("ЖЕНЩИНАМ", "МУЖЧИНАМ", "ДЕТСКИЕ ТОВАРЫ", "ЮВЕЛИРНЫЕ УКРАШЕНИЯ", "КРАСОТА И ЗДОРОВЬЕ", "ТОВАРЫ ДЛЯ ДОМА", "ТЕХНИКА", "АКЦИИ");
        List<String> actualItems = mainPage.getActualMenuItems();
        Assertions.assertFalse(actualItems.isEmpty(), "Список пунктов меню пуст!");
        Assertions.assertEquals(expectedItems, actualItems, "Состав или порядок меню не совпадает!");
    }
}
