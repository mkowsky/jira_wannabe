package pl.mkowsky.jirawannabedemo.services;

public interface EmailService {

    void sendEmaiLTaskCraeted();
    void sendEmailTaskStatusChanged();
    void sendConfirmationEmail(String odbiorca,String tytul, String zawartosc);
}
