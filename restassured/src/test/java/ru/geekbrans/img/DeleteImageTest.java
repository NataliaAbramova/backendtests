package ru.geekbrans.img;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrans.img.dto.CommonResponse;
import ru.geekbrans.img.dto.PostImageResponse;
import ru.geekbrans.img.step.CommonPostRequest;
import ru.geekbrans.img.utils.FileUtils;

import static io.restassured.RestAssured.given;

public class DeleteImageTest extends BaseTest {

    private String imgDeleteHash;

    @BeforeEach
    void setUp() {
        preparePostSpecs(FileUtils.getFileContent(Images.POSITIVE.path));
        //загружаем картинку
        PostImageResponse response = CommonPostRequest.uploadCommonImage(uploadReqSpec);
        imgDeleteHash = response.getData().getDeletehash();
        imgId = response.getData().getId();
    }

    @Test
    public void deleteAuthTest(){
        CommonResponse response = given()
                .spec(baseRequestSpecification)
                .when()
                .delete(Endpoints.DELETE_IMAGE_REQUEST, imgDeleteHash)
                .prettyPeek()
                .then()
                .spec(successRespSpek)
                .extract()
                .body()
                .as(CommonResponse.class);
        Assertions.assertTrue(response.getSuccess());
    }

    @Test
    public void deleteNoAuthTest(){
        given()
                .when()
                .delete(Endpoints.DELETE_IMAGE_REQUEST, imgDeleteHash)
                .prettyPeek()
                .then()
                .spec(notAuthRespSpek);
    }

}
