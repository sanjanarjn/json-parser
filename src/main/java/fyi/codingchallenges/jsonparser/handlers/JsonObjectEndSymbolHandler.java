package fyi.codingchallenges.jsonparser.handlers;

import fyi.codingchallenges.jsonparser.exceptions.JsonParseException;
import fyi.codingchallenges.jsonparser.models.*;

import java.text.MessageFormat;
import java.util.Stack;

public class JsonObjectEndSymbolHandler implements JsonSymbolHandler {

    @Override
    public void validateParseState(ParseState parseState, String token) throws JsonParseException {

        if (parseState.emptyParseState()) {
            throw new JsonParseException(MessageFormat.format("Unexpected character {0} at index {1}", token, parseState.getCurrentIndex()));
        }
        if (!parseState.isPreviousNodeJsonSymbol(JsonSymbol.JSON_OBJECT_START)) {
            Stack<JsonNode> nodeStack = parseState.getNodeStack();
            if (nodeStack.size() < 4)
                throw new JsonParseException(MessageFormat.format("Unexpected character {0} at index {1}", token, parseState.getCurrentIndex()));

            boolean isValidSymbol = parseState.isPreviousNodeJsonSymbol(JsonSymbol.ANY);
            if (!isValidSymbol)
                throw new JsonParseException(MessageFormat.format("Unexpected character {0} at index {1}", token, parseState.getCurrentIndex()));
        }
    }

    @Override
    public void updateParseState(ParseState parseState, String token) throws JsonParseException {

        JsonObject object = new JsonObject();

        Stack<JsonNode> nodeStack = parseState.getNodeStack();
        if (!parseState.isPreviousNodeJsonSymbol(JsonSymbol.JSON_OBJECT_START)) {
            JsonNode value = nodeStack.pop();
            nodeStack.pop();  //Popping the delimiting colon
            String key = ((JsonElement) nodeStack.pop()).getData();

            object.put(key, value);
        }
        boolean isValidSymbol = parseState.isPreviousNodeJsonSymbol(JsonSymbol.JSON_OBJECT_START);
        if (!isValidSymbol)
            throw new JsonParseException(MessageFormat.format("Unexpected character {0} at index {1}", token, parseState.getCurrentIndex()));

        //Popping the opening {
        nodeStack.pop();
        nodeStack.push(object);
    }
}
