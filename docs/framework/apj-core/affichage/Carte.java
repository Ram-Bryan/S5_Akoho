/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.postgis.Point
 *  service.SigService
 */
package affichage;

import affichage.ElementCarte;
import bean.CGenUtil;
import bean.ClassGeom;
import bean.ClassMAPTable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.postgis.Point;
import service.SigService;

public class Carte {
    private ClassMAPTable[] data;
    private String libelleMarker = "id";
    private boolean isCreate = false;
    private String legende;
    private String[] libelleMarkerlist;
    private String lien;

    public boolean isIsCreate() {
        return this.isCreate;
    }

    public void setIsCreate(boolean isCreate) {
        this.isCreate = isCreate;
    }

    public String[] getLibelleMarkerlist() {
        return this.libelleMarkerlist;
    }

    public void setLibelleMarkerlist(String[] libelleMarkerlist) {
        this.libelleMarkerlist = libelleMarkerlist;
    }

    public Carte() {
    }

    public Carte(ClassMAPTable[] data) {
        this.setData(data);
    }

    public Carte(ClassMAPTable[] data, String libelleMarker) {
        this.data = data;
        this.libelleMarker = libelleMarker;
    }

    public Carte(ClassMAPTable[] data, String libelleMarker, String[] libelleMarkerlist) {
        this.data = data;
        this.libelleMarker = libelleMarker;
        this.libelleMarkerlist = libelleMarkerlist;
    }

