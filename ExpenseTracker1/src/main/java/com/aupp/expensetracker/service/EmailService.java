package com.aupp.expensetracker.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendByMail(String to, String email);
}

