package cinema;

import bean.ClassMAPTable;
import java.sql.Connection;

public class CinemaClient extends ClassMAPTable {
    private String id;
    private String nom;

    public CinemaClient() throws Exception {
        this.setNomTable("cinema_client");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CCLI", "getseq_cinema_client");
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
        return new String[]{"nom"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"nom"};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
