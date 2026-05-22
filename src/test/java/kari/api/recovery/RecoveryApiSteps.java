package kari.api.recovery;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RecoveryApiSteps {

    private static final String RECOVERY_ENDPOINT = "/ecommerce/client/info";

    private final RequestSpecification requestSpec;

    public RecoveryApiSteps(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response recoveryWithoutAuthentication() {
        return given()
                .spec(requestSpec)
                .body("")
                .when()
                .post(RECOVERY_ENDPOINT);
    }

    public void assertMissingAuthenticationResponse(Response response) {
        response.then()
                .log().ifValidationFails()
                .statusCode(401)
                .body("statusCode", equalTo(401))
                .body("error", equalTo("Unauthorized"))
                .body("message", equalTo("Missing authentication"));
    }
}
