package com.eatery;

/**
 * Created by bruntha on 7/10/15.
 */
public class Tag {
    private String index;
    private int startIndex;
    private int endIndex;

    public Tag(int startIndex, int endIndex, String key) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.key = key;
    }

    public int getStartIndex() {
        return startIndex;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "index='" + index + '\'' +
                ", startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", key='" + key + '\'' +
                ", word='" + word + '\'' +
                '}';
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public Tag(String index, String key, String word) {
        this.index = index;
        this.key = key;
        this.word = word;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    private String key;
    private String word;

    public Tag(String key, String word) {
        this.key = key;
        this.word = word;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

}
