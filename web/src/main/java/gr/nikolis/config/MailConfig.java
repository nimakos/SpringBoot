package gr.nikolis.config;

import gr.nikolis.qualifiers.MailUserName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {

    // == fields ==
    @Value("${spring.mail.username:something@gmail.com}")
    private String mailUserName;

    @Value("${spring.mail.password:password}")
    private String mailPassword;

    // == Beans ==
    @Bean
    @MailUserName
    public String getMailUserName() {
        return mailUserName;
    }

    @Bean
    public String getMailPassword() {
        return mailPassword;
    }

    /**
     * Mail properties
     *
     * @return Mail properties
     */
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(getMailUserName());
        mailSender.setPassword(getMailPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
