package cinema;

import bean.ClassMAPTable;
import java.sql.Connection;

public class CinemaCategorieFilm extends ClassMAPTable {
    private String id;
    private String idcategorie;
    private String idfilm;

    public CinemaCategorieFilm() throws Exception {
        this.setNomTable("cinema_categorie_film");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CCF", "getseq_cinema_categorie_film");
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
        return new String[]{"idcategorie", "idfilm"};
    }

    @Override
    public String[] getValMotCles() {
        return new String[]{"idcategorie", "idfilm"};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdcategorie() {
        return idcategorie;
    }

    public void setIdcategorie(String idcategorie) {
        this.idcategorie = idcategorie;
    }

    public String getIdfilm() {
        return idfilm;
    }

    public void setIdfilm(String idfilm) {
        this.idfilm = idfilm;
    }
}
