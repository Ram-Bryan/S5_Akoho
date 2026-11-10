-- ============================================================================
-- CINEMA — Données de test
-- ============================================================================

-- ============================================================================
-- 1. Catégories de film (4 catégories)
-- ============================================================================
INSERT INTO cinema_categorie (id, libelle) VALUES ('CCAT000001', 'Action');
INSERT INTO cinema_categorie (id, libelle) VALUES ('CCAT000002', 'Comédie');
INSERT INTO cinema_categorie (id, libelle) VALUES ('CCAT000003', 'Drame');
INSERT INTO cinema_categorie (id, libelle) VALUES ('CCAT000004', 'Normal');

-- ============================================================================
-- 2. Catégories de range (3 catégories — référence)
-- ============================================================================
INSERT INTO cinema_categorie_range (id, libelle) VALUES ('CCRG000001', 'Standard');
INSERT INTO cinema_categorie_range (id, libelle) VALUES ('CCRG000002', 'Premium');
INSERT INTO cinema_categorie_range (id, libelle) VALUES ('CCRG000003', 'VIP');

-- ============================================================================
-- 3. Historique des prix des catégories de range (2 dates par catégorie)
--    Ancienne date = il y a 6 mois, date actuelle = aujourd'hui
--    Prix actuel : Standard +0, Premium +2000, VIP +5000
--    Ancien prix : Standard +0, Premium +1500, VIP +4000
-- ============================================================================

-- STANDARD — 2 historiques
INSERT INTO cinema_hist_prix_cat_range (id, idcategorierange, prixsupplement, datedebut)
VALUES ('CHPR000001', 'CCRG000001', 0, ADD_MONTHS(TRUNC(SYSDATE), -6));
INSERT INTO cinema_hist_prix_cat_range (id, idcategorierange, prixsupplement, datedebut)
VALUES ('CHPR000002', 'CCRG000001', 0, TRUNC(SYSDATE));

-- PREMIUM — 2 historiques (1500 → 2000)
INSERT INTO cinema_hist_prix_cat_range (id, idcategorierange, prixsupplement, datedebut)
VALUES ('CHPR000003', 'CCRG000002', 1500, ADD_MONTHS(TRUNC(SYSDATE), -6));
INSERT INTO cinema_hist_prix_cat_range (id, idcategorierange, prixsupplement, datedebut)
VALUES ('CHPR000004', 'CCRG000002', 2000, TRUNC(SYSDATE));

-- VIP — 2 historiques (4000 → 5000)
INSERT INTO cinema_hist_prix_cat_range (id, idcategorierange, prixsupplement, datedebut)
VALUES ('CHPR000005', 'CCRG000003', 4000, ADD_MONTHS(TRUNC(SYSDATE), -6));
INSERT INTO cinema_hist_prix_cat_range (id, idcategorierange, prixsupplement, datedebut)
VALUES ('CHPR000006', 'CCRG000003', 5000, TRUNC(SYSDATE));

-- ============================================================================
-- 4. Statuts de réservation (4 statuts — IDs fixes dans ConstanteCinema)
-- ============================================================================
INSERT INTO cinema_statut_reservation (id, libelle) VALUES ('CSTR000001', 'reserve');
INSERT INTO cinema_statut_reservation (id, libelle) VALUES ('CSTR000002', 'paye');
INSERT INTO cinema_statut_reservation (id, libelle) VALUES ('CSTR000003', 'annule');
INSERT INTO cinema_statut_reservation (id, libelle) VALUES ('CSTR000004', 'expire');

-- ============================================================================
-- 5. Salles (4 salles)
-- ============================================================================
INSERT INTO cinema_salle (id, libelle) VALUES ('CSAL000001', 'Salle 1');
INSERT INTO cinema_salle (id, libelle) VALUES ('CSAL000002', 'Salle 2');
INSERT INTO cinema_salle (id, libelle) VALUES ('CSAL000003', 'Salle 3');
INSERT INTO cinema_salle (id, libelle) VALUES ('CSAL000004', 'Salle 4');

-- ============================================================================
-- 6. Ranges
--    Salle 1 : 3 ranges (Standard, Premium, VIP)          → 30 places
--    Salle 2 : 4 ranges (Standard, Standard, Premium, VIP) → 50 places
--    Salle 3 : 3 ranges (Standard, Standard, Premium)     → 25 places
--    Salle 4 : 3 ranges (Standard, Premium, VIP)          → 20 places
-- ============================================================================

