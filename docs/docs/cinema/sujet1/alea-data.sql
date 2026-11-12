-- ============================================================================
-- CINEMA — Données de test (basées sur le planning fourni)
-- ============================================================================

-- ============================================================================
-- 1. Catégories de film (5)
-- ============================================================================
INSERT INTO cinema_categorie (id, libelle) VALUES ('CCAT000001', 'Aventure');
INSERT INTO cinema_categorie (id, libelle) VALUES ('CCAT000002', 'Thriller');
INSERT INTO cinema_categorie (id, libelle) VALUES ('CCAT000003', 'Animation');
INSERT INTO cinema_categorie (id, libelle) VALUES ('CCAT000004', 'Tous publics');
INSERT INTO cinema_categorie (id, libelle) VALUES ('CCAT000005', 'Normal');

-- ============================================================================
-- 2. Catégories de range (3) + prix (dénormalisé)
-- ============================================================================
INSERT INTO cinema_categorie_range (id, libelle, prix) VALUES ('CCRG000001', 'Standard', 15000);
INSERT INTO cinema_categorie_range (id, libelle, prix) VALUES ('CCRG000002', 'Confort',  20000);
INSERT INTO cinema_categorie_range (id, libelle, prix) VALUES ('CCRG000003', 'VIP',      30000);

-- ============================================================================
-- 3. Historique prix catégories range (ligne initiale)
-- ============================================================================
INSERT INTO cinema_hist_prix_cat_range (id, idcategorierange, prixsupplement, datedebut)
VALUES ('CHPR000001', 'CCRG000001', 15000, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_cat_range (id, idcategorierange, prixsupplement, datedebut)
VALUES ('CHPR000002', 'CCRG000002', 20000, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_cat_range (id, idcategorierange, prixsupplement, datedebut)
VALUES ('CHPR000003', 'CCRG000003', 30000, TRUNC(SYSDATE));

-- ============================================================================
-- 4. Statuts réservation
-- ============================================================================
INSERT INTO cinema_statut_reservation (id, libelle) VALUES ('CSTR000001', 'reserve');
INSERT INTO cinema_statut_reservation (id, libelle) VALUES ('CSTR000002', 'annule');
INSERT INTO cinema_statut_reservation (id, libelle) VALUES ('CSTR000003', 'expire');

-- ============================================================================
-- 5. Salles (3)
-- ============================================================================
INSERT INTO cinema_salle (id, libelle) VALUES ('CSAL000001', 'Salle A');
INSERT INTO cinema_salle (id, libelle) VALUES ('CSAL000002', 'Salle B');
INSERT INTO cinema_salle (id, libelle) VALUES ('CSAL000003', 'Salle C');

-- ============================================================================
-- 6. Ranges (15)
-- ============================================================================
-- Salle A
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange, etat, iduser) VALUES ('CRNG000001', 'A (Standard)', 'CSAL000001', 'CCRG000001', 1, '0');
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange, etat, iduser) VALUES ('CRNG000002', 'B (Standard)', 'CSAL000001', 'CCRG000001', 1, '0');
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange, etat, iduser) VALUES ('CRNG000003', 'C (Confort)',  'CSAL000001', 'CCRG000002', 1, '0');
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange, etat, iduser) VALUES ('CRNG000004', 'D (VIP)',      'CSAL000001', 'CCRG000003', 1, '0');

-- Salle B
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange, etat, iduser) VALUES ('CRNG000005', 'A (Standard)', 'CSAL000002', 'CCRG000001', 1, '0');
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange, etat, iduser) VALUES ('CRNG000006', 'B (Standard)', 'CSAL000002', 'CCRG000001', 1, '0');
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange, etat, iduser) VALUES ('CRNG000007', 'C (Confort)',  'CSAL000002', 'CCRG000002', 1, '0');
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange, etat, iduser) VALUES ('CRNG000008', 'D (VIP)',      'CSAL000002', 'CCRG000003', 1, '0');

