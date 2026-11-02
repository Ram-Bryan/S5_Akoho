/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.ClassMAPTable;

public class RoleValidation
extends ClassMAPTable {
    private String id;
    private String role;
    private String personne;
    private String mapping;
    private String refObject;
    private int niveau;
    private int valeurEtat;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPersonne() {
        return this.personne;
    }

    public void setPersonne(String personne) {
        this.personne = personne;
    }

    public String getMapping() {
        return this.mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public String getRefObject() {
        return this.refObject;
    }

    public void setRefObject(String refObject) {
        this.refObject = refObject;
    }

    public int getNiveau() {
        return this.niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public int getValeurEtat() {
        return this.valeurEtat;
    }

    public void setValeurEtat(int valeurEtat) {
        this.valeurEtat = valeurEtat;
    }

    @Override
    public String getTuppleID() {
        return this.getId();
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }

    public RoleValidation() {
        this.setNomTable("VALIDATION_ROLE");
    }
}

