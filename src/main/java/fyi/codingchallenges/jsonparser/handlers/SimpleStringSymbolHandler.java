package fyi.codingchallenges.jsonparser.handlers;

import fyi.codingchallenges.jsonparser.exceptions.JsonParseException;
import fyi.codingchallenges.jsonparser.models.JsonSymbol;
import fyi.codingchallenges.jsonparser.models.ParseState;

public abstract class SimpleStringSymbolHandler implements JsonSymbolHandler {

    @Override
    public void handleJsonSymbol(ParseState parseState, String token) throws JsonParseException {
        if(parseState.isPreviousNodeJsonSymbol(JsonSymbol.QUOTE)) {
            parseState.appendToCurrentToken(token);
        }
        else {
            JsonSymbolHandler.super.handleJsonSymbol(parseState, token);
        }
    }
}