-- SALLE 1
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange) VALUES ('CRNG000001', 'A (Standard)', 'CSAL000001', 'CCRG000001');
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange) VALUES ('CRNG000002', 'B (Premium)',  'CSAL000001', 'CCRG000002');
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange) VALUES ('CRNG000003', 'C (VIP)',      'CSAL000001', 'CCRG000003');

-- SALLE 2
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange) VALUES ('CRNG000004', 'A (Standard)', 'CSAL000002', 'CCRG000001');
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange) VALUES ('CRNG000005', 'B (Standard)', 'CSAL000002', 'CCRG000001');
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange) VALUES ('CRNG000006', 'C (Premium)',  'CSAL000002', 'CCRG000002');
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange) VALUES ('CRNG000007', 'D (VIP)',      'CSAL000002', 'CCRG000003');

-- SALLE 3
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange) VALUES ('CRNG000008', 'A (Standard)', 'CSAL000003', 'CCRG000001');
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange) VALUES ('CRNG000009', 'B (Standard)', 'CSAL000003', 'CCRG000001');
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange) VALUES ('CRNG000010', 'C (Premium)',  'CSAL000003', 'CCRG000002');

-- SALLE 4
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange) VALUES ('CRNG000011', 'A (Standard)', 'CSAL000004', 'CCRG000001');
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange) VALUES ('CRNG000012', 'B (Premium)',  'CSAL000004', 'CCRG000002');
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange) VALUES ('CRNG000013', 'C (VIP)',      'CSAL000004', 'CCRG000003');

-- ============================================================================
-- 7. Places
--    SALLE 1 : A=10, B=10, C=10   → 30 places
--    SALLE 2 : A=12, B=12, C=15, D=11 → 50 places
--    SALLE 3 : A=8,  B=8,  C=9    → 25 places
--    SALLE 4 : A=6,  B=7,  C=7    → 20 places
--    TOTAL : 125 places
-- ============================================================================

-- SALLE 1 — CRNG000001 (A, Standard, 10 places)
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000001', 'A1',  'CRNG000001');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000002', 'A2',  'CRNG000001');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000003', 'A3',  'CRNG000001');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000004', 'A4',  'CRNG000001');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000005', 'A5',  'CRNG000001');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000006', 'A6',  'CRNG000001');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000007', 'A7',  'CRNG000001');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000008', 'A8',  'CRNG000001');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000009', 'A9',  'CRNG000001');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000010', 'A10', 'CRNG000001');

-- SALLE 1 — CRNG000002 (B, Premium, 10 places)
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000011', 'B1',  'CRNG000002');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000012', 'B2',  'CRNG000002');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000013', 'B3',  'CRNG000002');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000014', 'B4',  'CRNG000002');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000015', 'B5',  'CRNG000002');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000016', 'B6',  'CRNG000002');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000017', 'B7',  'CRNG000002');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000018', 'B8',  'CRNG000002');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000019', 'B9',  'CRNG000002');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000020', 'B10', 'CRNG000002');

-- SALLE 1 — CRNG000003 (C, VIP, 10 places)
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000021', 'C1',  'CRNG000003');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000022', 'C2',  'CRNG000003');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000023', 'C3',  'CRNG000003');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000024', 'C4',  'CRNG000003');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000025', 'C5',  'CRNG000003');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000026', 'C6',  'CRNG000003');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000027', 'C7',  'CRNG000003');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000028', 'C8',  'CRNG000003');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000029', 'C9',  'CRNG000003');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000030', 'C10', 'CRNG000003');

-- SALLE 2 — CRNG000004 (A, Standard, 12 places)
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000031', 'A1',  'CRNG000004');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000032', 'A2',  'CRNG000004');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000033', 'A3',  'CRNG000004');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000034', 'A4',  'CRNG000004');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000035', 'A5',  'CRNG000004');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000036', 'A6',  'CRNG000004');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000037', 'A7',  'CRNG000004');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000038', 'A8',  'CRNG000004');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000039', 'A9',  'CRNG000004');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000040', 'A10', 'CRNG000004');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000041', 'A11', 'CRNG000004');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000042', 'A12', 'CRNG000004');

