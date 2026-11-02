/*
 * Decompiled with CFR 0.152.
 */
package affichage;

import affichage.Champ;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utilitaire.Utilitaire;

public class ChampCalcul
extends Champ {
    public ChampCalcul() {
    }

    public ChampCalcul(String nom) {
        super(nom);
    }

    @Override
    public void makeHtml() throws Exception {
        String temp = "";
        if (!this.getVisible()) {
            temp = "<input data-type='nombre' name=" + this.getNom() + " type='hidden' class=" + this.getCss() + "  id=" + this.getNom() + " value='" + Utilitaire.doubleWithoutExponential(Utilitaire.stringToDouble(this.getValeur()), this.getFormulaire().getObjet().getNombreApresVirgule()) + "' onblur=\"calculer('" + this.getNom() + "')\" " + this.getAutre() + ">";
        } else {
            String nb = "";
            Pattern pattern = Pattern.compile(".*_(\\d+)$");
            Matcher matcher = pattern.matcher(this.getNom());
            if (matcher.matches()) {
                String number;
                nb = number = matcher.group(1);
            }
            temp = "<input data-type='nombre' name=" + this.getNom() + " type=" + this.getType() + " class=" + this.getCss() + "  id=" + this.getNom() + " value='" + Utilitaire.doubleWithoutExponential(Utilitaire.stringToDouble(this.getValeur()), this.getFormulaire().getObjet().getNombreApresVirgule()) + "' onblur=\"calculer('" + this.getNom() + "')\" oninput=\"if(this.value !== '') { synchro(this,checkbox" + nb + ".id) }\"" + this.getAutre() + ">";
        }
        this.setHtml(temp);
    }
}

