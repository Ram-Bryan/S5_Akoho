package ferme.triageBatiment;

import bean.ClassMAPTable;
import bean.ClassMere;
import ferme.utils.ConstanteFerme;
import bean.CGenUtil;

import java.sql.Connection;
import java.sql.Date;
import utilitaire.UtilDB;

import bean.CGenUtil;
import bean.ClassFille;
import ferme.receptionlieux.ReceptionLieux;
import java.text.DecimalFormat;

public class TriageBatimentParquet extends ClassMere {
    private String id;
    private Date daty;
    private String idlot;
    private int dispomale;
    private int dispofemelle, etat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDaty() {
        return daty;
    }

    public void setDaty(Date daty) {
        this.daty = daty;
    }

    public String getIdlot() {
        return idlot;
    }

    public void setIdlot(String idlot) {
        this.idlot = idlot;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public int getDispomale() {
        return dispomale;
    }

    public void setDispomale(int dispomale) {
        this.dispomale = dispomale;
    }

    public int getDispofemelle() {
        return dispofemelle;
    }

    public void setDispofemelle(int dispofemelle) {
        this.dispofemelle = dispofemelle;
    }

    public TriageBatimentParquet() throws Exception {
        this.setNomTable("TRIAGEBATIMENTPARQUET");
        this.setNomClasseFille("ferme.triageBatiment.TriageBatimentParquetDetail");
        this.setLiaisonFille("idmere");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("TB", "getseq_triagebatimentparquet");
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
    public ClassMAPTable createObject(String u, Connection c) throws Exception {
        // TriageBatimentParquetDetail[] triageBatimentParquetDetails =
        // (TriageBatimentParquetDetail[]) this.getFille();
        // double quantiteMale = this.getDispomale();
        // double quantiteFemelle = this.getDispofemelle();
        //
        // double sommeMale = 0;
        // double sommeFemelle = 0;
        //
        // for (int i = 0; i < triageBatimentParquetDetails.length; i++) {
        // String idsexe = triageBatimentParquetDetails[i].getIdsexe();
        // double qte = triageBatimentParquetDetails[i].getQte();
        //
        // if (ConstanteFerme.IDSEXEMALE.equals(idsexe)) {
        // sommeMale += qte;
        // } else if (ConstanteFerme.IDSEXEFEMELLE.equals(idsexe)) {
        // sommeFemelle += qte;
        // } else {
        // throw new Exception("Sexe non reconnu pour la ligne de d\u00E9tail: " +
        // idsexe);
        // }
        // }
        //
        // if (sommeMale != quantiteMale) {
        // throw new Exception("La quantit\u00E9 totale des m\u00E2les tri\\u00E9s (" +
        // sommeMale
        // + ") ne correspond pas \u00E0 la quantit\u00E9 disponible male (" +
        // quantiteMale + ")");
        // }
        //
        // if (sommeFemelle != quantiteFemelle) {
        // throw new Exception("La quantit\u00E9 totale des femelles tri\u00E9es (" +
        // sommeFemelle
        // + ") ne correspond pas \u00E0 la quantit\u00E9 disponible femelle (" +
        // quantiteFemelle + ")");
        // }

        ClassFille[] filles = this.getFille();
        if (filles != null && filles.length > 0) {
            double sommeMale = 0;
            double sommeFemelle = 0;
            for (ClassFille f : filles) {
                TriageBatimentParquetDetail d = (TriageBatimentParquetDetail) f;
                String idsexe = d.getIdsexe();
                double qte = d.getQte();
                if (ConstanteFerme.IDSEXEMALE.equals(idsexe)) {
                    sommeMale += qte;
                } else if (ConstanteFerme.IDSEXEFEMELLE.equals(idsexe)) {
                    sommeFemelle += qte;
                } else {
                    throw new Exception("Sexe non reconnu pour la ligne de détail: " + idsexe);
                }
            }
            this.setDispomale((int) Math.round(sommeMale));
            this.setDispofemelle((int) Math.round(sommeFemelle));
        }

        return super.createObject(u, c);
    }

    public double getDisponnible(String idLot, String idSexe, Connection c) throws Exception {
        boolean canClose = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                canClose = true;
            }
            TriageLotSexeQualite t = new TriageLotSexeQualite();
            t.setIdLot(idLot);
            t.setIdsexe(idSexe);
            t.setIdqualite(ConstanteFerme.qualiteConforme);
            TriageLotSexeQualite[] r = (TriageLotSexeQualite[]) CGenUtil.rechercher(t, null, null, c, "");
            if (r.length > 0 && r != null) {
                return r[0].getQte();
            }
            return 0;
        } catch (Exception e) {
            throw e;
        } finally {
            if (canClose) {
                c.close();
            }
        }
    }

