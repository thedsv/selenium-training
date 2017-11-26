package ru.stqa.training.selenium.Litecart.MainPage;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.stqa.training.selenium.TestBase;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class ShopcartTests extends TestBase {

  @Test
  public void shopcartTest() {
    goToMainPage();

    for (int i = 1; i <= 3; i++) {
      openFirstProduct();
      selectSizeIfPresent();
      addToCart();
      waitForProductCounterEquals(i);
      returnToMainPage();
    }
    openShopcart();
    removeAllProducts();
  }

  private void waitForProductCounterEquals(int i) {
    wait.until(ExpectedConditions.textToBePresentInElement(By.cssSelector(".content>.quantity"), String.valueOf(i)));
  }

  private void removeAllProducts() {
    while (!shipcartIsEmpty()) {
      List<WebElement> productsList = driver.findElements(By.cssSelector("#box-checkout-summary tr:not(.header) .item"));
      String productName = productsList.get(0).getText();
      driver.findElement(By.xpath(String.format("//div[contains(.,'%s')]/p/button[text()='Remove']", productName))).click();
      Assert.assertTrue("Товар не удалился из списка", isElementAbsent(productsList.get(0)));
    }
  }

  private boolean shipcartIsEmpty() {
    return isElementPresent(By.xpath("//*[contains(text(),'There are no items in your cart')]"));
  }

  private void openShopcart() {
    driver.findElement(By.partialLinkText("Checkout")).click();
  }

  private void returnToMainPage() {
    driver.findElement(By.cssSelector(".fa.fa-home")).click();
    wait.until(titleIs("Online Store | My Store"));
  }

  private void addToCart() {
    driver.findElement(By.name("add_cart_product")).click();
  }

  private void selectSizeIfPresent() {
    if (isElementPresent(By.name("options[Size]"))) {
      driver.findElement(By.xpath("//option[not(@selected)]")).click();
    }
  }

  private void openFirstProduct() {
    driver.findElement(By.cssSelector(".product")).click();
  }

  private void goToMainPage() {
    driver.navigate().to("http://localhost/litecart/public_html/en/");
    wait.until(titleIs("Online Store | My Store"));
  }

  private boolean isElementPresent(By locator) {
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

  private boolean isElementAbsent(WebElement element) {
    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    Boolean elementIsAbsent = wait.until(stalenessOf(element));
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    return elementIsAbsent;
  }


}
