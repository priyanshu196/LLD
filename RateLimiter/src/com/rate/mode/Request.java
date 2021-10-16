package com.rate.mode;

public class Request {

    String type;
    String IP;
    String userID;

    public Request(String type, String IP, String userID) {
        this.type = type;
        this.IP = IP;
        this.userID = userID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
