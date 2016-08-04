package com.theironyard.command;

/**
 * Created by Nigel on 8/4/16.
 */
public class UserCommand {
    private String username;
    private String password;

    public UserCommand() {
    }

    public UserCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
