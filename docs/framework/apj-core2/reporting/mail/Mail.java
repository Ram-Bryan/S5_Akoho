/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.mail.Address
 *  javax.mail.Authenticator
 *  javax.mail.BodyPart
 *  javax.mail.Message
 *  javax.mail.Message$RecipientType
 *  javax.mail.MessagingException
 *  javax.mail.Multipart
 *  javax.mail.PasswordAuthentication
 *  javax.mail.Session
 *  javax.mail.Transport
 *  javax.mail.internet.InternetAddress
 *  javax.mail.internet.MimeBodyPart
 *  javax.mail.internet.MimeMessage
 *  javax.mail.internet.MimeMultipart
 */
package reporting.mail;

import java.io.File;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail {
    String sender;
    String password;
    String to;
    String[] cc;
    String subject;
    String message;
    String[] files;

    public void setSender(String from) throws Exception {
        if (from == null || from.isEmpty()) {
            throw new Exception("Sender obligatoire");
        }
        this.sender = from;
    }

    public String getSender() {
        return this.sender;
    }

    public void setPassword(String password) throws Exception {
        if (password == null || password.isEmpty()) {
            throw new Exception("Mot de passe obligatoire");
        }
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setTo(String to) throws Exception {
        if (to == null || to.isEmpty()) {
            throw new Exception("Destinataire obligatoire");
        }
        this.to = to;
    }

    public String getTo() {
        return this.to;
    }

    public void setCc(String[] cc) throws Exception {
        this.cc = cc;
    }

    public String[] getCc() {
        return this.cc;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String[] getFiles() {
        return this.files;
    }

    public void setFiles(String[] files) {
        this.files = files;
    }

    public Mail() {
    }

    public Mail(String sender, String password, String subject, String message) throws Exception {
        this.setSender(sender);
        this.setPassword(password);
        this.setSubject(subject);
        this.setMessage(message);
    }

    public Mail(String sender, String password, String to, String[] cc, String subject, String message) throws Exception {
        this.setSender(sender);
        this.setPassword(password);
        this.setTo(to);
        this.setCc(cc);
        this.setSubject(subject);
        this.setMessage(message);
    }

    public Mail(String sender, String password, String to, String subject, String message) throws Exception {
        this.setSender(sender);
        this.setPassword(password);
        this.setTo(to);
        this.setSubject(subject);
        this.setMessage(message);
    }

    public String cleanMessage(String message) {
        String htmlContent = message.replace("\u00c3\u00a9", "&eacute;").replace("\u00c3\u00a8", "&egrave;").replace("\u00c3\u00a0", "&agrave;").replace("\u00c2\u00b0", "&ordm;");
        return htmlContent;
    }

    public void send() throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.googlemail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        final String from = this.getSender();
        final String mdp = this.getPassword();
        Session session = Session.getInstance((Properties)props, (Authenticator)new Authenticator(){

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, mdp);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, (Address)new InternetAddress(this.getTo()));
            if (this.getCc() != null) {
                InternetAddress[] tabCC = new InternetAddress[this.getCc().length];
                for (int i = 0; i < tabCC.length; ++i) {
                    tabCC[i] = new InternetAddress(this.getCc()[i]);
                }
                message.addRecipients(Message.RecipientType.CC, (Address[])tabCC);
            }
            message.setSubject(this.getSubject());
            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            this.setMessage(this.cleanMessage(this.getMessage()));
            messageBodyPart.setContent((Object)this.getMessage(), "text/html; charset=UTF-8");
            multipart.addBodyPart((BodyPart)messageBodyPart);
            if (this.getFiles() != null && this.getFiles().length != 0) {
                for (int i = 0; i < this.getFiles().length; ++i) {
                    MimeBodyPart attachPart = new MimeBodyPart();
                    attachPart.attachFile(new File(this.getFiles()[i]));
                    multipart.addBodyPart((BodyPart)attachPart);
                }
            }
            message.setContent((Multipart)multipart);
            Transport.send((Message)message);
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

