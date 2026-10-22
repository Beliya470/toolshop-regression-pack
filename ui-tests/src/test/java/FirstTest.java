import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

public class FirstTest {

    private WebDriver driver;

    // a new Chrome window for every test
    @BeforeEach
    void setUp() {
        driver = DriverFactory.createChrome();
    }

    // quit() closes the browser and ends the session. close() would only close the window.
    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void homePageTitleContainsPracticeSoftwareTesting() {
        driver.get("https://practicesoftwaretesting.com");

        String title = driver.getTitle();

        assertTrue(title.contains("Practice Software Testing"), "The title was: " + title);
    }
}
