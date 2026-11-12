-- ============================================================================
-- CINEMA — DDL COMPLET (v4)
-- Oracle EE — préfixe cinema_ pour éviter les collisions
-- Colonnes : AUCUN underscore
-- Changements vs v3 :
--   - cinema_programme : + prix NUMBER(15,2) DEFAULT 0 NOT NULL (cache dénormalisé)
-- ============================================================================

-- ============================================================================
-- 1. cinema_categorie
-- ============================================================================
CREATE TABLE cinema_categorie (
    id          VARCHAR2(50)  PRIMARY KEY,
    libelle     VARCHAR2(255) NOT NULL
);

CREATE SEQUENCE SEQ_CINEMA_CATEGORIE START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE OR REPLACE FUNCTION getseq_cinema_categorie RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_CINEMA_CATEGORIE.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/

-- ============================================================================
-- 2. cinema_film
-- ============================================================================
CREATE TABLE cinema_film (
    id          VARCHAR2(50)  PRIMARY KEY,
    titre       VARCHAR2(255) NOT NULL,
    description VARCHAR2(2000),
    duree       NUMBER(5)     NOT NULL,
    CONSTRAINT CK_CINEMA_FILM_DUREE CHECK (duree > 0)
);

CREATE SEQUENCE SEQ_CINEMA_FILM START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE OR REPLACE FUNCTION getseq_cinema_film RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_CINEMA_FILM.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/

-- ============================================================================
-- 3. cinema_categorie_film  (M:N)
-- ============================================================================
CREATE TABLE cinema_categorie_film (
    id           VARCHAR2(50) PRIMARY KEY,
    idcategorie  VARCHAR2(50) NOT NULL,
    idfilm       VARCHAR2(50) NOT NULL,
    CONSTRAINT CCF_CATEGORIE_FK FOREIGN KEY (idcategorie) REFERENCES cinema_categorie(id),
    CONSTRAINT CCF_FILM_FK      FOREIGN KEY (idfilm)      REFERENCES cinema_film(id),
    CONSTRAINT CCF_UNIQUE_UK    UNIQUE (idcategorie, idfilm)
);

CREATE INDEX CCF_CATEGORIE_IDX ON cinema_categorie_film(idcategorie);
CREATE INDEX CCF_FILM_IDX      ON cinema_categorie_film(idfilm);

CREATE SEQUENCE SEQ_CINEMA_CATEGORIE_FILM START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE OR REPLACE FUNCTION getseq_cinema_categorie_film RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_CINEMA_CATEGORIE_FILM.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/

-- ============================================================================
-- 4. cinema_salle
-- ============================================================================
CREATE TABLE cinema_salle (
    id          VARCHAR2(50)  PRIMARY KEY,
    libelle     VARCHAR2(255) NOT NULL
);

CREATE SEQUENCE SEQ_CINEMA_SALLE START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE OR REPLACE FUNCTION getseq_cinema_salle RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_CINEMA_SALLE.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/

-- ============================================================================
-- 5. cinema_categorie_range
-- ============================================================================
CREATE TABLE cinema_categorie_range (
    id          VARCHAR2(50)  PRIMARY KEY,
    libelle     VARCHAR2(255) NOT NULL
);

CREATE SEQUENCE SEQ_CINEMA_CATEGORIE_RANGE START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE OR REPLACE FUNCTION getseq_cinema_categorie_range RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_CINEMA_CATEGORIE_RANGE.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/

-- ============================================================================
-- 6. cinema_hist_prix_cat_range
-- ============================================================================
CREATE TABLE cinema_hist_prix_cat_range (
    id                VARCHAR2(50)  PRIMARY KEY,
    idcategorierange  VARCHAR2(50)  NOT NULL,
    prixsupplement    NUMBER(15,2)  NOT NULL,
    datedebut         DATE          NOT NULL,
    CONSTRAINT CHPR_CATEG_RANGE_FK FOREIGN KEY (idcategorierange)
        REFERENCES cinema_categorie_range(id),
    CONSTRAINT CK_CHPR_PRIX CHECK (prixsupplement >= 0)
);

CREATE INDEX CHPR_CAT_RANGE_IDX ON cinema_hist_prix_cat_range(idcategorierange, datedebut);

