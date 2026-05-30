package ru.otus.pages;

import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.otus.config.WebDriverManager;
import ru.otus.pages.annotatios.Path;

import java.util.List;
import java.util.Random;

@Path("/")
@Component
@Lazy
public class MainPage extends BasePage<MainPage> {

    @Autowired
    WebDriverManager context;

    @Autowired
    private CoursesPage coursesPage;

    private final String studyDropdownButtonLocator = "//span[@title='Обучение']";
    private final String allCoursesLocators = "//*[@id=\"main-menu\"]/descendant::p[contains(text(),'Направления')][1]" +
            "/following-sibling::*/a";

    public String openRandomStudy() {
        getElement(studyDropdownButtonLocator).click();
        List<WebElement> courses = getElements(allCoursesLocators);
        int rnd = new Random().nextInt(courses.size());
        String courseName = courses.get(rnd).getText();
        courses.get(rnd).click();
        return courseName;
    }
}
