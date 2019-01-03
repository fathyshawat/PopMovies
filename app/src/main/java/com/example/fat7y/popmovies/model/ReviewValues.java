package com.example.fat7y.popmovies.model;

public class ReviewValues {


    private String author ;
    private String content;

    public ReviewValues(String author , String content){
        this.author = author;
        this.content = content;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}


