package com.sag.wedt;

import twitter4j.Status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dobi on 06.05.16.
 */
public class Category {

    private String category;
    private ArrayList<String> keyWords;
    private List<Status> statuses;


    public Category(String category) {
        this.category = category;
        this.keyWords = new ArrayList<>();

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getKeyWords() {
        return keyWords;
    }

    public void addKeyWord(String word) {
        this.keyWords.add(word);
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Status s : statuses ) {
            sb.append(category).append(" ").append(s.getText()).append("\n");
        }
        return sb.toString();
    }
}
