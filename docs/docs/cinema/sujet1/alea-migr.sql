ALTER TABLE cinema_categorie_range ADD (
    prix NUMBER(15,2) DEFAULT 0 NOT NULL,
    CONSTRAINT CK_CCRG_PRIX CHECK (prix >= 0)
);


UPDATE cinema_categorie_range c
SET c.prix = NVL((
    SELECT MAX(ch.prixsupplement) KEEP (DENSE_RANK FIRST ORDER BY ch.datedebut DESC)
    FROM cinema_hist_prix_cat_range ch
    WHERE ch.idcategorierange = c.id
      AND ch.datedebut <= TRUNC(SYSDATE)
), 0);

-- 1. Ajout de la colonne prix figé sur le détail
ALTER TABLE cinema_detail_reservation ADD (
    prixunitaire NUMBER(15,2) DEFAULT 0 NOT NULL,
    CONSTRAINT CK_CDTR_PRIX CHECK (prixunitaire >= 0)
);

COMMIT;

-- 2. Vue CPL (simple jointure, pas de sous-requête)
CREATE OR REPLACE VIEW cinema_detail_reservation_cpl AS
SELECT dr.id,
       dr.idreservation,
       dr.idplace,
       pl.libelle      AS placelib,
       r.libelle       AS rangelib,
       s.libelle       AS sallelib,
       dr.prixunitaire AS prix,
       dr.etat,
       dr.iduser
FROM cinema_detail_reservation dr
JOIN cinema_place pl    ON pl.id = dr.idplace
JOIN cinema_range r     ON r.id  = pl.idrange
JOIN cinema_salle s     ON s.id  = r.idsalle;

COMMIT;
