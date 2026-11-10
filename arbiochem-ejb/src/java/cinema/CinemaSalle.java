package cinema;

import bean.ClassMAPTable;
import java.sql.Connection;

public class CinemaSalle extends ClassMAPTable {
    private String id;
    private String libelle;

    public CinemaSalle() throws Exception {
        this.setNomTable("cinema_salle");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CSAL", "getseq_cinema_salle");
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