CREATE SEQUENCE SEQ_CIN_HIST_PRIX_CAT_RANGE START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE OR REPLACE FUNCTION getseq_cin_hist_prix_cat_rng RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_CIN_HIST_PRIX_CAT_RANGE.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/

-- ============================================================================
-- 7. cinema_range
-- ============================================================================
CREATE TABLE cinema_range (
    id                VARCHAR2(50)  PRIMARY KEY,
    libelle           VARCHAR2(255) NOT NULL,
    idsalle           VARCHAR2(50)  NOT NULL,
    idcategorierange  VARCHAR2(50)  NOT NULL,
    etat              NUMBER(2)     DEFAULT 1 NOT NULL,
    iduser            VARCHAR2(50)  DEFAULT '0',
    CONSTRAINT CRNG_SALLE_FK       FOREIGN KEY (idsalle)
        REFERENCES cinema_salle(id),
    CONSTRAINT CRNG_CATEG_RANGE_FK FOREIGN KEY (idcategorierange)
        REFERENCES cinema_categorie_range(id)
);

CREATE INDEX CRNG_SALLE_IDX ON cinema_range(idsalle);

CREATE SEQUENCE SEQ_CINEMA_RANGE START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE OR REPLACE FUNCTION getseq_cinema_range RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_CINEMA_RANGE.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/

-- ============================================================================
-- 8. cinema_place
-- ============================================================================
CREATE TABLE cinema_place (
    id          VARCHAR2(50)  PRIMARY KEY,
    libelle     VARCHAR2(50)  NOT NULL,
    idrange     VARCHAR2(50)  NOT NULL,
    etat        NUMBER(2)     DEFAULT 1 NOT NULL,
    iduser      VARCHAR2(50)  DEFAULT '0',
    CONSTRAINT CPLACE_RANGE_FK FOREIGN KEY (idrange)
        REFERENCES cinema_range(id),
    CONSTRAINT CPLACE_RANGE_UK UNIQUE (idrange, libelle)
);

CREATE INDEX CPLACE_RANGE_IDX ON cinema_place(idrange);

CREATE SEQUENCE SEQ_CINEMA_PLACE START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE OR REPLACE FUNCTION getseq_cinema_place RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_CINEMA_PLACE.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/

-- ============================================================================
-- 9. cinema_programme  (mère : salle + date/heure début + prix courant)
-- ============================================================================
CREATE TABLE cinema_programme (
    id           VARCHAR2(50)  PRIMARY KEY,
    idsalle      VARCHAR2(50)  NOT NULL,
    datedebut    DATE          NOT NULL,
    heuredebut   VARCHAR2(5)   NOT NULL,
    prix         NUMBER(15,2)  DEFAULT 0 NOT NULL,
    etat         NUMBER(2)     DEFAULT 1 NOT NULL,
    iduser       VARCHAR2(50)  DEFAULT '0',
    CONSTRAINT CPRG_SALLE_FK FOREIGN KEY (idsalle) REFERENCES cinema_salle(id),
    CONSTRAINT CK_CPRG_HEURE CHECK (REGEXP_LIKE(heuredebut, '^([01][0-9]|2[0-3]):[0-5][0-9]$')),
    CONSTRAINT CK_CPRG_PRIX  CHECK (prix >= 0)
);

CREATE INDEX CPRG_SALLE_IDX ON cinema_programme(idsalle, datedebut, heuredebut);

CREATE SEQUENCE SEQ_CINEMA_PROGRAMME START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE OR REPLACE FUNCTION getseq_cinema_programme RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_CINEMA_PROGRAMME.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/

-- ============================================================================
-- 10. cinema_detail_programme
-- ============================================================================
CREATE TABLE cinema_detail_programme (
    id           VARCHAR2(50)  PRIMARY KEY,
    idprogramme  VARCHAR2(50)  NOT NULL,
    idfilm       VARCHAR2(50)  NOT NULL,
    rang         NUMBER(3)     DEFAULT 1 NOT NULL,
    etat         NUMBER(2)     DEFAULT 1 NOT NULL,
    iduser       VARCHAR2(50)  DEFAULT '0',
    CONSTRAINT CDPRG_PROGRAMME_FK FOREIGN KEY (idprogramme)
        REFERENCES cinema_programme(id),
    CONSTRAINT CDPRG_FILM_FK      FOREIGN KEY (idfilm)
        REFERENCES cinema_film(id),
    CONSTRAINT CK_CDPRG_RANG CHECK (rang >= 1)
);

