-- ============================================================================
-- CINEMA — Menu sidebar
-- Ajoute une section "Cinéma" avec ses sous-menus CRUD
-- ============================================================================

-- ============================================================================
-- 1. Menu racine "Cinéma" (niveau 2)
-- ============================================================================
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA', 'Cinéma', 'movie', NULL, 10, 1, NULL);

-- ============================================================================
-- 2. Sous-menu "Catégories"
-- ============================================================================
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA01', 'Catégories', 'label', NULL, 1, 3, 'MNDNCINEMA');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA0101', 'Saisie', 'add', 'module.jsp?but=cinema/categorie/categorie-saisie.jsp', 1, 4, 'MNDNCINEMA01');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA0102', 'Liste', 'view_list', 'module.jsp?but=cinema/categorie/categorie-liste.jsp', 2, 4, 'MNDNCINEMA01');

-- ============================================================================
-- 3. Sous-menu "Catégories de range"
-- ============================================================================
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA02', 'Catégories de range', 'category', NULL, 2, 3, 'MNDNCINEMA');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA0201', 'Saisie', 'add', 'module.jsp?but=cinema/categorieRange/categorieRange-saisie.jsp', 1, 4, 'MNDNCINEMA02');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA0202', 'Liste', 'view_list', 'module.jsp?but=cinema/categorieRange/categorieRange-liste.jsp', 2, 4, 'MNDNCINEMA02');

-- ============================================================================
-- 4. Sous-menu "Historique prix catégorie range"
-- ============================================================================
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA03', 'Historique prix cat. range', 'payments', NULL, 3, 3, 'MNDNCINEMA');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA0301', 'Saisie', 'add', 'module.jsp?but=cinema/histPrixCatRange/histPrixCatRange-saisie.jsp', 1, 4, 'MNDNCINEMA03');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA0302', 'Liste', 'view_list', 'module.jsp?but=cinema/histPrixCatRange/histPrixCatRange-liste.jsp', 2, 4, 'MNDNCINEMA03');

-- ============================================================================
-- 5. Sous-menu "Salles"
-- ============================================================================
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA04', 'Salles', 'meeting_room', NULL, 4, 3, 'MNDNCINEMA');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA0401', 'Saisie', 'add', 'module.jsp?but=cinema/salle/salle-saisie.jsp', 1, 4, 'MNDNCINEMA04');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA0402', 'Liste', 'view_list', 'module.jsp?but=cinema/salle/salle-liste.jsp', 2, 4, 'MNDNCINEMA04');

-- ============================================================================
-- 6. Sous-menu "Ranges"
-- ============================================================================
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA05', 'Ranges', 'event_seat', NULL, 5, 3, 'MNDNCINEMA');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA0501', 'Saisie', 'add', 'module.jsp?but=cinema/range/range-saisie.jsp', 1, 4, 'MNDNCINEMA05');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA0502', 'Liste', 'view_list', 'module.jsp?but=cinema/range/range-liste.jsp', 2, 4, 'MNDNCINEMA05');

-- ============================================================================
-- 7. Sous-menu "Places"
-- ============================================================================
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA06', 'Places', 'chair', NULL, 6, 3, 'MNDNCINEMA');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA0601', 'Saisie', 'add', 'module.jsp?but=cinema/place/place-saisie.jsp', 1, 4, 'MNDNCINEMA06');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA0602', 'Liste', 'view_list', 'module.jsp?but=cinema/place/place-liste.jsp', 2, 4, 'MNDNCINEMA06');

-- ============================================================================
-- 8. Sous-menu "Films"
-- ============================================================================
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA07', 'Films', 'movie_filter', NULL, 7, 3, 'MNDNCINEMA');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA0701', 'Saisie', 'add', 'module.jsp?but=cinema/film/film-saisie.jsp', 1, 4, 'MNDNCINEMA07');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA0702', 'Liste', 'view_list', 'module.jsp?but=cinema/film/film-liste.jsp', 2, 4, 'MNDNCINEMA07');

-- ============================================================================
-- 9. Sous-menu "Programmes"
-- ============================================================================
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA08', 'Programmes', 'theaters', NULL, 8, 3, 'MNDNCINEMA');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA0801', 'Saisie', 'add', 'module.jsp?but=cinema/programme/programme-saisie.jsp', 1, 4, 'MNDNCINEMA08');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA0802', 'Liste', 'view_list', 'module.jsp?but=cinema/programme/programme-liste.jsp', 2, 4, 'MNDNCINEMA08');

-- ============================================================================
-- 10. Sous-menu "Clients"
-- ============================================================================
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA09', 'Clients', 'group', NULL, 9, 3, 'MNDNCINEMA');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA0901', 'Saisie', 'add', 'module.jsp?but=cinema/client/client-saisie.jsp', 1, 4, 'MNDNCINEMA09');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA0902', 'Liste', 'view_list', 'module.jsp?but=cinema/client/client-liste.jsp', 2, 4, 'MNDNCINEMA09');

-- ============================================================================
-- 11. Sous-menu "Réservations"
-- ============================================================================
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA10', 'Réservations', 'confirmation_number', NULL, 10, 3, 'MNDNCINEMA');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA1001', 'Saisie', 'add', 'module.jsp?but=cinema/reservation/reservation-saisie.jsp', 1, 4, 'MNDNCINEMA10');

INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA1002', 'Liste', 'view_list', 'module.jsp?but=cinema/reservation/reservation-liste.jsp', 2, 4, 'MNDNCINEMA10');

-- ============================================================================
-- 12. Sous-menu "Statuts de réservation" (lecture seule)
-- ============================================================================
INSERT INTO MENUDYNAMIQUE (ID, LIBELLE, ICONE, HREF, RANG, NIVEAU, ID_PERE)
VALUES ('MNDNCINEMA11', 'Statuts réservation', 'checklist', 'module.jsp?but=cinema/statutReservation/statutReservation-liste.jsp', 11, 3, 'MNDNCINEMA');

-- ============================================================================
-- COMMIT
-- ============================================================================
COMMIT;