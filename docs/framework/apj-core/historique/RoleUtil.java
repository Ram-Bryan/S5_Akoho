/*
 * Decompiled with CFR 0.152.
 */
package historique;

import bean.GenUtil;
import historique.MapRoles;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class RoleUtil
extends GenUtil
implements Serializable,
Cloneable {
    public RoleUtil() {
        super("roles");
    }

    public MapRoles rechercheById(int id) {
        MapRoles retour = ((MapRoles[])this.rechercher(1, String.valueOf(id)))[0];
        return retour;
    }

    public MapRoles rechercheByDesc(String desc) {
        MapRoles retour = ((MapRoles[])this.rechercher(2, desc))[0];
        return retour;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object[] resultatGen(ResultSet rs) {
        try {
            int i = 0;
            int k = 0;
            MapRoles temp = null;
            Vector<MapRoles> vect = new Vector<MapRoles>();
            while (rs.next()) {
                temp = new MapRoles(rs.getString(1), rs.getString(2));
                vect.add(i, temp);
                ++i;
            }
            Object[] retour = new MapRoles[i];
            while (k < i) {
                retour[k] = (MapRoles)vect.elementAt(k);
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

