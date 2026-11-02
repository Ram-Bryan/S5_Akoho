/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.AssociationTable;
import bean.GenUtil;
import java.sql.ResultSet;

public class AssociationTableUtil
extends GenUtil {
    public AssociationTableUtil() {
        super("AssociationTable", "bean.AssociationTable");
    }

    @Override
    public Object[] resultatLimit(int numBloc, ResultSet rs) {
        throw new UnsupportedOperationException("Method resultatLimit() not yet implemented.");
    }

    public AssociationTable[] findAssociationTable(String id, String t1, String t2, String vueLettre, String vueComplet, String tUnion) throws Exception {
        try {
            int[] a = new int[]{1, 2, 3, 4, 5, 6};
            Object[] val = new String[]{id, t1, t2, tUnion, vueLettre, vueComplet};
            AssociationTable[] at = (AssociationTable[])this.rechercher(a, val);
            return at;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur dans AssociationTableUtil " + ex.getMessage());
        }
    }
}

