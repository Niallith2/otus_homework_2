package ru.otus.pages;

import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.otus.config.WebDriverManager;
import ru.otus.pages.annotatios.Path;

import java.util.List;

@Component
@Path("/catalog/courses")
@Lazy
public class CoursesPage extends BasePage<CoursesPage> {
    @Autowired
    private CoursePage coursePage;
    @Autowired
    WebDriverManager context;

    private final String selectedStudyLocator = "//p[text()='Направление']/../" +
            "following-sibling::*//input[@checked]/../../label";
    private final String searchInputLocator = "//input[@type = 'search']";
    private final String coursesNamesLocator = "//h6/div";
    private final String courseDatesLocator = "//h6/../descendant::*[contains(text(), 'месяц')]";

    public String getSelectedStudy() {
        return getElement(selectedStudyLocator).getText();
    }

    public CoursePage getCourseByName(String name) {
        getElement(searchInputLocator).sendKeys(name);
        waitWhileElementIsPresent(String.format("//h6/div[contains(text(), '%s')]", name));
        List<WebElement> courses = getElements(coursesNamesLocator);
        WebElement course = courses.stream().filter(e -> e.getText().equals(name)).findFirst().get();
        course.click();
        return coursePage;
    }


    public void checkFirstAndLastCourses() {

    }
}
