package fyi.codingchallenges.jsonparser.models;

import lombok.Data;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

@Data
public class ParseState {

    private StringBuilder currentToken;
    private int currentIndex;
    private boolean escapeNextCharacter;

    private Deque<JsonNode> nodeStack;

    public ParseState() {
        this.nodeStack = new ArrayDeque<>();
        this.currentToken = new StringBuilder();
    }

    public String getCurrentToken() {
        return currentToken.toString();
    }

    public boolean isPreviousNodeJsonSymbol(JsonSymbol symbol) {
        if(nodeStack.isEmpty())
            return false;

        JsonNode prevNode = nodeStack.peek();
        boolean prevNodeHasToBeElement = prevNode instanceof JsonElement;
        if(!prevNodeHasToBeElement)
            return false;

        JsonElement element = (JsonElement) prevNode;
        JsonSymbol prevSymbol = element.getSymbol();
        return prevSymbol.equals(symbol);
    }

    public boolean isPreviousNodeJsonObject() {
        if(nodeStack.isEmpty())
            return false;

        JsonNode prevNode = nodeStack.peek();
        boolean prevNodeHasToBeObject = prevNode instanceof JsonObject;
        if(!prevNodeHasToBeObject)
            return false;

        return true;
    }

    public boolean emptyParseState() {
        return nodeStack.isEmpty();
    }

    public void pushCurrentTokenToNodeStack() {
        nodeStack.push(new JsonElement(this.getCurrentToken(), JsonSymbol.ANY));
        resetCurrentToken();
    }

    public void appendToCurrentToken(String token) {
        this.currentToken.append(token);
    }

    private void resetCurrentToken() {
        this.currentToken = new StringBuilder();
    }

    public boolean isPreviousNodeJsonArray() {
        if(nodeStack.isEmpty())
            return false;

        JsonNode prevNode = nodeStack.peek();
        boolean prevNodeHasToBeArray = prevNode instanceof JsonArray;
        if(!prevNodeHasToBeArray)
            return false;

        return true;
    }

    public void escapeToken(String token) {
        appendToCurrentToken(token);
        setEscapeNextCharacter(false);
    }
}