    public double getDisponnibleLib(String idLot, String idSexe, String idQualite, Connection c) throws Exception {
        boolean canClose = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                canClose = true;
            }
            TriageLotSexeQualiteLib t = new TriageLotSexeQualiteLib();
            t.setIdLot(idLot);
            t.setIdSexe(idSexe);
            t.setIdQualite(idQualite);
            TriageLotSexeQualiteLib[] r = (TriageLotSexeQualiteLib[]) CGenUtil.rechercher(t, null, null, c, "");
            if (r.length > 0 && r != null) {
                return r[0].getQte();
            }
            return 0;
        } catch (Exception e) {
            throw e;
        } finally {
            if (canClose) {
                c.close();
            }
        }
    }

    @Override
    public void controler(Connection c) throws Exception {
        super.controler(c);

        if (this.getIdlot() == null || this.getIdlot().trim().isEmpty()) {
            throw new Exception("Le lot est obligatoire.");
        }

        ClassFille[] filles = this.getFille();
        if (filles == null || filles.length == 0) {
            throw new Exception("Au moins une ligne de triage est requise.");
        }

        DecimalFormat fmt = new DecimalFormat("0.##");

        // 1. Sum of qte in this triage + reject negatives
        double totalTriaged = 0;
        for (ClassFille f : filles) {
            TriageBatimentParquetDetail d = (TriageBatimentParquetDetail) f;
            if (d.getQte() < 0) {
                throw new Exception("La quantité ne peut pas être négative.");
            }
            totalTriaged += d.getQte();
        }

        // 2. Total vivants still available for this lot (after all receptions)
        double vivants = ReceptionLieux.getVivantsDisponibles(this.getIdlot(), c);

        // 3. Already triaged in other triages (exclude cancelled)
        double alreadyTriaged = getTotalAlreadyTriaged(this.getIdlot(), c);

        // 4. If we're updating an existing triage, exclude its own qte from "already"
        if (this.getId() != null && !this.getId().trim().isEmpty()) {
            double ownQte = getTotalTriagedByTriageId(this.getId(), c);
            alreadyTriaged -= ownQte;
        }

        double available = vivants - alreadyTriaged;

        // 5. Business rule: ALL birds must be distributed
        double diff = Math.abs(totalTriaged - available);
        if (diff > 0.01) {
            String msg;
            if (totalTriaged < available) {
                msg = "Il reste " + fmt.format(available - totalTriaged)
                        + " poussin(s) non réparti(s). Vous devez distribuer TOUS les poussins disponibles ("
                        + fmt.format(available) + ") — actuellement " + fmt.format(totalTriaged)
                        + " réparti(s) dans le formulaire.";
            } else {
                msg = "Total réparti (" + fmt.format(totalTriaged)
                        + ") dépasse les vivants disponibles (" + fmt.format(available) + ").";
            }
            throw new Exception(msg);
        }
    }

    /**
     * Sum of all qte already triaged for this lot (excluding cancelled triages).
     */
    private static double getTotalAlreadyTriaged(String idLot, Connection c) throws Exception {
        Object o = CGenUtil.rechercherSimple(
                "SELECT NVL(SUM(D.QTE), 0) FROM TRIAGEBATIMENTPARQUETDETAIL D "
                        + "JOIN TRIAGEBATIMENTPARQUET M ON M.ID = D.IDMERE "
                        + "WHERE M.IDLOT = '" + idLot.replace("'", "''") + "' "
                        + "AND M.ETAT != 0",
                c);
        return (o == null) ? 0 : ((Number) o).doubleValue();
    }

    /**
     * Sum of qte in one specific triage (used to exclude own rows when updating).
     */
    private static double getTotalTriagedByTriageId(String idTriage, Connection c) throws Exception {
        Object o = CGenUtil.rechercherSimple(
                "SELECT NVL(SUM(QTE), 0) FROM TRIAGEBATIMENTPARQUETDETAIL "
                        + "WHERE IDMERE = '" + idTriage.replace("'", "''") + "'",
                c);
        return (o == null) ? 0 : ((Number) o).doubleValue();
    }

}
