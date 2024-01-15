package fyi.codingchallenges.jsonparser.models;

import lombok.Data;
import java.util.ArrayList;

@Data
public class JsonArray extends ArrayList<JsonNode> implements JsonNode {

    private int index;
    private int depth;

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public boolean isElement() {
        return false;
    }
}
