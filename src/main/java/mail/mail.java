package mail;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class mail {
    final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    Properties props = System.getProperties();
    static final String user = "";
    static final String pass = "";

    public void envoyer(String Toemail, String Subject, String Object) {

        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.starttls.enable", "true"); // Use STARTTLS
        props.setProperty("mail.smtp.port", "587"); // Use port 587 for SMTP
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");

        // Configure SSL to true
        props.put("mail.smtp.ssl.enable", "true");

        try {
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {

                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(user, pass);
                        }

                    });
            // Create a new message
            Message msg = new MimeMessage(session);

            // Set the FROM and TO fields
            msg.setFrom(new InternetAddress(user));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(Toemail, false));
            msg.setSubject(Subject);
            msg.setText(Object);
            msg.setSentDate(new Date());
            Transport.send(msg);
            System.out.println("Message sent.");
        } catch (MessagingException e) {
            System.out.println("Error sending message: " + e);
        }
    }
}
