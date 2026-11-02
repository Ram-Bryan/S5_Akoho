/*
 * Decompiled with CFR 0.152.
 */
package historique;

import bean.GenUtil;
import historique.MapHistorique;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class HistoriqueUtil
extends GenUtil
implements Serializable,
Cloneable {
    public HistoriqueUtil() {
        super("Historique", "historique.MapHistorique");
        System.out.println("ca y ets");
    }

    public int testException() throws Exception {
        try {
            throw new Exception("HistoriqueUtil");
        }
        catch (Exception ex) {
            throw new Exception("Exception aty @ " + ex.getMessage());
        }
    }

    public MapHistorique[] rechercheById(String ref) {
        MapHistorique[] retour = (MapHistorique[])this.rechercher(4, ref);
        return retour;
    }

    public MapHistorique[] rechercheActeur(String user) {
        MapHistorique[] retour = (MapHistorique[])this.rechercher(6, user);
        return retour;
    }

    public MapHistorique[] rechercheHeureAction(String action, String ref) {
        try {
            int[] a = new int[]{4, 5};
            Object[] val = new String[a.length];
            val[0] = new String(ref);
            val[1] = new String(action);
            MapHistorique[] retour = (MapHistorique[])this.rechercher(a, val);
            return retour;
        }
        catch (Exception ex) {
            return null;
        }
    }

    public MapHistorique[] rechercheByRefDate(String refUser, String daty) {
        try {
            int[] a = new int[]{2, 6};
            Object[] val = new String[a.length];
            val[0] = daty;
            val[1] = refUser;
            MapHistorique[] retour = (MapHistorique[])this.rechercher(a, val);
            return retour;
        }
        catch (Exception ex) {
            return null;
        }
    }

    public MapHistorique[] rechercheHisto(String refUser, String objet, String action, String daty) {
        try {
            int[] a = new int[]{2, 4, 5, 6};
            Object[] val = new String[a.length];
            val[0] = new String(daty);
            val[1] = new String(objet);
            val[2] = new String(action);
            val[3] = new String(refUser);
            MapHistorique[] retour = (MapHistorique[])this.rechercher(a, val);
            return retour;
        }
        catch (Exception ex) {
            return null;
        }
    }

    public MapHistorique[] recherche(int[] numChamp, Object[] valeur) {
        try {
            MapHistorique[] retour = (MapHistorique[])this.rechercher(numChamp, valeur);
            return retour;
        }
        catch (Exception ex) {
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object[] resultatGen(ResultSet rs) {
        try {
            int i = 0;
            int k = 0;
            MapHistorique temp = null;
            Vector<MapHistorique> vect = new Vector<MapHistorique>();
            while (rs.next()) {
                temp = new MapHistorique(rs.getString(1), rs.getDate(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                vect.add(i, temp);
                ++i;
            }
            Object[] retour = new MapHistorique[i];
            while (k < i) {
                retour[k] = (MapHistorique)vect.elementAt(k);
                ++k;
            }
            Object[] objectArray = retour;
            return objectArray;
        }
        catch (Exception s) {
            System.out.println("Resultat " + s.getMessage());
            Object[] objectArray = null;
            return objectArray;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL HistoriqueOrdUtil " + e.getMessage());
            }
        }
    }

    @Override
    public Object[] resultatLimit(int numBloc, ResultSet rs) {
        return null;
    }
}

