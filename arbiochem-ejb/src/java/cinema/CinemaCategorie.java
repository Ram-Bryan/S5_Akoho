package cinema;

import bean.ClassMAPTable;
import java.sql.Connection;

public class CinemaCategorie extends ClassMAPTable {
    private String id;
    private String libelle;

    public CinemaCategorie() throws Exception {
        this.setNomTable("cinema_categorie");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CCAT", "getseq_cinema_categorie");
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
        return new String[]{"libelle"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"libelle"};
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
}
