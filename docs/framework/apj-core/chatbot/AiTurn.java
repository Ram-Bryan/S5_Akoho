/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package chatbot;

import bean.CGenUtil;
import bean.ClassMAPTable;
import chatbot.AiColDesc;
import chatbot.AiTabDesc;
import chatbot.ClassIA;
import chatbot.UtilChatbot;
import java.sql.Connection;
import java.sql.Date;
import org.json.JSONObject;
import utilitaire.Utilitaire;

@AiTabDesc(value="This table is your entire chat history with all users across multiple sessions.")
public class AiTurn
extends ClassMAPTable
implements ClassIA {
    String id;
    @AiColDesc(value="The session that was used during this prompt")
    String sessionId;
    @AiColDesc(value="The role that sent the content, can either be bot aka you or user")
    String role;
    String content;
    int tokensPrompt;
    int tokensCompletion;
    @AiColDesc(value="The agent that executed the prompt")
    String agent;
    int latencyMs;
    Date dateCreatedAt;
    String timeCreatedAt;

    @Override
    public String getNomTableIA() {
        return "ai_turn";
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

    public AiTurn() {
        super.setNomTable("ai_turn");
    }

    public AiTurn(String sessionId, String role, String content, int tokensPrompt, int tokensCompletion, String agent, int latencyMs) {
        super.setNomTable("ai_turn");
        this.setSessionId(sessionId);
        this.setRole(role);
        this.setContent(content);
        this.setTokensPrompt(tokensPrompt);
        this.setTokensCompletion(tokensCompletion);
        this.setAgent(agent);
        this.setLatencyMs(latencyMs);
        this.setDateCreatedAt(Utilitaire.dateDuJourSql());
        this.setTimeCreatedAt(Utilitaire.heureCourante());
    }

    public static String context(String sessionId, int len) throws Exception {
        AiTurn turn = new AiTurn();
        turn.setSessionId(sessionId);
        AiTurn[] turns = (AiTurn[])CGenUtil.rechercher((ClassMAPTable)turn, null, null, " order by dateCreatedAt,timeCreatedAt desc");
        StringBuilder allText = new StringBuilder();
        allText.append("Here is the context:").append("\n");
        if (turns.length > 0) {
            for (int i = 0; i < len; ++i) {
                AiTurn turn1 = turns[i];
                allText.append(turn1.toString()).append("\n");
            }
        }
        return allText.toString();
    }

    public static String history(String sessionId) throws Exception {
        AiTurn turn = new AiTurn();
        turn.setSessionId(sessionId);
        AiTurn[] turns = (AiTurn[])CGenUtil.rechercher((ClassMAPTable)turn, null, null, " order by dateCreatedAt,timeCreatedAt");
        StringBuilder allText = new StringBuilder();
        for (AiTurn turn1 : turns) {
            allText.append(turn1.history()).append("\n");
        }
        return allText.toString();
    }

    public String toString() {
        return "Content=" + UtilChatbot.escapeQuotes(this.content) + " sent by " + this.role + ", using agent=" + this.agent + ", latency=" + this.latencyMs + "ms, tokensIn=" + this.tokensPrompt + ", tokensOut=" + this.tokensCompletion;
    }

    public String history() {
        return "Content=" + UtilChatbot.escapeQuotes(this.content) + " sent by " + this.role + ", using agent=" + this.agent;
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("AIT", "get_seq_ai_turn");
        this.setId(this.makePK(c));
    }

    @Override
    public String getTuppleID() {
        return this.id;
    }

    @Override
    public String getAttributIDName() {
        return "id";
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

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        if (content != null && content.length() > 4000) {
            try {
                JSONObject json = new JSONObject(content);
                String iaResp = json.getString("iaResp");
                JSONObject reduced = new JSONObject();
                reduced.put("iaResp", (Object)iaResp);
                this.content = reduced.toString();
            }
            catch (Exception e) {
                this.content = content.substring(0, 3999);
            }
        } else {
            this.content = content;
        }
    }

    public int getTokensPrompt() {
        return this.tokensPrompt;
    }

    public void setTokensPrompt(int tokensPrompt) {
        this.tokensPrompt = tokensPrompt;
    }

    public int getTokensCompletion() {
        return this.tokensCompletion;
    }

    public void setTokensCompletion(int tokensCompletion) {
        this.tokensCompletion = tokensCompletion;
    }

    public String getAgent() {
        return this.agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public int getLatencyMs() {
        return this.latencyMs;
    }

    public void setLatencyMs(int latencyMs) {
        this.latencyMs = latencyMs;
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

