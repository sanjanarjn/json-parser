package fyi.codingchallenges.jsonparser.core;

import fyi.codingchallenges.jsonparser.exceptions.JsonParseException;
import fyi.codingchallenges.jsonparser.handlers.JsonSymbolHandler;
import fyi.codingchallenges.jsonparser.handlers.JsonSymbolHandlerFactory;
import fyi.codingchallenges.jsonparser.models.*;

public class JsonParser {

    // {"key" : {"k1": "v1"}}
    public JsonNode parse(String s) throws JsonParseException {

        ParseState parseState = new ParseState();
        int size = s.length();
        for(int i = 0; i < size; i++) {
            char c = s.charAt(i);
            JsonSymbol jsonSymbol = JsonInterpreter.getJsonSymbol(c);
            JsonSymbolHandler handler = JsonSymbolHandlerFactory.getJsonSymbolHandler(jsonSymbol);
            parseState.setCurrentIndex(i);
            handler.handleJsonSymbol(parseState, String.valueOf(c));
        }
        if(parseState.getNodeStack().size() != 1)
            throw new JsonParseException("Invalid Json");

        return parseState.getNodeStack().peek();
    }
}
