/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 *  historique.MapUtilisateur
 *  utilitaire.ConstanteUser
 */
package utilisateur;

import bean.ClassMAPTable;
import historique.MapUtilisateur;
import java.sql.Connection;
import utilitaire.ConstanteUser;

public class ActionRole
extends ClassMAPTable {
    private String id;
    private String nomTableObjet;
    private String action;
    private int roleminimum;
    private String direction;
    String utilisateur;
    String groupeTable;

    public String getGroupeTable() {
        return this.groupeTable;
    }

    public void setGroupeTable(String groupeTable) {
        this.groupeTable = groupeTable;
    }

    public String getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public ActionRole() {
        super.setNomTable("ACTIONROLE");
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void construirePK(Connection c) throws Exception {
        this.preparePk("ACT", "getSeqactionrole");
        this.setId(this.makePK(c));
    }

    public String getTuppleID() {
        return this.id;
    }

    public String getAttributIDName() {
        return "id";
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getRoleminimum() {
        return this.roleminimum;
    }

    public void setRoleminimum(int roleminimum) {
        this.roleminimum = roleminimum;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean estAutorise(MapUtilisateur user, String action, ClassMAPTable objet) throws Exception {
        boolean directionAutorisee = false;
        if (this.getDirection() == null || this.getDirection().compareToIgnoreCase("") == 0 || "%".equalsIgnoreCase(this.getDirection())) {
            directionAutorisee = true;
        } else if (this.getDirection() != null && !this.getDirection().trim().isEmpty()) {
            String[] dir;
            for (String d : dir = this.getDirection().split(";")) {
                if (!user.getAdruser().equalsIgnoreCase(d.trim())) continue;
                directionAutorisee = true;
                break;
            }
        }
        if (this.getGroupeTable() != null && !objet.estDansleGroupeTable(this.getGroupeTable())) {
            return false;
        }
        if (this.getNomTableObjet() != null && this.getNomTableObjet().compareToIgnoreCase(objet.getNomTable()) != 0) {
            return false;
        }
        if (this.getRoleminimum() > user.getRang()) {
            return false;
        }
        if (this.getAction() != null && this.getAction().compareToIgnoreCase("") != 0 && this.getAction().compareToIgnoreCase(action) != 0) {
            return false;
        }
        return directionAutorisee;
    }

    public static boolean estAutorise(ActionRole[] listeRole, MapUtilisateur user, String action, ClassMAPTable objet) throws Exception {
        if ("HISTORIQUE".equalsIgnoreCase(objet.getNomTable()) || user.getRang() >= ConstanteUser.getRangDG()) {
            return true;
        }
        if (listeRole == null || listeRole.length == 0) {
            return true;
        }
        for (ActionRole rol : listeRole) {
            if (!rol.estAutorise(user, action, objet)) continue;
            return true;
        }
        return false;
    }

    public static boolean estAutorise(String action, ClassMAPTable objet) throws Exception {
        return true;
    }

    public String getNomTableObjet() {
        return this.nomTableObjet;
    }

    public void setNomTableObjet(String nomTableObjet) {
        this.nomTableObjet = nomTableObjet;
    }
}

