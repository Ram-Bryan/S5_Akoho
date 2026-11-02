/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.mail.Address
 *  javax.mail.Authenticator
 *  javax.mail.BodyPart
 *  javax.mail.Message
 *  javax.mail.Message$RecipientType
 *  javax.mail.Multipart
 *  javax.mail.PasswordAuthentication
 *  javax.mail.Session
 *  javax.mail.Transport
 *  javax.mail.internet.InternetAddress
 *  javax.mail.internet.MimeBodyPart
 *  javax.mail.internet.MimeMessage
 *  javax.mail.internet.MimeMultipart
 */
package utilitaire;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EnvoiMailService {
    public static void envoiMail(String destinataire, String envoyeur, String objetMail, String textMail, String utilisateur, String mdp) throws Exception {
        try {
            String smtpHost = "192.168.1.104";
            String from = envoyeur;
            String to = destinataire;
            String username = utilisateur;
            String password = mdp;
            Properties props = new Properties();
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance((Properties)props);
            session.setDebug(true);
            MimeMessage message = new MimeMessage(session);
            message.setFrom((Address)new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, (Address)new InternetAddress(to));
            message.setSubject(objetMail);
            message.setText(textMail);
            Transport tr = session.getTransport("smtp");
            tr.connect(smtpHost, 25, username, password);
            message.saveChanges();
            tr.sendMessage((Message)message, message.getAllRecipients());
            tr.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public static void envoiMailAvecPieceJointe(String destinataire, String envoyeur, String objetMail, String textMail, String[] pieces, String cheminServeur, String utilisateur, String mdp) throws Exception {
        try {
            String smtpHost = "192.168.1.104";
            String from = envoyeur;
            String to = destinataire;
            final String username = utilisateur;
            final String password = mdp;
            Properties props = new Properties();
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.auth", "true");
            Session session = Session.getInstance((Properties)props, (Authenticator)new Authenticator(){

                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            session.setDebug(true);
            MimeMessage message = new MimeMessage(session);
            message.setFrom((Address)new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, (Address)new InternetAddress(to));
            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart partieMessage = new MimeBodyPart();
            partieMessage.setText(textMail);
            multipart.addBodyPart((BodyPart)partieMessage);
            if (pieces != null && pieces.length > 0) {
                for (int i = 0; i < pieces.length; ++i) {
                    partieMessage = new MimeBodyPart();
                    FileDataSource source = new FileDataSource(cheminServeur + "/" + pieces[i]);
                    partieMessage.setDataHandler(new DataHandler(source));
                    partieMessage.setFileName(pieces[i].replaceAll(".*/", ""));
                    multipart.addBodyPart((BodyPart)partieMessage);
                }
            }
            message.setSubject(objetMail);
            message.setContent((Multipart)multipart);
            Transport.send((Message)message);
            message.saveChanges();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}

