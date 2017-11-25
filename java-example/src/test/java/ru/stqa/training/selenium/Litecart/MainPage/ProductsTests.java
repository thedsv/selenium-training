package ru.stqa.training.selenium.Litecart.MainPage;


import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.stqa.training.selenium.TestBase;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class ProductsTests extends TestBase {

  @Test
  public void litecartProductsTest() {
    driver.navigate().to("http://localhost/litecart/public_html/en/");
    wait.until(titleIs("Online Store | My Store"));

    List<WebElement> products = driver.findElements(By.cssSelector(".product"));

    for (WebElement product : products) {
      List<WebElement> productStickers = product.findElements(By.cssSelector(".sticker"));
      Assert.assertTrue("Количество стикеров не равно 1", productStickers.size() == 1);
    }
  }
}
