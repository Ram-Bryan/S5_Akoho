/*
 * Decompiled with CFR 0.152.
 */
package chatbot;

import bean.Champ;
import bean.ListeColonneTable;
import chatbot.AiColDesc;
import chatbot.AiTabDesc;
import chatbot.ClassIA;
import chatbot.UtilChatbot;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Vector;

public class StaticQuery {
    public static String dateToday = "Aujourd'hui on est le " + LocalDateTime.now();
    public static String queryPrevi = "Tu es un assistant d'application pour aider l'utilisateur a avoir des donn\u00c3\u00a9es de pr\u00c3\u00a9vision sur l'application'\n\n'type' est toujours 'prevision',\n'datyFiltre' corr\u00c3\u00a9spond au date de filtre, par d\u00c3\u00a9faut aujourd'hui,\n'deb' corr\u00c3\u00a9spond au debut de la pr\u00c3\u00a9vision, par d\u00c3\u00a9faut le premier jour de ce mois,\n'fin' corr\u00c3\u00a9spond a la fin de la pr\u00c3\u00a9vision, par d\u00c3\u00a9faut le dernier jour de ce mois,\n'grouper' corr\u00c3\u00a9spond a la facons qu'on groupe les pr\u00c3\u00a9vision, par d\u00c3\u00a9faut 'semaine',\nTa reponse doit \u00c3\u00aatre de cette forme (format JSON) et pas d'autre r\u00c3\u00a9ponse: \n{ \n    type : string,\n    datyFiltre: string, \n    deb: string, \n    fin: string, \n    grouper : string \n\n}\n### Ma question :";
    public static String queryType = "Tu es un assistant d'application pour aider l'utilisateur, Classifie ma question en fonction de leur objectif:\ndefinitions de quelques acronymes que l'utilisateur pourra utiliser : BC (bon de commande), OF(ordre de fabrication), FAB (fabrication)  \n'saisie' si l'utilisateur dedmande a saisir quelque chose, ou de creer ou ajouter quelque chose de nouveau, \n'prevision' seulement si la question parle de tresorerie prevu comme tresorerie la plus basse explicitement, pas de prevision recette ni prevision depense \n'analyse' si elle demande des des prevision de recette du mois,prevision de depenses du mois,fabrications, des produit ou chose specifique par exemple des chiffres d'affaires par produit ,des tendances des \u00c3\u00a9l\u00c3\u00a9ments les plus vendues,les moins achetes,les plus fr\u00c3\u00a9quents, les plus rentables, ou toute autre donn\u00c3\u00a9e n\u00c3\u00a9cessitant une comparaison ou une interpr\u00c3\u00a9tation,\n'liste' si elle demande des disponibilite de chambre,une liste de quelque chose,si elle extrait des donn\u00c3\u00a9es g\u00c3\u00a9n\u00c3\u00a9rales comme des totaux, des montants ou des transactions, par exemple les chiffres d'affaires mais pas ceux par produit,\n'lister' si elle demande a lister quelque chose explicitement, SEULEMENT SI la question explicitement commence par 'lister' ou 'donner' sinon 'liste' ,\n'aucun' si aucun n'applique\n\nSi aucun n'applique, repond directement a la question\nTa reponse doit \u00c3\u00aatre de cette forme (format JSON) et pas d'autre r\u00c3\u00a9ponse: \n{ \n    type: string, \n    iaResp: string, \n\n}\n### Ma question :";
    public static String queryLog = "Tu es un assistant d'application pour aider l'utilisateur, mais l'utilisateur doit etre logged in avant de t'utiliser\nTa reponse doit \u00c3\u00aatre de cette forme (format JSON) et pas d'autre r\u00c3\u00a9ponse: \n    { \n        type: string, \n        iaResp: string \n        \n    }\n- iaResp : ta reponse\n- type: 'noUser'\n### Ma question :\n";
    public static String queryDB;
    public static String querySaisie;
    public static String queryLister;

