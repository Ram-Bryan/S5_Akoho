/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassEtat
 *  bean.ClassMAPTable
 *  historique.MapUtilisateur
 *  user.UserEJB
 *  utilitaire.UtilDB
 *  utilitaire.Utilitaire
 */
package notification;

import bean.ClassEtat;
import bean.ClassMAPTable;
import historique.MapUtilisateur;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.sql.Connection;
import java.sql.Date;
import user.UserEJB;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;

public class Notification
extends ClassEtat {
    private String id;
    private String message;
    private String receiverlib;
    private String etatlib;
    private int receiver;
    private Date daty;
    private String heure;
    private String ecartdate;

    public String getEcartdate() {
        return this.ecartdate;
    }

    public void setEcartdate(String ecartdate) {
        this.ecartdate = ecartdate;
    }

    public Notification() {
        super.setNomTable("notification2");
    }

    public String getEtatlib() {
        return this.etatlib;
    }

    public void setEtatlib(String etatlib) {
        this.etatlib = etatlib;
    }

    public String getReceiverlib() {
        return this.receiverlib;
    }

    public void setReceiverlib(String receiverlib) {
        this.receiverlib = receiverlib;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHeure() {
        return this.heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public int getReceiver() {
        return this.receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public Date getDaty() {
        return this.daty;
    }

    public void setDaty(Date daty) {
        this.daty = daty;
    }

    public String getTuppleID() {
        return this.id;
    }

    public String getAttributIDName() {
        return "id";
    }

    public void construirePK(Connection c) throws Exception {
        this.preparePk("NT", "getseq_notification");
        this.setId(this.makePK(c));
    }

    public void vu(UserEJB u, Connection c) throws Exception {
        boolean canClose = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                c.setAutoCommit(false);
                canClose = true;
            }
            u.validerObject((ClassMAPTable)this);
        }
        catch (Exception e) {
            if (c != null && canClose) {
                c.rollback();
            }
            throw e;
        }
        finally {
            if (c != null && canClose) {
                c.close();
            }
        }
    }

    public void vu(UserEJB u, Connection c, Notification[] notifs) throws Exception {
        boolean canClose = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                canClose = true;
            }
            for (Notification notif : notifs) {
                notif.vu(u, c);
            }
        }
        catch (Exception ex) {
            if (c != null) {
                c.rollback();
            }
            throw ex;
        }
        finally {
            if (c != null && canClose) {
                c.close();
            }
        }
    }

    public void creerNotification(String u, Connection c) throws Exception {
        boolean canClose = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                canClose = true;
            }
            this.setDaty(Utilitaire.dateDuJourSql());
            this.setHeure(Utilitaire.heureCouranteHMS());
            this.createObject(u, c);
        }
        catch (Exception ex) {
            if (c != null) {
                c.rollback();
            }
            throw ex;
        }
        finally {
            if (c != null && canClose) {
                c.close();
            }
        }
    }

    public void creerNotificationMultiple(String u, MapUtilisateur[] receiver, Connection c) throws Exception {
        boolean canClose = false;
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                canClose = true;
            }
            for (MapUtilisateur r : receiver) {
                Notification n = new Notification();
                n.setReceiver(r.getRefuser());
                n.setMessage(this.getMessage());
                n.creerNotification(u, c);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            if (c != null) {
                c.rollback();
            }
            throw e;
        }
        finally {
            if (c != null && canClose) {
                c.close();
            }
        }
    }

    public static void notifSys(String message) throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);
        trayIcon.displayMessage(" ", message, TrayIcon.MessageType.INFO);
    }

    public String toString() {
        return "Notification [ message=" + this.message + ", receiver=" + this.receiver + "]";
    }
}

