package fyi.codingchallenges.jsonparser.handlers;

import fyi.codingchallenges.jsonparser.exceptions.JsonParseException;
import fyi.codingchallenges.jsonparser.models.ParseState;

public interface JsonSymbolHandler {

      void validateParseState(ParseState parseState, String token) throws JsonParseException;

     default void handleJsonSymbol(ParseState parseState, String token) throws JsonParseException {
          validateParseState(parseState, token);
          updateParseState(parseState, token);
     }

     void updateParseState(ParseState parseState, String token) throws JsonParseException;
}
