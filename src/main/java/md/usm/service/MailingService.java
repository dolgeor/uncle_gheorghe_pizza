package md.usm.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailingService {

    @Autowired
    public JavaMailSender emailSender;

    public void send(final String to, final String subject, final String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        emailSender.send(message);
    }

    public void sendWithAttachment(final String to, final String subject, final String msg, final String pathToFile) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            FileSystemResource file = new FileSystemResource(pathToFile);
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(msg);
            helper.addAttachment(file.getFilename(), file);
        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
        emailSender.send(message);
    }
}
