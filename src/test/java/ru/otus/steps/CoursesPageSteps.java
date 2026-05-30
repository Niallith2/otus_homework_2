package ru.otus.steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.ru.И;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import ru.otus.pages.CoursePage;
import ru.otus.pages.CoursesPage;

public class CoursesPageSteps {
    @Autowired
    CoursesPage coursesPage;

    @Autowired
    CoursePage coursePage;

    @И("^откроем страницу курсов$")
    public void openMainPage() {
        coursesPage.open();
    }


    @И("^откроем курс (.*?)$")
    public void openCourse(String courseName) {
        coursesPage.getCourseByName(courseName);
    }

    @И("^проверим на открывшейся странице заголовок (.*?)$")
    public void checkCourse(String course) {
        Assert.assertEquals(course, coursePage.getHeader());
    }
}
