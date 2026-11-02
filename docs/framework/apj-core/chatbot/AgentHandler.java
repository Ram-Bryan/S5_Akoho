/*
 * Decompiled with CFR 0.152.
 */
package chatbot;

import bean.Champ;
import bean.ListeColonneTable;
import chatbot.AiColDesc;
import chatbot.AiTabDesc;
import chatbot.ClassIA;
import chatbot.PromptLoader;
import chatbot.UtilChatbot;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Vector;

public class AgentHandler {
    public static String dateToday = "Aujourd'hui on est le " + LocalDateTime.now();

    public static String dispatchAgent(String query, String type, String bdd, Class<? extends ClassIA>[] tables, Connection c, String context, String summary) throws Exception {
        switch (type) {
            case "liste": {
                return dateToday + ",context: " + context + "\n" + AgentHandler.generateQueryDB(query, bdd, tables, c) + "\n what you've learnt in the past:" + summary;
            }
            case "analyse": {
                return dateToday + ",context: " + context + "\n" + AgentHandler.generateQueryDB(query, bdd, tables, c) + "\n what you've learnt in the past:" + summary;
            }
            case "saisie": {
                return dateToday + ",context: " + context + "\n" + AgentHandler.generateQuerySaisie(query, tables, c) + "\n what you've learnt in the past:" + summary;
            }
            case "lister": {
                return dateToday + ",context: " + context + "\n" + AgentHandler.generateQueryLister(query, bdd, tables, c) + "\n what you've learnt in the past:" + summary;
            }
            case "prevision": {
                return dateToday + ",context: " + context + "\n" + AgentHandler.generateQueryPrevi(query) + "\n what you've learnt in the past:" + summary;
            }
            case "dev": {
                return dateToday + ",context: " + context + "\n" + AgentHandler.generateQueryDeveloppeur(query, tables, c) + "\n what you've learnt in the past:" + summary;
            }
            case "compta": {
                return dateToday + ",context: " + context + "\n" + AgentHandler.generateQueryCompta(query, bdd) + "\n what you've learnt in the past:" + summary;
            }
            case "decisionnel": {
                return dateToday + ",context: " + context + "\n" + AgentHandler.generateQueryDecisionnel(query, bdd) + "\n what you've learnt in the past:" + summary;
            }
            case "analytics": {
                return dateToday + ",context: " + context + "\n" + AgentHandler.generateQueryAnalytics(query, tables, c) + "\n what you've learnt in the past:" + summary;
            }
        }
        return null;
    }

    public static String dispatchAgentResponse(String type, String data, String prompt, String query, String context, String summary) throws Exception {
        switch (type) {
            case "compta": {
                return dateToday + ",context: " + context + "\n" + AgentHandler.generateComptaResponse(data, prompt, query) + "\n what you've learnt in the past:" + summary;
            }
            case "decisionnel": {
                return dateToday + ",context: " + context + "\n" + AgentHandler.generateDecisionnelResponse(data, prompt, query) + "\n what you've learnt in the past:" + summary;
            }
            case "analyse": {
                return "Context: " + context + "\n" + AgentHandler.generateAnalytics(data, prompt, query) + "\n what you've learnt in the past:" + summary;
            }
            case "liste": {
                return "Context: " + context + "\n" + AgentHandler.generateAnalytics(data, prompt, query) + "\n what you've learnt in the past:" + summary;
            }
        }
        return dateToday + ",context: " + context + "\n" + AgentHandler.generateDefaultResponse(data, prompt, query) + "\n what you've learnt in the past:" + summary;
    }

