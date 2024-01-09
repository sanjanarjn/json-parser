package fyi.codingchallenges.jsonparser.exceptions;

public class JsonParseException extends Exception {

    public JsonParseException(String message) {
        super(message);
    }

    public JsonParseException(String message, Exception e) {
        super(message, e);
    }
}
