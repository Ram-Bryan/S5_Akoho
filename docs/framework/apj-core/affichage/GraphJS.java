/*
 * Decompiled with CFR 0.152.
 */
package affichage;

import bean.CGenUtil;
import bean.ClassMAPTable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import utilitaire.UtilDB;

public class GraphJS {
    private Map<String, Map<String, Double>> datagraphe;
    private String[] dataOrdonnee;
    private String[] dataOrdonneeDessiner;
    private String dataAbscisse;
    private String[] abscisseValue;
    private Map<String, String> libelleOrdonnee;
    private Map<String, String> couleurOrdonnee;
    private String apresForm;

    public String[] getDataOrdonneeDessiner() {
        return this.dataOrdonneeDessiner;
    }

    public void setDataOrdonneeDessiner(String[] dataOrdonneeDessiner) {
        this.dataOrdonneeDessiner = dataOrdonneeDessiner;
    }

    public String getApresForm() {
        return this.apresForm;
    }

    public void setApresForm(String apresForm) {
        this.apresForm = apresForm;
    }

    public Map<String, String> getCouleurOrdonnee() {
        return this.couleurOrdonnee;
    }

    public void setCouleurOrdonnee(Map<String, String> couleurOrdonnee) {
        this.couleurOrdonnee = couleurOrdonnee;
    }

    public Map<String, String> getLibelleOrdonnee() {
        return this.libelleOrdonnee;
    }

    public void setLibelleOrdonnee(Map<String, String> libelleOrdonnee) {
        this.libelleOrdonnee = libelleOrdonnee;
    }

    public String[] getAbscisseValue() {
        return this.abscisseValue;
    }

    public void setAbscisseValue(String[] abscisseValue) {
        this.abscisseValue = abscisseValue;
    }

    public String[] getDataOrdonnee() {
        return this.dataOrdonnee;
    }

    public void setDataOrdonnee(String[] dataOrdonnee) {
        this.dataOrdonnee = dataOrdonnee;
    }

    public String getDataAbscisse() {
        return this.dataAbscisse;
    }

    public void setDataAbscisse(String dataAbscisse) {
        this.dataAbscisse = dataAbscisse;
    }

    public Map<String, Map<String, Double>> getDatagraphe() {
        return this.datagraphe;
    }

    public void setDatagraphe(Map<String, Map<String, Double>> datagraphe) {
        this.datagraphe = datagraphe;
    }

    public GraphJS() {
    }