    public static String generateQueryType(String appContext, String definitions) {
        return "Tu es un assistant d'application pour aider l'utilisateur, Classifie ma question en fonction de leur objectif:\n" + appContext + "\ndefinitions et acronymes que l'utilisateur pourra utiliser : " + definitions + " \n'saisie' si l'utilisateur dedmande a saisir quelque chose, ou de creer ou ajouter quelque chose de nouveau, \n'dev' si l'utilisateur demande a generer du code APJ (framework de l'entreprise),des questions relatif au dev: code java, jsp, script oracle, explication de fonction etc  \n'compta' si l'utilisateur demande des etats comptables (balance, grand livre, journal, lettrage, ecritures,bilans etc) ou juste des commentaires  \n'analytics' si l'utilisateur veut un dashboard, un graphe, des indicateurs, etc\n'decisionnel' si l'utilisateur veut ton avis sur son entreprise, ses donn\u00c3\u00a9es, sa situation financieres, des recommendations etc  \n'prevision' seulement si la question parle de tresorerie prevu comme tresorerie la plus basse explicitement, pas de prevision recette ni prevision depense \n'analyse' si elle demande des des prevision de recette du mois,prevision de depenses du mois,fabrications, des produit ou chose specifique par exemple des chiffres d'affaires par produit ,des tendances des \u00c3\u00a9l\u00c3\u00a9ments les plus vendues,les moins achetes,les plus fr\u00c3\u00a9quents, les plus rentables, ou toute autre donn\u00c3\u00a9e n\u00c3\u00a9cessitant une comparaison ou une interpr\u00c3\u00a9tation,\n'liste' si elle demande des disponibilite de chambre,une liste de quelque chose,si elle extrait des donn\u00c3\u00a9es g\u00c3\u00a9n\u00c3\u00a9rales comme des totaux, des montants ou des transactions, par exemple les chiffres d'affaires mais pas ceux par produit,\n'lister' si elle demande a lister quelque chose explicitement, SEULEMENT SI la question explicitement commence par 'lister' ou 'donner' sinon 'liste' ,\n'aucun' si aucun n'applique\n\nSi aucun n'applique, repond directement a la question\nTa reponse doit \u00c3\u00aatre de cette forme (format JSON) et pas d'autre r\u00c3\u00a9ponse: \n{ \n    type: string, \n    iaResp: string, \n\n}\n### Ma question :";
    }

    public static String generateJavaCode(String besoin, String javadocContent) {
        return "Tu dois g\u00c3\u00a9n\u00c3\u00a9rer du code Java 8 en respectant ces r\u00c3\u00a8gles :\n1.  R\u00c3\u00a9ponds uniquement par le code Java avec de texte explicatif et mets ta reponse dans un JSON du format ou iaResp est ta reponse:     { \n        type: dev, \n        iaResp: string \n        \n    }\n\n2. Utilise les conventions du framework APJ (constructeurs, services, DAO, exceptions APJ...).\n3. Le code doit compiler sans d\u00c3\u00a9pendances externes.\n4. Le code doit \u00c3\u00aatre auto-contenu dans une seule classe si possible.\n5. Tu dois toujours utiliser l\u00e2\u0080\u0099un des templates suivants selon le cas :\n--- Classe simple (extends ClassMAPTable) ---\npackage chatbot.templates;\nimport bean.ClassMAPTable;\n\npublic class {CLASS_NAME} extends ClassMAPTable {\n    {ATTRIBUTES}\n    public {CLASS_NAME}(){ super.setNomTable(\"{TABLE_NAME}\"); }\n    @Override public String getTuppleID(){ return {ID_FIELD}; }\n    @Override public String getAttributIDName(){ return \"{ID_COLUMN}\"; }\n}\n--- Classe avec \u00c3\u00a9tat (extends ClassEtat) ---\npackage chatbot.templates;\nimport bean.ClassEtat;\n\npublic class {CLASS_NAME} extends ClassEtat {\n    {ATTRIBUTES}\n    public {CLASS_NAME}(){ super.setNomTable(\"{TABLE_NAME}\"); }\n    @Override public String getTuppleID(){ return {ID_FIELD}; }\n    @Override public String getAttributIDName(){ return \"{ID_COLUMN}\"; }\n}\n--- Classe m\u00c3\u00a8re (extends ClassMere) ---\npackage chatbot.templates;\nimport bean.ClassMere;\n\npublic class {CLASS_NAME} extends ClassMere {\n    {ATTRIBUTES}\n    public {CLASS_NAME}(){ super.setNomTable(\"{TABLE_NAME}\"); }\n    @Override public String getTuppleID(){ return {ID_FIELD}; }\n    @Override public String getAttributIDName(){ return \"{ID_COLUMN}\"; }\n    @Override public String getNomClasseFille(){ return \"{CHILD_CLASS}\"; }\n    @Override public String getLiaisonFille(){ return \"{FOREIGN_KEY}\"; }\n}\n--- Classe fille (extends ClassFille) ---\npackage chatbot.templates;\nimport bean.ClassFille;\n\npublic class {CLASS_NAME} extends ClassFille {\n    {ATTRIBUTES}\n    public {CLASS_NAME}(){ super.setNomTable(\"{TABLE_NAME}\"); }\n    @Override public String getTuppleID(){ return {ID_FIELD}; }\n    @Override public String getAttributIDName(){ return \"{ID_COLUMN}\"; }\n    @Override public String getNomClasseMere(){ return \"{PARENT_CLASS}\"; }\n    @Override public String getLiaisonMere(){ return \"{FOREIGN_KEY}\"; }\n}\n\nBesoin de l'utilisateur : " + besoin + "\n";
    }

