/*
 * Decompiled with CFR 0.152.
 */
package chatbot;

import bean.ClassMAPTable;
import java.sql.Connection;
import java.sql.Date;
import utilitaire.Utilitaire;

public class AiSession
extends ClassMAPTable {
    String id;
    String userId;
    String status;
    Date dateStartedAt;
    String timeStartedAt;
    String lastSeenAt;
    int ttlMinutes;
    String metadataJson;

    public AiSession() {
        super.setNomTable("ai_session");
    }

    public AiSession(String userId, int ttlMinutes) {
        super.setNomTable("ai_session");
        this.setUserId(userId);
        this.setStatus("ACTIVE");
        this.setDateStartedAt(Utilitaire.dateDuJourSql());
        this.setTimeStartedAt(Utilitaire.heureCourante());
        this.setTtlMinutes(ttlMinutes);
    }

    public int updateLastSeenAt(String userId, String lastSeenAt) throws Exception {
        this.setLastSeenAt(lastSeenAt);
        return this.updateToTableWithHisto(userId);
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
        this.preparePk("AIS", "get_seq_ai_session");
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

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateStartedAt() {
        return this.dateStartedAt;
    }

    public void setDateStartedAt(Date dateStartedAt) {
        this.dateStartedAt = dateStartedAt;
    }

    public String getTimeStartedAt() {
        return this.timeStartedAt;
    }

    public void setTimeStartedAt(String timeStartedAt) {
        this.timeStartedAt = timeStartedAt;
    }

    public String getLastSeenAt() {
        return this.lastSeenAt;
    }

    public void setLastSeenAt(String lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
    }

    public int getTtlMinutes() {
        return this.ttlMinutes;
    }

    public void setTtlMinutes(int ttlMinutes) {
        this.ttlMinutes = ttlMinutes;
    }

    public String getMetadataJson() {
        return this.metadataJson;
    }

    public void setMetadataJson(String metadataJson) {
        this.metadataJson = metadataJson;
    }
}

