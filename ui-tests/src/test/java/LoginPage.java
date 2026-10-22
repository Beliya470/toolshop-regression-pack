import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// The login page of the Toolshop. It holds the locators and the actions of this page.
// The tests only say what to check, so if the page changes I fix this one class.
public class LoginPage {

    // the clean Toolshop is the default. For the with-bugs UI: mvn test -Dui.url=https://...
    private static final String URL = System.getProperty("ui.url", "https://practicesoftwaretesting.com") + "/auth/login";

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailField = By.cssSelector("[data-test='email']");
    private final By passwordField = By.cssSelector("[data-test='password']");
    private final By loginButton = By.cssSelector("[data-test='login-submit']");
    private final By loginError = By.cssSelector("[data-test='login-error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // opens the page and waits until the email field can be used
    public void open() {
        driver.get(URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
    }

    public void loginAs(String email, String password) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    // waits until the error is shown, then gives its text
    public String getErrorText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loginError)).getText();
    }
}
