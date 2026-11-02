/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  javax.servlet.http.HttpServletResponse
 */
package affichage;

import bean.AdminGen;
import bean.ClassMAPTable;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public class Graphe {
    ClassMAPTable[] donnees;
    String colAbs;
    String[] colsOrd;
    String[] colsOrdLib;
    String idGraphe;
    Map<String, Double>[] valeurs;
    String typeGraphe = "bar";
    String[] bgCouleurs;
    String nomColTri;
    String[] couleurs = new String[]{"#FFB3BA", "#FFDFBA", "#FFFFBA", "#BAFFC9", "#BAE1FF", "#E2C6FF", "#F8C8DC", "#F3E5AB", "#CCE2CB", "#D4F1F4", "#F7D1CD", "#E8E8A6", "#C6E2E9", "#D5CFE1", "#EAD5E6", "#FAE3D9", "#D6E2E9", "#E2F0CB", "#FDE2FF", "#D7E3FC", "#F6F0C4", "#C9E4DE", "#ECCBD9", "#D4BBDD", "#E3DFFD", "#F7F7F7", "#FFE5E5", "#FFF1E6", "#E8F6EF", "#E0F7FA"};

    public Graphe() {
    }

    public Graphe(ClassMAPTable[] donnees, String colAbs, String[] colsOrd, String[] colsOrdLib, String idGraphe, String nomColTri) throws Exception {
        this.setColAbs(colAbs);
        this.setNomColTri(nomColTri);
        this.setDonnees(donnees);
        this.setColsOrd(colsOrd);
        this.setColsOrdLib(colsOrdLib);
        this.setIdGraphe(idGraphe);
        this.genererValeurs();
        this.genererCouleursRandom();
    }

    public Graphe(Map<String, Double>[] donnees, String colAbs, String[] colsOrd, String[] colsOrdLib, String idGraphe, String nomColTri) throws Exception {
        this.setColAbs(colAbs);
        this.setNomColTri(nomColTri);
        this.setValeurs(donnees);
        this.setColsOrd(colsOrd);
        this.setColsOrdLib(colsOrdLib);
        this.setIdGraphe(idGraphe);
    }

    public String getHtml() {
        return "<script>\n  (function() {\n    const ctx = document.getElementById('" + this.getIdGraphe() + "');\n    new Chart(ctx, {\n      type: '" + this.getTypeGraphe() + "',\n      data: {\n        labels: " + this.getColsOrdJson() + ",\n        datasets: " + this.getGrapheDataJson() + "\n      },\n      options: {\n        responsive: true,\n        maintainAspectRatio: false\n      }\n    });\n  })();\n</script>\n";
    }

    public String getHtml(String var) {
        String script = "<script>const " + var + " = document.getElementById('" + this.getIdGraphe() + "');\nnew Chart(" + var + ", {\n type: '" + this.getTypeGraphe() + "',\n  data: {\n  labels: " + this.getColsOrdJson() + ",\n  datasets: " + this.getGrapheDataJson() + "\n},\n";
        if (this.getTypeGraphe().equalsIgnoreCase("pie")) {
            script = script + "plugins: [{\n  afterDatasetsDraw: function(chart) {\n    if (chart.config.type === 'pie') {\n      const ctx = chart.ctx;\n      const data = chart.data.datasets[0].data;\n      const total = data.reduce((a, b) => a + b, 0);\n      chart.data.datasets.forEach((dataset, i) => {\n        const meta = chart.getDatasetMeta(i);\n        meta.data.forEach((element, index) => {\n          const value = dataset.data[index];\n          const percentage = ((value / total) * 100).toFixed(1);\n          const position = element.tooltipPosition();\n          ctx.save();\n          ctx.font = 'bold 14px Arial';\n          ctx.textAlign = 'center';\n          ctx.textBaseline = 'middle';\n          const text = percentage + '%';\n          ctx.strokeStyle = '#fff';\n          ctx.lineWidth = 3;\n          ctx.strokeText(text, position.x, position.y);\n          ctx.fillStyle = '#000';\n          ctx.fillText(text, position.x, position.y);\n          ctx.restore();\n        });\n      });\n    }\n  }\n}],\n";
        }
        script = script + "options: {\n responsive : true";
        script = this.getTypeGraphe().equalsIgnoreCase("pie") ? script + ",plugins: {\n  legend: { display: true }\n}" : script + ",plugins: {legend: { display: true }}";
        script = script + "}\n});</script>\n";
        return script;
    }

    public String getHtml(String var, boolean estHorizontal) {
        boolean cacherLegende = this.getColsOrd().length == 1 && this.getColsOrd()[0].isEmpty();
        String script = "<script>const " + var + " = document.getElementById('" + this.getIdGraphe() + "');\nnew Chart(" + var + ", {\n  type: '" + this.getTypeGraphe() + "',\n  data: {\n    labels: " + this.getColsOrdJson() + ",\n    datasets: " + this.getGrapheDataJson() + "\n  },\n  options: {\n    responsive: true" + (estHorizontal ? ",\n    indexAxis: 'y'" : "") + (cacherLegende ? ",\n    plugins: { legend: { display: false } }" : "") + "\n  }\n});</script>\n";
        return script;
    }

    public String getGrapheDataJson() {
        Gson gson = new Gson();
        ArrayList datasets = new ArrayList();
        for (int i = 0; i < this.getValeurs().length; ++i) {
            HashMap<String, Object> dataset = new HashMap<String, Object>();
            dataset.put("label", this.getColOrdAff(i));
            Map<String, Double> val = this.getValeurs()[i];
            dataset.put("data", val.values());
            if (this.getTypeGraphe().equalsIgnoreCase("line")) {
                String color = this.getCouleurs()[i % this.getCouleurs().length];
                dataset.put("borderColor", color);
                dataset.put("backgroundColor", color);
                dataset.put("tension", 0.1);
                dataset.put("fill", false);
            } else if (this.getTypeGraphe().equalsIgnoreCase("doughnut") || this.getTypeGraphe().equalsIgnoreCase("pie")) {
                ArrayList<String> colorList = new ArrayList<String>();
                int colorIndex = 0;
                for (int j = 0; j < val.size(); ++j) {
                    colorList.add(this.getCouleurs()[colorIndex % this.getCouleurs().length]);
                    ++colorIndex;
                }
                dataset.put("backgroundColor", colorList);
            } else {
                dataset.put("backgroundColor", this.getCouleurs()[i % this.getCouleurs().length]);
            }
            datasets.add(dataset);
        }
        return gson.toJson(datasets);
    }

    public String getColOrdAff(int i) {
        if (this.getColsOrdLib() != null && this.getColsOrdLib().length > 0) {
            return this.getColsOrdLib()[i];
        }
        return this.getColsOrd()[i];
    }

    public String getColsOrdJson() {
        Gson gson = new Gson();
        LinkedHashSet<String> keys = new LinkedHashSet<String>(this.getValeurs()[0].keySet());
        return gson.toJson(keys);
    }

    public void genererValeurs() throws Exception {
        HashMap[] valeurs = new HashMap[this.getColsOrd().length];
        AdminGen gen = new AdminGen();
        for (int i = 0; i < this.getColsOrd().length; ++i) {
            String col = this.getColsOrd()[i];
            valeurs[i] = gen.getValeurParColonne(this.getDonnees(), this.getColAbs(), col);
        }
        this.setValeurs(valeurs);
    }

    public void ajoutClicDroite(String texte, String page, boolean estModal) {
    }

    public void setClicGauche(String page, String id, boolean estModal, HttpServletResponse res) throws Exception {
        if (!estModal) {
            res.sendRedirect(page + "?id=" + id);
        }
    }

    public static String[] genererCouleur() {
        String[] couleurs = new String[2];
        int r = (int)(Math.random() * 256.0);
        int g = (int)(Math.random() * 256.0);
        int b = (int)(Math.random() * 256.0);
        couleurs[0] = String.format(Locale.US, "rgba(%d,%d,%d,%.1f)", r, g, b, 0.2);
        couleurs[1] = String.format(Locale.US, "rgba(%d,%d,%d,%.1f)", r, g, b, 1.0);
        return couleurs;
    }

    public void genererCouleursRandom() {
        String[] couleurs = new String[this.colsOrd.length];
        String[] bgCouleurs = new String[this.colsOrd.length];
        for (int i = 0; i < couleurs.length; ++i) {
            String[] coul = Graphe.genererCouleur();
            couleurs[i] = coul[0];
            bgCouleurs[i] = coul[1];
        }
        this.setCouleurs(couleurs);
        this.setBgCouleurs(bgCouleurs);
    }

    public void genererCouleurs() {
        String[] couleurs = new String[this.colsOrd.length];
        String[] bgCouleurs = new String[this.colsOrd.length];
        String bg = "RGB(109, 70, 207,0.7)";
        String br = "RGB(109, 70, 207,1)";
        couleurs[0] = bg;
        bgCouleurs[0] = br;
        String bg2 = "RGBA(232, 151, 51,0.7)";
        String br2 = "RGBA(232, 151, 51,1)";
        couleurs[1] = bg2;
        bgCouleurs[1] = br2;
        String bg3 = "RGBA(0, 153, 106,0.7)";
        String br3 = "RGBA(0, 153, 106,1)";
        couleurs[2] = bg3;
        bgCouleurs[2] = br3;
        for (int i = 3; i < couleurs.length; ++i) {
            String[] coul = Graphe.genererCouleur();
            couleurs[i] = coul[0];
            bgCouleurs[i] = coul[1];
        }
        this.setCouleurs(couleurs);
        this.setBgCouleurs(bgCouleurs);
    }

    public ClassMAPTable[] getDonnees() {
        return this.donnees;
    }

    public void setDonnees(ClassMAPTable[] donnees) throws Exception {
        this.donnees = AdminGen.trier(donnees, new String[]{this.getNomColTri()}, new String[]{"asc"});
    }

    public String getColAbs() {
        return this.colAbs;
    }

    public void setColAbs(String colAbs) {
        this.colAbs = colAbs;
    }

    public String[] getColsOrd() {
        return this.colsOrd;
    }

    public void setColsOrd(String[] colsOrd) {
        this.colsOrd = colsOrd;
    }

    public String[] getColsOrdLib() {
        return this.colsOrdLib;
    }

    public void setColsOrdLib(String[] colsOrdLib) {
        this.colsOrdLib = colsOrdLib;
    }

    public String getIdGraphe() {
        return this.idGraphe;
    }

    public void setIdGraphe(String idGraphe) {
        this.idGraphe = idGraphe;
    }

    public Map<String, Double>[] getValeurs() {
        return this.valeurs;
    }

    public void setValeurs(Map<String, Double>[] valeurs) {
        this.valeurs = valeurs;
    }

    public String getTypeGraphe() {
        return this.typeGraphe;
    }

    public void setTypeGraphe(String typeGraphe) {
        this.typeGraphe = typeGraphe;
    }

    public String[] getCouleurs() {
        return this.couleurs;
    }

    public void setCouleurs(String[] couleurs) {
        this.couleurs = couleurs;
    }

    public String[] getBgCouleurs() {
        return this.bgCouleurs;
    }

    public void setBgCouleurs(String[] bgCouleurs) {
        this.bgCouleurs = bgCouleurs;
    }

    public String getNomColTri() {
        return this.nomColTri;
    }

    public void setNomColTri(String nomColTri) {
        this.nomColTri = nomColTri;
    }
}

