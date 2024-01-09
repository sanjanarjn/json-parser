package fyi.codingchallenges.jsonparser.models;

import java.util.HashMap;

public class JsonObject extends HashMap<String, JsonNode> implements JsonNode {

    @Override
    public boolean isArray() {
        return false;
    }
}
