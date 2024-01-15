package fyi.codingchallenges.jsonparser.handlers;

import fyi.codingchallenges.jsonparser.exceptions.JsonParseException;
import fyi.codingchallenges.jsonparser.models.JsonSymbol;
import fyi.codingchallenges.jsonparser.models.ParseState;

import java.text.MessageFormat;

public class AnySymbolHandler implements JsonSymbolHandler {

    @Override
    public void validateParseState(ParseState parseState, String token) throws JsonParseException {

        // Checking if there are valid previous tokens like { or [
        if(parseState.emptyParseState()) {
            throw new JsonParseException(MessageFormat.format("Unexpected character {0} at index {1}", token, parseState.getCurrentIndex()));
        }
        else {
            /**
             * Validating if the tokens before are valid, few examples below
             * Comma: [1, 2]
             * Colon: {"key": true}
             * [    : [1]
             * {"key"}
             */

            boolean isValidSymbol = parseState.isPreviousNodeJsonSymbol(JsonSymbol.COMMA) || parseState.isPreviousNodeJsonSymbol(JsonSymbol.COLON) ||
                    parseState.isPreviousNodeJsonSymbol(JsonSymbol.JSON_ARRAY_START) || parseState.isPreviousNodeJsonSymbol(JsonSymbol.QUOTE);
            if (!isValidSymbol)
                throw new JsonParseException(MessageFormat.format("Unexpected character {0} at index {1}", token, parseState.getCurrentIndex()));
        }
    }

    @Override
    public void updateParseState(ParseState parseState, String token) {
        // Keep appending the new token to current token
        parseState.appendToCurrentToken(token);
    }
}
