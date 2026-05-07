package fixprice.api;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class LoginApiTest {
    @Test
    public void loginWithEmptyPhoneShouldReturn400() {
        given()
                .log().all()
                .baseUri("https://api.fix-price.com")
                .header("Accept", "application/json, text/plain, */*")
                .header("Content-Type", "application/json")
                .header("Origin", "https://fix-price.com")
                .header("Referer", "https://fix-price.com/")
                .header("X-City", "3")
                .header("X-Client-Route", "/")
                .header("X-Key", "T1E3b3lIQ0MrS3ZtNjJCdTVSRWJxUT09Ojp4dEk0bFFUSG1LRU9RVmo4ZnFvNnhRPT0=:8091cbe1f4432ff3ce691506378e4f99")
                .header("X-Language", "ru")
                .body("""
                    {
                      "phone": "",
                      "password": "123344"
                    }
                    """)
                .when()
                .post("/buyer/v2/auth/login/")
                .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    public void loginWithEmpyPasswordShouldReturn400(){
        given()
                .log().all()
                .redirects().follow(true)
                .baseUri("https://api.fix-price.com")
                .header("accept", "application/json, text/plain, */*")
                .header("accept-language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("content-type", "application/json")
                .header("origin", "https://fix-price.com")
                .header("referer", "https://fix-price.com/")
                .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36")
                .header("sec-ch-ua", "\"Google Chrome\";v=\"147\", \"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"147\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"Windows\"")
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "same-site")
                .header("x-city", "25")
                .header("x-client-route", "/")
                .header("x-key", "T1E3b3lIQ0MrS3ZtNjJCdTVSRWJxUT09Ojp4dEk0bFFUSG1LRU9RVmo4ZnFvNnhRPT0=:8091cbe1f4432ff3ce691506378e4f99")
                .header("x-language", "ru")
                .header("x-logged", "")
                .header("cookie", "spid=1778188282509_9b2b9b4d43e33f3cf68622b104f37ab8_n1iv6j144s2hfr9c; spsc=1778188282509_0e480bb044deb09bad3b67022bc58c4e_C2wzs6YmCD6ziXWq35fpJtvJHw8fnVXkNyBgqWtbUoUZ")
                .body("""
                {
                  "phone": "",
                  "password": "123344"
                }
                """)
                .when()
                .post("/buyer/v2/auth/login")
                .then()
                .log().all()
                .statusCode(400);
    }

    //Через UI/Postman endpoint возвращает 400.
    //Через Rest Assured без полноценного browser session API возвращает 307.
    //UI → 400
    //Postman → 400
    //Rest Assured → 307

}
