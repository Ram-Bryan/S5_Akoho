/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.GenUtil;
import bean.SituationUnion;
import java.sql.ResultSet;

public class SituationUnionUtil
extends GenUtil {
    public SituationUnionUtil() {
        super("situationDedBc", "bean.SituationUnion");
    }

    public SituationUnion[] findSituationUnion(String nomTable, String id) {
        this.setNomTable(nomTable);
        return (SituationUnion[])this.rechercher(1, id);
    }

    @Override
    public Object[] resultatLimit(int numBloc, ResultSet rs) {
        throw new UnsupportedOperationException("Method resultatLimit() not yet implemented.");
    }
}

