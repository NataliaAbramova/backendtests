package ru.geekbrans.updimg;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.geekbrans.BaseTest;

import java.util.Base64;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UpdateImageTest extends BaseTest {

    @Test
    void createUpdateGetDeleteTest() {
        //загружаем картинку
        Map data = given()
                .headers("Authorization", token)
                .multiPart("image", Base64.getEncoder().encodeToString(getFileContentInBase64("bfoto_ru_3269.jpg")))
                .expect()
                .body("success", is(true))
                .body("data.id", is(notNullValue()))
                .body("data.title", is(nullValue()))
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getMap("data");
        String deleteHash = (String) data.get("deletehash");
        String id = (String) data.get("id");

        //обновляем картинку
        given()
                .headers(headers)
                .param("title", "100500")
                .expect()
                .body("success", is(true))
                .when()
                .post("/image/{id}", id)
                .prettyPeek()
                .then()
                .statusCode(200);

        //проверяем, что поле обновилось
        given()
                .headers(headers)
                .expect()
                .body("data.title", is("100500"))
                .when()
                .get("image/{id}", id)
                .then()
                .statusCode(200);

        //удаляем картинку
        given()
                .headers("Authorization", token)
                .when()
                .delete("image/{deleteHash}", deleteHash)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
