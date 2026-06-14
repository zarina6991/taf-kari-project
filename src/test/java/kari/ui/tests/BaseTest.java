package kari.ui.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {

    protected WebDriver driver;

    public static final String BASE_UI_URL = "https://kari.com/";

    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeEach
    public void setUp() {
        logger.info("Инициализация драйвера: запрашиваем экземпляр у синглтона WebDriverManager");
        driver = kari.ui.base.WebDriverManager.getDriver();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            logger.info("Завершение теста: закрываем сессию браузера через WebDriverManager");
            kari.ui.base.WebDriverManager.quitDriver();
        }
    }
}
