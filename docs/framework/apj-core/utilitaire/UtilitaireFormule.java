/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mg.cnaps.configuration.Configuration
 *  net.sourceforge.jeval.EvaluationException
 *  net.sourceforge.jeval.Evaluator
 */
package utilitaire;

import bean.AdminGen;
import bean.CGenUtil;
import bean.ClassMAPTable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mg.cnaps.configuration.Configuration;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

public class UtilitaireFormule {
    public static double eval(String expression, HashMap<String, String> variables) throws EvaluationException {
        expression = expression.replace(",", ".");
        expression = expression.replace("[", "#{").replace("]", "}");
        return UtilitaireFormule.evalWithCurlyBrackets(expression, variables);
    }

    public static double evalWithCurlyBrackets(String expression, HashMap<String, String> variables) throws EvaluationException {
        Evaluator expr = new Evaluator();
        expr.setVariables(variables);
        String evaluate = expr.evaluate(expression);
        return Double.parseDouble(evaluate);
    }

    public static boolean isFormuleWithBrackets(String expression) {
        return expression.contains("[") && expression.contains("]");
    }

    public static String[] extractExpressionWithBrackets(String expression) throws Exception {
        Pattern p = Pattern.compile("\\[(.*?)\\]");
        Matcher m = p.matcher(expression);
        ArrayList<String> toRet = new ArrayList<String>();
        int nb = 0;
        while (m.find()) {
            toRet.add(m.group(1));
            ++nb;
        }
        if (nb == 0) {
            throw new EvaluationException("Formula invalid, use Brackets []");
        }
        String[] ret = new String[toRet.size()];
        ret = toRet.toArray(ret);
        return ret;
    }

    public static List<String> getListeRubrique(String formule) throws Exception {
        ArrayList<String> retour = null;
        try {
            retour = new ArrayList<String>();
            if (formule.contains("[") && formule.contains("]")) {
                String temp = "";
                boolean encours = false;
                for (int i = 0; i < formule.length(); ++i) {
                    if (formule.charAt(i) != '[' && !encours) continue;
                    if (formule.charAt(i + 1) == ']') {
                        retour.add(temp);
                        temp = new String("");
                        encours = false;
                        continue;
                    }
                    encours = true;
                    temp = temp + formule.charAt(i + 1);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return retour;
    }

    public static String[] getListeRubriqueTab(String formule) throws Exception {
        String[] retour = null;
        try {
            List<String> rep = UtilitaireFormule.getListeRubrique(formule);
            retour = new String[rep.size()];
            rep.toArray(retour);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return retour;
    }

    public static String remplacerRubriqueParChiffre(ClassMAPTable aCalc, String formule, ClassMAPTable[] lConfig) throws Exception {
        if (formule == null || formule.compareToIgnoreCase("") == 0) {
            return "";
        }
        String retour = formule;
        String valeur = "";
        try {
            String[] listeRub;
            String[] stringArray = listeRub = UtilitaireFormule.getListeRubriqueTab(formule);
            int n = stringArray.length;
            for (int i = 0; i < n; ++i) {
                String rub;
                valeur = rub = stringArray[i];
                String[] sousRub = rub.split("::");
                if (sousRub.length > 1) {
                    if (sousRub[0].compareToIgnoreCase("Conf") == 0 || sousRub[0].compareToIgnoreCase("Configuration") == 0) {
                        String[] attrFiltre = new String[]{"id"};
                        String[] splitPointPoint = sousRub[1].split("\\.\\.");
                        String[] attrValeur = new String[]{splitPointPoint[0]};
                        Configuration conf = (Configuration)AdminGen.findUnique(lConfig, attrFiltre, attrValeur);
                        if (conf == null) {
                            throw new Exception("Configuration inexistante");
                        }
                        valeur = CGenUtil.getValeurFieldByMethod((ClassMAPTable)conf, "valmin").toString();
                        if (splitPointPoint.length > 1 && splitPointPoint[1].compareToIgnoreCase("valmax") == 0) {
                            valeur = CGenUtil.getValeurFieldByMethod((ClassMAPTable)conf, "valmax").toString();
                        }
                    }
                } else {
                    Object oval = CGenUtil.getValeurFieldByMethod(aCalc, rub);
                    if (oval != null && oval instanceof Number) {
                        valeur = oval.toString();
                    } else if (oval != null) {
                        valeur = "'" + oval.toString() + "'";
                    }
                }
                retour = retour.replace("[" + rub + "]", valeur);
            }
        }
        catch (NoSuchMethodException nm) {
            return valeur;
        }
        catch (Exception e) {
            e.printStackTrace();
            return valeur;
        }
        return retour;
    }
}