-- Salle C
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange, etat, iduser) VALUES ('CRNG000009', 'A (Standard)', 'CSAL000003', 'CCRG000001', 1, '0');
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange, etat, iduser) VALUES ('CRNG000010', 'B (Standard)', 'CSAL000003', 'CCRG000001', 1, '0');
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange, etat, iduser) VALUES ('CRNG000011', 'C (Confort)',  'CSAL000003', 'CCRG000002', 1, '0');
INSERT INTO cinema_range (id, libelle, idsalle, idcategorierange, etat, iduser) VALUES ('CRNG000012', 'D (VIP)',      'CSAL000003', 'CCRG000003', 1, '0');

-- ============================================================================
-- 7. Places (60 = 4 ranges × 5 places × 3 salles)
-- ============================================================================
-- SALLE A
-- Range A (Standard)
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000001', 'A1', 'CRNG000001', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000002', 'A2', 'CRNG000001', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000003', 'A3', 'CRNG000001', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000004', 'A4', 'CRNG000001', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000005', 'A5', 'CRNG000001', 1, '0');
-- Range B (Standard)
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000006', 'B1', 'CRNG000002', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000007', 'B2', 'CRNG000002', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000008', 'B3', 'CRNG000002', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000009', 'B4', 'CRNG000002', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000010', 'B5', 'CRNG000002', 1, '0');
-- Range C (Confort)
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000011', 'C1', 'CRNG000003', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000012', 'C2', 'CRNG000003', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000013', 'C3', 'CRNG000003', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000014', 'C4', 'CRNG000003', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000015', 'C5', 'CRNG000003', 1, '0');
-- Range D (VIP)
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000016', 'D1', 'CRNG000004', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000017', 'D2', 'CRNG000004', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000018', 'D3', 'CRNG000004', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000019', 'D4', 'CRNG000004', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000020', 'D5', 'CRNG000004', 1, '0');

-- SALLE B
-- Range A (Standard)
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000021', 'A1', 'CRNG000005', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000022', 'A2', 'CRNG000005', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000023', 'A3', 'CRNG000005', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000024', 'A4', 'CRNG000005', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000025', 'A5', 'CRNG000005', 1, '0');
-- Range B (Standard)
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000026', 'B1', 'CRNG000006', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000027', 'B2', 'CRNG000006', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000028', 'B3', 'CRNG000006', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000029', 'B4', 'CRNG000006', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000030', 'B5', 'CRNG000006', 1, '0');
-- Range C (Confort)
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000031', 'C1', 'CRNG000007', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000032', 'C2', 'CRNG000007', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000033', 'C3', 'CRNG000007', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000034', 'C4', 'CRNG000007', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000035', 'C5', 'CRNG000007', 1, '0');
-- Range D (VIP)
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000036', 'D1', 'CRNG000008', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000037', 'D2', 'CRNG000008', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000038', 'D3', 'CRNG000008', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000039', 'D4', 'CRNG000008', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000040', 'D5', 'CRNG000008', 1, '0');

-- SALLE C
-- Range A (Standard)
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000041', 'A1', 'CRNG000009', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000042', 'A2', 'CRNG000009', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000043', 'A3', 'CRNG000009', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000044', 'A4', 'CRNG000009', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000045', 'A5', 'CRNG000009', 1, '0');
-- Range B (Standard)
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000046', 'B1', 'CRNG000010', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000047', 'B2', 'CRNG000010', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000048', 'B3', 'CRNG000010', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000049', 'B4', 'CRNG000010', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000050', 'B5', 'CRNG000010', 1, '0');
-- Range C (Confort)
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000051', 'C1', 'CRNG000011', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000052', 'C2', 'CRNG000011', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000053', 'C3', 'CRNG000011', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000054', 'C4', 'CRNG000011', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000055', 'C5', 'CRNG000011', 1, '0');
-- Range D (VIP)
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000056', 'D1', 'CRNG000012', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000057', 'D2', 'CRNG000012', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000058', 'D3', 'CRNG000012', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000059', 'D4', 'CRNG000012', 1, '0');
INSERT INTO cinema_place (id, libelle, idrange, etat, iduser) VALUES ('CPLA000060', 'D5', 'CRNG000012', 1, '0');

