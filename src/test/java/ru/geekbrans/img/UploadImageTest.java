package ru.geekbrans.img;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.geekbrans.img.dto.ErrorResponse;
import ru.geekbrans.img.dto.PostImageResponse;
import ru.geekbrans.img.step.CommonPostRequest;
import ru.geekbrans.img.utils.FileUtils;

import java.util.Base64;

import static io.restassured.RestAssured.given;

public class UploadImageTest extends BaseTest {

    static String uploadedImageHashCode;

    @Test
    void uploadFileTest() {
        preparePostSpecs(FileUtils.getFileContent(Images.POSITIVE.path));
        PostImageResponse response = CommonPostRequest.uploadCommonImage(uploadReqSpec);
        uploadedImageHashCode = response.getData().getDeletehash();
        Assertions.assertNotNull(response.getData().getId());
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(response.getData().getType(), imgType);
        Assertions.assertEquals(response.getData().getWidth(), Images.POSITIVE.width);
        Assertions.assertEquals(response.getData().getHeight(), Images.POSITIVE.height);
    }

    @Test
    void base64UploadFileTest() {
        preparePostSpecs(Base64.getEncoder().encodeToString(FileUtils.getFileContent(Images.POSITIVE.path)));
        PostImageResponse response = CommonPostRequest.uploadCommonImage(uploadReqSpec);
        uploadedImageHashCode = response.getData().getDeletehash();
        Assertions.assertNotNull(response.getData().getId());
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(response.getData().getType(), imgType);
        Assertions.assertEquals(response.getData().getWidth(), Images.POSITIVE.width);
        Assertions.assertEquals(response.getData().getHeight(), Images.POSITIVE.height);
    }

    @Test
    void uploadFileFromURLTest() {
        preparePostSpecs(Images.FROM_URL.path);
        PostImageResponse response = CommonPostRequest.uploadCommonImage(uploadReqSpec);
        uploadedImageHashCode = response.getData().getDeletehash();
        Assertions.assertNotNull(response.getData().getId());
        Assertions.assertTrue(response.getSuccess());
        Assertions.assertEquals(response.getData().getType(), imgType);
        Assertions.assertEquals(response.getData().getWidth(), Images.FROM_URL.width);
        Assertions.assertEquals(response.getData().getHeight(), Images.FROM_URL.height);
    }

    @Test
    void uploadLargeFile() {
        preparePostSpecs(FileUtils.getFileContent(Images.TO_BIG.path));
        ErrorResponse response = given()
                .spec(uploadReqSpec)
                .when()
                .post(Endpoints.POST_IMAGE_REQUEST)
                .prettyPeek()
                .then()
                .spec(wrongRespSpek)
                .extract()
                .body()
                .as(ErrorResponse.class);
        Assertions.assertFalse(response.getSuccess());
    }

    @AfterAll
    static void tearDown() {
        if (uploadedImageHashCode != null) {
        given()
                .spec(baseRequestSpecification)
                .when()
                .delete(Endpoints.DELETE_IMAGE_REQUEST, uploadedImageHashCode)
                .prettyPeek()
                .then()
                .spec(successRespSpek);
        }
    }

}
