-- ============================================================================
-- CINEMA — Suppression du menu sidebar
-- ============================================================================

-- 1. Supprimer les sous-sous-menus (niveau 4)
DELETE FROM MENUDYNAMIQUE WHERE ID_PERE IN (
    SELECT ID FROM MENUDYNAMIQUE WHERE ID_PERE = 'MNDNCINEMA'
);

-- 2. Supprimer les sous-menus (niveau 3)
DELETE FROM MENUDYNAMIQUE WHERE ID_PERE = 'MNDNCINEMA';

-- 3. Supprimer le menu racine (niveau 2)
DELETE FROM MENUDYNAMIQUE WHERE ID = 'MNDNCINEMA';

COMMIT;