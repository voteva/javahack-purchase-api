package com.purchase.admin.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.Properties;

@Data
@Validated
@ConfigurationProperties(prefix = "purchase.api.mail")
public class MailProperties {

    @NotBlank
    private String sendgridApiKey;

    @NotBlank
    private String defaultSubject;
    @NotBlank
    private String defaultReplyTo;
    @NotBlank
    private String storeProtocol;
    @NotBlank
    private String pop3sHost;
    @NotBlank
    private String pop3sPort;
    @NotBlank
    private String pop3StarttlsEnable;

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String connectiontimeout;

    public Properties getPopProperties() {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", storeProtocol);
        properties.put("mail.pop3s.host", pop3sHost);
        properties.put("mail.pop3s.port", pop3sPort);
        properties.put("mail.pop3.starttls.enable", pop3StarttlsEnable);
        properties.put("mail.pop3s.username", username);
        properties.put("mail.pop3s.password", password);
        properties.put("mail.pop3.connectiontimeout", connectiontimeout);

        return properties;
    }

    public Properties getSmtpProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return prop;
    }
}
