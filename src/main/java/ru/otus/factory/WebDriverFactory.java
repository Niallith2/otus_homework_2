package ru.otus.factory;

import org.openqa.selenium.WebDriver;
import ru.otus.config.WebDriverConfig;

public class WebDriverFactory {
    private static final String BROWSER = System.getProperty("browser");

    public WebDriver getDriver() {
        return switch (BROWSER.toLowerCase()) {
            case "chrome" -> new WebDriverConfig().chromeDriver();
            case "firefox" -> new WebDriverConfig().firefoxDriver();
            default -> throw new IllegalStateException("Unexpected value: " + BROWSER.toLowerCase());
        };
    }
}