    public void setLibelleChamp(String nomCollone, String libelle) throws Exception {
        try {
            if (this.getLibelleOrdonnee() == null) {
                this.setLibelleOrdonnee(new ConcurrentHashMap<String, String>());
            }
            if (!GraphJS.isInside(nomCollone, this.getDataOrdonnee())) {
                throw new Exception("Erreur nom de collone incorrect ou pas dans la liste");
            }
            this.getLibelleOrdonnee().put(nomCollone, libelle);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void setCouleurp(String nomCollone, String couleur) throws Exception {
        try {
            if (this.getCouleurOrdonnee() == null) {
                this.setCouleurOrdonnee(new ConcurrentHashMap<String, String>());
            }
            if (!GraphJS.isInside(nomCollone, this.getDataOrdonnee())) {
                throw new Exception("Erreur nom de collone incorrect ou pas dans la liste");
            }
            this.getCouleurOrdonnee().put(nomCollone, couleur);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static boolean isInside(String key, String[] tab) throws Exception {
        boolean retour = false;
        try {
            for (int i = 0; i < tab.length; ++i) {
                if (!tab[i].equals(key)) continue;
                retour = true;
                break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return retour;
    }

    public GraphJS(String dataAbscisse, String[] dataOrdonnee, String collonefiltre, String ciolloneValue, String nomVue, String nomClasse, String apwhere) throws Exception {
        try {
            ClassMAPTable[] tab = this.findDatafonrJS(dataAbscisse, dataOrdonnee, collonefiltre, ciolloneValue, nomVue, nomClasse, apwhere);
            ConcurrentHashMap<String, Map<String, Double>> data = new ConcurrentHashMap<String, Map<String, Double>>();
            ConcurrentHashMap<String, Double> dataTemp = new ConcurrentHashMap<String, Double>();
            this.setDataAbscisse(dataAbscisse);
            this.setDataOrdonnee(dataOrdonnee);
            this.setDataOrdonneeDessiner(dataOrdonnee);
            for (int j = 0; j < dataOrdonnee.length; ++j) {
                for (int i = 0; i < tab.length; ++i) {
                    Object valeurk = CGenUtil.getValeurFieldByMethod(tab[i], dataOrdonnee[j]);
                    Double temp_value = Double.valueOf(valeurk.toString());
                    dataTemp.put(String.valueOf(CGenUtil.getValeurFieldByMethod(tab[i], dataAbscisse)), temp_value);
                }
                TreeMap treeMap = new TreeMap(dataTemp);
                data.put(dataOrdonnee[j], treeMap);
                dataTemp = new ConcurrentHashMap();
            }
            this.setDatagraphe(data);
            this.setKeyAbscisse();
            for (int i = 0; i < this.getDataOrdonnee().length; ++i) {
                this.setLibelleChamp(this.getDataOrdonnee()[i], this.getDataOrdonnee()[i]);
                this.setCouleurp(this.getDataOrdonnee()[i], "#3c8dbc");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ClassMAPTable[] findDatafonrJS(String dataAbscisse, String[] dataOrdonnee, String collonefiltre, String ciolloneValue, String nomVue, String nomClasse, String apwhere) throws Exception {
        try (Connection c = null;){
            ClassMAPTable[] tab;
            String where = " and " + collonefiltre + " = '" + ciolloneValue + "' " + apwhere + " order by " + dataAbscisse + " asc ";
            c = new UtilDB().GetConn();
            ClassMAPTable t = (ClassMAPTable)Class.forName(nomClasse).newInstance();
            t.setNomTable(nomVue);
            ClassMAPTable[] classMAPTableArray = tab = (ClassMAPTable[])CGenUtil.rechercher(t, null, null, c, where);
            return classMAPTableArray;
        }
    }

    public void setKeyAbscisse() {
        String[] retour = null;
        try {
            ArrayList<String> list_temp = new ArrayList<String>();
            if (this.getDatagraphe() != null && this.getDataOrdonnee().length > 0) {
                for (String key : this.getDatagraphe().get(this.getDataOrdonnee()[0]).keySet()) {
                    list_temp.add(key);
                }
            }
            if (list_temp.size() > 0) {
                retour = new String[list_temp.size()];
                for (int i = 0; i < list_temp.size(); ++i) {
                    retour[i] = (String)list_temp.get(i);
                }
            }
            this.setAbscisseValue(retour);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static String convertTabStringToLabelJS(String[] tab) throws Exception {
        String retour = "[";
        try {
            for (int i = 0; i < tab.length; ++i) {
                retour = i == tab.length - 1 ? retour + "\"" + tab[i] + "\"" : retour + "\"" + tab[i] + "\" ,";
            }
            retour = retour + "]";
            System.out.println("retour = " + retour);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return retour;
    }

    public String construireDatasets() throws Exception {
        String retour = "[";
        try {
            if (this.getDataOrdonneeDessiner().length > 0) {
                for (int i = 0; i < this.getDatagraphe().size(); ++i) {
                    Double temp;
                    int k;
                    String ordonnee_temp = this.getDataOrdonnee()[i];
                    boolean testMandalo = false;
                    if (GraphJS.isInside(ordonnee_temp, this.getDataOrdonneeDessiner())) {
                        testMandalo = true;
                    }
                    if (!testMandalo) continue;
                    if (i == this.getDatagraphe().size() - 1) {
                        retour = retour + " { label : \"" + this.getLibelleOrdonnee().get(ordonnee_temp) + "\" , ";
                        retour = retour + " data: [";
                        for (k = 0; k < this.getAbscisseValue().length; ++k) {
                            temp = this.getDatagraphe().get(ordonnee_temp).get(this.getAbscisseValue()[k]);
                            retour = k == this.getAbscisseValue().length - 1 ? retour + temp : retour + temp + ",";
                        }
                        retour = retour + " ],";
                        retour = retour + "borderColor: ['" + this.getCouleurOrdonnee().get(ordonnee_temp) + "'],";
                        retour = retour + "borderWidth: 2 } ";
                        continue;
                    }
                    retour = retour + " { label : \"" + this.getLibelleOrdonnee().get(ordonnee_temp) + "\" , ";
                    retour = retour + " data: [";
                    for (k = 0; k < this.getAbscisseValue().length; ++k) {
                        temp = this.getDatagraphe().get(ordonnee_temp).get(this.getAbscisseValue()[k]);
                        retour = k == this.getAbscisseValue().length - 1 ? retour + temp : retour + temp + ",";
                    }
                    retour = retour + " ],";
                    retour = retour + "borderColor: ['" + this.getCouleurOrdonnee().get(ordonnee_temp) + "'],";
                    retour = retour + "borderWidth: 2 } ,";
                }
                retour = retour + "]";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return retour;
    }

    public String getHtmlJs(String idDuCanvas) throws Exception {
        String retour = "";
        try {
            if (this.getDatagraphe() != null) {
                retour = retour + "var ctxL = document.getElementById(\"" + idDuCanvas + "\").getContext('2d');    var myLineChart = new Chart(ctxL, {        type: 'line',        data: {            labels: " + GraphJS.convertTabStringToLabelJS(this.getAbscisseValue()) + ",            datasets: " + this.construireDatasets() + "        },        options: {            responsive: true        }    });";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return retour;
    }

    public static String converTableauStrongTovirg(String[] tab) throws Exception {
        String retour = "";
        try {
            if (tab != null && tab.length > 0) {
                for (int i = 0; i < tab.length; ++i) {
                    retour = i == tab.length - 1 ? retour + tab[i] : retour + tab[i] + ",";
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return retour;
    }

    public String construireFiltreGraphe() throws Exception {
        String retour = "<form action=\"" + this.getApresForm() + "\" method=\"post\">";
        try {
            for (int i = 0; i < this.getDataOrdonnee().length; ++i) {
                retour = retour + "<div class=\"custom-control custom-checkbox\">\n                          <label class=\"custom-control-label\" for=\"customCheck1\">" + this.getLibelleOrdonnee().get(this.getDataOrdonnee()[i]) + "</label>\n                          <input type=\"checkbox\" class=\"custom-control-input\" name=\"" + this.getDataOrdonnee()[i] + "\" " + GraphJS.printchecked(this.getDataOrdonnee()[i], this.getDataOrdonneeDessiner()) + ">\n                         </div>";
            }
            retour = retour + "<button class=\"btn btn-success\">Dessiner</button>";
            retour = retour + "</form>";
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return retour;
    }

    public static String[] convertListTotab(List<String> liste) throws Exception {
        String[] retour = null;
        try {
            if (liste.size() > 0) {
                retour = new String[liste.size()];
                for (int i = 0; i < liste.size(); ++i) {
                    retour[i] = liste.get(i);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return retour;
    }

    public static String printchecked(String text, String[] tab) throws Exception {
        String retour = "";
        try {
            if (GraphJS.isInside(text, tab)) {
                retour = "checked";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return retour;
    }
}

