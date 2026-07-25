package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DataUtils {
    private static final String testDataPath = "src/test/resources/TestData/";
    //TODO: Read data from Json file

    public static String getDataFromJson(String filename, String key) throws FileNotFoundException {
        try {// Implementation to read data from a JSON file
            FileReader fileReader = new FileReader(testDataPath + filename + ".json");
            JsonElement jsonElement = JsonParser.parseReader(fileReader);
            return jsonElement.getAsJsonObject().get(key).getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    //TODO: Read data from Properties file
    public static String getDataFromProperties(String fileName, String key) throws IOException {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(testDataPath + fileName + ".properties"));
            return properties.getProperty(key);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
