package am.itspace.eshop.service.impl;

import am.itspace.eshop.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;

@Service
@RequiredArgsConstructor
public class SendMailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${file.absolut.todourl}")
    private String todoUri;

    @Value("${server.url}")
    private String serverUrl;

    public void send(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmail);  // Use the property value here
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        javaMailSender.send(simpleMailMessage);
    }

    @Async
    public void sendWelcomeMail(User user) {
        final Context ctx = new Context();
        ctx.setVariable("user", user);
        ctx.setVariable("serverUrl", serverUrl);

        final String htmlContent = templateEngine.process("mail/welcome-mail1.html", ctx);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setSubject("Welcome HTML Mail");
            message.setFrom(fromEmail);
            message.setTo(user.getEmail());

            // Create the HTML body using Thymeleaf
            message.setText(htmlContent, true); // true = isHtml
            message.addAttachment("To-do-list.xlsx", new File(todoUri));


            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