-- SALLE 2 — CRNG000005 (B, Standard, 12 places)
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000043', 'B1',  'CRNG000005');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000044', 'B2',  'CRNG000005');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000045', 'B3',  'CRNG000005');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000046', 'B4',  'CRNG000005');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000047', 'B5',  'CRNG000005');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000048', 'B6',  'CRNG000005');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000049', 'B7',  'CRNG000005');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000050', 'B8',  'CRNG000005');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000051', 'B9',  'CRNG000005');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000052', 'B10', 'CRNG000005');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000053', 'B11', 'CRNG000005');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000054', 'B12', 'CRNG000005');

-- SALLE 2 — CRNG000006 (C, Premium, 15 places)
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000055', 'C1',  'CRNG000006');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000056', 'C2',  'CRNG000006');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000057', 'C3',  'CRNG000006');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000058', 'C4',  'CRNG000006');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000059', 'C5',  'CRNG000006');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000060', 'C6',  'CRNG000006');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000061', 'C7',  'CRNG000006');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000062', 'C8',  'CRNG000006');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000063', 'C9',  'CRNG000006');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000064', 'C10', 'CRNG000006');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000065', 'C11', 'CRNG000006');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000066', 'C12', 'CRNG000006');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000067', 'C13', 'CRNG000006');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000068', 'C14', 'CRNG000006');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000069', 'C15', 'CRNG000006');

-- SALLE 2 — CRNG000007 (D, VIP, 11 places)
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000070', 'D1',  'CRNG000007');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000071', 'D2',  'CRNG000007');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000072', 'D3',  'CRNG000007');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000073', 'D4',  'CRNG000007');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000074', 'D5',  'CRNG000007');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000075', 'D6',  'CRNG000007');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000076', 'D7',  'CRNG000007');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000077', 'D8',  'CRNG000007');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000078', 'D9',  'CRNG000007');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000079', 'D10', 'CRNG000007');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000080', 'D11', 'CRNG000007');

-- SALLE 3 — CRNG000008 (A, Standard, 8 places)
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000081', 'A1', 'CRNG000008');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000082', 'A2', 'CRNG000008');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000083', 'A3', 'CRNG000008');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000084', 'A4', 'CRNG000008');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000085', 'A5', 'CRNG000008');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000086', 'A6', 'CRNG000008');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000087', 'A7', 'CRNG000008');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000088', 'A8', 'CRNG000008');

-- SALLE 3 — CRNG000009 (B, Standard, 8 places)
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000089', 'B1', 'CRNG000009');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000090', 'B2', 'CRNG000009');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000091', 'B3', 'CRNG000009');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000092', 'B4', 'CRNG000009');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000093', 'B5', 'CRNG000009');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000094', 'B6', 'CRNG000009');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000095', 'B7', 'CRNG000009');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000096', 'B8', 'CRNG000009');

-- SALLE 3 — CRNG000010 (C, Premium, 9 places)
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000097', 'C1', 'CRNG000010');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000098', 'C2', 'CRNG000010');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000099', 'C3', 'CRNG000010');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000100', 'C4', 'CRNG000010');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000101', 'C5', 'CRNG000010');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000102', 'C6', 'CRNG000010');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000103', 'C7', 'CRNG000010');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000104', 'C8', 'CRNG000010');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000105', 'C9', 'CRNG000010');

-- SALLE 4 — CRNG000011 (A, Standard, 6 places)
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000106', 'A1', 'CRNG000011');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000107', 'A2', 'CRNG000011');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000108', 'A3', 'CRNG000011');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000109', 'A4', 'CRNG000011');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000110', 'A5', 'CRNG000011');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000111', 'A6', 'CRNG000011');

-- SALLE 4 — CRNG000012 (B, Premium, 7 places)
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000112', 'B1', 'CRNG000012');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000113', 'B2', 'CRNG000012');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000114', 'B3', 'CRNG000012');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000115', 'B4', 'CRNG000012');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000116', 'B5', 'CRNG000012');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000117', 'B6', 'CRNG000012');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000118', 'B7', 'CRNG000012');

-- SALLE 4 — CRNG000013 (C, VIP, 7 places)
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000119', 'C1', 'CRNG000013');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000120', 'C2', 'CRNG000013');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000121', 'C3', 'CRNG000013');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000122', 'C4', 'CRNG000013');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000123', 'C5', 'CRNG000013');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000124', 'C6', 'CRNG000013');
INSERT INTO cinema_place (id, libelle, idrange) VALUES ('CPLA000125', 'C7', 'CRNG000013');

