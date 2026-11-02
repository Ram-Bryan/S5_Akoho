/*
 * Decompiled with CFR 0.152.
 */
package importUtils.importable;

import java.sql.Connection;
import java.util.HashMap;

public interface Importable {
    public void buildSelf(HashMap<String, String> var1, Connection var2) throws Exception;

    public String[] importedFields();

    public Importable duplicate() throws Exception;

    public boolean withHeader();
}

