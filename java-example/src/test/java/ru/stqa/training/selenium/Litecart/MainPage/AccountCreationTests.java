package ru.stqa.training.selenium.Litecart.MainPage;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import ru.stqa.training.selenium.TestBase;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class AccountCreationTests extends TestBase {

  private final String unique = System.currentTimeMillis() + "";
  private final String email = uniqueValue("email") + "@email.com";
  private final String password = "testPwd";
  private final String firstname = uniqueValue("Name");
  private final String lastname = uniqueValue("Lastname");

  @Test
  public void litecartAccountCreationTests() {
    goToMainPage();
    initAccountCreation();
    fillAccountCreationForm(firstname,
            lastname,
            uniqueValue("Address"),
            "94024",
            "Cupertino",
            "United States",
            "California",
            email,
            unique.substring(0, 6),
            password);

    confirmAccountCreation();
    logout();
    login(email, password);

    Assert.assertTrue("Не удалось авторизоваться созданным пользователем",
            driver.findElement(By.cssSelector(".notice.success")).getText().contains(firstname + " " + lastname));

    logout();
  }

  private void login(String login, String password) {
    driver.findElement(By.name("email")).sendKeys(login);
    driver.findElement(By.name("password")).sendKeys(password);
    driver.findElement(By.name("login")).click();
  }

  private void logout() {
    driver.findElement(By.linkText("Logout")).click();
  }

  private void confirmAccountCreation() {
    driver.findElement(By.name("create_account")).click();
  }

  private String uniqueValue(String value) {
    return value + "_" + unique;
  }

  private void fillAccountCreationForm(String firstName, String lastname, String mainAddress, String postcode, String city, String country, String state, String email, String phone, String password) {
    driver.findElement(By.name("firstname")).sendKeys(firstName);
    driver.findElement(By.name("lastname")).sendKeys(lastname);
    driver.findElement(By.name("address1")).sendKeys(mainAddress);
    driver.findElement(By.name("postcode")).sendKeys(postcode);
    driver.findElement(By.name("city")).sendKeys(city);

    driver.findElement(By.cssSelector(".select2-selection__arrow")).click();
    driver.findElement(By.cssSelector(".select2-search__field")).sendKeys(country);
    driver.findElement(By.cssSelector(".select2-results li:nth-of-type(1)")).click();

    driver.findElement(By.xpath("//select[@name='zone_code']")).click();
    driver.findElement(By.xpath(String.format("//option[text()='%s']", state))).click();

    driver.findElement(By.name("email")).sendKeys(email);
    driver.findElement(By.name("phone")).sendKeys(phone);

    driver.findElement(By.name("password")).sendKeys(password);
    driver.findElement(By.name("confirmed_password")).sendKeys(password);
  }

  private void initAccountCreation() {
    driver.findElement(By.cssSelector("#box-account-login a")).click();
  }

  private void goToMainPage() {
    driver.navigate().to("http://localhost/litecart/public_html/en/");
    wait.until(titleIs("Online Store | My Store"));
  }

}
