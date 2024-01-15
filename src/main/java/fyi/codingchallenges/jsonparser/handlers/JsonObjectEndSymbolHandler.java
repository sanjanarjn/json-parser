package fyi.codingchallenges.jsonparser.handlers;

import fyi.codingchallenges.jsonparser.exceptions.JsonParseException;
import fyi.codingchallenges.jsonparser.models.*;

import java.text.MessageFormat;
import java.util.Stack;

public class JsonObjectEndSymbolHandler extends SimpleStringSymbolHandler {

    @Override
    public void validateParseState(ParseState parseState, String token) throws JsonParseException {

        if (parseState.emptyParseState()) {
            throw new JsonParseException(MessageFormat.format("Unexpected character {0} at index {1}", token, parseState.getCurrentIndex()));
        }
    }

    @Override
    public void updateParseState(ParseState parseState, String token) throws JsonParseException {

        JsonObject object = new JsonObject();

        Stack<JsonNode> nodeStack = parseState.getNodeStack();
        String currentToken = parseState.getCurrentToken();
        if(!currentToken.isEmpty()) {
            validate(parseState, currentToken, isValidLiteral(currentToken, LiteralType.UNQUOTED));
            parseState.pushCurrentTokenToNodeStack();
        }
        int count = 0;
        int maxDepth = 0;
        while (!parseState.isPreviousNodeJsonSymbol(JsonSymbol.JSON_OBJECT_START)) {

            if(count != 0) {
                nodeStack.pop(); // Popping the delimiting comma between multiple key values
            }
            JsonNode value = nodeStack.pop();

            validate(parseState, token, parseState.isPreviousNodeJsonSymbol(JsonSymbol.COLON));
            nodeStack.pop();  //Popping the delimiting colon
            String key = ((JsonElement) nodeStack.pop()).getData();

            maxDepth = Math.max(maxDepth, value.getDepth());
            object.put(key, value);
            count++;
        }
        object.setDepth(maxDepth + 1);
        boolean isValidSymbol = parseState.isPreviousNodeJsonSymbol(JsonSymbol.JSON_OBJECT_START);
        if (!isValidSymbol)
            throw new JsonParseException(MessageFormat.format("Unexpected character {0} at index {1}", token, parseState.getCurrentIndex()));

        if(object.getDepth() >= MAX_DEPTH) {
            throw new JsonParseException(MessageFormat.format("Depth of the object = {0} exceeds the limit of 20", object.getDepth()));
        }        //Popping the opening {
        nodeStack.pop();
        nodeStack.push(object);
    }
}
