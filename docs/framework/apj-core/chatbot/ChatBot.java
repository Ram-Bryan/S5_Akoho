/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package chatbot;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatBot {
    private String value;
    private String url;

    public ChatBot(String value, String url) {
        this.setValue(value);
        this.setUrl(url);
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private static String extractRelevantResponseChatGPT(String jsonResponse) {
        int index = jsonResponse.indexOf("\"content\":");
        if (index != -1) {
            int start = jsonResponse.indexOf("\"", index + 10) + 1;
            int end = jsonResponse.indexOf("\"", start);
            return jsonResponse.substring(start, end);
        }
        return jsonResponse;
    }

    public static String removeAllDoubleQuotes(String json) {
        return json.replace("\"", "");
    }

    public static void triggerSessionEndpoint(String apiUrl) throws IOException {
        String urlStr = apiUrl + "/apps/asyncAgent/users/u_123/sessions/s_123";
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(false);
        int responseCode = conn.getResponseCode();
        System.out.println("POST request sent. Response code: " + responseCode);
        conn.disconnect();
    }

    public static void sendMessage(String apiUrl, String message, String phone) throws IOException, JSONException {
        String responseLine;
        URL url = new URL(apiUrl);
        System.out.println("eto 1 " + apiUrl);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        System.out.println("eto 2 " + apiUrl);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        JSONObject json = new JSONObject();
        json.put("message", (Object)message);
        json.put("phone", (Object)phone);
        String jsonBody = json.toString();
        System.out.println("eto 3 " + apiUrl);
        try (OutputStream os = conn.getOutputStream();){
            byte[] input = jsonBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        System.out.println("eto 4 " + apiUrl);
        int responseCode = conn.getResponseCode();
        System.out.println("POST request sent. Response code: " + responseCode);
        BufferedReader br = responseCode >= 200 && responseCode < 300 ? new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8")) : new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }
        System.out.println("Response: " + response.toString());
        conn.disconnect();
    }

    /*
     * Loose catch block
     */
    public static String sendMessageWithFile(String prompt, File file, String apiUrl) throws Exception {
        String base64Data = ChatBot.encodeFileToBase64(file);
        String requestBody = String.format("{\n  \"app_name\": \"asyncAgent\",\n  \"user_id\": \"u_123\",\n  \"session_id\": \"s_123\",\n  \"new_message\": {\n    \"role\": \"user\",\n    \"parts\": [\n      {\"text\": \"%s\"},\n      {\n        \"inline_data\": {\n          \"mime_type\": \"image/png\",\n          \"data\": \"%s\"\n        }\n      }\n    ]\n  }\n}", ChatBot.escapeJson(prompt), base64Data);
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream();){
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        try {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(responseCode >= 200 && responseCode < 300 ? connection.getInputStream() : connection.getErrorStream(), StandardCharsets.UTF_8));){
                String responseLine;
                StringBuilder response = new StringBuilder();
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                String jsonResponse = response.toString();
                System.out.println("Raw Response: " + jsonResponse);
                String string = jsonResponse;
                return string;
            }
            {
                catch (Throwable throwable) {
                    throw throwable;
                }
            }
        }
        finally {
            connection.disconnect();
        }
    }

    private static String encodeFileToBase64(File file) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (FileInputStream is = new FileInputStream(file);){
            int nRead;
            byte[] data = new byte[8192];
            while ((nRead = ((InputStream)is).read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
        }
        byte[] fileBytes = buffer.toByteArray();
        return Base64.getEncoder().encodeToString(fileBytes);
    }

    private static String escapeJson(String text) {
        return text.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String callGenerativeAI(String prompt, String apiUrl, String apiKey) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("X-goog-api-key", apiKey);
        connection.setDoOutput(true);
        String requestBody = String.format("{\n  \"contents\": [{\n    \"parts\":[{\"text\": \"%s\" }]\n  }]\n}", prompt);
        System.out.println("ay " + requestBody);
        try {
            OutputStream os = connection.getOutputStream();
            Throwable throwable = null;
            try {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                int responseCode = connection.getResponseCode();
                System.out.println("Response Code: " + responseCode);
                if (responseCode == 200) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));){
                        String responseLine;
                        StringBuilder response = new StringBuilder();
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        String jsonResponse = response.toString();
                        String string = ChatBot.extractRelevantResponse(jsonResponse);
                        return string;
                    }
                }
                System.out.println("Error: " + connection.getResponseMessage());
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            Throwable throwable2 = null;
            try {
                String errorLine;
                while ((errorLine = br.readLine()) != null) {
                    System.err.println("Error Details: " + errorLine);
                }
                return null;
            }
            catch (Throwable throwable3) {
                throwable2 = throwable3;
                throw throwable3;
            }
            finally {
                if (os != null) {
                    if (throwable != null) {
                        try {
                            os.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    } else {
                        os.close();
                    }
                }
            }
        }
        finally {
            connection.disconnect();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String callGenerativeAI(String prompt, String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        String requestBody = String.format("{\n  \"contents\": [{\n    \"parts\":[{\"text\": \"%s\" }]\n  }]\n}", prompt);
        System.out.println("ay " + requestBody);
        try {
            OutputStream os = connection.getOutputStream();
            Throwable throwable = null;
            try {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                int responseCode = connection.getResponseCode();
                System.out.println("Response Code: " + responseCode);
                if (responseCode == 200) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));){
                        String responseLine;
                        StringBuilder response = new StringBuilder();
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        String jsonResponse = response.toString();
                        String string = ChatBot.extractRelevantResponse(jsonResponse);
                        return string;
                    }
                }
                System.out.println("Error: " + connection.getResponseMessage());
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            Throwable throwable2 = null;
            try {
                String errorLine;
                while ((errorLine = br.readLine()) != null) {
                    System.err.println("Error Details: " + errorLine);
                }
                return null;
            }
            catch (Throwable throwable3) {
                throwable2 = throwable3;
                throw throwable3;
            }
            finally {
                if (os != null) {
                    if (throwable != null) {
                        try {
                            os.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    } else {
                        os.close();
                    }
                }
            }
        }
        finally {
            connection.disconnect();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String callGenerativeAILocal(String prompt, String apiUrl) throws Exception {
        String ollamaUrl = "http://localhost:11434/api/generate";
        try {
            HttpURLConnection con = (HttpURLConnection)new URL(ollamaUrl).openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            String jsonInput = "{\"model\": \"phi3:3.8b\", \"prompt\": \"" + ChatBot.escapeJson(prompt) + "\", \"stream\": false}";
            try (OutputStream os = con.getOutputStream();){
                os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
            }
            int responseCode = con.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Ollama error: " + responseCode);
                throw new Exception("Ollama failed");
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));){
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
                String string = ChatBot.extractRelevantResponse(response.toString());
                return string;
            }
        }
        catch (Exception e) {
            System.out.println("Fallback to Gemini: " + e.getMessage());
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            String requestBody = String.format("{\"contents\": [{\"parts\": [{\"text\": \"%s\"}]}]}", ChatBot.escapeJson(prompt));
            try (OutputStream os = connection.getOutputStream();){
                os.write(requestBody.getBytes(StandardCharsets.UTF_8));
            }
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) throw new Exception("Gemini error: " + responseCode);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));){
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
                String string = ChatBot.extractRelevantResponse(response.toString());
                return string;
            }
        }
    }

    private static String extractRelevantResponse(String jsonResponse) {
        try {
            JsonObject firstCandidate;
            JsonObject content;
            JsonArray parts;
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(jsonResponse).getAsJsonObject();
            JsonArray candidates = jsonObject.getAsJsonArray("candidates");
            if (candidates != null && candidates.size() > 0 && (parts = (content = (firstCandidate = candidates.get(0).getAsJsonObject()).getAsJsonObject("content")).getAsJsonArray("parts")) != null && parts.size() > 0) {
                String text = parts.get(0).getAsJsonObject().get("text").getAsString();
                String cleanedJson2 = text.replaceAll("```json", "").replaceAll("```", "").trim();
                String cleanedJson = cleanedJson2.replaceAll("dictionnaire = ", "").trim();
                cleanedJson = cleanedJson.replaceAll("'(\\w+)':", "\"$1\":");
                cleanedJson = cleanedJson.replaceAll(": '(.*?)'", ": \"$1\"");
                return cleanedJson;
            }
        }
        catch (Exception e) {
            System.err.println("Error parsing response JSON: " + e.getMessage());
        }
        return null;
    }
}

