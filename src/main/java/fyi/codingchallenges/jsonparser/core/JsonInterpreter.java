package fyi.codingchallenges.jsonparser.core;

import fyi.codingchallenges.jsonparser.models.JsonSymbol;

public class JsonInterpreter {

    public static JsonSymbol getJsonSymbol(char c) {
        return getJsonSymbol(String.valueOf(c));
    }

    private static JsonSymbol getJsonSymbol(String s) {

        for(JsonSymbol jsonSymbol: JsonSymbol.values()) {
            if(jsonSymbol.getSymbol().equalsIgnoreCase(s.trim()))
                return jsonSymbol;
        }
        return JsonSymbol.ANY;
    }
}
