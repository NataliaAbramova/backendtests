package ru.geekbrans.img;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public abstract class BaseTest {
    protected static Properties prop = new Properties();
    protected static String token;
    protected static String username;
    protected static Map<String, String> headers = new HashMap<>();
    protected static String imgId;
    protected static ResponseSpecification successRespSpek;
    protected static ResponseSpecification notAuthRespSpek;
    protected static ResponseSpecification notFoundRespSpek;
    protected static ResponseSpecification wrongRespSpek;
    protected static String wrongImgId = "ad5t43tmjdsxfgnajk";
    protected static RequestSpecification baseRequestSpecification;
    protected static final String imgType = "image/jpeg";

    @BeforeAll
    static void beforeAll() {
        loadProperties();
        token = prop.getProperty("token");
        headers.put("Authorization", token);
        RestAssured.baseURI = prop.getProperty("base.url");
        username = prop.getProperty("username");
        imgId = prop.getProperty("imgId");
        successRespSpek = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .expectHeader("Access-Control-Allow-Credentials", "true")
                .build();
        notAuthRespSpek = new ResponseSpecBuilder()
                .expectStatusCode(401)
                .expectResponseTime(Matchers.lessThan(5000L))
                .expectHeader("Access-Control-Allow-Credentials", "true")
                .build();
        notFoundRespSpek = new ResponseSpecBuilder()
                .expectStatusCode(404)
                .expectResponseTime(Matchers.lessThan(5000L))
                .expectHeader("Access-Control-Allow-Credentials", "true")
                .build();
        wrongRespSpek = new ResponseSpecBuilder()
                .expectStatusCode(400)
                .expectResponseTime(Matchers.lessThan(5000L))
                .expectHeader("Access-Control-Allow-Credentials", "true")
                .build();
        baseRequestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .setAccept(ContentType.ANY)
                .build();
    }


    private static void loadProperties() {
        try {
            prop.load(new FileInputStream("src/test/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected byte[] getFileContentInBase64(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        byte[] fileContent = new byte[0];
        try {
            fileContent =   FileUtils.readFileToByteArray(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

}
