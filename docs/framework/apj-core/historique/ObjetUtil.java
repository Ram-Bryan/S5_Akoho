/*
 * Decompiled with CFR 0.152.
 */
package historique;

import bean.GenUtil;
import historique.Objet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ObjetUtil
extends GenUtil {
    public ObjetUtil() {
        super("objet");
    }

    @Override
    public Object[] resultatLimit(int numBloc, ResultSet rs) {
        throw new UnsupportedOperationException("Method resultatLimit() not yet implemented.");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object[] resultatGen(ResultSet rs) {
        try {
            int i = 0;
            int k = 0;
            Objet temp = null;
            Vector<Objet> vect = new Vector<Objet>();
            while (rs.next()) {
                temp = new Objet(rs.getString(1), rs.getString(2));
                vect.add(i, temp);
                ++i;
            }
            Object[] retour = new Objet[i];
            while (k < i) {
                retour[k] = (Objet)vect.elementAt(k);
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
                System.out.println("Erreur Fermeture SQL Operation Util " + e.getMessage());
            }
        }
    }
}

