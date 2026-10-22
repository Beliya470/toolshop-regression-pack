import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

// makes the Chrome driver for every test.
// Normal run: a visible Chrome window. With -Dheadless=true (used on the CI server): Chrome without a window.
public class DriverFactory {

    public static WebDriver createChrome() {
        ChromeOptions options = new ChromeOptions();

        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless");
            options.addArguments("--window-size=1920,1080"); // not in the lecture: my own addition so the headless page has a normal desktop size
        }

        return new ChromeDriver(options);
    }
}
