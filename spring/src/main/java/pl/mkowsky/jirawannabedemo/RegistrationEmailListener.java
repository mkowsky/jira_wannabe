package pl.mkowsky.jirawannabedemo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import pl.mkowsky.jirawannabedemo.model.User;
import pl.mkowsky.jirawannabedemo.services.EmailService;
import pl.mkowsky.jirawannabedemo.services.UserService;


import java.util.UUID;

@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {

    @Autowired
    private UserService userService;

    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;

    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(OnRegistrationSuccessEvent event) {
        this.confirmRegistration(event);

    }

    private void confirmRegistration(OnRegistrationSuccessEvent event) {

        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);
//
       //String recipient = user.getEmail();
        String subject = "Registration Confirmation";
        String url
                = event.getAppUrl() + "/confirmRegistration?token=" + token;

        //po co to ?
        //String message = messages.getMessage("message.registrationSuccessConfimationLink", null, event.getLocale());

        //TODO: zmienic tutaj na email uzytkownika
        emailService.sendConfirmationEmail(user.getEmail(), subject, "http://localhost:8080/api/auth" + url);

    }
}
