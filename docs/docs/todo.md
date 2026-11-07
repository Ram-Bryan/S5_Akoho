Heres teh full spec we will implmenet:
Voici la spec, sans nouvelle question. Deux conventions de ma part sont signalées au §1.

1. Conventions retenues
Semaine d’âge = jours ÷ 7 arrondi à l’entier inférieur, plus 1. La semaine 21 couvre donc les jours 140 à 146.
Début de ponte, qui était à 147 dans mon message précédent, devient 140 jours : votre Excel met 1 œuf par jour dès la semaine 21, donc dès le jour 140. La durée de vie de 72 semaines devient 504 jours (semaines 1 à 72).
Stockage en jours dans SOUCHE ; le basculement jours/semaines ne concerne que l’affichage, plus tard.
PONTEHEBDOMADAIRE existe déjà dans le détail du programme. On le lit comme « œufs par jour et par poule pendant cette semaine d’âge » (1 pour ISA Brown).
MORTALITE du programme est un pourcentage par semaine, par sexe.
OACPOURCENTAGE, FERTILITE, TAUXECLOSION : valeur vide ou 0 = non renseignée = 100 %. Si une ligne de semaine est absente, on reprend la dernière semaine renseignée.
Triage : toute quantité triée est vivante à la date du triage, sans tenir compte de la qualité (QP000022). Les morts viennent uniquement du suivi journalier.
2. Logique métier

Entités suivies. Chaque lot a, par date, des vivants, des morts (cumulés, ils restent dans le lot) et des perdus (cumulés, ils sortent du lot). Total du lot = vivants + morts (ou vivants seuls si on filtre les morts, filtre global du calendrier).

Cohortes. Une cohorte est un groupe d’oiseaux de même naissance et de même sexe :

Cohorte réelle : (lot, bâtiment, parquet, sexe), créée à la date du triage avec la quantité triée. Sa naissance est LOT.DATEECLOSION. Un lot à deux triages donne deux apports successifs (cas de LOT000005).
Cohorte virtuelle : (lot racine, date de naissance, sexe), sans bâtiment ni parquet (« non affecté »), créée par une éclosion simulée. Les descendants de tous âges d’un même lot sont regroupés par date de naissance, ce qui évite la récursion sur les générations.

Une journée de simulation, de la plus ancienne date de triage jusqu’à dateMax (si vide, aujourd’hui + 730 jours). dateMin ne filtre que l’affichage. Pour chaque jour d, dans cet ordre :

