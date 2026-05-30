package ru.otus.steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Тогда;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import ru.otus.pages.CoursesPage;
import ru.otus.pages.MainPage;

public class MainPageSteps {
    @Autowired
    MainPage mainPage;
    @Autowired
    CoursesPage coursesPage;

    @И("^откроем главную страницу$")
    public void openMainPage() {
        mainPage.open();
    }

    @Тогда("^откроем случайное направление и проверим наименование$")
    public void openRandomCourse() {
        String rndCourseName = mainPage.openRandomStudy();
        String selectedStudy = coursesPage.getSelectedStudy();
        Assert.assertTrue(rndCourseName.contains(selectedStudy));
    }
}
