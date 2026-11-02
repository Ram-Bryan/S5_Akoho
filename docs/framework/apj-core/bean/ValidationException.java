/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.ClassMAPTable;
import java.util.List;

public class ValidationException
extends Exception {
    private String messageavalider;
    private boolean response = false;
    private ClassMAPTable objet;
    private String acte;
    private List<ClassMAPTable> liste_object;

    public ClassMAPTable getObjet() {
        return this.objet;
    }

    public void setObjet(ClassMAPTable objet) {
        this.objet = objet;
    }

    public String getActe() {
        return this.acte;
    }

    public void setActe(String acte) {
        this.acte = acte;
    }

    public boolean isResponse() {
        return this.response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public String getMessageavalider() {
        return this.messageavalider;
    }

    public void setMessageavalider(String messageavalider) {
        this.messageavalider = messageavalider;
    }

    public ValidationException(String messageavalider) {
        this.setMessageavalider(messageavalider);
    }

    public ValidationException(String messageavalider, ClassMAPTable objet, String acte) {
        this.messageavalider = messageavalider;
        this.objet = objet;
        this.acte = acte;
    }

    public ValidationException(String messageavalider, String acte) {
        this.messageavalider = messageavalider;
        this.acte = acte;
    }

    public ValidationException(String message, boolean response) {
        this.setMessageavalider(this.messageavalider);
        this.setResponse(response);
    }

    public List<ClassMAPTable> getListe_object() {
        return this.liste_object;
    }

    public void setListe_object(List<ClassMAPTable> liste_object) {
        this.liste_object = liste_object;
    }
}

