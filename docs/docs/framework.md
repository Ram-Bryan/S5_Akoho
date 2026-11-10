Framework APJ — Guide de démarrage
Document destiné à tout développeur qui récupère le framework pour la première fois.
Il synthétise les fonctionnalités cœur et les patterns obligatoires.

1. Vue d'ensemble
Le framework est une surcouche Java EE (Tomcat 9 / WildFly 10) qui fournit :

Un ORM maison basé sur la réflexion (ClassMAPTable + CGenUtil)

Une couche UI auto-générée (PageRecherche, PageInsert, PageConsulte, etc.)

Une couche service générique (UserEJB) qui fait tout le CRUD pour n'importe quelle entité

Un workflow d'état (ClassEtat : créé / validé / clôturé / annulé)

Un mécanisme d'audit (historique + historique_valeur)

Un système de génération de PK via séquences Oracle

Philosophie : tu décris ton entité en Java (getters/setters + constructeur), et le framework génère tout le reste : pages liste, pages saisie, pages fiche, filtres, exports CSV/XLS/XML/PDF, split-view, calendrier, dashboards.

2. Hiérarchie des classes modèle (bean.*)
text
ClassMAPTable                  ← Classe mère obligatoire de toute entité
   │
   └── ClassUser               (+ iduser, idverif, datesaisie, direction, service)
          │
          └── ClassEtat        (+ etat, roleValidation — workflow visa)
                 │
                 ├── ClassMere    (+ fille[], liaisonFille — côté entité mère)
                 │
                 └── ClassFille   (+ liaisonMere, mere — côté entité fille)
Quand utiliser quoi
Cas	Extends
Entité simple sans workflow (référentiel, table de config)	ClassMAPTable
Entité avec workflow (créé → validé → clôturé)	ClassEtat
Entité mère d'une relation 1:N	ClassMere
Entité fille d'une relation 1:N	ClassFille
Vue SQL avec libellés FK	extends <entité parente> + setNomTable("<table>_lib")
Note : ClassMere extends ClassEtat et ClassFille extends ClassEtat. Donc une ClassMere a déjà etat + iduser + validerObject().

3. Une entité minimale (ClassMAPTable)
java
package monpackage;

import bean.ClassMAPTable;
import java.sql.Connection;

public class MonEntite extends ClassMAPTable {
    private String id;
    private String libelle;
    private String idFk;        // FK simple = juste le String de la PK
    private double montant;
    private Date daty;

    public MonEntite() throws Exception {
        this.setNomTable("MON_ENTITE");   // ← nom EXACT de la table Oracle
    }

    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("ME", "getseq_mon_entite");  // indice + fonction séquence
        this.setId(makePK(c));
    }

    @Override public String getTuppleID()       { return id; }
    @Override public String getAttributIDName() { return "id"; }
    @Override public String[] getMotCles()      { return new String[]{"libelle"}; }
    @Override public String[] getValMotCles()   { return new String[]{"libelle"}; }

    // + getters/setters pour tous les champs
}
Règles critiques
Règle	Raison
Nom du champ Java = nom de la colonne SQL (casse insensible, mais _ compte)	ListeColonneTable mappe par nom
Pas d'underscore dans les champs Java (préférer idFilm à id_film)	Getters idiomatiques (getIdFilm())
int primitif pour NUMBER entier, double pour NUMBER décimal, String pour VARCHAR2, java.sql.Date pour DATE	Le framework gère mal Integer, Timestamp, Long
Constructeur throws Exception	ClassMAPTable() ne throw pas, mais setNomTable dans le constructeur fils peut
setNomTable dans le constructeur	Sinon nomTable == null → NPE
4. Opérations CRUD (CGenUtil + méthodes héritées)
Méthodes héritées de ClassMAPTable (à utiliser partout)
java
// Insertion + audit
o.construirePK(c);
o.insertToTableWithHisto(refUser, c);     // refUser = getTuppleID() de l'utilisateur

// Modification + audit
o.updateToTableWithHisto(refUser, c);

// Suppression + audit
o.deleteToTableWithHisto(refUser, c);

// Chargement unitaire
MonEntite x = (MonEntite) new MonEntite().getById(id, null, c);
Recherche (CGenUtil)
java
// Simple (les champs non-null servent de LIKE)
MonEntite[] list = (MonEntite[]) CGenUtil.rechercher(new MonEntite(), null, null, c, " AND etat=1 ");

