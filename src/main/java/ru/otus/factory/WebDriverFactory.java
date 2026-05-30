package ru.otus.factory;

import org.openqa.selenium.WebDriver;
import ru.otus.config.WebDriverConfig;

public class WebDriverFactory {

    public WebDriver getDriver(String browserType) {
        return switch (browserType.toLowerCase()) {
            case "chrome" -> new WebDriverConfig().chromeDriver();
            case "firefox" -> new WebDriverConfig().firefoxDriver();
            default -> throw new IllegalStateException("Unexpected value: " + browserType.toLowerCase());
        };
    }
}
