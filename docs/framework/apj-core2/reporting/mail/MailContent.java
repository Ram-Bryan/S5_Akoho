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
import java.util.ArrayList;
import reporting.mapping.CRContent;
import reporting.mapping.CRContentFille;
import reporting.utilitaire.UtilitaireCR;
import utilitaire.UtilDB;

public class MailContent {
    private String titre;
    private String paragraphe;
    private ClassMAPTable mapping;
    private String[] libEntete;
    private String[] libEnteteAffiche;
    private String[] colSomme;
    private String colDate;
    private int graphe;
    private int ordre;
    private String requeteSql;
    private ClassMAPTable[] dataDirect;

    public ClassMAPTable[] getDataDirect() {
        return this.dataDirect;
    }

    public void setDataDirect(ClassMAPTable[] dataDirect) {
        this.dataDirect = dataDirect;
    }

    public MailContent() {
    }

    public MailContent(ClassMAPTable mapping, String titre, String paragraphe, String colDate) {
        this.titre = titre;
        this.paragraphe = paragraphe;
        this.mapping = mapping;
        this.colDate = colDate;
    }

    public MailContent(ClassMAPTable mapping, String titre, String[] libEntete) {
        this.titre = titre;
        this.mapping = mapping;
        this.libEntete = libEntete;
    }

