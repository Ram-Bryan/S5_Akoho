/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  user.UserEJB
 */
package affichage;

import affichage.Champ;
import affichage.Formulaire;
import affichage.TableauRecherche;
import bean.CGenUtil;
import bean.ClassMAPTable;
import bean.ListeColonneTable;
import chatbot.ChatBot;
import java.lang.reflect.Field;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import user.UserEJB;
import utilitaire.Utilitaire;

public class Page {
    Formulaire formu;
    private TableauRecherche tableau;
    private TableauRecherche tableauRecap;
    private String lien;
    private TableauRecherche tableauRegroup;
    private ClassMAPTable base;
    private ClassMAPTable[] baseTableau;
    private String titre;
    private UserEJB utilisateur;
    private String html;
    private HttpServletRequest req;
    private boolean testRecap = false;
    private int nombreLigne = 0;
    private String pathPage;
    private String[] tabIndexe;
    Champ[] champAdditionnel;
    private boolean isModalOnClick = false;
    private boolean isOnePage = false;
    boolean isGraphe = false;
    boolean estScrollable = false;

    public boolean getEstScrollable() {
        return this.estScrollable;
    }

    public void setEstScrollable(boolean estScrollable) {
        this.estScrollable = estScrollable;
    }

    public boolean getIsGraphe() {
        return this.isGraphe;
    }

    public void setIsGraphe(boolean graphe) {
        this.isGraphe = graphe;
    }

    public boolean isModalOnClick() {
        return this.isModalOnClick;
    }

    public void setModalOnClick(boolean modalOnClick) {
        this.isModalOnClick = modalOnClick;
    }

    public boolean isOnePage() {
        return this.isOnePage;
    }

    public void setOnePage(boolean isOnePage) {
        this.isOnePage = isOnePage;
    }

    public Champ[] getChampAdditionnel() {
        return this.champAdditionnel;
    }

    public void setChampAdditionnel(Champ[] additionnel) {
        this.champAdditionnel = additionnel;
    }

    public String[] getTabIndexe() {
        return this.tabIndexe;
    }

    public void setTabIndexe(String[] tabIndexe) {
        this.tabIndexe = tabIndexe;
    }

    public int getNombreLigne() {
        return this.nombreLigne;
    }

    public String getPathPage() {
        return this.pathPage;
    }

    public void setPathPage(String pathPage) {
        this.pathPage = pathPage;
    }

    public void setNombreLigne(int nombreLigne) {
        if (nombreLigne <= 0) {
            this.nombreLigne = 10;
        }
        this.nombreLigne = nombreLigne;
    }

    public Page() {
    }

    public Page(ClassMAPTable p, HttpServletRequest r) {
        this.setBase(p);
        this.setReq(r);
    }

    public Page(ClassMAPTable p, HttpServletRequest r, int nombreligne) {
        this.setBase(p);
        this.setReq(r);
        this.setNombreLigne(nombreligne);
    }

    public void makeHtml() {
    }

    public void makeHtmlTemplate() throws Exception {
    }

    public Formulaire getFormu() {
        return this.formu;
    }

    public void setFormu(Formulaire formu) {
        this.formu = formu;
    }

    public void setTableau(TableauRecherche tableau) {
        this.tableau = tableau;
    }

    public TableauRecherche getTableau() {
        return this.tableau;
    }

    public void setBase(ClassMAPTable base) {
        this.base = base;
    }

    public void setBaseTableau(ClassMAPTable[] baseT) {
        this.baseTableau = baseT;
    }

    public ClassMAPTable[] getBaseTableau() {
        return this.baseTableau;
    }

