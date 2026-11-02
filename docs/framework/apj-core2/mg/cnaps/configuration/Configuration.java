/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 *  utilitaire.Utilitaire
 */
package mg.cnaps.configuration;

import bean.ClassMAPTable;
import java.sql.Connection;
import java.sql.Date;
import utilitaire.Utilitaire;

public class Configuration
extends ClassMAPTable {
    String id;
    String valmin;
    String valmax;
    String desce;
    String remarque;
    Date daty;
    String typeconfig;
    String compte_credit;

    public String getCompte_credit() {
        return this.compte_credit;
    }

    public void setCompte_credit(String compte_credit) {
        this.compte_credit = compte_credit;
    }

    public Configuration() {
        super.setNomTable("configuration");
    }

    public Configuration(String valmin, String valmax, String desce, String remarque, Date daty, String typeconfig) throws Exception {
        super.setNomTable("configuration");
        this.setValmin(valmin);
        this.setValmax(valmax);
        this.setDesce(desce);
        this.setRemarque(remarque);
        this.setDaty(daty);
        this.setTypeconfig(typeconfig);
    }

    public void construirePK(Connection c) throws Exception {
        this.preparePk("CONF", "getSeqConfiguration");
        this.setId(this.makePK(c));
    }

    public String getTuppleID() {
        return this.getId();
    }

    public String getAttributIDName() {
        return "id";
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValmin() {
        return Utilitaire.champNull((String)this.valmin);
    }

    public void setValmin(String valmin) throws Exception {
        if (this.getMode().compareTo("modif") != 0) {
            this.valmin = valmin;
            return;
        }
        if (valmin == null || valmin.compareToIgnoreCase("") == 0) {
            throw new Exception("Valeur minimale vide");
        }
        this.valmin = valmin;
    }

    public String getValmax() {
        return Utilitaire.champNull((String)this.valmax);
    }

    public void setValmax(String valmax) throws Exception {
        if (this.getMode().compareTo("modif") != 0) {
            this.valmax = valmax;
            return;
        }
        if (valmax == null || valmax.compareToIgnoreCase("") == 0) {
            throw new Exception("Valeur maximale vide");
        }
        this.valmax = valmax;
    }

    public String getDesce() {
        return Utilitaire.champNull((String)this.desce);
    }

    public void setDesce(String desce) throws Exception {
        if (this.getMode().compareTo("modif") != 0) {
            this.desce = desce;
            return;
        }
        if (desce == null || desce.compareToIgnoreCase("") == 0) {
            throw new Exception("Description vide");
        }
        this.desce = desce;
    }

    public String getRemarque() {
        return Utilitaire.champNull((String)this.remarque);
    }

    public void setRemarque(String remarque) throws Exception {
        this.remarque = remarque;
    }

    public Date getDaty() {
        return this.daty;
    }

    public void setDaty(Date daty) throws Exception {
        if (this.getMode().compareTo("modif") != 0) {
            this.daty = daty;
            return;
        }
        if (daty == null) {
            throw new Exception("Date vide");
        }
        this.daty = daty;
    }

    public String getTypeconfig() {
        return Utilitaire.champNull((String)this.typeconfig);
    }

    public void setTypeconfig(String typeconfig) {
        this.typeconfig = typeconfig;
    }
}

