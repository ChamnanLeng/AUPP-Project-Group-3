package com.aupp.expensetracker.response;

import lombok.Getter;

@Getter
public class LoginMessage {
    int userId;
    String message;
    Boolean status;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public void setUserId(int userId){ this.userId = userId; }

    public LoginMessage(int userId, String message, Boolean status) {
        this.userId = userId;
        this.message = message;
        this.status = status;
    }
}
