package com.corsojava.esercizi.service.naruto.dto;

public class Message {

    private long id;
    private String content;

    public Message() {
        super();
        this.id = 0;
        this.content = "";
    }

    public Message(long id, String content) {
        super();
        this.id = id;
        this.content = content;
    }


    // Getters
    public long getId() { return id; }
    public String getContent() { return content; }


    // Setters
    public void setId(long id) { this.id = id; }
    public void setContent(String content) { this.content = content; }

}
