package ru.geekbrans;

import io.restassured.RestAssured;
import org.apache.commons.io.FileUtils;
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

    @BeforeAll
    static void beforeAll() {
        loadProperties();
        token = prop.getProperty("token");
        headers.put("Authorization", token);
        RestAssured.baseURI = prop.getProperty("base.url");
        username = prop.getProperty("username");
        imgId = prop.getProperty("imgId");
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
