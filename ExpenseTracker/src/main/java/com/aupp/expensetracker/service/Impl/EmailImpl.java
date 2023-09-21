package com.aupp.expensetracker.service.Impl;

import com.aupp.expensetracker.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailImpl implements EmailService {


    private final JavaMailSender javaMailSender;


    public EmailImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendByMail(String to, String email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage, "utf-8"
            );
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Verification code");

            helper.setFrom("trykrisnapcu@gmail.com");
            javaMailSender.send(mimeMessage);
        }catch (Exception ex){
            System.out.println("send: " + ex);
        }
    }
}
