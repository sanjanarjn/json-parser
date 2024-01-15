package fyi.codingchallenges.jsonparser.handlers;

import fyi.codingchallenges.jsonparser.exceptions.JsonParseException;
import fyi.codingchallenges.jsonparser.models.JsonSymbol;
import fyi.codingchallenges.jsonparser.models.ParseState;

public class EscapeSymbolHandler implements JsonSymbolHandler {
    @Override
    public void validateParseState(ParseState parseState, String token) throws JsonParseException {
        validate(parseState, token, !parseState.emptyParseState());
        validate(parseState, token, parseState.isPreviousNodeJsonSymbol(JsonSymbol.QUOTE));
    }

    @Override
    public void updateParseState(ParseState parseState, String token) {
        //Append escape to current token and update the parse state to indicate that the next char should be escaped.
        parseState.appendToCurrentToken(token);
        parseState.setEscapeNextCharacter(true);
    }
}
