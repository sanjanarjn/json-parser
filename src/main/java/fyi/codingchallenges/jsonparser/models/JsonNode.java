package fyi.codingchallenges.jsonparser.models;

public interface JsonNode {

    public int getDepth();
    public void setDepth(int depth);
    public boolean isArray();
    public boolean isElement();
    public int getIndex();
    public void setIndex(int index);
}
