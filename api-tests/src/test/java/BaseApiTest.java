import org.junit.jupiter.api.BeforeAll;

import io.restassured.RestAssured;

public class BaseApiTest {

    // the clean Toolshop API is the default. To use another one: mvn test -Dbase.uri=https://...
    private static final String DEFAULT_BASE_URI = "https://api.practicesoftwaretesting.com";

    @BeforeAll
    static void setBaseUri() {
        RestAssured.baseURI = System.getProperty("base.uri", DEFAULT_BASE_URI);
    }
}
