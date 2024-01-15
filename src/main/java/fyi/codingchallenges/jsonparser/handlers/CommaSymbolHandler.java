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

        // Validating that comma cannot be the first token.
        validate(parseState, token, !parseState.emptyParseState());

        if (!parseState.getCurrentToken().isEmpty()) {
            /**
             *  If currentToken is not empty, the previous symbols are validated.
             *
             *  [   : [true, 1] (true is the currentToken and prev symbol should be [
             *  ,   : [1, 2, 3] (2 is the currentToken and prev symbol should be comma
             *  :   : {"key": 1} ( 1 is the currentToken and prev symbol should be :
             */
            boolean isValidSymbol = parseState.isPreviousNodeJsonSymbol(JsonSymbol.JSON_ARRAY_START) || parseState.isPreviousNodeJsonSymbol(JsonSymbol.COMMA) || parseState.isPreviousNodeJsonSymbol(JsonSymbol.COLON);
            validate(parseState, token, isValidSymbol);
        } else {

            /**
             *  If currentToken is empty, the previous symbols are validated.
             *
             *  Any         : ["value1", "value2" ] ("value1" which is of any type should be on stack top)
             *  JsonObject  : [{"k1":"v1"}, {"k2":"v2"}] ({"k1":"v1"} which is a json object should be on stack top)
             *  JsonArray   : [[1], [2]] ([1] which is a json array should be on stack top)
             */
            boolean isValidSymbol = parseState.isPreviousNodeJsonSymbol(JsonSymbol.ANY) || parseState.isPreviousNodeJsonObject() || parseState.isPreviousNodeJsonArray();
            validate(parseState, token, isValidSymbol);
        }
    }

    @Override
    public void updateParseState(ParseState parseState, String token) throws JsonParseException {
        Deque<JsonNode> nodeStack = parseState.getNodeStack();

        // If current token not empty, validate if the current token is a valid json literal and push to stack
        String currentToken = parseState.getCurrentToken().trim();
        if (!currentToken.isEmpty()) {
            validate(parseState, currentToken, isValidLiteral(currentToken, LiteralType.UNQUOTED));
            parseState.pushCurrentTokenToNodeStack();
        }

        //Push comma to stack
        nodeStack.push(new JsonElement(token, JsonSymbol.COMMA));
    }
}
