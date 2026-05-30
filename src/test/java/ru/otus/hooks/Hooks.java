package ru.otus.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.config.WebDriverManager;

@Slf4j
public class Hooks {

    @Autowired
    WebDriverManager manager;

    @Before
    public void test(){
        log.info("123");
    }

    @After
    public void shutdown(){
        manager.quit();
    }
}
