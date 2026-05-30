package ru.otus.config;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;
import ru.otus.factory.WebDriverFactory;

@Component
public class WebDriverManager {

    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public WebDriver getDriver() {
        if (webDriver.get() == null) {
            return null;
        }
        return webDriver.get();
    }

    public WebDriver start(String browser) {
        if (webDriver.get() == null) {
            webDriver.set(new WebDriverFactory().getDriver(browser));
        }
        return webDriver.get();
    }

    public void quit() {
        if (webDriver != null) {
            webDriver.get().quit();
            webDriver.remove();
        }
    }
}
