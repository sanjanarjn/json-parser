package fyi.codingchallenges.jsonparser.models;

import lombok.Data;

import java.util.Stack;

@Data
public class ParseState {

    private StringBuilder currentToken;
    private int currentIndex;

    private Stack<JsonNode> nodeStack;

    public ParseState() {
        this.nodeStack = new Stack<>();
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

    public boolean emptyParseState() {
        return nodeStack.isEmpty();
    }

    public void appendToCurrentToken(String token) {
        this.currentToken.append(token);
    }

    public void resetCurrentToken() {
        this.currentToken = new StringBuilder();
    }
}
