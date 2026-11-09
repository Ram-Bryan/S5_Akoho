
20:50:54,757 INFO  [stdout] (default task-63) Requete select rechercher : select * from utilisateur where 1<2 
20:50:54,908 INFO  [stdout] (default task-63) Requete d'insertion : INSERT INTO Produit ( id , val , desce , idTypeProduit , puAchat , puVente , idUnite , idCategorie , idSousCategorie , puAchatUsd , puAchatEuro , puAchatAutreDevise , puVenteUsd , puVenteEuro , puVenteAutreDevise , isAchat , isVente ) values ( 'PRD004544','AkohoTest','','TPRD018',5000.0,10000.0,'UNIT0002','CAT0010','',0.0,0.0,0.0,0.0,0.0,0.0,0,0
20:50:54,918 ERROR [stderr] (default task-63) java.sql.SQLException: ORA-02291: integrity constraint (AKOHO.PRODUIT_CATEGORIE_FK) violated - parent key not found


