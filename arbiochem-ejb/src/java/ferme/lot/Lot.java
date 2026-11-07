package ferme.lot;

import bean.ClassEtat;
import java.sql.Connection;
import java.sql.Date;
import bean.CGenUtil;
import bean.ClassMAPTable;
import faturefournisseur.As_BonDeLivraison_Fille;
import stock.MvtStockLib;
import stock.MvtStockFille;
import faturefournisseur.As_BonDeLivraison_Fille;

public class Lot extends ClassEtat {
    private String id;
    private String nomlot;
    private String reference;
    private Date dateeclosion;
    private Date datearrivee, dateCloture;
    private String source;
    private String idferme;
    private String idarticle;
    private String idorigine;
    private String idprogramme, idSouche, idCategorieLot;
    private String idBlFille;

    public String getIdBlFille() {
        return idBlFille;
    }

    public void setIdBlFille(String idBlFille) {
        this.idBlFille = idBlFille;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomlot() {
        return nomlot;
    }

    public void setNomlot(String nomlot) {
        this.nomlot = nomlot;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Date getDateeclosion() {
        return dateeclosion;
    }

    public void setDateeclosion(Date dateeclosion) {
        this.dateeclosion = dateeclosion;
    }

    public Date getDatearrivee() {
        return datearrivee;
    }

    public void setDatearrivee(Date datearrivee) {
        this.datearrivee = datearrivee;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIdferme() {
        return idferme;
    }

    public void setIdferme(String idferme) {
        this.idferme = idferme;
    }

    public String getIdarticle() {
        return idarticle;
    }

    public void setIdarticle(String idarticle) {
        this.idarticle = idarticle;
    }

    public String getIdorigine() {
        return idorigine;
    }

    public void setIdorigine(String idorigine) {
        this.idorigine = idorigine;
    }

    public String getIdprogramme() {
        return idprogramme;
    }

    public void setIdprogramme(String idprogramme) {
        this.idprogramme = idprogramme;
    }

    @Override
    public ClassMAPTable createObject(String u, Connection c) throws Exception {
        checkDates();
        checkNomLotEtReference(c);
        ClassMAPTable o = super.createObject(u, c);
        return o;
    }

    public Date getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(Date dateCloture) {
        this.dateCloture = dateCloture;
    }

    public String getIdCategorieLot() {
        return idCategorieLot;
    }

    public void setIdCategorieLot(String idCategorieLot) {
        this.idCategorieLot = idCategorieLot;
    }

    public Lot() throws Exception {
        this.setNomTable("LOT");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("LOT", "getseq_lot");
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
        String[] motCles = { "nomlot" };
        return motCles;
    }

    @Override
    public String[] getValMotCles() {
        String[] valMotCles = { "nomlot" };
        return valMotCles;
    }

    public String getIdSouche() {
        return idSouche;
    }

    public void setIdSouche(String idSouche) {
        this.idSouche = idSouche;
    }

    public void checkNomLotEtReference(Connection c) throws Exception {
        if (this.getNomlot() == null || this.getNomlot().equals("") || this.getReference() == null
                || this.getReference().equals("")) {
            throw new Exception("Le nom du lot et la r\u00E9f\u00E9rence ne peuvent pas \u00EAtre vides");
        } else {
            Lot[] lots = (Lot[]) CGenUtil.rechercher(new Lot(), null, null, c, " and (UPPER(nomLot) = UPPER('"
                    + this.getNomlot() + "')) OR UPPER(reference) = UPPER('" + this.getReference() + "')");
            if (lots.length > 0) {
                throw new Exception("Le m\u00EAme nom du lot et/ou la r\u00E9f\u00E9rence existent d\u00E9j\u00E0");
            }
        }
    }

    public void checkDates() throws Exception {
        if (this.getDateeclosion() == null) {
            throw new Exception("La date d'éclosion est obligatoire");
        }
        if (this.getDatearrivee() == null) {
            throw new Exception("La date d'arrivée est obligatoire");
        }
        if (this.getDateeclosion().after(this.getDatearrivee())) {
            throw new Exception("La date d'éclosion ne peut pas être supérieure à la date d'arrivée");
        }
    }

    public String getAvertissementStock(Connection c) throws Exception {
        if (this.getIdBlFille() == null || this.getIdBlFille().trim().isEmpty()) {
            return null; // no BL line linked → no check
        }

        // 1. Load the BL line
        As_BonDeLivraison_Fille line = new As_BonDeLivraison_Fille();
        line = (As_BonDeLivraison_Fille) line.getById(this.getIdBlFille(), null, c);
        if (line == null) {
            return "Ligne de bon de livraison introuvable.";
        }

        // 2. Find validated stock movements for this BL
        MvtStockLib mvtCrit = new MvtStockLib();
        MvtStockLib[] mvts = (MvtStockLib[]) CGenUtil.rechercher(mvtCrit, null, null, c,
                " and idobjet = '" + line.getNumbl() + "' ");

        if (mvts == null || mvts.length == 0) {
            return "Aucune entrée en stock trouvée pour ce bon de livraison.";
        }

        // 3. For each, check if a MvtStockFille exists with this product
        for (MvtStockLib mvt : mvts) {
            MvtStockFille fCrit = new MvtStockFille();
            MvtStockFille[] filles = (MvtStockFille[]) CGenUtil.rechercher(fCrit, null, null, c,
                    " and idMvtStock = '" + mvt.getId() + "' and idproduit = '" + line.getProduit() + "' ");
            if (filles != null && filles.length > 0) {
                return null; // ✅ stock entry exists for this product
            }
        }

        return "Aucune entrée en stock trouvée pour ce produit sur ce bon de livraison.";
    }
}
