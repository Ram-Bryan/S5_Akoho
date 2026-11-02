/*
 * Decompiled with CFR 0.152.
 */
package chatbot;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TwilioSender {
    public static void sendSms(String ACCOUNT_SID, String AUTH_TOKEN, String to, String body) throws Exception {
        String urlStr = "https://api.twilio.com/2010-04-01/Accounts/" + ACCOUNT_SID + "/Messages.json";
        URL url = new URL(urlStr);
        String authStr = ACCOUNT_SID + ":" + AUTH_TOKEN;
        String base64Auth = Base64.getEncoder().encodeToString(authStr.getBytes(StandardCharsets.UTF_8));
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Basic " + base64Auth);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        String data = "To=" + URLEncoder.encode("whatsapp:+261340530020", "UTF-8") + "&From=" + URLEncoder.encode("whatsapp:+14155238886", "UTF-8") + "&Body=" + URLEncoder.encode(body, "UTF-8");
        try (OutputStream os = conn.getOutputStream();){
            os.write(data.getBytes(StandardCharsets.UTF_8));
        }
        int responseCode = conn.getResponseCode();
        System.out.println("Twilio API Response Code: " + responseCode);
        if (responseCode >= 200 && responseCode < 300) {
            System.out.println("Message sent successfully!");
        } else {
            System.out.println("Error response from Twilio API.");
        }
    }
}

