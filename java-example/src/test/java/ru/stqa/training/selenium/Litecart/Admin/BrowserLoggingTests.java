package ru.stqa.training.selenium.Litecart.Admin;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.stqa.training.selenium.TestBase;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class BrowserLoggingTests extends TestBase {

  @Test
  public void browserLoggingTest() {
    login("admin", "admin");
    driver.navigate().to("http://localhost/litecart/public_html/admin/?app=catalog&doc=catalog&category_id=1");
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(text(), 'Catalog')]")));

    List<WebElement> products = driver.findElements(By.xpath("//tr[contains(@class, 'row')]//td[not(i)]/a[not(@title)]"));

    for (int i = 0; i < products.size(); i++) {
      List<WebElement> productsTmp = driver.findElements(By.xpath("//tr[contains(@class, 'row')]//td[not(i)]/a[not(@title)]"));
      String productName = productsTmp.get(i).getText();
      productsTmp.get(i).click();

      //Проверяем наличие и выводим сообщения уровня WARNING
      List<String> warningsList = new ArrayList<>();
      driver.manage().logs().get("browser").filter(Level.WARNING).forEach(l -> warningsList.add(String.valueOf(l)));
      if (warningsList.size() != 0) {
        System.out.println(String.format("\n---------При открытии товара %s в логе браузера обнаружены сообщения уровня WARNING:----------", productName));
        for (String warning : warningsList) {
          System.out.println(warning);
        }
      }

      //Проверяем наличие сообщений уровня SEVERE и фейлим тест, если обнаружены
      List<LogEntry> failuresList = driver.manage().logs().get("browser").filter(Level.SEVERE);
      if (failuresList.size() != 0) {
        Assert.assertTrue(String.format("При открытии товара %s в логе браузера обнаружены сообщения уровня SEVERE:\n%s", productName, String.valueOf(failuresList)), false);
      }
      driver.navigate().back();
    }
  }

  private void login(String login, String password) {
    driver.navigate().to("http://localhost/litecart/public_html/admin/");
    driver.findElement(By.name("username")).sendKeys(login);
    driver.findElement(By.name("password")).sendKeys(password);
    driver.findElement(By.name("login")).click();
    wait.until(titleIs("My Store"));
  }


}
