package uy.com.pepeganga.userservice.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    private static String TEMPLATE = "<td style=\"text-align:center;vertical-align:top;direction:ltr;font-size:0px;padding:40px 50px\">\n" +
            "  <div aria-labelledby=\"mj-column-per-100\"\n" +
            "    style=\"vertical-align:top;display:inline-block;direction:ltr;font-size:13px;text-align:left;width:100%\">\n" +
            "    <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\n" +
            "      <tbody>\n" +
            "        <tr>\n" +
            "          <td style=\"word-break:break-word;font-size:0px;padding:0px\" align=\"left\">\n" +
            "            <div\n" +
            "              style=\"color:#737f8d;font-family:Whitney,Helvetica Neue,Helvetica,Arial,Lucida Grande,sans-serif;font-size:16px;line-height:24px;text-align:left\">\n" +
            "\n" +
            "              <h2\n" +
            "                style=\"font-family:Whitney,Helvetica Neue,Helvetica,Arial,Lucida Grande,sans-serif;font-weight:500;font-size:20px;color:#4f545c;letter-spacing:0.27px\">\n" +
            "                XusernameX,</h2>\n" +
            "              <p>Su contraseña de Pepeganga se puede restablecer haciendo clic en el botón de abajo. Si no solicitó una nueva contraseña, ignore este correo electrónico.</p>                \n" +
            "            </div>\n" +
            "          </td>\n" +
            "        </tr>\n" +
            "        <tr>\n" +
            "          <td style=\"word-break:break-word;font-size:0px;padding:10px 25px;padding-top:20px\" align=\"center\">\n" +
            "            <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:separate\" align=\"center\"\n" +
            "              border=\"0\">\n" +
            "              <tbody>\n" +
            "                <tr>\n" +
            "                  <td style=\"border:none;border-radius:3px;color:white;padding:15px 19px\" align=\"center\" valign=\"middle\"\n" +
            "                    bgcolor=\"#7289DA\"><a href=\"https://pepeganga-d4ce6.web.app\"\n" +
            "                      style=\"text-decoration:none;line-height:100%;background:#7289da;color:white;font-family:Ubuntu,Helvetica,Arial,sans-serif;font-size:15px;font-weight:normal;text-transform:none;margin:0px\"\n" +
            "                      target=\"_blank\" data-saferedirecturl=\"https://pepeganga-d4ce6.web.app\">\n" +
            "                      <span>Reiniciar</span> <span>Contraseña</span>\n" +
            "                    </a></td>\n" +
            "                </tr>\n" +
            "              </tbody>\n" +
            "            </table>\n" +
            "          </td>\n" +
            "        </tr>\n" +
            "        <tr>\n" +
            "          <td style=\"word-break:break-word;font-size:0px;padding:30px 0px\">\n" +
            "            <p style=\"font-size:1px;margin:0px auto;border-top:1px solid #dcddde;width:100%\"></p>\n" +
            "          </td>\n" +
            "        </tr>\n" +
            "        <tr>\n" +
            "          <td style=\"word-break:break-word;font-size:0px;padding:0px\" align=\"left\">\n" +
            "            <div\n" +
            "              style=\"color:#747f8d;font-family:Whitney,Helvetica Neue,Helvetica,Arial,Lucida Grande,sans-serif;font-size:13px;line-height:16px;text-align:left\">\n" +
            "\n" +
            "            </div>\n" +
            "          </td>\n" +
            "        </tr>\n" +
            "      </tbody>\n" +
            "    </table>\n" +
            "  </div>\n" +
            "</td>";


    @Override
    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("pepegangamail@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(TEMPLATE);
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
            helper.setText(TEMPLATE.replace("XusernameX", "Hola Yariel Usher"), true);
           /* FileSystemResource file
                    = new FileSystemResource(new File(pathToAttachment));
            System.out.println(file.getPath());
            helper.addAttachment("Invoice", file.getFile());*/

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

}