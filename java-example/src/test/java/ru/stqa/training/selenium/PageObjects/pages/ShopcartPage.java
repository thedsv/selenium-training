package ru.stqa.training.selenium.PageObjects.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ShopcartPage extends Page {
  public ShopcartPage(WebDriver driver) {
    super(driver);
  }

  public boolean shipcartIsEmpty() {
    return isElementPresent(By.xpath("//*[contains(text(),'There are no items in your cart')]"));
  }

  public void removeFirstProduct() {
    List<WebElement> productsList = driver.findElements(By.cssSelector("#box-checkout-summary tr:not(.header) .item"));
    String productName = productsList.get(0).getText();
    driver.findElement(By.xpath(String.format("//div[contains(.,'%s')]/p/button[text()='Remove']", productName))).click();
    Assert.assertTrue("Товар не удалился из списка", isElementAbsent(productsList.get(0)));
  }


}
