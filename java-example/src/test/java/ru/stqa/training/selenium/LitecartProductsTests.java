package ru.stqa.training.selenium;


import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LitecartProductsTests extends TestBase {

  @Test
  public void litecartProductsTest() {
    driver.navigate().to("http://localhost/litecart/public_html/en/");
    wait.until(titleIs("Online Store | My Store"));

    List<WebElement> products = driver.findElements(By.xpath("//div[@id='box-logotypes']/following-sibling::div//li"));

    for (WebElement product : products) {
      List<WebElement> productStickers = product.findElements(By.xpath(".//div[@class='image-wrapper']/div"));
      if (productStickers.size() == 1) {
        String productStickerText = productStickers.get(0).getText();
        Assert.assertTrue("Текст стикера отличен от: NEW, SALE", productStickerText.equals("NEW") || productStickerText.equals("SALE"));
      } else {
        Assert.assertTrue("Количество стикеров не равно 1", false);
      }
    }
  }
}
