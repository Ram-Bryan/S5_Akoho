/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.Champ;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import utilitaire.UtilDB;

public class Table {
    String nomTable;
    int nombreCol;
    Champ[] champ;

    public Table() {
    }

    public Table(String nom) {
        this.nomTable = nom;
    }

    public Table(String nom, Connection c) {
        this.nomTable = nom;
    }

    public Champ[] getChamp() {
        this.findInfoTable();
        return this.champ;
    }

    public Champ[] getChamp(Connection c) {
        this.findInfoTable(c);
        return this.champ;
    }

    public String getNomTable() {
        return this.nomTable;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void findInfoTable() {
        UtilDB util = null;
        Connection c = null;
        try {
            util = new UtilDB();
            c = util.GetConn();
            this.findInfoTable(c);
        }
        catch (Exception s) {
            System.out.println(String.valueOf(String.valueOf(new StringBuffer("RechercheExecution44444 ").append(this.getNomTable()).append(" par Reference ").append(s.getMessage()))));
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL RechercheExecution ".concat(String.valueOf(String.valueOf(e.getMessage()))));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void findInfoTable(Connection c) {
        Object util = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String param = "SELECT * FROM USER_TAB_COLUMNS where table_Name = upper(?) order by column_id asc";
            st = c.prepareStatement(param);
            st.setString(1, this.nomTable);
            rs = st.executeQuery();
            Vector<Champ> v = new Vector<Champ>();
            while (rs.next()) {
                v.add(new Champ(rs.getString(2), rs.getString(3), rs.getInt(7), rs.getInt(8)));
            }
            this.champ = new Champ[v.size()];
            v.copyInto(this.champ);
        }
        catch (Exception s) {
            s.printStackTrace();
            System.out.println("findInfoTable " + this.getNomTable() + " par Reference ");
        }
        finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (rs != null) {
                    rs.close();
                }
            }
            catch (SQLException e) {
                System.out.println("Erreur Fermeture SQL RechercheExecution ".concat(String.valueOf(String.valueOf(e.getMessage()))));
            }
        }
    }

    public void setNomTable(String nomTable) {
        this.nomTable = nomTable;
    }
}