    public static String generateQueryDB(Class<? extends ClassIA>[] tables, Connection c) throws Exception {
        return "Tu dois g\u00c3\u00a9n\u00c3\u00a9rer des requ\u00c3\u00aates SQL pour Oracle 11g en respectant ces r\u00c3\u00a8gles strictes :    Ta reponse doit \u00c3\u00aatre de cette forme (format JSON) et pas d'autre r\u00c3\u00a9ponse, et une seule reponse\n{\n    type: liste ou analyse,\n    nomTable: Nom de la table correcte,\n    date1: dd/MM/yyyy,\n    date2: dd/MM/yyyy,\n    requeteSql: Requ\u00c3\u00aate SQL correcte\n    grouper : idproduitlib ou idpointlib \n    id : l'id specifique qu'on recherche, separe par & si il y a plusieurs par exemple:id=ETU002630&semestre=S1&promotion=p16GrpA  \n}    - les fabrications sont par d\u00c3\u00a9fauts 'analyse'\n    - type: Classifie mes requ\u00c3\u00aates SQL en fonction de leur objectif : 'liste' si la requ\u00c3\u00aate extrait des donn\u00c3\u00a9es g\u00c3\u00a9n\u00c3\u00a9rales comme des totaux, des montants ou des transactions et n'implique aucun produit en particulier, et 'analyse' si elle implique une fabrication ou plusieurs fabrications, un produit ou point de vente specifique, si elle identifie des specificites par produits ou points de ventes comme depense ou CA par produit / points de vente, tendances, des \u00c3\u00a9l\u00c3\u00a9ments les plus fr\u00c3\u00a9quents, les plus rentables, ou toute autre donn\u00c3\u00a9e n\u00c3\u00a9cessitant une comparaison ou une interpr\u00c3\u00a9tation,'aucun' si aucun n'applique\n    - si le type correspond a 'liste' ou 'analyse' utilise les tables pour creer la requeteSql:'" + StaticQuery.getTables(tables, c) + "'\n    - nomTable correspond a la table que la requete devrait etre execute, si tu a besoin de faire une jointure, met un des tables et la reponse doit etre au format de cette table et utilise JOIN\n    - date1 et date2 sont les intervalle si il y a une intervalle de date sinon met la date d'aujourd'hui dans la requete. Une semaine commence par Lundi et se termine par Dimanche. N'utilises pas SYSDATE dans la requete\n    - requeteSql: la requete SQL correspondant a la requete sans ajouter de nouvelles colonnes ou alias qui n'existent pas d\u00c3\u00a9j\u00c3\u00a0, les colonnes calcul\u00c3\u00a9s doivent avoir comme alias des colonne existantes. La requ\u00c3\u00aate doit uniquement utiliser les noms de colonnes existants et n'utilise pas FETCH FIRST ROWS ni LIMIT.\n    - grouper : si le type est analyse. Il faut d\u00c3\u00a9terminer si c'est a propos de produits ou points de vente qu'on veut analyser.\n    - id : si on recherche par un ID specifique. utilise toujours like ''%id%''.\n      bien verifier les erreurs dans la requete sql (ne pas utiliser FETCH FIRST ROWS, dans la requete sql et utiliser les colonnes existantes pour les ALIAS)\n    - les ORDER BY doivent etre par colonne pas par fonction, si t'as fais sum(nb) as nb et tu veux faire group by sum(nb) tu fais group by nb\n    Ma requete:\n";
    }

