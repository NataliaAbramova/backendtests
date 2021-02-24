package ru.geekbrans.img.step;


import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import ru.geekbrans.img.Endpoints;
import ru.geekbrans.img.dto.PostImageResponse;

import static io.restassured.RestAssured.given;

@UtilityClass
public class CommonPostRequest {

    public static PostImageResponse uploadCommonImage(RequestSpecification spec) {
        return given()
                .spec(spec)
                .when()
                .post(Endpoints.POST_IMAGE_REQUEST)
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PostImageResponse.class);
    }
}
