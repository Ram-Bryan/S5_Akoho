/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.ClassMAPTable;

public abstract class ClassCarte
extends ClassMAPTable {
    double lattitude;
    double longitude;

    public double getLattitude() {
        return this.lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

