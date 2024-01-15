package fyi.codingchallenges.jsonparser.models;

import lombok.Data;
import java.util.HashMap;

@Data
public class JsonObject extends HashMap<String, JsonNode> implements JsonNode {

    private int index;
    private int depth;

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public boolean isElement() {
        return false;
    }
}
