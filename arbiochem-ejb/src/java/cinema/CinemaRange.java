package cinema;

import bean.ClassMere;
import java.sql.Connection;

public class CinemaRange extends ClassMere {
    private String id;
    private String libelle;
    private String idsalle;
    private String idcategorierange;


    public CinemaRange() throws Exception {
        this.setNomTable("cinema_range");
        this.setNomClasseFille("cinema.CinemaPlace");
        this.setLiaisonFille("idrange");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CRNG", "getseq_cinema_range");
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
        return new String[]{"libelle", "idsalle", "idcategorierange"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"libelle", "idsalle", "idcategorierange"};
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

    public String getIdsalle() {
        return idsalle;
    }

    public void setIdsalle(String idsalle) {
        this.idsalle = idsalle;
    }

    public String getIdcategorierange() {
        return idcategorierange;
    }

    public void setIdcategorierange(String idcategorierange) {
        this.idcategorierange = idcategorierange;
    }

}