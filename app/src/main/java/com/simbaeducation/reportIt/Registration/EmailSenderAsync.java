package com.simbaeducation.reportIt.Registration;


import android.os.AsyncTask;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;

import java.net.URL;

public class EmailSenderAsync extends AsyncTask<String, Void, Boolean> {

    private HtmlEmail email;

    @Override
    protected Boolean doInBackground(String[] params) {
        String textMsg;
        try {
            String userEmail = params[0];
            String message = params[1];
            String subject = params[2];
            /*String attachmentpath = params[3];

            EmailAttachment attachment = new EmailAttachment();
           // attachment.setURL(new URL("http://www.apache.org/images/asf_logo_wide.gif"));
            attachment.setPath(attachmentpath);
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            attachment.setDescription("Leave Request");
            attachment.setName("Leave Request");*/




            email = new HtmlEmail();



            email.setAuthenticator(new DefaultAuthenticator("kidkudzy@gmail.com", "laplazean"));

            email.setSmtpPort(587);

            email.setHostName("smtp.gmail.com");

            email.setDebug(true);

            email.addTo(userEmail, "kidkudzy@gmail.com");

            email.setFrom("kidkudzy@gmail.com", "Simba Education");

            email.setSubject(subject);

            email.getMailSession().getProperties().put("mail.smtps.auth", "true");

            email.getMailSession().getProperties().put("mail.debug", "true");

            email.getMailSession().getProperties().put("mail.smtps.port", "587");

            email.getMailSession().getProperties().put("mail.smtps.socketFactory.port", "587");

            email.getMailSession().getProperties().put("mail.smtps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            email.getMailSession().getProperties().put("mail.smtps.socketFactory.fallback", "false");

            email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");

            email.setTextMsg(message);

            //email.attach(attachment);

            email.send();


            return true;

        } catch (Exception e) {

            return false;
        }

    }
}