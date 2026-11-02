/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.ejb.Remote
 */
package historique;

import bean.ClassMAPTable;
import bean.ErreurDAO;
import historique.HistoriqueEtat;
import historique.MapHistorique;
import historique.MapRoles;
import historique.MapUtilisateur;
import historique.Objet;
import javax.ejb.Remote;

@Remote
public interface HistoriqueRemote {
    public MapHistorique[] findHistorique(String var1, String var2, String var3, String var4, String var5, String var6) throws Exception;

    public MapHistorique[] findHistoriquePage(String var1, String var2, String var3, String var4, String var5, String var6, int var7) throws Exception;

    public int calculHistoriquePage(String var1, String var2, String var3, String var4, String var5, String var6) throws Exception;

    public Objet[] findObjet(String var1, String var2) throws Exception;

    public MapRoles[] findRole(String var1);

    public String createUtilisateurs(String var1, String var2, String var3, String var4, String var5, String var6, String var7) throws Exception;

    public String updateUtilisateurs(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8) throws Exception;

    public int deleteUtilisateurs(String var1, String var2);

    public MapUtilisateur[] findUtilisateurs(String var1, String var2, String var3, String var4, String var5, String var6, String var7) throws Exception;

    public String createHistorique(String var1, String var2, String var3, String var4, String var5, String var6);

    public String updateHistorique(String var1, String var2, String var3, String var4, String var5, String var6, String var7);

    public int deleteHistorique(String var1, String var2);

    public String createHistoriqueEtat(String var1, String var2, String var3, String var4);

    public String updateHistoriqueEtat(String var1, String var2, String var3, String var4, String var5);

    public int deleteHistoriqueEtat(String var1, String var2);

    public HistoriqueEtat[] findHistoriqueEtat(String var1, String var2, String var3, String var4, String var5);

    public int desactiveUtilisateur(String var1, String var2) throws Exception;

    public int activeUtilisateur(String var1, String var2) throws ErreurDAO;

    public MapUtilisateur testeValide(String var1, String var2, String var3, String var4) throws Exception;

    public int annulerSortie(String var1);

    public int testException() throws ErreurDAO;

    public int create(ClassMAPTable var1, MapUtilisateur var2) throws Exception;

    public int update(ClassMAPTable var1, MapUtilisateur var2) throws Exception;

    public int delete(ClassMAPTable var1, MapUtilisateur var2) throws Exception;
}

