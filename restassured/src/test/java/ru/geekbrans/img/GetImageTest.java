package ru.geekbrans.img;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.geekbrans.img.dto.ErrorResponse;
import ru.geekbrans.img.dto.GetImageResponse;

import static io.restassured.RestAssured.given;

public class GetImageTest extends BaseTest {

    private final int width = 529;
    private final int height = 300;

    @Test
    void getImageTest() {
        GetImageResponse response = given()
                        .spec(baseRequestSpecification)
                        .when()
                        .get(Endpoints.GET_IMAGE_REQUEST, imgId)
                        .prettyPeek()
                        .then()
                        .spec(successRespSpek)
                        .extract()
                        .response()
                        .body()
                        .as(GetImageResponse.class);
        Assertions.assertEquals(response.getData().getType(), imgType);
        Assertions.assertEquals(response.getData().getWidth(), width);
        Assertions.assertEquals(response.getData().getHeight(), height);
    }

    @Test
    void brokenEndpointTest() {
        ErrorResponse response = given()
                            .spec(baseRequestSpecification)
                            .when()
                            .get(Endpoints.POST_IMAGE_REQUEST)
                            .prettyPeek()
                            .then()
                            .spec(wrongRespSpek)
                            .extract()
                            .response()
                            .body()
                            .as(ErrorResponse.class);
        Assertions.assertEquals(response.getSuccess(), false);
    }

    @Test
    void notFoundTest() {
        given()
            .spec(baseRequestSpecification)
            .when()
            .get(Endpoints.GET_IMAGE_REQUEST, wrongImgId)
            .prettyPeek()
            .then()
            .spec(notFoundRespSpek);
    }

    @Test
    void getImageTestNoAuth() {
        ErrorResponse response = given()
                .when()
                .get(Endpoints.GET_IMAGE_REQUEST, imgId)
                .prettyPeek()
                .then()
                .spec(notAuthRespSpek)
                .extract()
                .response()
                .body()
                .as(ErrorResponse.class);
        Assertions.assertEquals(response.getSuccess(), false);
    }

}
