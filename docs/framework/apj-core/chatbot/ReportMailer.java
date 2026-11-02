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
package chatbot;

import java.net.URLEncoder;
import java.util.Properties;
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

public class ReportMailer {
    public static void sendReportEmail(final String from, final String password, String to, String iaResp, String chartConfig) throws Exception {
        String host = "smtp.gmail.com";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance((Properties)props, (Authenticator)new Authenticator(){

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        String chartUrl = "https://quickchart.io/chart?c=" + URLEncoder.encode(chartConfig, "UTF-8");
        String htmlContent = "<p>" + iaResp + "</p><p><b>Chart:</b></p><img src='" + chartUrl + "' alt='Chart'/>";
        MimeMessage message = new MimeMessage(session);
        message.setFrom((Address)new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, (Address[])InternetAddress.parse((String)to));
        message.setSubject("Async Reports");
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent((Object)htmlContent, "text/html; charset=utf-8");
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart((BodyPart)htmlPart);
        message.setContent((Multipart)multipart);
        Transport.send((Message)message);
    }
}

