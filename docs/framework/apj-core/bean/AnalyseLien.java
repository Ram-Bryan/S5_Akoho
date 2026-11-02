/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  user.UserEJB
 */
package bean;

import bean.ClassMAPTable;
import java.sql.Connection;
import user.UserEJB;

public class AnalyseLien
extends ClassMAPTable {
    private String id;
    private String page;
    private int idUtilisateur;
    private String titre;
    private String lien;

    public AnalyseLien() {
        this.setNomTable("analyselien");
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPage() {
        return this.page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getIdUtilisateur() {
        return this.idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) throws Exception {
        if (this.getMode().compareTo("modif") == 0 && (titre.isEmpty() || titre == null)) {
            throw new Exception("Titre ne peut etre null");
        }
        this.titre = titre;
    }

    public void controler(UserEJB user) throws Exception {
        if (this.getIdUtilisateur() != user.getUser().getRefuser()) {
            throw new Exception("Utilisateur non valide");
        }
    }

    public String getLien() {
        return this.lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    @Override
    public String getTuppleID() {
        return this.getId();
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("AL", "getseqanalyselien");
        this.setId(this.makePK(c));
    }
}