    public static String generateDefaultResponse(String data, String prompt, String query) throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("prompt", prompt);
        params.put("data", data);
        params.put("query", query);
        return PromptLoader.format("default_response", params);
    }

    public static String generateComptaResponse(String data, String prompt, String query) throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("prompt", prompt);
        params.put("data", data);
        params.put("query", query);
        return PromptLoader.format("compta_response", params);
    }

    public static String generateDecisionnelResponse(String data, String prompt, String query) throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("prompt", prompt);
        params.put("data", data);
        params.put("query", query);
        return PromptLoader.format("decisionnel_response", params);
    }

    public static String generateAnalytics(String data, String prompt, String query) throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("prompt", prompt);
        params.put("data", data);
        params.put("query", query);
        return PromptLoader.format("analytics", params);
    }

    public static String generateQueryType(String prompt, String appContext, String definitions) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("prompt", prompt);
        params.put("appContext", appContext);
        params.put("definitions", definitions);
        return PromptLoader.format("query_type", params);
    }

    public static String generateQueryLog(String prompt) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("prompt", prompt);
        return PromptLoader.format("query_log", params);
    }

    public static String summarize(String history) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("history", history);
        return PromptLoader.format("summary", params);
    }

    public static String generateQueryPrevi(String prompt) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("prompt", prompt);
        return PromptLoader.format("query_previ", params);
    }

    public static String generateQueryDeveloppeur(String query, Class<? extends ClassIA>[] tables, Connection c) throws Exception {
        return AgentHandler.generateJavaCode(query) + " " + AgentHandler.generateJSPPage(query);
    }

    public static String generateQueryCompta(String prompt, String bdd) throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("prompt", prompt);
        params.put("bdd", bdd);
        return PromptLoader.format("query_compta", params);
    }

    public static String generateQueryDecisionnel(String prompt, String bdd) throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("prompt", prompt);
        params.put("bdd", bdd);
        return PromptLoader.format("query_decisionnel", params);
    }

    public static String generateQueryAnalytics(String query, Class<? extends ClassIA>[] tables, Connection c) throws Exception {
        return "";
    }

    public static String generateJavaCode(String prompt) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("besoin", prompt);
        return PromptLoader.format("java_code", params);
    }

    public static String generateJSPPage(String besoin) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("besoin", besoin);
        return PromptLoader.format("jsp_page", params);
    }

    public static String generateQueryDB(String prompt, String bdd, Class<? extends ClassIA>[] tables, Connection c) throws Exception {
        String tableString = AgentHandler.getTables(bdd, tables, c);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("prompt", prompt);
        params.put("bdd", bdd);
        params.put("tables", tableString);
        return PromptLoader.format("query_db", params);
    }

    public static String generateQuerySaisie(String prompt, Class<? extends ClassIA>[] tables, Connection c) throws Exception {
        String tableString = AgentHandler.getTablesSaisie(tables, c);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("prompt", prompt);
        params.put("tables", tableString);
        return PromptLoader.format("query_saisie", params);
    }

    public static String generateQueryLister(String prompt, String bdd, Class<? extends ClassIA>[] tables, Connection c) throws Exception {
        String tableString = AgentHandler.getTables(bdd, tables, c);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("prompt", prompt);
        params.put("tables", tableString);
        return PromptLoader.format("query_lister", params);
    }

    private static String getTables(String bdd, Class<? extends ClassIA>[] tables, Connection c) throws Exception {
        StringBuilder tablesString = new StringBuilder();
        for (Class<? extends ClassIA> tableClass : tables) {
            ClassIA table = tableClass.newInstance();
            Vector champs = ListeColonneTable.getChampFromTable(table.getNomTableIA(), c);
            tablesString.append("Nom table: ").append(table.getNomTableIA()).append(" => ");
            if (table.getClass().isAnnotationPresent(AiTabDesc.class)) {
                String description = table.getClass().getAnnotation(AiTabDesc.class).value();
                tablesString.append(description);
            }
            for (Champ champ : champs) {
                Field field = UtilChatbot.getDeclaredFieldIgnoreCase(table.getClass(), champ.getNomColonne());
                if (field == null) continue;
                field.setAccessible(true);
                tablesString.append(" ");
                tablesString.append(champ.getNomColonne()).append(" ").append(champ.getTypeColonne().toLowerCase());
                if (!field.isAnnotationPresent(AiColDesc.class)) continue;
                String description = field.getAnnotation(AiColDesc.class).value();
                tablesString.append(": ");
                tablesString.append(description);
            }
            tablesString.append("Utilise les id_lib si tu veux chercher les noms, par example noms de produits : idproduitlib, noms des magasins : idmagasinlib, etc etc.\nTu ne peux pas utiliser d'autres colonnes que ce que je t'ai donner car je vais mapper la requete a un objet deja defini donc pas d'alias mais met dans les colonnes existant sauf ID la reponse des sommes, groupes, counts, etc.\nPas de FETCH FIRST ROWS ONLY, tu ne peux en aucun cas l'utiliser\nBase de donnee: " + bdd + ";");
            tablesString.append("\n");
        }
        System.out.println(tablesString.toString());
        return tablesString.toString();
    }

    private static String getTablesSaisie(Class<? extends ClassIA>[] tables, Connection c) throws Exception {
        StringBuilder tablesString = new StringBuilder();
        for (Class<? extends ClassIA> tableClass : tables) {
            ClassIA table = tableClass.newInstance();
            Vector champs = ListeColonneTable.getChampFromTable(table.getNomTableIA(), c);
            tablesString.append("Nom table: ").append(table.getNomTableIA()).append(" => ");
            if (table.getClass().isAnnotationPresent(AiTabDesc.class)) {
                String description = table.getClass().getAnnotation(AiTabDesc.class).value();
                tablesString.append(description);
            }
            for (Champ champ : champs) {
                Field field = UtilChatbot.getDeclaredFieldIgnoreCase(table.getClass(), champ.getNomColonne());
                if (field == null) continue;
                field.setAccessible(true);
                tablesString.append(" ");
                tablesString.append(champ.getNomColonne()).append(" ").append(champ.getTypeColonne().toLowerCase());
                if (!field.isAnnotationPresent(AiColDesc.class)) continue;
                String description = field.getAnnotation(AiColDesc.class).value();
                tablesString.append(": ");
                tablesString.append(description);
            }
            tablesString.append(table.getSaisiePromptCompletion(c));
            tablesString.append(" | ");
        }
        tablesString.append("\nTu ne peux pas utiliser d'autres colonnes que ce que je t'ai donner car je vais mapper la requete a un objet deja defini donc pas d'alias mais met dans les colonnes existant sauf ID la reponse des sommes, groupes, counts, etc.\n");
        System.out.println(tablesString.toString());
        return tablesString.toString();
    }
}

