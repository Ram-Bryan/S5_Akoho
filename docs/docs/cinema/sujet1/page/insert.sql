-- ============================================================================
-- INSERT : Menu Cinéma (32 lignes) — sans accents, icone = home
-- ============================================================================

-- 1. Racine
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA', 'Cinema', 'home', NULL, 20, 1, NULL);

-- 2. Categories
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA01',   'Categories', 'home', NULL, 1, 2, 'MNDNCINEMA');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA0101', 'Liste',      'home', 'module.jsp?but=cinema/categorie/categorie-liste.jsp',  1, 3, 'MNDNCINEMA01');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA0102', 'Saisie',     'home', 'module.jsp?but=cinema/categorie/categorie-saisie.jsp', 2, 3, 'MNDNCINEMA01');

-- 3. Categories de range
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA02',   'Categories de range', 'home', NULL, 2, 2, 'MNDNCINEMA');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA0201', 'Liste',               'home', 'module.jsp?but=cinema/categorieRange/categorieRange-liste.jsp',  1, 3, 'MNDNCINEMA02');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA0202', 'Saisie',              'home', 'module.jsp?but=cinema/categorieRange/categorieRange-saisie.jsp', 2, 3, 'MNDNCINEMA02');

-- 4. Historique prix cat range
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA03',   'Historique prix cat. range', 'home', NULL, 3, 2, 'MNDNCINEMA');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA0301', 'Liste',                      'home', 'module.jsp?but=cinema/histPrixCatRange/histPrixCatRange-liste.jsp',  1, 3, 'MNDNCINEMA03');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA0302', 'Saisie',                     'home', 'module.jsp?but=cinema/histPrixCatRange/histPrixCatRange-saisie.jsp', 2, 3, 'MNDNCINEMA03');

-- 5. Salles
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA04',   'Salles', 'home', NULL, 4, 2, 'MNDNCINEMA');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA0401', 'Liste',  'home', 'module.jsp?but=cinema/salle/salle-liste.jsp',  1, 3, 'MNDNCINEMA04');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA0402', 'Saisie', 'home', 'module.jsp?but=cinema/salle/salle-saisie.jsp', 2, 3, 'MNDNCINEMA04');

-- 6. Ranges
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA05',   'Ranges', 'home', NULL, 5, 2, 'MNDNCINEMA');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA0501', 'Liste',  'home', 'module.jsp?but=cinema/range/range-liste.jsp',  1, 3, 'MNDNCINEMA05');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA0502', 'Saisie', 'home', 'module.jsp?but=cinema/range/range-saisie.jsp', 2, 3, 'MNDNCINEMA05');

-- 7. Places
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA06',   'Places', 'home', NULL, 6, 2, 'MNDNCINEMA');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA0601', 'Liste',  'home', 'module.jsp?but=cinema/place/place-liste.jsp',  1, 3, 'MNDNCINEMA06');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA0602', 'Saisie', 'home', 'module.jsp?but=cinema/place/place-saisie.jsp', 2, 3, 'MNDNCINEMA06');

-- 8. Films
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA07',   'Films', 'home', NULL, 7, 2, 'MNDNCINEMA');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA0701', 'Liste',  'home', 'module.jsp?but=cinema/film/film-liste.jsp',  1, 3, 'MNDNCINEMA07');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA0702', 'Saisie', 'home', 'module.jsp?but=cinema/film/film-saisie.jsp', 2, 3, 'MNDNCINEMA07');

-- 9. Programmes
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA08',   'Programmes', 'home', NULL, 8, 2, 'MNDNCINEMA');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA0801', 'Liste',      'home', 'module.jsp?but=cinema/programme/programme-liste.jsp',  1, 3, 'MNDNCINEMA08');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA0802', 'Saisie',     'home', 'module.jsp?but=cinema/programme/programme-saisie.jsp', 2, 3, 'MNDNCINEMA08');

-- 10. Clients
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA09',   'Clients', 'home', NULL, 9, 2, 'MNDNCINEMA');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA0901', 'Liste',   'home', 'module.jsp?but=cinema/client/client-liste.jsp',  1, 3, 'MNDNCINEMA09');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA0902', 'Saisie',  'home', 'module.jsp?but=cinema/client/client-saisie.jsp', 2, 3, 'MNDNCINEMA09');

-- 11. Reservations
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA10',   'Reservations', 'home', NULL, 10, 2, 'MNDNCINEMA');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA1001', 'Liste',        'home', 'module.jsp?but=cinema/reservation/reservation-liste.jsp',  1, 3, 'MNDNCINEMA10');
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA1002', 'Saisie',       'home', 'module.jsp?but=cinema/reservation/reservation-saisie.jsp', 2, 3, 'MNDNCINEMA10');

-- 12. Statuts reservation (lien direct, sans enfants)
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE) VALUES ('MNDNCINEMA11',   'Statuts reservation', 'home', 'module.jsp?but=cinema/statutReservation/statutReservation-liste.jsp', 11, 2, 'MNDNCINEMA');

-- ============================================================================
-- MIROIR MENUDYNAMIQUE2 (32 lignes identiques)
-- ============================================================================
INSERT INTO MENUDYNAMIQUE2 (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
SELECT ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE
FROM MENUDYNAMIQUE
WHERE ID LIKE 'MNDNCINEMA%';

-- ============================================================================
-- USERMENU : REFUSER='*' → visible par TOUS les utilisateurs
-- (correction du bug : NULL ne passait pas le filtre du framework)
-- ============================================================================
INSERT INTO USERMENU (ID, REFUSER, IDMENU, IDROLE, CODESERVICE, CODEDIR, INTERDIT)
SELECT 'UMECINEMA' || SUBSTR(ID, 11), '*', ID, NULL, NULL, NULL, 0
FROM MENUDYNAMIQUE
WHERE ID LIKE 'MNDNCINEMA%';

-- ============================================================================
-- USERMENU1707 : idem
-- ============================================================================
INSERT INTO USERMENU1707 (ID, REFUSER, IDMENU, IDROLE, CODESERVICE, CODEDIR, INTERDIT)
SELECT 'UMECINEMA1707_' || SUBSTR(ID, 11), '*', ID, NULL, NULL, NULL, 0
FROM MENUDYNAMIQUE
WHERE ID LIKE 'MNDNCINEMA%';

COMMIT;