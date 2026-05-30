package ru.otus.listeners;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import java.lang.reflect.Method;

public class HighlightListener implements WebDriverListener {

    private static final String HIGHLIGHT_SCRIPT =
            "arguments[0].style.border='3px solid red';" +
                    "arguments[0].style.backgroundColor='yellow';" +
                    "setTimeout(function() {arguments[0].style.border=''; " +
                    "arguments[0].style.backgroundColor='';}, 500);";

    @Override
    public void beforeClick(WebElement element) {
        highlightElement(element);
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        highlightElement(element);
    }

    private void highlightElement(WebElement element) {
        try {
            WebDriver driver = getDriverFromElement(element);
            if (driver != null) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript(HIGHLIGHT_SCRIPT, element);
                Thread.sleep(1000);
            }
        } catch (Exception ignored) {
        }
    }

    private WebDriver getDriverFromElement(WebElement element) {
        try {
            Method method = element.getClass().getMethod("getWrappedDriver");
            return (WebDriver) method.invoke(element);
        } catch (Exception ignored) {
            return null;
        }
    }
}