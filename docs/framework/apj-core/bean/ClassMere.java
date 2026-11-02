/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.AdminGen;
import bean.CGenUtil;
import bean.ClassEtat;
import bean.ClassFille;
import bean.ClassMAPTable;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utilitaire.ConstanteEtat;
import utilitaire.UtilDB;

public abstract class ClassMere
extends ClassEtat {
    private ClassFille[] fille;
    private String liaisonFille;
    String nomClasseFille;

    public void ajouterFille(ClassFille[] filleNouveau) throws Exception {
        ArrayList<Object> liste = new ArrayList<ClassFille>();
        if (this.getFille() != null) {
            liste = new ArrayList<ClassFille>(Arrays.asList(this.getFille()));
        }
        liste.addAll(Arrays.asList(filleNouveau));
        this.fille = (ClassFille[])Array.newInstance(filleNouveau[0].getClass(), liste.size());
        liste.toArray(this.fille);
    }

    public void ajouterFille(ClassFille filleNouveau) throws Exception {
        ArrayList<Object> liste = new ArrayList<ClassFille>();
        if (this.getFille() != null) {
            liste = new ArrayList<ClassFille>(Arrays.asList(this.getFille()));
        }
        filleNouveau.setMere(this);
        liste.add(filleNouveau);
        this.fille = (ClassFille[])Array.newInstance(filleNouveau.getClass(), liste.size());
        liste.toArray(this.fille);
    }

    public String getNomClasseFille() {
        return this.nomClasseFille;
    }

    public void setNomClasseFille(String nomClasseFill) throws Exception {
        this.nomClasseFille = nomClasseFill;
    }

    public ClassFille[] getFille() {
        return this.fille;
    }

    public void setFille(ClassFille[] fillee) throws Exception {
        this.fille = null;
        for (ClassFille f : fillee) {
            if (f == null) continue;
            f.setMere(this);
            this.ajouterFille(f);
        }
    }

    public void setFille(List<ClassFille> fille) throws Exception {
        ClassFille[] lf = new ClassFille[fille.size()];
        this.setFille(fille.toArray(lf));
    }

    public String getLiaisonFille() {
        return this.liaisonFille;
    }

    public void setLiaisonFille(String liaisonFille) {
        this.liaisonFille = liaisonFille;
    }

    @Override
    public ClassMAPTable createObject(String u, Connection c) throws Exception {
        ClassMAPTable ret = super.createObject(u, c);
        if (this.fille == null) {
            return ret;
        }
        Statement st = c.createStatement();
        try {
            this.createObjectFilleBatch(u, st);
            st.executeBatch();
            return ret;
        }
        catch (SQLException e) {
            System.err.println("Erreur principale : " + e.getMessage());
            for (SQLException nextE = e.getNextException(); nextE != null; nextE = nextE.getNextException()) {
                System.err.println("Vraie cause : " + nextE.getMessage());
            }
            throw e;
        }
    }

    @Override
    public ClassMAPTable createObjectBatch(String u, Statement st) throws Exception {
        this.construirePK(st.getConnection());
        ClassMAPTable ret = super.createObjectBatch(u, st);
        if (this.fille == null) {
            return ret;
        }
        this.createObjectFilleBatch(u, st);
        return ret;
    }

    public ClassMAPTable createObjectBatchConnection(String u, Connection c) throws Exception {
        ClassMAPTable ret = super.createObject(u, c);
        Statement st = c.createStatement();
        if (this.fille == null) {
            return ret;
        }
        this.createObjectFilleBatch(u, st);
        st.executeBatch();
        return ret;
    }

    public void createObjectFille(String u, Connection c) throws Exception {
        for (ClassFille f : this.getFille()) {
            String idMereF = f.getValInsert(this.getLiaisonFille());
            if (idMereF == null || this.getTuppleID().compareToIgnoreCase(idMereF) != 0) {
                f.setValChamp(this.getLiaisonFille(), (Object)this.getTuppleID());
            }
            f.createObject(u, c);
        }
    }

    public void createObjectFilleBatch(String u, Statement st) throws Exception {
        for (ClassFille f : this.getFille()) {
            String idMereF = f.getValInsert(this.getLiaisonFille());
            if (idMereF == null || this.getTuppleID().compareToIgnoreCase(idMereF) != 0) {
                f.setValChamp(this.getLiaisonFille(), (Object)this.getTuppleID());
            }
            f.createObjectBatch(u, st);
        }
    }

    @Override
    public Object validerObjectBatch(String u, Statement st) throws Exception {
        this.validerObjectFilleBatch(u, st);
        return super.validerObject(u, st.getConnection());
    }

    @Override
    public Object validerObject(String u, Connection c) throws Exception {
        this.validerObjectFille(u, c);
        return super.validerObject(u, c);
    }

    public void validerObjectFille(String u, Connection c) throws Exception {
        if (this.getFille() == null || this.getFille().length == 0) {
            return;
        }
        for (ClassFille f : this.getFille()) {
            f.validerObject(u, c);
        }
    }

    public void validerObjectFilleBatch(String u, Statement st) throws Exception {
        for (ClassFille f : this.getFille()) {
            f.validerObjectBatch(u, st);
        }
    }

    public ClassMAPTable createObjectMultiple(String u, Connection c) throws Exception {
        ClassMAPTable ret = super.createObject(u, c);
        if (this.fille == null) {
            return ret;
        }
        this.createObjectFilleMultiple(u, c);
        return ret;
    }

    public void createObjectFilleMultiple(String u, Connection c) throws Exception {
        for (ClassFille f : this.getFille()) {
            String idMereF = f.getValInsert(this.getLiaisonFille());
            if (idMereF == null || this.getTuppleID().compareToIgnoreCase(idMereF) != 0) {
                f.setValChamp(this.getLiaisonFille(), (Object)this.getTuppleID());
            }
            f.createObject(u, c);
        }
    }

    public ClassFille[] getFille(String nomTable, Connection c, String apresWhere) throws Exception {
        ClassMAPTable filleOrigine = (ClassMAPTable)Class.forName(this.getNomClasseFille()).newInstance();
        String nomTableOrigine = filleOrigine.getNomTable();
        if (!(this.getFille() == null || this.getFille().length <= 0 || nomTable != null && nomTable.compareToIgnoreCase("") != 0 && nomTable.compareToIgnoreCase(nomTableOrigine) != 0 || apresWhere != null && apresWhere.compareToIgnoreCase("") != 0)) {
            return this.getFille();
        }
        boolean estOuvert = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                estOuvert = true;
            }
            ClassFille crt = (ClassFille)Class.forName(this.getNomClasseFille()).newInstance();
            crt.setValChamp(this.getLiaisonFille(), (Object)this.getTuppleID());
            if (nomTable != null && nomTable.compareToIgnoreCase("") != 0) {
                crt.setNomTableSelect(nomTable);
            }
            ClassFille[] retour = (ClassFille[])CGenUtil.rechercher(crt, null, null, c, apresWhere);
            this.setFille(retour);
            ClassFille[] classFilleArray = retour;
            return classFilleArray;
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

    public ClassMere getMereFille(String nomTableMere, String ntFille, Connection c) throws Exception {
        boolean estOuvert = false;
        try {
            ClassMere[] listeMere;
            if (c == null) {
                c = new UtilDB().GetConn();
                estOuvert = true;
            }
            ClassMere crtMere = (ClassMere)Class.forName(this.getClass().getName()).newInstance();
            crtMere.setTuppleId(this.getTuppleID());
            if (nomTableMere != null && nomTableMere.compareToIgnoreCase("") != 0) {
                crtMere.setNomTable(nomTableMere);
            }
            if ((listeMere = (ClassMere[])CGenUtil.rechercher(crtMere, null, null, c, "")).length == 0) {
                throw new Exception("Objet mere non existant");
            }
            ClassMere obj = listeMere[0];
            ClassFille[] lf = obj.getFille(ntFille, c, "");
            obj.setFille(lf);
            ClassMere classMere = obj;
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

    public void afficher() throws Exception {
        System.out.println("MERE ==== " + this.toJson());
        for (int i = 0; i < this.getFille().length; ++i) {
            System.out.println("-------- FILLEEEEEE " + this.getFille()[i].toJson());
        }
    }

    @Override
    public ClassMAPTable dupliquer(String user, Connection c) throws Exception {
        boolean estOuvert = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                estOuvert = true;
                c.setAutoCommit(false);
            }
            ClassMere cm = (ClassMere)super.dupliquer(user, c);
            cm.setEtat(constante.ConstanteEtat.getEtatCreer());
            cm.insertToTableWithHisto(user, c);
            if (this.getFille() == null) {
                this.setFille(this.getFille(null, c, ""));
            }
            cm.setFille(this.dupliquerFille(user, cm.getTuppleID(), c));
            if (estOuvert) {
                c.commit();
            }
            ClassMere classMere = cm;
            return classMere;
        }
        catch (Exception e) {
            c.rollback();
            throw e;
        }
        finally {
            if (estOuvert && c != null) {
                c.close();
            }
        }
    }

    public ClassFille[] dupliquerFille(String user, String idMere, Connection c) throws Exception {
        ClassFille[] retour = new ClassFille[this.getFille().length];
        int i = 0;
        for (ClassFille f : this.getFille()) {
            ClassFille dupl = (ClassFille)f.dupliquer(user, c);
            dupl.setValChamp(this.getLiaisonFille(), (Object)idMere);
            dupl.insertToTableWithHisto(user, c);
            retour[i] = dupl;
            ++i;
        }
        return retour;
    }

    public ClassMere[] scinder(String nomAttributFille) throws Exception {
        ClassFille[] listeFille = (ClassFille[])AdminGen.trier(this.getFille(), new String[]{nomAttributFille}, new String[]{"ASC"});
        return null;
    }

    public ClassMere fusionner(String[] hoFusion, Connection c) throws Exception {
        boolean estOuvert = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                estOuvert = true;
            }
            ClassMAPTable fille = (ClassMAPTable)Class.forName(this.getNomClasseFille()).newInstance();
            for (int i = 0; i < hoFusion.length; ++i) {
                if (hoFusion[i].equalsIgnoreCase(this.getTuppleID())) continue;
                fille.setValChamp(this.getLiaisonFille(), (Object)hoFusion[i]);
                ClassFille[] filles = (ClassFille[])CGenUtil.rechercher(fille, null, null, c, "");
                for (int il = 0; il < filles.length; ++il) {
                    filles[il].setValChamp(this.getLiaisonFille(), (Object)this.getTuppleID());
                    filles[il].updateToTable(c);
                }
                ClassMere me = (ClassMere)Class.forName(this.getClassName()).newInstance();
                me.setTuppleId(hoFusion[i]);
                ClassMere[] meres = (ClassMere[])CGenUtil.rechercher(me, null, null, c, "");
                meres[0].setValChamp("etat", (Object)constante.ConstanteEtat.getEtatAnnuler());
                meres[0].updateToTable(c);
            }
            this.setFille(this.getFille(fille.getNomTable(), c, ""));
            ClassMere classMere = this;
            return classMere;
        }
        catch (Exception e) {
            c.rollback();
            e.printStackTrace();
            throw e;
        }
        finally {
            if (estOuvert && c != null) {
                c.close();
            }
        }
    }

    public ClassMere fusionnerObjet(String[] hoFusion, Connection c) throws Exception {
        boolean estOuvert = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                estOuvert = true;
            }
            ClassMAPTable fille = (ClassMAPTable)Class.forName(this.getNomClasseFille()).newInstance();
            ArrayList<ClassFille> filleList = new ArrayList<ClassFille>();
            for (int i = 0; i < hoFusion.length; ++i) {
                if (hoFusion[i].equalsIgnoreCase(this.getTuppleID())) continue;
                fille.setValChamp(this.getLiaisonFille(), (Object)hoFusion[i]);
                ClassFille[] filles = (ClassFille[])CGenUtil.rechercher(fille, null, null, c, "");
                for (int il = 0; il < filles.length; ++il) {
                    filles[il].setValChamp(this.getLiaisonFille(), (Object)this.getTuppleID());
                    filleList.add(filles[il]);
                }
            }
            ClassFille[] fillesTemp = this.getFille(fille.getNomTable(), c, "");
            filleList.addAll(Arrays.asList(fillesTemp));
            fillesTemp = filleList.toArray(new ClassFille[0]);
            this.setFille(fillesTemp);
            ClassMere classMere = this;
            return classMere;
        }
        catch (Exception e) {
            c.rollback();
            e.printStackTrace();
            throw e;
        }
        finally {
            if (estOuvert && c != null) {
                c.close();
            }
        }
    }

    public void deleteFille(ClassMAPTable[] fille, String user, Connection c) throws Exception {
        boolean estOuvert = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                estOuvert = true;
            }
            if (this.getEtat() >= ConstanteEtat.getEtatValider()) {
                throw new Exception("Objet deja viser non modifiable");
            }
            for (int i = 0; i < fille.length; ++i) {
                fille[i].deleteToTableWithHisto(user, c);
            }
        }
        catch (Exception e) {
            c.rollback();
            throw e;
        }
        finally {
            if (estOuvert && c != null) {
                c.close();
            }
        }
    }
}

