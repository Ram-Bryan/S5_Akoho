Feature 1:

module.jsp?but=test/livre/livre-liste.jsp → empty list

Click "Saisie" (or manually visit module.jsp?but=test/livre/livre-saisie.jsp) → fill form → submit

apresTarif.jsp redirects to livre-fiche.jsp?id=LIV…

On fiche: "Modifier" → change prix → save → back to fiche

Click "Viser" → etat goes to 11, "Modifier" disappears

Click "Supprimer" → back to list → gone

Observations:
- OKay everything works. Some questions: why when I create a livre it goes to Viser page. LIke we need to viser it. even tho its aready insiert inside DB. And it also have a button modifier. Wait so all these already comes frm the frameowkr ? Also when I already modifer it. How to de-viser an object already viser ?