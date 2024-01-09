package fyi.codingchallenges.jsonparser.models;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JsonElement implements JsonNode {

    private String data;
    private JsonSymbol symbol;

    public JsonElement() {
        super();
    }

    @Override
    public boolean isArray() {
        return false;
    }
}
