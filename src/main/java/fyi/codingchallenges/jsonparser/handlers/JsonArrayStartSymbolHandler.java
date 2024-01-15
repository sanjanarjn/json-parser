package fyi.codingchallenges.jsonparser.handlers;

import fyi.codingchallenges.jsonparser.exceptions.JsonParseException;
import fyi.codingchallenges.jsonparser.models.JsonElement;
import fyi.codingchallenges.jsonparser.models.JsonNode;
import fyi.codingchallenges.jsonparser.models.JsonSymbol;
import fyi.codingchallenges.jsonparser.models.ParseState;

import java.text.MessageFormat;
import java.util.Stack;

public class JsonArrayStartSymbolHandler extends SimpleStringSymbolHandler {
    @Override
    public void validateParseState(ParseState parseState, String token) throws JsonParseException {
        Stack<JsonNode> nodeStack = parseState.getNodeStack();
        if(!nodeStack.isEmpty()) {
            JsonNode prevNode = nodeStack.peek();
            boolean prevNodeHasToBeElement = prevNode instanceof JsonElement;

            if(!prevNodeHasToBeElement)
                throw new JsonParseException(MessageFormat.format("Unexpected character {0} at index {1}", token, parseState.getCurrentIndex()));

            JsonElement element = (JsonElement) prevNode;
            JsonSymbol prevSymbol = element.getSymbol();
            boolean isValidSymbol = JsonSymbol.COMMA.equals(prevSymbol) || JsonSymbol.COLON.equals(prevSymbol) ||
                    JsonSymbol.JSON_ARRAY_START.equals(prevSymbol);

            if(!isValidSymbol)
                throw new JsonParseException(MessageFormat.format("Unexpected character {0} at index {1}", token, parseState.getCurrentIndex()));
        }
    }

    @Override
    public void updateParseState(ParseState parseState, String token) {
        Stack<JsonNode> nodeStack = parseState.getNodeStack();
        nodeStack.push(new JsonElement(token, JsonSymbol.JSON_ARRAY_START));
    }
}
