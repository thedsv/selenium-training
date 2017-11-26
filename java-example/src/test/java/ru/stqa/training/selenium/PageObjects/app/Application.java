package ru.stqa.training.selenium.PageObjects.app;

import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.training.selenium.PageObjects.pages.MainPage;
import ru.stqa.training.selenium.PageObjects.pages.ProductPage;
import ru.stqa.training.selenium.PageObjects.pages.ShopcartPage;

public class Application {

  private WebDriverWait wait;
  private WebDriver driver;

  private MainPage mainPage;
  private ProductPage productPage;
  private ShopcartPage shopcartPage;

  public Application() {
    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability(FirefoxDriver.MARIONETTE, false);
    driver = new ChromeDriver(caps);
//    driver = new FirefoxDriver(caps);
//    driver = new InternetExplorerDriver(caps);
    System.out.println(((HasCapabilities) driver).getCapabilities());
    wait = new WebDriverWait(driver, 10);

    mainPage = new MainPage(driver);
    productPage = new ProductPage(driver);
    shopcartPage = new ShopcartPage(driver);
  }

  public void quit() {
    driver.quit();
  }

//  region Page actions

  public void removeAllProducts() {
    while (!shopcartPage.shipcartIsEmpty()) {
      shopcartPage.removeFirstProduct();
    }
  }

  public void goToMainPage() {
    mainPage.open();
  }

  public void addProductToShopcart() {
    int currentShopcartSize = Integer.valueOf(driver.findElement(By.cssSelector(".content>.quantity")).getText());
    mainPage.openFirstProduct();
    productPage.selectSizeIfPresent();
    productPage.addToCart();
    productPage.waitForShopcartSizeEquals(++currentShopcartSize);
    productPage.returnToMainPage();

  }

  public void addProductsToShopcart(int productsCount) {
    for (int i = 1; i <= productsCount; i++) {
      addProductToShopcart();
    }
  }

  public void openShopcart() {
    mainPage.openShopcart();
  }

//  region end

}

