/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.ClassMAPTable;
import historique.MapHistorique;
import historique.MapUtilisateur;

public class ClassEjb {
    public int create(ClassMAPTable a, MapUtilisateur b) throws Exception {
        MapHistorique h = new MapHistorique(a.getClassName(), "insert", b.getTuppleID(), a.getTuppleID());
        return a.insertToTable(h);
    }

    public int update(ClassMAPTable a, MapUtilisateur b) throws Exception {
        MapHistorique h = new MapHistorique(a.getClassName(), "update", b.getTuppleID(), a.getTuppleID());
        return a.updateToTableWithHisto(h);
    }

    public int delete(ClassMAPTable a, MapUtilisateur b) throws Exception {
        MapHistorique h = new MapHistorique(a.getClassName(), "delete", b.getTuppleID(), a.getTuppleID());
        return a.deleteToTable(h);
    }
}

