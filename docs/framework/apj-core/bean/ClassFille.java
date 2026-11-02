/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.CGenUtil;
import bean.ClassEtat;
import bean.ClassMere;
import constante.ConstanteEtat;
import java.sql.Connection;
import java.sql.Statement;
import utilitaire.UtilDB;

public abstract class ClassFille
extends ClassEtat {
    private String liaisonMere;
    private ClassMere mere;
    static String nomClasseMere;

    @Override
    public Object validerObjectBatch(String u, Statement st) throws Exception {
        return null;
    }

    @Override
    public Object validerObject(String u, Connection c) throws Exception {
        return null;
    }

    public String getNomClasseMere() {
        return nomClasseMere;
    }

    public void setNomClasseMere(String nomClasseMer) throws Exception {
        nomClasseMere = nomClasseMer;
    }

    @Override
    public boolean getEstIndexable() {
        return false;
    }

    public String getLiaisonMere() {
        return this.liaisonMere;
    }

    public void setLiaisonMere(String liaisonMere) {
        this.liaisonMere = liaisonMere;
    }

    public ClassMere getMere() {
        return this.mere;
    }

    public ClassMere findMere(String nomTable, Connection c) throws Exception {
        boolean estOuvert = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                estOuvert = true;
            }
            ClassMere crt = (ClassMere)Class.forName(this.getNomClasseMere()).newInstance();
            if (nomTable != null && nomTable.compareToIgnoreCase("") != 0) {
                crt.setNomTable(nomTable);
            }
            crt.setValChamp(this.getAttributIDName(), (Object)this.getValInsert(this.getLiaisonMere()));
            ClassMere[] valiny = (ClassMere[])CGenUtil.rechercher(crt, null, null, c, "");
            if (valiny.length > 0) {
                this.setMere(valiny[0]);
                ClassMere classMere = valiny[0];
                return classMere;
            }
            ClassMere classMere = null;
            return classMere;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (estOuvert && c != null) {
                c.close();
            }
        }
    }

    @Override
    public void controlerDelete(Connection c) throws Exception {
        this.controlerUpdate(c);
    }

    @Override
    public void controlerUpdate(Connection c) throws Exception {
        ClassMere m = this.findMere(null, c);
        if (m.getEtat() >= ConstanteEtat.getEtatValider()) {
            throw new Exception("Objet mere deja valide");
        }
    }

    public void setMere(ClassMere mere) {
        this.mere = mere;
    }
}

