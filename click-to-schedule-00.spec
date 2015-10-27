package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class ClickToSchedule00 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://inthel.timetradesystems.com/app/devauto/workflows/BLAZE001/schedule/?locationId=westford";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testClickToSchedule00() throws Exception {
    driver.get(baseUrl + "/app/devauto/workflows/BLAZE001/schedule/appointment-type?wfsid=b0da17d3-baba97f6-b0da17dc-baba97f6-00000002-ohrsgljk3cc3timai0up6vsqaak6068m&locationId=westford&fs=1");
    driver.findElement(By.cssSelector("#tt_form_ChoiceButton_2 > table > tbody > tr > td.labelContainer > label > div.labelNode")).click();
    driver.findElement(By.id("computersoffice")).click();
    driver.findElement(By.id("nextBtn")).click();
    new Select(driver.findElement(By.id("tt_form_ChoiceSelect_0"))).selectByVisibleText("4:00 PM");
    driver.findElement(By.cssSelector("option[value=\"2015-10-27T18:00:00.000Z\"]")).click();
    driver.findElement(By.id("nextBtn")).click();
    driver.findElement(By.name("attendee_person_firstName")).clear();
    driver.findElement(By.name("attendee_person_firstName")).sendKeys("Bill");
    driver.findElement(By.name("attendee_person_lastName")).clear();
    driver.findElement(By.name("attendee_person_lastName")).sendKeys("Sahlas");
    driver.findElement(By.id("attendee_email")).clear();
    driver.findElement(By.id("attendee_email")).sendKeys("bill.sahlas@gmail.com");
    driver.findElement(By.id("nextBtn")).click();
    driver.findElement(By.id("nextBtn")).click();
    driver.findElement(By.cssSelector("input.button.primary")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