    public static String generateQueryDB(String bdd, Class<? extends ClassIA>[] tables, Connection c) throws Exception {
        return "Tu dois g\u00c3\u00a9n\u00c3\u00a9rer des requ\u00c3\u00aates SQL pour " + bdd + " en respectant ces r\u00c3\u00a8gles strictes :    Ta reponse doit \u00c3\u00aatre de cette forme (format JSON) et pas d'autre r\u00c3\u00a9ponse, et une seule reponse\n{\n    type: liste ou analyse,\n    nomTable: Nom de la table correcte,\n    date1: dd/MM/yyyy,\n    date2: dd/MM/yyyy,\n    requeteSql: Requ\u00c3\u00aate SQL correcte\n    grouper : idproduitlib ou idpointlib \n    id : l'id specifique qu'on recherche, separe par & si il y a plusieurs par exemple:id=ETU002630&semestre=S1&promotion=p16GrpA  \n}    - les fabrications sont par d\u00c3\u00a9fauts 'analyse'\n    - type: Classifie mes requ\u00c3\u00aates SQL en fonction de leur objectif : 'liste' si la requ\u00c3\u00aate extrait des donn\u00c3\u00a9es g\u00c3\u00a9n\u00c3\u00a9rales comme des totaux, des montants ou des transactions et n'implique aucun produit en particulier, et 'analyse' si elle implique une fabrication ou plusieurs fabrications, un produit ou point de vente specifique, si elle identifie des specificites par produits ou points de ventes comme depense ou CA par produit / points de vente, tendances, des \u00c3\u00a9l\u00c3\u00a9ments les plus fr\u00c3\u00a9quents, les plus rentables, ou toute autre donn\u00c3\u00a9e n\u00c3\u00a9cessitant une comparaison ou une interpr\u00c3\u00a9tation,'aucun' si aucun n'applique\n    - si le type correspond a 'liste' ou 'analyse' utilise les tables pour creer la requeteSql:'" + StaticQuery.getTables(tables, c) + "'\n    - nomTable correspond a la table que la requete devrait etre execute, si tu a besoin de faire une jointure, met un des tables et la reponse doit etre au format de cette table et utilise JOIN\n    - date1 et date2 sont les intervalle si il y a une intervalle de date sinon met la date d'aujourd'hui dans la requete. Une semaine commence par Lundi et se termine par Dimanche. N'utilises pas SYSDATE dans la requete\n    - requeteSql: la requete SQL correspondant a la requete sans ajouter de nouvelles colonnes ou alias qui n'existent pas d\u00c3\u00a9j\u00c3\u00a0, les colonnes calcul\u00c3\u00a9s doivent avoir comme alias des colonne existantes. La requ\u00c3\u00aate doit uniquement utiliser les noms de colonnes existants et n'utilise pas FETCH FIRST ROWS ni LIMIT.\n    - grouper : si le type est analyse. Il faut d\u00c3\u00a9terminer si c'est a propos de produits ou points de vente qu'on veut analyser.\n    - id : si on recherche par un ID specifique. utilise toujours like ''%id%''.\n      bien verifier les erreurs dans la requete sql (ne pas utiliser FETCH FIRST ROWS, dans la requete sql et utiliser les colonnes existantes pour les ALIAS)\n    - les ORDER BY doivent etre par colonne pas par fonction, si t'as fais sum(nb) as nb et tu veux faire group by sum(nb) tu fais group by nb\n    Ma requete:\n";
    }

    public static String generateQuerySaisie(Class<? extends ClassIA>[] tables, Connection c) throws Exception {
        return "Tu dois determiner dans quelle table l'utilisateur veut saisir quelque chose et ce qu'il veut saisir :    les tables disponible:'" + StaticQuery.getTablesSaisie(tables, c) + "'    Ta reponse doit \u00c3\u00aatre de cette forme (format JSON) et pas d'autre r\u00c3\u00a9ponse, et une seule reponse\n{\n    type: saisie,\n    nomTable: Nom de la table correcte,\n    donnees: les donnees a saisir \n}    La colonne donnees correspond un tableau de JSON par rapport au prompt,met exactement ce que le client a saisie, tu met pas si il y a pas. Met en camelCase les cles sauf pr\u00c3\u00a9ciser autrement. Met que les colonnes non null \n    \n    Ma question:\n";
    }

