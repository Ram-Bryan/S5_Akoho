/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.GenUtil
 */
package lc;

import bean.GenUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import lc.Direction;

public class DirectionUtil
extends GenUtil {
    public DirectionUtil() {
        super("direction", "lc.Direction");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object[] resultatGen(ResultSet rs) {
        try {
            int i = 0;
            Direction temp = null;
            Vector<Direction> vect = new Vector<Direction>();
            while (rs.next()) {
                temp = new Direction(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5));
                vect.add(i, temp);
                ++i;
            }
            Direction[] retour = new Direction[i];
            vect.copyInto((Object[])retour);
            Direction[] directionArray = retour;
            return directionArray;
        }
        catch (Exception s) {
            System.out.println("Recherche Direction " + s.getMessage());
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
                System.out.println("Erreur Fermeture SQL Direction " + e.getMessage());
            }
        }
    }

    public Object[] resultatLimit(int numBloc, ResultSet rs) {
        throw new UnsupportedOperationException("Method resultatLimit() not yet implemented.");
    }
}

