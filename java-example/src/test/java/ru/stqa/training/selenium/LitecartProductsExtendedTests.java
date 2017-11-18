package ru.stqa.training.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LitecartProductsExtendedTests extends TestBase {

  private final String grayColorRegExp = "#(.)\\1+";
  private final String redColorRegExp = "#[^0]00";


  @Test
  public void litecartProductsTest() {
    driver.navigate().to("http://localhost/litecart/public_html/en/");
    wait.until(titleIs("Online Store | My Store"));

    WebElement campaign = driver.findElement(By.cssSelector("#box-campaigns .product"));

    WebElement mainPageProductNameElement = campaign.findElement(By.cssSelector(".name"));
    WebElement mainPageRegularPriceElement = campaign.findElement(By.cssSelector(".regular-price"));
    WebElement mainPageCampaignPriceElement = campaign.findElement(By.cssSelector(".campaign-price"));

    String mainPageProductName = mainPageProductNameElement.getText();
    String mainPageRegularPrice = mainPageRegularPriceElement.getText();
    String mainPageCampaignPrice = mainPageCampaignPriceElement.getText();

    Assert.assertTrue("Обычная цена не зачеркнута", fontIsStrikethrough(mainPageRegularPriceElement));
    Assert.assertTrue("Обычная цена не серого цвета", fontIsGrayColored(mainPageRegularPriceElement));
    Assert.assertTrue("Акционная цена не жирная", fontIsBold(mainPageCampaignPriceElement));
    Assert.assertTrue("Акционная цена не красная", fontIsRedColored(mainPageCampaignPriceElement));
    Assert.assertTrue("Размер шрифта акционной цены меньше обычной", getFontSize(mainPageCampaignPriceElement) > getFontSize(mainPageRegularPriceElement));

    campaign.click();
    WebElement productNameElement = driver.findElement(By.cssSelector("#box-product .title"));
    WebElement regularPriceElement = driver.findElement(By.cssSelector(".regular-price"));
    WebElement campaignPriceElement = driver.findElement(By.cssSelector(".campaign-price"));

    String productName = productNameElement.getText();
    String regularPrice = regularPriceElement.getText();
    String campaignPrice = campaignPriceElement.getText();

    Assert.assertTrue("Текст названия на главной и странице товара отличаются", productName.equals(mainPageProductName));
    Assert.assertTrue("Обычная цена на главной и странице товара отличаются", regularPrice.equals(mainPageRegularPrice));
    Assert.assertTrue("Акционная цена на главной и странице товара отличаются", campaignPrice.equals(mainPageCampaignPrice));
    Assert.assertTrue("Обычная цена не зачеркнута", fontIsStrikethrough(regularPriceElement));
    Assert.assertTrue("Обычная цена не серого цвета", fontIsGrayColored(regularPriceElement));
    Assert.assertTrue("Акционная цена не жирная", fontIsBold(campaignPriceElement));
    Assert.assertTrue("Акционная цена не красная", fontIsRedColored(campaignPriceElement));
    Assert.assertTrue("Размер шрифта акционной цены меньше обычной", getFontSize(campaignPriceElement) > getFontSize(regularPriceElement));
  }

  private Double getFontSize(WebElement textElement) {
    return Double.valueOf(textElement.getCssValue("font-size").replaceAll("px", ""));
  }

  private boolean fontIsStrikethrough(WebElement textElement) {
    return textElement.getCssValue("text-decoration-line").equals("line-through");
  }

  private boolean fontIsRedColored(WebElement textElement) {
    String[] colorRGBcode = textElement.getCssValue("color").replaceAll("rgba\\(", "").split(", ");
    return !colorRGBcode[0].equals("0") && colorRGBcode[1].equals("0") && colorRGBcode[2].equals("0");
  }

  private boolean fontIsGrayColored(WebElement textElement) {
    String[] colorRGBcode = textElement.getCssValue("color").replaceAll("rgba\\(", "").split(", ");
    return colorRGBcode[0].equals(colorRGBcode[1]) && colorRGBcode[0].equals(colorRGBcode[2]);
  }

  private boolean fontIsBold(WebElement textElement) {
    return Integer.valueOf(textElement.getCssValue("font-weight")) >= 700;
  }
}