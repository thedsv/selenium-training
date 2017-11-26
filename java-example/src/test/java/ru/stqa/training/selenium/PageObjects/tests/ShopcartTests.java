package ru.stqa.training.selenium.PageObjects.tests;

import org.junit.Test;

public class ShopcartTests extends TestBase {

  @Test
  public void shopcartTest() {
    app.goToMainPage();
    app.addProductsToShopcart(3);
    app.openShopcart();
    app.removeAllProducts();
  }
}
