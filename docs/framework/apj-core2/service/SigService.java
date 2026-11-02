/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.CGenUtil
 *  bean.ClassGeom
 *  bean.ClassMAPTable
 *  javax.servlet.http.HttpServletRequest
 *  org.postgis.LineString
 *  org.postgis.Point
 *  org.postgis.Polygon
 */
package service;

import bean.CGenUtil;
import bean.ClassGeom;
import bean.ClassMAPTable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.postgis.LineString;
import org.postgis.Point;
import org.postgis.Polygon;

public class SigService {
    public Object avoirInstance(String className) throws Exception {
        Object o = null;
        Class<?> clazz = Class.forName(className);
        Constructor<?> ctor = clazz.getConstructor(null);
        o = ctor.newInstance(null);
        return o;
    }

    public String[] getFieldName(ClassMAPTable c) throws Exception {
        String[] val = null;
        Field[] liste = c.getFieldList();
        if (liste != null) {
            val = new String[liste.length];
            for (int i = 0; i < liste.length; ++i) {
                val[i] = liste[i].getName();
            }
        }
        return val;
    }

    public ClassGeom[] callfunction(String functionName, String couche1, String couche2) throws Exception {
        ClassGeom[] liste = null;
        Class<?> base = Class.forName("bean.ClassGeom");
        Class<?> clazz = Class.forName(couche1);
        Constructor<?> ctor = clazz.getConstructor(null);
        Object object = ctor.newInstance(null);
        Class<?> clazz1 = Class.forName(couche2);
        Constructor<?> ctor1 = clazz1.getConstructor(null);
        Object object1 = ctor1.newInstance(null);
        Class<?> clazz2 = Class.forName("java.lang.String");
        Class[] cArg = new Class[]{base, base, clazz2};
        String aw = "";
        Object[] liste11 = new Object[]{object, object1, aw};
        if (functionName.compareTo("around") == 0) {
            liste11 = new Object[]{object, object1, "1", aw};
            cArg = new Class[]{base, base, clazz2, Class.forName("java.lang.String")};
        }
        liste = (ClassGeom[])this.getClass().getMethod(functionName, cArg).invoke(this, liste11);
        return liste;
    }

    public HashMap<String, String> getFonction() {
        HashMap<String, String> liste = new HashMap<String, String>();
        liste.put("Autour de", "around");
        liste.put("Est disjoint de", "isDisjoint");
        liste.put("Intersecte", "intersect");
        liste.put("Contient", "contains");
        liste.put("Coupe", "cut");
        liste.put("Touche", "touch");
        return liste;
    }

    public ClassGeom[] around(ClassGeom surround, ClassGeom center, String distance, String aw, HttpServletRequest request) throws Exception {
        ClassGeom[] liste = null;
        String wr = "";
        Field[] critere = surround.getFieldList();
        for (int i = 0; i < critere.length; ++i) {
            Object val = surround.getValField(critere[i]);
            if (val == null || request.getParameter(critere[i].getName()) == null) continue;
            wr = val instanceof Integer || val instanceof Double ? wr + " and b." + critere[i].getName() + " = '" + val + "'" : wr + " and b." + critere[i].getName() + " like '%" + val + "%'";
        }
        String requete = "SELECT a.*,ST_Distance(a.geom, b.geom)\nFROM " + surround.getNomTable() + " a\nLEFT JOIN " + center.getNomTable() + " b\nON ST_DWithin(a.geom, b.geom, " + distance + ")\nwhere ST_Distance(a.geom, b.geom)>0 " + wr + " " + aw;
        System.out.println(requete);
        liste = (ClassGeom[])CGenUtil.rechercher((ClassMAPTable)surround, (String)requete);
        return liste;
    }

    public ClassGeom[] intersect(ClassGeom surround, ClassGeom center, String aw) throws Exception {
        ClassGeom[] liste = null;
        String requete = "SELECT a.*,ST_Intersects(a.geom, b.geom)\nFROM " + surround.getNomTable() + " a\nLEFT JOIN " + center.getNomTable() + " b\nON ST_Intersects(a.geom, b.geom)\nwhere ST_Intersects(a.geom, b.geom) is not null " + aw;
        System.out.println("requete=" + requete);
        liste = (ClassGeom[])CGenUtil.rechercher((ClassMAPTable)surround, (String)requete);
        return liste;
    }

    public ClassGeom[] isDisjoint(ClassGeom surround, ClassGeom center, String aw) throws Exception {
        ClassGeom[] liste = null;
        String requete = "SELECT a.*,ST_Disjoint(a.geom, b.geom)\nFROM " + surround.getNomTable() + " a\nLEFT JOIN " + center.getNomTable() + " b\nON ST_Disjoint(a.geom, b.geom)\nwhere ST_Disjoint(a.geom, b.geom) is not null " + aw;
        liste = (ClassGeom[])CGenUtil.rechercher((ClassMAPTable)surround, (String)requete);
        System.out.println(requete);
        return liste;
    }

