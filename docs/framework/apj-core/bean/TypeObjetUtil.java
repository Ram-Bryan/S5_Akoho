/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.GenUtil;
import bean.TypeObjet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TypeObjetUtil
extends GenUtil {
    public TypeObjet[] findTypeObjet(String nomTable, String id, String typ) throws Exception {
        try {
            TypeObjetUtil to = new TypeObjetUtil();
            to.setNomTable(nomTable);
            int[] a = new int[]{1, 2};
            Object[] val = new String[a.length];
            val[0] = id;
            val[1] = typ;
            TypeObjet[] atypeobjet = (TypeObjet[])to.rechercher(a, val);
            return atypeobjet;
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
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
            Object[] atypeobjet;
            int i = 0;
            int k = 0;
            TypeObjet temp = null;
            Vector<TypeObjet> vect = new Vector<TypeObjet>();
            while (rs.next()) {
                temp = new TypeObjet(rs.getString(1), rs.getString(2), rs.getString(3));
                vect.add(i, temp);
                ++i;
            }
            Object[] retour = new TypeObjet[i];
            while (k < i) {
                retour[k] = (TypeObjet)vect.elementAt(k);
                ++k;
            }
            Object[] objectArray = atypeobjet = retour;
            return objectArray;
        }
        catch (Exception s) {
            System.out.println("Resultat ".concat(String.valueOf(String.valueOf(s.getMessage()))));
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
                System.out.println("Erreur Fermeture SQL Type Objet ".concat(String.valueOf(String.valueOf(e.getMessage()))));
            }
        }
    }
}

