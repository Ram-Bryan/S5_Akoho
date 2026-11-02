/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.GenUtil;
import bean.TypeObjetClasse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TypeObjetClasseUtil
extends GenUtil {
    private static final long serialVersionUID = -3722295495031696148L;

    public TypeObjetClasse[] findTypeObjetClasse(String nomTable, String id, String val, String desc, String idParent) throws Exception {
        try {
            TypeObjetClasseUtil to = new TypeObjetClasseUtil();
            to.setNomTable(nomTable);
            int[] a = new int[]{1, 2, 3, 4};
            Object[] valeurs = new String[a.length];
            valeurs[0] = id;
            valeurs[1] = val;
            valeurs[2] = desc;
            valeurs[3] = idParent;
            TypeObjetClasse[] retour = (TypeObjetClasse[])to.rechercher(a, valeurs, "ORDER BY VAL ASC");
            return retour;
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
            int i = 0;
            int k = 0;
            TypeObjetClasse temp = null;
            Vector<TypeObjetClasse> vect = new Vector<TypeObjetClasse>();
            while (rs.next()) {
                temp = new TypeObjetClasse(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                vect.add(i, temp);
                ++i;
            }
            Object[] retour = new TypeObjetClasse[i];
            while (k < i) {
                retour[k] = (TypeObjetClasse)vect.elementAt(k);
                ++k;
            }
            Object[] objectArray = retour;
            return objectArray;
        }
        catch (Exception s) {
            System.out.println("Resultat TypeObjetClasse" + s.getMessage());
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
                System.out.println("Erreur Fermeture SQL Type TypeObjetClasse " + e.getMessage());
            }
        }
    }
}

