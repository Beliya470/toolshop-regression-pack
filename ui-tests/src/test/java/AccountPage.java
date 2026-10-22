import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// The account page that opens after a good login
public class AccountPage {

    private final WebDriverWait wait;

    private final By pageTitle = By.cssSelector("[data-test='page-title']");

    public AccountPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // waits until the title is shown, then gives its text
    public String getTitleText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle)).getText();
    }
}
