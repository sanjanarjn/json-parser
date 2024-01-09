package fyi.codingchallenges.jsonparser.handlers;

import fyi.codingchallenges.jsonparser.models.JsonSymbol;

public class JsonSymbolHandlerFactory {

    public static JsonSymbolHandler getJsonSymbolHandler(JsonSymbol symbol) {
       return switch (symbol) {
           case JSON_OBJECT_START -> new JsonObjectStartSymbolHandler();
           case JSON_OBJECT_END ->  new JsonObjectEndSymbolHandler();
           case JSON_ARRAY_START -> new JsonArrayStartSymbolHandler();
           case JSON_ARRAY_END -> new JsonArrayEndSymbolHandler();
           case QUOTE ->  new QuoteSymbolHandler();
           case COLON -> new ColonSymbolHandler();
           case COMMA -> new CommaSymbolHandler();
           case ESCAPE -> new EscapeSymbolHandler();
           case ANY ->  new AnySymbolHandler();
       };
    }
}
