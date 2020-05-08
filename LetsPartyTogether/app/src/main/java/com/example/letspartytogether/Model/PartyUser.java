package com.example.letspartytogether.Model;

public class PartyUser {
    private String username;
    private String partyCode;
    private String partyName;

    public PartyUser(String username, String partyCode,String partyName) {
        this.username = username;
        this.partyCode = partyCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String name) {
        this.partyCode = partyCode;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
}