    public static Carte newInstance(ClassMAPTable[] data, String libelleMarker, String[] libelleMarkerlist) {
        try {
            String impl = System.getProperty("carte.impl");
            if (impl != null && !impl.isEmpty()) {
                Class<?> clazz = Class.forName(impl);
                return (Carte)clazz.getConstructor(ClassMAPTable[].class, String.class, String[].class).newInstance(data, libelleMarker, libelleMarkerlist);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new Carte(data, libelleMarker, libelleMarkerlist);
    }

    public ClassMAPTable[] getData() {
        return this.data;
    }

    public void setData(ClassMAPTable[] data) {
        this.data = data;
    }

    public void addData(ClassMAPTable[] data2) {
        ArrayList<ClassMAPTable> taloha = new ArrayList<ClassMAPTable>();
        for (ClassMAPTable avant : this.data) {
            taloha.add(avant);
        }
        for (ClassMAPTable nouveau : data2) {
            taloha.add(nouveau);
        }
        this.data = new ClassGeom[taloha.size()];
        for (int i = 0; i < taloha.size(); ++i) {
            this.data[i] = (ClassMAPTable)taloha.get(i);
        }
    }

    public String getLIbelleMarker() {
        return this.libelleMarker;
    }

    public void setLibelleMarker(String libelle) {
        this.libelleMarker = libelle;
    }

    public String getMarkers() throws Exception {
        String retour = "";
        ClassMAPTable[] liste = this.data;
        if (liste != null && liste.length > 0) {
            retour = retour + "<script>";
            for (int i = 0; i < liste.length; ++i) {
                retour = retour + "var marker=L.marker([" + CGenUtil.getValeurFieldByMethod(liste[i], "lattitude") + ", " + CGenUtil.getValeurFieldByMethod(liste[i], "longitude") + "]).addTo(map);\n        marker.bindPopup(\"<b>" + CGenUtil.getValeurFieldByMethod(liste[i], this.libelleMarker) + "</b>\");";
            }
            retour = retour + "</script>";
        }
        return retour;
    }

    public String getMarkers2() throws Exception {
        String retour = "";
        ClassMAPTable[] liste = this.data;
        if (liste != null && liste.length > 0) {
            retour = retour + "<script>";
            Point p = null;
            for (int i = 0; i < liste.length; ++i) {
                p = new Point(CGenUtil.getValeurFieldByMethod(liste[i], "geom").toString());
                retour = retour + "var marker=L.marker([" + p.getX() + ", " + p.getY() + "]).addTo(map);\n        marker.bindPopup(\"<b>" + CGenUtil.getValeurFieldByMethod(liste[i], this.libelleMarker) + "</b>\");";
            }
            retour = retour + "</script>";
        }
        return retour;
    }

    public String getCircles2() throws Exception {
        String retour = "";
        ClassMAPTable[] liste = this.data;
        if (liste != null && liste.length > 0) {
            retour = retour + "<script>";
            Point p = null;
            for (int i = 0; i < liste.length; ++i) {
                p = new Point(CGenUtil.getValeurFieldByMethod(liste[i], "geom").toString());
                retour = retour + "var circle = L.circle([" + p.getX() + ", " + p.getY() + "], {\n                color: 'red',\n                fillColor: '#f03',\n                fillOpacity: 1,\n                radius: 5\n        }).addTo(map);\n        circle.bindPopup(\"<b>" + CGenUtil.getValeurFieldByMethod(liste[i], this.libelleMarker) + "</b>\");";
            }
            retour = retour + "</script>";
        }
        return retour;
    }

    public String getCircles() throws Exception {
        String retour = "";
        ClassMAPTable[] liste = this.data;
        if (liste != null && liste.length > 0) {
            retour = retour + "<script>";
            for (int i = 0; i < liste.length; ++i) {
                retour = retour + "var circle = L.circle([" + CGenUtil.getValeurFieldByMethod(liste[i], "lattitude") + ", " + CGenUtil.getValeurFieldByMethod(liste[i], "longitude") + "], {\n                color: 'red',\n                fillColor: '#f03',\n                fillOpacity: 1,\n                radius: 5\n        }).addTo(map);\n        circle.bindPopup(\" " + CGenUtil.getValeurFieldByMethod(liste[i], this.libelleMarker) + " \").openPopup();";
                if (this.libelleMarker == null) continue;
                retour = retour + "circle.bindPopup(\" " + CGenUtil.getValeurFieldByMethod(liste[i], this.libelleMarker) + " \").openPopup();";
            }
            retour = retour + "</script>";
        }
        return retour;
    }

    public String drawPolygon(Point[] liste, String legende) {
        String retour = "<script>";
        if (liste != null && liste.length > 0) {
            retour = retour + "var polygon = L.polygon([";
            for (int i = 0; i < liste.length; ++i) {
                retour = retour + "[" + liste[i].getX() + "," + liste[i].getY() + "]";
                if (i == liste.length - 1) continue;
                retour = retour + ",";
            }
            retour = retour + "]).addTo(map);";
            retour = retour + "polygon.bindPopup(\" " + legende + " \").openPopup();";
        }
        retour = retour + "</script>";
        return retour;
    }

    public String drawCircleMarker(Point[] liste, String legende, String couleur) {
        String retour = "";
        if (liste != null && liste.length > 0) {
            retour = retour + "<script> var alavany=circle.length;";
            if (couleur == null || couleur.compareTo("") == 0) {
                couleur = "red";
            }
            for (int i = 0; i < liste.length; ++i) {
                retour = retour + "circle[alavany] = L.circle([" + liste[i].getX() + ", " + liste[i].getY() + "], {\n                color: '" + couleur + "',\n                fillColor: '" + couleur + "',\n                fillOpacity: 1,\n                radius: 5\n        }).addTo(map);\n        circle[alavany].bindPopup(\" " + legende + " \").openPopup();";
            }
            retour = retour + "</script>";
        }
        return retour;
    }

    public String drawMarker(Point[] liste, String legende) {
        String retour = "";
        if (liste != null && liste.length > 0) {
            retour = retour + "<script>var longueur=marker.length;";
            for (int i = 0; i < liste.length; ++i) {
                retour = retour + "var greenIcon = L.icon({\n    iconUrl: 'https://img.icons8.com/plasticine/344/bridge.png',\n    iconSize:     [20, 20] \n});";
                retour = retour + "marker[longueur]=L.marker([" + liste[i].getX() + ", " + liste[i].getY() + "], {icon: greenIcon}).addTo(map);";
                retour = retour + "marker[longueur].bindPopup(\" " + legende + " \").openPopup();";
            }
            retour = retour + "</script>";
        }
        return retour;
    }

    public String drawImage(Point[] liste, String legende, String image) {
        String retour = "";
        if (liste != null && liste.length > 0) {
            retour = retour + "<script>";
            for (int i = 0; i < liste.length; ++i) {
                retour = retour + "var greenIcon = L.icon({\n    iconUrl: '" + image + "',\n    iconSize:     [20, 20] \n});";
                retour = retour + "var marker=L.marker([" + liste[i].getX() + ", " + liste[i].getY() + "], {icon: greenIcon}).addTo(map);";
                retour = retour + "marker.bindPopup(\" " + legende + " \").openPopup();";
            }
            retour = retour + "</script>";
        }
        return retour;
    }

    public String drawStyledPolyline(Point[] liste, String color) {
        String retour = "";
        if (liste != null && liste.length > 0) {
            retour = "<script>var longueur1=polyline.length;polyline[longueur1] = L.polyline([";
            for (int i = 0; i < liste.length; ++i) {
                retour = retour + "[" + liste[i].getX() + "," + liste[i].getY() + "]";
                if (i == liste.length - 1) continue;
                retour = retour + ",";
            }
            retour = retour + "]).addTo(map);";
            if (color != null && color.compareTo("") != 0) {
                retour = retour + "polyline[longueur1].setStyle({\n    color: '" + color + "'\n});";
            }
            retour = retour + "</script>";
        }
        return retour;
    }

    public String getCouleur(ClassGeom geom) {
        String color = "red";
        return color;
    }

    public String showGeometry(Boolean circles, String color) throws Exception {
        String retour = "";
        SigService sig = new SigService();
        ClassGeom[] liste = (ClassGeom[])this.data;
        Object sousCat = null;
        Object lienImage = null;
        Object coul = null;
        try {
            if (liste != null && liste.length > 0) {
                Point[] p = null;
                int test = 0;
                block7: for (int i = 0; i < liste.length; ++i) {
                    p = sig.getAllPoints(liste[i]);
                    if (p == null || p.length <= 0) continue;
                    test = sig.getTypeGeom(liste[i]);
                    String after = "";
                    if (this.getLien() != null && !this.getLien().isEmpty()) {
                        after = "<br/><center><a href='" + this.getLien() + "&" + liste[i].getAttributIDName() + "=" + liste[i].getTuppleID() + "'><i class='glyphicon glyphicon-info-sign'></i> Voir Fiche</a></center>";
                    }
                    String markernom = "";
                    if (this.libelleMarkerlist != null && this.libelleMarkerlist.length != 0) {
                        for (int j = 0; j < this.libelleMarkerlist.length; ++j) {
                            String valeur = "" + CGenUtil.getValeurFieldByMethod((ClassMAPTable)liste[i], this.libelleMarkerlist[j]);
                            if (valeur == null || valeur.compareToIgnoreCase("null") == 0) continue;
                            markernom = markernom + "" + valeur + "<br/>";
                        }
                    } else {
                        markernom = this.libelleMarker;
                    }
                    switch (test) {
                        case 1: {
                            color = this.getCouleur(liste[i]);
                            retour = retour + this.drawCircleMarker(p, markernom + after, color);
                            continue block7;
                        }
                        case 2: {
                            retour = retour + this.drawPolygon(p, (String)CGenUtil.getValeurFieldByMethod((ClassMAPTable)liste[i], this.libelleMarker) + after);
                            continue block7;
                        }
                        case 3: {
                            retour = retour + this.drawStyledPolyline(p, null);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return retour;
    }

    public String showGeometry(Boolean circles, String color, String champ) throws Exception {
        return "";
    }

    public void createLegend(String legendeMarker, HashMap<String, String> couleurNom) {
        this.legende = "<script>var legend = L.control({ position: \"bottomright\" });\n\t\t\tlegend.onAdd = function(mymap) {\n                       var div = L.DomUtil.create(\"div\", \"legend\");\n                       div.innerHTML += \"<h4>L&eacute;gendes</h4>\";\n";
        if (legendeMarker != null && legendeMarker.compareTo("") != 0) {
            this.legende = this.legende + "div.innerHTML += '<i class=\"icon\" style=\"background-image: url(https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.5.1/images/marker-icon.png);background-repeat: no-repeat;\"></i><span>" + legendeMarker + "</span><br>';\n";
        }
        if (couleurNom != null && !couleurNom.isEmpty()) {
            for (Map.Entry<String, String> mapentry : couleurNom.entrySet()) {
                if (mapentry.getKey().toString().startsWith("https") && !mapentry.getKey().toString().endsWith(" ")) {
                    this.legende = this.legende + "div.innerHTML += '<img src=" + mapentry.getKey() + "><span>" + mapentry.getValue() + "</span><br>';\n";
                }
                if (mapentry.getKey().toString().startsWith("https") && mapentry.getKey().toString().endsWith(" ")) {
                    String[] liste = mapentry.getKey().toString().split("!");
                    this.legende = this.legende + "div.innerHTML += '<img src=" + liste[0] + "><i style=\"background: " + liste[1] + ";\"></i><span>" + mapentry.getValue() + "</span><br>';\n";
                }
                if (mapentry.getKey().toString().startsWith("https") || mapentry.getKey().toString().endsWith(" ")) continue;
                this.legende = this.legende + "div.innerHTML += '<i style=\"background: " + mapentry.getKey() + ";\"></i><span>" + mapentry.getValue() + "</span><br>';\n";
            }
        }
        this.legende = this.legende + "return div;\n}\n\nlegend.addTo(map);</script>";
    }

    public String getHtml() {
        String retour = ElementCarte.getElementBase();
        if (this.isIsCreate()) {
            return retour + ElementCarte.getElementCreate() + ElementCarte.getPosition;
        }
        return retour + this.legende + ElementCarte.printable;
    }

    public String getLien() {
        return this.lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }
}