-- ============================================================================
-- 8. Films (3)
-- ============================================================================
INSERT INTO cinema_film (id, titre, description, duree) VALUES ('CFIL000001', 'Les Gardiens du Baobab', 'Réalisateur: Hery Rakotomalala | Aventure | Tous publics', 115);
INSERT INTO cinema_film (id, titre, description, duree) VALUES ('CFIL000002', 'Nuit sur Analamanga', 'Réalisateur: Fanja Andriamanana | Thriller | -12', 105);
INSERT INTO cinema_film (id, titre, description, duree) VALUES ('CFIL000003', 'Le Petit Lémurien', 'Réalisateur: Tojo Randrianarisoa | Animation | Tous publics', 90);

-- ============================================================================
-- 9. Catégories de film (M:N)
-- ============================================================================
-- Les Gardiens du Baobab -> Aventure, Tous publics
INSERT INTO cinema_categorie_film (id, idcategorie, idfilm) VALUES ('CCAF000001', 'CCAT000001', 'CFIL000001');
INSERT INTO cinema_categorie_film (id, idcategorie, idfilm) VALUES ('CCAF000002', 'CCAT000004', 'CFIL000001');

-- Nuit sur Analamanga -> Thriller
INSERT INTO cinema_categorie_film (id, idcategorie, idfilm) VALUES ('CCAF000003', 'CCAT000002', 'CFIL000002');

-- Le Petit Lémurien -> Animation, Tous publics
INSERT INTO cinema_categorie_film (id, idcategorie, idfilm) VALUES ('CCAF000004', 'CCAT000003', 'CFIL000003');
INSERT INTO cinema_categorie_film (id, idcategorie, idfilm) VALUES ('CCAF000005', 'CCAT000004', 'CFIL000003');

-- ============================================================================
-- 10. Clients (2)
-- ============================================================================
INSERT INTO cinema_client (id, nom) VALUES ('CCLI000001', 'Jean');
INSERT INTO cinema_client (id, nom) VALUES ('CCLI000002', 'Jeannette');

-- ============================================================================
-- 11. Programmes (19 séances du 16/11/2026 au 30/11/2026)
-- PRIX de base = 10000 Ar (supposé, tu peux changer)
-- ============================================================================
-- Note : 16/11/2026 est un Lundi.
-- Lundi 16/11
INSERT INTO cinema_programme (id, idsalle, datedebut, heuredebut, prix, etat, iduser) VALUES ('CPRG000001', 'CSAL000001', TO_DATE('2026-11-16','YYYY-MM-DD'), '14:00', 0, 1, '0');
INSERT INTO cinema_programme (id, idsalle, datedebut, heuredebut, prix, etat, iduser) VALUES ('CPRG000002', 'CSAL000001', TO_DATE('2026-11-16','YYYY-MM-DD'), '20:00', 0, 1, '0');
INSERT INTO cinema_programme (id, idsalle, datedebut, heuredebut, prix, etat, iduser) VALUES ('CPRG000003', 'CSAL000002', TO_DATE('2026-11-16','YYYY-MM-DD'), '14:00', 0, 1, '0');
INSERT INTO cinema_programme (id, idsalle, datedebut, heuredebut, prix, etat, iduser) VALUES ('CPRG000004', 'CSAL000002', TO_DATE('2026-11-16','YYYY-MM-DD'), '17:00', 0, 1, '0');
INSERT INTO cinema_programme (id, idsalle, datedebut, heuredebut, prix, etat, iduser) VALUES ('CPRG000005', 'CSAL000002', TO_DATE('2026-11-16','YYYY-MM-DD'), '20:00', 0, 1, '0');

-- Mardi 17/11
INSERT INTO cinema_programme (id, idsalle, datedebut, heuredebut, prix, etat, iduser) VALUES ('CPRG000006', 'CSAL000002', TO_DATE('2026-11-17','YYYY-MM-DD'), '18:00', 0, 1, '0');
INSERT INTO cinema_programme (id, idsalle, datedebut, heuredebut, prix, etat, iduser) VALUES ('CPRG000007', 'CSAL000003', TO_DATE('2026-11-17','YYYY-MM-DD'), '14:00', 0, 1, '0');

-- Mercredi 18/11
INSERT INTO cinema_programme (id, idsalle, datedebut, heuredebut, prix, etat, iduser) VALUES ('CPRG000008', 'CSAL000003', TO_DATE('2026-11-18','YYYY-MM-DD'), '14:00', 0, 1, '0');
INSERT INTO cinema_programme (id, idsalle, datedebut, heuredebut, prix, etat, iduser) VALUES ('CPRG000009', 'CSAL000003', TO_DATE('2026-11-18','YYYY-MM-DD'), '17:00', 0, 1, '0');

