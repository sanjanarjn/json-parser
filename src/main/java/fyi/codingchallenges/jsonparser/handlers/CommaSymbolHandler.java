package fyi.codingchallenges.jsonparser.handlers;

import fyi.codingchallenges.jsonparser.exceptions.JsonParseException;
import fyi.codingchallenges.jsonparser.models.JsonElement;
import fyi.codingchallenges.jsonparser.models.JsonNode;
import fyi.codingchallenges.jsonparser.models.JsonSymbol;
import fyi.codingchallenges.jsonparser.models.ParseState;

import java.util.Deque;
import java.util.Stack;

public class CommaSymbolHandler extends SimpleStringSymbolHandler {

    @Override
    public void validateParseState(ParseState parseState, String token) throws JsonParseException {

        validate(parseState, token, !parseState.emptyParseState());

        if (!parseState.getCurrentToken().isEmpty()) {
            boolean isValidSymbol = parseState.isPreviousNodeJsonSymbol(JsonSymbol.JSON_ARRAY_START) || parseState.isPreviousNodeJsonSymbol(JsonSymbol.COMMA) || parseState.isPreviousNodeJsonSymbol(JsonSymbol.COLON);
            validate(parseState, token, isValidSymbol);
        } else {
            boolean isValidSymbol = parseState.isPreviousNodeJsonSymbol(JsonSymbol.ANY) || parseState.isPreviousNodeJsonObject() || parseState.isPreviousNodeJsonArray();
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

        nodeStack.push(new JsonElement(token, JsonSymbol.COMMA));
    }
}
