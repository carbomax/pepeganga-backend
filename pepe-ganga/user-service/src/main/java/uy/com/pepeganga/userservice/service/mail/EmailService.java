package uy.com.pepeganga.userservice.service.mail;

import uy.com.pepeganga.business.common.entities.User;
import uy.com.pepeganga.userservice.entities.VerificationToken;
import uy.com.pepeganga.userservice.models.EmailBody;

import javax.mail.MessagingException;
import java.util.Map;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);

    void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment);

    VerificationToken sendMessageToResetPassword(EmailBody body) throws MessagingException;

    Map<String, Object> sendEmailToResetPassword(String email, String url);
}