-- Jeudi 19/11
INSERT INTO cinema_programme (id, idsalle, datedebut, heuredebut, prix, etat, iduser) VALUES ('CPRG000010', 'CSAL000001', TO_DATE('2026-11-19','YYYY-MM-DD'), '14:00', 0, 1, '0');
INSERT INTO cinema_programme (id, idsalle, datedebut, heuredebut, prix, etat, iduser) VALUES ('CPRG000011', 'CSAL000001', TO_DATE('2026-11-19','YYYY-MM-DD'), '17:00', 0, 1, '0');

-- Vendredi 20/11
INSERT INTO cinema_programme (id, idsalle, datedebut, heuredebut, prix, etat, iduser) VALUES ('CPRG000012', 'CSAL000002', TO_DATE('2026-11-20','YYYY-MM-DD'), '17:00', 0, 1, '0');
INSERT INTO cinema_programme (id, idsalle, datedebut, heuredebut, prix, etat, iduser) VALUES ('CPRG000013', 'CSAL000002', TO_DATE('2026-11-20','YYYY-MM-DD'), '20:00', 0, 1, '0');

-- Samedi 21/11
INSERT INTO cinema_programme (id, idsalle, datedebut, heuredebut, prix, etat, iduser) VALUES ('CPRG000014', 'CSAL000001', TO_DATE('2026-11-21','YYYY-MM-DD'), '14:00', 0, 1, '0');
INSERT INTO cinema_programme (id, idsalle, datedebut, heuredebut, prix, etat, iduser) VALUES ('CPRG000015', 'CSAL000001', TO_DATE('2026-11-21','YYYY-MM-DD'), '20:00', 0, 1, '0');
INSERT INTO cinema_programme (id, idsalle, datedebut, heuredebut, prix, etat, iduser) VALUES ('CPRG000016', 'CSAL000003', TO_DATE('2026-11-21','YYYY-MM-DD'), '10:00', 0, 1, '0');
INSERT INTO cinema_programme (id, idsalle, datedebut, heuredebut, prix, etat, iduser) VALUES ('CPRG000017', 'CSAL000003', TO_DATE('2026-11-21','YYYY-MM-DD'), '14:00', 0, 1, '0');
INSERT INTO cinema_programme (id, idsalle, datedebut, heuredebut, prix, etat, iduser) VALUES ('CPRG000018', 'CSAL000003', TO_DATE('2026-11-21','YYYY-MM-DD'), '17:00', 0, 1, '0');

-- Dimanche 22/11
INSERT INTO cinema_programme (id, idsalle, datedebut, heuredebut, prix, etat, iduser) VALUES ('CPRG000019', 'CSAL000001', TO_DATE('2026-11-22','YYYY-MM-DD'), '15:00', 0, 1, '0');

