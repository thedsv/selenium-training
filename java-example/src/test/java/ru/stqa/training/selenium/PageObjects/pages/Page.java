package ru.stqa.training.selenium.PageObjects.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class Page {

  protected WebDriverWait wait;
  protected WebDriver driver;

  public Page(WebDriver driver) {
    this.driver = driver;
    wait = new WebDriverWait(driver, 10);
  }

  protected boolean isElementPresent(By locator) {
    return isElementPresent(locator, 0);
  }

  private boolean isElementPresent(By locator, int timeout) {
    try {
      driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
      return driver.findElements(locator).size() > 0;
    } finally {
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
  }

  protected boolean isElementAbsent(WebElement element) {
    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    Boolean elementIsAbsent = wait.until(stalenessOf(element));
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    return elementIsAbsent;
  }

  public void openShopcart() {
    driver.findElement(By.partialLinkText("Checkout")).click();
  }

}
