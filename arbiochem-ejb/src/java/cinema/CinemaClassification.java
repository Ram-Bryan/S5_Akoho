package cinema;

import bean.ClassMAPTable;
import java.sql.Connection;

public class CinemaClassification extends ClassMAPTable {
    private String id;
    private int limite;
    private String libelle;

    public CinemaClassification() throws Exception {
        this.setNomTable("cinema_classification");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CCLF", "getseq_cinema_classification");
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

    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }



}
