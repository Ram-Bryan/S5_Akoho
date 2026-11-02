/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

import java.math.BigDecimal;
import utilitaire.Utilitaire;

public class ChiffreLettre {
    public static String convertIntToString(long intToString) {
        long valeur = intToString;
        String result = "";
        if (valeur < 0L) {
            result = "-";
            valeur = -1L * valeur;
        }
        if (valeur > 1000000000L) {
            long valeurSupMilliard = valeur / 1000000000L;
            result = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(result))).append(" ").append(ChiffreLettre.convertIntToString(valeurSupMilliard)).append(" milliard")));
            valeur -= valeurSupMilliard * 1000000000L;
        }
        if (valeur < 1000000000L && valeur > 1000000L) {
            int valeurMillion = (int)valeur / 1000000;
            result = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(result))).append(" ").append(ChiffreLettre.convertIntToString(valeurMillion)).append(" million(s)")));
            valeur -= (long)(valeurMillion * 1000000);
        }
        if (valeur < 1000000L && valeur > 1000L) {
            int valeurMille = (int)valeur / 1000;
            result = valeurMille > 1 ? String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(result))).append(" ").append(ChiffreLettre.convertIntToString(valeurMille)).append(" mille"))) : String.valueOf(String.valueOf(result)).concat(" mille");
            valeur -= (long)(valeurMille * 1000);
        }
        if (valeur < 1000L && valeur > 100L) {
            int valeurCent = (int)valeur / 100;
            result = valeurCent > 1 ? String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(result))).append(" ").append(ChiffreLettre.convertIntToString(valeurCent)).append(" cent"))) : String.valueOf(String.valueOf(result)).concat(" cent");
            valeur -= (long)(valeurCent * 100);
        }
        if (valeur == 100L) {
            result = String.valueOf(String.valueOf(result)).concat(" cent ");
            valeur -= 100L;
        }
        if (valeur == 1000L) {
            result = String.valueOf(String.valueOf(result)).concat(" mille ");
            valeur -= 1000L;
        }
        if (valeur == 1000000L) {
            result = String.valueOf(String.valueOf(result)).concat(" un million ");
            valeur -= 1000000L;
        }
        if (valeur == 1000000000L) {
            result = String.valueOf(String.valueOf(result)).concat(" un milliard ");
            valeur -= 1000000000L;
        }
        switch ((int)valeur) {
            case 0: {
                if (result != "" && result != "-") break;
                result = "zero";
                break;
            }
            case 1: {
                result = String.valueOf(String.valueOf(result)).concat(" un");
                break;
            }
            case 2: {
                result = String.valueOf(String.valueOf(result)).concat(" deux");
                break;
            }
            case 3: {
                result = String.valueOf(String.valueOf(result)).concat(" trois");
                break;
            }
            case 4: {
                result = String.valueOf(String.valueOf(result)).concat(" quatre");
                break;
            }
            case 5: {
                result = String.valueOf(String.valueOf(result)).concat(" cinq");
                break;
            }
            case 6: {
                result = String.valueOf(String.valueOf(result)).concat(" six");
                break;
            }
            case 7: {
                result = String.valueOf(String.valueOf(result)).concat(" sept");
                break;
            }
            case 8: {
                result = String.valueOf(String.valueOf(result)).concat(" huit");
                break;
            }
            case 9: {
                result = String.valueOf(String.valueOf(result)).concat(" neuf");
                break;
            }
            case 10: {
                result = String.valueOf(String.valueOf(result)).concat(" dix");
                break;
            }
            case 11: {
                result = String.valueOf(String.valueOf(result)).concat(" onze");
                break;
            }
            case 12: {
                result = String.valueOf(String.valueOf(result)).concat(" douze");
                break;
            }
            case 13: {
                result = String.valueOf(String.valueOf(result)).concat(" treize");
                break;
            }
            case 14: {
                result = String.valueOf(String.valueOf(result)).concat(" quatorze");
                break;
            }
            case 15: {
                result = String.valueOf(String.valueOf(result)).concat(" quinze");
                break;
            }
            case 16: {
                result = String.valueOf(String.valueOf(result)).concat(" seize");
                break;
            }
            case 17: {
                result = String.valueOf(String.valueOf(result)).concat(" dix sept");
                break;
            }
            case 18: {
                result = String.valueOf(String.valueOf(result)).concat(" dix huit");
                break;
            }
            case 19: {
                result = String.valueOf(String.valueOf(result)).concat(" dix neuf");
                break;
            }
            case 20: {
                result = String.valueOf(String.valueOf(result)).concat(" vingt");
                break;
            }
            case 30: {
                result = String.valueOf(String.valueOf(result)).concat(" trente");
                break;
            }
            case 40: {
                result = String.valueOf(String.valueOf(result)).concat(" quarante");
                break;
            }
            case 50: {
                result = String.valueOf(String.valueOf(result)).concat(" cinquante");
                break;
            }
            case 60: {
                result = String.valueOf(String.valueOf(result)).concat(" soixante");
                break;
            }
            case 70: {
                result = String.valueOf(String.valueOf(result)).concat(" soixante dix");
                break;
            }
            case 80: {
                result = String.valueOf(String.valueOf(result)).concat(" quatre vingts");
                break;
            }
            case 81: {
                result = String.valueOf(String.valueOf(result)).concat(" quatre vingts un");
                break;
            }
            case 90: {
                result = String.valueOf(String.valueOf(result)).concat(" quatre vingts-dix");
                break;
            }
            case 91: {
                result = String.valueOf(String.valueOf(result)).concat(" quatre vingts onze");
                break;
            }
            default: {
                int valeurDizaine = (int)(valeur / 10L) * 10;
                int valeurUnite = (int)(valeur % 10L);
                if (valeur > 16L && valeur < 20L || valeur > 70L && valeur < 80L || valeur > 90L && valeur < 100L) {
                    valeurDizaine -= 10;
                    valeurUnite += 10;
                }
                result = valeurDizaine == 0 ? String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(result))).append(" ").append(ChiffreLettre.convertIntToString(valeurUnite)))) : (valeurUnite == 0 ? String.valueOf(String.valueOf(result)).concat(" ") : (valeurUnite == 1 ? String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(result))).append(" ").append(ChiffreLettre.convertIntToString(valeurDizaine)).append(" et ").append(ChiffreLettre.convertIntToString(valeurUnite)))) : String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(result))).append(" ").append(ChiffreLettre.convertIntToString(valeurDizaine)).append("-").append(ChiffreLettre.convertIntToString(valeurUnite))))));
            }
        }
        return result.trim();
    }

    public static String convertRealToString(double intToString) {
        double valeurApresVirgule = intToString - (double)((long)intToString);
        long valeurAvantVirgule = (long)(intToString - valeurApresVirgule);
        String result = "";
        BigDecimal cent = new BigDecimal("100");
        double arr = Utilitaire.arrondir(valeurApresVirgule, 2);
        BigDecimal apr = cent.multiply(new BigDecimal(String.valueOf(arr)));
        long apres = apr.longValue();
        result = apres == 0L ? ChiffreLettre.convertIntToString(valeurAvantVirgule) : (apres >= 10L ? String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(ChiffreLettre.convertIntToString(valeurAvantVirgule)))).append(" , ").append(ChiffreLettre.convertIntToString(apres)))) : String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(ChiffreLettre.convertIntToString(valeurAvantVirgule)))).append(", zero ").append(ChiffreLettre.convertIntToString(apres)))));
        return result;
    }

    public static String[] convertRealToStringSepare(double intToString) {
        double valeurApresVirgule = intToString - (double)((long)intToString);
        long valeurAvantVirgule = (long)(intToString - valeurApresVirgule);
        String[] result = new String[2];
        BigDecimal cent = new BigDecimal("100");
        double arr = Utilitaire.arrondir(valeurApresVirgule, 2);
        BigDecimal apr = cent.multiply(new BigDecimal(String.valueOf(arr)));
        long apres = apr.longValue();
        if (apres == 0L) {
            result[0] = ChiffreLettre.convertIntToString(valeurAvantVirgule);
            result[1] = "";
        } else if (apres >= 10L) {
            result[0] = ChiffreLettre.convertIntToString(valeurAvantVirgule);
            result[1] = ChiffreLettre.convertIntToString(apres);
        } else {
            result[0] = ChiffreLettre.convertIntToString(valeurAvantVirgule);
            result[1] = "zero ".concat(String.valueOf(String.valueOf(ChiffreLettre.convertIntToString(apres))));
        }
        return result;
    }

    public static String convertRealToStringDevise(double intToString, String devise) {
        double valeurApresVirgule = intToString - (double)((long)intToString);
        long valeurAvantVirgule = (long)(intToString - valeurApresVirgule);
        String result = "";
        BigDecimal cent = new BigDecimal("100");
        double arr = Utilitaire.arrondir(valeurApresVirgule, 2);
        BigDecimal apr = cent.multiply(new BigDecimal(String.valueOf(arr)));
        long apres = apr.longValue();
        result = apres == 0L ? ChiffreLettre.convertIntToString(valeurAvantVirgule) + "  " + devise + "  " : (apres >= 10L ? String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(ChiffreLettre.convertIntToString(valeurAvantVirgule)))).append("  " + devise + "  ").append(ChiffreLettre.convertIntToString(apres)))) : String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(ChiffreLettre.convertIntToString(valeurAvantVirgule)))).append("  " + devise + "  zero ").append(ChiffreLettre.convertIntToString(apres)))));
        return result;
    }
}