-- ============================================================================
-- 12. Détails programme (1 film par programme — les séances ne sont pas multiples)
-- ============================================================================
INSERT INTO cinema_detail_programme (id, idprogramme, idfilm, rang, etat, iduser) VALUES ('CDPR000001', 'CPRG000001', 'CFIL000001', 1, 1, '0'); -- Les Gardiens du Baobab
INSERT INTO cinema_detail_programme (id, idprogramme, idfilm, rang, etat, iduser) VALUES ('CDPR000002', 'CPRG000002', 'CFIL000001', 1, 1, '0');
INSERT INTO cinema_detail_programme (id, idprogramme, idfilm, rang, etat, iduser) VALUES ('CDPR000003', 'CPRG000003', 'CFIL000002', 1, 1, '0'); -- Nuit sur Analamanga
INSERT INTO cinema_detail_programme (id, idprogramme, idfilm, rang, etat, iduser) VALUES ('CDPR000004', 'CPRG000004', 'CFIL000002', 1, 1, '0');
INSERT INTO cinema_detail_programme (id, idprogramme, idfilm, rang, etat, iduser) VALUES ('CDPR000005', 'CPRG000005', 'CFIL000002', 1, 1, '0');
INSERT INTO cinema_detail_programme (id, idprogramme, idfilm, rang, etat, iduser) VALUES ('CDPR000006', 'CPRG000006', 'CFIL000002', 1, 1, '0');
INSERT INTO cinema_detail_programme (id, idprogramme, idfilm, rang, etat, iduser) VALUES ('CDPR000007', 'CPRG000007', 'CFIL000003', 1, 1, '0'); -- Le Petit Lémurien
INSERT INTO cinema_detail_programme (id, idprogramme, idfilm, rang, etat, iduser) VALUES ('CDPR000008', 'CPRG000008', 'CFIL000003', 1, 1, '0');
INSERT INTO cinema_detail_programme (id, idprogramme, idfilm, rang, etat, iduser) VALUES ('CDPR000009', 'CPRG000009', 'CFIL000003', 1, 1, '0');
INSERT INTO cinema_detail_programme (id, idprogramme, idfilm, rang, etat, iduser) VALUES ('CDPR000010', 'CPRG000010', 'CFIL000001', 1, 1, '0');
INSERT INTO cinema_detail_programme (id, idprogramme, idfilm, rang, etat, iduser) VALUES ('CDPR000011', 'CPRG000011', 'CFIL000001', 1, 1, '0');
INSERT INTO cinema_detail_programme (id, idprogramme, idfilm, rang, etat, iduser) VALUES ('CDPR000012', 'CPRG000012', 'CFIL000002', 1, 1, '0');
INSERT INTO cinema_detail_programme (id, idprogramme, idfilm, rang, etat, iduser) VALUES ('CDPR000013', 'CPRG000013', 'CFIL000002', 1, 1, '0');
INSERT INTO cinema_detail_programme (id, idprogramme, idfilm, rang, etat, iduser) VALUES ('CDPR000014', 'CPRG000014', 'CFIL000001', 1, 1, '0');
INSERT INTO cinema_detail_programme (id, idprogramme, idfilm, rang, etat, iduser) VALUES ('CDPR000015', 'CPRG000015', 'CFIL000001', 1, 1, '0');
INSERT INTO cinema_detail_programme (id, idprogramme, idfilm, rang, etat, iduser) VALUES ('CDPR000016', 'CPRG000016', 'CFIL000003', 1, 1, '0');
INSERT INTO cinema_detail_programme (id, idprogramme, idfilm, rang, etat, iduser) VALUES ('CDPR000017', 'CPRG000017', 'CFIL000003', 1, 1, '0');
INSERT INTO cinema_detail_programme (id, idprogramme, idfilm, rang, etat, iduser) VALUES ('CDPR000018', 'CPRG000018', 'CFIL000003', 1, 1, '0');
INSERT INTO cinema_detail_programme (id, idprogramme, idfilm, rang, etat, iduser) VALUES ('CDPR000019', 'CPRG000019', 'CFIL000001', 1, 1, '0');