    public MailContent(ClassMAPTable mapping, String titre, String[] libEntete, String[] libEnteteAffiche) {
        this.titre = titre;
        this.mapping = mapping;
        this.libEntete = libEntete;
        this.libEnteteAffiche = libEnteteAffiche;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getParagraphe() {
        return this.paragraphe;
    }

    public void setParagraphe(String paragraphe) {
        this.paragraphe = paragraphe;
    }

    public ClassMAPTable getMapping() {
        return this.mapping;
    }

    public void setMapping(ClassMAPTable mapping) {
        this.mapping = mapping;
    }

    public String[] getLibEntete() {
        return this.libEntete;
    }

    public void setLibEntete(String[] libEntete) {
        this.libEntete = libEntete;
    }

    public String[] getLibEnteteAffiche() {
        return this.libEnteteAffiche;
    }

    public void setLibEnteteAffiche(String[] libEnteteAffiche) {
        this.libEnteteAffiche = libEnteteAffiche;
    }

    public String[] getColSomme() {
        return this.colSomme;
    }

    public void setColSomme(String[] colSomme) {
        this.colSomme = colSomme;
    }

    public String getColDate() {
        return this.colDate;
    }

    public void setColDate(String colDate) {
        this.colDate = colDate;
    }

    public int getGraphe() {
        return this.graphe;
    }

    public void setGraphe(int graphe) {
        this.graphe = graphe;
    }

    public int getOrdre() {
        return this.ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public String getRequeteSql() {
        return this.requeteSql;
    }

    public void setRequeteSql(String requeteSql) {
        this.requeteSql = requeteSql;
    }

    public static MailContent transformerCRtoMail(CRContent crContent, Connection c) throws Exception {
        if (c == null) {
            c = new UtilDB().GetConn();
        }
        MailContent mailContent = new MailContent();
        mailContent.setTitre(crContent.getTitre());
        mailContent.setParagraphe(crContent.getParagraphe());
        String classe = crContent.getMapping();
        ClassMAPTable map = (ClassMAPTable)Class.forName(classe).newInstance();
        map.setNomTable(crContent.getVue());
        mailContent.setMapping(map);
        MailContent.transformerFilleToMail(mailContent, crContent, c);
        mailContent.setGraphe(crContent.getGraphe());
        mailContent.setOrdre(crContent.getOrdre());
        mailContent.setRequeteSql(crContent.getRequeteSql());
        return mailContent;
    }

    public static void transformerFilleToMail(MailContent mailContent, CRContent crContent, Connection c) throws Exception {
        if (c == null) {
            c = new UtilDB().GetConn();
        }
        CRContentFille[] crContentFilles = (CRContentFille[])crContent.getFille("CRCONTENTFILLE", c, "");
        ArrayList<String> libEnteteList = new ArrayList<String>();
        ArrayList<String> libEnteteAfficherList = new ArrayList<String>();
        ArrayList<String> colSommeList = new ArrayList<String>();
        String colDate = "";
        String temp = "";
        for (int i = 0; i < crContentFilles.length; ++i) {
            if (crContentFilles[i].getLibEntete().equals("")) continue;
            temp = crContentFilles[i].getLibEntete();
            libEnteteList.add(temp);
            if (crContentFilles[i].getColSomme() == 1) {
                colSommeList.add(crContentFilles[i].getLibEntete());
            }
            if (crContentFilles[i].getColDate() == 1) {
                colDate = temp;
            }
            if (crContentFilles[i].getLibEnteteAfficher() != null && !crContentFilles[i].getLibEnteteAfficher().equals("")) {
                temp = crContentFilles[i].getLibEnteteAfficher();
            }
            libEnteteAfficherList.add(temp);
        }
        String[] libEntete = libEnteteList.toArray(new String[0]);
        String[] libEnteteAffiche = libEnteteAfficherList.toArray(new String[0]);
        String[] colSomme = colSommeList.toArray(new String[0]);
        mailContent.setLibEntete(libEntete);
        mailContent.setLibEnteteAffiche(libEnteteAffiche);
        mailContent.setColSomme(colSomme);
        mailContent.setColDate(colDate);
    }

    public String genererAWhere(int nature, Date daty) throws Exception {
        if (this.getColDate() == null || this.getColDate().equals("")) {
            return "";
        }
        String aWhere = " and " + this.getColDate();
        if (nature == 1) {
            aWhere = aWhere + " = " + UtilitaireCR.dateRequete(daty);
        } else if (nature == 2) {
            Date semaineDerniere = UtilitaireCR.dateSemaineDerniere(daty);
            aWhere = aWhere + " > " + UtilitaireCR.dateRequete(semaineDerniere) + "" + aWhere + " <= " + UtilitaireCR.dateRequete(daty);
        } else {
            Date moisDernier = UtilitaireCR.dateMoisDernier(daty);
            aWhere = aWhere + " > " + UtilitaireCR.dateRequete(moisDernier) + "" + aWhere + " <= " + UtilitaireCR.dateRequete(daty);
        }
        aWhere = aWhere + " order by " + this.getColDate() + " asc";
        return aWhere;
    }

    public String genererMailContent(int nature, Date daty, Connection c) throws Exception {
        ClassMAPTable[] mappings;
        if (c == null) {
            c = new UtilDB().GetConn();
        }
        String aWhere = "";
        aWhere = aWhere + this.genererAWhere(nature, daty);
        ClassMAPTable mapping = this.getMapping();
        if (this.getDataDirect() != null) {
            mappings = this.getDataDirect();
        } else if (this.getRequeteSql() != null && !this.getRequeteSql().equals("")) {
            String requete = this.getRequeteSql().replace("{filtre date}", aWhere);
            this.setRequeteSql(requete);
            mappings = (ClassMAPTable[])CGenUtil.rechercher((ClassMAPTable)mapping, (String)this.getRequeteSql(), (Connection)c);
        } else {
            mappings = (ClassMAPTable[])CGenUtil.rechercher((ClassMAPTable)mapping, null, null, (Connection)c, (String)aWhere);
        }
        String contenus = "<div class=\"crcontent\">";
        contenus = contenus + "<h3>" + this.getTitre() + "</h3>";
        if (this.getParagraphe() != null && !this.getParagraphe().equals("")) {
            contenus = contenus + "<p>" + this.getParagraphe() + "</p>";
        }
        if (this.getColSomme() != null) {
            contenus = contenus + UtilitaireCR.sommeTable(mappings, this.getColSomme());
        }
        String header = UtilitaireCR.tableHeader(this.getLibEntete());
        if (this.getLibEnteteAffiche() != null && this.getLibEnteteAffiche().length != 0) {
            header = UtilitaireCR.tableHeader(this.getLibEnteteAffiche());
        }
        contenus = contenus + header;
        contenus = contenus + UtilitaireCR.tableContent(mappings, this.getLibEntete());
        contenus = contenus + "</div>";
        contenus = contenus + "<br></br>";
        return contenus;
    }

    public String genererMailContent(Connection c, String aWhere) throws Exception {
        ClassMAPTable[] mappings;
        if (c == null) {
            c = new UtilDB().GetConn();
        }
        ClassMAPTable mapping = this.getMapping();
        if (this.getDataDirect() != null) {
            mappings = this.getDataDirect();
        } else if (this.getRequeteSql() != null && !this.getRequeteSql().equals("")) {
            String requete = this.getRequeteSql().replace("{filtre date}", aWhere);
            this.setRequeteSql(requete);
            mappings = (ClassMAPTable[])CGenUtil.rechercher((ClassMAPTable)mapping, (String)this.getRequeteSql(), (Connection)c);
        } else {
            mappings = (ClassMAPTable[])CGenUtil.rechercher((ClassMAPTable)mapping, null, null, (Connection)c, (String)aWhere);
        }
        String contenus = "<div class=\"crcontent\">";
        contenus = contenus + "<h3>" + this.getTitre() + "</h3>";
        if (this.getParagraphe() != null && !this.getParagraphe().equals("")) {
            contenus = contenus + "<p>" + this.getParagraphe() + "</p>";
        }
        if (this.getColSomme() != null) {
            contenus = contenus + UtilitaireCR.sommeTable(mappings, this.getColSomme());
        }
        String header = UtilitaireCR.tableHeader(this.getLibEntete());
        if (this.getLibEnteteAffiche() != null && this.getLibEnteteAffiche().length != 0) {
            header = UtilitaireCR.tableHeader(this.getLibEnteteAffiche());
        }
        contenus = contenus + header;
        contenus = contenus + UtilitaireCR.tableContent(mappings, this.getLibEntete());
        contenus = contenus + "</div>";
        contenus = contenus + "<br></br>";
        return contenus;
    }
}

