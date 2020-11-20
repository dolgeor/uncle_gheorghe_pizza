package md.usm.controller.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//todo remove in future
@RestController
public class MailController {

    @Autowired
    public JavaMailSender emailSender;

    @GetMapping("/send/{msg}")
    public SimpleMailMessage send(@PathVariable String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("dolgeor@yandex.ru");
        message.setSubject("HELLO");
        message.setText(msg);
        emailSender.send(message);
        return message;
    }
}