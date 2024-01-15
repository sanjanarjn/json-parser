package fyi.codingchallenges.jsonparser.handlers;

import fyi.codingchallenges.jsonparser.exceptions.JsonParseException;
import fyi.codingchallenges.jsonparser.models.*;

import java.text.MessageFormat;
import java.util.Deque;
import java.util.Stack;

public class JsonArrayEndSymbolHandler extends SimpleStringSymbolHandler {

    @Override
    public void validateParseState(ParseState parseState, String token) throws JsonParseException {
        validate(parseState, token, !parseState.emptyParseState());

        if (!parseState.getCurrentToken().isEmpty()) {
            boolean isValidSymbol = parseState.isPreviousNodeJsonSymbol(JsonSymbol.JSON_ARRAY_START) || parseState.isPreviousNodeJsonSymbol(JsonSymbol.COMMA);
            validate(parseState, token, isValidSymbol);
        }
        else {
            boolean isValidSymbol = parseState.isPreviousNodeJsonSymbol(JsonSymbol.JSON_ARRAY_START) || parseState.isPreviousNodeJsonSymbol(JsonSymbol.ANY)
                    || parseState.isPreviousNodeJsonObject() || parseState.isPreviousNodeJsonArray();
            validate(parseState, token, isValidSymbol);
        }
    }

    @Override
    public void updateParseState(ParseState parseState, String token) throws JsonParseException {
        Deque<JsonNode> nodeStack = parseState.getNodeStack();

        String currentToken = parseState.getCurrentToken().trim();
        if (!currentToken.isEmpty()) {
            validate(parseState, currentToken, isValidLiteral(currentToken, LiteralType.UNQUOTED));
            parseState.pushCurrentTokenToNodeStack();
        }

        int maxDepth = 0;
        JsonArray array = new JsonArray();
        while(!nodeStack.isEmpty() && !parseState.isPreviousNodeJsonSymbol(JsonSymbol.JSON_ARRAY_START)) {
            JsonNode node = nodeStack.pop();
            boolean isCommaNode = isCommaNode(node);
            if(isCommaNode)
                continue;

            maxDepth = Math.max(maxDepth, node.getDepth());
            array.add(node);
        }
        validate(parseState, token, parseState.isPreviousNodeJsonSymbol(JsonSymbol.JSON_ARRAY_START));
        array.setDepth(maxDepth + 1);

        if(array.getDepth() >= MAX_DEPTH) {
            throw new JsonParseException(MessageFormat.format("Depth of the array = {0} exceeds the limit of 20", array.getDepth()));
        }
        //Popping the opening [
        nodeStack.pop();
        nodeStack.push(array);
    }

    private boolean isCommaNode(JsonNode node) {
        return node.isElement() && JsonSymbol.COMMA.equals(((JsonElement) node).getSymbol());
    }
}
