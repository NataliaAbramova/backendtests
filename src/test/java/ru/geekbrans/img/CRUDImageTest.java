package ru.geekbrans.img;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.geekbrans.img.dto.GetImageResponse;
import ru.geekbrans.img.dto.PostImageResponse;
import ru.geekbrans.img.step.CommonPostRequest;
import ru.geekbrans.img.utils.FileUtils;

import java.util.Base64;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CRUDImageTest extends BaseTest {

    private final String field = "title";
    private final String value = faker.demographic().demonym();

    @Test
    void createUpdateGetDeleteTest() {
        preparePostSpecs(FileUtils.getFileContent(Images.POSITIVE.path));
        //загружаем картинку
        PostImageResponse response = CommonPostRequest.uploadCommonImage(uploadReqSpec);
        String deleteHash = response.getData().getDeletehash();
        String id = response.getData().getId();

        //обновляем картинку
        given()
                .spec(baseRequestSpecification)
                .param(field, value)
                .expect()
                .when()
                .post(Endpoints.PUT_IMAGE_REQUEST, id)
                .prettyPeek()
                .then()
                .spec(successRespSpek);

        //проверяем, что поле обновилось
        GetImageResponse getImageResponse = given()
                .spec(baseRequestSpecification)
                .expect()
                .when()
                .get(Endpoints.GET_IMAGE_REQUEST, id)
                .prettyPeek()
                .then()
                .spec(successRespSpek)
                .extract()
                .body()
                .as(GetImageResponse.class);
        Assertions.assertEquals(getImageResponse.getData().getTitle(), value);

        //удаляем картинку
        given()
                .spec(baseRequestSpecification)
                .when()
                .delete(Endpoints.DELETE_IMAGE_REQUEST, deleteHash)
                .prettyPeek()
                .then()
                .spec(successRespSpek);
    }
}
