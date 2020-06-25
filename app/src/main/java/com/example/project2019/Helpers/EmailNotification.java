package com.example.project2019.Helpers;

import android.util.Log;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//this class uses a library (check the imports) to send emails
public class EmailNotification {

    public void notifyViaEmail(String recipients, String subject, String textMessage) {
        final String user = "seddiqinajem@gmail.com";
        final String pass = "pdlq kzeu kbxn ftuy";

        //We specify the protocols and ports
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            javax.mail.Message message = new MimeMessage(session);
            //Set a real email address to send this from
            message.setFrom(new InternetAddress(""));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
            message.setSubject(subject);
            message.setText(textMessage);
            Transport.send(message);

            Log.println(Log.INFO, "emailNotification", "Done");
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
//        } finally {
            //I have this here just to remember
//            //Sessions just manages configuration information, there is no need to close it.
//        }
        }
    }
}
