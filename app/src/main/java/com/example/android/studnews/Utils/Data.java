package com.example.android.studnews.Utils;

import java.io.Serializable;

/**
 * Created by kedee on 28/2/17.
 */

public class Data implements Serializable{

    private static final long serialVersionUID = 8L;

    private int id;
    private String category;
    private String tagLine;
    private String content;
    private String imageUrl;

    public Data() {
    }

    public Data(int id, String category, String tagLine, String content, String imageUrl) {
        this.id = id;
        this.category = category;
        this.tagLine = tagLine;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
