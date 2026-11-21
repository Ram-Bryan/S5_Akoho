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
DELETE FROM cinema_remise;

-- Standard (S) >= 2 : 20 % sur Confort
INSERT INTO cinema_remise VALUES ('CREM000001','CCRG000001',2,20,'CCRG000002',TRUNC(SYSDATE)-90,NULL);
-- Standard >= 4 : 70 % sur Confort (domine R1)
INSERT INTO cinema_remise VALUES ('CREM000002','CCRG000001',4,70,'CCRG000002',TRUNC(SYSDATE)-90,NULL);
-- Standard >= 3 : 10 % sur VIP
INSERT INTO cinema_remise VALUES ('CREM000003','CCRG000001',3,10,'CCRG000003',TRUNC(SYSDATE)-90,NULL);
-- Confort (C) >= 2 : 25 % sur VIP
INSERT INTO cinema_remise VALUES ('CREM000004','CCRG000002',2,25,'CCRG000003',TRUNC(SYSDATE)-90,NULL);
-- VIP (V) >= 1 : 5 % sur Standard
INSERT INTO cinema_remise VALUES ('CREM000005','CCRG000003',1,5,'CCRG000001',TRUNC(SYSDATE)-90,NULL);
-- Confort >= 3 : 15 % sur Standard
INSERT INTO cinema_remise VALUES ('CREM000006','CCRG000002',3,15,'CCRG000001',TRUNC(SYSDATE)-90,NULL);
-- Standard >= 6 : 80 % sur VIP (domine R3)
INSERT INTO cinema_remise VALUES ('CREM000007','CCRG000001',6,80,'CCRG000003',TRUNC(SYSDATE)-90,NULL);
-- VIP >= 2 : 33,33 % sur Confort (décimal)
INSERT INTO cinema_remise VALUES ('CREM000008','CCRG000003',2,33.33,'CCRG000002',TRUNC(SYSDATE)-90,NULL);


COMMIT;