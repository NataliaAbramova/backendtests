package ru.geekbrans.img;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrans.img.dto.GetImageResponse;
import ru.geekbrans.img.dto.PostImageResponse;
import ru.geekbrans.img.step.CommonPostRequest;
import ru.geekbrans.img.utils.FileUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UpdateImageTest extends BaseTest {

    private Map paramsUpd = new HashMap();
    private String imgId;
    private String imgDeleteHash;
    private final List<String> pUpdName = Arrays.asList("title", "description", "type");

    @BeforeEach
    void setUp() {
        preparePostSpecs(FileUtils.getFileContent(Images.POSITIVE.path));
        //загружаем картинку
        PostImageResponse response = CommonPostRequest.uploadCommonImage(uploadReqSpec);
        imgDeleteHash = response.getData().getDeletehash();
        imgId = response.getData().getId();
        paramsUpd.put(pUpdName.get(0), faker.demographic().demonym());
        paramsUpd.put(pUpdName.get(1), faker.rickAndMorty().character());
        paramsUpd.put(pUpdName.get(2), faker.ancient().god());
    }

    @Test
    void updateImgTest() {
        given()
                .spec(baseRequestSpecification)
                .params(paramsUpd)
                .expect()
                .when()
                .post(Endpoints.PUT_IMAGE_REQUEST, imgId)
                .prettyPeek()
                .then()
                .spec(successRespSpek);
        GetImageResponse getImageResponse = given()
                .spec(baseRequestSpecification)
                .expect()
                .when()
                .get(Endpoints.GET_IMAGE_REQUEST, imgId)
                .prettyPeek()
                .then()
                .spec(successRespSpek)
                .extract()
                .body()
                .as(GetImageResponse.class);
        Assertions.assertEquals(getImageResponse.getData().getTitle(), paramsUpd.get(pUpdName.get(0)));
        Assertions.assertEquals(getImageResponse.getData().getDescription(), paramsUpd.get(pUpdName.get(1)));
        Assertions.assertEquals(getImageResponse.getData().getType(), imgType);
    }

    @AfterEach
    void tearDown() {
        given()
                .spec(baseRequestSpecification)
                .when()
                .delete(Endpoints.DELETE_IMAGE_REQUEST, imgDeleteHash)
                .prettyPeek()
                .then()
                .spec(successRespSpek);
    }

}
