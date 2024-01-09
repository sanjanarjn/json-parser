package fyi.codingchallenges.jsonparser;


import fyi.codingchallenges.jsonparser.core.JsonParser;
import fyi.codingchallenges.jsonparser.exceptions.JsonParseException;
import fyi.codingchallenges.jsonparser.models.JsonArray;
import fyi.codingchallenges.jsonparser.models.JsonElement;
import fyi.codingchallenges.jsonparser.models.JsonNode;
import fyi.codingchallenges.jsonparser.models.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JsonParserTest {

    private JsonParser parser = new JsonParser();

    @Test
    public void testParseEmptyObject() throws JsonParseException {
        JsonNode node = parser.parse("{}");

        assertBasicJsonObjectCases(node);

        JsonObject jsonObject = (JsonObject) node;
        Assertions.assertTrue(jsonObject.keySet().isEmpty());
    }

    @Test
    public void testParseSimpleObject() throws JsonParseException {
        JsonNode node = parser.parse("{\"key\":\"value\"}");

        assertBasicJsonObjectCases(node);

        JsonObject jsonObject = (JsonObject) node;
        Assertions.assertTrue(jsonObject.keySet().size() == 1);
        Assertions.assertTrue(jsonObject.containsKey("key"));
        Assertions.assertTrue(jsonObject.get("key") != null && jsonObject.get("key") instanceof JsonElement);
        Assertions.assertTrue(((JsonElement)jsonObject.get("key")).getData().equals("value") );


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
}
