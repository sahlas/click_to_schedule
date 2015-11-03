package tests;

/**
 * Created by billsahlas on 10/23/15.
 */
public interface Config {
    final String baseUrl = System.getProperty("baseUrl", "https://inthel.timetradesystems.com/app/devauto/workflows/BLAZE001");
    final String browser = System.getProperty("browser", "firefox");
    final String host = System.getProperty("host", "localhost");
    final String browserVersion = System.getProperty("browserVersion", "33");
    final String platform = System.getProperty("platform", "Windows XP");
    final String sauceUser = System.getenv("SAUCE_USERNAME");
    final String sauceKey = System.getenv("SAUCE_ACCESS_KEY");
    final String firstName = "Bill";
    final String lastName = "Sahlas";
    final String email = firstName + "." + lastName + "@test.com";

}