-- ============================================================================
-- 13. Historique prix programme (prix initial)
-- ============================================================================
INSERT INTO cinema_hist_prix_programme (id, idprogramme, prix, datedebut) VALUES ('CHPP000001', 'CPRG000001', 0, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_programme (id, idprogramme, prix, datedebut) VALUES ('CHPP000002', 'CPRG000002', 0, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_programme (id, idprogramme, prix, datedebut) VALUES ('CHPP000003', 'CPRG000003', 0, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_programme (id, idprogramme, prix, datedebut) VALUES ('CHPP000004', 'CPRG000004', 0, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_programme (id, idprogramme, prix, datedebut) VALUES ('CHPP000005', 'CPRG000005', 0, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_programme (id, idprogramme, prix, datedebut) VALUES ('CHPP000006', 'CPRG000006', 0, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_programme (id, idprogramme, prix, datedebut) VALUES ('CHPP000007', 'CPRG000007', 0, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_programme (id, idprogramme, prix, datedebut) VALUES ('CHPP000008', 'CPRG000008', 0, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_programme (id, idprogramme, prix, datedebut) VALUES ('CHPP000009', 'CPRG000009', 0, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_programme (id, idprogramme, prix, datedebut) VALUES ('CHPP000010', 'CPRG000010', 0, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_programme (id, idprogramme, prix, datedebut) VALUES ('CHPP000011', 'CPRG000011', 0, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_programme (id, idprogramme, prix, datedebut) VALUES ('CHPP000012', 'CPRG000012', 0, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_programme (id, idprogramme, prix, datedebut) VALUES ('CHPP000013', 'CPRG000013', 0, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_programme (id, idprogramme, prix, datedebut) VALUES ('CHPP000014', 'CPRG000014', 0, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_programme (id, idprogramme, prix, datedebut) VALUES ('CHPP000015', 'CPRG000015', 0, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_programme (id, idprogramme, prix, datedebut) VALUES ('CHPP000016', 'CPRG000016', 0, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_programme (id, idprogramme, prix, datedebut) VALUES ('CHPP000017', 'CPRG000017', 0, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_programme (id, idprogramme, prix, datedebut) VALUES ('CHPP000018', 'CPRG000018', 0, TRUNC(SYSDATE));
INSERT INTO cinema_hist_prix_programme (id, idprogramme, prix, datedebut) VALUES ('CHPP000019', 'CPRG000019', 0, TRUNC(SYSDATE));

COMMIT;

-- ============================================================================
-- 14. Resynchronisation des séquences
-- ============================================================================
ALTER SEQUENCE SEQ_CINEMA_CATEGORIE INCREMENT BY 5;
SELECT SEQ_CINEMA_CATEGORIE.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_CATEGORIE INCREMENT BY 1;

ALTER SEQUENCE SEQ_CINEMA_CATEGORIE_FILM INCREMENT BY 5;
SELECT SEQ_CINEMA_CATEGORIE_FILM.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_CATEGORIE_FILM INCREMENT BY 1;

ALTER SEQUENCE SEQ_CINEMA_CATEGORIE_RANGE INCREMENT BY 3;
SELECT SEQ_CINEMA_CATEGORIE_RANGE.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_CATEGORIE_RANGE INCREMENT BY 1;

ALTER SEQUENCE SEQ_CIN_HIST_PRIX_CAT_RANGE INCREMENT BY 3;
SELECT SEQ_CIN_HIST_PRIX_CAT_RANGE.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CIN_HIST_PRIX_CAT_RANGE INCREMENT BY 1;

ALTER SEQUENCE SEQ_CINEMA_SALLE INCREMENT BY 3;
SELECT SEQ_CINEMA_SALLE.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_SALLE INCREMENT BY 1;

ALTER SEQUENCE SEQ_CINEMA_RANGE INCREMENT BY 12;
SELECT SEQ_CINEMA_RANGE.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_RANGE INCREMENT BY 1;

ALTER SEQUENCE SEQ_CINEMA_PLACE INCREMENT BY 60;
SELECT SEQ_CINEMA_PLACE.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_PLACE INCREMENT BY 1;

ALTER SEQUENCE SEQ_CINEMA_FILM INCREMENT BY 3;
SELECT SEQ_CINEMA_FILM.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_FILM INCREMENT BY 1;

ALTER SEQUENCE SEQ_CINEMA_CLIENT INCREMENT BY 2;
SELECT SEQ_CINEMA_CLIENT.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_CLIENT INCREMENT BY 1;

ALTER SEQUENCE SEQ_CINEMA_STATUT_RESERVATION INCREMENT BY 3;
SELECT SEQ_CINEMA_STATUT_RESERVATION.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_STATUT_RESERVATION INCREMENT BY 1;

ALTER SEQUENCE SEQ_CINEMA_PROGRAMME INCREMENT BY 19;
SELECT SEQ_CINEMA_PROGRAMME.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_PROGRAMME INCREMENT BY 1;

ALTER SEQUENCE SEQ_CINEMA_DETAIL_PROGRAMME INCREMENT BY 19;
SELECT SEQ_CINEMA_DETAIL_PROGRAMME.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CINEMA_DETAIL_PROGRAMME INCREMENT BY 1;

ALTER SEQUENCE SEQ_CIN_HIST_PRIX_PROG INCREMENT BY 19;
SELECT SEQ_CIN_HIST_PRIX_PROG.NEXTVAL FROM DUAL;
ALTER SEQUENCE SEQ_CIN_HIST_PRIX_PROG INCREMENT BY 1;

COMMIT;