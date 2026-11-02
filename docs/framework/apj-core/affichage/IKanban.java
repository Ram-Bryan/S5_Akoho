/*
 * Decompiled with CFR 0.152.
 */
package affichage;

import java.sql.Connection;

public interface IKanban {
    public String getKanbanCardDataContent(Connection var1) throws Exception;

    public String getConditionDateFilter(String var1, String var2) throws Exception;
}

