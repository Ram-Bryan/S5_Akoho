/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.GenUtil;
import bean.UnionIntraTable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class UnionIntraTableUtil
extends GenUtil {
    @Override
    public Object[] resultatLimit(int numBloc, ResultSet rs) {
        throw new UnsupportedOperationException("Method resultatLimit() not yet implemented.");
    }

    public UnionIntraTable[] findUnionIntraTable(String nomTable, String id, String id1, String id2, String rem, String mont1, String mont2, String eta, String apresW) throws Exception {
        try {
            this.setNomTable(nomTable);
            int[] a = new int[]{1, 2, 3, 4, 6};
            Object[] val = new String[]{id, id1, id2, rem, eta};
            int[] b = new int[]{5};
            String[] valInt = new String[]{mont1, mont2};
            this.utiliserChampBase();
            UnionIntraTable[] atypeobjet = (UnionIntraTable[])this.rechercher(a, val, apresW, b, valInt);
            for (int i = 0; i < atypeobjet.length; ++i) {
                atypeobjet[i].setNomTable(nomTable);
            }
            return atypeobjet;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur dans UnionIntraTableUtil " + ex.getMessage());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object[] resultatGen(ResultSet rs) {
        try {
            int i = 0;
            boolean k = false;
            UnionIntraTable temp = null;
            Vector<UnionIntraTable> vect = new Vector<UnionIntraTable>();
            while (rs.next()) {
                temp = new UnionIntraTable(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getInt(6));
                vect.add(i, temp);
                ++i;
            }
            Object[] retour = new UnionIntraTable[i];
            vect.copyInto(retour);
            Object[] objectArray = retour;
            return objectArray;
        }
        catch (Exception s) {
            s.printStackTrace();
            System.out.println("Erreur dans " + this.getClass().getName() + " " + s.getMessage());
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
                System.out.println("Erreur Fermeture SQL Type Objet " + this.getClass().getName() + " " + e.getMessage());
            }
        }
    }
}

