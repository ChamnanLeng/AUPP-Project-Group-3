package com.aupp.expensetracker.response;

import lombok.Getter;

@Getter
public class RegisterResponse {
    String message;
    Boolean status;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public RegisterResponse(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }
}
