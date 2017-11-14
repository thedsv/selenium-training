package ru.stqa.training.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class LitecartCountriesTests extends TestBase {

  @Test
  public void litecartCountriesTests() {
    driver.navigate().to("http://localhost/litecart/public_html/admin/");
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("login")).click();
    wait.until(titleIs("My Store"));

    driver.findElement(By.xpath("//span[text()='Countries']")).click();


    List<WebElement> countriesRowList = driver.findElements(By.cssSelector(".row"));
    ArrayList<String> actualCountriesList = new ArrayList<>();

    for (int i = 0; i < countriesRowList.size(); i++) {
      WebElement countryRow = driver.findElements(By.cssSelector(".row")).get(i);
      WebElement country = countryRow.findElement(By.xpath("./td[5]/a"));
      actualCountriesList.add(country.getText());

      if (!countryRow.findElement(By.xpath("./td[6]")).getText().equals("0")) {
        country.click();
        List<WebElement> zonesList = driver.findElements(By.xpath("//input[contains(@name, 'name')][@type='hidden']"));
        ArrayList<String> actualZonesList = new ArrayList<>();

        for (WebElement zone : zonesList) {
          actualZonesList.add(zone.getAttribute("value"));
        }
        ArrayList<String> expectedZonesList = new ArrayList<>();
        expectedZonesList.addAll(actualZonesList);
        Collections.sort(expectedZonesList);
        Assert.assertTrue("Список геозон расположен не в алфавитном порядке", actualZonesList.equals(expectedZonesList));
        driver.navigate().back();
      }
    }

    ArrayList<String> expectedCountriesList = new ArrayList<>();
    expectedCountriesList.addAll(actualCountriesList);
    Collections.sort(expectedCountriesList);
    Assert.assertTrue("Список стран расположен не в алфавитном порядке", actualCountriesList.equals(expectedCountriesList));
  }

}
