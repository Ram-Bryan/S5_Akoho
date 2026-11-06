## Ajouter un lien sur un tableau existant:

Voir inc/bondelivraison-details.jsp
```jsp

java.util.Map<String, String> actions = new java.util.LinkedHashMap<>();
        actions.put("Generer un lot", pr.getLien() + "?but=ferme/lot/lot-saisie.jsp");
        pr.getTableau().setLienClicDroite(actions);

```

Le framework ajoute automatiquement l'id de la ou on est, au lien comme ca: http://localhost:8088/async-standard/pages/module.jsp?but=ferme/lot/lot-saisie.jsp&id=BLF002929

Si on veut changer le nom du parametre, on met "-xxxx" sur le texte. Donc ca devient:
```jsp
        actions.put("Generer un lot-idblfille", pr.getLien() + "?but=ferme/lot/lot-saisie.jsp");

        pour donner: http://localhost:8088/async-standard/pages/module.jsp?but=ferme/lot/lot-saisie.jsp&idblfille=BLF002929

```

## GetById:

```java
Connection tmpConn = new UtilDB().GetConn();
As_BonDeLivraison_Fille line = new As_BonDeLivraison_Fille();
line = (As_BonDeLivraison_Fille) line.getById(idBlFilleParam, null, tmpConn);
```