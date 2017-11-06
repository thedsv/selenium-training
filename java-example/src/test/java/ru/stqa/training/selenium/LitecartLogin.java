package ru.stqa.training.selenium;


import org.junit.Test;
import org.openqa.selenium.By;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LitecartLogin extends TestBase {

  @Test
  public void litecartAdminLoginTest() {
    driver.navigate().to("http://localhost/litecart/public_html/admin/");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("login")).click();
    wait.until(titleIs("My Store"));
  }
}
