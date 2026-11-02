/*
 * Decompiled with CFR 0.152.
 */
package affichage;

import affichage.Champ;
import affichage.Formulaire;
import bean.CGenUtil;
import bean.ClassMAPTable;
import bean.LibelleAffichage;
import constante.ConstanteAffichage;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import utilitaire.Utilitaire;

public class Liste
extends Champ {
    private String colAffiche;
    private String colValeur;
    private ClassMAPTable singleton;
    private Object[] valeurBrute;
    private String[] colValeurBrute;
    private String apresW = "";

    public Liste() {
    }

    public Liste(String nom) {
        super(nom);
    }

    public Liste(ClassMAPTable[] f) {
        this.setBase(f);
    }

    public Liste(String nom, Object[] f, String defaut) {
        super(nom);
        this.setValeurBrute(f);
        this.setDefaultSelected(defaut);
    }

    public Liste(String nom, ClassMAPTable f) {
        super(nom);
        this.setSingleton(f);
    }

    public Liste(String nom, Object[] f) {
        super(nom);
        this.setValeurBrute(f);
    }

    public Liste(String nom, String lib, Object[] f) {
        super(nom);
        this.setLibelle(lib);
        this.setValeurBrute(f);
    }

    public Liste(String nom, ClassMAPTable f, String colAff) {
        super(nom);
        this.setSingleton(f);
        this.setColAffiche(colAff);
    }

    public Liste(String nom, ClassMAPTable f, String colAff, String colV) throws Exception {
        super(nom);
        this.setSingleton(f);
        this.setColAffiche(colAff);
        this.setColValeur(colV);
    }

    public Liste(String nom, ClassMAPTable f, String colAff, String colV, String awhere) throws Exception {
        super(nom);
        this.setSingleton(f);
        this.setColAffiche(colAff);
        this.setColValeur(colV);
        this.setApresW(awhere);
    }

    public Liste(String nom, Object[] f, String[] valeur) {
        super(nom);
        this.setValeurBrute(f);
        this.setColValeurBrute(valeur);
    }

    public void makeListeMois() {
        Object[] affiche = new String[]{"Janvier", "F\u00e9vrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "D\u00e9cembre"};
        String[] valeur = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        this.setValeurBrute(affiche);
        this.setColValeurBrute(valeur);
    }

    public void makeListeAnnee() {
        int anneeReference;
        String anneeChoisie = this.getValeur();
        if (anneeChoisie == null || anneeChoisie.compareToIgnoreCase("") == 0) {
            anneeChoisie = this.getDefaultSelected();
        }
        if (anneeChoisie != null && anneeChoisie.compareToIgnoreCase("") != 0) {
            try {
                anneeReference = Utilitaire.stringToInt(anneeChoisie);
            }
            catch (Exception e) {
                anneeReference = Utilitaire.getAneeEnCours();
            }
        } else {
            anneeReference = Utilitaire.getAneeEnCours();
        }
        int anneeDebut = anneeReference - 10;
        int anneeFin = anneeReference + 1;
        int nombreAnnees = anneeFin - anneeDebut + 1;
        Object[] affiche = new String[nombreAnnees];
        String[] valeur = new String[nombreAnnees];
        for (int i = 0; i < nombreAnnees; ++i) {
            int annee = anneeDebut + i;
            affiche[i] = String.valueOf(annee);
            valeur[i] = String.valueOf(annee);
        }
        this.setValeurBrute(affiche);
        this.setColValeurBrute(valeur);
        if (this.getDefaultSelected() == null || this.getDefaultSelected().compareToIgnoreCase("") == 0) {
            this.setDefaultSelected(String.valueOf(anneeReference));
        }
    }

    public void makeListeOuiNon() {
        Object[] affiche = new String[]{"NON", "OUI"};
        String[] valeur = new String[]{"0", "1"};
        this.setValeurBrute(affiche);
        this.setColValeurBrute(valeur);
    }

    public void makeListeOuiNon2() {
        Object[] affiche = new String[]{"NON", "OUI", "AVOIR"};
        String[] valeur = new String[]{"0", "1", "2"};
        this.setValeurBrute(affiche);
        this.setColValeurBrute(valeur);
    }

    public void makeListeChamp() {
        Object[] affiche = new String[]{"SOLDE", "CUMUL"};
        String[] valeur = new String[]{"SOLDE", "CUMUL"};
        this.setValeurBrute(affiche);
        this.setColValeurBrute(valeur);
    }

    public void makeListeDbCr() {
        Object[] affiche = new String[]{"DB", "CR"};
        String[] valeur = new String[]{"DB", "CR"};
        this.setValeurBrute(affiche);
        this.setColValeurBrute(valeur);
    }

    public Liste(String nom, ClassMAPTable[] f, String colAff, String colV) {
        super(nom);
        this.setBase(f);
        this.setColAffiche(colAff);
        this.setColValeur(colV);
    }

    public Liste(String nom, ClassMAPTable f, String colAff, String colV, Connection c) throws Exception {
        super(nom);
        this.setSingleton(f);
        this.setColAffiche(colAff);
        this.setColValeur(colV);
        this.findData(c);
    }

    public Liste(String nom, ClassMAPTable f, String colAff, String colV, boolean multiple) {
        super(nom);
        this.setSingleton(f);
        this.setColAffiche(colAff);
        this.setColValeur(colV);
        this.setEstMultiple(multiple);
    }

    @Override
    public void findData(Connection c) throws Exception {
        if (this.getSingleton() != null) {
            String aW = " order by " + this.getColAffiche() + " ASC";
            ClassMAPTable[] ret = (ClassMAPTable[])CGenUtil.rechercher(this.getSingleton(), null, null, c, this.getApresW() + aW);
            this.setBase(ret);
        }
    }

    public String makeHtmlValeur(String temp) throws Exception {
        for (int i = 0; i < this.getValeurBrute().length; ++i) {
            LibelleAffichage la;
            Field f;
            String champValeur = "";
            champValeur = this.getColValeurBrute() == null ? this.valeurBrute[i].toString() : this.getColValeurBrute()[i];
            String champAffiche = this.getValeurBrute()[i].toString();
            if (this.getValeurBrute()[i] instanceof bean.Champ && (f = ((bean.Champ)this.getValeurBrute()[i]).getNomClasse()) != null && (la = f.getAnnotation(LibelleAffichage.class)) != null && la.value() != null && !la.value().isEmpty()) {
                champAffiche = la.value();
            }
            String selected = "";
            if (champValeur.compareToIgnoreCase(this.getValeur()) == 0) {
                selected = "selected";
            } else if ((this.getValeur() == null || this.getValeur().compareToIgnoreCase("") == 0) && this.getDefaultSelected() != null && this.getDefaultSelected().compareToIgnoreCase("") != 0 && champValeur.compareToIgnoreCase(this.getDefaultSelected()) == 0) {
                selected = "selected";
            }
            temp = temp + "<option value='" + champValeur + "' " + selected + ">" + champAffiche + "</option>";
        }
        return temp;
    }

    @Override
    public void makeHtml() throws Exception {
        String temp = "";
        if (this.getEstRadio()) {
            temp = temp + "<style>\n  .radio-chip-group {\n    display: flex;\n    flex-wrap: wrap;\n    gap: 8px;\n  }\n  .radio-chip-item {\n    position: relative;\n    display: inline-flex;\n    cursor: pointer;\n  }\n  .radio-chip-item .hidden-radio {\n    position: absolute;\n    opacity: 0;\n    width: 0;\n    height: 0;\n    margin: 0;\n  }\n  .radio-chip-item .chip-content {\n    display: flex;\n    align-items: center;\n    gap: 8px;\n    height: 36px;\n    box-sizing: border-box;\n    padding: var(--Bases-4-space-2, 8px) var(--Bases-4-space-3, 12px);\n    border: 1px solid var(--Border, #f3f4f4);\n    border-radius: var(--radius-r2, 8px);\n    background: #ffffff;\n    transition: border-color 0.15s ease, padding 0.15s ease;\n    user-select: none;\n  }\n  .radio-chip-item .chip-text {\n    font-family: 'DM Sans', sans-serif;\n    font-weight: 400;\n    font-size: 14px;\n    line-height: normal;\n    color: var(--Text-text-primary, #1c1f21);\n    white-space: nowrap;\n  }\n  .radio-chip-item .chip-check {\n    display: none;\n    align-items: center;\n    justify-content: center;\n    width: 16px;\n    height: 16px;\n    font-size: 16px;\n    color: var(--Primaire-blue-main-color, #2b68d9);\n    flex-shrink: 0;\n  }\n  /* S\u00c3\u00a9lectionn\u00c3\u00a9 : bordure bleue 2px + ic\u00c3\u00b4ne check.\n     On retire 1px de padding pour compenser le +1px de bordure\n     et garder la m\u00c3\u00aame taille de chip (pas de \"saut\" visuel). */\n  .radio-chip-item .hidden-radio:checked ~ .chip-content {\n    border: 2px solid var(--Primaire-blue-main-color, #2b68d9);\n    padding: calc(var(--Bases-4-space-2, 8px) - 1px) calc(var(--Bases-4-space-3, 12px) - 1px);\n  }\n  .radio-chip-item .hidden-radio:checked ~ .chip-content .chip-check {\n    display: flex;\n  }\n  .radio-chip-item .hidden-radio:focus-visible ~ .chip-content {\n    outline: 2px solid var(--Primaire-blue-main-color, #2b68d9);\n    outline-offset: 2px;\n  }\n</style>\n";
            List<String> selected = this.parseSelectedValues(this.getValeur());
            boolean hasSelection = !selected.isEmpty();
            temp = temp + "<div class='radio-chip-group' id='" + this.getNom() + "_radio'>\n";
            boolean tousChecked = hasSelection ? selected.contains(ConstanteAffichage.asterisque) : this.getDefaultSelected() == null || ConstanteAffichage.asterisque.equalsIgnoreCase(this.getDefaultSelected());
            temp = temp + this.buildRadioChip(this.getNom(), ConstanteAffichage.asterisque, "Tous", tousChecked);
            if (this.getBase() != null && this.getBase().length > 0) {
                for (int i = 0; i < this.getBase().length; ++i) {
                    String champAffiche = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColAffiche()));
                    String champValeur = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColValeur()));
                    boolean checked = hasSelection ? selected.contains(champValeur) : this.getDefaultSelected() != null && champValeur.equalsIgnoreCase(this.getDefaultSelected());
                    temp = temp + this.buildRadioChip(this.getNom(), champValeur, champAffiche, checked);
                }
            } else if (this.getValeurBrute() != null && this.getValeurBrute().length > 0) {
                for (int i = 0; i < this.getValeurBrute().length; ++i) {
                    String champValeur;
                    String champAffiche = String.valueOf(this.getValeurBrute()[i]);
                    String string = champValeur = this.getColValeurBrute() != null && i < this.getColValeurBrute().length ? this.getColValeurBrute()[i] : champAffiche;
                    boolean checked = hasSelection ? selected.contains(champValeur) : this.getDefaultSelected() != null && champValeur.equalsIgnoreCase(this.getDefaultSelected());
                    temp = temp + this.buildRadioChip(this.getNom(), champValeur, champAffiche, checked);
                }
            }
            temp = temp + "</div>\n";
            this.setHtml(temp);
            return;
        }
        if (this.getEstMultiple()) {
            temp = temp + "<style>\n\n.filtre-nb.badge-count {\n  text-align: center;\n}\n  .dropdown.multiselect-checkbox {\n    margin: 0;\n    position: relative;\n    width: 100%;\n  }\n  .btn.btn-secondary.btn-small.dropdown-toggle {\n    padding: var(--Bases-4-space-2, 8px) var(--Bases-4-space-3, 12px);\n    height: 34px;\n    width: 100%;\n    display: flex;\n    align-items: center;\n    justify-content: space-between;\n  }\n  .dropdown-toggle .arrow-icon {\n    transition: transform 0.3s ease;\n  }\n  .multiselect-checkbox.open .dropdown-toggle .arrow-icon {\n    transform: rotate(180deg);\n  }\n  /* Menu flottant avec position FIXED pour sortir de la modale */\n  .custom-dropdown-menu {\n    display: none;\n    position: fixed; /* <-- LE SECRET EST L\u00c3\u0080 */\n    background: #ffffff;\n    border: 1px solid var(--Border, #E5E7EB);\n    border-radius: 12px;\n    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);\n    z-index: 999999; /* Toujours au premier plan */\n    overflow: hidden;\n  }\n  .custom-dropdown-menu.show {\n    display: block;\n    animation: fadeInMenu 0.15s ease-out;\n  }\n  @keyframes fadeInMenu {\n    from { opacity: 0; transform: translateY(-5px); }\n    to { opacity: 1; transform: translateY(0); }\n  }\n  .checkbox-list-wrapper {\n    max-height: 220px;\n    overflow-y: auto;\n    display: flex;\n    flex-direction: column;\n    gap: 12px;\n    padding: 16px;\n  }\n  .custom-dropdown-item {\n    display: flex;\n    align-items: center;\n    gap: 12px;\n    cursor: pointer;\n    margin: 0;\n  }\n  .hidden-cb {\n    position: absolute;\n    opacity: 0;\n    cursor: pointer;\n    height: 0;\n    width: 0;\n  }\n  .custom-cb {\n    width: 22px;\n    height: 22px;\n    border-radius: 6px;\n    border: 1.5px solid var(--Onyx-200, #9b9ea0);\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    transition: all 0.2s ease;\n    background: white;\n    flex-shrink: 0;\n  }\n  .cb-text {\n    font-size: 14px;\n    color: var(--Onyx-700, #1c1f21);\n    font-weight: 500;\n    user-select: none;\n  }\n  .custom-cb .check-icon {\n    font-size: 16px;\n    color: white;\n    opacity: 0;\n    transform: scale(0.5);\n    transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);\n  }\n  .hidden-cb:checked ~ .custom-cb {\n    background-color: var(--Primaire-blue-main-color, #2b68d9);\n    border-color: var(--Primaire-blue-main-color, #2b68d9);\n  }\n  .hidden-cb:checked ~ .custom-cb .check-icon {\n    opacity: 1;\n    transform: scale(1);\n  }\n  .checkbox-list-wrapper::-webkit-scrollbar { width: 6px; }\n  .checkbox-list-wrapper::-webkit-scrollbar-thumb { background-color: var(--Onyx-100, #c3c5c6); border-radius: 4px; }\n</style>\n";
            String idBloc = this.getNom() + "_ms";
            List<String> selected = this.parseSelectedValues(this.getValeur());
            temp = temp + "<div class='dropdown multiselect-checkbox' id='" + idBloc + "'>\n";
            temp = temp + "  <button type='button' class='btn btn-secondary btn-small dropdown-toggle' aria-expanded='false'>\n";
            temp = temp + "    <span class='dropdown-label'>Choisir</span><i class='material-symbols-rounded arrow-icon'>expand_more</i>\n";
            temp = temp + "  </button>\n";
            temp = temp + "  <div class='custom-dropdown-menu'>\n";
            temp = temp + "    <div class='checkbox-list-wrapper'>\n";
            temp = temp + "      <label class='custom-dropdown-item'>\n";
            temp = temp + "        <input type='checkbox' name='" + this.getNom() + "' value='%' class='hidden-cb' " + (selected.contains("%") ? "checked" : "") + ">\n";
            temp = temp + "        <span class='custom-cb'><i class='material-symbols-rounded check-icon'>check</i></span>\n";
            temp = temp + "        <span class='cb-text'>Tous</span>\n";
            temp = temp + "      </label>\n";
            if (this.getBase() != null && this.getBase().length > 0) {
                for (int i = 0; i < this.getBase().length; ++i) {
                    String champAffiche = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColAffiche()));
                    String champValeur = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColValeur()));
                    boolean checked = selected.contains(champValeur);
                    temp = temp + "      <label class='custom-dropdown-item'>\n";
                    temp = temp + "        <input type='checkbox' name='" + this.getNom() + "' value='" + Liste.escapeHtml(champValeur) + "' class='hidden-cb' " + (checked ? "checked" : "") + ">\n";
                    temp = temp + "        <span class='custom-cb'><i class='material-symbols-rounded check-icon'>check</i></span>\n";
                    temp = temp + "        <span class='cb-text'>" + Liste.escapeHtml(champAffiche) + "</span>\n";
                    temp = temp + "      </label>\n";
                }
            }
            temp = temp + "    </div>\n";
            temp = temp + "    <div class='dropdown-action-row' style='display:flex;align-items:center;justify-content:space-between;padding:12px 16px;border-top:1px solid var(--Onyx-050, #ebebec);'>\n";
            temp = temp + "      <span class='reset-link clear-multi' style='color:var(--Primaire-blue-main-color, #2b68d9);font-size:14px;font-weight:600;cursor:pointer;user-select:none;'>R&eacute;initialiser</span>\n";
            temp = temp + "      <span class='filtre-nb badge-count'>0</span>\n";
            temp = temp + "    </div>\n";
            temp = temp + "  </div>\n";
            temp = temp + "</div>\n";
            temp = temp + "<script>\n(function () {\n    const rootId = '" + idBloc + "';\n    const root = document.getElementById(rootId);\n    if (!root) return;\n\n    // Nettoyage en cas de rechargement AJAX (\u00c3\u00a9vite les doublons de menus)\n    const menuId = 'menu_' + rootId;\n    const oldMenu = document.getElementById(menuId);\n    if (oldMenu && oldMenu.parentNode === document.body) {\n        oldMenu.remove();\n    }\n\n    const btn = root.querySelector('.dropdown-toggle');\n    const label = root.querySelector('.dropdown-label');\n    const menu = root.querySelector('.custom-dropdown-menu');\n    menu.id = menuId;\n\n    // D\u00c3\u0089PLACEMENT STRAT\u00c3\u0089GIQUE : On sort le menu de la modale vers le body\n    document.body.appendChild(menu);\n\n    // Cr\u00c3\u00a9ation d'un conteneur dans le formulaire pour envoyer les donn\u00c3\u00a9es au serveur\n    let hiddenContainer = document.createElement('div');\n    root.appendChild(hiddenContainer);\n\n    // On cherche maintenant les \u00c3\u00a9l\u00c3\u00a9ments DANS le menu d\u00c3\u00a9plac\u00c3\u00a9\n    const boxes = Array.from(menu.querySelectorAll(\"input[type='checkbox']\"));\n    const clearBtn = menu.querySelector('.clear-multi');\n    const badge = menu.querySelector('.badge-count');\n\n    function updateUI() {\n        const selected = boxes.filter(cb => cb.checked);\n        const count = selected.length;\n\n        // 1. Synchronisation avec le formulaire (cr\u00c3\u00a9ation d'inputs invisibles)\n        hiddenContainer.innerHTML = '';\n        selected.forEach(cb => {\n            const input = document.createElement('input');\n            input.type = 'hidden';\n            input.name = cb.name;\n            input.value = cb.value;\n            hiddenContainer.appendChild(input);\n        });\n\n        // 2. Mise \u00c3\u00a0 jour de l'affichage\n        if (count === 0) {\n            label.textContent = 'Choisir';\n        } else if (count === 1) {\n            label.textContent = selected[0].closest('label').querySelector('.cb-text').textContent.trim();\n        } else {\n            label.innerHTML = count + ' s&eacute;lectionn&eacute;s';\n        }\n        if(badge) badge.textContent = count;\n    }\n\n    function positionMenu() {\n        const rect = btn.getBoundingClientRect();\n        menu.style.width = rect.width + 'px';\n        menu.style.left = rect.left + 'px';\n        \n        // S'il n'y a pas assez de place en bas, on l'ouvre vers le haut !\n        const spaceBelow = window.innerHeight - rect.bottom;\n        const menuHeight = menu.offsetHeight || 290;\n        if (spaceBelow < menuHeight && rect.top > menuHeight) {\n            menu.style.top = 'auto';\n            menu.style.bottom = (window.innerHeight - rect.top + 4) + 'px';\n        } else {\n            menu.style.bottom = 'auto';\n            menu.style.top = (rect.bottom + 4) + 'px';\n        }\n    }\n\n    btn.addEventListener('click', (e) => {\n        e.stopPropagation();\n        if(!menu.classList.contains('show')) {\n             menu.style.display = 'block'; // Necessaire pour mesurer la hauteur r\u00c3\u00a9elle avant affichage final\n             positionMenu();\n             menu.classList.add('show');\n             root.classList.add('open');\n        } else {\n             menu.classList.remove('show');\n             root.classList.remove('open');\n             setTimeout(() => menu.style.display = 'none', 150);\n        }\n    });\n\n    // Fermer le menu en cas de d\u00c3\u00a9filement (scroll) dans la modale ou la page\n    document.addEventListener('scroll', (e) => {\n        if (menu.contains(e.target)) return; // Autoriser le scroll \u00c3\u00a0 l'int\u00c3\u00a9rieur du menu lui-m\u00c3\u00aame\n        if (menu.classList.contains('show')) {\n            menu.classList.remove('show');\n            root.classList.remove('open');\n            setTimeout(() => menu.style.display = 'none', 150);\n        }\n    }, true);\n\n    boxes.forEach(cb => {\n        cb.addEventListener('change', updateUI);\n    });\n\n    if (clearBtn) {\n        clearBtn.addEventListener('click', (e) => {\n            e.stopPropagation();\n            boxes.forEach(cb => cb.checked = false);\n            updateUI();\n        });\n    }\n\n    document.addEventListener('click', (e) => {\n        if (!root.contains(e.target) && !menu.contains(e.target)) {\n            menu.classList.remove('show');\n            root.classList.remove('open');\n            setTimeout(() => menu.style.display = 'none', 150);\n        }\n    });\n\n    updateUI();\n})();\n</script>\n";
            this.setHtml(temp);
            return;
        }
        temp = "<select name='" + this.getNom() + "' class='" + this.getCss() + "' id='" + this.getNom() + "' " + this.getAutre() + ">";
        if (this.valeurBrute != null && this.valeurBrute.length > 0) {
            temp = this.makeHtmlValeur(temp);
        }
        if (this.isEstVide()) {
            temp = temp + "<option value='' multi-lang>(Vide)</option>";
        }
        if (this.getBase() != null && this.getBase().length > 0) {
            temp = temp + "<option value='" + ConstanteAffichage.asterisque + "'>Tous</option>";
            for (int i = 0; i < this.getBase().length; ++i) {
                String champValeur = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColValeur()));
                String champAffiche = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColAffiche()));
                String selected = "";
                if ((this.getValeur() == null || this.getValeur().isEmpty()) && this.getDefaultSelected() != null) {
                    if (champValeur.equalsIgnoreCase(this.getDefaultSelected())) {
                        selected = "selected";
                    }
                } else if (champValeur.equalsIgnoreCase(this.getValeur())) {
                    selected = "selected";
                }
                temp = temp + "<option value='" + champValeur + "' " + selected + ">" + champAffiche + "</option>";
            }
        }
        temp = temp + "</select>";
        this.setHtml(temp);
    }

    private String buildRadioChip(String nom, String valeurBrute, String labelBrut, boolean checked) {
        String valeur = Liste.escapeHtml(valeurBrute);
        String label = Liste.escapeHtml(labelBrut);
        StringBuilder sb = new StringBuilder();
        sb.append("  <label class='radio-chip-item'>\n");
        sb.append("    <input type='radio' name='").append(nom).append("' value='").append(valeur).append("' class='hidden-radio'").append(checked ? " checked" : "").append(">\n");
        sb.append("    <span class='chip-content'>\n");
        sb.append("      <span class='chip-text'>").append(label).append("</span>\n");
        sb.append("      <span class='chip-check'><i class='material-symbols-rounded'>check_circle</i></span>\n");
        sb.append("    </span>\n");
        sb.append("  </label>\n");
        return sb.toString();
    }

    @Override
    public void makeHtmlInsert() throws Exception {
        String temp = "";
        if (this.getEstRadio()) {
            temp = temp + "<style>\n  .radio-chip-group {\n    display: flex;\n    flex-wrap: wrap;\n    gap: 8px;\n  }\n  .radio-chip-item {\n    position: relative;\n    display: inline-flex;\n    cursor: pointer;\n  }\n  .radio-chip-item .hidden-radio {\n    position: absolute;\n    opacity: 0;\n    width: 0;\n    height: 0;\n    margin: 0;\n  }\n  .radio-chip-item .chip-content {\n    display: flex;\n    align-items: center;\n    gap: 8px;\n    height: 36px;\n    box-sizing: border-box;\n    padding: var(--Bases-4-space-2, 8px) var(--Bases-4-space-3, 12px);\n    border: 1px solid var(--Border, #f3f4f4);\n    border-radius: var(--radius-r2, 8px);\n    background: #ffffff;\n    transition: border-color 0.15s ease, padding 0.15s ease;\n    user-select: none;\n  }\n  .radio-chip-item .chip-text {\n    font-family: 'DM Sans', sans-serif;\n    font-weight: 400;\n    font-size: 14px;\n    line-height: normal;\n    color: var(--Text-text-primary, #1c1f21);\n    white-space: nowrap;\n  }\n  .radio-chip-item .chip-check {\n    display: none;\n    align-items: center;\n    justify-content: center;\n    width: 16px;\n    height: 16px;\n    font-size: 16px;\n    color: var(--Primaire-blue-main-color, #2b68d9);\n    flex-shrink: 0;\n  }\n  .radio-chip-item .hidden-radio:checked ~ .chip-content {\n    border: 2px solid var(--Primaire-blue-main-color, #2b68d9);\n    padding: calc(var(--Bases-4-space-2, 8px) - 1px) calc(var(--Bases-4-space-3, 12px) - 1px);\n  }\n  .radio-chip-item .hidden-radio:checked ~ .chip-content .chip-check {\n    display: flex;\n  }\n  .radio-chip-item .hidden-radio:focus-visible ~ .chip-content {\n    outline: 2px solid var(--Primaire-blue-main-color, #2b68d9);\n    outline-offset: 2px;\n  }\n</style>\n";
            List<String> selected = this.parseSelectedValues(this.getValeur());
            boolean hasSelection = !selected.isEmpty();
            temp = temp + "<div class='radio-chip-group' id='" + this.getNom() + "_radio'>\n";
            if (this.getBase() != null && this.getBase().length > 0) {
                for (int i = 0; i < this.getBase().length; ++i) {
                    String champAffiche = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColAffiche()));
                    String champValeur = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColValeur()));
                    boolean checked = hasSelection ? selected.contains(champValeur) : this.getDefaultSelected() != null && champValeur.equalsIgnoreCase(this.getDefaultSelected());
                    temp = temp + this.buildRadioChip(this.getNom(), champValeur, champAffiche, checked);
                }
            } else if (this.getValeurBrute() != null && this.getValeurBrute().length > 0) {
                for (int i = 0; i < this.getValeurBrute().length; ++i) {
                    String champValeur;
                    String champAffiche = String.valueOf(this.getValeurBrute()[i]);
                    String string = champValeur = this.getColValeurBrute() != null && i < this.getColValeurBrute().length ? this.getColValeurBrute()[i] : champAffiche;
                    boolean checked = hasSelection ? selected.contains(champValeur) : this.getDefaultSelected() != null && champValeur.equalsIgnoreCase(this.getDefaultSelected());
                    temp = temp + this.buildRadioChip(this.getNom(), champValeur, champAffiche, checked);
                }
            }
            temp = temp + "</div>\n";
            this.setHtmlInsert(temp);
            return;
        }
        if (this.getEstMultiple()) {
            temp = temp + "<style>\n\n.filtre-nb.badge-count {\n  text-align: center;\n}\n  .dropdown.multiselect-checkbox {\n    margin: 0;\n    position: relative;\n    width: 100%;\n  }\n  .btn.btn-secondary.btn-small.dropdown-toggle {\n    padding: var(--Bases-4-space-2, 8px) var(--Bases-4-space-3, 12px);\n    height: 34px;\n    width: 100%;\n    display: flex;\n    align-items: center;\n    justify-content: space-between;\n  }\n  .dropdown-toggle .arrow-icon {\n    transition: transform 0.3s ease;\n  }\n  .multiselect-checkbox.open .dropdown-toggle .arrow-icon {\n    transform: rotate(180deg);\n  }\n  /* Menu flottant avec position FIXED pour sortir de la modale */\n  .custom-dropdown-menu {\n    display: none;\n    position: fixed; /* <-- LE SECRET EST L\u00c3\u0080 */\n    background: #ffffff;\n    border: 1px solid var(--Border, #E5E7EB);\n    border-radius: 12px;\n    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);\n    z-index: 999999; /* Toujours au premier plan */\n    overflow: hidden;\n  }\n  .custom-dropdown-menu.show {\n    display: block;\n    animation: fadeInMenu 0.15s ease-out;\n  }\n  @keyframes fadeInMenu {\n    from { opacity: 0; transform: translateY(-5px); }\n    to { opacity: 1; transform: translateY(0); }\n  }\n  .checkbox-list-wrapper {\n    max-height: 220px;\n    overflow-y: auto;\n    display: flex;\n    flex-direction: column;\n    gap: 12px;\n    padding: 16px;\n  }\n  .custom-dropdown-item {\n    display: flex;\n    align-items: center;\n    gap: 12px;\n    cursor: pointer;\n    margin: 0;\n  }\n  .hidden-cb {\n    position: absolute;\n    opacity: 0;\n    cursor: pointer;\n    height: 0;\n    width: 0;\n  }\n  .custom-cb {\n    width: 22px;\n    height: 22px;\n    border-radius: 6px;\n    border: 1.5px solid var(--Onyx-200, #9b9ea0);\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    transition: all 0.2s ease;\n    background: white;\n    flex-shrink: 0;\n  }\n  .cb-text {\n    font-size: 14px;\n    color: var(--Onyx-700, #1c1f21);\n    font-weight: 500;\n    user-select: none;\n  }\n  .custom-cb .check-icon {\n    font-size: 16px;\n    color: white;\n    opacity: 0;\n    transform: scale(0.5);\n    transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);\n  }\n  .hidden-cb:checked ~ .custom-cb {\n    background-color: var(--Primaire-blue-main-color, #2b68d9);\n    border-color: var(--Primaire-blue-main-color, #2b68d9);\n  }\n  .hidden-cb:checked ~ .custom-cb .check-icon {\n    opacity: 1;\n    transform: scale(1);\n  }\n  .checkbox-list-wrapper::-webkit-scrollbar { width: 6px; }\n  .checkbox-list-wrapper::-webkit-scrollbar-thumb { background-color: var(--Onyx-100, #c3c5c6); border-radius: 4px; }\n</style>\n";
            String idBloc = this.getNom() + "_ms";
            List<String> selected = this.parseSelectedValues(this.getValeur());
            temp = temp + "<div class='dropdown multiselect-checkbox' id='" + idBloc + "'>\n";
            temp = temp + "  <button type='button' class='btn btn-secondary btn-small dropdown-toggle' aria-expanded='false'>\n";
            temp = temp + "    <span class='dropdown-label'>Choisir</span><i class='material-symbols-rounded arrow-icon'>expand_more</i>\n";
            temp = temp + "  </button>\n";
            temp = temp + "  <div class='custom-dropdown-menu'>\n";
            temp = temp + "    <div class='checkbox-list-wrapper'>\n";
            if (this.isEstVide()) {
                temp = temp + "      <label class='custom-dropdown-item'>\n";
                temp = temp + "        <input type='checkbox' name='" + this.getNom() + "' value='' class='hidden-cb' " + (selected.contains("") ? "checked" : "") + ">\n";
                temp = temp + "        <span class='custom-cb'><i class='material-symbols-rounded check-icon'>check</i></span>\n";
                temp = temp + "        <span class='cb-text'>(Vide)</span>\n";
                temp = temp + "      </label>\n";
            }
            if (this.getBase() != null && this.getBase().length > 0) {
                for (int i = 0; i < this.getBase().length; ++i) {
                    String champAffiche = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColAffiche()));
                    String champValeur = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColValeur()));
                    boolean checked = selected.contains(champValeur);
                    temp = temp + "      <label class='custom-dropdown-item'>\n";
                    temp = temp + "        <input type='checkbox' name='" + this.getNom() + "' value='" + Liste.escapeHtml(champValeur) + "' class='hidden-cb' " + (checked ? "checked" : "") + ">\n";
                    temp = temp + "        <span class='custom-cb'><i class='material-symbols-rounded check-icon'>check</i></span>\n";
                    temp = temp + "        <span class='cb-text'>" + Liste.escapeHtml(champAffiche) + "</span>\n";
                    temp = temp + "      </label>\n";
                }
            }
            temp = temp + "    </div>\n";
            temp = temp + "    <div class='dropdown-action-row' style='display:flex;align-items:center;justify-content:space-between;padding:12px 16px;border-top:1px solid var(--Onyx-050, #ebebec);'>\n";
            temp = temp + "      <span class='reset-link clear-multi' style='color:var(--Primaire-blue-main-color, #2b68d9);font-size:14px;font-weight:600;cursor:pointer;user-select:none;'>R&eacute;initialiser</span>\n";
            temp = temp + "      <span class='filtre-nb badge-count'>0</span>\n";
            temp = temp + "    </div>\n";
            temp = temp + "  </div>\n";
            temp = temp + "</div>\n";
            temp = temp + "<script>\n(function () {\n    const rootId = '" + idBloc + "';\n    const root = document.getElementById(rootId);\n    if (!root) return;\n\n    // Nettoyage en cas de rechargement AJAX (\u00c3\u00a9vite les doublons de menus)\n    const menuId = 'menu_' + rootId;\n    const oldMenu = document.getElementById(menuId);\n    if (oldMenu && oldMenu.parentNode === document.body) {\n        oldMenu.remove();\n    }\n\n    const btn = root.querySelector('.dropdown-toggle');\n    const label = root.querySelector('.dropdown-label');\n    const menu = root.querySelector('.custom-dropdown-menu');\n    menu.id = menuId;\n\n    // D\u00c3\u0089PLACEMENT STRAT\u00c3\u0089GIQUE : On sort le menu de la modale vers le body\n    document.body.appendChild(menu);\n\n    // Cr\u00c3\u00a9ation d'un conteneur dans le formulaire pour envoyer les donn\u00c3\u00a9es au serveur\n    let hiddenContainer = document.createElement('div');\n    root.appendChild(hiddenContainer);\n\n    // On cherche maintenant les \u00c3\u00a9l\u00c3\u00a9ments DANS le menu d\u00c3\u00a9plac\u00c3\u00a9\n    const boxes = Array.from(menu.querySelectorAll(\"input[type='checkbox']\"));\n    const clearBtn = menu.querySelector('.clear-multi');\n    const badge = menu.querySelector('.badge-count');\n\n    function updateUI() {\n        const selected = boxes.filter(cb => cb.checked);\n        const count = selected.length;\n\n        // 1. Synchronisation avec le formulaire (cr\u00c3\u00a9ation d'inputs invisibles)\n        hiddenContainer.innerHTML = '';\n        selected.forEach(cb => {\n            const input = document.createElement('input');\n            input.type = 'hidden';\n            input.name = cb.name;\n            input.value = cb.value;\n            hiddenContainer.appendChild(input);\n        });\n\n        // 2. Mise \u00c3\u00a0 jour de l'affichage\n        if (count === 0) {\n            label.textContent = 'Choisir';\n        } else if (count === 1) {\n            label.textContent = selected[0].closest('label').querySelector('.cb-text').textContent.trim();\n        } else {\n            label.innerHTML = count + ' s&eacute;lectionn&eacute;s';\n        }\n        if(badge) badge.textContent = count;\n    }\n\n    function positionMenu() {\n        const rect = btn.getBoundingClientRect();\n        menu.style.width = rect.width + 'px';\n        menu.style.left = rect.left + 'px';\n        \n        // S'il n'y a pas assez de place en bas, on l'ouvre vers le haut !\n        const spaceBelow = window.innerHeight - rect.bottom;\n        const menuHeight = menu.offsetHeight || 290;\n        if (spaceBelow < menuHeight && rect.top > menuHeight) {\n            menu.style.top = 'auto';\n            menu.style.bottom = (window.innerHeight - rect.top + 4) + 'px';\n        } else {\n            menu.style.bottom = 'auto';\n            menu.style.top = (rect.bottom + 4) + 'px';\n        }\n    }\n\n    btn.addEventListener('click', (e) => {\n        e.stopPropagation();\n        if(!menu.classList.contains('show')) {\n             menu.style.display = 'block'; // Necessaire pour mesurer la hauteur r\u00c3\u00a9elle avant affichage final\n             positionMenu();\n             menu.classList.add('show');\n             root.classList.add('open');\n        } else {\n             menu.classList.remove('show');\n             root.classList.remove('open');\n             setTimeout(() => menu.style.display = 'none', 150);\n        }\n    });\n\n    // Fermer le menu en cas de d\u00c3\u00a9filement (scroll) dans la modale ou la page\n    document.addEventListener('scroll', (e) => {\n        if (menu.contains(e.target)) return; // Autoriser le scroll \u00c3\u00a0 l'int\u00c3\u00a9rieur du menu lui-m\u00c3\u00aame\n        if (menu.classList.contains('show')) {\n            menu.classList.remove('show');\n            root.classList.remove('open');\n            setTimeout(() => menu.style.display = 'none', 150);\n        }\n    }, true);\n\n    boxes.forEach(cb => {\n        cb.addEventListener('change', updateUI);\n    });\n\n    if (clearBtn) {\n        clearBtn.addEventListener('click', (e) => {\n            e.stopPropagation();\n            boxes.forEach(cb => cb.checked = false);\n            updateUI();\n        });\n    }\n\n    document.addEventListener('click', (e) => {\n        if (!root.contains(e.target) && !menu.contains(e.target)) {\n            menu.classList.remove('show');\n            root.classList.remove('open');\n            setTimeout(() => menu.style.display = 'none', 150);\n        }\n    });\n\n    updateUI();\n})();\n</script>\n";
            this.setHtmlInsert(temp);
            return;
        }
        temp = "<select name=" + this.getNom() + " class=" + this.getCss() + " id=" + this.getNom() + " " + this.getAutre() + " >";
        if (this.valeurBrute != null && this.valeurBrute.length > 0) {
            temp = this.makeHtmlValeur(temp);
        }
        if (this.isEstVide()) {
            temp = temp + "<option value='' multi-lang>(Vide)</option>";
        }
        if (this.getBase() != null && this.getBase().length > 0) {
            for (int i = 0; i < this.getBase().length; ++i) {
                String champValeur = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColValeur()));
                String champAffiche = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColAffiche()));
                String selected = "";
                if ((this.getValeur() == null || this.getValeur().compareToIgnoreCase("") == 0) && this.getDefaultSelected() != null) {
                    if (champValeur.compareToIgnoreCase(this.getDefaultSelected()) == 0) {
                        selected = "selected";
                    }
                } else if (champValeur.compareToIgnoreCase(this.getValeur()) == 0) {
                    selected = "selected";
                }
                temp = temp + "<option value='" + champValeur + "' " + selected + " multi-lang>" + champAffiche + "</option>";
            }
        }
        temp = temp + "</select>";
        this.setHtmlInsert(temp);
    }

    public void ajouterValeur(String[] valeur, String[] affiche) {
        this.setColValeurBrute(valeur);
        this.setValeurBrute(affiche);
    }

    public void setColAffiche(String colAffiche) {
        this.colAffiche = colAffiche;
    }

    public String getColAffiche() throws Exception {
        if (this.colAffiche == null) {
            if (this.getSingleton() != null) {
                return this.getSingleton().getFieldList()[1].getName();
            }
            return this.getColValeur();
        }
        return this.colAffiche;
    }

    public void setColValeur(String colValeur) {
        this.colValeur = colValeur;
    }

    public String getColValeur() {
        if (this.colValeur == null && this.getBase() != null && this.getBase().length > 0) {
            return this.getBase()[0].getAttributIDName();
        }
        return this.colValeur;
    }

    public void setSingleton(ClassMAPTable singleton) {
        this.singleton = singleton;
    }

    public ClassMAPTable getSingleton() {
        return this.singleton;
    }

    public void setValeurBrute(Object[] valeurBrute) {
        this.valeurBrute = valeurBrute;
    }

    public Object[] getValeurBrute() {
        return this.valeurBrute;
    }

    public void setColValeurBrute(String[] colValeurBrute) {
        this.colValeurBrute = colValeurBrute;
    }

    public String[] getColValeurBrute() {
        return this.colValeurBrute;
    }

    public void setDefaultSelected(String defaultSelected) {
        this.setDefaut(defaultSelected);
    }

    public String getDefaultSelected() {
        return this.getDefaut();
    }

    public void setApresW(String apresW) {
        this.apresW = apresW;
    }

    private static String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }

    private List<String> parseSelectedValues(String raw) {
        String[] parts;
        ArrayList<String> res = new ArrayList<String>();
        if (raw == null || raw.trim().isEmpty()) {
            return res;
        }
        String cleaned = raw.replace("'", "");
        for (String p : parts = cleaned.split("[,;]")) {
            if (p == null || p.trim().isEmpty()) continue;
            res.add(p.trim());
        }
        return res;
    }

    public String getApresW() {
        return this.apresW;
    }

    public void makeListeStatut() {
        Object[] affiche = new String[]{"ENC", "EFA", "ECD", "STG"};
        String[] valeur = new String[]{"ENC", "EFA", "ECD", "STG"};
        this.setValeurBrute(affiche);
        this.setColValeurBrute(valeur);
    }

    public void makeListeTypeInterneExterne() {
        Object[] affiche = new String[]{"Interne", "Externe"};
        String[] valeur = new String[]{"interne", "externe"};
        this.setValeurBrute(affiche);
        this.setColValeurBrute(valeur);
    }

    public void makeListeString(String[] aaffiche, String[] value) {
        Object[] affiche = aaffiche;
        String[] valeur = value;
        this.setValeurBrute(affiche);
        this.setColValeurBrute(valeur);
    }

    public void makeListeTypeDeplacementArrive() {
        Object[] affiche = new String[]{"Final", "Intermediaire", "Retour"};
        String[] valeur = new String[]{"Final", "Intermediaire", "Retour"};
        this.setValeurBrute(affiche);
        this.setColValeurBrute(valeur);
    }

    public void makeListeTypeRoute() {
        Object[] affiche = new String[]{"Ville", "Route"};
        String[] valeur = new String[]{"0", "1"};
        this.setValeurBrute(affiche);
        this.setColValeurBrute(valeur);
    }

    public void makeListeEnquete() {
        Object[] affiche = new String[]{"Pris en charge", "Rejeter"};
        String[] valeur = new String[]{"0", "1"};
        this.setValeurBrute(affiche);
        this.setColValeurBrute(valeur);
    }

    public void makeListeFromArray(String afficher, String val, ClassMAPTable[] array) throws Exception {
        try {
            Method valeurAffiche = array[0].getClass().getMethod("get" + Utilitaire.convertDebutMajuscule(afficher), null);
            Method valeurVal = array[0].getClass().getMethod("get" + Utilitaire.convertDebutMajuscule(val), null);
            Object[] affiche = new String[array.length];
            String[] valeur = new String[array.length];
            for (int i = 0; i < array.length; ++i) {
                affiche[i] = (String)valeurAffiche.invoke(array[i], null);
                valeur[i] = (String)valeurVal.invoke(array[i], null);
            }
            this.setValeurBrute(affiche);
            this.setColValeurBrute(valeur);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("method get" + Utilitaire.convertDebutMajuscule(afficher) + " get" + Utilitaire.convertDebutMajuscule(val) + " introuvable");
        }
    }

    public void setDeroulanteDependante(Liste autre, String nomColonneFiltreAutre, String event) throws Exception {
        String evenement = "onblur";
        if (event != null && event.compareToIgnoreCase("") != 0) {
            evenement = event;
        }
        this.setAutre(" " + this.getAutre() + " " + evenement + "=\"dependante(this.value,'" + autre.getNom() + "','" + autre.getSingleton().getNomTable() + "','" + autre.getSingleton().getClass().getName() + "','" + nomColonneFiltreAutre + "','" + autre.getColValeur() + "','" + autre.getColAffiche() + "')\"");
    }

    public void setDeroulanteDependante(Formulaire formu, String autre, String nomColonneFiltreAutre, String event) throws Exception {
        Liste c = (Liste)formu.getChamp(autre);
        this.setDeroulanteDependante(c, nomColonneFiltreAutre, event);
    }

    public void setDeroulanteDependanteAvecTiret(Liste autre, String nomColonneFiltreAutre, String event, boolean esttiret) throws Exception {
        String evenement = "onblur";
        if (event != null && event.compareToIgnoreCase("") != 0) {
            evenement = event;
        }
        this.setAutre(this.getAutre() + " " + evenement + "=\"dependantewithtiret(this.value,'" + autre.getNom() + "','" + autre.getSingleton().getNomTable() + "','" + autre.getSingleton().getClass().getName() + "','" + nomColonneFiltreAutre + "','" + autre.getColValeur() + "','" + autre.getColAffiche() + "','" + esttiret + "')\"");
    }

    @Override
    public void makeHtmlInsertTableau(int indice) throws Exception {
        String temp = "";
        temp = "<select name=" + this.getNom() + " class=" + this.getCss() + " id=" + this.getNom() + " " + this.getAutre() + " >";
        if (this.valeurBrute != null && this.valeurBrute.length > 0) {
            temp = this.makeHtmlValeur(temp);
        }
        if (this.getBase() != null && this.getBase().length > 0) {
            for (int i = 0; i < this.getBase().length; ++i) {
                String champValeur = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColValeur()));
                String champAffiche = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColAffiche()));
                String selected = "";
                if ((this.getValeur() == null || this.getValeur().compareToIgnoreCase("") == 0) && this.getDefaultSelected() != null) {
                    if (champValeur.compareToIgnoreCase(this.getDefaultSelected()) == 0) {
                        selected = "selected";
                    }
                } else if (champValeur.compareToIgnoreCase(this.getValeur()) == 0) {
                    selected = "selected";
                }
                temp = temp + "<option value='" + champValeur + "' " + selected + ">" + champAffiche + "</option>";
            }
        }
        temp = temp + "</select>";
        this.setHtmlTableauInsert(temp);
    }

    public String makeHtmlTabInsert(int indice) throws Exception {
        String temp = "";
        temp = "<select name=" + this.getNom() + "_" + indice + " class=" + this.getCss() + " id=" + this.getNom() + "_" + indice + " " + this.getAutre() + " >";
        if (this.valeurBrute != null && this.valeurBrute.length > 0) {
            temp = this.makeHtmlValeur(temp);
        }
        if (this.getBase() != null && this.getBase().length > 0) {
            for (int i = 0; i < this.getBase().length; ++i) {
                String champValeur = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColValeur()));
                String champAffiche = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColAffiche()));
                String selected = "";
                if ((this.getValeur() == null || this.getValeur().compareToIgnoreCase("") == 0) && this.getDefaultSelected() != null) {
                    if (champValeur.compareToIgnoreCase(this.getDefaultSelected()) == 0) {
                        selected = "selected";
                    }
                } else if (champValeur.compareToIgnoreCase(this.getValeur()) == 0) {
                    selected = "selected";
                }
                temp = temp + "<option value='" + champValeur + "' " + selected + ">" + champAffiche + "</option>";
            }
        }
        temp = temp + "</select>";
        this.setHtmlTableauInsert(temp);
        return temp;
    }

    public String makeHtmlTabInsert(int indice, String valeur) throws Exception {
        String temp = "";
        this.setValeur(valeur);
        temp = "<select name=" + this.getNom() + "_" + indice + " class=" + this.getCss() + " id=" + this.getNom() + "_" + indice + " " + this.getAutre() + " >";
        if (this.valeurBrute != null && this.valeurBrute.length > 0) {
            temp = this.makeHtmlValeur(temp);
        }
        if (this.getBase() != null && this.getBase().length > 0) {
            for (int i = 0; i < this.getBase().length; ++i) {
                String champValeur = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColValeur()));
                String champAffiche = String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase()[i], this.getColAffiche()));
                String selected = "";
                if ((this.getValeur() == null || this.getValeur().compareToIgnoreCase("") == 0) && this.getDefaultSelected() != null) {
                    if (champValeur.compareToIgnoreCase(this.getDefaultSelected()) == 0) {
                        selected = "selected";
                    }
                } else if (champValeur.compareToIgnoreCase(this.getValeur()) == 0) {
                    selected = "selected";
                }
                temp = temp + "<option value='" + champValeur + "' " + selected + ">" + champAffiche + "</option>";
            }
        }
        temp = temp + "</select>";
        this.setHtmlTableauInsert(temp);
        return temp;
    }

    @Override
    public String getHtmlTableauInsert(int i, String valeur) throws Exception {
        return this.makeHtmlTabInsert(i, valeur);
    }
}

