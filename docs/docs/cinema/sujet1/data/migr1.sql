-- ============================================================================
-- CINEMA — Script de migration vers les tables Akoho (COMPLET ET CORRIGÉ)
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 1. ALIGNEMENT ET SÉCURISATION DE LA FK CLIENT
-- ----------------------------------------------------------------------------

-- A. Aligner le type
ALTER TABLE cinema_reservation MODIFY idclient VARCHAR2(255);

-- B. Supprimer l'ancienne FK AVANT de toucher aux données
ALTER TABLE cinema_reservation DROP CONSTRAINT CRES_CLIENT_FK;

-- C. Créer le client fictif "INCONNU"
INSERT INTO CLIENT (ID, NOM) 
SELECT 'CINEMA_INCONNU', 'Client Inconnu (Migration)' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM CLIENT WHERE ID = 'CINEMA_INCONNU');

-- D. Rattacher les éventuelles réservations orphelines
UPDATE cinema_reservation 
SET idclient = 'CINEMA_INCONNU' 
WHERE idclient NOT IN (SELECT ID FROM CLIENT);

-- E. Ajouter la nouvelle FK vers CLIENT d'Akoho
ALTER TABLE cinema_reservation ADD CONSTRAINT CRES_CLIENT_AKOHO_FK 
    FOREIGN KEY (idclient) REFERENCES CLIENT(ID);


-- ----------------------------------------------------------------------------
-- 2. MISE A JOUR DE FACTURECLIENT (Table Mère Akoho)
-- ----------------------------------------------------------------------------
ALTER TABLE FACTURECLIENT ADD (
    IDRESERVATIONCINEMA VARCHAR2(50) 
);

ALTER TABLE FACTURECLIENT ADD CONSTRAINT FK_FACTURE_RESERV_CINEMA 
    FOREIGN KEY (IDRESERVATIONCINEMA) REFERENCES cinema_reservation(id);


-- ----------------------------------------------------------------------------
-- 3. MISE A JOUR DE FACTURECLIENTDETAILS (Table Fille Akoho)
-- ----------------------------------------------------------------------------
ALTER TABLE FACTURECLIENTDETAILS ADD (
    IDDETAILRESERVATIONCINEMA VARCHAR2(50) 
);

ALTER TABLE FACTURECLIENTDETAILS ADD CONSTRAINT FK_FACTDET_DETRESERV_CINEMA 
    FOREIGN KEY (IDDETAILRESERVATIONCINEMA) REFERENCES cinema_detail_reservation(id);


-- ----------------------------------------------------------------------------
-- 4. CRÉATION DE LA TABLE PAIEMENT
-- ----------------------------------------------------------------------------
CREATE TABLE cinema_paiement (
    id             VARCHAR2(50)  PRIMARY KEY,
    idfacture      VARCHAR2(100) NOT NULL, 
    daty           DATE          NOT NULL,
    montant        NUMBER(15,2)  NOT NULL,
    idmodepaiement VARCHAR2(255),
    iduser         VARCHAR2(50),
    CONSTRAINT FK_PAIEMENT_MODEPAIEMENT FOREIGN KEY (idmodepaiement) REFERENCES modepaiement(id),
    CONSTRAINT CINEMA_PAIEMENT_FACTURE_FK FOREIGN KEY (idfacture) REFERENCES FACTURECLIENT(id)
);


-- ----------------------------------------------------------------------------
-- 5. SÉQUENCE ET FONCTION POUR LE PAIEMENT
-- ----------------------------------------------------------------------------
CREATE SEQUENCE SEQ_CINEMA_PAIEMENT START WITH 1 INCREMENT BY 1 NOCACHE;

CREATE OR REPLACE FUNCTION getseq_cinema_paiement RETURN NUMBER IS
  v_seq NUMBER;
BEGIN
  SELECT SEQ_CINEMA_PAIEMENT.NEXTVAL INTO v_seq FROM DUAL;
  RETURN v_seq;
END;
/

COMMIT;