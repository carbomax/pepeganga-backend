package uy.com.pepeganga.userservice.service.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import uy.com.pepeganga.business.common.entities.Profile;
import uy.com.pepeganga.userservice.entities.VerificationToken;
import uy.com.pepeganga.userservice.models.EmailBody;
import uy.com.pepeganga.userservice.models.EmailTemplates;
import uy.com.pepeganga.userservice.repository.VerificationTokenRepository;
import uy.com.pepeganga.userservice.service.IProfileService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Component
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    IProfileService profileService;

    @Autowired
    VerificationTokenRepository tokenRepository;


    @Override
    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("pepegangamail@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(EmailTemplates.RESET_PASSWORD);
        javaMailSender.send(message);
    }

    @Override
    public void sendMessageWithAttachment(
            String to, String subject, String text, String pathToAttachment) {


        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("pepegangamail@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(EmailTemplates.RESET_PASSWORD.replace("XusernameX", "Hola Yariel Usher"), true);
            FileSystemResource file
                    = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice", file.getFile());

            javaMailSender.send(message);
        } catch (MessagingException e) {
           logger.error(e.getMessage());
        }


    }

    @Override
    public VerificationToken sendMessageToResetPassword(EmailBody body) throws MessagingException {
        logger.info("Preparing email to reset password....");
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("PEPEGANGA");
        helper.setTo(body.getTo());
        helper.setSubject(body.getSubject());
        VerificationToken verificationTokenFounded = tokenRepository.findByUser(body.getUser().getId());
        if (Objects.nonNull(verificationTokenFounded)) {
            verificationTokenFounded.updateToken(UUID.randomUUID().toString());
            tokenRepository.save(verificationTokenFounded);
        } else {
            verificationTokenFounded = tokenRepository.save(new VerificationToken(UUID.randomUUID().toString(), body.getUser()));
        }

        helper.setText(EmailTemplates.RESET_PASSWORD.replace("XusernameX", "Hola " + body.getUserName()).replace("Xurl-reset-passwordX", body.getAttach().concat("?token=").concat(verificationTokenFounded.getToken())), true);
        javaMailSender.send(message);
        logger.info("Email sent successfully....");
        return verificationTokenFounded;

    }

    public Map<String, Object> sendEmailToResetPassword(@RequestParam String email, @RequestParam String url) {
        Map<String, Object> map = new HashMap<>();
        try {
            Profile profile = profileService.findProfileByUserEmail(email.trim());
            if (Objects.isNull(profile.getUser())) {
                map.put("userNotFound", "User not Found");
            } else  if(Boolean.TRUE.equals(profile.getUser().getEnabled())){
                 // Send email and await response
                EmailBody emailBody = new EmailBody();
                emailBody.setTo(profile.getUser().getEmail());
                emailBody.setUserName(profile.getBusinessName());
                emailBody.setSubject("Cambiar contraseña");
                emailBody.setUser(profile.getUser());
                emailBody.setAttach(url);
                if(Objects.nonNull(this.sendMessageToResetPassword(emailBody))){
                    map.put("sent", "Email sent successfully");
                } else map.put("tokenNotSaved", "Token not saved");

            } else {
                map.put("userNotEnabled", "User not enable");
            }
        } catch (Exception e) {
            map.put("error", e.getMessage());
        }
        return map;
    }

}