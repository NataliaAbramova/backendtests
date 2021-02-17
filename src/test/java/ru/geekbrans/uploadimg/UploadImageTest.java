package ru.geekbrans.uploadimg;

import org.junit.jupiter.api.*;
import ru.geekbrans.BaseTest;

import java.util.Base64;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UploadImageTest extends BaseTest {

    String encodedImage;
    static String uploadedImageHashCode;
    byte[] fileContent;

    @BeforeEach
    void setUp() {
        fileContent = getFileContentInBase64("bfoto_ru_3269.jpg");
        encodedImage = Base64.getEncoder().encodeToString(fileContent);
    }

    @Test
    void base64UploadFileTest() {
         Map data = given()
                .headers("Authorization", token)
                .multiPart("image", encodedImage)
                .expect()
                .body("success", is(true))
                .body("data.id", is(notNullValue()))
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getMap("data");
        uploadedImageHashCode = (String) data.get("deletehash");
        Assertions.assertEquals(data.get("type"), "image/jpeg");
        Assertions.assertEquals(data.get("width"), 820);
        Assertions.assertEquals(data.get("height"), 579);
    }

    @Test
    void uploadFileFromURLTest() {
        Map data = given()
                .headers("Authorization", token)
                .multiPart("image", "https://upload.wikimedia.org/wikipedia/commons/8/80/140-P1020281_-_Flickr_-_Laurie_Nature_Bee.jpg")
                .expect()
                .body("success", is(true))
                .body("data.id", is(notNullValue()))
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getMap("data");
        uploadedImageHashCode = (String) data.get("deletehash");
        Assertions.assertEquals(data.get("type"), "image/jpeg");
        Assertions.assertEquals(data.get("width"), 3504);
        Assertions.assertEquals(data.get("height"), 2336);
    }

    @Test
    void uploadLargeFile() {
        Map data = given()
                .headers("Authorization", token)
                .multiPart("image", Base64.getEncoder().encodeToString(getFileContentInBase64("DSC_0557.JPG")))
                .expect()
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .statusCode(400)
                .extract()
                .response()
                .jsonPath().getMap("");
        Assertions.assertEquals(data.get("success"), false);
    }

    @AfterAll
    static void tearDown() {
        if (uploadedImageHashCode != null) {
        given()
                .headers("Authorization", token)
                .when()
                .delete("image/{deleteHash}", uploadedImageHashCode)
                .prettyPeek()
                .then()
                .statusCode(200);
        }
    }

}