CREATE INDEX CDPRG_PROGRAMME_IDX ON cinema_detail_programme(idprogramme);
CREATE INDEX CDPRG_FILM_IDX      ON cinema_detail_programme(idfilm);

CREATE SEQUENCE SEQ_CINEMA_DETAIL_PROGRAMME START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE OR REPLACE FUNCTION getseq_cinema_detail_programme RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_CINEMA_DETAIL_PROGRAMME.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/
-- ============================================================================
-- 11. cinema_hist_prix_programme  (renommé : <= 30 chars Oracle 11g)
-- ============================================================================
CREATE TABLE cinema_hist_prix_programme (
    id           VARCHAR2(50)  PRIMARY KEY,
    idprogramme  VARCHAR2(50)  NOT NULL,
    prix         NUMBER(15,2)  NOT NULL,
    datedebut    DATE          NOT NULL,
    CONSTRAINT CHPPROG_PROGRAMME_FK FOREIGN KEY (idprogramme)
        REFERENCES cinema_programme(id),
    CONSTRAINT CK_CHPPROG_PRIX CHECK (prix >= 0)
);

CREATE INDEX CHPPROG_PROGRAMME_IDX ON cinema_hist_prix_programme(idprogramme, datedebut);

CREATE SEQUENCE SEQ_CIN_HIST_PRIX_PROG START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE OR REPLACE FUNCTION getseq_cin_hist_prix_prog RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_CIN_HIST_PRIX_PROG.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/

-- ============================================================================
-- 12. cinema_client
-- ============================================================================
CREATE TABLE cinema_client (
    id          VARCHAR2(50)  PRIMARY KEY,
    nom         VARCHAR2(255) NOT NULL
);

CREATE SEQUENCE SEQ_CINEMA_CLIENT START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE OR REPLACE FUNCTION getseq_cinema_client RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_CINEMA_CLIENT.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/

-- ============================================================================
-- 13. cinema_statut_reservation
-- ============================================================================
CREATE TABLE cinema_statut_reservation (
    id          VARCHAR2(50)  PRIMARY KEY,
    libelle     VARCHAR2(50)  NOT NULL
);

CREATE SEQUENCE SEQ_CINEMA_STATUT_RESERVATION START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE OR REPLACE FUNCTION getseq_cinema_statut_reserv RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_CINEMA_STATUT_RESERVATION.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/

-- ============================================================================
-- 14. cinema_reservation
-- ============================================================================
CREATE TABLE cinema_reservation (
    id                    VARCHAR2(50)  PRIMARY KEY,
    idclient              VARCHAR2(50)  NOT NULL,
    iddetailprogramme     VARCHAR2(50)  NOT NULL,
    datereservation       DATE          NOT NULL,
    idstatutreservation   VARCHAR2(50),
    etat                  NUMBER(2)     DEFAULT 1 NOT NULL,
    iduser                VARCHAR2(50)  DEFAULT '0',
    CONSTRAINT CRES_CLIENT_FK     FOREIGN KEY (idclient)
        REFERENCES cinema_client(id),
    CONSTRAINT CRES_DETAIL_PRG_FK FOREIGN KEY (iddetailprogramme)
        REFERENCES cinema_detail_programme(id),
    CONSTRAINT CRES_STATUT_FK     FOREIGN KEY (idstatutreservation)
        REFERENCES cinema_statut_reservation(id)
);

CREATE INDEX CRES_CLIENT_IDX     ON cinema_reservation(idclient);
CREATE INDEX CRES_DETAIL_PRG_IDX ON cinema_reservation(iddetailprogramme);
CREATE INDEX CRES_STATUT_IDX     ON cinema_reservation(idstatutreservation);

CREATE SEQUENCE SEQ_CINEMA_RESERVATION START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE OR REPLACE FUNCTION getseq_cinema_reservation RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_CINEMA_RESERVATION.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/

