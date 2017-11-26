package ru.stqa.training.selenium.Litecart.Admin;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import ru.stqa.training.selenium.TestBase;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class CountryEditionTests extends TestBase {

  @Test
  public void countryEditionTest() {
    login("admin", "admin");
    goToCountriesPage();

    if (getCountriesCount() > 0) {
      editFirstCountry();
    } else {
      createNewCountry();
    }

    List<WebElement> externalLinksList = driver.findElements(By.cssSelector(".fa.fa-external-link"));
    for (WebElement externalLink : externalLinksList) {
      String mainWindow = driver.getWindowHandle();
      Set<String> existingWindows = driver.getWindowHandles();
      externalLink.click();
      String newWindow = wait.until(anyWindowOtherThan(existingWindows));
      driver.switchTo().window(newWindow);
      driver.close();
      driver.switchTo().window(mainWindow);
    }

  }

  public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
    return new ExpectedCondition<String>() {
      @Nullable
      @Override
      public String apply(@Nullable WebDriver input) {
        Set<String> handles = driver.getWindowHandles();
        handles.removeAll(oldWindows);
        return handles.size() > 0 ? handles.iterator().next() : null;
      }
    };
  }

  private void editFirstCountry() {
    driver.findElement(By.cssSelector(".fa.fa-pencil")).click();
  }

  private int getCountriesCount() {
    return driver.findElements(By.cssSelector(".row")).size();
  }

  private void createNewCountry() {
    driver.findElement(By.xpath("//a[@class='button'][contains(@href, 'edit_country')]")).click();
  }

  private void goToCountriesPage() {
    driver.findElement(By.xpath("//span[text()='Countries']")).click();
  }

  private void login(String login, String password) {
    driver.navigate().to("http://localhost/litecart/public_html/admin/");
    driver.findElement(By.name("username")).sendKeys(login);
    driver.findElement(By.name("password")).sendKeys(password);
    driver.findElement(By.name("login")).click();
    wait.until(titleIs("My Store"));
  }


}
