package ru.otus.config;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import ru.otus.listeners.HighlightListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class WebDriverConfig {

    private static final String MOON_IP = System.getProperty("moon_ip");
    private static final String MOON_PORT = System.getProperty("moon_port");
    private static final String MOON_URL = String.format("http://%s:%s/wd/hub", MOON_IP, MOON_PORT);

    public WebDriver chromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        WebDriver driver = createRemoteDriver(options);
        configureTimeouts(driver);
        return decorateWithListener(driver);
    }

    public WebDriver firefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        WebDriver driver = createRemoteDriver(options);
        configureTimeouts(driver);
        return decorateWithListener(driver);
    }

    private WebDriver createRemoteDriver(org.openqa.selenium.Capabilities options) {
        try {
            return new RemoteWebDriver(new URL(MOON_URL), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Некорректный URL Moon: " + MOON_URL, e);
        }
    }

    private void configureTimeouts(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    private WebDriver decorateWithListener(WebDriver driver) {
        HighlightListener highlightListener = new HighlightListener();
        EventFiringDecorator<WebDriver> decorator = new EventFiringDecorator<>(highlightListener);
        return decorator.decorate(driver);
    }
}