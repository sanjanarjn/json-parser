package fyi.codingchallenges.jsonparser;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class TestUtil {

    public static Map<String, String> readJsonsFromFolder(String testFolder) {
        String folderPath = testFolder;

        Map<String, String>  fileContents = new TreeMap<>();
        ClassLoader classLoader = TestUtil.class.getClassLoader();
        try {
            URL resource = classLoader.getResource(folderPath);
            File folder  = new File(testFolder);
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    String content = new String(Files.readAllBytes(Path.of(file.getPath())));
                    fileContents.put(file.getName(), content);
                }
            } else {
                System.err.println("Resource folder not found: " + folderPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContents;
    }
}
