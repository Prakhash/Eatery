package com.eatery;

/**
 * Created by bruntha on 7/10/15.
 */
public class Tag {
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
