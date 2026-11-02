/*
 * Decompiled with CFR 0.152.
 */
package chatbot;

import bean.ClassMAPTable;
import bean.Constante;
import chatbot.AiColDesc;
import chatbot.AiTabDesc;
import chatbot.ClassIA;
import java.sql.Connection;
import java.sql.Date;
import utilitaire.Utilitaire;

@AiTabDesc(value="If the user wants to know about user metrics like token usage and cost and latency or even agent usage, this is the right table.")
public class AiAgentCall
extends ClassMAPTable
implements ClassIA {
    private String id;
    private String userId;
    private String sessionId;
    @AiColDesc(value="The agent that executed the prompt")
    private String agent;
    @AiColDesc(value="The approximate number of tokens for the prompt sent")
    private int tokensIn;
    @AiColDesc(value="The approximate number of tokens for your response")
    private int tokensOut;
    @AiColDesc(value="The total cost of the call")
    private double costMga;
    private String success;
    @AiColDesc(value="The latency in Ms for the response")
    private int latencyMs;
    private Date createdAt;

    @Override
    public String getNomTableIA() {
        return "ai_agent_call";
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

    public AiAgentCall() {
        super.setNomTable("ai_agent_call");
    }

    public AiAgentCall(String userId, String sessionId, String agent, int tokensIn, int tokensOut, int latencyMs) {
        super.setNomTable("ai_agent_call");
        this.setUserId(userId);
        this.setSessionId(sessionId);
        this.setAgent(agent);
        this.setTokensIn(tokensIn);
        this.setTokensOut(tokensOut);
        this.setLatencyMs(latencyMs);
        this.setCreatedAt(Utilitaire.dateDuJourSql());
    }

    public static double calculateCostUSD(int tokensIn, int tokensOut) {
        double costIn = (double)tokensIn * Constante.GeminicostIn;
        double costOut = (double)tokensOut * Constante.GeminicostOut;
        return costIn + costOut;
    }

    public void calculateCostMGA(double usdToMgaRate) {
        double c = AiAgentCall.calculateCostUSD(this.getTokensIn(), this.getTokensOut()) * usdToMgaRate;
        this.setCostMga(c);
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
        this.preparePk("AIC", "get_seq_ai_agent_call");
        this.setId(this.makePK(c));
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getAgent() {
        return this.agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public int getTokensIn() {
        return this.tokensIn;
    }

    public void setTokensIn(int tokensIn) {
        this.tokensIn = tokensIn;
    }

    public int getTokensOut() {
        return this.tokensOut;
    }

    public void setTokensOut(int tokensOut) {
        this.tokensOut = tokensOut;
    }

    public double getCostMga() {
        return this.costMga;
    }

    public void setCostMga(double costMga) {
        this.costMga = costMga;
    }

    public String getSuccess() {
        return this.success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public int getLatencyMs() {
        return this.latencyMs;
    }

    public void setLatencyMs(int latencyMs) {
        this.latencyMs = latencyMs;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

