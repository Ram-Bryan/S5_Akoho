/*
 * Decompiled with CFR 0.152.
 */
package historique;

import bean.ClassMAPTable;

public class Objet
extends ClassMAPTable {
    public String objet;
    public String desce;

    public Objet(String objete, String desc) {
        if (objete.compareTo("") == 0 || objete == null) {
            this.setObjet("-");
        } else {
            this.setObjet(objete);
        }
        if (desc.compareTo("") == 0 || desc == null) {
            this.setDesc("-");
        } else {
            this.setDesc(desc);
        }
    }

    @Override
    public String getAttributIDName() {
        return "objet";
    }

    @Override
    public String getTuppleID() {
        return this.objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getObjet() {
        return this.objet;
    }

    public void setDesc(String desc) {
        this.desce = desc.compareTo("") == 0 || desc == null ? "-" : desc;
    }

    public String getDesc() {
        return this.desce;
    }
}

