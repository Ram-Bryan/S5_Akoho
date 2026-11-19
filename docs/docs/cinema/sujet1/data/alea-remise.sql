-- ============================================================================
-- CINEMA — Table des remises (SANS traçabilité sur les détails)
-- ============================================================================

DROP TABLE cinema_remise             CASCADE CONSTRAINTS;
DROP SEQUENCE SEQ_CINEMA_REMISE;
DROP FUNCTION getseq_cinema_remise;

CREATE TABLE cinema_remise (
    id                VARCHAR2(50) PRIMARY KEY,
    idcategorie       VARCHAR2(50) NOT NULL,
    seuilplace        NUMBER(3)    NOT NULL,
    pourcentage       NUMBER(5,2)  NOT NULL,
    idcategorietarget VARCHAR2(50) NOT NULL,
    datydebut         DATE         NOT NULL,
    datyfin           DATE,
    CONSTRAINT CINEMA_REMISE_CAT_FK    FOREIGN KEY (idcategorie)       REFERENCES cinema_categorie_range(id),
    CONSTRAINT CINEMA_REMISE_TARGET_FK FOREIGN KEY (idcategorietarget) REFERENCES cinema_categorie_range(id),
    CONSTRAINT CK_CINEMA_REMISE_PCT    CHECK (pourcentage >= 0 AND pourcentage <= 100),
    CONSTRAINT CK_CINEMA_REMISE_CAT_DIFF
    CHECK (idcategorie <> idcategorietarget)
);

CREATE SEQUENCE SEQ_CINEMA_REMISE START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE OR REPLACE FUNCTION getseq_cinema_remise RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_CINEMA_REMISE.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/


-- =========================================================
-- GROUPE 1 : Plusieurs seuils pour une même cible
-- =========================================================

-- Standard >= 2 : 20% sur Confort
INSERT INTO cinema_remise
VALUES ('CREM000001', 'CCRG000001', 2, 20, 'CCRG000002', TRUNC(SYSDATE)-30, NULL);

-- Standard >= 4 : 70% sur Confort
INSERT INTO cinema_remise
VALUES ('CREM000002', 'CCRG000001', 4, 70, 'CCRG000002', TRUNC(SYSDATE)-30, NULL);

-- Standard >= 5 : 10% sur Confort
-- Important : 10% est volontairement inférieur à 70%.
INSERT INTO cinema_remise
VALUES ('CREM000003', 'CCRG000001', 5, 10, 'CCRG000002', TRUNC(SYSDATE)-30, NULL);

-- =========================================================
-- GROUPE 2 : Même source, cibles différentes
-- =========================================================

-- Standard >= 2 : 20% sur VIP
INSERT INTO cinema_remise
VALUES ('CREM000004', 'CCRG000001', 2, 20, 'CCRG000003', TRUNC(SYSDATE)-30, NULL);

-- Standard >= 4 : 80% sur VIP
INSERT INTO cinema_remise
VALUES ('CREM000005', 'CCRG000001', 4, 80, 'CCRG000003', TRUNC(SYSDATE)-30, NULL);

-- =========================================================
-- GROUPE 3 : Plusieurs catégories sources vers une cible
-- =========================================================

-- VIP >= 2 : 50% sur Confort
INSERT INTO cinema_remise
VALUES ('CREM000006', 'CCRG000003', 2, 50, 'CCRG000002', TRUNC(SYSDATE)-30, NULL);

-- Confort >= 3 : 30% sur VIP
INSERT INTO cinema_remise
VALUES ('CREM000007', 'CCRG000002', 3, 30, 'CCRG000003', TRUNC(SYSDATE)-30, NULL);

-- VIP >= 5 : 40% sur Confort
-- Une deuxième règle provenant de VIP vers Confort.
INSERT INTO cinema_remise
VALUES ('CREM000008', 'CCRG000003', 5, 40, 'CCRG000002', TRUNC(SYSDATE)-30, NULL);

-- =========================================================
-- GROUPE 4 : Plusieurs sources vers Standard
-- =========================================================

-- VIP >= 5 : 60% sur Standard
INSERT INTO cinema_remise
VALUES ('CREM000009', 'CCRG000003', 5, 60, 'CCRG000001', TRUNC(SYSDATE)-30, NULL);

-- Confort >= 3 : 50% sur Standard
INSERT INTO cinema_remise
VALUES ('CREM000010', 'CCRG000002', 3, 50, 'CCRG000001', TRUNC(SYSDATE)-30, NULL);

-- =========================================================
-- GROUPE 5 : Règles temporelles
-- =========================================================

-- Règle expirée : ne doit pas être appliquée
INSERT INTO cinema_remise
VALUES ('CREM000011', 'CCRG000003', 1, 100, 'CCRG000002',
        TRUNC(SYSDATE)-30, NULL);

-- Règle future : ne doit pas être appliquée aujourd'hui
INSERT INTO cinema_remise
VALUES ('CREM000012', 'CCRG000003', 1, 100, 'CCRG000002',
        TRUNC(SYSDATE)-30, NULL);


COMMIT;