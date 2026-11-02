/*
 * Decompiled with CFR 0.152.
 */
package mg.cnaps.notification;

import mg.cnaps.notification.NotificationLibelle;

public class NotificationLibelleS
extends NotificationLibelle {
    private String sous_prestation_libelle;
    private String heure;

    public NotificationLibelleS() {
        super.setNomTable("NOTIFICATION_LIBELLE3");
    }

    public String getSous_prestation_libelle() {
        return this.sous_prestation_libelle;
    }

    public void setSous_prestation_libelle(String sous_prestation_libelle) {
        this.sous_prestation_libelle = sous_prestation_libelle;
    }

    public String getHeure() {
        return this.heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }
}

