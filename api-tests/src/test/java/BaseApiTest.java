import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.BeforeAll;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

// Every API test class extends this class.
// It sets the base URI once, and it makes one new customer for the whole run (customer A).
// The token of customer A is kept and reused by all tests, so no test depends on a seeded or shared account.
public class BaseApiTest {

    // the clean Toolshop API is the default. For the with-bugs API: mvn test -Dbase.uri=https://...
    private static final String DEFAULT_BASE_URI = "https://api.practicesoftwaretesting.com";

    protected static final String PASSWORD = "Str0ng!Pass#2026";

    // customer A: the data of the customer who is registered and logged in for this run
    protected static String email;
    protected static String userId;
    protected static String token;

    @BeforeAll
    static void setUpApiAndCustomerA() {
        RestAssured.baseURI = System.getProperty("base.uri", DEFAULT_BASE_URI);

        // only the first test class of the run does this, the next classes reuse customer A and the token
        if (token == null) {
            email = newEmail("");
            userId = register(email, PASSWORD);
            token = login(email, PASSWORD);
        }
    }

    // a new email for every call. The tag keeps two emails of the same millisecond different.
    protected static String newEmail(String tag) {
        return "fellow+" + System.currentTimeMillis() + tag + "@test.com";
    }

    // registers a customer (must work, status 201) and gives back the id from the response
    protected static String register(String email, String password) {
        RegisterRequest customer = new RegisterRequest("Anne", "Tester", email, password, "1990-05-15");
        return given()
            .contentType(ContentType.JSON)
            .body(customer)
        .when()
            .post("/users/register")
        .then()
            .statusCode(201)
            .extract().path("id");
    }

    protected static String login(String email, String password) {
        return given()
            .contentType(ContentType.JSON)
            .body(new LoginRequest(email, password))
        .when()
            .post("/users/login")
        .then()
            .statusCode(200)
            .extract().path("access_token");
    }
}