    public static String generateQueryLister(Class<? extends ClassIA>[] tables, Connection c) throws Exception {
        return "Tu dois determiner quelle table l'utilisateur veut lister et sur quelle intervalle de date :    les tables disponible:'" + StaticQuery.getTables(tables, c) + "'    date1 et date2 sont les intervalle si il y a une intervalle de date sinon met la date d'aujourd'hui. Une semaine commence par Lundi et se termine par Dimanche\n    Ta reponse doit \u00c3\u00aatre de cette forme (format JSON) et pas d'autre r\u00c3\u00a9ponse, et une seule reponse\n{\n    type: lister,\n    nomTable: Nom de la table correcte,\n    date1: dd/MM/yyyy,\n    date2: dd/MM/yyyy,\n}    La colonne donnees correspond un tableau de JSON par rapport au prompt,met exactement ce que le client a saisie, tu met pas si il y a pas. Met en camelCase les cles: \n    \n    Ma question:\n";
    }

    private static String getTables() throws Exception {
        return "    La structure de ma table de vente VENTE_DETAILS_CPL_2_VISEE est comme ceci : ID : string, IDVENTE : string, IDVENTELIB : string, IDPRODUIT : string, IDPRODUITLIB : string, IDORIGINE : string, QTE : decimal, PU : decimal, puTotal : decimal(30,2), puTotalAchat : decimal(30,2), puRevient : decimal(30,2), IDCATEGORIE : string, IDCATEGORIELIB : string, daty : date, IDMAGASIN : string, IDMAGASINLIB : string, IDPOINT : string, IDPOINTLIB : string, IDDEVISE : string, IDDEVISELIB.\n    La structure de ma table de bon de commande BC_CLIENT_FILLE_CPL_LIB_VISEE est comme ceci :ID string, IDBC string (id bon de commande), IDDEVISE string, MONTANT decimal, PRODUIT string, PU number(30,2), QUANTITE number(30,2), REMISE number(30,2), TVA number(30,2), UNITE string, QTEOF (qte ordre de fabrication) number(30,2), QTEOFRESTANTE (qte reste ordre de fabrication) number(30,2), QTEFAB (qte fabrique) number(30,2), QTEFABRESTANTE (qte reste a fabriquer) number(30,2), QTELIVRE number(30,2), QTENONLIVRE number(30,2), PRODUITLIB string.\n     La structure de ma table de verification de chambre disponible et reserve V_DISPONIBILTE_CHAMBRE est comme ceci: DATY: date, IDCHAMBRE : string,NOMCHAMBRE : string, QTE: int, IDRESERVATION : string,ETAT : int.\n    La structure de ma table de liste et saisie de reservation RESERVATIONDETAILS_LIB est comme ceci: ID string, IDMERE string, QTE int, DATY date, IDPRODUIT string, libelleproduit string, categorieproduit string, PU number, montant number, tva number, montantTva number 20 2, montantttc number 20 2, categorieproduitlib string, heure string.    La structure de ma table d'achat et depesnes FFFILLECPL_VISEE est comme ceci : ID : string, IDFACTUREFOURNISSEUR : string, IDPRODUIT : string, IDPRODUITLIB : string, QTE : number, PU : number, REMISES : number, IDBCDETAIL : string, TVA : number, IDDEVISE : string, DATY : date, MONTANTHT : number, MONTANTTTC : number, MONTANTTVA : number, MONTANTREMISE : number, MONTANT : number.\n    La structure de ma table de fabrication FABRICATIONFILLECPL2 est comme ceci: ID : string,IDINGREDIENTS : string,LIBELLE : string,REMARQUE : string,IDMERE :string,DATYBESOIN : string,QTE : number(30,2),IDUNITE : string,PU : number(30,2),IDINGREDIENTSLIB : string,DATY : date,revient : decimal. \n    La structure de ma table de composants PRODUIT_LIB est : ID (string), val (string), DESCE (string), IDTYPEPRODUIT (string), IDTYPEPRODUITLIB (string), PUACHAT (number), PUVENTE (number), idunite (string), IDUNITELIB (string), IDCATEGORIE (string), IDCATEGORIELIB (string), IDSOUSCATEGORIELIB (string), seuilmin (number), seuilmax (number), puAchatUsd (number), puAchatEuro (number), puAchatAutreDevise (number), puVenteUsd (number), puVenteEuro (number), puVenteAutreDevise (number), isvente (boolean), isachat (boolean), compte_vente (string), compte_achat (string).\n    La structure de ma table de pr\u00c3\u00a9vision, si ma question inclu prevu ou n'importe qui parle de prevision, utilise cette table PREVISION_COMPLET_CPL est comme ceci : ID (string), DESIGNATION (string), IDCAISSE (string), IDCAISSELIB (string), IDVENTEDETAIL (string), IDVENTE (string), IDVENTELIB (string), IDVIREMENT (string), DEBIT (decimal), CREDIT (decimal), DATY (date), ETAT (string), IDOP (string), IDOPLIB (string), IDORIGINE (string), IDDEVISE (string), IDDEVISELIB (string), TAUX (decimal), IDTIERS (string), COMPTE (string), effectifDebit (decimal), effectifCredit (decimal), depenseEcart (decimal), recetteEcart (decimal), idfacture (string).    Dans ma table de pr\u00c3\u00a9vision, la colonne 'credit' represente la recette prevu et 'debit' le depense prevu.\n    Dans ma table de bon de commande ou BC, on parle de OF (ordre de fabrication), FAB (fabrication) et LIVRE (livraison) de produits.\n    Dans ma table V_DISPONIBILTE_CHAMBRE, une chambre libre est d\u00c3\u00a9fini par une idReservation null pour l'intervalle de date et un etat null OU une reservation pas null mais un etat < 11, une chambre reserve est une chambre avec un etat >=11 et qui a un idReservation. Si la personne demande la disponibilite d'une chambre, elle donnera le numero que tu vas mettre en like nomchambre '%nb%' dans la requete, si elle ne donne pas ne met de like. N'oublie de mettre l'intervalle de date si il y'en a \n    Utilise les id_lib si tu veux chercher les noms, par example noms de produits : idproduitlib, noms des magasins : idmagasinlib, etc etc.\n    Tu ne peux pas utiliser d'autres colonnes que ce que je t'ai donner car je vais mapper la requete a un objet deja defini donc pas d'alias mais met dans les colonnes existant sauf ID la reponse des sommes, groupes, counts, etc    Pas de FETCH FIRST ROWS ONLY, tu ne peux en aucun cas l'utiliser\n    Base de donnee: Oracle 11g\n";
    }

