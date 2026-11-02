/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  constante.ConstanteEtat
 *  historique.MapUtilisateur
 *  historique.MapUtilisateurServiceDirection
 *  utilitaire.Utilitaire
 */
package mg.cnaps.notification;

import constante.ConstanteEtat;
import historique.MapUtilisateur;
import historique.MapUtilisateurServiceDirection;
import java.sql.Connection;
import java.sql.Time;
import java.util.Date;
import mg.cnaps.notification.NotificationMessage;
import utilitaire.Utilitaire;

public class NotificationService {
    public static void createNotification(String currentUser, String objet, String message, String userDest, String serviceDest, String directionDest, String idObjet, String lien, int priorite, int classee, Connection c) throws Exception {
        Date daty = new Date();
        java.sql.Date dateZao = new java.sql.Date(daty.getTime());
        Time timeZao = new Time(daty.getHours(), daty.getMinutes(), daty.getSeconds());
        NotificationMessage notification = new NotificationMessage();
        notification.setDaty(dateZao);
        notification.setHeure("" + timeZao);
        notification.setIduser(currentUser);
        notification.setObjet(objet);
        notification.setMessage(message);
        notification.setDestinataire(userDest);
        notification.setService(serviceDest);
        notification.setDirection(directionDest);
        notification.setIdobjet(idObjet);
        notification.setLien(lien);
        notification.setPriorite(priorite);
        notification.setClassee(classee);
        notification.setEtat(ConstanteEtat.getEtatCreer());
        notification.construirePK(c);
        notification.insertToTable(c);
    }

    public static String createNotification(String currentUser, String objet, String message, String userDest, String serviceDest, String directionDest, String idObjet, String lien, Connection c) throws Exception {
        Date daty = new Date();
        java.sql.Date dateZao = new java.sql.Date(daty.getTime());
        Time timeZao = new Time(daty.getHours(), daty.getMinutes(), daty.getSeconds());
        NotificationMessage notification = new NotificationMessage();
        notification.setDaty(dateZao);
        notification.setHeure("" + timeZao);
        notification.setIduser(currentUser);
        notification.setObjet(objet);
        notification.setMessage(message);
        notification.setDestinataire(userDest);
        notification.setIduser_recevant(userDest);
        notification.setService(serviceDest);
        notification.setDirection(directionDest);
        notification.setIdobjet(idObjet);
        notification.setLien(lien);
        notification.setEtat(ConstanteEtat.getEtatCreer());
        notification.construirePK(c);
        notification.insertToTable(c);
        return notification.getId();
    }

    public static void createNotification(java.sql.Date daty, String objetDeLaNotification, String message, String destinataire, String idobjet, String lien, MapUtilisateur u, String service, String direction, Connection c) throws Exception {
        Time timeZao = new Time(daty.getHours(), daty.getMinutes(), daty.getSeconds());
        NotificationMessage notification = new NotificationMessage();
        notification.setDaty(daty);
        notification.setHeure("" + timeZao);
        notification.setObjet(objetDeLaNotification);
        notification.setMessage(message);
        notification.setIdobjet(idobjet);
        notification.setLien(lien);
        notification.setEtat(ConstanteEtat.getEtatCreer());
        notification.setIduser(u.getTuppleID());
        notification.setService(service);
        notification.setDirection(direction);
        notification.setDestinataire(destinataire);
        notification.construirePK(c);
        notification.insertToTable(c);
    }

    public static String createNotification(java.sql.Date daty, String objetDeLaNotification, String message, String destinataire, String idobjet, String lien, MapUtilisateur u, Connection c) throws Exception {
        Time timeZao = new Time(daty.getHours(), daty.getMinutes(), daty.getSeconds());
        NotificationMessage notification = new NotificationMessage();
        notification.setDaty(daty);
        notification.setHeure("" + timeZao);
        notification.setObjet(objetDeLaNotification);
        notification.setMessage(message);
        notification.setDestinataire(destinataire);
        notification.setIdobjet(idobjet);
        notification.setLien(lien);
        notification.setEtat(ConstanteEtat.getEtatCreer());
        notification.setIduser(u.getTuppleID());
        notification.construirePK(c);
        notification.insertToTable(c);
        return notification.getId();
    }

