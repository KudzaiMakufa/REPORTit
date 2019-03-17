package com.simbaeducation.reportIt.Registration;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;
import java.util.logging.Level;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail extends AsyncTask<String, String, Void> {

    @Override
    protected Void doInBackground(String... strings) {

        Properties props = new java.util.Properties();
        props.put("mail.smtp.host", "yourHost");
        props.put("mail.smtp.port", "yourHostPort");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");


        // Session session = Session.getDefaultInstance(props, null);
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("kidkudzy@gmail.com", "laplazean");
                    }
                });


        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress("kidkudzy@gmail.com"));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress("pmakufa@ttcsglobal.com"));
            msg.setSubject("your subject");

            Multipart multipart = new MimeMultipart();

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("your text");

            MimeBodyPart attachmentBodyPart= new MimeBodyPart();
            DataSource source = new FileDataSource("Report.pdf"); // ex : "C:\\test.pdf"
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName("Report.pdf"); // ex : "test.pdf"

            multipart.addBodyPart(textBodyPart);  // add the text part
            multipart.addBodyPart(attachmentBodyPart); // add the attachement part

            msg.setContent(multipart);


            Transport.send(msg);
        } catch (MessagingException e) {
            Log.d("message","Error while sending email \n"+e);
        }
        return null;
    }
}