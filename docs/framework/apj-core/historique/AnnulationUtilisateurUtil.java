/*
 * Decompiled with CFR 0.152.
 */
package historique;

import bean.GenUtil;
import historique.AnnulationUtilisateur;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class AnnulationUtilisateurUtil
extends GenUtil {
    public AnnulationUtilisateurUtil() {
        super("AnnulationUtilisateur");
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
            AnnulationUtilisateur temp = null;
            Vector<AnnulationUtilisateur> vect = new Vector<AnnulationUtilisateur>();
            while (rs.next()) {
                temp = new AnnulationUtilisateur(rs.getString(1), rs.getString(2), rs.getDate(3));
                vect.add(i, temp);
                ++i;
            }
            Object[] retour = new AnnulationUtilisateur[i];
            while (k < i) {
                retour[k] = (AnnulationUtilisateur)vect.elementAt(k);
                ++k;
            }
            Object[] objectArray = retour;
            return objectArray;
        }
        catch (Exception s) {
            System.out.println("AnnulationUtilisateur " + s.getMessage());
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
                System.out.println("Erreur Fermeture SQL AnnulationUtilisateur " + e.getMessage());
            }
        }
    }
}

