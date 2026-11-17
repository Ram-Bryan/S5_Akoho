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
-- (Attention: On remet la taille à 50. Si tu as des idclient > 50 caractères, cela plantera. 
--  Dans ce cas, supprime cette ligne MODIFY ou garde 255)
ALTER TABLE cinema_reservation DROP CONSTRAINT CRES_CLIENT_AKOHO_FK;
ALTER TABLE cinema_reservation MODIFY idclient VARCHAR2(50);

-- On recrée l'ancienne FK vers cinema_client
ALTER TABLE cinema_reservation ADD CONSTRAINT CRES_CLIENT_FK 
    FOREIGN KEY (idclient) REFERENCES cinema_client(id);

-- (Optionnel) Nettoyer le client fictif si tu veux
-- DELETE FROM CLIENT WHERE ID = 'CINEMA_INCONNU';

COMMIT;