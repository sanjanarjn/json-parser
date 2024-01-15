package fyi.codingchallenges.jsonparser.handlers;

import fyi.codingchallenges.jsonparser.exceptions.JsonParseException;
import fyi.codingchallenges.jsonparser.models.JsonElement;
import fyi.codingchallenges.jsonparser.models.JsonNode;
import fyi.codingchallenges.jsonparser.models.JsonSymbol;
import fyi.codingchallenges.jsonparser.models.ParseState;

import java.text.MessageFormat;
import java.util.Deque;
import java.util.Stack;

public class ColonSymbolHandler extends SimpleStringSymbolHandler {

    @Override
    public void validateParseState(ParseState parseState, String token) throws JsonParseException {

        // Validating that colon cannot be the first token.
        validate(parseState, token, parseState.emptyParseState());

        // Validating that symbol before is always a key which is of any type
        boolean isValidSymbol = parseState.isPreviousNodeJsonSymbol(JsonSymbol.ANY);
        if (!isValidSymbol)
            throw new JsonParseException(MessageFormat.format("Unexpected character {0} at index {1}", token, parseState.getCurrentIndex()));
    }

    @Override
    public void updateParseState(ParseState parseState, String token) {
        //Push colon to the stack
        Deque<JsonNode> nodeStack = parseState.getNodeStack();
        nodeStack.push(new JsonElement(token, JsonSymbol.COLON));
    }
}
