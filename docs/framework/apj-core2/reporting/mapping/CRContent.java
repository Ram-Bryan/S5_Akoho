/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMere
 */
package reporting.mapping;

import bean.ClassMere;
import java.sql.Connection;

public class CRContent
extends ClassMere {
    private String id;
    private String titre;
    private String paragraphe;
    private String mapping;
    private String vue;
    private int graphe;
    private int ordre;
    private String requeteSql;

    public CRContent() throws Exception {
        this.setNomTable("CRCONTENT");
        this.setNomClasseFille("reporting.mapping.CRContentFille");
        this.setLiaisonFille("idCrContent");
    }

    public void construirePK(Connection c) throws Exception {
        this.preparePk("CRC", "getseqCRContent");
        this.setId(this.makePK(c));
    }

    public String getTuppleID() {
        return this.id;
    }

    public String getAttributIDName() {
        return "id";
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getParagraphe() {
        return this.paragraphe;
    }

    public void setParagraphe(String paragraphe) {
        this.paragraphe = paragraphe;
    }

    public String getMapping() {
        return this.mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public String getVue() {
        return this.vue;
    }

    public void setVue(String vue) {
        this.vue = vue;
    }

    public int getGraphe() {
        return this.graphe;
    }

    public void setGraphe(int graphe) {
        this.graphe = graphe;
    }

    public int getOrdre() {
        return this.ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public String getRequeteSql() {
        return this.requeteSql;
    }

    public void setRequeteSql(String requeteSql) {
        this.requeteSql = requeteSql;
    }
}

