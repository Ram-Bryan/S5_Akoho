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

-- reception par etape
-- ============================================================
-- SEQUENCE + FUNCTION for RECEPTIONLIEUX PKs
-- ============================================================
CREATE SEQUENCE SEQ_RECEPTIONLIEUX START WITH 1 INCREMENT BY 1 NOCACHE;

CREATE OR REPLACE FUNCTION GETSEQ_RECEPTIONLIEUX RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_RECEPTIONLIEUX.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/

-- ============================================================
-- TABLE
-- ============================================================
CREATE TABLE RECEPTIONLIEUX (
  ID           VARCHAR2(50)  PRIMARY KEY,
  IDLOT        VARCHAR2(100) NOT NULL,
  ORDRE        NUMBER(3)     NOT NULL,
  IDLIEU       VARCHAR2(50)  NOT NULL,
  DATY         DATE          NOT NULL,
  QTERECUS     NUMBER        NOT NULL,
  NBMORT       NUMBER        DEFAULT 0 NOT NULL,
  NBPERDU      NUMBER        DEFAULT 0 NOT NULL,
  HEUREDEPART  VARCHAR2(10),
  HEUREARRIVE  VARCHAR2(10),
  REMARQUE     VARCHAR2(500),
  ETAT         NUMBER        DEFAULT 1,
  IDUSER    VARCHAR2(50)  DEFAULT '0',
  CONSTRAINT RECLIEUX_LOT_FK   FOREIGN KEY (IDLOT)  REFERENCES LOT(ID),
  CONSTRAINT RECLIEUX_LIEU_FK  FOREIGN KEY (IDLIEU) REFERENCES LIEU(ID),
  CONSTRAINT RECLIEUX_ORDRE_UK UNIQUE (IDLOT, ORDRE),
  CONSTRAINT RECLIEUX_ORDRE_CK CHECK (ORDRE >= 1),
  CONSTRAINT RECLIEUX_QTE_CK   CHECK (QTERECUS >= 0 AND NBMORT >= 0 AND NBPERDU >= 0
                                      AND NBMORT + NBPERDU <= QTERECUS)
);

CREATE INDEX RECLIEUX_LOT_IDX ON RECEPTIONLIEUX(IDLOT);

-- ============================================================
-- VIEW: places eligible for "next point" (not airports)
-- ============================================================
CREATE OR REPLACE VIEW LIEU_ETAPE AS
  SELECT * FROM LIEU WHERE TYPELIEU IN ('ARRET_ROUTE','ENTREPOT');

-- ============================================================
-- SEED — at least one ARRET_ROUTE and one ENTREPOT so the view is not empty
-- (adapt ids/libelles to your real locations)
-- ============================================================
INSERT INTO LIEU (ID, LIBELLE, TYPELIEU, IDFERME, ETAT, IDUSER)
  VALUES ('LIE000002', 'Ambatolampy (arrêt)', 'ARRET_ROUTE', NULL, 1, '0');
INSERT INTO LIEU (ID, LIBELLE, TYPELIEU, IDFERME, ETAT, IDUSER)
  VALUES ('LIE000003', 'Antsirabe (arrêt)', 'ARRET_ROUTE', NULL, 1, '0');
INSERT INTO LIEU (ID, LIBELLE, TYPELIEU, IDFERME, ETAT, IDUSER)
  VALUES ('LIE000004', 'Entrepôt ferme principal', 'ENTREPOT', NULL, 1, '0');
  
COMMIT;

