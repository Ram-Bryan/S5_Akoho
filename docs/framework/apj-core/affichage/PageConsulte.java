/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  configuration.CynthiaConf
 *  javax.servlet.http.HttpServletRequest
 *  mg.spat.AttacherFichier
 *  service.UploadService
 *  user.UserEJB
 */
package affichage;

import affichage.Button;
import affichage.Champ;
import affichage.Formulaire;
import affichage.OngletOP;
import affichage.Page;
import bean.CGenUtil;
import bean.ClassEtat;
import bean.ClassMAPTable;
import bean.ListeColonneTable;
import configuration.CynthiaConf;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import mg.spat.AttacherFichier;
import service.UploadService;
import user.UserEJB;
import utilitaire.Utilitaire;

public class PageConsulte
extends Page {
    private Champ[] listeChamp;
    private ClassMAPTable critere;
    private String[] libAffichage;
    private String cssTableau = "monographe";
    private String apres;
    private String apresLienPage = "";
    private String donnee;
    private String titre;
    private boolean canUploadFile = true;
    private String fichier_table_cible = "ATTACHER_FICHIER";
    private String fichier_table_procedure = "getSeq_attacher_fichier";
    private String nomClasseFilleADuplique = "";
    private String nomColonneMere = "";
    private String nomtable = "";
    String[] ordre = null;
    Button[] buttons = null;
    OngletOP[] onglets = null;
    String[] listeCritere = new String[0];
    private String dossier;
    private String pageSaisie;
    private LinkedHashMap<String, String[]> rubrique;

    public String getPageSaisie() {
        return this.pageSaisie;
    }

    public void setPageSaisie(String pageSaisie) {
        this.pageSaisie = pageSaisie;
    }

    public String getDossier() {
        return this.dossier;
    }

    public void setDossier(String dossier) {
        if (dossier != null) {
            dossier = dossier.toUpperCase();
        }
        this.dossier = dossier;
    }

    public LinkedHashMap<String, String[]> getRubrique() {
        return this.rubrique;
    }

    public void setRubrique(LinkedHashMap<String, String[]> rubrique) {
        this.rubrique = rubrique;
    }

    public String[] getListeCritere() {
        return this.listeCritere;
    }

    public void setListeCritere(String[] listeCritere) throws Exception {
        this.listeCritere = listeCritere;
        this.formu = new Formulaire();
        this.formu.setObjet(this.getCritere());
        this.formu.makeChampFormu(listeCritere, new String[0], 4);
        if (this.getReq().getParameter(listeCritere[0]) != null) {
            this.makeCriterePlus();
            this.getData();
            this.makeChamp();
        }
    }

    public void setButtons(Button[] buttons) {
        if (buttons != null && buttons.length > 0) {
            ArrayList<Button> buttonList = new ArrayList<Button>(Arrays.asList(buttons));
            buttonList.add(new Button(this.getLien() + "?but=pageupload.jsp", "&id=" + this.getBase().getTuppleID() + "&dossier=" + this.getBase().getClass().getSimpleName() + "&nomtable=ATTACHER_FICHIER&procedure=GETSEQ_ATTACHER_FICHIER&bute=" + this.getReq().getQueryString().split("&")[0].split("=")[1] + "&nomprj=" + this.getBase().getClass().getSimpleName(), "Attacher Fichier", "btn btn-info", null, null));
            this.buttons = buttonList.toArray(new Button[0]);
        } else {
            this.buttons = buttons;
        }
    }

    public void setButtons(List<Button> buttons) {
        if (buttons != null && !buttons.isEmpty()) {
            buttons.add(new Button(this.getLien() + "?but=pageupload.jsp", "&id=" + this.getBase().getTuppleID() + "&dossier=" + this.getBase().getClass().getSimpleName() + "&nomtable=ATTACHER_FICHIER&procedure=GETSEQ_ATTACHER_FICHIER&bute=" + this.getReq().getQueryString().split("&")[0].split("=")[1] + "&nomprj=" + this.getBase().getClass().getSimpleName(), "Attacher Fichier", "btn btn-info", null, null));
            this.buttons = buttons.toArray(new Button[0]);
        } else {
            this.buttons = buttons.toArray(new Button[0]);
        }
    }

    public Button[] getButtons() {
        return this.buttons;
    }

    public void setOnglets(OngletOP[] onglets) {
        this.onglets = onglets;
        if (onglets != null && onglets.length > 0) {
            for (int i = 0; i < onglets.length; ++i) {
                onglets[i].setOnePage(this.isOnePage());
            }
        }
    }

    public void setOnglets(List<OngletOP> onglets) {
        this.setOnglets(onglets.toArray(new OngletOP[0]));
    }

    public OngletOP[] getOnglets() {
        return this.onglets;
    }

    public String[] getOrdre() {
        return this.ordre;
    }

    public Button getButton(String nom) {
        for (int i = 0; i < this.buttons.length; ++i) {
            if (this.buttons[i].getNom() == null || this.buttons[i].getNom().compareToIgnoreCase(nom) != 0) continue;
            return this.buttons[i];
        }
        return null;
    }

    public String getOngletsHtml() {
        String retour = "";
        if (this.onglets != null && this.onglets.length > 0) {
            for (int i = 0; i < this.onglets.length; ++i) {
                retour = retour + this.onglets[i].getHtml();
            }
        }
        return retour;
    }

    public String getButtonsHtml() {
        String retour = "";
        if (this.buttons != null && this.buttons.length > 0) {
            for (int i = 0; i < this.buttons.length; ++i) {
                retour = retour + this.buttons[i].getHtml();
            }
        }
        return retour;
    }

    public OngletOP getOnglet(String name) {
        for (int i = 0; i < this.onglets.length; ++i) {
            if (this.onglets[i].getNom().compareToIgnoreCase(name) != 0) continue;
            return this.onglets[i];
        }
        return null;
    }

    public void setOrdre(String[] ordre) {
        this.ordre = ordre;
        Champ[] listeChamp = this.getListeChamp();
        Champ[] listeVaovao = new Champ[listeChamp.length];
        int index = 0;
        block0: for (String nomOrdre : ordre) {
            for (Champ champ : listeChamp) {
                if (!champ.getNom().equalsIgnoreCase(nomOrdre)) continue;
                listeVaovao[index++] = champ;
                continue block0;
            }
        }
        for (Champ champ : listeChamp) {
            boolean dejaPris = false;
            for (String nomOrdre : ordre) {
                if (!champ.getNom().equalsIgnoreCase(nomOrdre)) continue;
                dejaPris = true;
                break;
            }
            if (dejaPris || index >= listeVaovao.length) continue;
            listeVaovao[index++] = champ;
        }
        this.setListeChamp(listeVaovao);
    }

    public String getNomColonneMere() {
        return this.nomColonneMere;
    }

    public void setNomColonneMere(String nomColonneMere) {
        this.nomColonneMere = nomColonneMere;
    }

    public String getNomClasseFilleADuplique() {
        return this.nomClasseFilleADuplique;
    }

    public void setNomClasseFilleADuplique(String nomClasseFilleADuplique) {
        this.nomClasseFilleADuplique = nomClasseFilleADuplique;
    }

    @Override
    public String getTitre() {
        return this.titre;
    }

    @Override
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public PageConsulte() {
    }

    public boolean isCanUploadFile() {
        return this.canUploadFile;
    }

    public void setCanUploadFile(boolean canUploadFile) {
        this.canUploadFile = canUploadFile;
    }

    public String getFichier_table_cible() {
        return this.fichier_table_cible;
    }

    public void setFichier_table_cible(String fichier_table_cible) {
        this.fichier_table_cible = fichier_table_cible;
    }

    public String getFichier_table_procedure() {
        return this.fichier_table_procedure;
    }

    public void setFichier_table_procedure(String fichier_table_procedure) {
        this.fichier_table_procedure = fichier_table_procedure;
    }

    public PageConsulte(ClassMAPTable o, HttpServletRequest req, UserEJB u) throws Exception {
        this.setCritere(o);
        if (req != null && (req.getParameter(o.getAttributIDName()) == null || req.getParameter(o.getAttributIDName()).equalsIgnoreCase(""))) {
            throw new Exception("Pas d identifiant");
        }
        this.setReq(req);
        this.makeCritere();
        this.setUtilisateur(u);
        this.getData();
        this.makeChamp();
    }

    public void getData() throws Exception {
        ClassMAPTable[] result = (ClassMAPTable[])this.getUtilisateur().getData(this.getCritere(), null, null, null, "");
        if (result == null || result.length == 0) {
            throw new Exception("Pas de resultat pour votre consultation");
        }
        result[0].setNomTable(this.getCritere().getNomTable());
        this.setBase(result[0]);
    }

    public void makeChamp() throws Exception {
        Champ[] listeChamp = null;
        bean.Champ[] t = ListeColonneTable.getFromListe(this.getBase(), null);
        listeChamp = new Champ[t.length];
        for (int i = 0; i < t.length; ++i) {
            listeChamp[i] = new Champ(t[i].getNomColonne());
            listeChamp[i].setObjet(this.getBase());
            listeChamp[i].setLibelle(t[i].getLibelleAfficheAnnotation());
            Object tempVal = CGenUtil.getValeurFieldByMethod(this.getBase(), t[i].getNomColonne());
            String valeur = String.valueOf(tempVal);
            if (valeur != null && valeur.contains("'")) {
                valeur = valeur.replaceAll("'", "&apos;");
            }
            if (valeur != null && valeur.contains("\"")) {
                valeur = valeur.replaceAll("\"", " ");
            }
            if (tempVal == null) {
                valeur = "";
            } else if (t[i].getTypeJava().compareToIgnoreCase("java.sql.Date") == 0) {
                valeur = Utilitaire.formatterDaty(valeur);
            } else if (t[i].getTypeJava().compareToIgnoreCase("double") == 0) {
                valeur = Utilitaire.formaterAr(valeur);
            }
            listeChamp[i].setValeur(valeur);
            listeChamp[i].setUserEJB(this.getUtilisateur());
        }
        this.setListeChamp(listeChamp);
    }

    public void makeCritere() throws Exception {
        String valeur = this.getReq().getParameter(this.getCritere().getAttributIDName());
        Field f = CGenUtil.getField(this.getCritere(), this.getCritere().getAttributIDName());
        if (f.getName().compareToIgnoreCase("refuser") == 0 && f.getType().equals("int") || f.getType().equals("Integer")) {
            CGenUtil.setValChamp(this.getCritere(), f, Integer.parseInt(valeur));
        } else {
            CGenUtil.setValChamp(this.getCritere(), f, valeur);
        }
    }

    public void makeCriterePlus() throws Exception {
        Field id = CGenUtil.getField(this.getCritere(), this.getCritere().getAttributIDName());
        if (id.getName().compareToIgnoreCase("refuser") == 0 && id.getType().equals("int") || id.getType().equals("Integer")) {
            CGenUtil.setValChamp(this.getCritere(), id, Integer.parseInt(""));
        } else {
            CGenUtil.setValChamp(this.getCritere(), id, "");
        }
        for (int i = 0; i < this.getListeCritere().length; ++i) {
            String valeur = this.getReq().getParameter(this.getListeCritere()[i]);
            Field f = CGenUtil.getField(this.getCritere(), this.getListeCritere()[i]);
            CGenUtil.setValChamp(this.getCritere(), f, valeur);
        }
    }

    public Champ[] getListeChamp() {
        return this.listeChamp;
    }

    public void setListeChamp(Champ[] listeChamp) {
        this.listeChamp = listeChamp;
    }

    public String getDuplicateButtonJS(String pageModif, String id) {
        pageModif = pageModif.split("&")[0];
        return "<script>document.addEventListener('DOMContentLoaded', function () {\n    // Cr\u00c3\u00a9ation du lien\n    const btnLink = document.createElement('a');\n\n    // Classes Bootstrap (\u00c3\u00a0 adapter selon tes besoins)\n    btnLink.classList.add('btn', 'btn-secondary', 'pull-right');\n    btnLink.style.marginRight = '10px';\n\n    // Attributs de base\n    btnLink.href = '" + this.getReq().getSession().getValue("lien") + "?but=" + pageModif + "&acte=dupliquer&id=" + id + "';\n    btnLink.textContent = 'Dupliquer';\n\n    // Endroit o\u00c3\u00b9 l'ins\u00c3\u00a9rer\n    const container = document.querySelector('.box-footer');\n    if (container) {\n        // On cherche tous les .btn-secondary du container\n        const secondaryBtns = container.querySelectorAll('.btn-secondary');\n        if (secondaryBtns.length > 0) {\n            // On l'ins\u00c3\u00a8re apr\u00c3\u00a8s le DERNIER btn-secondary\n            const lastSecondary = secondaryBtns[secondaryBtns.length - 1];\n            lastSecondary.insertAdjacentElement('afterend', btnLink);\n        } else {\n            const primaryBtns = container.querySelectorAll('.btn-primary');\n            if (primaryBtns.length > 0) {\n                // Aucun secondary, on l'ins\u00c3\u00a8re apr\u00c3\u00a8s le DERNIER btn-primary\n                const lastPrimary = primaryBtns[primaryBtns.length - 1];\n                lastPrimary.insertAdjacentElement('afterend', btnLink);\n            } else {\n                // Ni secondary ni primary, on ajoute normalement\n                container.appendChild(btnLink);\n            }\n        }\n    }\n});</script>";
    }

    @Override
    public void makeHtml() {
        Properties prop = CynthiaConf.load();
        String temp = "";
        Class[] paramType = new Class[]{Integer.TYPE};
        if (this.getListeChamp().length < 13) {
            temp = temp + "<div class='row'>";
            temp = temp + "<div class='col-md-12'>";
            temp = temp + "<table class='table table-bordered'>";
            int nbChampAdditionel = 0;
            if (this.getChampAdditionnel() != null) {
                nbChampAdditionel = this.getChampAdditionnel().length;
            }
            for (int i = 0; i < this.getListeChamp().length + nbChampAdditionel; ++i) {
                String lienFin;
                Champ champ = i < this.getListeChamp().length ? this.getListeChamp()[i] : this.getListeChamp()[i - this.getListeChamp().length];
                String lienDebut = champ.getLien() == null ? "" : " <a href='" + champ.getLien().getLien() + "&" + champ.getLien().getQueryString() + champ.getValeur() + "'>";
                String string = lienFin = champ.getLien() == null ? "" : "</a>";
                if (champ.getTypeData().compareToIgnoreCase("date") == 0) {
                    // empty if block
                }
                if (champ.isPhoto()) {
                    temp = temp + "<tr>";
                    temp = temp + "<td class='" + champ.getCssLibelle() + "'><B>" + champ.getLibelleAfficheAnnotation() + "</B></td>";
                    temp = temp + "<td>" + lienDebut + "<img src='" + prop.getProperty("cdnReadUri") + champ.getValeur() + "' alt='" + champ.getValeur() + "' style='width:100%;'/>" + lienFin + " </td>";
                    temp = temp + "</tr>";
                    continue;
                }
                if (!champ.getVisible()) continue;
                temp = temp + "<tr>";
                temp = temp + "<td class='" + champ.getCssLibelle() + "'><B>" + champ.getLibelleAfficheAnnotation() + "</B></td>";
                if (champ.getNom().compareToIgnoreCase("etat") == 0 && this.getBase() instanceof ClassEtat) {
                    temp = temp + "<td>" + lienDebut + "<b>" + ((ClassEtat)this.getBase()).chaineEtat() + "</b>" + lienFin + "</td>";
                } else {
                    String css = "";
                    if (champ.getLibelleAfficheAnnotation().contains("mail")) {
                        css = "style='text-transform: lowercase;'";
                    }
                    temp = temp + "<td " + css + ">" + lienDebut + champ.getValeur() + lienFin + "</td>";
                }
                temp = temp + "</tr>";
            }
            temp = temp + "<input type='hidden' name='id' value='" + this.getListeChamp()[0].getValeur() + "'/>";
            temp = temp + "</table>";
            temp = temp + "</div></div>";
        } else {
            String lienFin;
            String lienDebut;
            Champ champ;
            int i;
            int nb2 = this.getListeChamp().length / 2;
            temp = temp + "<div class='row'>";
            temp = temp + "<div class='col-md-6'>";
            temp = temp + "<table class='table table-bordered'>";
            for (i = 0; i < nb2; ++i) {
                champ = this.getListeChamp()[i];
                lienDebut = champ.getLien() == null ? "" : " <a href='" + champ.getLien().getLien() + "&" + champ.getLien().getQueryString() + this.getListeChamp()[i].getValeur() + "'>";
                String string = lienFin = champ.getLien() == null ? "" : "</a>";
                if (this.getListeChamp()[i].getTypeData().compareToIgnoreCase("date") == 0) {
                    // empty if block
                }
                if (this.getListeChamp()[i].isPhoto()) {
                    temp = temp + "<tr>";
                    temp = temp + "<td class='" + this.getListeChamp()[i].getCssLibelle() + "'><B>" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "</B></td>";
                    temp = temp + "<td>" + lienDebut + "<img src='" + prop.getProperty("cdnReadUri") + this.getListeChamp()[i].getValeur() + "' alt='" + this.getListeChamp()[i].getValeur() + "' style='width:100%;'/>" + lienFin + " </td>";
                    temp = temp + "</tr>";
                    continue;
                }
                if (!this.getListeChamp()[i].getVisible()) continue;
                temp = temp + "<tr>";
                temp = temp + "<td class='" + this.getListeChamp()[i].getCssLibelle() + "'><B>" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "</B></td>";
                if (this.getListeChamp()[i].getNom().compareToIgnoreCase("etat") == 0 && this.getBase() instanceof ClassEtat) {
                    temp = temp + "<td>" + lienDebut + "<b>" + ((ClassEtat)this.getBase()).chaineEtat() + "</b>" + lienFin + "</td>";
                } else {
                    String css = "";
                    if (this.getListeChamp()[i].getLibelleAfficheAnnotation().contains("mail")) {
                        css = "style='text-transform: lowercase;'";
                    }
                    temp = temp + "<td " + css + ">" + lienDebut + this.getListeChamp()[i].getValeur() + lienFin + "</td>";
                }
                temp = temp + "</tr>";
            }
            temp = temp + "</table>";
            temp = temp + "</div>";
            temp = temp + "<div class='col-md-6'>";
            temp = temp + "<table class='table table-bordered'>";
            for (i = nb2; i < this.getListeChamp().length; ++i) {
                champ = this.getListeChamp()[i];
                lienDebut = champ.getLien() == null ? "" : " <a href='" + champ.getLien().getLien() + "&" + champ.getLien().getQueryString() + this.getListeChamp()[i].getValeur() + "'>";
                String string = lienFin = champ.getLien() == null ? "" : "</a>";
                if (this.getListeChamp()[i].getTypeData().compareToIgnoreCase("date") == 0) {
                    // empty if block
                }
                if (this.getListeChamp()[i].isPhoto()) {
                    temp = temp + "<tr>";
                    temp = temp + "<td class='" + this.getListeChamp()[i].getCssLibelle() + "'><B>" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "</B></td>";
                    temp = temp + "<td>" + lienDebut + "<img src='" + prop.getProperty("cdnReadUri") + this.getListeChamp()[i].getValeur() + "' alt='" + this.getListeChamp()[i].getValeur() + "' style='width:100%;'/>" + lienFin + " </td>";
                    temp = temp + "</tr>";
                    continue;
                }
                if (!this.getListeChamp()[i].getVisible()) continue;
                temp = temp + "<tr>";
                temp = temp + "<td class='" + this.getListeChamp()[i].getCssLibelle() + "'><B>" + this.getListeChamp()[i].getLibelleAfficheAnnotation() + "</B></td>";
                temp = this.getListeChamp()[i].getNom().compareToIgnoreCase("etat") == 0 && this.getBase() instanceof ClassEtat ? temp + "<td>" + lienDebut + "<b>" + ((ClassEtat)this.getBase()).chaineEtat() + "</b>" + lienFin + "</td>" : temp + "<td>" + lienDebut + this.getListeChamp()[i].getValeur() + lienFin + "</td>";
                temp = temp + "</tr>";
            }
            temp = temp + "</table>";
            temp = temp + "</div>";
            temp = temp + "</div>";
        }
        this.setHtml(temp);
    }

    @Override
    public void makeHtmlTemplate() throws Exception {
        Properties prop = CynthiaConf.load();
        String temp = "";
        Class[] paramType = new Class[]{Integer.TYPE};
        String idObject = "";
        if (this.getRubrique() == null) {
            temp = temp + "<div class='fiche-details-container'>";
            for (int i = 0; i < this.getListeChamp().length; ++i) {
                String lienFin;
                Champ champ;
                if (this.getListeChamp()[i].getNom().equalsIgnoreCase("id")) {
                    idObject = this.getListeChamp()[i].getValeur();
                    this.getListeChamp()[i].setVisible(false);
                }
                if (this.getListeChamp()[i].getNom().equalsIgnoreCase("motsClesss")) {
                    this.getListeChamp()[i].setVisible(false);
                }
                String lien = (champ = this.getListeChamp()[i]).getLien() == null ? "" : champ.getLien().getLien();
                String lienDebut = champ.getLien() == null ? "" : " <a href='" + champ.getLien().getLien() + "&" + champ.getLien().getQueryString() + this.getListeChamp()[i].getValeur() + "'>";
                String string = lienFin = champ.getLien() == null ? "" : "</a>";
                if (this.getListeChamp()[i].isPhoto()) {
                    temp = temp + "<div class='fiche-details' style='margin: 0px 0px 20px 0;'>";
                    temp = temp + "<div class='" + this.getListeChamp()[i].getCssLibelle() + "'><span class=\"fiche-label Body14pxRegular\">" + Character.toUpperCase(this.getListeChamp()[i].getLibelleAfficheAnnotation().charAt(0)) + this.getListeChamp()[i].getLibelleAfficheAnnotation().substring(1) + "</span></div>";
                    temp = temp + "<div>" + lienDebut + "<img src='" + prop.getProperty("cdnReadUri") + this.getListeChamp()[i].getValeur() + "' alt='" + this.getListeChamp()[i].getValeur() + "' style='width:100%;'/>" + lienFin + " </div>";
                    temp = temp + "</div>";
                    continue;
                }
                if (!this.getListeChamp()[i].getVisible()) continue;
                temp = temp + "<div class='fiche-details' style='margin-bottom: 15px;'>";
                temp = temp + "<div class='" + this.getListeChamp()[i].getCssLibelle() + "'><span class=\"fiche-label Body14pxRegular\" multi-lang>" + Character.toUpperCase(this.getListeChamp()[i].getLibelleAfficheAnnotation().charAt(0)) + this.getListeChamp()[i].getLibelleAfficheAnnotation().substring(1) + "</span></div>";
                temp = temp + "<div>";
                if (this.getListeChamp()[i].getNom().compareToIgnoreCase("etat") == 0 && this.getBase() instanceof ClassEtat) {
                    temp = temp + lienDebut + "<span style=\"font-size: 14px;\" class=\"fiche-label Body14pxRegular\">" + ((ClassEtat)this.getBase()).getEtatText(Utilitaire.stringToInt(this.getListeChamp()[i].getValeur())) + "</span>" + lienFin;
                } else {
                    String css = "class='h617pxSemibold fiche-details'";
                    if (this.getListeChamp()[i].getLibelleAfficheAnnotation().contains("mail")) {
                        css = "class='h617pxSemibold fiche-details lower-case'";
                    }
                    if (this.getListeChamp()[i].getValeur().equals("")) {
                        temp = temp + "<span " + css + ">-</span>";
                    } else if (!this.isModalOnClick()) {
                        temp = temp + "<span " + css + ">" + lienDebut + this.getListeChamp()[i].getValeur() + lienFin + "</span>";
                    } else if (!lien.equalsIgnoreCase("")) {
                        String lienLeger = lien.replace("module.jsp", "moduleLeger.jsp");
                        String lienModal = lienLeger + "&" + champ.getLien().getQueryString() + this.getListeChamp()[i].getValeur();
                        lienDebut = "<a href='#' onclick=\"ouvrirModal(event, '" + lienModal + "&ismodal=true','modalContent')\">";
                        temp = temp + "<span " + css + ">" + lienDebut + this.getListeChamp()[i].getValeur() + lienFin + "</span>";
                    } else {
                        temp = temp + "<span " + css + ">" + this.getListeChamp()[i].getValeur() + "</span>";
                    }
                }
                temp = temp + "</div>";
                temp = temp + "</div>";
            }
            temp = temp + "</div>";
        } else {
            Champ champ;
            int i;
            HashSet<String> champsAssignes = new HashSet<String>();
            for (Map.Entry<String, String[]> entry : this.getRubrique().entrySet()) {
                for (String nomChamp : entry.getValue()) {
                    champsAssignes.add(nomChamp.toLowerCase());
                }
            }
            for (Map.Entry<String, String[]> entry : this.getRubrique().entrySet()) {
                String titreRubrique = entry.getKey();
                String[] nomsChamps = entry.getValue();
                temp = temp + "<div class='rubrique-section' style='margin-bottom: 25px;'>";
                temp = temp + "<div class='rubrique-header' style='border-bottom: 0.5px solid #e0e0e0; padding-bottom: 8px; margin-bottom: 15px;'>";
                temp = temp + "<h5 class='Body14pxSemiBold' >" + titreRubrique + "</h5>";
                temp = temp + "</div>";
                temp = temp + "<div class='fiche-details-container'>";
                for (int i2 = 0; i2 < this.getListeChamp().length; ++i2) {
                    String lienFin;
                    if (this.getListeChamp()[i2].getNom().equalsIgnoreCase("id")) {
                        idObject = this.getListeChamp()[i2].getValeur();
                        this.getListeChamp()[i2].setVisible(false);
                    }
                    if (this.getListeChamp()[i2].getNom().equalsIgnoreCase("motsClesss")) {
                        this.getListeChamp()[i2].setVisible(false);
                    }
                    Champ champ2 = this.getListeChamp()[i2];
                    boolean appartientRubrique = false;
                    for (String nomChamp : nomsChamps) {
                        if (!champ2.getNom().equalsIgnoreCase(nomChamp)) continue;
                        appartientRubrique = true;
                        break;
                    }
                    if (!appartientRubrique) continue;
                    String lien = champ2.getLien() == null ? "" : champ2.getLien().getLien();
                    String lienDebut = champ2.getLien() == null ? "" : " <a href='" + champ2.getLien().getLien() + "&" + champ2.getLien().getQueryString() + this.getListeChamp()[i2].getValeur() + "'>";
                    String string = lienFin = champ2.getLien() == null ? "" : "</a>";
                    if (this.getListeChamp()[i2].isPhoto()) {
                        temp = temp + "<div class='fiche-details' style='margin: 0px 0px 20px 0;'>";
                        temp = temp + "<div class='" + this.getListeChamp()[i2].getCssLibelle() + "'><span class=\"fiche-label Body14pxRegular\">" + Character.toUpperCase(this.getListeChamp()[i2].getLibelleAfficheAnnotation().charAt(0)) + this.getListeChamp()[i2].getLibelleAfficheAnnotation().substring(1) + "</span></div>";
                        temp = temp + "<div>" + lienDebut + "<img src='" + prop.getProperty("cdnReadUri") + this.getListeChamp()[i2].getValeur() + "' alt='" + this.getListeChamp()[i2].getValeur() + "' style='width:100%;'/>" + lienFin + " </div>";
                        temp = temp + "</div>";
                        continue;
                    }
                    if (!this.getListeChamp()[i2].getVisible()) continue;
                    temp = temp + "<div class='fiche-details' style='margin-bottom: 15px;'>";
                    temp = temp + "<div class='" + this.getListeChamp()[i2].getCssLibelle() + "'><span class=\"fiche-label Body14pxRegular\" multi-lang>" + Character.toUpperCase(this.getListeChamp()[i2].getLibelleAfficheAnnotation().charAt(0)) + this.getListeChamp()[i2].getLibelleAfficheAnnotation().substring(1) + "</span></div>";
                    temp = temp + "<div>";
                    if (this.getListeChamp()[i2].getNom().compareToIgnoreCase("etat") == 0 && this.getBase() instanceof ClassEtat) {
                        temp = temp + lienDebut + "<span style=\"font-size: 14px;\" class=\"fiche-label Body14pxRegular\">" + ((ClassEtat)this.getBase()).getEtatText(Utilitaire.stringToInt(this.getListeChamp()[i2].getValeur())) + "</span>" + lienFin;
                    } else {
                        String css = "class='h617pxSemibold fiche-details'";
                        if (this.getListeChamp()[i2].getLibelleAfficheAnnotation().contains("mail")) {
                            css = "class='h617pxSemibold fiche-details lower-case'";
                        }
                        if (this.getListeChamp()[i2].getValeur().equals("")) {
                            temp = temp + "<span " + css + ">-</span>";
                        } else if (!this.isModalOnClick()) {
                            temp = temp + "<span " + css + ">" + lienDebut + this.getListeChamp()[i2].getValeur() + lienFin + "</span>";
                        } else if (!lien.equalsIgnoreCase("")) {
                            String lienLeger = lien.replace("module.jsp", "moduleLeger.jsp");
                            String lienModal = lienLeger + "&" + champ2.getLien().getQueryString() + this.getListeChamp()[i2].getValeur();
                            lienDebut = "<a href='#' onclick=\"ouvrirModal(event, '" + lienModal + "&ismodal=true','modalContent')\">";
                            temp = temp + "<span " + css + ">" + lienDebut + this.getListeChamp()[i2].getValeur() + lienFin + "</span>";
                        } else {
                            temp = temp + "<span " + css + ">" + this.getListeChamp()[i2].getValeur() + "</span>";
                        }
                    }
                    temp = temp + "</div>";
                    temp = temp + "</div>";
                }
                temp = temp + "</div>";
                temp = temp + "</div>";
            }
            boolean hasAutres = false;
            for (i = 0; i < this.getListeChamp().length; ++i) {
                champ = this.getListeChamp()[i];
                if (champsAssignes.contains(champ.getNom().toLowerCase()) || !champ.getVisible()) continue;
                hasAutres = true;
                break;
            }
            if (hasAutres) {
                temp = temp + "<div class='rubrique-section' style='margin-bottom: 25px;'>";
                temp = temp + "<div class='rubrique-header' style='border-bottom: 0.5px solid #e0e0e0; padding-bottom: 8px; margin-bottom: 15px;'>";
                temp = temp + "<h5 class='Body14pxSemiBold' >Autres</h5>";
                temp = temp + "</div>";
                temp = temp + "<div class='fiche-details-container'>";
                for (i = 0; i < this.getListeChamp().length; ++i) {
                    String lienFin;
                    champ = this.getListeChamp()[i];
                    if (champsAssignes.contains(champ.getNom().toLowerCase()) || !champ.getVisible()) continue;
                    String lien = champ.getLien() == null ? "" : champ.getLien().getLien();
                    String lienDebut = champ.getLien() == null ? "" : " <a href='" + champ.getLien().getLien() + "&" + champ.getLien().getQueryString() + this.getListeChamp()[i].getValeur() + "'>";
                    String string = lienFin = champ.getLien() == null ? "" : "</a>";
                    if (this.getListeChamp()[i].isPhoto()) {
                        temp = temp + "<div class='fiche-details' style='margin: 0px 0px 20px 0;'>";
                        temp = temp + "<div class='" + this.getListeChamp()[i].getCssLibelle() + "'><span class=\"fiche-label Body14pxRegular\">" + Character.toUpperCase(this.getListeChamp()[i].getLibelleAfficheAnnotation().charAt(0)) + this.getListeChamp()[i].getLibelleAfficheAnnotation().substring(1) + "</span></div>";
                        temp = temp + "<div>" + lienDebut + "<img src='" + prop.getProperty("cdnReadUri") + this.getListeChamp()[i].getValeur() + "' alt='" + this.getListeChamp()[i].getValeur() + "' style='width:100%;'/>" + lienFin + " </div>";
                        temp = temp + "</div>";
                        continue;
                    }
                    if (!this.getListeChamp()[i].getVisible()) continue;
                    temp = temp + "<div class='fiche-details' style='margin-bottom: 15px;'>";
                    temp = temp + "<div class='" + this.getListeChamp()[i].getCssLibelle() + "'><span class=\"fiche-label Body14pxRegular\" multi-lang>" + Character.toUpperCase(this.getListeChamp()[i].getLibelleAfficheAnnotation().charAt(0)) + this.getListeChamp()[i].getLibelleAfficheAnnotation().substring(1) + "</span></div>";
                    temp = temp + "<div>";
                    if (this.getListeChamp()[i].getNom().compareToIgnoreCase("etat") == 0 && this.getBase() instanceof ClassEtat) {
                        temp = temp + lienDebut + "<span style=\"font-size: 14px;\" class=\"fiche-label Body14pxRegular\">" + ((ClassEtat)this.getBase()).getEtatText(Utilitaire.stringToInt(this.getListeChamp()[i].getValeur())) + "</span>" + lienFin;
                    } else {
                        String css = "class='h617pxSemibold fiche-details'";
                        if (this.getListeChamp()[i].getLibelleAfficheAnnotation().contains("mail")) {
                            css = "class='h617pxSemibold fiche-details lower-case'";
                        }
                        if (this.getListeChamp()[i].getValeur().equals("")) {
                            temp = temp + "<span " + css + ">-</span>";
                        } else if (!this.isModalOnClick()) {
                            temp = temp + "<span " + css + ">" + lienDebut + this.getListeChamp()[i].getValeur() + lienFin + "</span>";
                        } else if (!lien.equalsIgnoreCase("")) {
                            String lienLeger = lien.replace("module.jsp", "moduleLeger.jsp");
                            String lienModal = lienLeger + "&" + champ.getLien().getQueryString() + this.getListeChamp()[i].getValeur();
                            lienDebut = "<a href='#' onclick=\"ouvrirModal(event, '" + lienModal + "&ismodal=true','modalContent')\">";
                            temp = temp + "<span " + css + ">" + lienDebut + this.getListeChamp()[i].getValeur() + lienFin + "</span>";
                        } else {
                            temp = temp + "<span " + css + ">" + this.getListeChamp()[i].getValeur() + "</span>";
                        }
                    }
                    temp = temp + "</div>";
                    temp = temp + "</div>";
                }
                temp = temp + "</div>";
                temp = temp + "</div>";
            }
        }
        temp = temp + "<input type='hidden' name='id' value='" + this.getListeChamp()[0].getValeur() + "'/>";
        if (this.isOnePage()) {
            temp = temp + "\n<div class=\"box-footer\">\n" + this.getButtonsHtml() + "</div>\n";
        }
        if (this.getPageSaisie() != null && !this.getPageSaisie().equalsIgnoreCase("")) {
            temp = temp + "<a class=\"btn btn-secondary pull-right\"  href=\"module.jsp?but=facturefournisseur/dmdachat/dmdachat-saisie.jsp&acte=dupliquer&id=" + idObject + "\" style=\"margin-left: 1em; margin-top: 1.2em;\">Dupliquer</a>";
        }
        this.setHtml(temp);
    }

    public String getValeurByNom(String nomChamp) throws Exception {
        return CGenUtil.getValeurFieldByMethod(this.getBase(), nomChamp).toString();
    }

    public void setCritere(ClassMAPTable critere) {
        this.critere = critere;
    }

    public ClassMAPTable getCritere() {
        return this.critere;
    }

    public void setLibAffichage(String[] libAffichag) throws Exception {
        for (int i = 0; i < libAffichag.length; ++i) {
            Champ c = this.getListeChamp()[i];
            c.setLibelle(libAffichag[i]);
        }
    }

    public String[] getLibAffichage() {
        return this.libAffichage;
    }

    public void setCssTableau(String cssTableau) {
        this.cssTableau = cssTableau;
    }

    public String getCssTableau() {
        return this.cssTableau;
    }

    public String getBasPage() throws Exception {
        String uploadXhtml = this.isCanUploadFile() ? "<td style=\"padding:5px;\"><a class=\"btn btn-success\" href=\"" + this.getReq().getSession().getAttribute("lien") + "?but=pageupload.jsp&id=" + this.getReq().getParameter("id") + "&nomtable=" + this.fichier_table_cible + "&procedure=" + this.fichier_table_procedure + "&bute=" + this.getReq().getParameter("but") + "\">Attacher fichier</a></td>" : "";
        String retour = "";
        retour = retour + "<table border=0 cellpadding=0 cellspacing=0 align=center style=\"text-align: center;\">";
        retour = retour + "<tr>" + uploadXhtml + "<td style=\"padding:5px;\"><form action=\"" + this.getReq().getSession().getAttribute("lien") + "?but=apresTarif.jsp\" method=\"POST\"><input type=\"hidden\" name=\"nomClasseFille\" value=\"" + this.getNomClasseFilleADuplique() + "\"><input type=\"hidden\" name=\"nomColonneMere\" value=\"" + this.getNomColonneMere() + "\"><input type=\"hidden\" name=\"bute\" value=\"" + this.getReq().getParameter("but") + "\"><input type=\"hidden\" name=\"id\" value=\"" + this.getReq().getParameter("id") + "\"><input type=\"hidden\" name=\"rajoutLien\" value=\"id\"><input type=\"hidden\" name=\"acte\" value=\"dupliquer\"><input type=\"hidden\" name=\"nomtable\" value=\"" + this.getNomtable() + "\"><input type=\"hidden\" name=\"classe\" value=\"" + this.getCritere().getClassName() + "\"><td style=\"padding:5px;\"><form action=\"../ExportServlet\" method=\"POST\"><input type=\"hidden\" name=\"but\" value=\"" + this.getReq().getParameter("but") + "\"><input type=\"hidden\" name=\"titre\" value=\"" + this.getTitre() + "\"><input type=\"hidden\" name=\"htmlcontent\" value=\"" + this.getHtml() + "\"></form></td>";
        if (this.getBase().getClass().getSuperclass().getSimpleName() != null && this.getBase().getClass().getSuperclass().getSimpleName().compareToIgnoreCase("ClassEtat") == 0) {
            retour = retour + "<td style=\"padding:5px;\"><form action=\"" + this.getReq().getSession().getAttribute("lien") + "?but=apresTarif.jsp\" method=\"POST\"><input type=\"hidden\" name=\"bute\" value=\"" + this.getReq().getParameter("but") + "\"><input type=\"hidden\" name=\"id\" value=\"" + this.getReq().getParameter("id") + "\"><input type=\"hidden\" name=\"rajoutLien\" value=\"id\"><input type=\"hidden\" name=\"acte\" value=\"annulerVisa\"><input type=\"hidden\" name=\"nomtable\" value=\"" + this.getNomtable() + "\"><input type=\"hidden\" name=\"classe\" value=\"" + this.getCritere().getClassName() + "\">";
            retour = retour + "</form></td>";
        }
        retour = retour + "</tr>";
        retour = retour + "</table>";
        return retour;
    }

    public String getBasPageSansUpload() throws Exception {
        String retour = "";
        retour = retour + "<table border=0 cellpadding=0 cellspacing=0 align=center style=\"text-align: center;\">";
        retour = retour + "<tr><td style=\"padding:5px;\"><form action=\"../ExportServlet\" method=\"POST\"><input type=\"hidden\" name=\"but\" value=\"" + this.getReq().getParameter("but") + "\"><input type=\"hidden\" name=\"titre\" value=\"" + this.getTitre() + "\"><input type=\"hidden\" name=\"htmlcontent\" value=\"" + this.getHtml() + "\"><button type=\"submit\" class=\"btn btn-warning\">Export PDF</button></form></td>";
        retour = retour + "</tr>";
        retour = retour + "</table>";
        return retour;
    }

    public void setApres(String apres) {
        this.apres = apres;
    }

    public String getApres() {
        return this.apres;
    }

    public void setApresLienPage(String apresLienPage) {
        this.apresLienPage = apresLienPage;
    }

    public String getApresLienPage() {
        return this.apresLienPage;
    }

    public void setDonnee(String d) {
        this.getReq().getSession().setAttribute("donnee", (Object)d);
    }

    public String getDonnee() {
        return this.donnee;
    }

    public String getNomtable() {
        return this.nomtable;
    }

    public void setNomtable(String nomtable) {
        this.nomtable = nomtable;
    }

    public Champ getChampByName(String nomChamp) {
        for (int i = 0; i < this.getListeChamp().length; ++i) {
            if (this.getListeChamp()[i].getNom().compareToIgnoreCase(nomChamp) != 0) continue;
            return this.getListeChamp()[i];
        }
        return null;
    }

    public String getHtmlAttacherFichier() throws Exception {
        String html = "";
        String dossier = this.getBase().getClass().getSimpleName();
        if (this.getDossier() != null) {
            dossier = this.getDossier();
        }
        AttacherFichier[] fichiers = UploadService.getUploadFile((String)this.getReq().getParameter("id"));
        html = html + "<div class=\"row m-0 attache-fichier\">\n<div class=\"col-md-12 nopadding bottom-vente-fiche\">\n    <div class=\"box\">\n        <h2 class=\"box-title\">Les fichiers attach&eacute;s</h2>\n        <div class=\"box-body\">\n            <table class=\"table table-striped table-bordered table-condensed tree\" style=\"color: #4e4e4e;\">\n                <thead>\n                    <tr>\n                        <th class='contenuetable '></th>\n                        <th class='contenuetable ' style='width:20%'>Aper&ccedil;u</th>\n                        <th class='contenuetable '>Libell&eacute;</th>\n                        <th class='contenuetable '>Fichier</th>\n                        <th class='contenuetable '>Date d`upload</th>\n                        <th class='contenuetable '>T&eacute;l&eacute;charger</th>\n                     </tr>\n                </thead>\n                <tbody>";
        if (fichiers == null || fichiers.length == 0) {
            html = html + "<tr>\n    <td colspan=\"3\" style=\"text-align: center;\"><strong>Aucun fichier</strong></td>\n</tr>";
        } else {
            for (AttacherFichier fichier : fichiers) {
                html = html + "<tr class=\"treegrid-1 treegrid-expanded\">\n    <td><span class=\"treegrid-expander glyphicon glyphicon-minus\"></span></td>\n    <td><img class='preview-img' onclick=\"openPreview(this.src)\" name=" + fichier.getChemin() + " id=" + fichier.getChemin() + " src=\"/dossier/async/" + dossier + "/" + fichier.getChemin() + "\" style=\"width:100%;\"></td>\n    <td>" + fichier.getChemin() + "</td>\n    <td>" + Utilitaire.champNull(fichier.getLibelle()) + "</td>\n    <td>" + fichier.getDaty() + "</td>\n    <td>\n        <a href=\"" + this.getReq().getContextPath() + "/FileManager2?parent=/" + dossier + "/" + fichier.getChemin() + "\" class=\"btn btn-success\" >T&eacute;l&eacute;charger</a>\n    </td>\n</tr>";
            }
        }
        html = html + "</tbody>\n\n                </table>\n            </div>\n        </div>\n    </div>\n</div>\n<div id=\"imageModal\" class=\"modal\">\n    <span class=\"close\" onclick=\"closePreview()\">&times;</span>\n    <img class=\"modal-content\" id=\"modalImage\">\n</div><script>\nfunction openPreview(src) {\n    const modal = document.getElementById(\"imageModal\");\n    const modalImg = document.getElementById(\"modalImage\");\n\n    modal.style.display = \"flex\";\n    modalImg.src = src;\n}\n\nfunction closePreview() {\n    document.getElementById(\"imageModal\").style.display = \"none\";\n}\ndocument.getElementById(\"imageModal\").onclick = function (e) {\n    if (e.target === this) closePreview();\n};\n</script>\n<style>\n#imageModal {\n    display: none;              /* cach\u00c3\u00a9 par d\u00c3\u00a9faut */\n    position: fixed;\n    inset: 0;\n    background: rgba(0,0,0,0.4);\n\n    /* centrage */\n    justify-content: center;\n    align-items: center;\n}</style>";
        return html;
    }
}

