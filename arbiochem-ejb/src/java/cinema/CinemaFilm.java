package cinema;

import bean.ClassMAPTable;
import java.sql.Connection;

public class CinemaFilm extends ClassMAPTable {
    private String id;
    private String titre;
    private String description;
    private int duree;

    public CinemaFilm() throws Exception {
        this.setNomTable("cinema_film");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CFIL", "getseq_cinema_film");
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
        return new String[]{"titre", "description"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"titre", "description"};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }
}
