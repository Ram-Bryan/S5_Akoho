# CINEMA — Conception de la base de données

## 1. Gestion des films

### cinema_categorie
- id
- libelle

### cinema_film
- id
- titre
- description
- duree

### cinema_categorie_film
- id
- id_categorie
- id_film

---

## 2. Gestion des salles et des sièges

### cinema_salle
- id
- libelle

### cinema_categorie_range
- id
- libelle

### cinema_historique_prix_categorie_range
- id
- id_categorie_range
- prix_supplement
- date_debut

### cinema_range
- id
- libelle
- id_salle
- id_categorie_range

### cinema_place
- id
- libelle
- id_range

---

## 3. Gestion des programmes (séances)

### cinema_programme
- id
- id_film
- id_salle
- date_debut
- date_fin
- prix_base

---

## 4. Gestion des clients et des réservations

### cinema_client
- id
- nom

### cinema_reservation
- id
- id_client
- id_programme
- date_reservation

### cinema_detail_reservation
- id
- id_reservation
- id_place

### cinema_statut_reservation
- id
- libelle (reserve, paye, annule, expire)

### cinema_historique_statut_reservation
- id
- id_statut_reservation
- id_reservation
- date

---

## 5. Gestion des factures

### cinema_facture_client
- id
- numero
- id_reservation
- date_facture

---

## 6. Règles de gestion

- Un film peut appartenir à plusieurs catégories et une catégorie peut être associée à plusieurs films.
- Un cinéma possède plusieurs salles.
- Chaque salle possède sa propre disposition de ranges. Les dispositions peuvent être différentes d'une salle à une autre.
- Chaque range appartient à une seule salle et à une catégorie de range.
- Chaque catégorie de range possède un historique de suppléments fixes.
- Chaque place appartient à une seule range.
- Un programme représente une séance d'un film dans une salle, à une date et une heure données.
- Chaque programme possède un prix de base fixé par le cinéma.
- Une réservation appartient à un client et concerne un seul programme.
- Une réservation peut contenir plusieurs places, y compris des places appartenant à différentes ranges.
- Une place ne peut pas être attribuée à plusieurs réservations actives pour un même programme.
- Le client représente l'acheteur. Il n'est pas nécessaire d'enregistrer individuellement chaque spectateur.
- Le prix d'un billet est calculé dynamiquement selon la formule :
  **Prix du billet = Prix de base du programme + Supplément de la catégorie de range.**
- Le supplément applicable est déterminé à partir de l'historique des prix de la catégorie de range et de la date de référence de la vente.
- Les changements de statut des réservations sont conservés dans l'historique des statuts.
- Une facture est associée à une réservation.
- Le mode de paiement est enregistré directement dans la facture.
- Le montant total de la facture est calculé dynamiquement à partir des billets de la réservation.
- Les historiques de prix ne doivent pas être supprimés ou modifiés rétroactivement lorsqu'ils ont déjà été utilisés pour des ventes.
- Une réservation annulée ou expirée ne doit plus bloquer les places concernées.   