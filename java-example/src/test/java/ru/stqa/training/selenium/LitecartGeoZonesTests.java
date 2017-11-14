package ru.stqa.training.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LitecartGeoZonesTests extends TestBase {

  @Test
  public void litecartGeoZonesTests() {
    driver.navigate().to("http://localhost/litecart/public_html/admin/");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("login")).click();
    wait.until(titleIs("My Store"));

    driver.findElement(By.xpath("//span[text()='Geo Zones']")).click();

    List<WebElement> zonesRowList = driver.findElements(By.cssSelector(".row"));

    for (int i = 0; i < zonesRowList.size(); i++) {
      driver.findElements(By.cssSelector(".row>td>a:not([title])")).get(i).click();

      List<WebElement> zonesList = driver.findElements(By.xpath("//select[contains(@name, 'zone_code')]/option[@selected]"));
      ArrayList<String> actualZonesList = new ArrayList<>();

      for (WebElement zone : zonesList) {
        actualZonesList.add(zone.getText());
      }
      ArrayList<String> expectedZonesList = new ArrayList<>();
      expectedZonesList.addAll(actualZonesList);
      Collections.sort(expectedZonesList);
      Assert.assertTrue("Список геозон расположен не в алфавитном порядке", actualZonesList.equals(expectedZonesList));
      driver.navigate().back();
    }

  }
}
