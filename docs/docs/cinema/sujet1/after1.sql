-- ============================================================================
-- Ajout de cinema_programme.prix (cache dénormalisé du prix courant)
-- ============================================================================

-- 1. Ajouter la colonne
ALTER TABLE cinema_programme ADD (
    prix NUMBER(15,2) DEFAULT 0 NOT NULL,
    CONSTRAINT CK_CPRG_PRIX CHECK (prix >= 0)
);

-- 2. Migrer les prix existants depuis l'historique
UPDATE cinema_programme p
SET p.prix = NVL((
    SELECT h.prix
    FROM cinema_historique_prix_programme h
    WHERE h.idprogramme = p.id
      AND h.datedebut = (
          SELECT MAX(h2.datedebut)
          FROM cinema_historique_prix_programme h2
          WHERE h2.idprogramme = p.id
            AND h2.datedebut <= TRUNC(SYSDATE)
      )
), 0);

COMMIT;

=