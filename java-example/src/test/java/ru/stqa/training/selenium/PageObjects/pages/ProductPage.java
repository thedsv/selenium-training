package ru.stqa.training.selenium.PageObjects.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class ProductPage extends Page {
  public ProductPage(WebDriver driver) {
    super(driver);
  }

  public void selectSizeIfPresent() {
    if (isElementPresent(By.name("options[Size]"))) {
      driver.findElement(By.xpath("//option[not(@selected)]")).click();
    }
  }

  public void addToCart() {
    driver.findElement(By.name("add_cart_product")).click();
  }

  public void waitForShopcartSizeEquals(int expectedProductCount) {
    wait.until(ExpectedConditions.textToBePresentInElement(By.cssSelector(".content>.quantity"), String.valueOf(expectedProductCount)));
  }

  public void returnToMainPage() {
    driver.findElement(By.cssSelector(".fa.fa-home")).click();
    wait.until(titleIs("Online Store | My Store"));
  }

}