// Par critère nommé
CGenUtil.rechercher(e, new String[]{"idFilm"}, new String[]{"FILM001"}, c, "");
Hooks de validation
Surcharger dans l'entité — le framework les appelle automatiquement avant insert/update/delete :

java
@Override public void controler(Connection c)        throws Exception { /* avant insert */ }
@Override public void controlerUpdate(Connection c)  throws Exception { /* avant update */ }
@Override public void controlerDelete(Connection c)  throws Exception { /* avant delete */ }
5. Couche UI — la partie qui te fait gagner 90% du temps
Le framework génère le HTML des pages. Toi tu ne codes que la logique.

5.1 Page liste (PageRecherche)
jsp
<%
    MonEntite o = new MonEntite();
    String[] criteres      = {"libelle", "daty"};       // champs du formulaire filtre
    String[] intervalles   = {"daty"};                   // champs à filtrer par plage
    String[] colonnesAffic = {"id", "libelle", "daty"}; // colonnes du tableau
    PageRecherche pr = new PageRecherche(o, request, criteres, intervalles, 4, colonnesAffic, colonnesAffic.length);
    pr.setTitre("Liste");
    pr.setLien((String) session.getValue("lien"));
    pr.setApres("monpackage/mon-entite-liste.jsp");
    pr.setUtilisateur((UserEJB) session.getValue("u"));
    pr.creerObjetPage(colonnesAffic, null);   // null = pas de colonne somme
%>
<%= pr.getFormu().getHtmlEnsemble() %>
<%= pr.getTableau().getHtml() %>
<%= pr.getBasPage() %>
Gratuit : filtres, tri, pagination, export CSV/XLS/XML/PDF, filtre par colonne JS.

5.2 Page saisie (PageInsert)
jsp
<%
    MonEntite o = new MonEntite();
    PageInsert pi = new PageInsert(o, request, (UserEJB) session.getValue("u"));
    pi.setLien((String) session.getValue("lien"));
    pi.setTitre("Saisie");
    pi.getFormu().getChamp("etat").setVisible(false);
    pi.getFormu().getChamp("etat").setDefaut("1");
    pi.getFormu().getChamp("iduser").setVisible(false);
    pi.getFormu().getChamp("iduser").setDefaut("0");
    pi.preparerDataFormu();
%>
<form action="<%=pi.getLien()%>?but=apresTarif.jsp" method="post">
    <%= pi.getFormu().getHtmlInsert() %>
    <input name="acte"     type="hidden" value="insert">
    <input name="bute"     type="hidden" value="monpackage/mon-entite-fiche.jsp">
    <input name="classe"   type="hidden" value="monpackage.MonEntite">
    <input name="nomtable" type="hidden" value="MON_ENTITE">
</form>
La même JSP sert pour l'update : PageInsert détecte acte=update et pré-remplit l'objet.

5.3 Page fiche (PageConsulte)
jsp
<%
    MonEntite o = new MonEntite();
    PageConsulte pc = new PageConsulte(o, request, (UserEJB) session.getValue("u"));
    pc.setTitre("Fiche");
    pc.getChampByName("libelle").setLibelle("Libellé");
    pc.getChampByName("iduser").setVisible(false);
%>
<%= pc.getHtml() %>
Avec onglets (relation mère-fille) : pc.setButtons(...), pc.setOnglets(...), jsp:include des sous-onglets.

6. Les 3 JSP d'action centrales (NE PAS RÉINVENTER)
Tout le CRUD passe par 3 JSP génériques :

JSP	Cas d'usage
apresTarif.jsp	CRUD simple (une entité)
apresMultiple.jsp	CRUD mère-fille (PageInsertMultiple / PageUpdateMultiple)
apresTarifMultiple.jsp	Mappings M:N (attacher / détacher)
Signature des paramètres (apresTarif.jsp)
Param	Rôle
acte	insert / update / delete / valider / cloturer / annuler / dupliquer / custom
classe	Nom complet Java (monpackage.MonEntite)
nomtable	Nom Oracle (MON_ENTITE)
bute	JSP cible après succès
id	PK (pour update/delete/valider)
action	Pour acte=insertWithAction
acteDetail	Pour acte=custom (changeState)
Détection automatique : si acte=insert ET id fourni → bascule en update.

Signature (apresMultiple.jsp)
Ajoute : classefille, colonneMere, nombreLigne, ids[] (checkboxes), idmere.

7. module.jsp — le contrôleur unique de page
Toutes les pages de l'app sont appelées par :

