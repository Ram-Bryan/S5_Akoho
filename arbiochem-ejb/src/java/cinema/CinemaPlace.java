package cinema;

import bean.ClassMAPTable;
import java.sql.Connection;

public class CinemaPlace extends ClassMAPTable {
    private String id;
    private String libelle;
    private String idrange;

    public CinemaPlace() throws Exception {
        this.setNomTable("cinema_place");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CPL", "getseq_cinema_place");
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
        return new String[]{"libelle", "id_range"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"libelle", "id_range"};
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
}