    public static String createNotification(java.sql.Date daty, String objetDeLaNotification, String message, String destinataire, String idobjet, String lien, int priorite, MapUtilisateur u, Connection c) throws Exception {
        Time timeZao = new Time(daty.getHours(), daty.getMinutes(), daty.getSeconds());
        NotificationMessage notification = new NotificationMessage();
        notification.setDaty(daty);
        notification.setHeure("" + timeZao);
        notification.setObjet(objetDeLaNotification);
        notification.setMessage(message);
        notification.setDestinataire(destinataire);
        notification.setIdobjet(idobjet);
        notification.setLien(lien);
        notification.setPriorite(priorite);
        notification.setEtat(ConstanteEtat.getEtatCreer());
        notification.setIduser(u.getTuppleID());
        notification.construirePK(c);
        notification.insertToTable(c);
        return notification.getId();
    }

    public static String createNotification(java.sql.Date daty, String objetDeLaNotification, String message, String destinataire, String idobjet, String lien, int priorite, String iduser, String direction, String service, MapUtilisateur u, Connection c) throws Exception {
        Time timeZao = new Time(daty.getHours(), daty.getMinutes(), daty.getSeconds());
        NotificationMessage notification = new NotificationMessage();
        notification.setDaty(daty);
        notification.setHeure("" + timeZao);
        notification.setObjet(objetDeLaNotification);
        notification.setMessage(message);
        notification.setDestinataire(destinataire);
        notification.setIdobjet(idobjet);
        notification.setLien(lien);
        notification.setPriorite(priorite);
        notification.setEtat(ConstanteEtat.getEtatCreer());
        notification.setIduser(u.getTuppleID());
        notification.setIduser(iduser);
        notification.setDirection(direction);
        notification.setService(service);
        notification.construirePK(c);
        notification.insertToTable(c);
        return notification.getId();
    }

    public static void updateNotif(String idnotif, String serviceDest, String directionDest, String userDest, Connection c) throws Exception {
        String apresSet = " daty ='" + Utilitaire.dateDuJour() + "'";
        if (serviceDest != null && serviceDest.compareToIgnoreCase("") != 0) {
            apresSet = apresSet + " , service = '" + serviceDest + "'";
        }
        if (directionDest != null && directionDest.compareToIgnoreCase("") != 0) {
            apresSet = apresSet + " , direction = '" + directionDest + "'";
        }
        if (userDest != null && userDest.compareToIgnoreCase("") != 0) {
            apresSet = apresSet + " , destinataire = '" + userDest + "'";
        }
        NotificationMessage notif = new NotificationMessage();
        apresSet = apresSet + " where id = '" + idnotif + "'";
        notif.updateToTable(apresSet, c);
    }

    public static String conditionLectureNotification(MapUtilisateurServiceDirection currentUser) {
        return "";
    }

    public static String createNotificationAccueil(String prestation, String currentUser, String objet, String message, String userDest, String serviceDest, String directionDest, String idObjet, String lien, Connection c) throws Exception {
        NotificationMessage notification = new NotificationMessage();
        notification.setDaty(Utilitaire.dateDuJourSql());
        notification.setIduser(currentUser);
        notification.setObjet(objet);
        notification.setMessage(message);
        notification.setDestinataire(userDest);
        notification.setService(serviceDest);
        notification.setDirection(directionDest);
        notification.setIdobjet(idObjet);
        notification.setLien(lien);
        notification.setPrestation(prestation);
        notification.setEtat(ConstanteEtat.getEtatCreer());
        notification.construirePK(c);
        notification.insertToTable(c);
        return notification.getId();
    }
}

