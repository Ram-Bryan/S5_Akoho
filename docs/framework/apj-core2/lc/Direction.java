/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 */
package lc;

import bean.ClassMAPTable;
import java.sql.Connection;

public class Direction
extends ClassMAPTable {
    public String idDir;
    public String libelledir;
    public String descdir;
    public double idDirecteur;
    public String abbrevDir;

    public Direction() {
        super.setNomTable("direction");
    }

    public Direction(String libelledir, String descdir, double idDirecteur, String abbrevDir) throws Exception {
        this.libelledir = libelledir;
        this.descdir = descdir;
        this.idDirecteur = idDirecteur;
        this.abbrevDir = abbrevDir;
        super.setNomTable("direction");
    }

    public Direction(String idDir, String libelledir, String descdir, double idDirecteur, String abbrevDir) throws Exception {
        this.idDir = idDir;
        this.libelledir = libelledir;
        this.descdir = descdir;
        this.idDirecteur = idDirecteur;
        this.abbrevDir = abbrevDir;
        super.setNomTable("direction");
    }

    public String getAttributIDName() {
        return "idDir";
    }

    public String getTuppleID() {
        return String.valueOf(this.idDir);
    }

    public String getAbbrevDir() {
        return this.abbrevDir;
    }

    public void setAbbrevDir(String abbrevDir) {
        this.abbrevDir = abbrevDir;
    }

    public String getDescdir() {
        return this.descdir;
    }

    public void setDescdir(String descdi) {
        this.descdir = descdi;
    }

    public String getIdDir() {
        return this.idDir;
    }

    public void setIdDir(String id) {
        this.idDir = id;
    }

    public double getIdDirecteur() {
        return this.idDirecteur;
    }

    public void setIdDirecteur(double idDirecteur) {
        this.idDirecteur = idDirecteur;
    }

    public String getLibelledir() {
        return this.libelledir;
    }

    public void setLibelledir(String libelledir) {
        this.libelledir = libelledir;
    }

    public void construirePK(Connection c) throws Exception {
        super.setNomTable("direction");
        this.preparePk("DIR", "getSeqDirection");
        this.setIdDir(this.makePK(c));
    }
}

