package kari.api.registration;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RegistrationApiSteps {

    private static final String PHONE_VERIFY_V2_ENDPOINT = "/ecommerce/client/v2/phone/verify";

    private final RequestSpecification requestSpec;

    public RegistrationApiSteps(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    @Step("Выполнение POST запроса на отправку кода регистрации для номера: '{phone}'")
    public Response sendRegistrationCode(String phone) {
        return given()
                .spec(requestSpec)
                .body(createPhoneBody(phone))
                .when()
                .post(PHONE_VERIFY_V2_ENDPOINT);
    }

    @Step("Выполнение POST запроса на отправку кода регистрации с пустым телом")
    public Response sendRegistrationCodeWithEmptyBody() {
        return given()
                .spec(requestSpec)
                .when()
                .post(PHONE_VERIFY_V2_ENDPOINT);
    }

    @Step("Выполнение POST запроса на подтверждение кода регистрации. Номер: '{phone}', Код: '*****'")
    public Response confirmRegistrationCode(String phone, String code) {
        return given()
                .spec(requestSpec)
                .body(createConfirmBody(phone, code))
                .when()
                .post(PHONE_VERIFY_V2_ENDPOINT);
    }

    private String createPhoneBody(String phone) {
        return String.format("""
                {
                  "phone": "%s"
                }
                """, phone);
    }

    private String createConfirmBody(String phone, String code) {
        return String.format("""
                {
                  "phone": "%s",
                  "confirmCode": "%s"
                }
                """, phone, code);
    }
}
