/*
 * Decompiled with CFR 0.152.
 */
package affichage;

import affichage.PageRechercheGroupe;
import affichage.TableauRecherche;
import bean.CGenUtil;
import bean.ClassMAPTable;
import bean.EtiquetteLigneCol;
import bean.ValeurCroises;
import bean.ValeurEtiquette;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import utilitaire.Utilitaire;

public class TableauRechercheGroupe
extends TableauRecherche {
    ArrayList<String> ligne = new ArrayList();
    ArrayList<String> colonne = new ArrayList();
    HashMap<String, ValeurCroises> valeur = new HashMap();
    HashMap<String, ValeurCroises> somme = new HashMap();
    private ValeurCroises sommeGenerale;
    String[] attrLig;
    String[] attrColonne;
    int nombreMesures;
    private PageRechercheGroupe pageProprietaire;
    private boolean dndAffiche;

    public void setPageProprietaire(PageRechercheGroupe pageProprietaire) {
        this.pageProprietaire = pageProprietaire;
    }

    public void setAttrLig(String[] a) {
        this.attrLig = a;
    }

    public void setAttrColonne(String[] c) {
        this.attrColonne = c;
    }

    public void setAttrLigneColonne(String[] lign, String[] colonne) {
        ArrayList<String> ligne = new ArrayList<String>();
        for (int i = 0; i < lign.length; ++i) {
            if (Utilitaire.estIlDedans(lign[i], colonne) != -1) continue;
            ligne.add(lign[i]);
        }
        this.setAttrLig(TableauRechercheGroupe.getAttr(ligne));
        this.setAttrColonne(colonne);
    }

    public void setAttrLigneColonne(String[] c) {
        ArrayList<String> col = new ArrayList<String>();
        ArrayList<String> ligne = new ArrayList<String>();
        for (int i = 0; i < c.length; ++i) {
            if (i % 2 == 0) {
                ligne.add(c[i]);
                continue;
            }
            col.add(c[i]);
        }
        this.setAttrLig(TableauRechercheGroupe.getAttr(ligne));
        this.setAttrColonne(TableauRechercheGroupe.getAttr(col));
    }

    public static String[] getAttr(ArrayList<String> c) {
        String[] attr = new String[c.size()];
        c.toArray(attr);
        return attr;
    }

    public TableauRechercheGroupe(ClassMAPTable[] liste, String[] attrLigneC, String[] attrCentre, String lien) throws Exception {
        this.nombreMesures = attrCentre == null ? 0 : attrCentre.length;
        this.setAttrLigneColonne(attrLigneC);
        this.transformer(liste, attrCentre, lien);
        super.setValeurEtiquette(this.getValeur());
    }

    @Override
    public String getHtml() throws Exception {
        StringBuilder sortie = new StringBuilder();
        if (this.pageProprietaire != null && this.pageProprietaire.isAfficherDnDAutomatique() && !this.dndAffiche) {
            this.dndAffiche = true;
            sortie.append(this.pageProprietaire.getDragAndDropColumnChangerHtml(this.pageProprietaire.getIdentifiantFormulaire()));
        }
        sortie.append(super.getHtml());
        return sortie.toString();
    }

    public void afficher(String[] aAfficher) {
        for (int i = 0; i < aAfficher.length; ++i) {
            System.out.println("a Afficher " + aAfficher[i]);
        }
    }

    public TableauRechercheGroupe(ClassMAPTable[] liste, String[] attrLigne, String[] attrCol, String[] attrCentre, String lien) throws Exception {
        this.nombreMesures = attrCentre == null ? 0 : attrCentre.length;
        this.setAttrLigneColonne(attrLigne, attrCol);
        this.transformer(liste, attrCentre, lien);
        super.setValeurEtiquette(this.getValeur());
    }

    public void transformer(ClassMAPTable[] liste, String[] attrCentre, String lie) throws Exception {
        if (liste.length == 0) {
            return;
        }
        String lien = lie;
        double[] totalGeneral = new double[attrCentre.length];
        for (int i = 0; i < liste.length; ++i) {
            double[] valeurCentre = new double[attrCentre.length];
            for (int iCentre = 0; iCentre < attrCentre.length; ++iCentre) {
                valeurCentre[iCentre] = Double.valueOf(String.valueOf(CGenUtil.getValeurFieldByMethod(liste[i], attrCentre[iCentre])));
                int n = iCentre;
                totalGeneral[n] = totalGeneral[n] + valeurCentre[iCentre];
            }
            String lig = TableauRechercheGroupe.transformer(liste[i], this.attrLig);
            String col = TableauRechercheGroupe.transformer(liste[i], this.attrColonne);
            if (!TableauRechercheGroupe.estDejaDansListe(this.ligne, lig)) {
                this.ligne.add(lig);
            }
            if (!TableauRechercheGroupe.estDejaDansListe(this.colonne, col)) {
                this.colonne.add(col);
            }
            ValeurCroises valCroise = new ValeurCroises(valeurCentre, lien + this.makeLien(lig, col));
            this.valeur.put(lig + "::" + col, valCroise);
            this.getSomme(lig, valeurCentre, lien + this.makeLien(lig, ""));
            this.getSomme(col, valeurCentre, lien + this.makeLien("", col));
        }
        this.sommeGenerale = new ValeurCroises(totalGeneral, lien);
    }

    public void getSomme(String ligCol, double[] valeur, String lien) {
        ValeurCroises efaAo = this.somme.get(ligCol);
        if (efaAo != null) {
            double[] nouvelleVal = new double[valeur.length];
            for (int i = 0; i < valeur.length; ++i) {
                nouvelleVal[i] = efaAo.getListeVal()[i] + valeur[i];
            }
            efaAo.setListeVal(nouvelleVal);
        } else {
            this.somme.put(ligCol, new ValeurCroises(valeur, lien));
        }
    }

    public void getSomme(String ligCol, double valeur, String lien) {
        ValeurCroises efaAo = this.somme.get(ligCol);
        if (efaAo != null) {
            efaAo.setValeur(efaAo.getValeur() + valeur);
        } else {
            this.somme.put(ligCol, new ValeurCroises(valeur, lien));
        }
    }

    public String makeLien(String ligne, String colonne) {
        String[] listeL = TableauRechercheGroupe.getAttrSansSep(ligne);
        String[] listeC = TableauRechercheGroupe.getAttrSansSep(colonne);
        String retour = "";
        if (ligne != null && ligne.compareToIgnoreCase("") != 0) {
            for (int l = 0; l < this.attrLig.length && l < listeL.length; ++l) {
                retour = retour + "&" + this.attrLig[l] + "=" + ValeurEtiquette.formatter(listeL[l]);
            }
        }
        if (colonne != null && colonne.compareToIgnoreCase("") != 0) {
            for (int c = 0; c < this.attrColonne.length && c < listeC.length; ++c) {
                retour = retour + "&" + this.attrColonne[c] + "=" + ValeurEtiquette.formatter(listeC[c]);
            }
        }
        return retour;
    }

    public static void init(ValeurEtiquette[][] retour) {
        for (int i = 0; i < retour.length; ++i) {
            for (int j = 0; j < retour[i].length; ++j) {
                retour[i][j] = new ValeurEtiquette("", "");
            }
        }
    }

    public ValeurEtiquette[][] getValeur() throws Exception {
        String libelle;
        int c;
        int i;
        int nbLigne = this.attrLig.length;
        int nbColonne = this.attrColonne.length;
        ValeurEtiquette[][] retour = new ValeurEtiquette[this.ligne.size() + nbColonne + 1][this.colonne.size() + nbLigne + 1];
        TableauRechercheGroupe.init(retour);
        for (i = this.attrColonne.length; i < this.ligne.size() + this.attrColonne.length; ++i) {
            String[] listeLigne = TableauRechercheGroupe.getAttrSansSep(this.ligne.get(i - this.attrColonne.length));
            for (c = 0; c < listeLigne.length; ++c) {
                libelle = listeLigne[c];
                if (c < this.attrLig.length && (libelle == null || libelle.trim().isEmpty())) {
                    libelle = "Autre";
                }
                retour[i][c] = new ValeurEtiquette(libelle, "");
            }
        }
        for (i = this.attrLig.length; i < this.colonne.size() + this.attrLig.length; ++i) {
            String[] listeColonne = TableauRechercheGroupe.getAttrSansSep(this.colonne.get(i - this.attrLig.length));
            for (c = 0; c < listeColonne.length; ++c) {
                libelle = listeColonne[c];
                if (c < this.attrColonne.length && (libelle == null || libelle.trim().isEmpty())) {
                    libelle = "Autre";
                }
                retour[c][i] = new ValeurEtiquette(libelle, "");
            }
        }
        for (int iLigne = nbColonne; iLigne < this.ligne.size() + nbColonne + 1; ++iLigne) {
            for (int iColonne = nbLigne; iColonne < this.colonne.size() + nbLigne + 1; ++iColonne) {
                ValeurCroises val = null;
                val = iLigne == this.ligne.size() + nbColonne && iColonne < this.colonne.size() + nbLigne ? this.somme.get(this.colonne.get(iColonne - nbLigne)) : (iColonne == this.colonne.size() + nbLigne && iLigne < this.ligne.size() + nbColonne ? this.somme.get(this.ligne.get(iLigne - nbColonne)) : (iLigne < this.ligne.size() + nbColonne && iColonne < this.colonne.size() + nbLigne ? this.valeur.get(this.ligne.get(iLigne - nbColonne) + "::" + this.colonne.get(iColonne - nbLigne)) : this.sommeGenerale));
                if (val == null) continue;
                retour[iLigne][iColonne] = new ValeurEtiquette(val);
            }
        }
        return retour;
    }

    public static String[] getAttrSansSep(String valeur) {
        String[] retour = valeur.split("\\.\\.");
        if (retour.length <= 1) {
            String[] ret = new String[]{valeur, ""};
            return ret;
        }
        return retour;
    }

    public static String transformer(ClassMAPTable e, String[] attr) throws Exception {
        String retour = "";
        for (int iLigne = 0; iLigne < attr.length; ++iLigne) {
            String valeurLigne = "";
            Object o = CGenUtil.getValeurFieldByMethod(e, attr[iLigne]);
            if (o != null) {
                valeurLigne = o.toString();
            }
            retour = retour + valeurLigne;
            if (iLigne >= attr.length - 1) continue;
            retour = retour + "..";
        }
        return retour;
    }

    public static boolean estDejaDansListe(ArrayList<String> liste, String valeur) throws Exception {
        for (String val : liste) {
            if (val.compareToIgnoreCase(valeur) != 0) continue;
            return true;
        }
        return false;
    }

    public boolean estDejaDansEtiquette(ArrayList<EtiquetteLigneCol> liste, EtiquetteLigneCol valeur) throws Exception {
        Iterator<EtiquetteLigneCol> iterator = liste.iterator();
        if (iterator.hasNext()) {
            EtiquetteLigneCol lc = iterator.next();
            return lc.comparer(valeur);
        }
        return false;
    }

    @Override
    public int[] getPropEntet() {
        int[] ret = new int[this.colonne.size() + this.attrLig.length + 1];
        for (int i = 0; i < ret.length; ++i) {
            ret[i] = 100 / ret.length;
        }
        return ret;
    }

    @Override
    public void makeHtml() throws Exception {
        if (this.getValeurEtiquette() == null || this.getValeurEtiquette().length == 0) {
            return;
        }
        String temp = "";
        String tempcsv = "";
        String tempxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau>";
        temp = temp + "<div >";
        temp = temp + "<div class=\"row m-0 nopadding col-md-12\">";
        temp = temp + "<div class=\"box\">";
        temp = temp + "<div class=\"contenuetable\">";
        temp = temp + "<h3 class=\"h520pxSemibold\" align=\"center\">" + this.getTitre() + "</h3>";
        temp = temp + "</div>";
        temp = temp + "<div class=\"box-body table-responsive no-padding\">";
        temp = temp + "<div id=\"selectnonee\">";
        temp = temp + "<table width=\"" + this.getTailleTableau() + "\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"3\" class=\"table  table-bordered table-striped \">";
        int nombreLigne = 0;
        int nombreColonne = 0;
        nombreLigne = this.getValeurEtiquette().length;
        nombreColonne = this.getValeurEtiquette()[0].length;
        temp = temp + "<tbody>";
        for (int i = 0; i < nombreLigne; ++i) {
            temp = temp + "<tr>";
            tempxml = tempxml + "<row>";
            int j = 0;
            boolean l = false;
            for (j = 0; j < nombreColonne; ++j) {
                String lien = "";
                String apresLien = "";
                if (this.getValeurEtiquette()[i][j].getLien() != null && this.getValeurEtiquette()[i][j].getLien().compareTo("") != 0) {
                    lien = "<a href='" + this.getValeurEtiquette()[i][j].getLien() + "'>";
                    apresLien = "</a>";
                }
                temp = temp + "<td width=\"" + this.getPropEntet()[j] + "%\" align=\"" + this.getValeurEtiquette()[i][j].getAlign() + "\" >" + lien + Utilitaire.champNull(this.getValeurEtiquette()[i][j].getValeur()) + apresLien + "</td>";
                if (i > 0 && j > 0) {
                    tempcsv = tempcsv + TableauRechercheGroupe.cleanCsv(this.getValeurEtiquette()[i][j].getValeur());
                }
                if (j == nombreColonne - 1) continue;
                tempcsv = tempcsv + ";";
            }
            temp = temp + "</tr>";
            tempcsv = tempcsv + "\r\n";
            tempxml = tempxml + "</row>\r\n";
        }
        temp = temp + "</tbody>";
        tempxml = tempxml + "</tableau>\r\n";
        temp = temp + "</table>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        temp = temp + "</div>";
        this.setHtml(temp);
        this.setExpcsv(tempcsv);
        this.setExpxml(tempxml);
    }

    @Override
    public void makeHtmlTemplate() throws Exception {
        ValeurEtiquette[][] valeurEtiquette = this.getValeurEtiquette();
        if (valeurEtiquette == null || valeurEtiquette.length == 0) {
            return;
        }
        StringBuilder temp = new StringBuilder(4096);
        StringBuilder tempcsv = new StringBuilder(2048);
        StringBuilder tempxml = new StringBuilder(2048);
        int nombreLigne = valeurEtiquette.length;
        int nombreColonne = valeurEtiquette[0].length;
        int lastIndex = nombreLigne - 1;
        int[] propEntet = this.getPropEntet();
        String tailleTableau = this.getTailleTableau();
        String titre = this.getTitre();
        tempxml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau>");
        temp.append("<div >").append("<div class=\"row m-0 nopadding col-md-12\">").append("<div class=\"box mb-5\">").append("<div class=\"contenuetable\">").append("<h3 style=\"text-align:center;\">").append(titre).append("</h3>").append("</div>").append("<div class=\"box-body table-responsive no-padding\">").append("<div id=\"selectnonee\">").append("<table width=\"").append(tailleTableau).append("\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"3\" class=\"table table-hover table-stripped table-bordered\" data-apj-analysis-table=\"true\">").append("<tbody>");
        boolean totalRowAdded = false;
        for (int i = 0; i < lastIndex; ++i) {
            boolean celluleAxeLigne;
            String lienUrl;
            String apresLien;
            String lien;
            ValeurEtiquette[] ligne = valeurEtiquette[i];
            if (i < this.attrColonne.length) {
                temp.append("<tr data-apj-analysis-field=\"ligneGroupe").append(i + 1).append("\" role=\"button\" tabindex=\"0\" aria-label=\"Modifier la colonne group&eacute;e\" style=\"cursor:pointer;\">");
            } else {
                temp.append("<tr>");
            }
            tempxml.append("<row>");
            for (int j = 0; j < nombreColonne; ++j) {
                ValeurEtiquette cellule = ligne[j];
                String valeurCellule = Utilitaire.champNull(cellule.getValeur());
                String align = cellule.getAlign();
                if (valeurCellule.isEmpty()) {
                    valeurCellule = "-";
                    align = "right";
                } else if (i <= 0 || j > 0) {
                    // empty if block
                }
                lien = "";
                apresLien = "";
                lienUrl = cellule.getLien();
                if (lienUrl != null && !lienUrl.isEmpty()) {
                    lien = "<a href='" + lienUrl + "'>";
                    apresLien = "</a>";
                }
                celluleAxeLigne = i >= this.attrColonne.length && j < this.attrLig.length;
                boolean celluleMesure = i >= this.attrColonne.length && j >= this.attrLig.length;
                String contenuCellule = celluleMesure ? this.getHtmlValeursMesures(valeurCellule) : valeurCellule;
                temp.append("<td");
                if (celluleAxeLigne) {
                    temp.append(" data-apj-analysis-field=\"colGroupe").append(j + 1).append("\" role=\"button\" tabindex=\"0\" aria-label=\"Modifier la ligne group&eacute;e\"");
                }
                temp.append(" width=\"").append(propEntet[j]).append("%\" style=\"border:1px solid #eeeeee;text-align:").append(align).append(";");
                if (celluleAxeLigne) {
                    temp.append("cursor:pointer;");
                }
                temp.append("\"><span>").append(lien).append(contenuCellule).append(apresLien).append("</span></td>");
                tempcsv.append(TableauRechercheGroupe.cleanCsv(cellule.getValeur()));
                if (j >= nombreColonne - 1) continue;
                tempcsv.append(";");
            }
            temp.append("</tr>");
            tempcsv.append("\r\n");
            tempxml.append("</row>\r\n");
            if (i != this.attrColonne.length - 1 || nombreLigne <= 1 || totalRowAdded) continue;
            ValeurEtiquette[] ligneTotal = valeurEtiquette[lastIndex];
            temp.append("<tr>");
            tempxml.append("<row>");
            for (int j = 0; j < nombreColonne; ++j) {
                String valeurCellule;
                ValeurEtiquette cellule = ligneTotal[j];
                if (j == 0) {
                    valeurCellule = "Total";
                } else {
                    valeurCellule = Utilitaire.champNull(cellule.getValeur());
                    if (!valeurCellule.isEmpty() && !valeurCellule.equals("-")) {
                        valeurCellule = Utilitaire.arrondirValeur(valeurCellule);
                    }
                }
                lien = "";
                apresLien = "";
                lienUrl = cellule.getLien();
                if (lienUrl != null && !lienUrl.isEmpty()) {
                    lien = "<a href='" + lienUrl + "'>";
                    apresLien = "</a>";
                }
                celluleAxeLigne = j < this.attrLig.length;
                String contenuCellule = celluleAxeLigne ? valeurCellule : this.getHtmlValeursMesures(valeurCellule);
                temp.append("<td");
                if (celluleAxeLigne) {
                    temp.append(" data-apj-analysis-field=\"colGroupe").append(j + 1).append("\" role=\"button\" tabindex=\"0\" aria-label=\"Modifier la ligne group&eacute;e\"");
                }
                temp.append(" width=\"").append(propEntet[j]).append("%\" style=\"text-align:").append(cellule.getAlign()).append(";");
                if (j == nombreColonne - 1) {
                    temp.append("font-weight:bold;");
                }
                if (celluleAxeLigne) {
                    temp.append("cursor:pointer;");
                }
                temp.append("\">").append(lien).append(contenuCellule).append(apresLien).append("</td>");
                tempcsv.append(TableauRechercheGroupe.cleanCsv(cellule.getValeur()));
                if (j >= nombreColonne - 1) continue;
                tempcsv.append(";");
            }
            temp.append("</tr>");
            tempcsv.append("\r\n");
            tempxml.append("</row>\r\n");
            totalRowAdded = true;
        }
        temp.append("</tbody>").append("</table>").append("</div>").append("</div>").append("</div>").append("</div>").append("</div>");
        tempxml.append("</tableau>\r\n");
        this.setHtml(temp.toString());
        this.setExpcsv(tempcsv.toString());
        this.setExpxml(tempxml.toString());
    }

    private String getHtmlValeursMesures(String valeurCellule) {
        String valeur = valeurCellule == null || valeurCellule.isEmpty() ? "-" : valeurCellule;
        String[] lignes = valeur.split("(?i)<br\\s*/?>", -1);
        int nombreLignes = Math.max(this.nombreMesures, lignes.length);
        StringBuilder html = new StringBuilder(Math.max(1, nombreLignes) * 96);
        for (int i = 0; i < nombreLignes; ++i) {
            String valeurLigne = i < lignes.length && !lignes[i].isEmpty() ? lignes[i] : "-";
            String valeurTemp = "";
            if (!valeurLigne.equalsIgnoreCase("-")) {
                String vt = Utilitaire.enleverEspace(valeurLigne).replace(",", ".");
                valeurTemp = Utilitaire.formaterAr(Utilitaire.arrondir(Double.parseDouble(vt), 2));
            } else {
                valeurTemp = valeurLigne;
            }
            html.append("<span data-apj-analysis-field=\"colSomme").append(i + 1).append("\" role=\"button\" tabindex=\"0\" aria-label=\"Modifier cette valeur\" style=\"display:block;min-height:22px;padding:1px 3px;text-align:right;cursor:pointer;\">").append(valeurTemp).append("</span>");
        }
        return html.toString();
    }
}

