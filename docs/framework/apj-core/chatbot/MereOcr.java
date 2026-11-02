/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonSyntaxException
 */
package chatbot;

import chatbot.FilleOcr;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MereOcr {
    String daty;
    String idOrigine;
    String idMagasin;
    String designation;
    List<FilleOcr> details;
    String type;
    double montant;
    String beneficiaire;
    String typeDepense;

    public static MereOcr extractOcr(String jsonResponse) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(jsonResponse).getAsJsonArray();
        for (JsonElement element : array) {
            JsonObject obj = element.getAsJsonObject();
            JsonObject content = obj.getAsJsonObject("content");
            JsonArray parts = content.getAsJsonArray("parts");
            for (JsonElement part : parts) {
                JsonObject partObj = part.getAsJsonObject();
                if (!partObj.has("text")) continue;
                String text = partObj.get("text").getAsString();
                String json = MereOcr.extractJsonFromMarkdown(text);
                if (json == null) {
                    json = text;
                }
                try {
                    return (MereOcr)gson.fromJson(json, MereOcr.class);
                }
                catch (JsonSyntaxException e) {
                    System.err.println("Invalid JSON: " + json);
                    e.printStackTrace();
                }
            }
        }
        throw new IllegalArgumentException("No valid JSON found in response.");
    }

    private static String extractJsonFromMarkdown(String markdownText) {
        Pattern pattern = Pattern.compile("```json\\s*(\\{.*?})\\s*```", 32);
        Matcher matcher = pattern.matcher(markdownText);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public String getDaty() {
        return this.daty;
    }

    public void setDaty(String daty) {
        this.daty = daty;
    }

    public String getIdOrigine() {
        return this.idOrigine;
    }

    public void setIdOrigine(String idOrigine) {
        this.idOrigine = idOrigine;
    }

    public String getIdMagasin() {
        return this.idMagasin;
    }

    public void setIdMagasin(String idMagasin) {
        this.idMagasin = idMagasin;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<FilleOcr> getDetails() {
        return this.details;
    }

    public void setDetails(List<FilleOcr> details) {
        this.details = details;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMontant() {
        return this.montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getBeneficiaire() {
        return this.beneficiaire;
    }

    public void setBeneficiaire(String beneficiaire) {
        this.beneficiaire = beneficiaire;
    }

    public String getTypeDepense() {
        return this.typeDepense;
    }

    public void setTypeDepense(String typeDepense) {
        this.typeDepense = typeDepense;
    }
}

