package ru.otus.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.config.WebDriverManager;
import ru.otus.pages.annotatios.Path;

import java.time.Duration;
import java.util.List;

@Component
public abstract class BasePage<T> {

    @Autowired
    WebDriverManager manager;

    private String getPath() {
        Class<T> clazz = (Class<T>) getClass();
        if (clazz.isAnnotationPresent(Path.class)) {
            return clazz.getAnnotation(Path.class).value();
        }
        return "";
    }

    public void open() {
        manager.getDriver().get("https://otus.ru" + getPath());
    }

    protected WebElement getElement(String xPath) {
        waitWhileElementIsPresent(xPath);
        return manager.getDriver().findElement(By.xpath(xPath));
    }

    protected List<WebElement> getElements(String xPath) {
        waitWhileElementIsPresent(xPath);
        return manager.getDriver().findElements(By.xpath(xPath));
    }

    public void waitWhileElementIsPresent(String xPath) {
        PageFactory.initElements(manager.getDriver(), this);
        WebDriverWait wait = new WebDriverWait(manager.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
    }
}