/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.ejb.EJBObject
 */
package historique;

import bean.ClassMAPTable;
import bean.ErreurDAO;
import historique.HistoriqueEtat;
import historique.MapHistorique;
import historique.MapRoles;
import historique.MapUtilisateur;
import historique.Objet;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface Historique
extends EJBObject {
    public MapHistorique[] findHistorique(String var1, String var2, String var3, String var4, String var5, String var6) throws Exception, RemoteException;

    public MapHistorique[] findHistoriquePage(String var1, String var2, String var3, String var4, String var5, String var6, int var7) throws Exception, RemoteException;

    public int calculHistoriquePage(String var1, String var2, String var3, String var4, String var5, String var6) throws Exception, RemoteException;

    public Objet[] findObjet(String var1, String var2) throws Exception, RemoteException;

    public MapRoles[] findRole(String var1) throws RemoteException;

    public int deleteUtilisateurs(String var1, String var2) throws RemoteException;

    public String createHistorique(String var1, String var2, String var3, String var4, String var5, String var6) throws RemoteException;

    public String updateHistorique(String var1, String var2, String var3, String var4, String var5, String var6, String var7) throws RemoteException;

    public int deleteHistorique(String var1, String var2) throws RemoteException;

    public String createHistoriqueEtat(String var1, String var2, String var3, String var4) throws RemoteException;

    public String updateHistoriqueEtat(String var1, String var2, String var3, String var4, String var5) throws RemoteException;

    public int deleteHistoriqueEtat(String var1, String var2) throws RemoteException;

    public HistoriqueEtat[] findHistoriqueEtat(String var1, String var2, String var3, String var4, String var5) throws RemoteException;

    public int desactiveUtilisateur(String var1, String var2) throws Exception, RemoteException;

    public int activeUtilisateur(String var1, String var2) throws ErreurDAO, RemoteException;

    public MapUtilisateur testeValide(String var1, String var2) throws Exception, RemoteException;

    public int annulerSortie(String var1) throws RemoteException;

    public int testException() throws ErreurDAO, RemoteException;

    public int create(ClassMAPTable var1, MapUtilisateur var2) throws Exception, RemoteException;

    public int update(ClassMAPTable var1, MapUtilisateur var2) throws Exception, RemoteException;

    public int delete(ClassMAPTable var1, MapUtilisateur var2) throws Exception, RemoteException;

    public String createUtilisateurs(String var1, String var2, String var3, String var4, String var5, String var6, String var7) throws Exception, RemoteException;

    public String updateUtilisateurs(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8) throws Exception, RemoteException;

    public MapUtilisateur[] findUtilisateurs(String var1, String var2, String var3, String var4, String var5, String var6, String var7) throws Exception, RemoteException;
}

