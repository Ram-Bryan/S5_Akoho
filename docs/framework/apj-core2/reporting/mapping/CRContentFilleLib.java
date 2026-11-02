/*
 * Decompiled with CFR 0.152.
 */
package reporting.mapping;

import reporting.mapping.CRContentFille;

public class CRContentFilleLib
extends CRContentFille {
    private String colSommeLib;
    private String colDateLib;

    public CRContentFilleLib() throws Exception {
        this.setNomTable("CRCONTENTFILLELIB");
    }

    public String getColSommeLib() {
        return this.colSommeLib;
    }

    public void setColSommeLib(String colSommeLib) {
        this.colSommeLib = colSommeLib;
    }

    public String getColDateLib() {
        return this.colDateLib;
    }

    public void setColDateLib(String colDateLib) {
        this.colDateLib = colDateLib;
    }
}