Apports : les triages datés d créent leurs cohortes. Les oiseaux n’existent pas avant leur triage, donc aucune ponte n’est simulée avant.
Mortalité :
Jusqu’à aujourd’hui (7/11/2026), seuls les événements réels du suivi journalier comptent : NBMORT augmente les morts et diminue les vivants, NBPERDU diminue les vivants et augmente les perdus. Un jour sans saisie n’a aucun événement.
Après aujourd’hui, on applique le taux de mortalité du programme divisé par 7, par jour, sur les vivants de la cohorte (par sexe). Ces morts s’ajoutent aux morts du lot.
Fin de vie : une cohorte dont l’âge atteint DUREEVIEJOURS quitte la ferme. Ses vivants disparaissent des totaux, sans être comptés comme morts.
Poussin ou adulte : adulte si âge ≥ AGEADULTEJOURS, sinon DEBUTPONTEJOURS. Sans aucun des deux, tous les oiseaux sont comptés adultes.
Ponte : seulement si la souche a un DEBUTPONTEJOURS et si l’âge est au moins égal à ce seuil. Pour chaque cohorte de femelles :
œufs = vivantes × ponte/jour (semaine d'âge) × OAC% × fertilité% × éclosion%.
Les œufs forment un lot d’éclosion prévu à d + INCUBATIONJOURS, s’il est ≤ dateMax. Les œufs ne sont jamais comptés comme poulets.
Éclosions du jour : les œufs prévus ce jour naissent. POURCENTAGEFEMELLE % deviennent des femelles, le reste des mâles, dans la cohorte virtuelle du lot racine (nouvelle ou existante). Les descendants utilisent le programme du lot racine. Ils pondent à leur tour à l’âge voulu.
Instantané : on enregistre, par lot et par (bâtiment, parquet), poussins, adultes, morts cumulés et perdus cumulés. Les calculs restent en nombres décimaux, et on arrondit à l’affichage.

Cas limites.

Un lot sans DATEECLOSION est exclu avec un message.
Un lot sans souche ni programme n’est compté qu’en réel, sans ponte ni fin de vie, et signalé « paramètres manquants ».
Les programmes existants sont des données de test. Ils donneront des valeurs absurdes tant qu’ils ne sont pas remplis, d’où le message ci-dessus.
3. Contrôle de saisie du suivi journalier

À l’enregistrement d’une ligne (lot, parquet, sexe, nbMort, nbPerdu) :

IDSEXE est obligatoire pour toute nouvelle ligne.
Restant à la date saisie = triages de ce lot, ce parquet et ce sexe datés au plus tard à cette date − morts et perdus déjà saisis jusqu’à cette date (hors la ligne en cours). nbMort + nbPerdu ne doit pas le dépasser.
Restant total : le même calcul sans limite de date, pour qu’une saisie antidatée ne rende pas négatif ce qui suit.
En cas de dépassement, on refuse avec un message du type « Femelles restantes : 12, saisie : 15 ».
Ce calcul est le même code que celui de la simulation (service unique), pour qu’ils ne divergent pas.
MORTALITE et CULLS de l’ancienne saisie sont masqués. Leurs anciennes valeurs restent en base et sont ignorées.
4. Spec technique

Package ferme.previsionpoulet, pour éviter la confusion avec l’existant prevision, qui concerne la trésorerie :

ParametresRace : paramètres de souche et courbe du programme (par sexe et par semaine, avec le report de la dernière semaine). Les valeurs vides valent 100 %.
SimulationEffectif : entrée (idFerme, dateMin, dateMax), sortie une liste d’EffectifLotJour (date, lot racine, bâtiment, parquet, poussins, adultes, morts, perdus). Elle lit les trois vues du §5, filtre sur les enregistrements validés (ETAT ≥ 11) et ne contient aucune logique en SQL.
EffectifService : le solde par (lot, parquet, sexe) utilisé par le contrôle du suivi.
PrevisionCalendrier : clone de CaCalendrier (mois en colonnes, jours en lignes, total par jour et par mois), alimenté par SimulationEffectif.
Modifications : SuiviJournalierDetail (attributs nbMort, nbPerdu, idSexe, contrôle du §3) et sa page de saisie.

Pages

prevision-calendrier.jsp (modèle ca-calendrier-v2.jsp) : choix de la ferme (obligatoire), dates min et max (facultatives), case « inclure les morts », et un lien sur chaque cellule vers la page de détail, que l’original n’a pas. Son catch doit afficher une alerte au lieu de seulement printStackTrace.
prevision-detail.jsp : un jour. Tableau lot / bâtiment / parquet avec poussins, adultes, morts, total, une ligne de total pour la ferme, et le tri par lot, bâtiment, parquet ou date (croissant et décroissant). Les descendants apparaissent sous « Descendants du lot X », en « non affecté ».
5. SQL
sql
-- 1. Paramètres de race (durées en jours)
ALTER TABLE SOUCHE ADD (
  INCUBATIONJOURS    NUMBER(4)   DEFAULT 21 NOT NULL,
  POURCENTAGEFEMELLE NUMBER(5,2) DEFAULT 50 NOT NULL,
  DEBUTPONTEJOURS    NUMBER(5),
  AGEADULTEJOURS     NUMBER(5),
  DUREEVIEJOURS      NUMBER(5)
);
ALTER TABLE SOUCHE ADD CONSTRAINT SOUCHE_PCTF_CK CHECK (POURCENTAGEFEMELLE BETWEEN 0 AND 100);

-- 2. Suivi journalier : morts, perdus, sexe
ALTER TABLE SUIVIJOURNALIERDETAIL ADD (
  NBMORT  NUMBER DEFAULT 0 NOT NULL,
  NBPERDU NUMBER DEFAULT 0 NOT NULL,
  IDSEXE  VARCHAR2(50)
);
ALTER TABLE SUIVIJOURNALIERDETAIL ADD CONSTRAINT SJD_SEXE_FK
  FOREIGN KEY (IDSEXE) REFERENCES SEXE(ID);
ALTER TABLE SUIVIJOURNALIERDETAIL ADD CONSTRAINT SJD_MORTPERDU_CK
  CHECK (NBMORT >= 0 AND NBPERDU >= 0);

-- 3. Vues de lecture (jointures seulement)
CREATE OR REPLACE VIEW PREV_TRIAGE_V AS
SELECT t.IDLOT, t.DATY, t.ETAT, d.IDBATIMENT, d.IDPARQUET, d.IDSEXE, d.QTE
FROM TRIAGEBATIMENTPARQUET t JOIN TRIAGEBATIMENTPARQUETDETAIL d ON d.IDMERE = t.ID;

CREATE OR REPLACE VIEW PREV_SUIVI_V AS
SELECT s.IDLOT, s.DATY, s.ETAT, d.IDBATIMENT, d.IDPARQUET, d.IDSEXE, d.NBMORT, d.NBPERDU
FROM SUIVIJOURNALIER s JOIN SUIVIJOURNALIERDETAIL d ON d.IDMERE = s.ID
WHERE d.IDSEXE IS NOT NULL;

CREATE OR REPLACE VIEW PREV_LOT_V AS
SELECT l.ID AS IDLOT, l.NOMLOT, l.IDFERME, l.DATEECLOSION, l.IDPROGRAMME, p.IDSOUCHE,
       s.VAL AS SOUCHELIB, s.INCUBATIONJOURS, s.POURCENTAGEFEMELLE,
       s.DEBUTPONTEJOURS, s.AGEADULTEJOURS, s.DUREEVIEJOURS
FROM LOT l
LEFT JOIN PROGRAMMEZOOTECHNIQUE p ON p.ID = l.IDPROGRAMME
LEFT JOIN SOUCHE s ON s.ID = p.IDSOUCHE;

Here ar ethe sqls you asked for:
SQL> 
SQL> 
SQL> SELECT COUNT(*) TOTAL, SUM(CASE WHEN l.IDFERME <> b.IDFERME THEN 1 ELSE 0 END) DIFFERENTES
FROM LOT l
JOIN TRIAGEBATIMENTPARQUET t ON t.IDLOT = l.ID
JOIN TRIAGEBATIMENTPARQUETDETAIL d ON d.IDMERE = t.ID
JOIN BATIMENT b ON b.ID = d.IDBATIMENT;
  2    3    4    5  
     TOTAL DIFFERENTES
---------- -----------
	20	    18

SQL> 
SQL> 
SQL> 
SQL> SELECT d.IDMERE||'|lot='||d.IDLOT||'|ponte='||TO_CHAR(d.DATEPONTE,'DD/MM/YY')||'|qte='||d.QTE
  ||'|mise='||TO_CHAR(m.DATEMISEENMACHINE,'DD/MM/YY')||'|prevue='||TO_CHAR(m.DATEECLOSIONPREVUE,'DD/MM/YY') AS LIGNE
FROM MISEENINCUBATEURDETAIL d
JOIN MISEENINCUBATEUR m ON m.ID = d.IDMERE;
SELECT ID||'|lot='||IDLOT||'|eclos='||TO_CHAR(DATEECLOSION,'DD/MM/YY')||'|oeufs='||NOMBREOEUFINITIAL AS LIGNE
FROM ECLOSION;  2    3    4  
LIGNE
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
MEI000001|lot=LOTN0003|ponte=18/09/26|qte=15|mise=18/09/26|prevue=18/09/26
MEI000001|lot=LOTN0003|ponte=18/09/26|qte=15|mise=18/09/26|prevue=18/09/26

SQL>   2  

LIGNE
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
ECL000001|lot=LOT000002|eclos=18/09/26|oeufs=150

SQL> 
SQL> 
SQL> 
SQL> 


And here ar ethe 2 classes:
package vente;

import bean.CGenUtil;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.Locale;

public class CaCalendrier {
    String[] listeMois;
    HashMap<String, Map<String, List<CaJourDetailPourCalendrier>>> caMensuels;
    String[] listeDate;
    HashMap<String, Vector> reservations;
    int [] jours;
    HashMap<String,Double[]> total = new HashMap<>();
    public CaCalendrier(String dtMin,String dtMax) throws Exception {
        Connection c=null;
        try {
            c = new UtilDB().GetConn();
            this.jours = new int[31];
            for (int i = 0; i < 31; i++) {
                jours[i] = i+1;
            }
            this.setListeDate(dtMin,dtMax);
            this.setReservations(dtMin,dtMax,c);
            this.setCaMensuels();
            this.setTotal();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if(c!=null)c.close();
        }
    }

    public int[] getJours() {
        return jours;
    }

    public void setJours(int[] jours) {
        this.jours = jours;
    }

    public String[] getListeDate() {
        return listeDate;
    }

    public void setListeDate(String dtMin,String dtMax) {
//        if(Utilitaire.diffJourDaty(dtMax,dtMin)<0)throw new Exception("Date sup inferieur a date Inf");
        int day = Utilitaire.diffJourDaty(dtMax, dtMin);
        String liste[]=new String[day];
        for (int i = 0; i < day; i++) {
            liste[i]=Utilitaire.formatterDaty(Utilitaire.ajoutJourDate(dtMin,i))  ;
        }
        this.listeDate = liste;
    }

    public HashMap<String, Vector> getReservations() {
        return reservations;
    }

    public void setReservations(String dMin,String dMax,Connection c) throws Exception {
        CaJourDetailPourCalendrier res = new CaJourDetailPourCalendrier();
        if(dMin==null||dMin.compareToIgnoreCase("")==0)dMin=Utilitaire.formatterDaty(Utilitaire.getDebutSemaine(Utilitaire.dateDuJourSql())) ;
        String[] colInt={"daty"};
        String[] valInt={dMin,dMax};
        this.reservations= CGenUtil.rechercher2D(res,colInt,valInt,"daty",c,"");
    }

    public HashMap<String, Map<String, List<CaJourDetailPourCalendrier>>> getCaMensuels() {
        return caMensuels;
    }

    public void setCaMensuels(HashMap<String, Map<String, List<CaJourDetailPourCalendrier>>> caMensuels) {
        this.caMensuels = caMensuels;
    }

    public void setCaMensuels() {
        this.caMensuels = fusionParMois(this.getReservations());
        // Générer la liste complète de tous les mois entre min et max, même sans données
        this.listeMois = genererTousLesMois();
        
        // Initialiser les mois vides pour éviter les NullPointerException
        for (String mois : listeMois) {
            if (!caMensuels.containsKey(mois)) {
                caMensuels.put(mois, new HashMap<>());
            }
        }
    }

    private String[] genererTousLesMois() {
        if (listeDate == null || listeDate.length == 0) {
            return new String[]{};
        }
        
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE);
        LinkedHashMap<String, Boolean> moisMap = new LinkedHashMap<>();
        
        // Extraire la première et dernière date pour parcourir tous les mois
        LocalDate debut = LocalDate.parse(listeDate[0], inputFormat);
        LocalDate fin = LocalDate.parse(listeDate[listeDate.length - 1], inputFormat);
        
        // Parcourir chaque jour et ajouter le mois correspondant
        LocalDate courant = debut;
        while (!courant.isAfter(fin)) {
            String cleMois = courant.getMonth().getDisplayName(TextStyle.FULL, Locale.FRANCE)
                    + " " + courant.getYear();
            moisMap.putIfAbsent(cleMois, true);
            courant = courant.plusMonths(1).withDayOfMonth(1);
        }
        
        return moisMap.keySet().toArray(new String[]{});
    }

    public HashMap<String, Map<String,List<CaJourDetailPourCalendrier>>> fusionParMois(HashMap<String, Vector> caJour) {
        HashMap<String, Map<String,List<CaJourDetailPourCalendrier>>> resultat = new LinkedHashMap<>();
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE);
        for (String dateStr:listeDate) {

            Vector valeursJour = caJour.get(dateStr);
            if (valeursJour!=null){
                LocalDate date = LocalDate.parse(dateStr, inputFormat);

                String cleMois = date.getMonth().getDisplayName(TextStyle.FULL, Locale.FRANCE)
                        + " " + date.getYear();
                String cleJour = String.valueOf(date.getDayOfMonth());
                resultat.putIfAbsent(cleMois, new HashMap<>());
                resultat.get(cleMois).putIfAbsent(cleJour, new ArrayList<>());
                resultat.get(cleMois).get(cleJour).addAll(valeursJour);
            }
        }
        return resultat;
    }

    public void setTotal() {
        // total[0] - Montant Total
        // total[1] - Moyenne Total
        this.total = new HashMap<>();
        for (String dt : listeMois) {
            double montantTotal = 0;
            double moyenne = 0;
            int count = 0;
            Map<String, List<CaJourDetailPourCalendrier>> caJours = this.caMensuels.get(dt);
            for (Map.Entry<String, List<CaJourDetailPourCalendrier>> entry : caJours.entrySet()) {
                String cle = entry.getKey();
                List<CaJourDetailPourCalendrier> v = entry.getValue();
                if (v != null) {
                    for (Object o:v){
                        CaJourDetailPourCalendrier res = (CaJourDetailPourCalendrier) o;
                        montantTotal += res.getMontant();
                    }
                    count += v.size();
                }
            }
            moyenne += montantTotal / count;
            total.put(dt,new Double[]{montantTotal,moyenne});
        }
    }

    public HashMap<String, Double[]> getTotal() {
        return total;
    }

    public String[] getListeMois() {
        return listeMois;
    }

    public void setListeMois(String[] listeMois) {
        this.listeMois = listeMois;
    }

    public CaJourDetailPourCalendrier [] getReservationByTime(int jour, String mois) throws Exception {
        System.out.println(mois+"  "+jour);
        List<CaJourDetailPourCalendrier> res = this.caMensuels.get(mois).get(String.valueOf(jour));
        if (res == null) {
            return null;
        }
        System.out.println(res.size());
        return res.toArray(new CaJourDetailPourCalendrier[]{});
    }
}
package vente;

