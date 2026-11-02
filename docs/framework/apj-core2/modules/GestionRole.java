/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.CGenUtil
 *  bean.ClassMAPTable
 *  javax.servlet.http.HttpServletRequest
 *  utilitaire.UtilDB
 */
package modules;

import bean.CGenUtil;
import bean.ClassMAPTable;
import config.Table;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import utilisateur.Restriction;
import utilitaire.UtilDB;

public class GestionRole {
    public int testRestriction(String idrole, String permission, String table, String direc, Connection c) throws Exception {
        Restriction r = new Restriction();
        r.setTablename(table);
        String aw = " AND idaction='" + permission + "' and IDDIRECTION='" + direc + "'";
        Restriction[] res = (Restriction[])CGenUtil.rechercher((ClassMAPTable)r, null, null, (Connection)c, (String)aw);
        if (res != null && res.length != 0) {
            return 1;
        }
        return 0;
    }

    public String[] getAllTAble(Connection con) throws Exception {
        String req = "SELECT table_name FROM matable";
        Statement s = con.createStatement();
        ResultSet res = s.executeQuery(req);
        Vector<String> v = new Vector<String>();
        while (res.next()) {
            String st = res.getString(1);
            v.add(st);
        }
        Object[] valin = new String[v.size()];
        v.copyInto(valin);
        return valin;
    }

    public void ajoutrestriction(String[] valtab, String idrole, String act, Connection c) throws Exception {
        try {
            for (int i = 0; i < valtab.length; ++i) {
                Restriction r = new Restriction(idrole, act, valtab[i]);
                r.construirePK(c);
                r.insertToTableWithHisto("1060", c);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public void ajoutrestriction(String[] valtab, String idrole, String act, String depart, Connection c) throws Exception {
        try {
            for (int i = 0; i < valtab.length; ++i) {
                Restriction r = new Restriction(idrole, act, valtab[i], null, null, depart);
                r.construirePK(c);
                r.insertToTableWithHisto("1060", c);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public void modifRestriction(String[] valtab, String idrole, String ajout, String modif, String suppr, String read, HttpServletRequest req, Connection c) throws Exception {
    }

    public void updateRestriction(String tab, String rest, String role, String act, HttpServletRequest req, Connection c) throws Exception {
        try {
            Restriction r = new Restriction();
            r.setId(rest);
            r.deleteToTable();
            if (act != null) {
                r = new Restriction(role, act, tab);
                r.insertToTableWithHisto("UPDATE RESTRICTION");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Table[] getListeTable(String role, String adr) throws Exception {
        try (Connection con = null;){
            con = new UtilDB().GetConn();
            String req = "SELECT * FROM matable where 1<2 and  table_name not in(select r.tablename from restriction r where r.idrole='" + role + "' and r.iddirection='" + adr + "')";
            Statement s = con.createStatement();
            ResultSet res = s.executeQuery(req);
            Vector<Table> v = new Vector<Table>();
            while (res.next()) {
                Table st = new Table();
                st.setTable_name(res.getString(1));
                v.add(st);
            }
            Table[] valin = new Table[v.size()];
            v.copyInto((Object[])valin);
            Table[] tableArray = valin;
            return tableArray;
        }
    }
}

