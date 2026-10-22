import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BrandsApiTest extends BaseApiTest {

    // GET /brands gives a plain list (a JSON array), not an object with a "data" list
    @Test
    @DisplayName("TF40-97: GET /brands returns a non-empty list and every brand has an id and a name")
    void brandsListIsNotEmptyAndEveryBrandHasIdAndName() {
        given()
        .when()
            .get("/brands")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0))                       // the list is not empty
            .body("id", everyItem(not(emptyOrNullString())))      // every brand has an id
            .body("name", everyItem(not(emptyOrNullString())));   // and a name
    }
}