text
module.jsp?but=<chemin relatif de la page>
module.jsp :

vérifie l'authentification (session.u obligatoire)

injecte le layout (header, menu, footer, JS)

inclut la page demandée dans <div id="butjsp">

fournit à chaque page : session.lien (URL de base), session.u (UserEJB), session.langue

Conséquence : tes JSP ne doivent pas contenir <html>, <head>, <body> — elles sont incluses.

8. UserEJB — le service central
C'est le bean de session (session HTTP u) qui fait tout le CRUD pour n'importe quelle entité :

java
u.createObject(ClassMAPTable)                                  → ClassMAPTable (avec PK)
u.updateObject(ClassMAPTable)
u.deleteObject(ClassMAPTable)
u.validerObject(ClassMAPTable)
u.cloturerObject(ClassMAPTable)
u.annulerObject(ClassMAPTable)
u.dupliquerObject(ClassMAPTable, classeFille, colonneMere)

u.createObjectMultiple(mere, colonneMere, filles)
u.updateObjectMultiple(mere, colonneMere, filles)
u.deleteObjectMultiple(objet[])

u.getData(e, colInt, valInt, c, aWhere)                        → Object[]
u.getDataPage(e, colInt, valInt, numPage, aWhere, colSom, c, npp) → ResultatEtSomme

u.getUser()                → MapUtilisateur
u.getMapAutoComplete()     → Map pour l'autocomplete
u.getTraduction(String)    → i18n
→ Pas besoin d'EJB dédié par entité. UserEJB est générique.

9. Fonctionnalités avancées (à connaître)
Fonctionnalité	Classe	Usage
Split-view liste/fiche	ApjSplitView + pr.setApjSplitEnabled(true)	Liste à gauche, fiche à droite en AJAX
Calendrier drag & drop	Calendrier + CalendrierElement	Planning programmes, réservations
Analyse croisée	PageRechercheGroupe + TableauRechercheGroupe	Group by, sommes, drag & drop de colonnes
Dashboard	Dashboard, DashboardApj, DashboardGraph, DashCard	Cartes KPI + graphiques (bar/line/pie/doughnut)
Kanban	Kanban	Vue tableau drag & drop par statut
Export PDF/CSV/XLS/XML	intégré à PageRecherche.getBasPage()	Bouton "Exporter"
Autocomplete FK	champ.setPageAppelComplete(...)	Champ avec loupe + recherche AJAX
Popup création FK	champ.setPageAppelInsert(...)	Bouton "+" pour créer une FK à la volée
Éditeur riche	Champ.toEditorFull(...)	WYSIWYG (gras, italique, tables...)
Import Excel	XlsImport / AdminGen.fromExcel	Import CSV/XLS vers entités
Mère-fille inline	PageInsertMultiple / PageUpdateMultiple	Tableau éditable multi-lignes
10. Génération de PK — pattern obligatoire
Pas d'auto-increment. Chaque table a une séquence Oracle + une fonction PL/SQL wrapper :

sql
CREATE SEQUENCE SEQ_MON_ENTITE START WITH 1 INCREMENT BY 1 NOCACHE;

CREATE OR REPLACE FUNCTION getseq_mon_entite RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_MON_ENTITE.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/
Et côté Java :

java
this.preparePk("ME", "getseq_mon_entite");   // indice 2-4 lettres + fonction
this.setId(makePK(c));                       // → "ME" + "000001" = "ME000001"
Format PK final : <INDICE><SEQ_6_chiffres> sur VARCHAR2(50).

11. Conventions à respecter (résumé)
Élément	Convention
Package entité	Un package par module métier (cinema, stock, client...)
Nom de table	snake_case minuscule (cinema_film)
Nom de colonne	camelCase (ou minuscules, sans underscore — sinon getters getId_film())
Nom de champ Java	Identique à la colonne (même casse, ou casse différente sans underscore)
Getters/setters	get + Majuscule(champ) — dérivé par réflexion
Séquences	SEQ_<TABLE> + fonction getseq_<table>
Vues de lecture	suffixe _lib (cinema_programme_lib)
Indice PK	2-4 majuscules, unique par table (ex: CFIL, CPRG)
Dates	java.sql.Date ↔ DATE Oracle
Heures séparées	String "HH:mm" ↔ VARCHAR2(5) (éviter Timestamp)
JSP	xxx-liste.jsp, xxx-saisie.jsp, xxx-fiche.jsp, inc/xxx-det.jsp
12. Pièges à éviter
❌ Ne PAS utiliser
Integer objet → utiliser int primitif

