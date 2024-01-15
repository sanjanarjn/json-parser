package fyi.codingchallenges.jsonparser.handlers;

import fyi.codingchallenges.jsonparser.exceptions.JsonParseException;
import fyi.codingchallenges.jsonparser.models.JsonSymbol;
import fyi.codingchallenges.jsonparser.models.ParseState;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface JsonSymbolHandler {

    static final int MAX_DEPTH = 20;

    static final String VALID_STRING_REGEX = "(?:\\\\u[\\da-fA-F]{4}|\\\\[\"\\\\/bfnrt]|[^\"\\\\])*";

    static final String VALID_NUMERIC_REGEX = "-?([0]{1}|[1-9]{1}\\d*)(\\.\\d+)?([eE][+-]?\\d+)?";

    enum LiteralType {
        QUOTED, UNQUOTED
    }

    void validateParseState(ParseState parseState, String token) throws JsonParseException;

    default void handleJsonSymbol(ParseState parseState, String token) throws JsonParseException {
        if (parseState.isEscapeNextCharacter()) {
            parseState.escapeToken(token);
        }
        else {
            validateParseState(parseState, token);
            updateParseState(parseState, token);
        }
    }

    void updateParseState(ParseState parseState, String token) throws JsonParseException;

    default void validate(ParseState parseState, String token, boolean isValidSymbol) throws JsonParseException {
        if (!isValidSymbol)
            throw new JsonParseException(MessageFormat.format("Unexpected character {0} at index {1}", token, parseState.getCurrentIndex()));
    }

    default boolean isValidLiteral(String currentToken, LiteralType literalType) {
        currentToken = currentToken.trim();
        boolean isDistinctValue = "null".equalsIgnoreCase(currentToken) || "true".equalsIgnoreCase(currentToken) || "false".equalsIgnoreCase(currentToken);
        if(isDistinctValue) {
            return true;
        }

        if(currentToken.contains("\u0009") || (currentToken.contains("\n"))) {
            return false;
        }
        if(LiteralType.QUOTED.equals(literalType)) {
            return validateWithRegex(currentToken, VALID_STRING_REGEX);
        }
        else {
            return validateWithRegex(currentToken, VALID_NUMERIC_REGEX);
        }
    }

    default boolean validateWithRegex(String currentToken, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(currentToken);
        return matcher.matches();
    }
}
