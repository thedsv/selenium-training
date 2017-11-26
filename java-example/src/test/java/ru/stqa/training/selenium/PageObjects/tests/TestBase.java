package ru.stqa.training.selenium.PageObjects.tests;

import org.junit.After;
import org.junit.Before;
import ru.stqa.training.selenium.PageObjects.app.Application;

public class TestBase {

  public static ThreadLocal<Application> tlApp = new ThreadLocal<>();
  public Application app;

  @Before
  public void start() {
    if (tlApp.get() != null) {
      app = tlApp.get();
      return;
    }

    app = new Application();
    tlApp.set(app);

    Runtime.getRuntime().addShutdownHook(
            new Thread(() -> {
              app.quit();
              app = null;
            }));
  }

  @After
  public void stop() {
    //app.quit();
    //app = null;
  }
}
