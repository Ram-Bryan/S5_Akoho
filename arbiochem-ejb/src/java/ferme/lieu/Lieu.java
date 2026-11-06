package ferme.lieu;

import bean.ClassEtat;
import java.sql.Connection;

public class Lieu extends ClassEtat {

    private String id;
    private String libelle;
    private String typelieu;
    private String idferme;

    public Lieu() throws Exception {
        this.setNomTable("LIEU");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("LIE", "getseq_lieu");
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

    @Override
    public String[] getMotCles() {
        return new String[]{"libelle"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"libelle"};
    }

    // ==================== GETTERS / SETTERS ====================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getTypelieu() {
        return typelieu;
    }

    public void setTypelieu(String typelieu) {
        this.typelieu = typelieu;
    }

    public String getIdferme() {
        return idferme;
    }

    public void setIdferme(String idferme) {
        this.idferme = idferme;
    }
}