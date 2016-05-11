package com.sag.wedt;

import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dobi on 06.05.16.
 */
public class Category {

    private String category;
    private ArrayList<String> keyWords;
    private List<String> statuses;


    public Category(String category) {
        this.category = category;
        this.keyWords = new ArrayList<>();
        this.statuses = new ArrayList<>();

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

    public List<String> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> ss) {
        for (Status s : ss) {
            // remove urls "https://...":
            String trimmed = s.getText().replaceAll("https?://\\S+\\s?", "");
            // remove users "@...":
            trimmed = trimmed.replaceAll("@\\S+\\s?", "");
            statuses.add(trimmed);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String s : statuses ) {
            sb.append(category).append(" ").append(s).append("\n");
        }
        return sb.toString();
    }
}
