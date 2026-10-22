import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.nullValue;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ProductsApiTest extends BaseApiTest {

    // The same check runs for 4 search terms: the term in lower case, in upper case, a part of a word, and a second product.
    // The search gives a page object, and the products are in its "data" list.
    @ParameterizedTest(name = "search term: {0}")
    @ValueSource(strings = {"pliers", "PLIERS", "Pli", "hammer"})
    @DisplayName("TF40-98: Product search returns only products whose name contains the term, ignoring case and accepting part of a word")
    void searchReturnsOnlyProductsWhoseNameContainsTheTerm(String term) {
        given()
            .queryParam("q", term)
        .when()
            .get("/products/search")
        .then()
            .statusCode(200)
            .body("data.size()", greaterThan(0))                                 // something was found
            .body("data.name", everyItem(containsStringIgnoringCase(term)));     // and every name contains the term
    }

    // A random UUID can never be a real product id, so nothing is hardcoded.
    @Test
    @DisplayName("TF40-99: GET /products/{id} with an unknown id returns 404 and no product data")
    void unknownProductIdReturns404AndNoProductData() {
        String unknownId = UUID.randomUUID().toString();

        given()
            .pathParam("id", unknownId)
        .when()
            .get("/products/{id}")
        .then()
            .statusCode(404)
            .body("message", equalTo("Requested item not found"))
            .body("name", nullValue());                                          // no product name in the answer
    }
}
