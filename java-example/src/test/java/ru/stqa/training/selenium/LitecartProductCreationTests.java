package ru.stqa.training.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LitecartProductCreationTests extends TestBase {

  private String unique = System.currentTimeMillis() + "";
  private final String name = "Duck_" + unique;
  private final String code = unique.substring(0, 5);
  private File image = new File("src/test/resources/blackduck.jpg");

  @Test
  public void litecartProductCreationTests() {
    login("admin", "admin");
    goToCatalogPage();
    initNewProductCreation();

    fillGeneralTab(name, code, "100", image);
    goToInformationTab();
    fillInformationTab("Keys", "Short description", "Description", "Head title", "Meta description");
    goToPricesTab();
    fillPricesTab("15", "USD");
    confirmProductCreation();

    Assert.assertTrue("Созданный продукт отсутствует в списке", productIsPresent(name));
  }

  private boolean productIsPresent(String productName) {
    return driver.findElement(By.xpath(String.format("//a[text()='%s']", productName))).isDisplayed();
  }

  private void confirmProductCreation() {
    driver.findElement(By.name("save")).click();
    wait.until(titleIs("Catalog | My Store"));
  }

  private void fillPricesTab(String price, String currency) {
    driver.findElement(By.name("purchase_price")).sendKeys(price);
    WebElement purchasePrice = driver.findElement(By.name("purchase_price_currency_code"));
    purchasePrice.findElement(By.xpath(String.format("//option[@value='%s']", currency))).click();
  }

  private void goToPricesTab() {
    driver.findElement(By.xpath("//a[text()='Prices']")).click();
    sleep(300);
  }

  private void fillInformationTab(String keywords, String shortDescription, String description, String headTitle, String metaDescription) {
    WebElement manufacturer = driver.findElement(By.name("manufacturer_id"));
    manufacturer.findElement(By.xpath("./option[not(@selected)]")).click();
    driver.findElement(By.name("keywords")).sendKeys(keywords);
    driver.findElement(By.name("short_description[en]")).sendKeys(shortDescription);
    driver.findElement(By.cssSelector(".trumbowyg-editor")).sendKeys(description);
    driver.findElement(By.name("head_title[en]")).sendKeys(headTitle);
    driver.findElement(By.name("meta_description[en]")).sendKeys(metaDescription);
  }

  private void goToInformationTab() {
    driver.findElement(By.xpath("//a[text()='Information']")).click();
    sleep(300);
  }

  private void fillGeneralTab(String name, String code, String quantity, File image) {
    driver.findElement(By.name("name[en]")).sendKeys(name);
    driver.findElement(By.name("code")).sendKeys(code);
    driver.findElement(By.name("quantity")).sendKeys(quantity);
    driver.findElement(By.name("new_images[]")).sendKeys(image.getAbsolutePath());
  }

  private void initNewProductCreation() {
    driver.findElement(By.xpath("//a[contains(text(), 'Add New Product')]")).click();
  }

  private void goToCatalogPage() {
    driver.findElement(By.xpath("//span[text()='Catalog']")).click();
  }

  private void login(String login, String password) {
    driver.navigate().to("http://localhost/litecart/public_html/admin/");
    driver.findElement(By.name("username")).sendKeys(login);
    driver.findElement(By.name("password")).sendKeys(password);
    driver.findElement(By.name("login")).click();
    wait.until(titleIs("My Store"));
  }

  private void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


}
