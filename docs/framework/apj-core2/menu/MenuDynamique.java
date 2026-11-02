/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.AdminGen
 *  bean.CGenUtil
 *  bean.ClassMAPTable
 *  historique.MapUtilisateur
 *  javax.servlet.http.HttpServletRequest
 */
package menu;

import bean.AdminGen;
import bean.CGenUtil;
import bean.ClassMAPTable;
import historique.MapUtilisateur;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import mg.cnaps.utilisateur.CNAPSUser;

public class MenuDynamique
extends ClassMAPTable {
    private String id;
    private String libelle;
    private String icone;
    private String href;
    private int rang;
    private int niveau;
    private String id_pere;
    private Object element;
    private ArrayList<MenuDynamique> fils = new ArrayList();
    private String description;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<MenuDynamique> getFils() {
        return this.fils;
    }

    public void setFils(ArrayList<MenuDynamique> fils) {
        this.fils = fils;
    }

    public void setRang(int value) {
        this.rang = value;
    }

    public int getRang() {
        return this.rang;
    }

    public void setNiveau(int value) {
        this.niveau = value;
    }

    public int getNiveau() {
        return this.niveau;
    }

    public void setId_pere(String value) {
        this.id_pere = value;
    }

    public String getId_pere() {
        return this.id_pere;
    }

    public void setIcone(String value) {
        this.icone = value;
    }

    public String getIcone() {
        return this.icone;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getId() {
        return this.id;
    }

    public void setLibelle(String value) {
        this.libelle = value;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setHref(String value) {
        this.href = value;
    }

    public String getHref() {
        return this.href;
    }

    public boolean estFeuille() {
        return this.getHref() != null || this.getHref().compareTo("") == 0;
    }

    public MenuDynamique[] getElem_menu(int niveau) throws Exception {
        MenuDynamique[] elem = (MenuDynamique[])CGenUtil.rechercher((ClassMAPTable)this, null, null, (String)(" AND NIVEAU='" + niveau + "' ORDER BY rang"));
        if (elem != null && elem.length > 0) {
            return elem;
        }
        throw new Exception("ID NON VALIDE");
    }

    public String toString() {
        return "[" + this.id + ", " + this.libelle + ", " + this.niveau + ", " + this.rang + ", " + this.icone + ", " + this.id_pere + ", " + this.href + "]";
    }

    public MenuDynamique() {
        this.setNomTable("MENUDYNAMIQUE");
    }


    public MenuDynamique(String id, String libelle, String href, String icone, String pere, int niveau, int rang) {
        this.setId(id);
        this.setLibelle(libelle);
        this.setHref(href);
        this.setIcone(icone);
        this.setId_pere(pere);
        this.setNiveau(niveau);
        this.setRang(rang);
        this.setNomTable("MENUDYNAMIQUE");
    }

    public MenuDynamique(String id) throws Exception {
        MenuDynamique[] elem = (MenuDynamique[])CGenUtil.rechercher((ClassMAPTable)new MenuDynamique(), null, null, (String)(" AND id='" + id + "'"));
        if (elem != null && elem.length > 0) {
            MenuDynamique el = elem[0];
            this.setId(el.getId());
            this.setLibelle(el.getLibelle());
            this.setHref(el.getHref());
            this.setIcone(el.getIcone());
            this.setId_pere(el.getId_pere());
            this.setNiveau(el.getNiveau());
            this.setRang(el.getRang());
            this.setNomTable("MENUDYNAMIQUE");
        }
        throw new Exception("ID NON VALIDE");
    }

    public String getTuppleID() {
        return this.id;
    }

    public String getAttributIDName() {
        return "id";
    }

    public void construirePK(Connection c) throws Exception {
        super.setNomTable("MENUDYNAMIQUE");
        this.preparePk("MENU", "getSeqmenud");
        this.setId(this.makePK(c));
    }

    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (((Object)((Object)this)).getClass() != obj.getClass()) {
            return false;
        }
        MenuDynamique other = (MenuDynamique)((Object)obj);
        return Objects.equals(this.id, other.id);
    }

    public static ArrayList<ArrayList<MenuDynamique>> getElementMenu(HttpServletRequest request, MapUtilisateur user, CNAPSUser cnapsUser) {
        MenuDynamique[] interdit = null;
        MenuDynamique[] autorise = null;
        MenuDynamique[] tabMenu = null;
        ArrayList<ArrayList<MenuDynamique>> arbre = new ArrayList<ArrayList<MenuDynamique>>();
        try {
            if (request.getServletContext().getAttribute("tabMenu") != null) {
                tabMenu = (MenuDynamique[])request.getServletContext().getAttribute("tabMenu");
            } else {
                tabMenu = (MenuDynamique[])CGenUtil.rechercher((ClassMAPTable)new MenuDynamique(), null, null, (String)" order by niveau,rang asc");
                request.getServletContext().setAttribute("tabMenu", (Object)tabMenu);
            }
            String codeService = "%";
            String codeDr = "%";
            if (cnapsUser != null) {
                codeService = cnapsUser.getCode_service().trim();
                codeDr = cnapsUser.getCode_dr().trim();
            }
            String reketyInterdit = "select MENUDYNAMIQUE.* from MENUDYNAMIQUE inner join usermenu on usermenu.IDMENU=MENUDYNAMIQUE.ID  where interdit=1 and (refuser='*' or refuser='" + user.getRefuser() + "' OR idrole='" + user.getIdrole() + "' OR CODESERVICE='" + codeService + "' OR CODEDIR='" + codeDr + "'  ) order by MENUDYNAMIQUE.niveau,MENUDYNAMIQUE.rang asc";
            String reketyAutorise = "select MENUDYNAMIQUE.* from MENUDYNAMIQUE inner join usermenu on ( usermenu.IDMENU=MENUDYNAMIQUE.ID)  where (interdit=0 OR interdit is null) and (  refuser='*' or refuser='" + user.getRefuser() + "' OR idrole='" + user.getIdrole() + "' OR CODESERVICE='" + codeService + "' OR CODEDIR='" + codeDr + "'  ) order by MENUDYNAMIQUE.niveau,MENUDYNAMIQUE.rang asc";
            interdit = (MenuDynamique[])CGenUtil.rechercher((ClassMAPTable)new MenuDynamique(), (String)reketyInterdit);
            autorise = (MenuDynamique[])CGenUtil.rechercher((ClassMAPTable)new MenuDynamique(), (String)reketyAutorise);
            List<MenuDynamique> listInterdit = Arrays.asList(interdit);
            HashSet<MenuDynamique> listAutorise = MenuDynamique.getParentsAndFils(autorise, tabMenu);
            ArrayList<MenuDynamique> arbreNiveau = new ArrayList<MenuDynamique>();
            for (MenuDynamique elt : tabMenu) {
                if (listInterdit.contains((Object)elt) || !listAutorise.contains((Object)elt)) continue;
                if (arbre.isEmpty()) {
                    arbreNiveau.add(elt);
                    arbre.add(arbreNiveau);
                    continue;
                }
                if (arbre.size() >= elt.getNiveau()) {
                    arbre.get(elt.getNiveau() - 1).add(elt);
                    continue;
                }
                arbreNiveau = new ArrayList();
                arbreNiveau.add(elt);
                arbre.add(arbreNiveau);
            }
        }
        catch (Exception ex) {
            Logger.getLogger(MenuDynamique.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return arbre;
    }

    public static MenuDynamique addFirstElement(ArrayList<ArrayList<MenuDynamique>> arbre, ArrayList<MenuDynamique> listAutorise, int niveau) throws Exception {
        MenuDynamique retour = null;
        if (arbre.size() != niveau) {
            retour = MenuDynamique.addFirstElement(arbre, listAutorise, niveau - 1);
        } else {
            for (MenuDynamique elt : listAutorise) {
                if (elt.getNiveau() != niveau + 1) continue;
                ArrayList<MenuDynamique> arbreNiveau = new ArrayList<MenuDynamique>();
                arbreNiveau.add(elt);
                arbre.add(arbreNiveau);
                return elt;
            }
        }
        return retour;
    }

    public static void getParents(HashSet<MenuDynamique> set, MenuDynamique[] tab, MenuDynamique[] total) {
        for (MenuDynamique elt : tab) {
            set.add(elt);
            MenuDynamique.checkParent(set, elt, total);
        }
    }

    private static void checkParent(HashSet<MenuDynamique> set, MenuDynamique elt, MenuDynamique[] total) {
        for (MenuDynamique em : total) {
            if (!em.getId().equals(elt.getId_pere())) continue;
            set.add(em);
            MenuDynamique.checkParent(set, em, total);
        }
    }

    public static void getFils(HashSet<MenuDynamique> set, MenuDynamique[] tab, MenuDynamique[] total) {
        for (MenuDynamique elt : tab) {
            set.add(elt);
            MenuDynamique.checkFils(set, elt, total);
        }
    }

    private static void checkFils(HashSet<MenuDynamique> set, MenuDynamique elt, MenuDynamique[] total) {
        for (MenuDynamique em : total) {
            if (em.getId_pere() == null || !em.getId_pere().equals(elt.getId())) continue;
            set.add(em);
            MenuDynamique.checkFils(set, em, total);
        }
    }

    public static HashSet<MenuDynamique> getParentsAndFils(MenuDynamique[] tab, MenuDynamique[] total) {
        HashSet<MenuDynamique> set = new HashSet<MenuDynamique>();
        MenuDynamique.getParents(set, tab, total);
        MenuDynamique.getFils(set, tab, total);
        return set;
    }

    public static boolean containsParent(ArrayList<MenuDynamique> liste, String id) {
        boolean ret = false;
        for (MenuDynamique elt : liste) {
            if (elt.getId_pere() == null || !elt.getId_pere().equals(id)) continue;
            ret = true;
            break;
        }
        return ret;
    }

    public static String renderMenuHorizontal(ArrayList<ArrayList<MenuDynamique>> arbre, String currentMenu, MenuDynamique[] tabMenu, ResourceBundle RB) {
        String ret = "";
        if (arbre == null || arbre.size() == 0) {
            return ret;
        }
        int index = 0;
        HashSet<MenuDynamique> set = new HashSet<MenuDynamique>();
        if (currentMenu != null && !currentMenu.equalsIgnoreCase("")) {
            try {
                MenuDynamique m = new MenuDynamique();
                m.setId(currentMenu);
                String[] attr = new String[]{"id"};
                String[] val = new String[]{currentMenu};
                ClassMAPTable[] tabobject = AdminGen.find((ClassMAPTable[])tabMenu, (String[])attr, (String[])val);
                MenuDynamique[] tab = new MenuDynamique[1];
                if (tabobject.length > 0) {
                    tab[0] = (MenuDynamique)tabobject[0];
                    MenuDynamique.getParents(set, tab, tabMenu);
                }
            }
            catch (Exception m) {
                // empty catch block
            }
        }
        ArrayList<MenuDynamique> arbreNiveau = arbre.get(0);
        for (MenuDynamique elt : arbreNiveau) {
            ret = ret + MenuDynamique.renderMenuFilsHorizontal(elt, arbre, index, currentMenu, set, RB);
        }
        return ret;
    }

    public static String renderMenu(ArrayList<ArrayList<MenuDynamique>> arbre, String currentMenu, MenuDynamique[] tabMenu, ResourceBundle RB) {
        String ret = "";
        if (arbre == null || arbre.size() == 0) {
            return ret;
        }
        int index = 0;
        HashSet<MenuDynamique> set = new HashSet<MenuDynamique>();
        if (currentMenu != null && !currentMenu.equalsIgnoreCase("")) {
            try {
                MenuDynamique m = new MenuDynamique();
                m.setId(currentMenu);
                String[] attr = new String[]{"id"};
                String[] val = new String[]{currentMenu};
                ClassMAPTable[] tabobject = AdminGen.find((ClassMAPTable[])tabMenu, (String[])attr, (String[])val);
                MenuDynamique[] tab = new MenuDynamique[1];
                if (tabobject.length > 0) {
                    tab[0] = (MenuDynamique)tabobject[0];
                    MenuDynamique.getParents(set, tab, tabMenu);
                }
            }
            catch (Exception m) {
                // empty catch block
            }
        }
        ArrayList<MenuDynamique> arbreNiveau = arbre.get(0);
        ret = ret + "<div class=\"dropdown\">";
        for (MenuDynamique elt : arbreNiveau) {
            ret = ret + MenuDynamique.renderMenuFils(elt, arbre, index, currentMenu, set, RB);
        }
        ret = ret + "</div>";
        ret = ret + "<script>\n$(document).ready(function () {\n    // 1. RETIRER data-toggle=\"offcanvas\" via JS pour \u00c3\u00a9viter les comportements natifs doubles\n    $('.sidebar-toggle').removeAttr('data-toggle');\n\n    // 2. Forcer la fermeture du menu par d\u00c3\u00a9faut UNIQUEMENT sur mobile\n    if (window.innerWidth <= 768) {\n        localStorage.setItem('menuCollapse', 'true');\n    }\n\n    // 3. Restaurer l'\u00c3\u00a9tat du menu au chargement\n    var savedState = localStorage.getItem('menuCollapse');\n    if (savedState === 'true') {\n        $('body').addClass('sidebar-collapse');\n        $('.treeview-menu').hide();\n    } else if (savedState === null) {\n        // Premi\u00c3\u00a8re visite (sur PC) : menu OUVERT par d\u00c3\u00a9faut\n        localStorage.setItem('menuCollapse', 'false');\n        $('body').removeClass('sidebar-collapse');\n    }\n\n    // 4. Nettoyer les \u00c3\u00a9v\u00c3\u00a9nements et g\u00c3\u00a9rer le clic sur le Toggle\n    $('.sidebar-toggle').off().on('click', function(e) {\n        e.preventDefault();\n        e.stopImmediatePropagation();\n        \n        var isCollapsed = $('body').hasClass('sidebar-collapse');\n        \n        if (isCollapsed) {\n            // OUVRIR la sidebar\n            localStorage.setItem('menuCollapse', 'false');\n            $('body').removeClass('sidebar-collapse');\n        } else {\n            // FERMER la sidebar\n            localStorage.setItem('menuCollapse', 'true');\n            $('body').addClass('sidebar-collapse');\n            $('.treeview-menu').slideUp(300);\n        }\n    });\n\n    // 5. G\u00c3\u00a9rer le clic sur un lien dans la sidebar\n    $('.sidebar a').on('click', function() {\n        if (window.innerWidth <= 768) {\n            // Sur mobile : refermer automatiquement le menu quand on clique sur un lien\n            localStorage.setItem('menuCollapse', 'true');\n            $('body').addClass('sidebar-collapse');\n        } else {\n            // Sur PC : ouvrir le menu complet s'il \u00c3\u00a9tait ferm\u00c3\u00a9 (mode ic\u00c3\u00b4nes)\n            var isCollapsed = $('body').hasClass('sidebar-collapse');\n            if (isCollapsed) {\n                localStorage.setItem('menuCollapse', 'false');\n                $('body').removeClass('sidebar-collapse');\n            }\n        }\n    });\n});\n</script>";
        return ret;
    }

    public static String renderMenuFils(MenuDynamique elt, ArrayList<ArrayList<MenuDynamique>> arbre, int niveau, String currentMenu, HashSet<MenuDynamique> tabMenuParent, ResourceBundle RB) {
        String ret = "";
        ArrayList<MenuDynamique> fils = new ArrayList<MenuDynamique>();
        String classCurrent = "";
        String classeMenu = "left-menu";
        if (currentMenu != null && currentMenu.equalsIgnoreCase(elt.getId())) {
            classCurrent = " currentMenu ";
        }
        if (arbre.size() > niveau + 1) {
            ArrayList<MenuDynamique> arbreNiveauPlusUn = arbre.get(niveau + 1);
            for (MenuDynamique em : arbreNiveauPlusUn) {
                if (em.getId_pere() == null || !em.getId_pere().equals(elt.getId())) continue;
                fils.add(em);
            }
            if (MenuDynamique.containsParent(arbreNiveauPlusUn, elt.getId())) {
                String libtemp1;
                String style = "";
                String classUl = "";
                if (tabMenuParent.contains((Object)elt)) {
                    style = " style=\"display: block;\" ";
                    classUl = " menu-open  ";
                }
                if (RB.containsKey(libtemp1 = elt.getLibelle())) {
                    libtemp1 = RB.getString(libtemp1);
                }
                String redirection = "onclick=\"window.location.href='" + MenuDynamique.getHref(elt) + "'\"";
                ret = ret + "<li><a style='display: block;padding: 8px;' href='" + MenuDynamique.getHref(elt) + "' class='" + classCurrent + " " + classeMenu + "' " + redirection + " ><i style='margin-right: 3.6px;' class='material-symbols-rounded'>" + elt.getIcone() + "</i> <span>" + libtemp1 + "</span> <i class='material-symbols-rounded pull-right'>arrow_drop_down</i></a>\n<ul class=\"treeview-menu " + classUl + "\" " + style + "  >\n";
                for (MenuDynamique ee : fils) {
                    ret = ret + MenuDynamique.renderMenuFils(ee, arbre, niveau + 1, currentMenu, tabMenuParent, RB);
                }
                ret = ret + "</ul></li>";
            } else {
                String libtemp2 = elt.getLibelle();
                if (RB.containsKey(libtemp2)) {
                    libtemp2 = RB.getString(libtemp2);
                }
                ret = ret + "<li><a href=\"" + MenuDynamique.getHref(elt) + "\" class='" + classCurrent + "' ><i class=\"material-symbols-rounded\">" + elt.getIcone() + "</i><span>" + libtemp2 + "</span></a></li>";
            }
        } else {
            String libtemp3 = elt.getLibelle();
            if (RB.containsKey(libtemp3)) {
                libtemp3 = RB.getString(libtemp3);
            }
            ret = ret + "<li><a href=\"" + MenuDynamique.getHref(elt) + "\" class='" + classCurrent + "'><i class=\"material-symbols-rounded\">" + elt.getIcone() + "</i><span>" + libtemp3 + "</span></a></li>";
        }
        return ret;
    }

    public static String renderMenuFilsHorizontal(MenuDynamique elt, ArrayList<ArrayList<MenuDynamique>> arbre, int niveau, String currentMenu, HashSet<MenuDynamique> tabMenuParent, ResourceBundle RB) {
        String ret = "";
        ArrayList<MenuDynamique> fils = new ArrayList<MenuDynamique>();
        String classCurrent = "";
        String dataToggle = "";
        String classParent = "";
        if (niveau == 0) {
            dataToggle = " type='button' data-toggle='dropdown' ";
            classParent = " btn btn-menu ";
        }
        if (currentMenu != null && currentMenu.equalsIgnoreCase(elt.getId())) {
            classCurrent = " currentMenu ";
        }
        if (arbre.size() > niveau + 1) {
            ArrayList<MenuDynamique> arbreNiveauPlusUn = arbre.get(niveau + 1);
            for (MenuDynamique em : arbreNiveauPlusUn) {
                if (em.getId_pere() == null || !em.getId_pere().equals(elt.getId())) continue;
                fils.add(em);
            }
            if (MenuDynamique.containsParent(arbreNiveauPlusUn, elt.getId())) {
                String libtemp1;
                String style = "";
                String classUl = "";
                if (tabMenuParent.contains((Object)elt)) {
                    style = " style=\"display: block;\" ";
                    classUl = " menu-open  ";
                }
                if (RB.containsKey(libtemp1 = elt.getLibelle())) {
                    libtemp1 = RB.getString(libtemp1);
                }
                String redirection = "onclick=\"window.location.href='" + MenuDynamique.getHref(elt) + "'\"";
                ret = ret + "<li class=\"dropdown-submenu\"><a href='" + MenuDynamique.getHref(elt) + "' class='apj-menu-nouveau " + classCurrent + classParent + " dropdown-toggle' " + dataToggle + " " + redirection + " > <span>" + libtemp1 + "</span></a>\n<ul class=\"dropdown-menu " + classUl + " \" " + style + "  >\n\n";
                for (MenuDynamique ee : fils) {
                    ret = ret + MenuDynamique.renderMenuFilsHorizontal(ee, arbre, niveau + 1, currentMenu, tabMenuParent, RB);
                }
                ret = ret + "</ul></li>";
            } else {
                String libtemp2 = elt.getLibelle();
                if (RB.containsKey(libtemp2)) {
                    libtemp2 = RB.getString(libtemp2);
                }
                ret = ret + "<li tabindex=\"-1\"><a href=\"" + MenuDynamique.getHref(elt) + "\" class='" + classCurrent + classParent + "' >" + libtemp2 + "</a></li>";
            }
        } else {
            String libtemp3 = elt.getLibelle();
            if (RB.containsKey(libtemp3)) {
                libtemp3 = RB.getString(libtemp3);
            }
            ret = ret + "<li tabindex=\"-1\"><a href=\"" + MenuDynamique.getHref(elt) + "\" class='" + classCurrent + classParent + "'>" + libtemp3 + "</a></li>";
        }
        return ret;
    }

    private static String getHref(MenuDynamique em) {
        String unificateur = "?";
        if (em.getHref() != null && !em.getHref().isEmpty() && em.getHref().indexOf("?") >= 0) {
            unificateur = "&";
        }
        String ret = em.getHref() != null && !em.getHref().isEmpty() ? em.getHref() + unificateur + "currentMenu=" + em.getId() : "#";
        return ret;
    }

    public static String renderMenuEdition(ArrayList<ArrayList<MenuDynamique>> arbre) {
        String ret = "";
        if (arbre == null || arbre.isEmpty()) {
            return ret;
        }
        ArrayList<MenuDynamique> arbreNiveauZero = arbre.get(0);
        for (MenuDynamique item : arbreNiveauZero) {
            ret = ret + MenuDynamique.renderMenuEditionFils(item, arbre, 0);
        }
        return ret;
    }

    public static String renderMenuEditionFils(MenuDynamique elt, ArrayList<ArrayList<MenuDynamique>> arbre, int niveau) {
        String ret = "";
        ArrayList<MenuDynamique> fils = new ArrayList<MenuDynamique>();
        if (arbre.size() > niveau + 1) {
            ArrayList<MenuDynamique> arbreNiveauSuivant = arbre.get(niveau + 1);
            for (MenuDynamique item : arbreNiveauSuivant) {
                if (item.getId_pere() == null || !item.getId_pere().equals(elt.getId())) continue;
                fils.add(item);
            }
            if (!fils.isEmpty()) {
                ret = ret + "<li>" + elt.getLibelle() + "<ul>";
                for (MenuDynamique itemFils : fils) {
                    ret = ret + MenuDynamique.renderMenuEditionFils(itemFils, arbre, niveau + 1);
                }
                ret = ret + "</ul></li>";
            } else {
                ret = ret + "<li>" + elt.getLibelle() + "</li>";
            }
        } else {
            ret = ret + "<li>" + elt.getLibelle() + "</li>";
        }
        return ret;
    }

    public static MenuDynamique[] orderMenuRecurs(ArrayList<MenuDynamique> dynamMen, MenuDynamique[] di, MenuDynamique[] tabMenu, boolean follow) throws Exception {
        MenuDynamique[] rst = null;
        for (int i = 0; i < di.length; ++i) {
            if (!dynamMen.contains((Object)di[i])) {
                dynamMen.add(di[i]);
            }
            String[] attr = new String[]{"id_pere"};
            String[] val = new String[]{di[i].getId()};
            ClassMAPTable[] cmta = AdminGen.find((ClassMAPTable[])tabMenu, (String[])attr, (String[])val);
            MenuDynamique[] tmpFils = new MenuDynamique[cmta.length];
            for (int co = 0; co < tmpFils.length; ++co) {
                tmpFils[co] = (MenuDynamique)cmta[co];
            }
            if (tmpFils.length == 0) continue;
            MenuDynamique.orderMenuRecurs(dynamMen, tmpFils, tabMenu, false);
        }
        if (follow) {
            rst = new MenuDynamique[dynamMen.size()];
            int co = 0;
            Iterator<MenuDynamique> iterator = dynamMen.iterator();
            while (iterator.hasNext()) {
                MenuDynamique tmp;
                rst[co] = tmp = iterator.next();
                ++co;
            }
            return rst;
        }
        return rst;
    }

    public static ArrayList<ArrayList<MenuDynamique>> getElementMenu(HttpServletRequest request, MapUtilisateur user) {
        MenuDynamique[] interdit = null;
        MenuDynamique[] autorise = null;
        MenuDynamique[] tabMenu = null;
        ArrayList<ArrayList<MenuDynamique>> arbre = new ArrayList<ArrayList<MenuDynamique>>();
        try {
            if (request.getServletContext().getAttribute("tabMenu") != null) {
                tabMenu = (MenuDynamique[])request.getServletContext().getAttribute("tabMenu");
            } else {
                tabMenu = (MenuDynamique[])CGenUtil.rechercher((ClassMAPTable)new MenuDynamique(), null, null, (String)" order by rang");
                request.getServletContext().setAttribute("tabMenu", (Object)tabMenu);
            }
            String reketyInterdit = "select MENUDYNAMIQUE.* from MENUDYNAMIQUE inner join usermenu on usermenu.IDMENU=MENUDYNAMIQUE.ID  where interdit=1 and (refuser='*' or refuser='" + user.getRefuser() + "' OR idrole='" + user.getIdrole() + "'  ) order by MENUDYNAMIQUE.niveau,MENUDYNAMIQUE.rang asc";
            String reketyAutorise = "select MENUDYNAMIQUE.* from MENUDYNAMIQUE inner join usermenu on ( usermenu.IDMENU=MENUDYNAMIQUE.ID)  where (interdit=0 OR interdit is null) and (  refuser='*' or refuser='" + user.getRefuser() + "' OR idrole='" + user.getIdrole() + "'  ) order by MENUDYNAMIQUE.niveau,MENUDYNAMIQUE.rang asc";
            interdit = (MenuDynamique[])CGenUtil.rechercher((ClassMAPTable)new MenuDynamique(), (String)reketyInterdit);
            autorise = (MenuDynamique[])CGenUtil.rechercher((ClassMAPTable)new MenuDynamique(), (String)reketyAutorise);
            List<MenuDynamique> listInterdit = Arrays.asList(interdit);
            HashSet<MenuDynamique> listAutorise = MenuDynamique.getParentsAndFils(autorise, tabMenu);
            ArrayList<MenuDynamique> arbreNiveau = new ArrayList<MenuDynamique>();
            for (MenuDynamique elt : tabMenu) {
                if (listInterdit.contains((Object)elt) || !listAutorise.contains((Object)elt)) continue;
                if (arbre.isEmpty() && elt.getNiveau() != 1) {
                    for (int i = 0; i < elt.getNiveau(); ++i) {
                        arbre.add(new ArrayList());
                    }
                }
                if (arbre.isEmpty()) {
                    arbreNiveau.add(elt);
                    arbre.add(arbreNiveau);
                    continue;
                }
                if (arbre.size() >= elt.getNiveau()) {
                    arbre.get(elt.getNiveau() - 1).add(elt);
                    continue;
                }
                arbreNiveau = new ArrayList();
                arbreNiveau.add(elt);
                arbre.add(arbreNiveau);
            }
        }
        catch (Exception ex) {
            Logger.getLogger(MenuDynamique.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return arbre;
    }

    public static String renderMenu(ArrayList<ArrayList<MenuDynamique>> arbre, String currentMenu, MenuDynamique[] tabMenu) {
        String ret = "";
        if (arbre == null || arbre.size() == 0) {
            return ret;
        }
        int index = 0;
        HashSet<MenuDynamique> set = new HashSet<MenuDynamique>();
        if (currentMenu != null && !currentMenu.equalsIgnoreCase("")) {
            try {
                MenuDynamique m = new MenuDynamique();
                m.setId(currentMenu);
                String[] attr = new String[]{"id"};
                String[] val = new String[]{currentMenu};
                ClassMAPTable[] tabobject = AdminGen.find((ClassMAPTable[])tabMenu, (String[])attr, (String[])val);
                MenuDynamique[] tab = new MenuDynamique[1];
                if (tabobject.length > 0) {
                    tab[0] = (MenuDynamique)tabobject[0];
                    MenuDynamique.getParents(set, tab, tabMenu);
                }
            }
            catch (Exception m) {
                // empty catch block
            }
        }
        ArrayList<MenuDynamique> arbreNiveau = arbre.get(0);
        for (MenuDynamique elt : arbreNiveau) {
            ret = ret + MenuDynamique.renderMenuFils(elt, arbre, index, currentMenu, set);
        }
        return ret;
    }

    public static String renderMenuFils(MenuDynamique elt, ArrayList<ArrayList<MenuDynamique>> arbre, int niveau, String currentMenu, HashSet<MenuDynamique> tabMenuParent) {
        String ret = "";
        ArrayList<MenuDynamique> fils = new ArrayList<MenuDynamique>();
        String classCurrent = "";
        if (currentMenu != null && currentMenu.equalsIgnoreCase(elt.getId())) {
            classCurrent = " currentMenu ";
        }
        if (arbre.size() > niveau + 1) {
            ArrayList<MenuDynamique> arbreNiveauPlusUn = arbre.get(niveau + 1);
            for (MenuDynamique em : arbreNiveauPlusUn) {
                if (em.getId_pere() == null || !em.getId_pere().equals(elt.getId())) continue;
                fils.add(em);
            }
            if (MenuDynamique.containsParent(arbreNiveauPlusUn, elt.getId())) {
                String style = "";
                String classUl = "";
                if (tabMenuParent.contains((Object)elt)) {
                    style = " style=\"display: block;\" ";
                    classUl = " menu-open  ";
                }
                ret = ret + "<li><a href='" + MenuDynamique.getHref(elt) + "' class=' " + classCurrent + "' ><i class='material-symbols-rounded'>" + elt.getIcone() + "</i> <span>" + elt.getLibelle() + "</span> <i class='material-symbols-rounded pull-right'>arrow_drop_down</i></a>\n<ul class=\"treeview-menu " + classUl + " \" " + style + "  >\n\n";
                for (MenuDynamique ee : fils) {
                    ret = ret + MenuDynamique.renderMenuFils(ee, arbre, niveau + 1, currentMenu, tabMenuParent);
                }
                ret = ret + "</ul></li>";
            } else {
                ret = ret + "<li><a href=\"" + MenuDynamique.getHref(elt) + "\" class='" + classCurrent + "' ><i style=\"display: block;\" class=\"material-symbols-rounded\">" + elt.getIcone() + "</i><span>" + elt.getLibelle() + "</span></a></li>";
            }
        } else {
            ret = ret + "<li><a href=\"" + MenuDynamique.getHref(elt) + "\" class='" + classCurrent + "'><i style=\"display: block;\" class=\"material-symbols-rounded\">" + elt.getIcone() + "</i><span>" + elt.getLibelle() + "</span></a></li>";
        }
        return ret;
    }

    public static String renderMenuJson(ArrayList<ArrayList<MenuDynamique>> arbre, ResourceBundle RB) {
        if (arbre == null || arbre.isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        ArrayList<MenuDynamique> niveau1 = arbre.get(0);
        for (int i = 0; i < niveau1.size(); ++i) {
            if (i > 0) {
                sb.append(",");
            }
            MenuDynamique.appendMenuJson(sb, niveau1.get(i), arbre, 0, RB);
        }
        sb.append("]");
        return sb.toString();
    }

    private static void appendMenuJson(StringBuilder sb, MenuDynamique elt, ArrayList<ArrayList<MenuDynamique>> arbre, int niveau, ResourceBundle RB) {
        String libelle = elt.getLibelle();
        if (RB != null && RB.containsKey(libelle)) {
            libelle = RB.getString(libelle);
        }
        sb.append("{");
        sb.append("\"id\":").append(MenuDynamique.jsonString(elt.getId())).append(",");
        sb.append("\"libelle\":").append(MenuDynamique.jsonString(libelle)).append(",");
        sb.append("\"icone\":").append(MenuDynamique.jsonString(elt.getIcone())).append(",");
        sb.append("\"href\":").append(MenuDynamique.jsonString(MenuDynamique.getHref(elt))).append(",");
        sb.append("\"description\":").append(MenuDynamique.jsonString(elt.getDescription())).append(",");
        sb.append("\"niveau\":").append(elt.getNiveau()).append(",");
        sb.append("\"fils\":[");
        if (arbre.size() > niveau + 1) {
            ArrayList<MenuDynamique> niveauSuivant = arbre.get(niveau + 1);
            boolean premierFils = true;
            for (MenuDynamique enfant : niveauSuivant) {
                if (enfant.getId_pere() == null || !enfant.getId_pere().equals(elt.getId())) continue;
                if (!premierFils) {
                    sb.append(",");
                }
                MenuDynamique.appendMenuJson(sb, enfant, arbre, niveau + 1, RB);
                premierFils = false;
            }
        }
        sb.append("]}");
    }

    private static String jsonString(String value) {
        if (value == null) {
            return "null";
        }
        return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t") + "\"";
    }
}

