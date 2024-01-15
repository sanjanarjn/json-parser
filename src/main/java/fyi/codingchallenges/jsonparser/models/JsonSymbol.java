package fyi.codingchallenges.jsonparser.models;

public enum JsonSymbol {

    JSON_OBJECT_START("{"), JSON_OBJECT_END("}"), JSON_ARRAY_START("["), JSON_ARRAY_END("]"), QUOTE("\""), COLON(":" ), COMMA(","), ESCAPE("\\"), WHITE_SPACE(""), ANY("Any");

    private String symbol;

    private JsonSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
    }
}