-- ============================================================================
-- 15. cinema_detail_reservation
-- ============================================================================
CREATE TABLE cinema_detail_reservation (
    id             VARCHAR2(50)  PRIMARY KEY,
    idreservation  VARCHAR2(50)  NOT NULL,
    idplace        VARCHAR2(50)  NOT NULL,
    etat           NUMBER(2)     DEFAULT 1 NOT NULL,
    iduser         VARCHAR2(50)  DEFAULT '0',
    CONSTRAINT CDTR_RESERVATION_FK FOREIGN KEY (idreservation)
        REFERENCES cinema_reservation(id),
    CONSTRAINT CDTR_PLACE_FK       FOREIGN KEY (idplace)
        REFERENCES cinema_place(id)
);

CREATE INDEX CDTR_RESERVATION_IDX ON cinema_detail_reservation(idreservation);
CREATE INDEX CDTR_PLACE_IDX       ON cinema_detail_reservation(idplace);

CREATE SEQUENCE SEQ_CINEMA_DETAIL_RESERVATION START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE OR REPLACE FUNCTION getseq_cinema_det_reserv RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_CINEMA_DETAIL_RESERVATION.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/

-- ============================================================================
-- 16. cinema_hist_statut_reserv
-- ============================================================================
CREATE TABLE cinema_hist_statut_reserv (
    id                    VARCHAR2(50) PRIMARY KEY,
    idstatutreservation   VARCHAR2(50) NOT NULL,
    idreservation         VARCHAR2(50) NOT NULL,
    datehist              DATE         NOT NULL,
    CONSTRAINT CHSR_STATUT_FK      FOREIGN KEY (idstatutreservation)
        REFERENCES cinema_statut_reservation(id),
    CONSTRAINT CHSR_RESERVATION_FK FOREIGN KEY (idreservation)
        REFERENCES cinema_reservation(id)
);

CREATE INDEX CHSR_RESERV_IDX ON cinema_hist_statut_reserv(idreservation, datehist);

CREATE SEQUENCE SEQ_CINEMA_HIST_STATUT_RES START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE OR REPLACE FUNCTION getseq_cinema_hist_statut_res RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_CINEMA_HIST_STATUT_RES.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/

-- ============================================================================
-- 17. cinema_facture_client
-- ============================================================================
CREATE TABLE cinema_facture_client (
    id             VARCHAR2(50)  PRIMARY KEY,
    numero         VARCHAR2(30)  NOT NULL,
    idreservation  VARCHAR2(50)  NOT NULL,
    datefacture    DATE          NOT NULL,
    etat           NUMBER(2)     DEFAULT 1 NOT NULL,
    iduser         VARCHAR2(50)  DEFAULT '0',
    CONSTRAINT CFCT_RESERVATION_FK FOREIGN KEY (idreservation)
        REFERENCES cinema_reservation(id),
    CONSTRAINT CFCT_RESERVATION_UK UNIQUE (idreservation),
    CONSTRAINT CFCT_NUMERO_UK      UNIQUE (numero)
);

CREATE INDEX CFCT_DATE_IDX ON cinema_facture_client(datefacture);

CREATE SEQUENCE SEQ_CINEMA_FACTURE_CLIENT START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE OR REPLACE FUNCTION getseq_cinema_facture_client RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_CINEMA_FACTURE_CLIENT.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/

-- ============================================================================
-- VUES _LIB
-- ============================================================================
CREATE OR REPLACE VIEW cinema_place_lib AS
SELECT pl.id,
       pl.libelle,
       pl.idrange,
       pl.etat,
       pl.iduser,
       r.libelle AS rangelib,
       r.idsalle,
       s.libelle AS sallelib,
       r.idcategorierange,
       cr.libelle AS categorierangelib
FROM cinema_place pl
JOIN cinema_range r  ON r.id  = pl.idrange
JOIN cinema_salle s  ON s.id  = r.idsalle
JOIN cinema_categorie_range cr ON cr.id = r.idcategorierange;

CREATE OR REPLACE VIEW cinema_detail_programme_lib AS
SELECT d.id,
       d.idprogramme,
       d.idfilm,
       f.titre AS filmlib,
       f.duree AS filmeduree,
       p.idsalle,
       d.idprogramme || ' - ' || f.titre AS programmefilm,
       d.rang,
       d.etat,
       d.iduser
FROM cinema_detail_programme d
JOIN cinema_film f ON f.id = d.idfilm
JOIN cinema_programme p ON p.id = d.idprogramme;

COMMIT;