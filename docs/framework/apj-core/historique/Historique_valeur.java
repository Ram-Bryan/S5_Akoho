/*
 * Decompiled with CFR 0.152.
 */
package historique;

import bean.Champ;
import bean.ClassMAPTable;
import bean.ListeColonneTable;
import java.sql.Connection;
import java.util.Arrays;

public class Historique_valeur
extends ClassMAPTable {
    private String id;
    private String idhisto;
    private String refhisto;
    private String nom_table;
    private String nom_classe;
    private String val1;
    private String val2;
    private String val3;
    private String val4;
    private String val5;
    private String val6;
    private String val7;
    private String val8;
    private String val9;
    private String val10;
    private String val11;
    private String val12;
    private String val13;
    private String val14;
    private String val15;
    private String val16;
    private String val17;
    private String val18;
    private String val19;
    private String val20;
    private String val21;
    private String val22;
    private String val23;
    private String val24;
    private String val25;
    private String val26;
    private String val27;
    private String val28;
    private String val29;
    private String val30;
    private String val31;
    private String val32;
    private String val33;
    private String val34;
    private String val35;
    private String val36;
    private String val37;
    private String val38;
    private String val39;
    private String val40;
    private String ipuser;

    private String[] getValeursArray() {
        return (String[])Arrays.stream(new String[]{this.val1, this.val2, this.val3, this.val4, this.val5, this.val6, this.val7, this.val8, this.val9, this.val10, this.val11, this.val12, this.val13, this.val14, this.val15, this.val16, this.val17, this.val18, this.val19, this.val20, this.val21, this.val22, this.val23, this.val24, this.val25, this.val26, this.val27, this.val28, this.val29, this.val30, this.val31, this.val32, this.val33, this.val34, this.val35, this.val36, this.val37, this.val38, this.val39, this.val40}).filter(v -> v != null && !v.trim().isEmpty()).toArray(String[]::new);
    }

    public ClassMAPTable toClassMAPtable() throws Exception {
        ClassMAPTable retour = (ClassMAPTable)Class.forName(this.getNom_classe()).newInstance();
        Champ[] champs = ListeColonneTable.getFromListe(retour, null);
        String[] valeurs = this.getValeursArray();
        for (int i = 0; i < champs.length; ++i) {
            if (valeurs[i] == null || valeurs[i].trim().isEmpty()) continue;
            String[] parts = valeurs[i].split(":", 3);
            String fieldName = parts[0];
            String fieldValue = parts.length > 1 ? parts[1] : null;
            String fieldLibValue = parts.length > 2 ? parts[2] : null;
            try {
                if (fieldValue != null && fieldValue.trim().compareToIgnoreCase("null") != 0) {
                    if (fieldLibValue != null) {
                        retour.setValChamp(fieldName, (Object)fieldLibValue);
                    } else {
                        retour.setValChamp(fieldName, (Object)fieldValue);
                    }
                }
                retour.setIdHistoriqueValeur(this.getTuppleID());
                retour.setDateHistoriqueValeur(this.getDateHistoriqueValeur());
                continue;
            }
            catch (Exception e) {
                System.err.println("Erreur lors de l'affectation du champ " + fieldName + " avec la valeur " + fieldValue + ": " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
        return retour;
    }

    @Override
    public String getTuppleID() {
        return this.getId();
    }

    @Override
    public String getAttributIDName() {
        return "id";
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("VAL", "getSeqhistovaleur");
        this.setId(this.makePK(c));
    }

    public Historique_valeur() {
        this.setNomTable("historique_valeur");
    }

    public Historique_valeur(String idhisto, String refhisto, String nom_table, String nom_classe) {
        this.setNomTable("historique_valeur");
        this.setIdhisto(idhisto);
        this.setRefhisto(refhisto);
        this.setNom_table(nom_table);
        this.setNom_classe(nom_classe);
    }

    public Historique_valeur(String idhisto, String refhisto, String nom_table, String nom_classe, String val, String val1, String val2, String val3, String val4, String val5, String val6, String val7, String val8, String val9, String val10, String val11, String val12, String val13, String val14, String val15, String val16, String val17, String val18, String val19, String val20, String val21, String val22, String val23, String val24, String val25, String val26, String val27, String val28, String val29, String val30, String val31, String val32, String val33, String val34, String val35, String val36, String val37, String val38, String val39, String val40) {
        this.setNomTable("historique_valeur");
        this.setIdhisto(idhisto);
        this.setRefhisto(refhisto);
        this.setNom_table(nom_table);
        this.setNom_classe(nom_classe);
        this.setVal1(val1);
        this.setVal2(val2);
        this.setVal3(val3);
        this.setVal4(val4);
        this.setVal5(val5);
        this.setVal6(val6);
        this.setVal7(val7);
        this.setVal8(val8);
        this.setVal9(val9);
        this.setVal10(val10);
        this.setVal11(val11);
        this.setVal12(val12);
        this.setVal13(val13);
        this.setVal14(val14);
        this.setVal15(val15);
        this.setVal16(val16);
        this.setVal17(val17);
        this.setVal18(val18);
        this.setVal19(val19);
        this.setVal20(val20);
        this.setVal21(val21);
        this.setVal22(val22);
        this.setVal23(val23);
        this.setVal24(val24);
        this.setVal25(val25);
        this.setVal26(val26);
        this.setVal27(val27);
        this.setVal28(val28);
        this.setVal29(val29);
        this.setVal30(val30);
        this.setVal31(val31);
        this.setVal32(val32);
        this.setVal33(val33);
        this.setVal34(val34);
        this.setVal35(val35);
        this.setVal36(val36);
        this.setVal37(val37);
        this.setVal38(val38);
        this.setVal39(val39);
        this.setVal40(val40);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdhisto() {
        return this.idhisto;
    }

    public void setIdhisto(String idhisto) {
        this.idhisto = idhisto;
    }

    public String getRefhisto() {
        return this.refhisto;
    }

    public void setRefhisto(String refhisto) {
        this.refhisto = refhisto;
    }

    public String getNom_table() {
        return this.nom_table;
    }

    public void setNom_table(String nom_table) {
        this.nom_table = nom_table;
    }

    public String getNom_classe() {
        return this.nom_classe;
    }

    public void setNom_classe(String nom_classe) {
        this.nom_classe = nom_classe;
    }

    public String getVal1() {
        return this.val1;
    }

    public void setVal1(String val1) {
        this.val1 = val1;
    }

    public String getVal2() {
        return this.val2;
    }

    public void setVal2(String val2) {
        this.val2 = val2;
    }

    public String getVal3() {
        return this.val3;
    }

    public void setVal3(String val3) {
        this.val3 = val3;
    }

    public String getVal4() {
        return this.val4;
    }

    public void setVal4(String val4) {
        this.val4 = val4;
    }

    public String getVal5() {
        return this.val5;
    }

    public void setVal5(String val5) {
        this.val5 = val5;
    }

    public String getVal6() {
        return this.val6;
    }

    public void setVal6(String val6) {
        this.val6 = val6;
    }

    public String getVal7() {
        return this.val7;
    }

    public void setVal7(String val7) {
        this.val7 = val7;
    }

    public String getVal8() {
        return this.val8;
    }

    public void setVal8(String val8) {
        this.val8 = val8;
    }

    public String getVal9() {
        return this.val9;
    }

    public void setVal9(String val9) {
        this.val9 = val9;
    }

    public String getVal10() {
        return this.val10;
    }

    public void setVal10(String val10) {
        this.val10 = val10;
    }

    public String getVal11() {
        return this.val11;
    }

    public void setVal11(String val11) {
        this.val11 = val11;
    }

    public String getVal12() {
        return this.val12;
    }

    public void setVal12(String val12) {
        this.val12 = val12;
    }

    public String getVal13() {
        return this.val13;
    }

    public void setVal13(String val13) {
        this.val13 = val13;
    }

    public String getVal14() {
        return this.val14;
    }

    public void setVal14(String val14) {
        this.val14 = val14;
    }

    public String getVal15() {
        return this.val15;
    }

    public void setVal15(String val15) {
        this.val15 = val15;
    }

    public String getVal16() {
        return this.val16;
    }

    public void setVal16(String val16) {
        this.val16 = val16;
    }

    public String getVal17() {
        return this.val17;
    }

    public void setVal17(String val17) {
        this.val17 = val17;
    }

    public String getVal18() {
        return this.val18;
    }

    public void setVal18(String val18) {
        this.val18 = val18;
    }

    public String getVal19() {
        return this.val19;
    }

    public void setVal19(String val19) {
        this.val19 = val19;
    }

    public String getVal20() {
        return this.val20;
    }

    public void setVal20(String val20) {
        this.val20 = val20;
    }

    public String getVal21() {
        return this.val21;
    }

    public void setVal21(String val21) {
        this.val21 = val21;
    }

    public String getVal22() {
        return this.val22;
    }

    public void setVal22(String val22) {
        this.val22 = val22;
    }

    public String getVal23() {
        return this.val23;
    }

    public void setVal23(String val23) {
        this.val23 = val23;
    }

    public String getVal24() {
        return this.val24;
    }

    public void setVal24(String val24) {
        this.val24 = val24;
    }

    public String getVal25() {
        return this.val25;
    }

    public void setVal25(String val25) {
        this.val25 = val25;
    }

    public String getVal26() {
        return this.val26;
    }

    public void setVal26(String val26) {
        this.val26 = val26;
    }

    public String getVal27() {
        return this.val27;
    }

    public void setVal27(String val27) {
        this.val27 = val27;
    }

    public String getVal28() {
        return this.val28;
    }

    public void setVal28(String val28) {
        this.val28 = val28;
    }

    public String getVal29() {
        return this.val29;
    }

    public void setVal29(String val29) {
        this.val29 = val29;
    }

    public String getVal30() {
        return this.val30;
    }

    public void setVal30(String val30) {
        this.val30 = val30;
    }

    public String getVal31() {
        return this.val31;
    }

    public void setVal31(String val31) {
        this.val31 = val31;
    }

    public String getVal32() {
        return this.val32;
    }

    public void setVal32(String val32) {
        this.val32 = val32;
    }

    public String getVal33() {
        return this.val33;
    }

    public void setVal33(String val33) {
        this.val33 = val33;
    }

    public String getVal34() {
        return this.val34;
    }

    public void setVal34(String val34) {
        this.val34 = val34;
    }

    public String getVal35() {
        return this.val35;
    }

    public void setVal35(String val35) {
        this.val35 = val35;
    }

    public String getVal36() {
        return this.val36;
    }

    public void setVal36(String val36) {
        this.val36 = val36;
    }

    public String getVal37() {
        return this.val37;
    }

    public void setVal37(String val37) {
        this.val37 = val37;
    }

    public String getVal38() {
        return this.val38;
    }

    public void setVal38(String val38) {
        this.val38 = val38;
    }

    public String getVal39() {
        return this.val39;
    }

    public void setVal39(String val39) {
        this.val39 = val39;
    }

    public String getVal40() {
        return this.val40;
    }

    public void setVal40(String val40) {
        this.val40 = val40;
    }

    public String getIpuser() {
        return this.ipuser;
    }

    public void setIpuser(String ipuser) {
        this.ipuser = ipuser;
    }
}

