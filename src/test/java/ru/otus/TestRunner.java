package ru.otus;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/ru/otus/features",
        glue = {"ru.otus.config", "ru.otus.steps", "ru.otus.hooks"},
        plugin = {"summary", "progress"},
        dryRun = false
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
