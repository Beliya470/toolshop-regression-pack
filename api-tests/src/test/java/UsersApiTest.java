import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UsersApiTest extends BaseApiTest {

    // the password rule is: at least 8 characters. 7 is just under the limit, 8 is exactly on it.
    @Test
    @DisplayName("TF40-100: Registration rejects a password of 7 characters")
    void passwordOf7CharactersIsRejected() {
        RegisterRequest customer = new RegisterRequest("Anne", "Tester", newEmail("p7"), "Ab1!xyz", "1990-05-15");

        given()
            .contentType(ContentType.JSON)
            .body(customer)
        .when()
            .post("/users/register")
        .then()
            .statusCode(422)
            .body("password[0]", equalTo("The password field must be at least 8 characters."));
    }

    @Test
    @DisplayName("TF40-100: Registration accepts a password of 8 characters")
    void passwordOf8CharactersIsAccepted() {
        String newCustomerEmail = newEmail("p8");
        RegisterRequest customer = new RegisterRequest("Anne", "Tester", newCustomerEmail, "Ab1!xyzw", "1990-05-15");

        given()
            .contentType(ContentType.JSON)
            .body(customer)
        .when()
            .post("/users/register")
        .then()
            .statusCode(201)
            .body("email", equalTo(newCustomerEmail))                // the answer has the email I sent
            .body("id", not(emptyOrNullString()))                    // and a new id
            .body("containsKey('password')", is(false));             // but never the password
    }

    // customer A (made in BaseApiTest) reads their own profile with the token
    @Test
    @DisplayName("TF40-101: A logged-in customer reads their own profile with GET /users/me")
    void loggedInCustomerReadsOwnProfile() {
        Response response = given()
            .header("Authorization", "Bearer " + token)
        .when()
            .get("/users/me")
        .then()
            .statusCode(200)
            .extract().response();

        UserProfile profile = response.as(UserProfile.class);     // the answer as a Java object

        assertEquals(email, profile.getEmail());
        assertEquals(userId, profile.getId());                    // the id from the registration, not a hardcoded one
        assertEquals("Anne", profile.getFirstName());
        assertEquals("Tester", profile.getLastName());
        assertFalse(response.jsonPath().getMap("$").containsKey("password"));
    }

    // account boundary: customer A has a token, customer B is another customer. A must not see B.
    @Test
    @DisplayName("TF40-102: Customer A cannot read the profile of customer B")
    void customerACannotReadTheProfileOfCustomerB() {
        String emailOfB = newEmail("b");
        String idOfB = register(emailOfB, PASSWORD);

        String body = given()
            .header("Authorization", "Bearer " + token)             // the token of A
            .pathParam("id", idOfB)
        .when()
            .get("/users/{id}")
        .then()
            .statusCode(404)
            .body("error", equalTo("You are not authorized to view this user."))
            .extract().asString();

        assertFalse(body.contains(emailOfB), "The answer must not show the email of B");
        assertFalse(body.contains(idOfB), "The answer must not show the id of B");
    }
}
