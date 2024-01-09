package fyi.codingchallenges.jsonparser.handlers;

import fyi.codingchallenges.jsonparser.exceptions.JsonParseException;
import fyi.codingchallenges.jsonparser.models.JsonElement;
import fyi.codingchallenges.jsonparser.models.JsonNode;
import fyi.codingchallenges.jsonparser.models.JsonSymbol;
import fyi.codingchallenges.jsonparser.models.ParseState;

import java.text.MessageFormat;
import java.util.Stack;

public class QuoteSymbolHandler implements JsonSymbolHandler {

    @Override
    public void validateParseState(ParseState parseState, String token) throws JsonParseException {

        if (parseState.emptyParseState())
            throw new JsonParseException(MessageFormat.format("Unexpected character {0} at index {1}", token, parseState.getCurrentIndex()));

        boolean isClosingQuote = !parseState.getCurrentToken().isEmpty();
        if (isClosingQuote) {
            boolean isOpeningQuotePresent = parseState.isPreviousNodeJsonSymbol(JsonSymbol.QUOTE);
            if (!isOpeningQuotePresent)
                throw new JsonParseException(MessageFormat.format("Unexpected character {0} at index {1}", token, parseState.getCurrentIndex()));
        }
        else {
            boolean isValidSymbol = parseState.isPreviousNodeJsonSymbol(JsonSymbol.COMMA) || parseState.isPreviousNodeJsonSymbol(JsonSymbol.COLON) ||
                    parseState.isPreviousNodeJsonSymbol(JsonSymbol.JSON_ARRAY_START) || parseState.isPreviousNodeJsonSymbol(JsonSymbol.JSON_OBJECT_START);
            if (!isValidSymbol)
                throw new JsonParseException(MessageFormat.format("Unexpected character {0} at index {1}", token, parseState.getCurrentIndex()));
        }
    }

    @Override
    public void updateParseState(ParseState parseState, String token) throws JsonParseException {

        Stack<JsonNode> nodeStack = parseState.getNodeStack();
        JsonElement element = new JsonElement();
        element.setSymbol(JsonSymbol.QUOTE);
        element.setData(JsonSymbol.QUOTE.getSymbol());

        String currentToken = parseState.getCurrentToken();
        boolean isClosingQuote = !currentToken.isEmpty();

        if (isClosingQuote) {
            // Pop the opening quote
            nodeStack.pop();

            //Push the value within quotes
            JsonElement node = new JsonElement(currentToken, JsonSymbol.ANY);
            nodeStack.push(node);
            parseState.resetCurrentToken();
        } else {
            JsonElement node = new JsonElement(token, JsonSymbol.QUOTE);
            nodeStack.push(node);
        }
    }
}
