package cinema;

import bean.ClassFille;
import java.sql.Connection;

public class CinemaDetailProgramme extends ClassFille {
    private String id;
    private String idprogramme;
    private String idfilm;
    private int rang;
    private int etat;
    private String iduser;

    public CinemaDetailProgramme() throws Exception {
        this.setNomTable("cinema_detail_programme");
        this.setNomClasseMere("cinema.CinemaProgramme");
        this.setLiaisonMere("idprogramme");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CDPR", "getseq_cinema_detail_programme");
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
        return new String[]{"idprogramme", "idfilm"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"idprogramme", "idfilm"};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdprogramme() {
        return idprogramme;
    }

    public void setIdprogramme(String idprogramme) {
        this.idprogramme = idprogramme;
    }

    public String getIdfilm() {
        return idfilm;
    }

    public void setIdfilm(String idfilm) {
        this.idfilm = idfilm;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
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