package ru.geekbrans.getimg;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.geekbrans.BaseTest;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetImageTest extends BaseTest {

    @Test
    void getImageTest() {
        Map data = given()
                        .headers(headers)
                        .when()
                        .get("image/{id}", imgId)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response()
                        .jsonPath().getMap("data");
        Assertions.assertEquals(data.get("type"), "image/jpeg");
        Assertions.assertEquals(data.get("width"), 529);
        Assertions.assertEquals(data.get("height"), 300);
    }

    @Test
    void brokenEndpointTest() {
        Map data = given()
                .headers(headers)
                .when()
                .get("image")
                .prettyPeek()
                .then()
                .statusCode(400)
                .extract()
                .response()
                .jsonPath().getMap("");
        Assertions.assertEquals(data.get("success"), false);
    }

    @Test
    void notFoundTest() {
        given()
                .headers(headers)
                .when()
                .get("image/akfdsnpajfdhgpadsf834hdf")
                .prettyPeek()
                .then()
                .statusCode(404);
    }

    @Test
    void getImageTestNoAuth() {
        Map data = given()
                .when()
                .get("image/{id}", imgId)
                .prettyPeek()
                .then()
                .statusCode(401)
                .extract()
                .response()
                .jsonPath().getMap("");
        Assertions.assertEquals(data.get("success"), false);
    }

}
