/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.CGenUtil
 *  bean.ClassMAPTable
 *  bean.UploadPj
 *  utilitaire.UtilDB
 */
package service;

import bean.CGenUtil;
import bean.ClassMAPTable;
import bean.UploadPj;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import mg.spat.AttacherFichier;
import utilitaire.UtilDB;

public class UploadService {
    public static AttacherFichier[] getUploadFile(String idmere) throws Exception {
        try (Connection c = null;){
            AttacherFichier[] fichierConsultation;
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            AttacherFichier criteriaConsultation = new AttacherFichier();
            AttacherFichier[] attacherFichierArray = fichierConsultation = (AttacherFichier[])CGenUtil.rechercher((ClassMAPTable)criteriaConsultation, null, null, (Connection)c, (String)(" and mere ='" + idmere + "'"));
            return attacherFichierArray;
        }
    }

    public static void createUploadedPj(String iddossier, String iduser, HashMap<String, String> listeVal, Iterator it, String nomtable, String nomprocedure, String mere) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            while (it.hasNext()) {
                Map.Entry item = (Map.Entry)it.next();
                String key = item.getKey().toString();
                String value = item.getValue().toString();
                String valI = value.substring(8, value.length());
                String libelle = listeVal.get("libelle" + valI);
                UploadPj fichier = new UploadPj(nomtable, nomprocedure, "FLE", libelle, key, mere);
                System.out.println("FIHCIER ============= " + key);
                fichier.construirePK(c);
                fichier.insertToTableWithHisto(iduser, c);
            }
            c.commit();
        }
    }

    public static void createUploadedPj(String iddossier, String iduser, HashMap<String, String> listeVal, Iterator it, String nomtable, String nomprocedure, String mere, String Smapping, String id) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            while (it.hasNext()) {
                Map.Entry item = (Map.Entry)it.next();
                String key = item.getKey().toString();
                String value = item.getValue().toString();
                String valI = value.substring(8, value.length());
                String libelle = listeVal.get("libelle" + valI);
                UploadPj fichier = new UploadPj(nomtable, nomprocedure, "FLE", libelle, key, mere);
                fichier.construirePK(c);
                fichier.insertToTableWithHisto(iduser, c);
                ClassMAPTable mapping = (ClassMAPTable)Class.forName(Smapping).newInstance();
                mapping.setTuppleId(id);
                mapping = mapping.getById(id, mapping.getNomTable(), c);
                mapping.setValChamp("image", (Object)(iddossier + "/" + key));
                mapping.updateToTableWithHisto(iduser, c);
            }
            c.commit();
        }
    }

    public static UploadPj createUploadedPjRetour(String iddossier, String iduser, HashMap<String, String> listeVal, Iterator it, String nomtable, String nomprocedure, String mere) throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            c.setAutoCommit(false);
            UploadPj fichier = null;
            while (it.hasNext()) {
                Map.Entry item = (Map.Entry)it.next();
                String key = item.getKey().toString();
                String value = item.getValue().toString();
                String valI = value.substring(8, value.length());
                String libelle = listeVal.get("libelle" + valI);
                fichier = new UploadPj(nomtable, nomprocedure, "FLE", libelle, key, mere);
                fichier.construirePK(c);
                fichier.insertToTableWithHisto(iduser, c);
            }
            c.commit();
            UploadPj uploadPj = fichier;
            return uploadPj;
        }
    }
}