    public ClassGeom[] contains(ClassGeom surround, ClassGeom center, String aw) throws Exception {
        ClassGeom[] liste = null;
        String requete = "SELECT a.*,ST_Contains(a.geom, b.geom)\nFROM " + surround.getNomTable() + " a\nLEFT JOIN " + center.getNomTable() + " b\nON ST_Contains(a.geom, b.geom)\nwhere ST_Contains(a.geom, b.geom) is not null " + aw;
        System.out.println(requete);
        liste = (ClassGeom[])CGenUtil.rechercher((ClassMAPTable)surround, (String)requete);
        return liste;
    }

    public ClassGeom[] cut(ClassGeom surround, ClassGeom center, String aw) throws Exception {
        ClassGeom[] liste = null;
        String requete = "SELECT a.*,ST_Crosses(a.geom, b.geom)\nFROM " + surround.getNomTable() + " a\nLEFT JOIN " + center.getNomTable() + " b\nON ST_Crosses(a.geom, b.geom)\nwhere ST_Crosses(a.geom, b.geom) is not null " + aw;
        System.out.println(requete);
        liste = (ClassGeom[])CGenUtil.rechercher((ClassMAPTable)surround, (String)requete);
        return liste;
    }

    public ClassGeom[] touch(ClassGeom surround, ClassGeom center, String aw) throws Exception {
        ClassGeom[] liste = null;
        String requete = "SELECT a.*,ST_Touches(a.geom, b.geom)\nFROM " + surround.getNomTable() + " a\nLEFT JOIN " + center.getNomTable() + " b\nON ST_Touches(a.geom, b.geom)\nwhere ST_Touches(a.geom, b.geom) is not null " + aw;
        System.out.println(requete);
        liste = (ClassGeom[])CGenUtil.rechercher((ClassMAPTable)surround, (String)requete);
        return liste;
    }

    public int getTypeGeom(ClassGeom geometry) throws Exception {
        int val = 0;
        if (CGenUtil.getValeurFieldByMethod((ClassMAPTable)geometry, (String)"geom") != null) {
            String value = CGenUtil.getValeurFieldByMethod((ClassMAPTable)geometry, (String)"geom").toString();
            if (value.contains("POINT")) {
                return 1;
            }
            if (value.contains("POLYGON")) {
                return 2;
            }
            if (value.contains("LINESTRING")) {
                return 3;
            }
            if (value.contains("CIRCLE")) {
                return 4;
            }
        }
        return val;
    }

    public Point[] getAllPoints(ClassGeom geometry) throws Exception {
        Point[] liste = null;
        int test = this.getTypeGeom(geometry);
        switch (test) {
            case 1: {
                liste = new Point[]{new Point(CGenUtil.getValeurFieldByMethod((ClassMAPTable)geometry, (String)"geom").toString())};
                break;
            }
            case 2: {
                Polygon p = new Polygon(CGenUtil.getValeurFieldByMethod((ClassMAPTable)geometry, (String)"geom").toString());
                liste = p.getRing(0).getPoints();
                break;
            }
            case 3: {
                LineString l = new LineString(CGenUtil.getValeurFieldByMethod((ClassMAPTable)geometry, (String)"geom").toString());
                liste = l.getPoints();
            }
        }
        return liste;
    }

    public int getTypeGeom(ClassGeom geometry, String champ) throws Exception {
        int val = 0;
        if (CGenUtil.getValeurFieldByMethod((ClassMAPTable)geometry, (String)champ) != null) {
            String value = CGenUtil.getValeurFieldByMethod((ClassMAPTable)geometry, (String)champ).toString();
            if (value.contains("POINT")) {
                return 1;
            }
            if (value.contains("POLYGON")) {
                return 2;
            }
            if (value.contains("LINESTRING")) {
                return 3;
            }
            if (value.contains("CIRCLE")) {
                return 4;
            }
        }
        return val;
    }

    public Point[] getAllPoints(ClassGeom geometry, String champ) throws Exception {
        Point[] liste = null;
        int test = this.getTypeGeom(geometry, champ);
        switch (test) {
            case 1: {
                liste = new Point[]{new Point(CGenUtil.getValeurFieldByMethod((ClassMAPTable)geometry, (String)champ).toString())};
                break;
            }
            case 2: {
                Polygon p = new Polygon(CGenUtil.getValeurFieldByMethod((ClassMAPTable)geometry, (String)champ).toString());
                liste = p.getRing(0).getPoints();
                break;
            }
            case 3: {
                LineString l = new LineString(CGenUtil.getValeurFieldByMethod((ClassMAPTable)geometry, (String)champ).toString());
                liste = l.getPoints();
            }
        }
        return liste;
    }
}

