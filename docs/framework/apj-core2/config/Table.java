/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 *  utilitaire.UtilDB
 */
package config;

import bean.ClassMAPTable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import utilitaire.UtilDB;

public class Table
extends ClassMAPTable {
    private String table_name;

    public Table() {
        this.setNomTable("table");
    }

    public Table(String name) {
        this.setNomTable("table");
        this.table_name = name;
    }

    public String getTuppleID() {
        return null;
    }

    public String getAttributIDName() {
        return null;
    }

    public String getTable_name() {
        return this.table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public Table[] getListeTable(String role) throws SQLException, Exception {
        try (Connection con = new UtilDB().GetConn();){
            String req = "SELECT * FROM matable where 1<2 and  table_name not in(select r.nomtable from restriction r where r.idrole='" + role + "')";
            System.out.print(req);
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

    public Table[] getListeTable(String role, String adr) throws SQLException, Exception {
        try (Connection con = new UtilDB().GetConn();){
            String req = "SELECT * FROM matable where 1<2 and  table_name not in(select r.tablename from restriction r where r.idrole='" + role + "' and r.iddirection='" + adr + ")";
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

