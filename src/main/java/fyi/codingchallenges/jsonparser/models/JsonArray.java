package fyi.codingchallenges.jsonparser.models;

import java.util.ArrayList;

public class JsonArray extends ArrayList<JsonNode> implements JsonNode {

    @Override
    public boolean isArray() {
        return true;
    }
}
