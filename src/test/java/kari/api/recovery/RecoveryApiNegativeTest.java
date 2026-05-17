package kari.api.recovery;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RecoveryApiNegativeTest {

    private static final String BASE_URL = "https://i.api.kari.com";
    private static final String RECOVERY_ENDPOINT = "/ecommerce/client/info";

    @Test
    public void recoveryWithoutAuthenticationShouldReturn401() {
        given()
                .log().all()
                .baseUri(BASE_URL)
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("origin", "https://kari.com")
                .header("referer", "https://kari.com/")
                .body("")
                .when()
                .post(RECOVERY_ENDPOINT)
                .then()
                .log().all()
                .statusCode(401)
                .body("statusCode", equalTo(401))
                .body("error", equalTo("Unauthorized"))
                .body("message", equalTo("Missing authentication"));
    }

    @Test
    public void recoveryWithPhoneWithoutAuthenticationShouldReturn401() {
        given()
                .log().all()
                .baseUri(BASE_URL)
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("origin", "https://kari.com")
                .header("referer", "https://kari.com/")
                .body("""
                        {
                          "phone": "79999999999"
                        }
                        """)
                .when()
                .post(RECOVERY_ENDPOINT)
                .then()
                .log().all()
                .statusCode(401)
                .body("statusCode", equalTo(401))
                .body("error", equalTo("Unauthorized"))
                .body("message", equalTo("Missing authentication"));
    }
}
