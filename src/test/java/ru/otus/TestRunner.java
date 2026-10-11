package ru.otus;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/ru/otus/features",
        glue = {"ru.otus.config", "ru.otus.steps", "ru.otus.hooks"},
        plugin = {"summary", "progress", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        dryRun = false
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
