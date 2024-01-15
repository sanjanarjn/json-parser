package fyi.codingchallenges.jsonparser.handlers;

import fyi.codingchallenges.jsonparser.exceptions.JsonParseException;
import fyi.codingchallenges.jsonparser.models.JsonSymbol;
import fyi.codingchallenges.jsonparser.models.ParseState;

public class WhiteSpaceSymbolHandler implements JsonSymbolHandler {

    @Override
    public void validateParseState(ParseState parseState, String token) throws JsonParseException {}

    @Override
    public void updateParseState(ParseState parseState, String token) throws JsonParseException {
        if(parseState.isPreviousNodeJsonSymbol(JsonSymbol.QUOTE)) {
            parseState.appendToCurrentToken(token);
        }
    }
}
