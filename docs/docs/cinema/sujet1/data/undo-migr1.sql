-- ============================================================================
-- CINEMA — Script UNDO (Annulation de la migration)
-- ============================================================================

-- 1. Annulation Paiement
DROP FUNCTION getseq_cinema_paiement;
DROP SEQUENCE SEQ_CINEMA_PAIEMENT;
DROP TABLE cinema_paiement CASCADE CONSTRAINTS;

-- 2. Annulation FACTURECLIENTDETAILS
ALTER TABLE FACTURECLIENTDETAILS DROP CONSTRAINT FK_FACTDET_DETRESERV_CINEMA;
ALTER TABLE FACTURECLIENTDETAILS DROP COLUMN IDDETAILRESERVATIONCINEMA;

-- 3. Annulation FACTURECLIENT
ALTER TABLE FACTURECLIENT DROP CONSTRAINT FK_FACTURE_RESERV_CINEMA;
ALTER TABLE FACTURECLIENT DROP COLUMN IDRESERVATIONCINEMA;

-- 4. Annulation FK CLIENT
ALTER TABLE cinema_reservation DROP CONSTRAINT CRES_CLIENT_AKOHO_FK;

-- On remet le type à 50 (ATTENTION: si des ID > 50 existent, commente cette ligne)
ALTER TABLE cinema_reservation MODIFY idclient VARCHAR2(50);

-- On s'assure que le client fictif existe dans l'ancienne table pour éviter l'erreur FK
INSERT INTO cinema_client (id, nom) 
SELECT 'CINEMA_INCONNU', 'Client Inconnu (UNDO)' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM cinema_client WHERE id = 'CINEMA_INCONNU');

-- On recrée l'ancienne FK vers cinema_client
ALTER TABLE cinema_reservation ADD CONSTRAINT CRES_CLIENT_FK 
    FOREIGN KEY (idclient) REFERENCES cinema_client(id);

COMMIT;