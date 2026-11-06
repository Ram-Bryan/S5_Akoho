package ferme.utils;

import affichage.Liste;

public class ConstanteFerme {
    public static String IDSEXEMALE = "1";
    public static String IDSEXEFEMELLE = "0";
    public static String qualiteMort = "QP000022";
    public static String qualiteConforme = "QP000001";

    public static Liste getListeIndiceSante(String nomChamp, String libelle) throws Exception {
        Liste l = new Liste(nomChamp);
        l.setLibelle(libelle);
        String[] aff = new String[10];
        String[] val = new String[10];
        for (int i = 0; i < 10; i++) {
            String s = String.valueOf(i + 1);
            aff[i] = s;
            val[i] = s;
        }
        l.setValeurBrute(aff);
        l.setColValeurBrute(val);
        l.setDefaultSelected("5");
        return l;
    }
}
