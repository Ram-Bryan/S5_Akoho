/*
 * Decompiled with CFR 0.152.
 */
package reporting.mapping;

import reporting.mapping.CRContent;

public class CRContentLib
extends CRContent {
    private String grapheLib;

    public CRContentLib() throws Exception {
        this.setNomTable("CRCONTENTLIB");
    }

    public String getGrapheLib() {
        return this.grapheLib;
    }

    public void setGrapheLib(String grapheLib) {
        this.grapheLib = grapheLib;
    }
}

