-- ============================================================================
-- DROP EVERYTHING — CINEMA schema cleanup
-- Run as akoho/Akoho123
-- Naming convention: all identifiers <= 30 chars (Oracle 11g limit)
-- ============================================================================

-- Views first
DROP VIEW cinema_programme_lib;
DROP VIEW cinema_place_lib;

-- Child tables first (FK dependencies)
DROP TABLE cinema_facture_client       CASCADE CONSTRAINTS;
DROP TABLE cinema_hist_statut_reserv   CASCADE CONSTRAINTS;
DROP TABLE cinema_detail_reservation   CASCADE CONSTRAINTS;
DROP TABLE cinema_reservation          CASCADE CONSTRAINTS;
DROP TABLE cinema_place                CASCADE CONSTRAINTS;
DROP TABLE cinema_range                CASCADE CONSTRAINTS;
DROP TABLE cinema_hist_prix_cat_range  CASCADE CONSTRAINTS;
DROP TABLE cinema_categorie_range      CASCADE CONSTRAINTS;
DROP TABLE cinema_programme            CASCADE CONSTRAINTS;
DROP TABLE cinema_categorie_film       CASCADE CONSTRAINTS;
DROP TABLE cinema_film                 CASCADE CONSTRAINTS;
DROP TABLE cinema_categorie            CASCADE CONSTRAINTS;
DROP TABLE cinema_salle                CASCADE CONSTRAINTS;
DROP TABLE cinema_client               CASCADE CONSTRAINTS;
DROP TABLE cinema_statut_reservation   CASCADE CONSTRAINTS;

-- In case the OLD (long) names were partially created earlier
DROP TABLE cinema_historique_prix_categorie_range  CASCADE CONSTRAINTS;
DROP TABLE cinema_historique_statut_reservation    CASCADE CONSTRAINTS;

-- Sequences (final names)
DROP SEQUENCE SEQ_CINEMA_CATEGORIE;
DROP SEQUENCE SEQ_CINEMA_FILM;
DROP SEQUENCE SEQ_CINEMA_CATEGORIE_FILM;
DROP SEQUENCE SEQ_CINEMA_SALLE;
DROP SEQUENCE SEQ_CINEMA_CATEGORIE_RANGE;
DROP SEQUENCE SEQ_CIN_HIST_PRIX_CAT_RANGE;
DROP SEQUENCE SEQ_CINEMA_RANGE;
DROP SEQUENCE SEQ_CINEMA_PLACE;
DROP SEQUENCE SEQ_CINEMA_PROGRAMME;
DROP SEQUENCE SEQ_CINEMA_CLIENT;
DROP SEQUENCE SEQ_CINEMA_STATUT_RESERVATION;
DROP SEQUENCE SEQ_CINEMA_RESERVATION;
DROP SEQUENCE SEQ_CINEMA_DETAIL_RESERVATION;
DROP SEQUENCE SEQ_CINEMA_HIST_STATUT_RES;
DROP SEQUENCE SEQ_CINEMA_FACTURE_CLIENT;
-- In case old (long / intermediate) sequence names were partially created
DROP SEQUENCE SEQ_CINEMA_HIST_PRIX_CATEGORIE_RANGE;
DROP SEQUENCE SEQ_CINEMA_HIST_PRIX_CAT_RANGE;
DROP SEQUENCE SEQ_CINEMA_HIST_STATUT_RESERVATION;

-- Functions (final names)
DROP FUNCTION getseq_cinema_categorie;
DROP FUNCTION getseq_cinema_film;
DROP FUNCTION getseq_cinema_categorie_film;
DROP FUNCTION getseq_cinema_salle;
DROP FUNCTION getseq_cinema_categorie_range;
DROP FUNCTION getseq_cin_hist_prix_cat_rng;
DROP FUNCTION getseq_cinema_range;
DROP FUNCTION getseq_cinema_place;
DROP FUNCTION getseq_cinema_programme;
DROP FUNCTION getseq_cinema_client;
DROP FUNCTION getseq_cinema_statut_reserv;
DROP FUNCTION getseq_cinema_reservation;
DROP FUNCTION getseq_cinema_det_reserv;
DROP FUNCTION getseq_cinema_hist_statut_res;
DROP FUNCTION getseq_cinema_facture_client;
-- In case old (long / intermediate) function names were partially created
DROP FUNCTION getseq_cinema_hist_prix_categorie_range;
DROP FUNCTION getseq_cinema_hist_prix_cat_range;
DROP FUNCTION getseq_cinema_hist_statut_reservation;
DROP FUNCTION getseq_cinema_statut_reservation;
DROP FUNCTION getseq_cinema_detail_reservation;

COMMIT;