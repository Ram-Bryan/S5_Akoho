/*
 * Decompiled with CFR 0.152.
 */
package chatbot;

import bean.CGenUtil;
import bean.ClassMAPTable;
import chatbot.AiTabDesc;
import chatbot.ClassIA;
import chatbot.UtilChatbot;
import java.sql.Connection;
import java.sql.Date;
import utilitaire.Utilitaire;

@AiTabDesc(value="These are the summaries for all conversations you've had, summaries you can learn from.")
public class ChatSummary
extends ClassMAPTable
implements ClassIA {
    private String id;
    private String sessionId;
    private String userId;
    private String summaryText;
    private String keywords;
    private Date dateCreatedAt;
    private String timeCreatedAt;

    @Override
    public String getNomTableIA() {
        return "chat_summary";
    }

    @Override
    public ClassIA getClassSaisie() {
        return this;
    }

    @Override
    public ClassIA getClassListe() {
        return this;
    }

    @Override
    public ClassIA getClassAnalyse() {
        return this;
    }

    @Override
    public String getUrlSaisie() {
        return "";
    }

    @Override
    public String getUrlListe() {
        return "";
    }

    @Override
    public String getUrlAnalyse() {
        return "";
    }

    public ChatSummary() {
        super.setNomTable("chat_summary");
    }

    public ChatSummary(String sessionId, String userId, String summary, String keywords) {
        super.setNomTable("chat_summary");
        this.setSessionId(sessionId);
        this.setUserId(userId);
        this.setSummaryText(summary);
        this.setKeywords(keywords);
        this.setDateCreatedAt(Utilitaire.dateDuJourSql());
        this.setTimeCreatedAt(Utilitaire.heureCourante());
    }

    public static String summary() throws Exception {
        ChatSummary chatSummary = new ChatSummary();
        ChatSummary[] chatSummaries = (ChatSummary[])CGenUtil.rechercher((ClassMAPTable)chatSummary, null, null, "");
        StringBuilder allText = new StringBuilder();
        for (ChatSummary chatSummary1 : chatSummaries) {
            allText.append(chatSummary1.getSummaryText()).append("\n");
        }
        return UtilChatbot.escapeQuotes(allText.toString());
    }

    @Override
    public String getTuppleID() {
        return this.id;
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CHS", "get_seq_chat_summary");
        this.setId(this.makePK(c));
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSummaryText() {
        return this.summaryText;
    }

    public void setSummaryText(String summaryText) {
        this.summaryText = summaryText;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Date getDateCreatedAt() {
        return this.dateCreatedAt;
    }

    public void setDateCreatedAt(Date dateCreatedAt) {
        this.dateCreatedAt = dateCreatedAt;
    }

    public String getTimeCreatedAt() {
        return this.timeCreatedAt;
    }

    public void setTimeCreatedAt(String timeCreatedAt) {
        this.timeCreatedAt = timeCreatedAt;
    }
}

