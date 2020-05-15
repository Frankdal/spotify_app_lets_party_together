package com.example.letspartytogether.Model;

public class Artist {
    private String name;

    public Artist(String name, String uri) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
