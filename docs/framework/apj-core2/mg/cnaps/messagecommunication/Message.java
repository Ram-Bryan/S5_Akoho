/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 *  utilitaire.Utilitaire
 */
package mg.cnaps.messagecommunication;

import bean.ClassMAPTable;
import java.sql.Connection;
import java.sql.Timestamp;
import utilitaire.Utilitaire;

public class Message
extends ClassMAPTable {
    private String id;
    private String idconversation;
    private String sender;
    private String receiver;
    private String msg;
    private Timestamp dateheure;
    private String statut;

    public Message() {
        super.setNomTable("message");
    }

    public String getTuppleID() {
        return this.id;
    }

    public String getAttributIDName() {
        return "id";
    }

    public void construirePK(Connection c) throws Exception {
        this.preparePk("MSG", "getSeqmessage");
        this.setId(this.makePK(c));
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return Utilitaire.champNull((String)this.sender);
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMsg() {
        return Utilitaire.champNull((String)this.msg);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatut() {
        return Utilitaire.champNull((String)this.statut);
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Timestamp getDateheure() {
        return this.dateheure;
    }

    public void setDateheure(Timestamp dateheure) {
        this.dateheure = dateheure;
    }

    public String getIdconversation() {
        return Utilitaire.champNull((String)this.idconversation);
    }

    public void setIdconversation(String idconversation) {
        this.idconversation = idconversation;
    }

    public String getReceiver() {
        return Utilitaire.champNull((String)this.receiver);
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}

