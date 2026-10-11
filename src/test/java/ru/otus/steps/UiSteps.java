package ru.otus.steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.config.WebDriverManager;

import static java.lang.Thread.sleep;

@Slf4j
public class UiSteps {
    @Autowired
    WebDriverManager manager;

    @Когда("^откроем браузер$")
    public void openBrowser() {
        manager.start();
    }


    @И("подождем")
    public void подождем() throws InterruptedException {
        sleep(10000);
    }


}
