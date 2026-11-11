package cinema;

import bean.ClassFille;
import java.sql.Connection;

public class CinemaPlace extends ClassFille {
    private String id;
    private String libelle;
    private String idrange;
    private int etat;
    private String iduser;

    public CinemaPlace() throws Exception {
        this.setNomTable("cinema_place");
        this.setNomClasseMere("cinema.CinemaRange");
        this.setLiaisonMere("idrange");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CPLA", "getseq_cinema_place");
        this.setId(makePK(c));
    }

    @Override
    public String getTuppleID() {
        return id;
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }

    @Override
    public String[] getMotCles() {
        return new String[]{"libelle", "idrange"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"libelle", "idrange"};
    }

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

    public String getIdrange() {
        return idrange;
    }

    public void setIdrange(String idrange) {
        this.idrange = idrange;
    }

    @Override
    public int getEtat() {
        return etat;
    }

    @Override
    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }
}