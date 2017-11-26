package ru.stqa.training.selenium.PageObjects.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MainPage extends Page {

  public MainPage(WebDriver driver) {
    super(driver);
  }

  public MainPage open() {
    driver.navigate().to("http://localhost/litecart/public_html/en/");
    wait.until(titleIs("Online Store | My Store"));
    return this;
  }

  public String openFirstProduct() {
    WebElement firstProduct = driver.findElement(By.cssSelector(".product"));
    String firstProductName = firstProduct.findElement(By.cssSelector(".name")).getText();
    firstProduct.click();
    return firstProductName;
  }


}
