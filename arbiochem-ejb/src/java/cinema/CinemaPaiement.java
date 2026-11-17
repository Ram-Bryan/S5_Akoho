package cinema;

import bean.ClassMAPTable;
import java.sql.Connection;
import java.sql.Date;

public class CinemaPaiement extends ClassMAPTable {
    private String id;
    private String idfacture;
    private Date daty;
    private double montant;
    private String idmodepaiement;
    private String iduser;

    public CinemaPaiement() throws Exception {
        this.setNomTable("cinema_paiement");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CPAY", "getseq_cinema_paiement");
        this.setId(makePK(c));
    }

    @Override public String getTuppleID() { return id; }
    @Override public String getAttributIDName() { return "id"; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getIdfacture() { return idfacture; }
    public void setIdfacture(String idfacture) { this.idfacture = idfacture; }
    public Date getDaty() { return daty; }
    public void setDaty(Date daty) { this.daty = daty; }
    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }
    public String getIdmodepaiement() { return idmodepaiement; }
    public void setIdmodepaiement(String idmodepaiement) { this.idmodepaiement = idmodepaiement; }
    public String getIduser() { return iduser; }
    public void setIduser(String iduser) { this.iduser = iduser; }
}