# Json Parser

A simple json parser created as part the https://codingchallenges.fyi/challenges/challenge-json-parser by John Crickett.

# Approach

The parser uses a Deque<JsonNode> as the LIFO data structure for remembering the previous state. A JsonNode is an interface as below.

```
public interface JsonNode {
    public int getDepth();
    public void setDepth(int depth);
    public boolean isArray();
    public boolean isElement();
    public int getIndex();
    public void setIndex(int index);
}
```

The interface is implemented by the following concrete classes. (The class code is truncated for brevity here).

**1. JsonElement - Represents the json syntactical tokens( {  }  [  ]  ,  : etc), simple string or number literals.**

```
public class JsonElement implements JsonNode {
    private String data;
    private JsonSymbol symbol;
    private int index;
}
```

**2. JsonObject - Represents a json object which can have key-value pairs.**

```
public class JsonObject extends HashMap<String, JsonNode> implements JsonNode {
    private int index;
    private int depth;
}
```

**3. JsonArray - Represents a json array which can list of values(may be json objects, jsonarrays or simple tokens).**
```
public class JsonArray extends ArrayList<JsonNode> implements JsonNode {
    private int index;
    private int depth;
}
```

The following image represents how the parsing is achieved for a simple json ```{"key":"value"}```
![](/images/simple-json.png)


