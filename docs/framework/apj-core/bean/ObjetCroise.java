/*
 * Decompiled with CFR 0.152.
 */
package bean;

import utilitaire.Utilitaire;

public class ObjetCroise {
    double[][] croise = null;
    String nomCol;
    String nomLigne;
    String[] valCol = null;
    String[] valLigne = null;
    Object[] aCroise;
    String nomTable;
    String nomCentre;
    double[] sommeColonne = null;
    double[] sommeLigne = null;
    double sommeTotal = 0.0;

    public ObjetCroise(Object[] obj, String nomCol, String nomLigne, String centre, String table) throws Exception {
        this.setACroise(obj);
        this.setNomCol(nomCol);
        this.setNomLigne(nomLigne);
        if (nomCol.compareToIgnoreCase(nomLigne) == 0) {
            throw new Exception("Colonne et ligne identique");
        }
        this.setNomTable(table);
        this.setNomCentre(centre);
        this.setValColBdd();
        this.calculCroise();
        this.calculSommeColonne();
        this.setValColBdd();
        this.calculSommeLigne();
        this.calculSommeTotal();
    }

    public void calculSommeTotal() {
        for (int i = 0; i < this.sommeLigne.length; ++i) {
            this.sommeTotal += this.sommeLigne[i];
        }
    }

    public void calculSommeLigne() {
        this.sommeLigne = new double[this.valLigne.length];
        for (int i = 0; i < this.valLigne.length; ++i) {
            this.sommeLigne[i] = 0.0;
            for (int k = 0; k < this.valCol.length; ++k) {
                this.sommeLigne[i] = this.sommeLigne[i] + this.croise[i][k];
            }
        }
    }

    public void calculSommeColonne() {
        this.sommeColonne = new double[this.valCol.length];
        for (int i = 0; i < this.valCol.length; ++i) {
            this.sommeColonne[i] = 0.0;
            for (int k = 0; k < this.valLigne.length; ++k) {
                this.sommeColonne[i] = this.sommeColonne[i] + this.croise[k][i];
            }
        }
    }

    public void calculCroise() {
        this.croise = new double[this.valLigne.length][this.valCol.length];
        for (int i = 0; i < this.valLigne.length; ++i) {
            for (int j = 0; j < this.valCol.length; ++j) {
                this.croise[i][j] = this.getSommeUnique(this.valLigne[i], this.valCol[j]);
            }
        }
    }

    public String getSortie() {
        String resultat = "";
        double retour = 0.0;
        try {
            for (int i = 0; i < this.aCroise.length; ++i) {
                String valColAi = (String)this.aCroise[i].getClass().getMethod("get" + Utilitaire.convertDebutMajuscule(this.nomCol), null).invoke(this.aCroise[i], null);
                String valLigneAi = (String)this.aCroise[i].getClass().getMethod("get" + Utilitaire.convertDebutMajuscule(this.nomLigne), null).invoke(this.aCroise[i], null);
                resultat = resultat + (String)this.aCroise[i].getClass().getMethod("get" + Utilitaire.convertDebutMajuscule(this.nomCentre), null).invoke(this.aCroise[i], null);
            }
            return resultat;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return resultat;
        }
    }

    public double getSommeUnique(String valLi, String valCo) {
        double retour = 0.0;
        try {
            for (int i = 0; i < this.aCroise.length; ++i) {
                String valColAi = (String)this.aCroise[i].getClass().getMethod("get" + Utilitaire.convertDebutMajuscule(this.nomCol), null).invoke(this.aCroise[i], null);
                String valLigneAi = (String)this.aCroise[i].getClass().getMethod("get" + Utilitaire.convertDebutMajuscule(this.nomLigne), null).invoke(this.aCroise[i], null);
                if (valColAi.compareToIgnoreCase(valCo) != 0 || valLigneAi.compareToIgnoreCase(valLi) != 0) continue;
                Double temp = (Double)this.aCroise[i].getClass().getMethod("get" + Utilitaire.convertDebutMajuscule(this.nomCentre), null).invoke(this.aCroise[i], null);
                retour += temp.doubleValue();
            }
            return retour;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0.0;
        }
    }

    public void setValColBdd() {
        try {
            this.valCol = Utilitaire.getvalCol(this.nomTable, this.nomCol);
            this.valLigne = Utilitaire.getvalCol(this.nomTable, this.nomLigne);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Object[] getACroise() {
        return this.aCroise;
    }

    public double[][] getCroise() {
        return this.croise;
    }

    public String getNomCol() {
        return this.nomCol;
    }

    public String getNomLigne() {
        return this.nomLigne;
    }

    public String[] getValCol() {
        return this.valCol;
    }

    public String[] getValLigne() {
        return this.valLigne;
    }

    public void setNomLigne(String nomLigne) {
        this.nomLigne = nomLigne;
    }

    public void setNomCol(String nomCol) {
        this.nomCol = nomCol;
    }

    public void setCroise(double[][] croise) {
        this.croise = croise;
    }

    public void setACroise(Object[] aCroise) {
        this.aCroise = aCroise;
    }

    public String getNomTable() {
        return this.nomTable;
    }

    public void setNomTable(String nomTable) {
        this.nomTable = nomTable;
    }

    public String getNomCentre() {
        return this.nomCentre;
    }

    public void setNomCentre(String nomCentre) {
        this.nomCentre = nomCentre;
    }

    public double[] getSommeLigne() {
        return this.sommeLigne;
    }

    public double[] getSommeColonne() {
        return this.sommeColonne;
    }

    public double getSommeTotal() {
        return this.sommeTotal;
    }
}

