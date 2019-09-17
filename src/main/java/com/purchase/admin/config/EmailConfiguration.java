package com.purchase.admin.config;

import com.purchase.admin.config.properties.MailProperties;
import com.sendgrid.SendGrid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

@Configuration
@RequiredArgsConstructor
@ConditionalOnBean(PropertiesConfiguration.class)
public class EmailConfiguration {

    private final MailProperties mailProperties;

    @Bean("sendgrid")
    public SendGrid sendGridClient() {
        return new SendGrid("");
    }

    @Bean("smtpSession")
    public Session smtpSession() {
        return Session.getInstance(mailProperties.getSmtpProperties(),
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mailProperties.getUsername(), mailProperties.getPassword());
                    }
                });
    }
}
