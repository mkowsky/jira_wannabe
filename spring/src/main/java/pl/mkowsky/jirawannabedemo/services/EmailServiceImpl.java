package pl.mkowsky.jirawannabedemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import org.springframework.mail.javamail.JavaMailSender;
import java.util.Properties;



@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmaiLTaskCraeted() throws MailException {
        String templateUsername = "cookie";
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setSubject("New task has been created.");
        mail.setText("Hello "  + templateUsername + ", new task that you're involded in has been created.");
        javaMailSender.send(mail);
}

    @Override
    public void sendEmailTaskStatusChanged() {
        String templateUsername = "cookie";
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setSubject("Task's status has been changed.");
        mail.setText("Hello "  + templateUsername + ",one of tasks, that you're involved in has changed its status.");
        javaMailSender.send(mail);
    }
}
