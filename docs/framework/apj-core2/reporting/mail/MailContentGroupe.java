/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.CGenUtil
 *  bean.ClassMAPTable
 *  utilitaire.UtilDB
 */
package reporting.mail;

import bean.CGenUtil;
import bean.ClassMAPTable;
import java.sql.Connection;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeSet;
import reporting.mail.MailContent;
import reporting.utilitaire.UtilitaireCR;
import utilitaire.UtilDB;

public class MailContentGroupe
extends MailContent {
    private String[] sommeDefaut;
    private String[] colDefaut;
    private String[] colGrCol;
    private String[] explications;
    private LinkedHashSet<String> colDefautSet = new LinkedHashSet();
    private TreeSet<String> colGrColSet = new TreeSet();
    private Map<String, Map<String, Double[]>> map = new LinkedHashMap<String, Map<String, Double[]>>();
    private Map<String, Double[]> totauxColonnes = new LinkedHashMap<String, Double[]>();
    private ClassMAPTable[] data;

    public String[] getSommeDefaut() {
        return this.sommeDefaut;
    }

    public void setSommeDefaut(String[] sommeDefaut) {
        this.sommeDefaut = sommeDefaut;
    }

    public String[] getColDefaut() {
        return this.colDefaut;
    }

    public void setColDefaut(String[] colDefaut) {
        this.colDefaut = colDefaut;
    }

    public String[] getColGrCol() {
        return this.colGrCol;
    }

    public void setColGrCol(String[] colGrCol) {
        this.colGrCol = colGrCol;
    }

    public String[] getExplications() {
        return this.explications;
    }

    public void setExplications(String[] explications) {
        this.explications = explications;
    }

    public void cleanAttributes() {
        this.colDefautSet.clear();
        this.colGrColSet.clear();
        this.map.clear();
        this.totauxColonnes.clear();
        this.data = new ClassMAPTable[0];
    }

    public void genererMailContentData(Connection c, String aWhere) throws Exception {
        String colGrColVal;
        String colDefautVal;
        boolean hasColGrCol;
        this.cleanAttributes();
        ClassMAPTable mapping = this.getMapping();
        if (this.getRequeteSql() != null && !this.getRequeteSql().equals("")) {
            String requete = this.getRequeteSql().replace("{filtre date}", aWhere);
            this.setRequeteSql(requete);
            this.data = (ClassMAPTable[])CGenUtil.rechercher((ClassMAPTable)mapping, (String)this.getRequeteSql(), (Connection)c);
        } else {
            this.data = (ClassMAPTable[])CGenUtil.rechercher((ClassMAPTable)mapping, null, null, (Connection)c, (String)aWhere);
        }
        if (this.data == null || this.data.length == 0) {
            return;
        }
        boolean hasColDefaut = this.colDefaut != null && this.colDefaut.length > 0;
        boolean bl = hasColGrCol = this.colGrCol != null && this.colGrCol.length > 0;
        if (!hasColDefaut) {
            this.colDefautSet.add("Total");
        }
        for (Object h : this.data) {
            colDefautVal = hasColDefaut ? UtilitaireCR.mappingAttributContent(h, this.colDefaut[0]) : "Total";
            String string = colGrColVal = hasColGrCol ? UtilitaireCR.mappingAttributContent(h, this.colGrCol[0]) : "Total";
            if (colDefautVal != null) {
                this.colDefautSet.add(colDefautVal);
            }
            if (colGrColVal == null) continue;
            this.colGrColSet.add(colGrColVal);
        }
        for (String grCol : this.colGrColSet) {
            this.map.put(grCol, new LinkedHashMap());
        }
        for (String defaut : this.colDefautSet) {
            this.totauxColonnes.put(defaut, UtilitaireCR.emptyDoubleTable(this.sommeDefaut.length));
        }
        for (Object h : this.data) {
            colDefautVal = hasColDefaut ? UtilitaireCR.mappingAttributContent(h, this.colDefaut[0]) : "Total";
            String string = colGrColVal = hasColGrCol ? UtilitaireCR.mappingAttributContent(h, this.colGrCol[0]) : "Total";
            if (colDefautVal == null || colGrColVal == null) continue;
            String colDefaut = colDefautVal;
            String colGrCol = colGrColVal;
            Double[] cellule = this.map.get(colGrCol).computeIfAbsent(colDefaut, k -> UtilitaireCR.emptyDoubleTable(this.sommeDefaut.length));
            Double[] totCol = this.totauxColonnes.get(colDefaut);
            int i = 0;
            while (i < this.sommeDefaut.length) {
                double valeur = ((Number)UtilitaireCR.mappingAttributContent(h, this.sommeDefaut[i])).doubleValue();
                Double[] doubleArray = cellule;
                int n = i;
                Double.valueOf(doubleArray[n] + valeur);
                doubleArray = totCol;
                n = i++;
                Double.valueOf(doubleArray[n] + valeur);
            }
        }
    }

    public void genererMailContentData(int nature, Date daty, Connection c) throws Exception {
        String colGrColVal;
        String colDefautVal;
        boolean hasColGrCol;
        this.cleanAttributes();
        String aWhere = "";
        aWhere = aWhere + this.genererAWhere(nature, daty);
        ClassMAPTable mapping = this.getMapping();
        if (this.getRequeteSql() != null && !this.getRequeteSql().equals("")) {
            String requete = this.getRequeteSql().replace("{filtre date}", aWhere);
            this.setRequeteSql(requete);
            this.data = (ClassMAPTable[])CGenUtil.rechercher((ClassMAPTable)mapping, (String)this.getRequeteSql(), (Connection)c);
        } else {
            this.data = (ClassMAPTable[])CGenUtil.rechercher((ClassMAPTable)mapping, null, null, (Connection)c, (String)aWhere);
        }
        if (this.data == null || this.data.length == 0) {
            return;
        }
        boolean hasColDefaut = this.colDefaut != null && this.colDefaut.length > 0;
        boolean bl = hasColGrCol = this.colGrCol != null && this.colGrCol.length > 0;
        if (!hasColDefaut) {
            this.colDefautSet.add("Total");
        }
        for (Object h : this.data) {
            colDefautVal = hasColDefaut ? UtilitaireCR.mappingAttributContent(h, this.colDefaut[0]) : "Total";
            String string = colGrColVal = hasColGrCol ? UtilitaireCR.mappingAttributContent(h, this.colGrCol[0]) : "Total";
            if (colDefautVal != null) {
                this.colDefautSet.add(colDefautVal);
            }
            if (colGrColVal == null) continue;
            this.colGrColSet.add(colGrColVal);
        }
        for (String grCol : this.colGrColSet) {
            this.map.put(grCol, new LinkedHashMap());
        }
        for (String defaut : this.colDefautSet) {
            this.totauxColonnes.put(defaut, UtilitaireCR.emptyDoubleTable(this.sommeDefaut.length));
        }
        for (Object h : this.data) {
            colDefautVal = hasColDefaut ? UtilitaireCR.mappingAttributContent(h, this.colDefaut[0]) : "Total";
            String string = colGrColVal = hasColGrCol ? UtilitaireCR.mappingAttributContent(h, this.colGrCol[0]) : "Total";
            if (colDefautVal == null || colGrColVal == null) continue;
            String colDefaut = colDefautVal;
            String colGrCol = colGrColVal;
            Double[] cellule = this.map.get(colGrCol).computeIfAbsent(colDefaut, k -> UtilitaireCR.emptyDoubleTable(this.sommeDefaut.length));
            Double[] totCol = this.totauxColonnes.get(colDefaut);
            int i = 0;
            while (i < this.sommeDefaut.length) {
                double valeur = ((Number)UtilitaireCR.mappingAttributContent(h, this.sommeDefaut[i])).doubleValue();
                Double[] doubleArray = cellule;
                int n = i;
                Double.valueOf(doubleArray[n] + valeur);
                doubleArray = totCol;
                n = i++;
                Double.valueOf(doubleArray[n] + valeur);
            }
        }
    }

    public String genererMailContentHtml() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("<h3>").append(this.getTitre()).append("</h3>");
        if (this.getParagraphe() != null && !this.getParagraphe().equals("")) {
            sb.append("<p>").append(this.getParagraphe()).append("</p>");
        }
        if (this.getExplications() != null && this.getExplications().length > 0) {
            sb.append("<ul>\n");
            for (String explication : this.getExplications()) {
                sb.append("  <li>").append(explication).append("</li>\n");
            }
            sb.append("</ul>\n");
        }
        if (this.colGrColSet.isEmpty() && this.colDefautSet.isEmpty()) {
            sb.append("<p>Aucune donnees disponible</p>");
            return sb.toString();
        }
        if (this.getColSomme() != null) {
            sb.append(UtilitaireCR.sommeTable(this.data, this.getColSomme()));
        }
        boolean hasColDefaut = this.colDefaut != null && this.colDefaut.length > 0;
        boolean hasColGrCol = this.colGrCol != null && this.colGrCol.length > 0;
        sb.append("<table>\n");
        sb.append("  <thead><tr>\n");
        if (hasColGrCol) {
            sb.append("    <th>-</th>\n");
        }
        if (hasColDefaut) {
            for (String colDefaut : this.colDefautSet) {
                sb.append("    <th>").append(colDefaut).append("</th>\n");
            }
        }
        sb.append("    <th>Total</th>\n");
        sb.append("  </tr></thead>\n");
        sb.append("  <tbody>\n");
        Double[] totalGeneral = UtilitaireCR.emptyDoubleTable(this.sommeDefaut.length);
        if (hasColGrCol) {
            for (String grCol : this.colGrColSet) {
                sb.append("  <tr>\n");
                sb.append("    <td class=\"col-libelle\"><b>").append(grCol).append("</b></td>\n");
                Double[] totalLigne = UtilitaireCR.emptyDoubleTable(this.sommeDefaut.length);
                if (hasColDefaut) {
                    for (String defaut : this.colDefautSet) {
                        Double[] val = this.map.get(grCol).getOrDefault(defaut, UtilitaireCR.emptyDoubleTable(this.sommeDefaut.length));
                        boolean vide = true;
                        for (int i = 0; i < val.length; ++i) {
                            Double[] doubleArray = totalLigne;
                            int n = i;
                            Double.valueOf(doubleArray[n] + val[i]);
                            doubleArray = totalGeneral;
                            n = i;
                            Double.valueOf(doubleArray[n] + val[i]);
                            if (val[i] == 0.0) continue;
                            vide = false;
                        }
                        sb.append("    <td>").append(vide ? "-" : UtilitaireCR.totalContent(val)).append("</td>\n");
                    }
                } else {
                    Double[] val = this.map.get(grCol).getOrDefault("Total", UtilitaireCR.emptyDoubleTable(this.sommeDefaut.length));
                    for (int i = 0; i < val.length; ++i) {
                        Double[] doubleArray = totalLigne;
                        int n = i;
                        Double.valueOf(doubleArray[n] + val[i]);
                        doubleArray = totalGeneral;
                        n = i;
                        Double.valueOf(doubleArray[n] + val[i]);
                    }
                }
                sb.append("    <td>").append(UtilitaireCR.totalContent(totalLigne)).append("</td>\n");
                sb.append("  </tr>\n");
            }
            sb.append("  <tr>\n");
            sb.append("    <td class=\"col-libelle\">Total</td>\n");
            if (hasColDefaut) {
                for (String defaut : this.colDefautSet) {
                    sb.append("    <td>").append(UtilitaireCR.totalContent(this.totauxColonnes.get(defaut))).append("</td>\n");
                }
            }
            sb.append("    <td>").append(UtilitaireCR.totalContent(totalGeneral)).append("</td>\n");
            sb.append("  </tr>\n");
        } else {
            sb.append("  <tr>\n");
            if (hasColDefaut) {
                for (String defaut : this.colDefautSet) {
                    Double[] val = this.totauxColonnes.get(defaut);
                    for (int i = 0; i < val.length; ++i) {
                        Double[] doubleArray = totalGeneral;
                        int n = i;
                        Double.valueOf(doubleArray[n] + val[i]);
                    }
                    sb.append("    <td>").append(UtilitaireCR.totalContent(val)).append("</td>\n");
                }
            }
            sb.append("    <td>").append(UtilitaireCR.totalContent(totalGeneral)).append("</td>\n");
            sb.append("  </tr>\n");
        }
        sb.append("  </tbody>\n</table>");
        return sb.toString();
    }

    public String genererMailContentGroupe(int nature, Date daty, Connection c) throws Exception {
        if (c == null) {
            c = new UtilDB().GetConn();
        }
        this.genererMailContentData(nature, daty, c);
        String message = this.genererMailContentHtml();
        return message;
    }

    public String genererMailContentGroupeAWhere(Connection c, String aWhere) throws Exception {
        if (c == null) {
            c = new UtilDB().GetConn();
        }
        this.genererMailContentData(c, aWhere);
        String message = this.genererMailContentHtml();
        return message;
    }
}