    private static String getTables(Class<? extends ClassIA>[] tables, Connection c) throws Exception {
        StringBuilder tablesString = new StringBuilder();
        for (Class<? extends ClassIA> tableClass : tables) {
            ClassIA table = tableClass.newInstance();
            Vector champs = ListeColonneTable.getChampFromTable(table.getNomTableIA(), c);
            tablesString.append("Nom table: ").append(table.getNomTableIA()).append(" => ");
            if (table.getClass().isAnnotationPresent(AiTabDesc.class)) {
                String description = table.getClass().getAnnotation(AiTabDesc.class).value();
                tablesString.append(description);
            }
            for (Champ champ : champs) {
                Field field = UtilChatbot.getDeclaredFieldIgnoreCase(table.getClass(), champ.getNomColonne());
                if (field == null) continue;
                field.setAccessible(true);
                tablesString.append(" ");
                tablesString.append(champ.getNomColonne()).append(" ").append(champ.getTypeColonne().toLowerCase());
                if (!field.isAnnotationPresent(AiColDesc.class)) continue;
                String description = field.getAnnotation(AiColDesc.class).value();
                tablesString.append(": ");
                tablesString.append(description);
            }
            tablesString.append(" | ");
        }
        tablesString.append("\n Utilise les id_lib si tu veux chercher les noms, par example noms de produits : idproduitlib, noms des magasins : idmagasinlib, etc etc.\nTu ne peux pas utiliser d'autres colonnes que ce que je t'ai donner car je vais mapper la requete a un objet deja defini donc pas d'alias mais met dans les colonnes existant sauf ID la reponse des sommes, groupes, counts, etc.\nPas de FETCH FIRST ROWS ONLY, tu ne peux en aucun cas l'utiliser\nBase de donnee: Oracle 11g;");
        tablesString.append("\n");
        System.out.println(tablesString.toString());
        return tablesString.toString();
    }

