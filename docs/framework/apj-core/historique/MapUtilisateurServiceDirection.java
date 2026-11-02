/*
 * Decompiled with CFR 0.152.
 */
package historique;

import historique.MapUtilisateur;

public class MapUtilisateurServiceDirection
extends MapUtilisateur {
    String service;
    String direction;

    public MapUtilisateurServiceDirection(int refus, String loginus, String pwdus, String nomus, String adrus, String telus, String idrol, String service, String direction) throws Exception {
        super(refus, loginus, pwdus, nomus, adrus, telus, idrol);
        this.setService(service);
        this.setDirection(direction);
    }

    public MapUtilisateurServiceDirection() {
        super.setNomTable("utilisateurrole");
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String getTuppleID() {
        return String.valueOf(this.getRefuser());
    }
}

