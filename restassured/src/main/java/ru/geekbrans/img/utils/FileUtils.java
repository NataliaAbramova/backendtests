package ru.geekbrans.img.utils;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;

@UtilityClass
public class FileUtils {

    public byte[] getFileContent(String filePath) {
        File inputFile = new File(filePath);
        byte[] fileContent = new byte[0];
        try {
            fileContent = org.apache.commons.io.FileUtils.readFileToByteArray(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

}
