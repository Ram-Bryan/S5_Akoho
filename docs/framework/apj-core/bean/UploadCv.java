/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.UploadPj;
import java.sql.Connection;

public class UploadCv
extends UploadPj {
    public UploadCv() {
        this.setNomTable("CV_SCAN_UPLOAD");
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("CVSCAN", "getsequploadscancv");
        this.setId(this.makePK(c));
    }
}