Long → utiliser int ou double

Timestamp → utiliser Date + String "HH:mm"

java.util.Date → utiliser java.sql.Date

Noms de colonnes avec _ → getters moches (getId_film())

CGenUtil.rechercher(int[] indicesColonnes, ...) → fragile (ordre physique des colonnes)

✅ Faire
Toujours setNomTable(...) dans le constructeur

Toujours construirePK(c) avant insertToTable

Toujours setLien(...) + setApres(...) + setUtilisateur(...) sur PageRecherche

Toujours fermer les Connection (try/finally ou try-with-resources)

Toujours passer refUser (getTuppleID de l'utilisateur) à insertToTableWithHisto

Sur les listes : ajouter pr.getTableau().setLien(...) pour rendre les lignes cliquables

Sur les FK : champ.setPageAppelComplete(classe, colVal, table, retour, mapping)

Sur les dates : ChampDate (auto), pas Champ simple

⚠️ Attention
PageInsert / PageConsulte chargent toutes les colonnes de la table — si une colonne existe en base mais pas dans le Java, elle est ignorée ; l'inverse plante.

insertToTableWithHisto commit déjà (ou pas — vérifier UserEJB) : si tu enchaînes plusieurs inserts, faire une seule transaction explicite avec Connection c + try/finally.

ConnectionProxy warn si tu ouvres une 2ème connexion sans avoir fermé la 1ère → une connexion à la fois par thread.

module.jsp?but=... — toujours passer par là, jamais une JSP directe.

13. Flux complet d'un CRUD (de bout en bout)
text
1. Utilisateur clique menu → module.jsp?but=xxx-liste.jsp
2. xxx-liste.jsp : PageRecherche.creerObjetPage() → HTML + filtres + pagination
3. Utilisateur clique "Saisie" → xxx-saisie.jsp
4. xxx-saisie.jsp : PageInsert → form HTML
5. Utilisateur submit → apresTarif.jsp?acte=insert&classe=...&nomtable=...&bute=xxx-fiche.jsp
6. apresTarif.jsp :
   - instancie la classe via réflexion
   - PageInsert.getObjectAvecValeur() → objet rempli
   - u.createObject(o) → construirePK + insertToTableWithHisto
   - redirige vers bute&id=<nouvel_id>
7. xxx-fiche.jsp : PageConsulte → affiche la fiche + boutons Modifier/Supprimer/Valider
Tout le code "métier" est dans l'entité : constructeur, construirePK, controler(), overrides de createObject() si logique custom.

14. Checklist d'intégration d'un nouveau module
□ Créer un package Java (monpackage)
□ Créer les entités (extends ClassMAPTable / ClassEtat / ClassMere / ClassFille)
□ Créer les vues _lib SQL pour les FK
□ Créer les séquences + fonctions Oracle
□ Créer le DDL des tables (colonnes sans underscore, etat/iduser pour ClassEtat)
□ Créer les JSP liste / saisie / fiche / inc
□ Ajouter l'entrée menu dans elements/menu/module.jsp
□ Tester via module.jsp?but=monpackage/xxx-liste.jsp
□ Vérifier export CSV/XLS/XML/PDF
□ Vérifier le split-view si activé
□ Vérifier le workflow visa si ClassEtat
15. Aide-mémoire express
text
Entité ?              → extends ClassMAPTable (+ ClassEtat / ClassMere / ClassFille si besoin)
Nom table ?           → setNomTable("<snake_case>") dans constructeur
PK ?                  → construirePK(c) avec preparePk + makePK
CRUD ?                → insertToTableWithHisto / updateToTableWithHisto / deleteToTableWithHisto
Liste ?               → PageRecherche + creerObjetPage
Saisie ?              → PageInsert + form → apresTarif.jsp
Fiche ?               → PageConsulte
Mère-fille ?          → PageInsertMultiple + apresMultiple.jsp
FK autocomplete ?     → champ.setPageAppelComplete(...)
Validation ?          → override controler(Connection c)
Historique ?          → déjà géré par insertToTableWithHisto
Export ?              → déjà géré par PageRecherche.getBasPage()
Workflow visa ?       → ClassEtat + apresTarif.jsp?acte=valider
Dashboard ?           → DashboardApj + DashboardGraph
Calendrier ?          → Calendrier + CalendrierElement
Analyse ?             → PageRechercheGroupe