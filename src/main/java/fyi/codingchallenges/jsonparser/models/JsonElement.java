package fyi.codingchallenges.jsonparser.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class JsonElement implements JsonNode {

    private String data;
    private JsonSymbol symbol;
    private int index;

    public JsonElement(String data, JsonSymbol symbol) {
        this.data = data;
        this.symbol = symbol;
    }

    public JsonElement() {
        super();
    }

    @Override
    public int getDepth() {
        return 0;
    }

    @Override
    public void setDepth(int depth) {}

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public boolean isElement() {
        return true;
    }
}
