/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.GenUtil;
import bean.TypeObjetSupprime;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TypeObjetSupprimeUtil
extends GenUtil {
    private static final long serialVersionUID = 4805940850425269963L;

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
            TypeObjetSupprime temp = null;
            Vector<TypeObjetSupprime> vect = new Vector<TypeObjetSupprime>();
            while (rs.next()) {
                temp = new TypeObjetSupprime(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), "");
                vect.add(i, temp);
                ++i;
            }
            Object[] retour = new TypeObjetSupprime[i];
            while (k < i) {
                retour[k] = (TypeObjetSupprime)vect.elementAt(k);
                ++k;
            }
            Object[] objectArray = retour;
            return objectArray;
        }
        catch (Exception s) {
            System.out.println("Resultat TypeObjetSupprime:" + s.getMessage());
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
                System.out.println("Erreur Fermeture SQL Type Objet Supprime" + e.getMessage());
            }
        }
    }
}

