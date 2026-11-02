/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import utilitaire.UtilDB;

public class Parametre {
    static String ref = "1";
    static String user = "haja";
    static String pwd = "haja";
    static String serveurApp = "localhost";
    static String nomJndiDB = "Port";
    static String monographieJNDI = "MonographieJNDI";
    static String entrepriseJNDI = "Entreprise";
    static String transportJNDI = "TransportRemote";
    static String santeJNDI = "CentresanteRemote";
    static String telecomJNDI = "Telecom";
    static String trasnportJNDI = "Transport";
    static String ministereJNDI = "Ministere";
    static String serviceadmJNDI = "Serviceadm";
    static String infoetcommunicJNDI = "Infoetcommunic";
    static int reductionTrafic = 20;
    static int nbParPage = 100;
    static int exercice = 2009;
    public static final int maxRecherche = 50;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void getParametre() {
        Connection c = null;
        Statement st = null;
        try {
            c = new UtilDB().GetConn();
            String param = "SELECT * FROM parametre WHERE ref LIKE ?";
            st = c.prepareStatement(param);
            st.setString(1, "1");
            ResultSet rs = st.executeQuery();
            rs.next();
            nbParPage = rs.getInt(3);
            exercice = rs.getInt(2);
        }
        catch (SQLException s) {
            System.out.println("Parametre non recu ".concat(String.valueOf(String.valueOf(s.getMessage()))));
        }
        finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (c != null) {
                    c.close();
                }
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL Parametre ".concat(String.valueOf(String.valueOf(e.getMessage()))));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int updParametre() {
        Connection c = null;
        Statement st = null;
        try {
            int i;
            c = new UtilDB().GetConn();
            String param = "update parametre set reductionTrafic=?,nbPage=? ref LIKE ?";
            st = c.prepareStatement(param);
            st.setInt(1, reductionTrafic);
            st.setInt(2, nbParPage);
            st.setInt(4, 1);
            int n = i = st.executeUpdate();
            return n;
        }
        catch (SQLException s) {
            int j;
            System.out.println("Parametre non recu ".concat(String.valueOf(String.valueOf(s.getMessage()))));
            int n = j = 0;
            return n;
        }
        finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (c != null) {
                    c.close();
                }
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL Parametre ".concat(String.valueOf(String.valueOf(e.getMessage()))));
            }
        }
    }

    public static String getNomJndiDB() {
        return nomJndiDB;
    }

    public static void setNomJndiDB(String nomJnd) {
        nomJndiDB = nomJnd;
    }

    public static String getPwd() {
        return pwd;
    }

    public static void setPwd(String pwdd) {
        pwd = pwdd;
    }

    public static String getServeurApp() {
        return serveurApp;
    }

    public static void setServeurApp(String serveurAppl) {
        serveurApp = serveurAppl;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String usere) {
        user = usere;
    }

    public static String getMonographieJNDI() {
        return monographieJNDI;
    }

    public static void setMonographieJNDI(String mono) {
        monographieJNDI = mono;
    }

    public static String getEntrepriseJNDI() {
        return entrepriseJNDI;
    }

    public static void setEntrepriseJNDI(String mono) {
        entrepriseJNDI = mono;
    }

    public static String getTransportJNDI() {
        return transportJNDI;
    }

    public static void setTransportJNDI(String mono) {
        transportJNDI = mono;
    }

    public static String getSanteJNDI() {
        return santeJNDI;
    }

    public static void setSanteJNDI(String mono) {
        santeJNDI = mono;
    }

    public static String getTelecomJNDI() {
        return telecomJNDI;
    }

    public static void setTelecomJNDI(String mono) {
        telecomJNDI = mono;
    }

    public static void setReductionTrafic(int red) {
        reductionTrafic = red;
    }

    public static int getReductionTrafic() {
        return reductionTrafic;
    }

    public static void setNbParPage(int page) {
        nbParPage = page;
    }

    public static int getNbParPage() {
        return nbParPage;
    }
}