    private static String getTablesSaisie(Class<? extends ClassIA>[] tables, Connection c) throws Exception {
        StringBuilder tablesString = new StringBuilder();
        for (Class<? extends ClassIA> tableClass : tables) {
            ClassIA table = tableClass.newInstance();
            Vector champs = ListeColonneTable.getChampFromTable(table.getNomTableIA(), c);
            tablesString.append("Nom table: ").append(table.getNomTableIA()).append(" => ");
            if (table.getClass().isAnnotationPresent(AiTabDesc.class)) {
                String description = table.getClass().getAnnotation(AiTabDesc.class).value();
                tablesString.append(description);
            }
            for (Champ champ : champs) {
                Field field = UtilChatbot.getDeclaredFieldIgnoreCase(table.getClass(), champ.getNomColonne());
                if (field == null) continue;
                field.setAccessible(true);
                tablesString.append(" ");
                tablesString.append(champ.getNomColonne()).append(" ").append(champ.getTypeColonne().toLowerCase());
                if (!field.isAnnotationPresent(AiColDesc.class)) continue;
                String description = field.getAnnotation(AiColDesc.class).value();
                tablesString.append(": ");
                tablesString.append(description);
            }
            tablesString.append(table.getSaisiePromptCompletion(c));
            tablesString.append(" | ");
        }
        tablesString.append("\nTu ne peux pas utiliser d'autres colonnes que ce que je t'ai donner car je vais mapper la requete a un objet deja defini donc pas d'alias mais met dans les colonnes existant sauf ID la reponse des sommes, groupes, counts, etc.\n");
        System.out.println(tablesString.toString());
        return tablesString.toString();
    }

    private static String getTables(String bdd, Class<? extends ClassIA>[] tables, Connection c) throws Exception {
        StringBuilder tablesString = new StringBuilder();
        for (Class<? extends ClassIA> tableClass : tables) {
            ClassIA table = tableClass.newInstance();
            Vector champs = ListeColonneTable.getChampFromTable(table.getNomTableIA(), c);
            tablesString.append("Nom table: ").append(table.getNomTableIA()).append(" => ");
            if (table.getClass().isAnnotationPresent(AiTabDesc.class)) {
                String description = table.getClass().getAnnotation(AiTabDesc.class).value();
                tablesString.append(description);
            }
            for (Champ champ : champs) {
                Field field = UtilChatbot.getDeclaredFieldIgnoreCase(table.getClass(), champ.getNomColonne());
                if (field == null) continue;
                field.setAccessible(true);
                tablesString.append(" ");
                tablesString.append(champ.getNomColonne()).append(" ").append(champ.getTypeColonne().toLowerCase());
                if (!field.isAnnotationPresent(AiColDesc.class)) continue;
                String description = field.getAnnotation(AiColDesc.class).value();
                tablesString.append(": ");
                tablesString.append(description);
            }
            tablesString.append("Utilise les id_lib si tu veux chercher les noms, par example noms de produits : idproduitlib, noms des magasins : idmagasinlib, etc etc.\nTu ne peux pas utiliser d'autres colonnes que ce que je t'ai donner car je vais mapper la requete a un objet deja defini donc pas d'alias mais met dans les colonnes existant sauf ID la reponse des sommes, groupes, counts, etc.\nPas de FETCH FIRST ROWS ONLY, tu ne peux en aucun cas l'utiliser\nBase de donnee: " + bdd + ";");
            tablesString.append("\n");
        }
        System.out.println(tablesString.toString());
        return tablesString.toString();
    }

