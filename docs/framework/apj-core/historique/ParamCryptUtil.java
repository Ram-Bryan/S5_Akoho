/*
 * Decompiled with CFR 0.152.
 */
package historique;

import bean.GenUtil;
import historique.ParamCrypt;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ParamCryptUtil
extends GenUtil {
    public ParamCryptUtil() {
        super("ParamCrypt");
    }

    public ParamCryptUtil(Connection c) {
        super("ParamCrypt", c);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object[] resultatGen(ResultSet rs) {
        try {
            int i = 0;
            int k = 0;
            ParamCrypt temp = null;
            Vector<ParamCrypt> vect = new Vector<ParamCrypt>();
            while (rs.next()) {
                temp = new ParamCrypt(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4));
                vect.add(i, temp);
                ++i;
            }
            Object[] retour = new ParamCrypt[i];
            while (k < i) {
                retour[k] = (ParamCrypt)vect.elementAt(k);
                ++k;
            }
            Object[] objectArray = retour;
            return objectArray;
        }
        catch (Exception s) {
            System.out.println("Recherche ParamCrypt " + s.getMessage());
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
                System.out.println("Erreur Fermeture SQL ParamCrypt " + e.getMessage());
            }
        }
    }

    @Override
    public Object[] resultatLimit(int numBloc, ResultSet rs) {
        throw new UnsupportedOperationException("Method resultatLimit() not yet implemented.");
    }
}

