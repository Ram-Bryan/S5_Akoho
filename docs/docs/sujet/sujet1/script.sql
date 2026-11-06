-- lien bl et lot
ALTER TABLE LOT ADD IDBLFILLE VARCHAR2(100);

ALTER TABLE LOT ADD CONSTRAINT LOT_BLFILLE_FK
  FOREIGN KEY (IDBLFILLE) REFERENCES AS_BONDELIVRAISON_FILLE(ID);

CREATE INDEX LOT_IDBLFILLE_IDX ON LOT(IDBLFILLE);

-- reception a l'aeroport
-- ============================================================
-- 1. LIEU — reception/departure points (airport, stops, warehouse)
-- ============================================================

CREATE TABLE LIEU (
    ID        VARCHAR2(50)  PRIMARY KEY,
    LIBELLE   VARCHAR2(255) NOT NULL,
    TYPELIEU  VARCHAR2(30)  NOT NULL,
    IDFERME   VARCHAR2(100),
    ETAT      NUMBER(2)     DEFAULT 1 NOT NULL,
    IDUSER    VARCHAR2(50)  DEFAULT '0',
    CONSTRAINT CK_LIEU_TYPE CHECK (TYPELIEU IN ('AEROPORT','ARRET_ROUTE','ENTREPOT')),
    CONSTRAINT LIEU_FERME_FK FOREIGN KEY (IDFERME) REFERENCES MAGASIN2(ID)
);

CREATE SEQUENCE SEQ_LIEU START WITH 1 INCREMENT BY 1 NOCACHE;

CREATE OR REPLACE FUNCTION getseq_lieu RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_LIEU.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/

CREATE INDEX IDX_LIEU_TYPE ON LIEU(TYPELIEU, ETAT);

-- ============================================================
-- 2. View for airport-only dropdowns
-- ============================================================

CREATE OR REPLACE VIEW LIEU_AEROPORT AS 
  SELECT * FROM LIEU WHERE TYPELIEU = 'AEROPORT';

-- ============================================================
-- 3. Reception — add location + morts/perdus
-- ============================================================

ALTER TABLE RECEPTIONPOUSSINAEROPORT ADD (
    IDLIEU   VARCHAR2(50),
    NBMORT   NUMBER DEFAULT 0 NOT NULL,
    NBPERDU  NUMBER DEFAULT 0 NOT NULL,
    CONSTRAINT RECPOUSS_LIEU_FK FOREIGN KEY (IDLIEU) REFERENCES LIEU(ID)
);

-- ============================================================
-- 4. Backfill: existing rows get NBMORT = 0 / NBPERDU = 0
--    (the DEFAULT only applies on INSERT, not on existing rows)
-- ============================================================

UPDATE RECEPTIONPOUSSINAEROPORT SET NBMORT  = 0 WHERE NBMORT  IS NULL;
UPDATE RECEPTIONPOUSSINAEROPORT SET NBPERDU = 0 WHERE NBPERDU IS NULL;

INSERT INTO LIEU (ID, LIBELLE, TYPELIEU, IDFERME, ETAT, IDUSER) 
VALUES ('LIE000001', 'IVATO', 'AEROPORT', NULL, 1, '0');

COMMIT;