    static {
        try {
            queryDB = "Tu dois g\u00c3\u00a9n\u00c3\u00a9rer des requ\u00c3\u00aates SQL pour Oracle 11g en respectant ces r\u00c3\u00a8gles strictes :    Ta reponse doit \u00c3\u00aatre de cette forme (format JSON) et pas d'autre r\u00c3\u00a9ponse, et une seule reponse\n{\n    type: liste ou analyse,\n    nomTable: Nom de la table correcte,\n    date1: dd/MM/yyyy,\n    date2: dd/MM/yyyy,\n    requeteSql: Requ\u00c3\u00aate SQL correcte\n    grouper : idproduitlib ou idpointlib \n    id : l'id specifique qu'on recherche  \n}    - les fabrications sont par d\u00c3\u00a9fauts 'analyse'    - type: Classifie mes requ\u00c3\u00aates SQL en fonction de leur objectif : 'liste' si la requ\u00c3\u00aate extrait des donn\u00c3\u00a9es g\u00c3\u00a9n\u00c3\u00a9rales comme des totaux, des montants ou des transactions et n'implique aucun produit en particulier, et 'analyse' si elle implique une fabrication ou plusieurs fabrications, un produit ou point de vente specifique, si elle identifie des specificites par produits ou points de ventes comme depense ou CA par produit / points de vente, tendances, des \u00c3\u00a9l\u00c3\u00a9ments les plus fr\u00c3\u00a9quents, les plus rentables, ou toute autre donn\u00c3\u00a9e n\u00c3\u00a9cessitant une comparaison ou une interpr\u00c3\u00a9tation,'aucun' si aucun n'applique\n    - si le type correspond a 'liste' ou 'analyse' utilise les tables pour creer la requeteSql:'" + StaticQuery.getTables() + "'    - nomTable correspond a la table que la requete devrait etre execute, si tu a besoin de faire une jointure, met un des tables et la reponse doit etre au format de cette table et utilise JOIN\n    - date1 et date2 sont les intervalle si il y a une intervalle de date sinon met la date d'aujourd'hui dans la requete. Une semaine commence par Lundi et se termine par Dimanche. N'utilises pas SYSDATE dans la requete\n    - requeteSql: la requete SQL correspondant a la requete sans ajouter de nouvelles colonnes ou alias qui n'existent pas d\u00c3\u00a9j\u00c3\u00a0, les colonnes calcul\u00c3\u00a9s doivent avoir comme alias des colonne existantes. La requ\u00c3\u00aate doit uniquement utiliser les noms de colonnes existants et n'utilise pas FETCH FIRST ROWS ni LIMIT.\n    - grouper : si le type est analyse. Il faut d\u00c3\u00a9terminer si c'est a propos de produits ou points de vente qu'on veut analyser.\n    - id : si on recherche par un ID specifique. utilise toujours like ''%id%'.\n      bien verifier les erreurs dans la requete sql (ne pas utiliser FETCH FIRST ROWS, dans la requete sql et utiliser les colonnes existantes pour les ALIAS)\n    - les ORDER BY doivent etre par colonne pas par fonction, si t'as fais sum(nb) as nb et tu veux faire group by sum(nb) tu fais group by nb    Ma requete:\n";
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            querySaisie = "Tu dois determiner dans quelle table l'utilisateur veut saisir quelque chose et ce qu'il veut saisir :    les tables disponible:'" + StaticQuery.getTables() + "'    Ta reponse doit \u00c3\u00aatre de cette forme (format JSON) et pas d'autre r\u00c3\u00a9ponse, et une seule reponse\n{\n    type: saisie,\n    nomTable: Nom de la table correcte,\n    donnees: les donnees a saisir \n}    La colonne donnees correspond un tableau de JSON par rapport au prompt,met exactement ce que le client a saisie, tu met pas si il y a pas. Met en camelCase les cles sauf pr\u00c3\u00a9ciser autrement. Met que les colonnes non null \n    \n    Ma question:\n";
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            queryLister = "Tu dois determiner quelle table l'utilisateur veut lister et sur quelle intervalle de date :    les tables disponible:'" + StaticQuery.getTables() + "'    date1 et date2 sont les intervalle si il y a une intervalle de date sinon met la date d'aujourd'hui. Une semaine commence par Lundi et se termine par Dimanche\n    Ta reponse doit \u00c3\u00aatre de cette forme (format JSON) et pas d'autre r\u00c3\u00a9ponse, et une seule reponse\n{\n    type: lister,\n    nomTable: Nom de la table correcte,\n    date1: dd/MM/yyyy,\n    date2: dd/MM/yyyy,\n}    La colonne donnees correspond un tableau de JSON par rapport au prompt,met exactement ce que le client a saisie, tu met pas si il y a pas. Met en camelCase les cles: \n    \n    Ma question:\n";
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

