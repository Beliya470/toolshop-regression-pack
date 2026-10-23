import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

public class LoginTest {

    // the API is only used to make a new customer. For the with-bugs API: mvn test -Dbase.uri=https://...
    private static final String REGISTER_URL = System.getProperty("base.uri", "https://api.practicesoftwaretesting.com") + "/users/register";

    // my own user, made fresh for each run (the shop data is shared and resets)
    private static final String PASSWORD = "Str0ng!Pass#2026";
    private static String email;

    private WebDriver driver;
    private LoginPage loginPage;

    // register a new user through the API, so the valid login test does not depend on a shared account
    @BeforeAll
    static void registerUser() throws Exception {
        email = "fellow+" + System.currentTimeMillis() + "@test.com";
        String body = "{\"first_name\":\"Anne\",\"last_name\":\"Tester\",\"email\":\"" + email
                + "\",\"password\":\"" + PASSWORD + "\",\"dob\":\"1990-05-15\"";
        // the with-bugs API wants these three as plain text (-Dregister.flat=true), the normal one does not
        if (Boolean.getBoolean("register.flat")) {
            body += ",\"address\":\"1 Main Street\",\"city\":\"Nairobi\",\"country\":\"KE\"";
        }
        body += "}";

        HttpRequest request = HttpRequest.newBuilder(URI.create(REGISTER_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        assertTrue(response.statusCode() == 201, "Register gave " + response.statusCode() + ": " + response.body());
    }

    @BeforeEach
    void setUp() {
        driver = DriverFactory.createChrome();
        loginPage = new LoginPage(driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("TF40-103: Valid login with a registered user lands on the account page")
    void validLoginLandsOnAccountPage() {
        loginPage.open();
        loginPage.loginAs(email, PASSWORD);

        String title = new AccountPage(driver).getTitleText();
        assertTrue(title.contains("My account"), "The title was: " + title);
    }

    @Test
    @DisplayName("TF40-104: Login with an unknown email shows an error message")
    void loginWithUnknownEmailShowsError() {
        loginPage.open();
        loginPage.loginAs("nobody.unknown@example.com", "wrong-password-1");

        String error = loginPage.getErrorText();
        assertTrue(error.contains("Invalid email or password"), "The error was: " + error);
    }
}
