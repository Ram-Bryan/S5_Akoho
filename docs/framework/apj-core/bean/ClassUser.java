/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.ClassMAPTable;
import java.sql.Date;

public abstract class ClassUser
extends ClassMAPTable {
    private String iduser;
    private String idverif;
    private String idrempli;
    private Date dateverif;
    private Date daterempli;
    private Date datesaisie;
    private String direction;
    String service;

    public String getDirection() {
        return this.direction;
    }

    public Date getDatesaisie() {
        return this.datesaisie;
    }

    public void setDatesaisie(Date datesaisie) {
        this.datesaisie = datesaisie;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getIduser() {
        return this.iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getIdverif() {
        return this.idverif;
    }

    public void setIdverif(String idverif) throws Exception {
        this.idverif = idverif;
    }

    public String getIdrempli() {
        return this.idrempli;
    }

    public void setIdrempli(String idrempli) throws Exception {
        this.idrempli = idrempli;
    }

    public Date getDateverif() {
        return this.dateverif;
    }

    public void setDateverif(Date dateverification) throws Exception {
        this.dateverif = dateverification;
    }

    public Date getDaterempli() {
        return this.daterempli;
    }

    public void setDaterempli(Date dateremplissage) throws Exception {
        this.daterempli = dateremplissage;
    }
}

