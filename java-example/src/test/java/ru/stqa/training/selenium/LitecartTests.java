package ru.stqa.training.selenium;


import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LitecartTests extends TestBase {

  @Test
  public void litecartTest() {
    driver.navigate().to("http://localhost/litecart/public_html/admin/");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("login")).click();
    wait.until(titleIs("My Store"));


    int sectionsCount = driver.findElements(By.id("app-")).size();

    for (int i = 1; i <= sectionsCount; i++) {
      WebElement section = driver.findElement(By.xpath(String.format("//li[@id='app-'][%s]/a", i)));
      String sectionName = section.getText();
      section.click();
      int subSectionsCount = driver.findElements(By.xpath("//ul[@class='docs']/li")).size();

      if (subSectionsCount > 0) {
        for (int j = 1; j <= subSectionsCount; j++) {
          WebElement subSection = driver.findElement(By.xpath(String.format("//ul[@class='docs']/li[%s]/a", j)));
          String subSectionName = subSection.getText();
          subSection.click();
          if (sectionName.equals("Settings")) {
            Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains("Settings"));
          } else if (subSectionName.equals("Background Jobs")) {
            Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains("Job"));
          } else {
            Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains(subSectionName));
          }
        }
      } else {
        Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains(sectionName));
      }
    }
  }
}
