package fyi.codingchallenges.jsonparser.handlers;

import fyi.codingchallenges.jsonparser.exceptions.JsonParseException;
import fyi.codingchallenges.jsonparser.models.JsonElement;
import fyi.codingchallenges.jsonparser.models.JsonNode;
import fyi.codingchallenges.jsonparser.models.JsonSymbol;
import fyi.codingchallenges.jsonparser.models.ParseState;

import java.text.MessageFormat;
import java.util.Deque;
import java.util.Stack;

public class JsonObjectStartSymbolHandler extends SimpleStringSymbolHandler {

    @Override
    public void validateParseState(ParseState parseState, String token) throws JsonParseException {
        Deque<JsonNode> nodeStack = parseState.getNodeStack();
        if(!nodeStack.isEmpty()) {
           boolean isValidSymbol = parseState.isPreviousNodeJsonSymbol(JsonSymbol.COMMA) ||
                   parseState.isPreviousNodeJsonSymbol(JsonSymbol.COLON) ||
                   parseState.isPreviousNodeJsonSymbol(JsonSymbol.JSON_ARRAY_START);
           if(!isValidSymbol)
               throw new JsonParseException(MessageFormat.format("Unexpected character {0} at index {1}", token, parseState.getCurrentIndex()));
        }
    }

    @Override
    public void updateParseState(ParseState parseState, String token) {
        Deque<JsonNode> nodeStack = parseState.getNodeStack();
        nodeStack.push(new JsonElement(JsonSymbol.JSON_OBJECT_START.getSymbol(), JsonSymbol.JSON_OBJECT_START));
    }
}