    public ClassMAPTable getBase() {
        return this.base;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getLien() {
        return this.lien;
    }

    public TableauRecherche getTableauRecap() {
        return this.tableauRecap;
    }

    public void setTableauRecap(TableauRecherche tableauRecap) {
        this.testRecap = true;
        this.tableauRecap = tableauRecap;
    }

    public void setTableauRegroup(TableauRecherche tableauRegroup) {
        this.tableauRegroup = tableauRegroup;
    }

    public TableauRecherche getTableauRegroup() {
        return this.tableauRegroup;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setUtilisateur(UserEJB utilisateur) {
        this.utilisateur = utilisateur;
    }

    public boolean getTestRecap() {
        return this.testRecap;
    }

    public UserEJB getUtilisateur() {
        return this.utilisateur;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getHtml() throws Exception {
        return this.getHtmlTemplate();
    }

    public String getHtmlTemplate() throws Exception {
        if (this.html == null || this.html.compareToIgnoreCase("") == 0) {
            this.makeHtmlTemplate();
        }
        return this.html;
    }

    public String getEvaluation(String apiUrl, String apiKey, String contexte) {
        try {
            String prompt = "Tu es un analyste strat\u00c3\u00a9gique expert. Analyse les donn\u00c3\u00a9es du tableau ci-dessous et fournis une analyse strat\u00c3\u00a9gique destin\u00c3\u00a9e aux dirigeants. ";
            prompt = prompt + "Identifie les insights pertinents, les opportunit\u00c3\u00a9s, les risques et propose des recommandations actionnables. ";
            prompt = prompt + "Sois bref, clair et complet. Retourne uniquement l'analyse, sans mentionner de titre ni d'en-t\u00c3\u00aate.";
            prompt = prompt + contexte;
            String reponse = ChatBot.callGenerativeAI(prompt, apiUrl, apiKey);
            return reponse;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String reformatPageContent(String pageContent) {
        return pageContent.replaceAll("\"", "").replaceAll("'", "").replaceAll("\\n", " ").replaceAll("\\t", " ").replaceAll("\\r", " ");
    }

    public String getHtmlWithEvaluation(String apiUrl, String apiKey, String title, String contexte) {
        try {
            String safeTitle = (this.getTitre() != null ? this.getTitre() : title).replace("`", "\\`");
            String safeContexte = contexte != null ? contexte.replace("`", "\\`") : "";
            String safeApiUrl = apiUrl != null ? apiUrl : "";
            StringBuilder html = new StringBuilder();
            html.append("<style>");
            html.append(".ai-card-interpretation { background-color: var(--Primaire-blue-050); border-radius: 12px; padding: 20px; box-shadow: 0 2px 8px rgba(0,0,0,0.08); margin-top: 15px; margin-bottom: 25px; border: 1px solid #e0e0e0; display: none; opacity: 0; transition: opacity 0.5s ease-in-out; }");
            html.append(".ai-header-toggle { display: flex; justify-content: space-between; align-items: center; cursor: pointer; user-select: none; }");
            html.append(".ai-header-toggle:hover .ai-title-text { color: #3b71ca; }");
            html.append(".skeleton-line { height: 14px; background: linear-gradient(90deg, #f0f0f0 25%, #e0e0e0 50%, #f0f0f0 75%); background-size: 200% 100%; animation: shimmer 1.5s infinite; border-radius: 4px; margin-bottom: 12px; }");
            html.append("@keyframes shimmer { 0% { background-position: 200% 0; } 100% { background-position: -200% 0; } }");
            html.append(".btn-ai-action { display: inline-flex; align-items: center; gap: 8px; font-family: inherit; font-size: 14px; font-weight: 500; border: none; cursor: pointer; transition: all 0.3s ease;}");
            html.append(".btn-ai-solid { background-color: #3b71ca; color: white; padding: 10px 24px; border-radius: 50px; }");
            html.append(".btn-ai-solid:hover { background-color: #2b5bad; }");
            html.append(".btn-ai-ghost { background-color: transparent; color: #333; }");
            html.append(".ai-icon-circle { display: inline-flex; align-items: center; justify-content: center; width: 32px; height: 32px; background-color: #3b71ca; color: white; border-radius: 50%; }");
            html.append("@keyframes spin-slow { 100% { transform: rotate(360deg); } }");
            html.append(".text-shimmer-black-white { background: linear-gradient(90deg, #000000 20%, #ffffff 50%, #000000 80%); background-size: 200% auto; color: transparent; -webkit-background-clip: text; background-clip: text; animation: shimmer-text 2s linear infinite; font-weight: 600; }");
            html.append("@keyframes shimmer-text { to { background-position: 200% center; } }");
            html.append("</style>");
            html.append("<div id=\"aiButtonContainer\">");
            html.append("  <button id=\"aiTriggerBtn\" class=\"btn-ai-action btn-ai-solid btn btn-primary btn-small\" type=\"button\" data-state=\"ready\">");
            html.append("    <i class=\"material-symbols-rounded\">auto_awesome</i> <span>Analyser la page</span>");
            html.append("  </button>");
            html.append("</div>");
            html.append("<div id=\"aiContainer\" class=\"ai-card-interpretation\">");
            html.append("  <div class=\"ai-header-toggle\" onclick=\"toggleAiContent()\">");
            html.append("    <div style=\"font-weight:600; font-size: 18px; color: #202124; display: flex; align-items: center; gap: 8px;\" class=\"ai-title-text\">");
            html.append("      <i class=\"material-symbols-rounded\" style=\"color: #3b71ca; font-size: 24px;\">auto_awesome</i>");
            html.append("    </div>");
            html.append("  </div>");
            html.append("  <div id=\"aiBody\" style=\"margin-top: 20px; overflow: hidden; display: block;\">");
            html.append("    <div id=\"aiPlaceholder\">");
            html.append("      <div class=\"skeleton-line\" style=\"width: 90%;\"></div>");
            html.append("      <div class=\"skeleton-line\" style=\"width: 75%;\"></div>");
            html.append("      <div class=\"skeleton-line\" style=\"width: 95%;\"></div>");
            html.append("    </div>");
            html.append("    <div id=\"aiContent\" style=\"display: none; line-height: 1.6; color: var(--Text-primary);\"></div>");
            html.append("  </div>");
            html.append("</div>");
            html.append("<script>");
            html.append("document.addEventListener('DOMContentLoaded', function() {");
            html.append("    const btn = document.getElementById('aiTriggerBtn');");
            html.append("    const aiCard = document.getElementById('aiContainer');");
            html.append("    const placeholder = document.getElementById('aiPlaceholder');");
            html.append("    const contentBox = document.getElementById('aiContent');");
            html.append("    const jsTitle = `").append(safeTitle).append("`;");
            html.append("    const jsContexte = `").append(safeContexte).append("`;");
            html.append("    const jsApiUrl = `").append(safeApiUrl).append("`;");
            html.append("    const jsApiKey = `").append(apiKey).append("`;");
            html.append("    const targetBtn = document.querySelector('.btn-secondary.btn-bg-white.btn.btn-small.filtre-btn');");
            html.append("    if(targetBtn) {");
            html.append("        document.getElementById('aiButtonContainer').style.display = 'flex';");
            html.append("        document.getElementById('aiButtonContainer').style.marginLeft = '8px';");
            html.append("        document.getElementById('aiButtonContainer').style.alignItems = 'center';");
            html.append("        targetBtn.insertAdjacentElement('afterend', document.getElementById('aiButtonContainer'));");
            html.append("    }");
            html.append("    const selectnonee = document.querySelector('.box #selectnonee');");
            html.append("    if(selectnonee) {");
            html.append("        const boxDiv = selectnonee.closest('.box');");
            html.append("        if(boxDiv && boxDiv.parentNode) {");
            html.append("            boxDiv.parentNode.insertBefore(aiCard, boxDiv);");
            html.append("        }");
            html.append("    }");
            html.append("    function typeWriterHTML(htmlString, elementId, speed) {");
            html.append("        const el = document.getElementById(elementId); el.innerHTML = '';");
            html.append("        let i = 0; let isTag = false; let textToDisplay = '';");
            html.append("        function type() {");
            html.append("            if (i < htmlString.length) {");
            html.append("                let char = htmlString.charAt(i);");
            html.append("                if (char === '<') isTag = true;");
            html.append("                textToDisplay += char; el.innerHTML = textToDisplay;");
            html.append("                i++;");
            html.append("                if (isTag) { if (char === '>') isTag = false; type(); } ");
            html.append("                else { setTimeout(type, speed); }");
            html.append("            }");
            html.append("        }");
            html.append("        type();");
            html.append("    }");
            html.append("    btn.addEventListener('click', async function() {");
            html.append("        const state = this.getAttribute('data-state');");
            html.append("        if (state === 'ready') {");
            html.append("            this.className = 'btn-ai-action btn-ai-ghost';");
            html.append("            this.innerHTML = '<span class=\"ai-icon-circle\" style=\"width: 40px; height: 40px;\"><i class=\"material-symbols-rounded\" >auto_awesome</i></span> <span class=\"text-shimmer-black-white\">Analyse de la page en cours...</span>';");
            html.append("            this.setAttribute('data-state', 'loading');");
            html.append("const mainTables = document.querySelectorAll('.box #selectnonee');");
            html.append("let tableContent = '';");
            html.append("mainTables.forEach(function(mainTable) {");
            html.append("    tableContent += mainTable.innerText + '\\n';");
            html.append("});");
            html.append("            const promptStr = `Titre: ${jsTitle}\\nContexte: ${jsContexte}\\nDonn\u00c3\u00a9es:\\n${tableContent}`;");
            html.append("            try {");
            html.append("                const formData = new URLSearchParams();");
            html.append("                formData.append('prompt', promptStr);");
            html.append("                formData.append('apiUrl', jsApiUrl);");
            html.append("                formData.append('apiKey', jsApiKey);");
            html.append("                const contextPath = '/' + window.location.pathname.split('/')[1];");
            html.append("                const response = await fetch(contextPath + '/ai-evaluation', {");
            html.append("                    method: 'POST',");
            html.append("                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },");
            html.append("                    body: formData");
            html.append("                });");
            html.append("                if (!response.ok) throw new Error('Erreur r\u00c3\u00a9seau');");
            html.append("                const responseText = await response.text();");
            html.append("                btn.innerHTML = '<span class=\"ai-icon-circle\"><i class=\"material-symbols-rounded\">check</i></span> <span>Analyse termin&eacute;e</span>';");
            html.append("                setTimeout(() => {");
            html.append("                    btn.innerHTML = '<i class=\"material-symbols-rounded\" style=\"color:#3b71ca;\">auto_awesome</i> <span style=\"color:#3b71ca; font-weight:600;\">Masquer analyse IA</span>';");
            html.append("                    btn.setAttribute('data-state', 'show');");
            html.append("                    aiCard.style.display = 'block';");
            html.append("                    setTimeout(() => { aiCard.style.opacity = '1'; }, 10);");
            html.append("                    setTimeout(() => {");
            html.append("                        placeholder.style.display = 'none';");
            html.append("                        contentBox.style.display = 'block';");
            html.append("                        let parsedHTML = responseText;");
            html.append("                        if (typeof marked !== 'undefined') { parsedHTML = marked.parse(responseText); }");
            html.append("                        typeWriterHTML(parsedHTML, 'aiContent', 10);");
            html.append("                    }, 800);");
            html.append("                }, 1000);");
            html.append("            } catch (error) {");
            html.append("                console.error(error);");
            html.append("                btn.className = 'btn-ai-action btn-ai-ghost btn btn-primary btn-small';");
            html.append("                btn.innerHTML = '<i class=\"material-symbols-rounded\" style=\"color:red;\">error</i> <span style=\"color:red;\">\u00c3\u0089chec de l\\'analyse. R\u00c3\u00a9essayer.</span>';");
            html.append("                btn.setAttribute('data-state', 'ready');");
            html.append("            }");
            html.append("        } else if (state === 'show' || state === 'hide') {");
            html.append("            toggleAiContent();");
            html.append("        }");
            html.append("    });");
            html.append("    window.toggleAiContent = function() {");
            html.append("        if (aiCard.style.display === 'none' || aiCard.style.opacity === '0') {");
            html.append("            aiCard.style.display = 'block';");
            html.append("            setTimeout(() => { aiCard.style.opacity = '1'; }, 10);");
            html.append("            btn.innerHTML = '<i class=\"material-symbols-rounded\" style=\"color:#3b71ca;\">auto_awesome</i> <span style=\"color:#3b71ca; font-weight:600;\">Afficher analyse IA</span>';");
            html.append("            btn.setAttribute('data-state', 'hide');");
            html.append("        } else {");
            html.append("            aiCard.style.opacity = '0';");
            html.append("            setTimeout(() => { aiCard.style.display = 'none'; }, 500);");
            html.append("            btn.innerHTML = '<i class=\"material-symbols-rounded\" style=\"color:#3b71ca;\">auto_awesome</i> <span style=\"color:#3b71ca; font-weight:600;\">Masquer analyse IA</span>';");
            html.append("            btn.setAttribute('data-state', 'show');");
            html.append("        }");
            html.append("    };");
            html.append("});");
            html.append("</script>");
            return html.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "<div class=\"alert alert-danger\">Erreur de g\u00c3\u00a9n\u00c3\u00a9ration du composant IA.</div>";
        }
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
        if (this.getReq() != null) {
            this.setPathPage(this.getReq().getParameter("but"));
        }
    }

    public HttpServletRequest getReq() {
        return this.req;
    }

    public String getParamSansNull(String nomP) {
        String temp = this.getReq().getParameter(Utilitaire.remplacerUnderscore(nomP));
        if (temp != null && temp.compareToIgnoreCase("") != 0) {
            temp = temp.replace("'", "'");
            return temp;
        }
        return "";
    }

    public String getParamSansNull(String nomP, HashMap<String, String> valeurs) {
        String temp = valeurs.get(Utilitaire.remplacerUnderscore(nomP));
        if (temp != null && temp.compareToIgnoreCase("") != 0) {
            return temp;
        }
        return "";
    }

    public void getValeurFormulaire() throws Exception {
        Champ[] af;
        Champ[] c = this.formu.getListeChamp();
        for (int i = 0; i < c.length; ++i) {
            String valeurRecup = c[i].getEstMultiple() ? this.getParamSansNullMultiple(c[i].getNom()) : this.getParamSansNull(c[i].getNom());
            c[i].setValeur(valeurRecup);
        }
        Champ[] f = this.formu.getChampGroupe();
        if (f != null) {
            for (int j = 0; j < f.length; ++j) {
                f[j].setValeur(this.getParamSansNull(f[j].getNom()));
            }
        }
        if ((af = this.formu.getChampTableauAff()) != null) {
            for (int j = 0; j < af.length; ++j) {
                af[j].setValeur(this.getParamSansNull(af[j].getNom()));
            }
        }
    }

    public ClassMAPTable getObjectAvecValeur() throws Exception {
        Field[] tempChamp = ListeColonneTable.getFieldListeHeritage(this.getBase());
        for (int i = 0; i < tempChamp.length; ++i) {
            Field f = tempChamp[i];
            String valeur = this.getParamSansNull(f.getName());
            if (f.getType().getName().compareToIgnoreCase("java.lang.String") == 0) {
                CGenUtil.setValChamp(this.getBase(), f, valeur);
            }
            if (f.getType().getName().compareToIgnoreCase("java.sql.Date") == 0) {
                CGenUtil.setValChamp(this.getBase(), f, Utilitaire.string_date("dd/MM/yyyy", valeur));
            }
            if (f.getType().getName().compareToIgnoreCase("double") == 0) {
                CGenUtil.setValChamp(this.getBase(), f, new Double(Utilitaire.stringToDouble(valeur)));
            }
            if (f.getType().getName().compareToIgnoreCase("int") == 0 && valeur != null && valeur.compareToIgnoreCase("") != 0) {
                CGenUtil.setValChamp(this.getBase(), f, new Integer(valeur));
            }
            if (f.getType().getName().compareToIgnoreCase("float") != 0) continue;
            CGenUtil.setValChamp(this.getBase(), f, new Float(Utilitaire.stringToFloat(valeur)));
        }
        if (this.getBase().getEstHistorise()) {
            this.getBase().setMemo(this.getParamSansNull("memo"));
        }
        return this.getBase();
    }

    public static String getContenuServletWithTiret(String valeurFiltre, String nomTable, String nomClasse, String nomColoneFiltre, String nomColvaleur, String nomColAffiche, boolean esttiret) throws Exception {
        ClassMAPTable filtre = (ClassMAPTable)Class.forName(nomClasse).newInstance();
        filtre.setNomTable(nomTable);
        String rekety = "select * from " + nomTable + " where " + nomColoneFiltre + "='" + valeurFiltre + "'";
        ClassMAPTable[] listee = (ClassMAPTable[])CGenUtil.rechercher(filtre, rekety);
        String valeur = "[";
        if (esttiret) {
            valeur = valeur + "{\"id\":\"\",\"valeur\":\"-\"}";
            if (listee.length > 0) {
                valeur = valeur + ",";
            }
        }
        for (int i = 0; i < listee.length; ++i) {
            ClassMAPTable to = listee[i];
            String valeurId = (String)CGenUtil.getValeurFieldByMethod(to, nomColvaleur);
            String valeurAffiche = (String)CGenUtil.getValeurFieldByMethod(to, nomColAffiche);
            valeur = valeur + "{\"id\":\"" + valeurId + "\",\"valeur\":\"" + valeurAffiche + "\"}";
            if (i >= listee.length - 1) continue;
            valeur = valeur + ",";
        }
        valeur = valeur + "]";
        return "{\"valeure\":" + valeur + "}";
    }

    public ClassMAPTable getObjectAvecValeur(HashMap<String, String> listeValeur) throws Exception {
        try {
            Field[] tempChamp = ListeColonneTable.getFieldListeHeritage(this.getBase());
            for (int i = 0; i < tempChamp.length; ++i) {
                Field f = tempChamp[i];
                String valeur = this.getParamSansNull(f.getName(), listeValeur);
                if (f.getType().getName().compareToIgnoreCase("java.lang.String") == 0) {
                    CGenUtil.setValChamp(this.getBase(), f, valeur);
                }
                if (f.getType().getName().compareToIgnoreCase("java.sql.Date") == 0) {
                    CGenUtil.setValChamp(this.getBase(), f, Utilitaire.string_date("dd/MM/yyyy", valeur));
                }
                if (f.getType().getName().compareToIgnoreCase("double") == 0) {
                    CGenUtil.setValChamp(this.getBase(), f, new Double(Utilitaire.stringToDouble(valeur)));
                }
                if (f.getType().getName().compareToIgnoreCase("int") == 0 && valeur != null && valeur.compareToIgnoreCase("") != 0) {
                    CGenUtil.setValChamp(this.getBase(), f, new Integer(valeur));
                }
                if (f.getType().getName().compareToIgnoreCase("float") != 0) continue;
                CGenUtil.setValChamp(this.getBase(), f, new Float(Utilitaire.stringToFloat(valeur)));
            }
            return this.getBase();
        }
        catch (NumberFormatException n) {
            throw new Exception("format de nombre invalide");
        }
        catch (Exception e) {
            throw e;
        }
    }

    public ClassMAPTable[] getObjectAvecValeurTableau() throws Exception {
        int nombreLigne = this.getNombreLigne();
        ClassMAPTable[] liste = new ClassMAPTable[nombreLigne];
        try {
            Field[] tempChamp = ListeColonneTable.getFieldListeHeritage(this.getBase());
            int x = 0;
            for (int iLigne = 0; iLigne < nombreLigne; ++iLigne) {
                ClassMAPTable ex = (ClassMAPTable)Class.forName(this.getBase().getClassName()).newInstance();
                for (int i = 0; i < tempChamp.length; ++i) {
                    Field f = tempChamp[i];
                    String nomChamp = f.getName() + "_" + iLigne;
                    String valeur = this.getParamSansNull(nomChamp);
                    if (i == 1 && (valeur == null || valeur.compareTo("") == 0)) break;
                    if (f.getType().getName().compareToIgnoreCase("java.lang.String") == 0) {
                        CGenUtil.setValChamp(ex, f, valeur);
                    }
                    if (f.getType().getName().compareToIgnoreCase("java.sql.Date") == 0) {
                        CGenUtil.setValChamp(ex, f, Utilitaire.string_date("dd/MM/yyyy", valeur));
                    }
                    if (f.getType().getName().compareToIgnoreCase("double") == 0) {
                        CGenUtil.setValChamp(ex, f, new Double(Utilitaire.stringToDouble(valeur)));
                    }
                    if (f.getType().getName().compareToIgnoreCase("int") == 0) {
                        if (valeur == null || valeur.compareToIgnoreCase("") == 0) {
                            CGenUtil.setValChamp(ex, f, new Integer(0));
                        } else {
                            CGenUtil.setValChamp(ex, f, new Integer(valeur));
                        }
                    }
                    if (f.getType().getName().compareToIgnoreCase("float") == 0) {
                        CGenUtil.setValChamp(ex, f, new Float(Utilitaire.stringToFloat(valeur)));
                    }
                    if (i != 1) continue;
                    ++x;
                }
                liste[iLigne] = ex;
            }
            ClassMAPTable[] ret = new ClassMAPTable[x];
            for (int j = 0; j < x; ++j) {
                ret[j] = liste[j];
            }
            return ret;
        }
        catch (NumberFormatException n) {
            throw new Exception("format de nombre invalide");
        }
        catch (Exception e) {
            throw e;
        }
    }

    public void getValeurFormulaireMultiple() throws Exception {
        Champ[] af;
        Champ[] c = this.formu.getListeChamp();
        for (int i = 0; i < c.length; ++i) {
            String valeurRecup = this.getParamSansNullMultiple(c[i].getNom());
            c[i].setValeur(valeurRecup);
        }
        Champ[] f = this.formu.getChampGroupe();
        if (f != null) {
            for (int j = 0; j < f.length; ++j) {
                f[j].setValeur(this.getParamSansNullMultiple(f[j].getNom()));
            }
        }
        if ((af = this.formu.getChampTableauAff()) != null) {
            for (int j = 0; j < af.length; ++j) {
                af[j].setValeur(this.getParamSansNullMultiple(af[j].getNom()));
            }
        }
    }

    public String getParamSansNullMultiple(String nomP) {
        String[] listetemp = this.getReq().getParameterValues(Utilitaire.remplacerUnderscore(nomP));
        String temp = "";
        if (listetemp != null) {
            temp = listetemp[0];
            for (int i = 1; i < listetemp.length; ++i) {
                if (listetemp[i].compareToIgnoreCase("") == 0) continue;
                temp = temp + "','" + listetemp[i];
            }
        }
        if (nomP.compareTo("colonne") == 0 || nomP.compareTo("ordre") == 0) {
            temp = this.getReq().getParameter(Utilitaire.remplacerUnderscore(nomP));
        }
        if (temp != null && temp.compareToIgnoreCase("") != 0) {
            return temp;
        }
        return "";
    }

    public static String getContenuServlet(String valeurFiltre, String nomTable, String nomClasse, String nomColoneFiltre, String nomColvaleur, String nomColAffiche) throws Exception {
        ClassMAPTable filtre = (ClassMAPTable)Class.forName(nomClasse).newInstance();
        filtre.setNomTable(nomTable);
        String rekety = "select * from " + nomTable + " where " + nomColoneFiltre + "='" + valeurFiltre + "'";
        ClassMAPTable[] listee = (ClassMAPTable[])CGenUtil.rechercher(filtre, rekety);
        String valeur = "[";
        for (int i = 0; i < listee.length; ++i) {
            ClassMAPTable to = listee[i];
            String valeurId = (String)CGenUtil.getValeurFieldByMethod(to, nomColvaleur);
            Object val = CGenUtil.getValeurFieldByMethod(to, nomColAffiche);
            String valeurAffiche = val.toString();
            valeur = valeur + "{\"id\":\"" + valeurId + "\",\"valeur\":\"" + valeurAffiche + "\"}";
            if (i >= listee.length - 1) continue;
            valeur = valeur + ",";
        }
        valeur = valeur + "]";
        return "{\"valeure\":" + valeur + "}";
    }

    public static String getContenuServlet(String valeurFiltre, String nomTable, String nomClasse, String nomColoneFiltre, String nomColvaleur, String nomColAffiche, String nomOrderby, String sensOrderBy, boolean liste) throws Exception {
        ClassMAPTable filtre = (ClassMAPTable)Class.forName(nomClasse).newInstance();
        filtre.setNomTable(nomTable);
        String rekety = "select * from " + nomTable + " where " + nomColoneFiltre + "='" + valeurFiltre + "' order by " + nomOrderby + " " + sensOrderBy;
        ClassMAPTable[] listee = (ClassMAPTable[])CGenUtil.rechercher(filtre, rekety);
        int taille = listee.length;
        if (!liste) {
            taille = 1;
        }
        String valeur = "[";
        for (int i = 0; i < taille; ++i) {
            ClassMAPTable to = listee[i];
            String valeurId = (String)CGenUtil.getValeurFieldByMethod(to, nomColvaleur);
            String valeurAffiche = CGenUtil.getValeurFieldByMethod(to, nomColAffiche).toString();
            valeur = valeur + "{\"id\":\"" + valeurId + "\",\"valeur\":\"" + valeurAffiche + "\"}";
            if (i >= taille - 1) continue;
            valeur = valeur + ",";
        }
        valeur = valeur + "]";
        return "{\"valeure\":" + valeur + "}";
    }

    public ClassMAPTable[] getObjectAvecValeurTableauUpdate() throws Exception {
        int nombreLigne = this.getNombreLigne();
        String[] tabIndexe = this.getTabIndexe();
        if (tabIndexe == null) {
            throw new Exception("Vous devez cocher");
        }
        ClassMAPTable[] liste = new ClassMAPTable[tabIndexe.length];
        try {
            Field[] tempChamp = ListeColonneTable.getFieldListeHeritage(this.getBase());
            int x = 0;
            for (int iLigne = 0; iLigne < nombreLigne; ++iLigne) {
                ClassMAPTable ex = (ClassMAPTable)Class.forName(this.getBase().getClassName()).newInstance();
                for (int indice = 0; indice < tabIndexe.length; ++indice) {
                    String ligne = "" + iLigne;
                    if (!ligne.equals(tabIndexe[indice])) continue;
                    for (int i = 0; i < tempChamp.length; ++i) {
                        Field f = tempChamp[i];
                        String nomChamp = f.getName() + "_" + iLigne;
                        String valeur = this.getParamSansNull(nomChamp);
                        if (valeur == null || valeur.compareTo("") == 0) continue;
                        if (i == 1 && (valeur == null || valeur.compareTo("") == 0)) break;
                        if (f.getType().getName().compareToIgnoreCase("java.lang.String") == 0) {
                            CGenUtil.setValChamp(ex, f, valeur);
                        }
                        if (f.getType().getName().compareToIgnoreCase("java.sql.Date") == 0) {
                            CGenUtil.setValChamp(ex, f, Utilitaire.string_date("dd/MM/yyyy", valeur));
                        }
                        if (f.getType().getName().compareToIgnoreCase("double") == 0) {
                            CGenUtil.setValChamp(ex, f, new Double(Utilitaire.stringToDouble(valeur)));
                        }
                        if (f.getType().getName().compareToIgnoreCase("int") == 0) {
                            if (valeur == null || valeur.compareToIgnoreCase("") == 0) {
                                CGenUtil.setValChamp(ex, f, new Integer(0));
                            } else {
                                CGenUtil.setValChamp(ex, f, new Integer(valeur));
                            }
                        }
                        if (f.getType().getName().compareToIgnoreCase("float") == 0) {
                            CGenUtil.setValChamp(ex, f, new Float(Utilitaire.stringToFloat(valeur)));
                        }
                        if (i != 1) continue;
                        ++x;
                    }
                    liste[indice] = ex;
                }
            }
            ClassMAPTable[] ret = new ClassMAPTable[liste.length];
            for (int j = 0; j < liste.length; ++j) {
                ret[j] = liste[j];
            }
            return ret;
        }
        catch (NumberFormatException n) {
            throw new Exception("format de nombre invalide");
        }
        catch (Exception e) {
            throw e;
        }
    }

    public Page(ClassMAPTable p, HttpServletRequest r, int nombreligne, String[] tabIndexe) {
        this.setBase(p);
        this.setReq(r);
        this.setNombreLigne(nombreligne);
        this.setTabIndexe(tabIndexe);
    }

    public String getModalHtml(String nomDiv) throws Exception {
        String temp = "";
        temp = temp + "<div class=\"modal fade\" id=\"linkModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"linkModalLabel\" aria-hidden=\"true\">\r\n  <div style='width:60%;' class=\"modal-dialog modal-dialog-centered\" role=\"dialog\">\r\n    <div style=\"border-radius: 16px;padding:15px;\" class=\"modal-content\">\r\n      <div class=\"modal-body\">\r\n        <div id=\"" + nomDiv + "\">\r\n        </div>\r\n      </div>\r\n    </div>\r\n  </div>\r\n</div>";
        return temp;
    }

    public String getHtmlClavierVisuel() {
        StringBuilder sb = new StringBuilder();
        sb.append("    <div class=\"clavier-container vente-direct-box\" >\n");
        sb.append("        <div class=\"numbers col-md-10 nopadding\">\n");
        sb.append("            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"7\" >7</button>\n");
        sb.append("            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"8\" >8</button>\n");
        sb.append("            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"9\" >9</button>\n");
        sb.append("            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"4\" >4</button>\n");
        sb.append("            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"5\" >5</button>\n");
        sb.append("            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"6\" >6</button>\n");
        sb.append("            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"1\" >1</button>\n");
        sb.append("            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"2\" >2</button>\n");
        sb.append("            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"3\" >3</button>\n");
        sb.append("            <button class=\"btn-clavier btn col-md-12 h142pxRegular\" type=\"button\" data-key=\"0\" >0</button>\n");
        sb.append("        </div>\n");
        sb.append("        <div class=\"actions-container col-md-4 nopadding\">\n");
        sb.append("            <button class=\"btn-clavier btn col-md-12 h142pxRegular delete\" type=\"button\" id=\"deleteBtn2\" ><i class='material-symbols-rounded'>arrow_back_ios</i></button>\n");
        sb.append("            <button class=\"btn-clavier btn col-md-12 h520pxRegular\" id=\"btnClavierABC\" data-toggle=\"modal\" data-target=\"#clavier-abc\" type=\"button\" >ABC</button>\n");
        sb.append("        </div>\n");
        sb.append("    </div>\n");
        sb.append("    <div class=\"modal fade\" id=\"clavier-abc\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"clavier-abc\" aria-hidden=\"true\">\n");
        sb.append("        <div class=\"modal-dialog clavier\" role=\"document\">\n");
        sb.append("            <div class=\"modal-content\">\n");
        sb.append("                <div class=\"modal-body\">\n");
        sb.append("                    <div class=\"w-100 clavier-abc-container\">\n");
        sb.append("                        <div class=\"clavier-range\">\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"a\" >A</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"z\" >Z</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"e\" >E</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"r\" >R</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"t\" >T</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"y\" >Y</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"u\" >U</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"i\" >I</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"o\" >O</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"p\" >P</button>\n");
        sb.append("                        </div>\n");
        sb.append("                        <div class=\"clavier-range\">\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"q\" >Q</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"s\" >S</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"d\" >D</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"f\" >F</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"g\" >G</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"h\" >H</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"j\" >J</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"k\" >K</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"l\" >L</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"m\" >M</button>\n");
        sb.append("                        </div>\n");
        sb.append("                        <div class=\"clavier-range\">\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 btn-primary h235pxBold maj\" type=\"button\" id=\"shiftBtn\" ><i class='material-symbols-rounded'>keyboard_arrow_up</i> </button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"w\" >W</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"x\" >X</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"c\" >C</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"v\" >V</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"b\" >B</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular\" type=\"button\" data-key=\"n\" >N</button>\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 h142pxRegular delete\" type=\"button\" id=\"deleteBtn\" ><i class=\"material-symbols-rounded\">arrow_back_ios</i> </button>\n");
        sb.append("                        </div>\n");
        sb.append("                        <div class=\"clavier-range\">\n");
        sb.append("                            <button class=\"btn-clavier btn col-md-3 change-clavier\" data-dismiss=\"modal\" type=\"button\">123</button>\n");
        sb.append("                            <button class=\"btn-clavier btn w-100 space-touch\" type=\"button\" >Espace</button>\n");
        sb.append("                        </div>\n");
        sb.append("                    </div>\n");
        sb.append("                </div>\n");
        sb.append("            </div>\n");
        sb.append("        </div>\n");
        sb.append("    </div>");
        return sb.toString();
    }
}

