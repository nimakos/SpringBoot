package gr.nikolis.service.web;

import gr.nikolis.qualifiers.MailUserName;
import gr.nikolis.sql.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    @MailUserName
    public String mailUserName;

    public void constructEmail(HttpServletRequest request, User user) {
        String appUrl = String.format("%s://%s:%s", request.getScheme(), request.getServerName(), request.getServerPort());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Registration Confirmation");
        message.setText("To confirm your e-mail address, please click the link below:\n"
                + appUrl + "/confirm?token=" + user.getConfirmationToken());
        message.setFrom(mailUserName);

        sendEmail(message);
    }

    @Async
    private void sendEmail(SimpleMailMessage message) {
        mailSender.send(message);
    }
}
