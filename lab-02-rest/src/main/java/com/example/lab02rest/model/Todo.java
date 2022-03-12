package com.example.lab02rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Todo {
    private int id = 0;
    private String summary;
    private String description;

    public Todo(){

    }

    public Todo(int id, String summary){
        this.id = id;
        this.summary = summary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