import bean.ClassMAPTable;

import java.sql.Date;

public class CaJourDetailPourCalendrier extends ClassMAPTable {
    Date daty;
    int jour,semainemois,mois,annee;
    String jourLib;
    double montant;
    String idjoursemaine;
    int numerojoursemaine;

    public CaJourDetailPourCalendrier() {
        this.setNomTable("CA_JOUR_DETAILS_CA");
    }

    @Override
    public String getTuppleID() {
        return "";
    }

    @Override
    public String getAttributIDName() {
        return "";
    }

    public Date getDaty() {
        return daty;
    }

    public void setDaty(Date daty) {
        this.daty = daty;
    }

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }

    public int getSemainemois() {
        return semainemois;
    }

    public void setSemainemois(int semainemois) {
        this.semainemois = semainemois;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getJourLib() {
        return jourLib;
    }

    public void setJourLib(String jourLib) {
        this.jourLib = jourLib;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getIdjoursemaine() {
        return idjoursemaine;
    }

    public void setIdjoursemaine(String idjoursemaine) {
        this.idjoursemaine = idjoursemaine;
    }

    public int getNumerojoursemaine() {
        return numerojoursemaine;
    }

    public void setNumerojoursemaine(int numerojoursemaine) {
        this.numerojoursemaine = numerojoursemaine;
    }
}