-- ============================================================================
-- 8. Clients (3 clients)
-- ============================================================================
INSERT INTO cinema_client (id, nom) VALUES ('CCLI000001', 'Jean');
INSERT INTO cinema_client (id, nom) VALUES ('CCLI000002', 'Jeannette');
INSERT INTO cinema_client (id, nom) VALUES ('CCLI000003', 'Billy Jean');

-- ============================================================================
-- 9. Films (10 films) + prix base (10000 à 30000)
--    Le prix base est stocké dans cinema_programme.prixbase (pas dans film).
--    On crée 10 programmes, chacun avec un film différent et un prix base
--    réparti entre 10000 et 30000.
-- ============================================================================

-- Films
INSERT INTO cinema_film (id, titre, description, duree) VALUES ('CFIL000001', 'Avatar: La Voie de l''Eau',       'Aventure sous-marine sur Pandora',        192);
INSERT INTO cinema_film (id, titre, description, duree) VALUES ('CFIL000002', 'Dune: Deuxième Partie',            'Suite de l''épopée de Paul Atréides',     166);
INSERT INTO cinema_film (id, titre, description, duree) VALUES ('CFIL000003', 'Oppenheimer',                      'Biographie du père de la bombe atomique', 180);
INSERT INTO cinema_film (id, titre, description, duree) VALUES ('CFIL000004', 'Barbie',                           'Comédie satirique sur la poupée',         114);
INSERT INTO cinema_film (id, titre, description, duree) VALUES ('CFIL000005', 'Oppenheimer: Making Of',           'Documentaire sur le tournage',             95);
INSERT INTO cinema_film (id, titre, description, duree) VALUES ('CFIL000006', 'Le Parrain',                       'Classique du cinéma sur la mafia',        175);
INSERT INTO cinema_film (id, titre, description, duree) VALUES ('CFIL000007', 'Interstellar',                     'Voyage spatial à travers un trou noir',  169);
INSERT INTO cinema_film (id, titre, description, duree) VALUES ('CFIL000008', 'Inception',                        'Voleur de rêves',                        148);
INSERT INTO cinema_film (id, titre, description, duree) VALUES ('CFIL000009', 'Pulp Fiction',                     'Récits croisés à Los Angeles',            154);
INSERT INTO cinema_film (id, titre, description, duree) VALUES ('CFIL000010', 'Spirited Away',                    'Animation japonaise dans un monde magique',125);

