package com.example.letspartytogether.Model;

import java.io.Serializable;

public class Song implements Serializable {

    private String name;
    private String uri;

    public Song(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String name) {
        this.name = name;
    }

}
