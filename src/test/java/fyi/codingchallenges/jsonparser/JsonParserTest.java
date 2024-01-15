package fyi.codingchallenges.jsonparser;


import fyi.codingchallenges.jsonparser.core.JsonParser;
import fyi.codingchallenges.jsonparser.exceptions.JsonParseException;
import fyi.codingchallenges.jsonparser.models.JsonArray;
import fyi.codingchallenges.jsonparser.models.JsonElement;
import fyi.codingchallenges.jsonparser.models.JsonNode;
import fyi.codingchallenges.jsonparser.models.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonParserTest {

    private JsonParser parser = new JsonParser();

    @Test
    public void testMultipleJsonFiles() {
        Map<String, String> testJsons = TestUtil.readJsonsFromFolder("/Users/sanjana/Desktop/Work/json-parser/src/test/resources/test");
        Map<String, String> result = new LinkedHashMap<>();

        for (String fileName : testJsons.keySet()) {

            try {
                parser.parse(testJsons.get(fileName));
                result.put(fileName, "passed");
            } catch (Exception e) {
                e.printStackTrace();
                result.put(fileName, "failed");
            }
        }

        for (String fileName : result.keySet()) {
            String status = result.get(fileName);
            if((fileName.startsWith("pass") && !status.startsWith("pass")) || (fileName.startsWith("fail") && !status.startsWith("fail"))) {
                System.out.println(fileName +" ---> Failed");
            }
            else {
                System.out.println(fileName +" ---> Success");

            }
        }
    }
    @Test
    public void testParseEmptyObject() throws JsonParseException {
        JsonNode node = parser.parse("{}");

        assertBasicJsonObjectCases(node);

        JsonObject jsonObject = (JsonObject) node;
        Assertions.assertTrue(jsonObject.keySet().isEmpty());
    }

    @Test
    public void testParseSimpleObject() throws JsonParseException {
        JsonNode node = parser.parse("{\"Comma instead of colon\", null}");



        assertBasicJsonObjectCases(node);

        JsonObject jsonObject = (JsonObject) node;
        Assertions.assertTrue(jsonObject.keySet().size() == 1);
        Assertions.assertTrue(jsonObject.containsKey("key"));
        Assertions.assertTrue(jsonObject.get("key") != null && jsonObject.get("key") instanceof JsonElement);
        Assertions.assertTrue(((JsonElement)jsonObject.get("key")).getData().equals("1") );


    }


    private static void assertBasicJsonObjectCases(JsonNode node) {
        Assertions.assertTrue(node != null);
        Assertions.assertTrue(!node.isArray());
        Assertions.assertTrue(node instanceof JsonObject);
    }

    private static void assertBasicJsonArrayCases(JsonNode node) {
        Assertions.assertTrue(node != null);
        Assertions.assertTrue(node.isArray());
        Assertions.assertTrue(node instanceof JsonArray);
    }

    public static void main(String[] args) {

    }
}