-- ============================================================================
-- 10. Catégories de film (M:N : certains 1 seule catégorie, d'autres plusieurs)
-- ============================================================================
-- CFIL000001 (Avatar) → Action, Drame (2)
INSERT INTO cinema_categorie_film (id, idcategorie, idfilm) VALUES ('CCAF000001', 'CCAT000001', 'CFIL000001');
INSERT INTO cinema_categorie_film (id, idcategorie, idfilm) VALUES ('CCAF000002', 'CCAT000003', 'CFIL000001');

-- CFIL000002 (Dune) → Action (1)
INSERT INTO cinema_categorie_film (id, idcategorie, idfilm) VALUES ('CCAF000003', 'CCAT000001', 'CFIL000002');

-- CFIL000003 (Oppenheimer) → Drame (1)
INSERT INTO cinema_categorie_film (id, idcategorie, idfilm) VALUES ('CCAF000004', 'CCAT000003', 'CFIL000003');

-- CFIL000004 (Barbie) → Comédie (1)
INSERT INTO cinema_categorie_film (id, idcategorie, idfilm) VALUES ('CCAF000005', 'CCAT000002', 'CFIL000004');

-- CFIL000005 (Oppenheimer Making Of) → Drame, Normal (2)
INSERT INTO cinema_categorie_film (id, idcategorie, idfilm) VALUES ('CCAF000006', 'CCAT000003', 'CFIL000005');
INSERT INTO cinema_categorie_film (id, idcategorie, idfilm) VALUES ('CCAF000007', 'CCAT000004', 'CFIL000005');

-- CFIL000006 (Le Parrain) → Drame (1)
INSERT INTO cinema_categorie_film (id, idcategorie, idfilm) VALUES ('CCAF000008', 'CCAT000003', 'CFIL000006');

-- CFIL000007 (Interstellar) → Action, Drame (2)
INSERT INTO cinema_categorie_film (id, idcategorie, idfilm) VALUES ('CCAF000009', 'CCAT000001', 'CFIL000007');
INSERT INTO cinema_categorie_film (id, idcategorie, idfilm) VALUES ('CCAF000010', 'CCAT000003', 'CFIL000007');

-- CFIL000008 (Inception) → Action (1)
INSERT INTO cinema_categorie_film (id, idcategorie, idfilm) VALUES ('CCAF000011', 'CCAT000001', 'CFIL000008');

-- CFIL000009 (Pulp Fiction) → Drame, Normal (2)
INSERT INTO cinema_categorie_film (id, idcategorie, idfilm) VALUES ('CCAF000012', 'CCAT000003', 'CFIL000009');
INSERT INTO cinema_categorie_film (id, idcategorie, idfilm) VALUES ('CCAF000013', 'CCAT000004', 'CFIL000009');

-- CFIL000010 (Spirited Away) → Normal (1)
INSERT INTO cinema_categorie_film (id, idcategorie, idfilm) VALUES ('CCAF000014', 'CCAT000004', 'CFIL000010');

-- ============================================================================
-- 11. Programmes (10 séances, prix base de 10 000 à 30 000)
--     Répartis sur les 4 salles, sur 3 jours à venir
-- ============================================================================

-- J+1
INSERT INTO cinema_programme (id, idfilm, idsalle, datedebut, heuredebut, datefin, heurefin, prixbase, etat, iduser)
VALUES ('CPRG000001', 'CFIL000001', 'CSAL000001', TRUNC(SYSDATE)+1, '14:00', TRUNC(SYSDATE)+1, '17:12', 25000, 1, '0');

INSERT INTO cinema_programme (id, idfilm, idsalle, datedebut, heuredebut, datefin, heurefin, prixbase, etat, iduser)
VALUES ('CPRG000002', 'CFIL000002', 'CSAL000002', TRUNC(SYSDATE)+1, '18:00', TRUNC(SYSDATE)+1, '20:46', 28000, 1, '0');

INSERT INTO cinema_programme (id, idfilm, idsalle, datedebut, heuredebut, datefin, heurefin, prixbase, etat, iduser)
VALUES ('CPRG000003', 'CFIL000003', 'CSAL000003', TRUNC(SYSDATE)+1, '20:00', TRUNC(SYSDATE)+1, '23:00', 30000, 1, '0');

-- J+2
INSERT INTO cinema_programme (id, idfilm, idsalle, datedebut, heuredebut, datefin, heurefin, prixbase, etat, iduser)
VALUES ('CPRG000004', 'CFIL000004', 'CSAL000004', TRUNC(SYSDATE)+2, '10:00', TRUNC(SYSDATE)+2, '11:54', 10000, 1, '0');

INSERT INTO cinema_programme (id, idfilm, idsalle, datedebut, heuredebut, datefin, heurefin, prixbase, etat, iduser)
VALUES ('CPRG000005', 'CFIL000005', 'CSAL000001', TRUNC(SYSDATE)+2, '13:00', TRUNC(SYSDATE)+2, '14:35', 12000, 1, '0');

INSERT INTO cinema_programme (id, idfilm, idsalle, datedebut, heuredebut, datefin, heurefin, prixbase, etat, iduser)
VALUES ('CPRG000006', 'CFIL000006', 'CSAL000002', TRUNC(SYSDATE)+2, '16:00', TRUNC(SYSDATE)+2, '18:55', 22000, 1, '0');

INSERT INTO cinema_programme (id, idfilm, idsalle, datedebut, heuredebut, datefin, heurefin, prixbase, etat, iduser)
VALUES ('CPRG000007', 'CFIL000007', 'CSAL000003', TRUNC(SYSDATE)+2, '19:00', TRUNC(SYSDATE)+2, '21:49', 27000, 1, '0');

-- J+3
INSERT INTO cinema_programme (id, idfilm, idsalle, datedebut, heuredebut, datefin, heurefin, prixbase, etat, iduser)
VALUES ('CPRG000008', 'CFIL000008', 'CSAL000004', TRUNC(SYSDATE)+3, '11:00', TRUNC(SYSDATE)+3, '13:28', 15000, 1, '0');

INSERT INTO cinema_programme (id, idfilm, idsalle, datedebut, heuredebut, datefin, heurefin, prixbase, etat, iduser)
VALUES ('CPRG000009', 'CFIL000009', 'CSAL000001', TRUNC(SYSDATE)+3, '15:00', TRUNC(SYSDATE)+3, '17:34', 18000, 1, '0');

INSERT INTO cinema_programme (id, idfilm, idsalle, datedebut, heuredebut, datefin, heurefin, prixbase, etat, iduser)
VALUES ('CPRG000010', 'CFIL000010', 'CSAL000002', TRUNC(SYSDATE)+3, '20:00', TRUNC(SYSDATE)+3, '22:05', 20000, 1, '0');

-- ============================================================================
-- 12. Resynchronisation des séquences
--     Les INSERTs en dur n'ont PAS avancé les séquences Oracle.
--     Sans cette étape, la prochaine insertion via le framework écraserait
--     les IDs déjà créés.
-- ============================================================================

-- ============================================================
-- RESYNCHRONISATION DES SEQUENCES - ORACLE 11G
-- ============================================================

-- CATEGORIE : prochaine valeur = 5
ALTER SEQUENCE SEQ_CINEMA_CATEGORIE INCREMENT BY 4;
SELECT SEQ_CINEMA_CATEGORIE.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_CATEGORIE INCREMENT BY 1;

-- FILM : prochaine valeur = 11
ALTER SEQUENCE SEQ_CINEMA_FILM INCREMENT BY 10;
SELECT SEQ_CINEMA_FILM.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_FILM INCREMENT BY 1;

-- CATEGORIE_FILM : prochaine valeur = 15
ALTER SEQUENCE SEQ_CINEMA_CATEGORIE_FILM INCREMENT BY 14;
SELECT SEQ_CINEMA_CATEGORIE_FILM.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_CATEGORIE_FILM INCREMENT BY 1;

-- SALLE : prochaine valeur = 5
ALTER SEQUENCE SEQ_CINEMA_SALLE INCREMENT BY 4;
SELECT SEQ_CINEMA_SALLE.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_SALLE INCREMENT BY 1;

-- CATEGORIE_RANGE : prochaine valeur = 4
ALTER SEQUENCE SEQ_CINEMA_CATEGORIE_RANGE INCREMENT BY 3;
SELECT SEQ_CINEMA_CATEGORIE_RANGE.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_CATEGORIE_RANGE INCREMENT BY 1;

-- HIST_PRIX_CAT_RANGE : prochaine valeur = 7
ALTER SEQUENCE SEQ_CIN_HIST_PRIX_CAT_RANGE INCREMENT BY 6;
SELECT SEQ_CIN_HIST_PRIX_CAT_RANGE.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CIN_HIST_PRIX_CAT_RANGE INCREMENT BY 1;

-- RANGE : prochaine valeur = 14
ALTER SEQUENCE SEQ_CINEMA_RANGE INCREMENT BY 13;
SELECT SEQ_CINEMA_RANGE.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_RANGE INCREMENT BY 1;

-- PLACE : prochaine valeur = 126
ALTER SEQUENCE SEQ_CINEMA_PLACE INCREMENT BY 125;
SELECT SEQ_CINEMA_PLACE.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_PLACE INCREMENT BY 1;

-- PROGRAMME : prochaine valeur = 11
ALTER SEQUENCE SEQ_CINEMA_PROGRAMME INCREMENT BY 10;
SELECT SEQ_CINEMA_PROGRAMME.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_PROGRAMME INCREMENT BY 1;

-- CLIENT : prochaine valeur = 4
ALTER SEQUENCE SEQ_CINEMA_CLIENT INCREMENT BY 3;
SELECT SEQ_CINEMA_CLIENT.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_CLIENT INCREMENT BY 1;

-- STATUT_RESERVATION : prochaine valeur = 5
ALTER SEQUENCE SEQ_CINEMA_STATUT_RESERVATION INCREMENT BY 4;
SELECT SEQ_CINEMA_STATUT_RESERVATION.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_STATUT_RESERVATION INCREMENT BY 1;

-- Les séquences suivantes restent à leur valeur initiale (1).
-- Aucun NEXTVAL n'est nécessaire si elles n'ont jamais été utilisées.

COMMIT;