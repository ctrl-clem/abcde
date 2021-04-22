package com.example.myapplication;

public class UserHelper {

    String name, username, email, password,wakeTime,sleepTime;

    public UserHelper() {

    }

    public UserHelper(String name, String username, String email, String password,String wakeTime, String sleepTime) {
        this.name = name;
        this.username= username;
        this.email=email;
        this.password=password;
        this.wakeTime=wakeTime;
        this.sleepTime=sleepTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWakeTime() {
        return wakeTime;
    }

    public void setWakeTime(String wakeTime) {
        this.wakeTime = wakeTime;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }
}
