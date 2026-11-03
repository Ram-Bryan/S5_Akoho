Feature 2:

module.jsp?but=test/maison/maison-liste.jsp → empty

Click to saisie (module.jsp?but=test/maison/maison-saisie.jsp)

Fill mere fields + 3 rows of pieces → submit

apresMultiple.jsp redirects to maison-fiche.jsp?id=MSN…

Tab "Pièces" shows the 3 pieces

On fiche: "Modifier" → change → save → same tab workflow

Verify in DB: SELECT * FROM PIECE WHERE IDMERE='MSN…';