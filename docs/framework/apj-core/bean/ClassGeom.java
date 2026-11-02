/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.postgis.PGgeometry
 */
package bean;

import bean.ClassEtat;
import org.postgis.PGgeometry;

public abstract class ClassGeom
extends ClassEtat {
    private PGgeometry geom;

    public PGgeometry getGeom() {
        return this.geom;
    }

    public void setGeom(PGgeometry geom) {
        this.geom = geom;
    }
}

