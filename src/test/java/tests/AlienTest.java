package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AlienTest {
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
  public void testAlien() throws Exception {
    driver.get(baseUrl + "/app/devauto/workflows/BLAZE001/schedule/appointment-type?wfsid=b0da17dc-baba97f6-b0da17d2-baba97f6-00000002-5iqk9h6hjsa2hr6k154hdrndl76h94tg&locationId=westford&fs=1");
    driver.findElement(By.cssSelector("td.labelContainer")).click();
    driver.findElement(By.id("nextBtn")).click();
    new Select(driver.findElement(By.id("tt_form_ChoiceSelect_0"))).selectByVisibleText("8:00 PM");
    driver.findElement(By.cssSelector("option[value=\"2015-10-28T00:00:00.000Z\"]")).click();
    driver.findElement(By.id("nextBtn")).click();
    driver.findElement(By.name("attendee_person_firstName")).clear();
    driver.findElement(By.name("attendee_person_firstName")).sendKeys("Bill");
    driver.findElement(By.name("attendee_person_lastName")).clear();
    driver.findElement(By.name("attendee_person_lastName")).sendKeys("Sahlas");
    driver.findElement(By.id("attendee_email")).clear();
    driver.findElement(By.id("attendee_email")).sendKeys("bill.sahlas@gmail.com");
    new Select(driver.findElement(By.id("questionId__current"))).selectByVisibleText("Yes");
    driver.findElement(By.id("nextBtn")).click();
    driver.findElement(By.id("nextBtn")).click();
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
