# AKOHO PROJECT — Technical Specification

> **Purpose:** Ultra-detailed technical specification for developers joining the Akoho project. Covers every layer, file, pattern, and workflow.

---

## Table of Contents

1. [System Overview](#1-system-overview)
2. [Technology Stack](#2-technology-stack)
3. [Project Structure](#3-project-structure)
4. [Architecture Layers](#4-architecture-layers)
5. [Framework Internals](#5-framework-internals)
6. [Entity System](#6-entity-system)
7. [UI Generation System](#7-ui-generation-system)
8. [Controller Layer](#8-controller-layer)
9. [Service Layer](#9-service-layer)
10. [Data Access Layer](#10-data-access-layer)
11. [Database Schema](#11-database-schema)
12. [State Machine](#12-state-machine)
13. [Security & Permissions](#13-security--permissions)
14. [Audit Trail](#14-audit-trail)
15. [Ferme Workflow — Complete Flow](#15-ferme-workflow--complete-flow)
16. [Calendar Systems](#16-calendar-systems)
17. [Build & Deployment](#17-build--deployment)
18. [Development Guide](#18-development-guide)
19. [Sprint Specifications](#19-sprint-specifications)
20. [Troubleshooting](#20-troubleshooting)

---

## 1. System Overview

**Akoho** is a Java EE 8 enterprise application for managing a chicken farm operation. It handles the complete lifecycle from purchasing chickens to selling eggs and meat.

### Core Business Domains:
- **Farm Management** — Lots, buildings, compartments, chicken tracking
- **Production** — Egg collection, hatching, incubation
- **Sales** — Customer orders, invoicing, delivery
- **Purchasing** — Supplier orders, receiving, supplier invoices
- **Payroll** — Employee management, attendance, salaries
- **Accounting** — Journal entries, financial reports
- **Maintenance** — Equipment scheduling, work requests
- **Inventory** — Stock management, movements

---

## 2. Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Language | Java | 8 |
| Server | Tomcat | 9 |
| Database | Oracle | 11g+ |
| JDBC Driver | ojdbc6 | 11 |
| Framework | APJ (Custom) | Internal |
| Presentation | JSP + JavaScript | — |
| CSS Framework | Bootstrap | 3.x |
| JS Libraries | jQuery, FullCalendar, Chart.js | — |
| Build Tool | Ant | — |
| Deployment | Docker (Oracle EE) | — |
| EJB | Java EE 8 | — |

---

## 3. Project Structure

```
/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/
│
├── framework/                              # Framework source (decompiled from JARs)
│   ├── apj-core/                           # Core framework
│   │   ├── bean/                           # ORM layer
│   │   │   ├── ClassMAPTable.java          # Base entity class
│   │   │   ├── CGenUtil.java               # CRUD utility
│   │   │   ├── TypeObjet.java              # Dropdown entity
│   │   │   ├── ClassMere.java              # Master entity base
│   │   │   ├── ClassFille.java             # Detail entity base
│   │   │   ├── ClassEtat.java              # Stateful entity base
│   │   │   ├── ClassUser.java              # User-linked entity base
│   │   │   ├── ListeColonneTable.java      # Column metadata
│   │   │   ├── ResultatEtSomme.java        # Paginated result
│   │   │   ├── Champ.java                  # Form field
│   │   │   ├── Liste.java                  # Dropdown list
│   │   │   └── Constante.java              # Constants
│   │   ├── affichage/                      # UI generation
│   │   │   ├── Page.java                   # Base page
│   │   │   ├── PageRecherche.java          # List/search page
│   │   │   ├── PageInsert.java             # Insert/update form
│   │   │   ├── PageInsertMultiple.java     # Master-detail form
│   │   │   ├── Formulaire.java             # Form generator
│   │   │   ├── TableauRecherche.java       # Data table
│   │   │   ├── PageCalendrier.java         # Event calendar
│   │   │   ├── Calendrier.java             # Calendar with legends
│   │   │   └── CalendrierElement.java      # Calendar event
│   │   ├── utilitaire/                     # Utilities
│   │   │   ├── UtilDB.java                 # DB connection manager
│   │   │   ├── Utilitaire.java             # General utilities
│   │   │   ├── UtilitaireString.java       # String utilities
│   │   │   └── ConstanteEtat.java          # State constants
│   │   ├── historique/                     # Audit trail
│   │   │   ├── MapHistorique.java          # History entity
│   │   │   ├── HistoriqueBean.java         # History EJB
│   │   │   ├── HistoriqueLocal.java        # Local interface
│   │   │   └── HistoriqueRemote.java       # Remote interface
│   │   └── constante/                      # Constants
│   │       └── ConstanteAffichage.java     # UI constants
│   │
│   └── apj-core2/                          # Extended framework
│       ├── utilisateur/
│       │   └── ActionRole.java             # Role permissions
│       ├── notification/
│       │   ├── NotificationService.java
│       │   └── NotificationMessage.java
│       ├── web/servlet/
│       │   ├── CalendrierServlet.java
│       │   └── CalendrierDataServlet.java
│       ├── config/
│       │   └── CynthiaConf.java
│       └── importController/
│           └── ImportUtilServlet.java
│
├── arbiochem-ejb/                          # EJB Module
│   └── src/java/
│       ├── user/                           # User management
│       │   ├── UserEJB.java                # Interface
│       │   ├── UserEJBBean.java            # Implementation (1467 lines)
│       │   ├── UserEJBRemote.java
│       │   ├── UserEJBClient.java
│       │   ├── CreateObject.java
│       │   ├── UpdateObject.java
│       │   └── ValiderObject.java
│       ├── ferme/                          # Farm domain
│       │   ├── lot/
│       │   │   ├── Lot.java                # Lot entity
│       │   │   └── LotLib.java
│       │   ├── receptionaeroport/
│       │   │   ├── ReceptionPoussinAeroport.java
│       │   │   ├── ReceptionPoussinAeroportDetail.java
│       │   │   ├── ReceptionPoussinAeroportLib.java
│       │   │   └── ReceptionPoussinAeroportDetailLib.java
│       │   ├── transfertpoulet/
│       │   │   ├── TransfertPoulet.java
│       │   │   ├── TransfertPouletDetail.java
│       │   │   ├── TransfertPouletLib.java
│       │   │   └── TransfertPouletDetailLib.java
│       │   ├── triageBatiment/
│       │   │   ├── TriageBatimentParquet.java
│       │   │   ├── TriageBatimentParquetDetail.java
│       │   │   ├── TriageLotSexeQualite.java
│       │   │   └── TriageLotSexeQualiteLib.java
│       │   ├── configuration/
│       │   │   ├── Batiment.java
│       │   │   ├── Parquet.java
│       │   │   ├── ParquetBatiment.java
│       │   │   ├── Souche.java
│       │   │   ├── OrigineLot.java
│       │   │   ├── QualitePoussin.java
│       │   │   ├── Vehicule.java
│       │   │   ├── Incubateur.java
│       │   │   └── Eclosoir.java
│       │   ├── production/
│       │   │   ├── ReceptionOAC.java
│       │   │   ├── ImportOAC.java
│       │   │   ├── Mirage.java
│       │   │   ├── TriageOeuf.java
│       │   │   └── Fumigation.java
│       │   ├── couvoir/
│       │   │   ├── Eclosion.java
│       │   │   ├── MiseEnIncubateur.java
│       │   │   ├── SuiviChambreFroide.java
│       │   │   └── TriageQualitePoussin.java
│       │   ├── programme/
│       │   │   ├── ProgrammeZootechnique.java
│       │   │   └── ProgrammeZootechniqueDetails.java
│       │   ├── aliment/
│       │   │   ├── DistributionAliment.java
│       │   │   └── DistributionAlimentDetail.java
│       │   ├── pesee/
│       │   │   ├── PeseePoussin.java
│       │   │   └── PeseePoussinDetail.java
│       │   ├── vaccination/
│       │   │   ├── VaccinationPoussin.java
│       │   │   └── VaccinationPoussinDetail.java
│       │   ├── suiviJournalier/
│       │   │   ├── SuiviJournalier.java
│       │   │   └── SuiviJournalierDetail.java
│       │   └── utils/
│       │       ├── ConstanteFerme.java
│       │       └── ConfigPoids.java
│       ├── vente/                          # Sales domain
│       │   ├── CaCalendrier.java           # Revenue calendar
│       │   └── CaJourDetailPourCalendrier.java
│       ├── paie/                           # Payroll domain
│       ├── produits/                       # Products domain
│       ├── rapport/                        # Reports domain
│       ├── pertegain/                      # Profit/loss
│       ├── paramCompta/                    # Accounting params
│       ├── change/                         # Exchange rates
│       ├── maintenance/                    # Equipment maintenance
│       ├── previsionFerme/                 # Farm forecasting
│       ├── prevision/                      # Financial forecasting
│       └── ... (60+ domain packages)
│
├── arbiochem-war/                          # WAR Module
│   ├── src/java/servlet/                   # Controllers
│   │   ├── ApresMultiple.java              # Main POST handler
│   │   ├── CompteServlet.java
│   │   ├── IngredientsRemiseServlet.java
│   │   ├── DeviseServlet.java
│   │   ├── EtatComptableServlet.java
│   │   ├── Reporting.java
│   │   ├── ApresTarif.java
│   │   ├── CSVUploadServlet.java
│   │   ├── LoadFromCsv.java
│   │   ├── BalanceExportServlet.java
│   │   ├── ImportPointageCantineServlet.java
│   │   ├── ImportChargePersonnelServlet.java
│   │   ├── ExportTemplateChargePersonnelServlet.java
│   │   ├── CompteurElectriciteServlet.java
│   │   ├── CompteurMaintenanceServlet.java
│   │   ├── UtilitaireImpression.java
│   │   ├── chatbot/
│   │   │   ├── ResponseBuilderServlet.java
│   │   │   ├── QueryGeneratorServlet.java
│   │   │   └── ResponseHandlerServlet.java
│   │   ├── paie/
│   │   │   ├── pointage/PointageServlet.java
│   │   │   ├── export/ExportServlet.java
│   │   │   ├── export/ostie/ExportServlet.java
│   │   │   ├── export/cnaps/ExportServlet.java
│   │   │   ├── report/EtatReportJasper.java
│   │   │   └── absence/SoldeCongePermission.java
│   │   ├── filemanager/
│   │   │   ├── FileManager.java
│   │   │   ├── FileManager2.java
│   │   │   └── Util.java
│   │   ├── uploadbean/
│   │   │   ├── UploadDownloadFileServlet.java
│   │   │   └── StringUtil.java
│   │   └── station/export/
│   │       ├── ExportPDF.java
│   │       ├── ExportPDFARBIOCHEM.java
│   │       └── ExportExcel.java
│   │
│   └── web/
│       ├── index.jsp                       # Login page
│       ├── module.jsp                      # Main framework page
│       ├── accueil.jsp                     # Home page
│       ├── WEB-INF/
│       │   └── web.xml                     # Servlet config
│       ├── assets/
│       │   ├── css/                        # Stylesheets
│       │   ├── js/                         # JavaScript
│       │   │   ├── jquery-latest.js
│       │   │   ├── bootstrap.min.js
│       │   │   ├── Chart.min.js
│       │   │   ├── moment.min.js
│       │   │   ├── sidebar.js
│       │   │   ├── script.js
│       │   │   ├── messagews.js
│       │   │   ├── controleTj.js
│       │   │   ├── breadcrumb/
│       │   │   └── socket.io/
│       │   ├── img/                        # Images
│       │   └── calendar/                   # FullCalendar assets
│       │       ├── fullcalendar.min.js
│       │       └── fullcalendar.min.css
│       └── pages/                          # JSP pages by domain
│           ├── ferme/
│           │   ├── lot/
│           │   │   ├── lot-saisie.jsp
│           │   │   ├── lot-liste.jsp
│           │   │   └── lot-fiche.jsp
│           │   ├── receptionaeroport/
│           │   │   ├── receptionaeroport-saisie.jsp
│           │   │   ├── receptionaeroport-liste.jsp
│           │   │   ├── receptionaeroport-fiche.jsp
│           │   │   └── inc/receptionaeroport-details.jsp
│           │   ├── transfertpoulet/
│           │   │   ├── transfertpoulet-saisie.jsp
│           │   │   ├── transfertpoulet-liste.jsp
│           │   │   ├── transfertpoulet-fiche.jsp
│           │   │   └── inc/transfetpoulet-det.jsp
│           │   ├── triagebatiment/
│           │   │   ├── triagebatimentparquet-saisie.jsp
│           │   │   ├── triagebatimentparquet-liste.jsp
│           │   │   ├── triagebatimentparquet-fiche.jsp
│           │   │   └── inc/triagebatimentparquet-det.jsp
│           │   ├── configuration/
│           │   │   ├── batiment-saisie.jsp
│           │   │   ├── parquet-saisie.jsp
│           │   │   ├── souche-saisie.jsp
│           │   │   ├── originelot-saisie.jsp
│           │   │   ├── qualitepoussin-saisie.jsp
│           │   │   ├── vehicule-saisie.jsp
│           │   │   ├── incubateur-saisie.jsp
│           │   │   └── eclosoir-saisie.jsp
│           │   ├── production/
│           │   │   ├── receptionoac-saisie.jsp
│           │   │   ├── importoac-saisie.jsp
│           │   │   ├── mirage-saisie.jsp
│           │   │   ├── triageoeuf-saisie.jsp
│           │   │   └── fumigation-saisie.jsp
│           │   ├── couvoir/
│           │   │   ├── eclosion-saisie.jsp
│           │   │   ├── miseneincubateur-saisie.jsp
│           │   │   ├── suivichambrefroide-saisie.jsp
│           │   │   └── triagequalitepoussin-saisie.jsp
│           │   ├── programme/
│           │   │   ├── programmezoo-saisie.jsp
│           │   │   ├── programmezoo-liste.jsp
│           │   │   └── programmezoo-fiche.jsp
│           │   ├── aliment/
│           │   │   ├── distributionaliment-saisie.jsp
│           │   │   ├── distributionaliment-liste.jsp
│           │   │   └── distributionaliment-fiche.jsp
│           │   ├── pesee/
│           │   │   ├── peseepoussin-saisie.jsp
│           │   │   ├── peseepoussin-liste.jsp
│           │   │   └── peseepoussin-fiche.jsp
│           │   ├── vaccination/
│           │   │   ├── vaccinationpoussin-saisie.jsp
│           │   │   ├── vaccinationpoussin-liste.jsp
│           │   │   └── vaccinationpoussin-fiche.jsp
│           │   └── suiviJournalier/
│           │       ├── suivijournalier-saisie.jsp
│           │       ├── suivijournalier-liste.jsp
│           │       └── suivijournalier-fiche.jsp
│           ├── vente/
│           │   ├── analyse/
│           │   │   ├── akoho-prev.jsp      # Revenue calendar
│           │   │   └── ca-calendrier-v2.jsp
│           │   └── inc/
│           │       ├── vente-paiement-calendrier-details.jsp
│           │       └── liste-prevision.jsp
│           ├── paie/
│           │   ├── pointage/
│           │   │   ├── calendrier-saisie.jsp
│           │   │   ├── calendrier-liste.jsp
│           │   │   └── calendrier-fiche.jsp
│           │   └── onBoarding/
│           │       ├── checkentree-saisie.jsp
│           │       ├── checkentree-liste.jsp
│           │       └── checkentree-fiche.jsp
│           ├── maintenance/
│           │   └── planning/
│           │       ├── calendrier-demande-travaux.jsp
│           │       └── calendrier-maintenance.jsp
│           ├── prevision/
│           │   ├── prevision-saisie.jsp
│           │   ├── prevision-liste.jsp
│           │   ├── prevision-fiche.jsp
│           │   ├── prevision-modif.jsp
│           │   ├── prevision-scinder.jsp
│           │   ├── prevision-duplication.jsp
│           │   ├── previsionRecette-saisie.jsp
│           │   ├── previsionDepense-saisie.jsp
│           │   ├── resultat-prevision.jsp
│           │   ├── prevision-liste-recette.jsp
│           │   ├── prevision-liste-depense.jsp
│           │   ├── prevision-liste-recette-echue.jsp
│           │   ├── prevision-liste-depense-echue.jsp
│           │   ├── prevision-non-regle.jsp
│           │   ├── previsiongroupe-liste.jsp
│           │   ├── previsionDecaler-liste.jsp
│           │   └── decalage/
│           │       ├── decalage-prevision-saisie.jsp
│           │       └── decalage-previsionMultiple-saisie.jsp
│           ├── previsionFerme/
│           │   ├── previsionFermeEntree-saisie.jsp
│           │   ├── previsionFermeEntree-liste.jsp
│           │   ├── previsionFermeSortie-saisie.jsp
│           │   ├── previsionFermeSortie-liste.jsp
│           │   ├── previsionFerme-saisie.jsp
│           │   ├── previsionFerme-liste.jsp
│           │   ├── previsionFerme-fiche.jsp
│           │   ├── previsionFerme-modif.jsp
│           │   ├── resultat-previsionFerme.jsp
│           │   └── apresPrevisionFerme.jsp
│           ├── produits/
│           │   ├── as-produits-saisie.jsp
│           │   ├── as-produits-liste.jsp
│           │   ├── as-produits-fiche.jsp
│           │   ├── as-produits-modif.jsp
│           │   ├── as-produitsprix-saisie.jsp
│           │   ├── as-ingredients-saisie.jsp
│           │   ├── as-ingredients-fiche.jsp
│           │   ├── as-ingredients-modif.jsp
│           │   ├── as-ingredients-arbiochem-fiche.jsp
│           │   ├── as-tarifproduits-saisie.jsp
│           │   ├── as-tarifproduits-liste.jsp
│           │   ├── as-tarifproduits-fiche.jsp
│           │   ├── as-tarifproduits-modif.jsp
│           │   ├── recette-saisie.jsp
│           │   ├── recette-liste.jsp
│           │   ├── recette-fiche.jsp
│           │   ├── ingredientsPrecuit-saisie.jsp
│           │   ├── tarifIngredients/
│           │   │   ├── tarif-ingredients-fiche.jsp
│           │   │   └── tarifIngredients-saisie.jsp
│           │   ├── apresValidationRecette.jsp
│           │   ├── apresProduits.jsp
│           │   └── inc/
│           │       ├── recette-fille.jsp
│           │       └── recette-fille.jsp
│           ├── compta/
│           │   └── exercice/
│           │       ├── fiche-cloture.jsp
│           │       └── ouvertureCloture.jsp
│           ├── facturefournisseur/
│           │   ├── repartition-frais-divers.jsp
│           │   └── inc/
│           │       └── liste-prevision.jsp
│           ├── pertegain/
│           │   ├── pertegainimprevue-liste.jsp
│           │   ├── pertegainimprevue-saisie.jsp
│           │   ├── pertegainimprevue-fiche.jsp
│           │   ├── pertegainimprevue-modif.jsp
│           │   ├── pertegain-mvtcaisse-saisie.jsp
│           │   └── inc/
│           │       └── ecriture-details.jsp
│           ├── notification/
│           │   ├── notification-liste.jsp
│           │   ├── notification-fiche.jsp
│           │   └── apresnotif.jsp
│           ├── tableaudebord/
│           │   └── tableau-bord-bc.jsp
│           ├── tempsOuverture/
│           │   ├── tempsOuverture-saisie.jsp
│           │   ├── tempsOuverture-liste.jsp
│           │   └── tempsOuverture-fiche.jsp
│           ├── caisse/mvt/
│           │   ├── mvtcaisse-entree-pos.jsp
│           │   ├── mvtCaisse-saisie-entree.jsp
│           │   ├── mvtCaisse-saisie-entree-avoir.jsp
│           │   ├── mvtCaisse-saisie-entree-fc.jsp
│           │   ├── mvtCaisse-saisie-entree-vente.jsp
│           │   ├── mvtCaisse-saisie-entree-vente-arbiochem.jsp
│           │   └── apres-attacher-prevision.jsp
│           ├── stock/etatstock/
│           │   └── fichier-inventaire-par-entree.jsp
│           ├── stat/
│           │   ├── repartition-cout-of.jsp
│           │   └── repartition-cout-production.jsp
│           └── elements/
│               └── menu/
│                   └── module.jsp
│
├── codebar-generator/                      # Barcode generator
│   ├── Main.java
│   ├── apj.properties
│   └── lib/
│       ├── apj-core.jar
│       ├── apj-core2.jar
│       ├── barcode4j-2.1.jar
│       ├── core-2.2.jar
│       ├── javase-2.2.jar
│       ├── htmlcomponent.jar
│       ├── artisanat-ejb.jar
│       ├── ojdbc6-11.jar
│       ├── httpclient-4.3.4.jar
│       ├── httpcore-4.3.2.jar
│       └── httpmime-4.3.jar
│
├── docs/
│   ├── ai/
│   │   ├── PROJECT_GUIDE.md                # Complete project guide
│   │   └── TECHNICAL_SPEC.md               # This file
│   ├── prompt/
│   │   ├── ROLE.md
│   │   ├── CONTEXT.md
│   │   └── tasks/
│   │       ├── task1.md
│   │       └── task2.md
│   └── sprint/
│       ├── sprint1.md
│       └── sprint2.md
│
├── build-file/                             # Build artifacts
│   ├── arbiochem_war/
│   │   ├── index.jsp
│   │   ├── dateBuild.txt
│   │   ├── testdoudou.txt
│   │   └── lib/                            # All JAR dependencies
│   └── arbiochem_jar/
│
├── google-adk/                             # Google ADK
├── control/                                # Control scripts
│
├── build.xml                               # Main Ant build
├── build-tomcat.xml                        # Tomcat build
├── build-jenkins.xml                       # Jenkins build
├── build-hook.xml                          # Build hook
├── build-revert-hook.xml                   # Revert hook
├── build.sh                                # Build script
├── Dockerfile                              # Docker config
├── wildfly.md                              # WildFly docs
└── projet-standard.iml                     # IntelliJ module
```

---

## 4. Architecture Layers

### 4.1 Layer Diagram

```
┌─────────────────────────────────────────────────────────────────────────┐
│                           CLIENT LAYER                                   │
│  Browser (HTML, CSS, JavaScript)                                        │
│  Libraries: jQuery, Bootstrap, FullCalendar, Chart.js, Moment.js        │
└────────────────────────────────┬────────────────────────────────────────┘
                                 │ HTTP GET/POST
                                 ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                     PRESENTATION LAYER (WAR)                             │
│                                                                          │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │  JSP PAGES (View)                                                  │ │
│  │  /arbiochem-war/web/pages/                                         │ │
│  │                                                                    │ │
│  │  Page Types:                                                       │ │
│  │  - *-saisie.jsp  → Create/Edit forms                               │ │
│  │  - *-liste.jsp   → Search/List with pagination                     │ │
│  │  - *-fiche.jsp   → Detail view of saved record                     │ │
│  │  - apres*.jsp    → Post-action handlers                            │ │
│  │                                                                    │ │
│  │  Key Pages:                                                        │ │
│  │  - index.jsp     → Login                                           │ │
│  │  - module.jsp    → Main framework page                             │ │
│  │  - accueil.jsp   → Home dashboard                                  │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                                                                          │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │  SERVLETS (Controller)                                             │ │
│  │  /arbiochem-war/src/java/servlet/                                  │ │
│  │                                                                    │ │
│  │  @WebServlet annotated classes:                                    │ │
│  │  - ApresMultiple.java  → Main POST handler (generic CRUD)          │ │
│  │  - CompteServlet.java  → Account management                        │ │
│  │  - DeviseServlet.java  → Currency management                       │ │
│  │  - Reporting.java      → Reports                                   │ │
│  │  - ... (20+ servlets)                                              │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                                                                          │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │  APJ UI FRAMEWORK (apj-core.jar)                                   │ │
│  │  /framework/apj-core/affichage/                                    │ │
│  │                                                                    │ │
│  │  Generates HTML from entity metadata:                              │ │
│  │  - PageRecherche    → Search form + data table + pagination        │ │
│  │  - PageInsert       → Insert/update form                           │ │
│  │  - PageInsertMultiple → Master-detail form                         │ │
│  │  - Formulaire       → Form generator                               │ │
│  │  - Champ/Liste      → Individual fields/dropdowns                  │ │
│  │  - TableauRecherche → Data table                                   │ │
│  └────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────┘
                                 │
                                 ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                   BUSINESS LOGIC LAYER (EJB)                             │
│                                                                          │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │  USER SERVICE BEAN                                                 │ │
│  │  /arbiochem-ejb/src/java/user/UserEJBBean.java                      │ │
│  │                                                                    │ │
│  │  Stateful Session Bean — one per user session                      │ │
│  │  - Authentication (testLogin)                                      │ │
│  │  - CRUD operations (createObject, updateObject, deleteObject)      │ │
│  │  - Transaction management (manual commit/rollback)                 │ │
│  │  - History/audit trail                                             │ │
│  │  - Role-based access control                                       │ │
│  │  - Notification management                                         │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                                                                          │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │  DOMAIN EJBs (60+ packages)                                        │ │
│  │  /arbiochem-ejb/src/java/                                          │ │
│  │                                                                    │ │
│  │  Each package contains entities extending ClassMAPTable:           │ │
│  │  - ferme/        → Farm management                                 │ │
│  │  - vente/        → Sales                                           │ │
│  │  - paie/         → Payroll                                         │ │
│  │  - produits/     → Products                                        │ │
│  │  - rapport/      → Reports                                         │ │
│  │  - maintenance/  → Equipment maintenance                           │ │
│  │  - ... etc.                                                        │ │
│  └────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────┘
                                 │
                                 ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                   DATA ACCESS LAYER (Framework)                          │
│                                                                          │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │  ORM LAYER                                                         │ │
│  │  /framework/apj-core/bean/                                         │ │
│  │                                                                    │ │
│  │  ClassMAPTable (abstract)                                          │ │
│  │  - Reflection-based field-to-column mapping                        │ │
│  │  - Dynamic SQL generation                                          │ │
│  │  - Primary key generation via DB sequences                         │ │
│  │                                                                    │ │
│  │  CGenUtil (static utility)                                         │ │
│  │  - rechercher() → Build and execute SELECT                         │ │
│  │  - rechercherPageMax() → Paginated queries                         │ │
│  │  - makeWhere() → Build WHERE clauses                               │ │
│  │  - getValeurFieldByMethod() → Reflection field access              │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│                                                                          │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │  CONNECTION LAYER                                                  │ │
│  │  /framework/apj-core/utilitaire/UtilDB.java                        │ │
│  │                                                                    │ │
│  │  - JNDI DataSource lookup: java:VVFinances                         │ │
│  │  - Oracle and PostgreSQL support                                   │ │
│  │  - Connection counting via ConnectionProxy                         │ │
│  │  - Date format configuration                                       │ │
│  └────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────┘
                                 │
                                 ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                   DATABASE LAYER                                         │
│  Oracle Database (AKOHO schema)                                         │
│  Access via JNDI DataSource: java:VVFinances                            │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 5. Framework Internals

### 5.1 Entity Hierarchy

```
ClassMAPTable (abstract)
    │
    ├── ClassEtat (abstract) — adds state management
    │   │
    │   └── ClassUser (abstract) — adds user tracking
    │       │
    │       └── [Your Entity] — e.g., Lot, ReceptionPoussinAeroport
    │
    ├── ClassMere (abstract) — for master-detail entities
    │   │
    │   └── [Your Master Entity] — e.g., ReceptionPoussinAeroport
    │       │
    │       └── ClassFille (abstract) — for detail entities
    │           │
    │           └── [Your Detail Entity] — e.g., ReceptionPoussinAeroportDetail
    │
    └── [Your Simple Entity] — e.g., TypeObjet
```

### 5.2 ClassMAPTable — Core Methods

```java
public abstract class ClassMAPTable implements Serializable {
    // Fields
    String nomTable;           // DB table name
    Vector champ;             // Field definitions
    String mode = "modif";    // "modif" or "select"
    boolean estHistorise;     // History tracking flag
    String nomProcedureSequence;  // ID generation procedure
    int longuerClePrimaire = 6;   // PK length
    
    // Abstract methods (must implement)
    public abstract String getNomTable();
    public abstract String getAttributIDName();
    public abstract String getTuppleID();
    public abstract void construirePK(Connection c) throws Exception;
    
    // Concrete methods (inherited)
    public String makePK(Connection c);           // Generate PK
    public ClassMAPTable createObject(String u, Connection c);   // INSERT
    public int updateToTableWithHisto(String u, Connection c);   // UPDATE
    public void deleteToTable(MapHistorique h);   // DELETE
    public void setValChamp(String col, Object val);  // Set field value
    public Object getValChamp(String col);        // Get field value
}
```

### 5.3 CGenUtil — Key Methods

```java
public class CGenUtil {
    // Search all records
    public static Object[] rechercher(ClassMAPTable e, String[] colInt, 
                                      String[] valInt, Connection c, String apresWhere);
    
    // Paginated search
    public static ResultatEtSomme rechercherPageMax(ClassMAPTable e, 
                                                    String[] colInt, String[] valInt, 
                                                    int numPage, String apresWhere, 
                                                    String[] nomColSomme, Connection c, int npp);
    
    // Build WHERE clause from entity fields
    public static String makeWhere(ClassMAPTable e, Connection c);
    
    // Get field value via reflection
    public static Object getValeurFieldByMethod(Object obj, String fieldName);
    
    // Set field value via reflection
    public static void setValChamp(Object obj, String fieldName, Object value);
}
```

### 5.4 UtilDB — Connection Management

```java
public class UtilDB {
    Connection conn;
    static Properties properties = PropertiesLoader.getInstance().getProperties();
    
    // Constructor with credentials
    public UtilDB(String user, String passWOracle) {
        InitialContext jndiContext = getContext();
        DataSource ds = (DataSource)jndiContext.lookup("java:VVFinances");
        this.conn = ds.getConnection(user, passWOracle);
    }
    
    // JNDI context
    public static InitialContext getContext() {
        Properties env = new Properties();
        env.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        env.put("java.naming.provider.url", "localhost:1099");
        return new InitialContext(env);
    }
    
    // Get connection (used everywhere)
    public Connection GetConn() { return conn; }
}
```

---

## 6. Entity System

### 6.1 Entity Types

| Type | Base Class | Example | Table |
|------|-----------|---------|-------|
| Simple | `ClassMAPTable` | `TypeObjet` | `SEXE` |
| Stateful | `ClassEtat` | `Lot` | `LOT` |
| Master | `ClassMere` | `ReceptionPoussinAeroport` | `RECEPTIONPOUSSINAEROPORT` |
| Detail | `ClassFille` | `ReceptionPoussinAeroportDetail` | `RECEPTIONPOUSSINAEROPORTDETAIL` |

### 6.2 Entity Definition Pattern

```java
package ferme.lot;
import bean.ClassEtat;
import java.sql.Connection;

public class Lot extends ClassEtat {
    // Fields (auto-mapped to DB columns)
    private String id;
    private String nomlot;
    private String reference;
    private Date dateeclosion;
    private Date datearrivee;
    // ... more fields
    
    // Constructor — set table name
    public Lot() throws Exception {
        this.setNomTable("LOT");
    }
    
    // PK generation
    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("LOT", "getseq_lot");
        this.setId(this.makePK(c));
    }
    
    // Required abstract methods
    @Override
    public String getTuppleID() { return id; }
    
    @Override
    public String getAttributIDName() { return "id"; }
    
    // Getters and setters...
}
```

### 6.3 Master-Detail Pattern

```java
// Master entity
public class ReceptionPoussinAeroport extends ClassMere {
    private String id;
    private String idLot;
    private Date daty;
    private double qteRecus;
    // ...
    
    public ReceptionPoussinAeroport() throws Exception {
        super.setNomTable("receptionPoussinAeroport");
        this.setNomClasseFille("ferme.receptionaeroport.ReceptionPoussinAeroportDetail");
        this.setLiaisonFille("idMere");
    }
}

// Detail entity
public class ReceptionPoussinAeroportDetail extends ClassFille {
    private String id;
    private String idMere;
    private String idSexe;
    private double qte;
    private String idQualitePoussin;
    // ...
}
```

---

## 7. UI Generation System

### 7.1 Page Types

| Class | Purpose | JSP Usage |
|-------|---------|-----------|
| `PageRecherche` | List/Search page | `*-liste.jsp` |
| `PageInsert` | Insert/Update form | `*-saisie.jsp` |
| `PageInsertMultiple` | Master-detail form | `*-saisie.jsp` (with details) |
| `PageCalendrier` | Event calendar | Calendar pages |

### 7.2 PageRecherche — List Page

```java
public class PageRecherche extends Page {
    private ClassMAPTable[] liste;
    private ResultatEtSomme rs;
    private int numPage = 1;
    private int npp = 0;
    private String aWhere = "";
    private static final int[] NPP_CHOIX = {25, 50, 100, 200};
    
    // Generate complete list page
    public void creerObjetPage(String[] libEntete, String[] colSomme) {
        // 1. Build search form
        // 2. Execute search via CGenUtil
        // 3. Generate HTML table
        // 4. Add pagination
    }
}
```

### 7.3 PageInsert — Form Page

```java
public class PageInsert extends Page {
    private ClassMAPTable critere;
    
    // Constructor handles 3 modes
    public PageInsert(ClassMAPTable o, HttpServletRequest req, UserEJB u) {
        if (req.getParameter("acte").equalsIgnoreCase("update")) {
            // Load existing data for editing
        } else if (req.getParameter("acte").equalsIgnoreCase("dupliquer")) {
            // Load data but clear ID
        } else {
            // New insert form
        }
    }
}
```

### 7.4 Formulaire — Form Generator

```java
public class Formulaire {
    private Champ[] listeChamp;
    private ClassMAPTable objet;
    
    // Generate complete form HTML
    public String getHtmlEnsemble() {
        // 1. Search fields
        // 2. Grouping options
        // 3. Column selection
        // 4. Sort options
        // 5. Action buttons
    }
    
    // Generate insert form HTML
    public void makeHtmlInsertTabIndex() {
        // Table with input fields
        // Handles: autocomplete, date pickers, popups
    }
}
```

---

## 8. Controller Layer

### 8.1 Main Controller — ApresMultiple

**File:** `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/src/java/servlet/ApresMultiple.java`

This is the **central POST handler** for all form submissions. It:
1. Reads hidden fields (`classe`, `acte`, `bute`)
2. Loads the entity class dynamically via `Class.forName()`
3. Calls `UserEJB.createObject()` or `updateObject()`
4. Redirects to the target page

```java
@WebServlet("/ApresMultiple")
@MultipartConfig
public class ApresMultiple extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String classe = request.getParameter("classe");
        String acte = request.getParameter("acte");
        String bute = request.getParameter("bute");
        
        // Load entity class dynamically
        Class<?> clazz = Class.forName(classe);
        ClassMAPTable obj = (ClassMAPTable) clazz.newInstance();
        
        // Process based on action
        UserEJB u = (UserEJB) request.getSession().getAttribute("u");
        u.createObject(obj);  // or updateObject, etc.
        
        // Redirect
        response.sendRedirect("module.jsp?but=" + bute);
    }
}
```

### 8.2 Other Key Servlets

| Servlet | URL | Purpose |
|---------|-----|---------|
| `CompteServlet` | `/CompteServlet` | Account CRUD |
| `IngredientsRemiseServlet` | `/IngredientsRemiseServlet` | Discount API (JSON) |
| `DeviseServlet` | `/DeviseServlet` | Currency |
| `EtatComptableServlet` | `/EtatComptableServlet` | Accounting state |
| `Reporting` | `/Reporting` | Reports |
| `ApresTarif` | `/ApresTarif` | Post-tariff |
| `CSVUploadServlet` | `/CSVUploadServlet` | CSV import |
| `BalanceExportServlet` | `/BalanceExportServlet` | Balance export |

---

## 9. Service Layer

### 9.1 UserEJBBean — The Central Service

**File:** `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-ejb/src/java/user/UserEJBBean.java`

This is the **most important class** in the application. It's a stateful session bean that:

#### Authentication
```java
public void testLogin(String user, String pass, String interim, String service) {
    // 1. Validate credentials
    u = testeValide(user, pass, interim, service, c);
    // 2. Load user context
    personnel = new LogPersonnel();
    // 3. Load roles and permissions
    ActionRole[] actionRoles = uVue.getActionRoles(c);
    this.setActionRole(actionRoles);
    // 4. Load configuration
    this.setListeConfig((Configuration[]) CGenUtil.rechercher(...));
    // 5. Load company info
    this.setMaSociete((Societe) CGenUtil.rechercher(...)[0]);
}
```

#### CRUD Operations
```java
// Create
public Object createObject(ClassMAPTable o) throws Exception {
    Connection c = new UtilDB().GetConn();
    c.setAutoCommit(false);
    try {
        o.setUserEJBBean(this);
        Object result = o.createObject(u.getTuppleID(), c);
        c.commit();
        return result;
    } catch (Exception ex) {
        c.rollback();
        throw ex;
    } finally {
        c.close();
    }
}

// Update
public Object updateObject(ClassMAPTable o) throws Exception {
    // Similar pattern with transaction
}

// Delete
public void deleteObject(ClassMAPTable o) throws Exception {
    // Similar pattern with transaction
}
```

#### Master-Detail Operations
```java
public Object createObjectMultiple(ClassMAPTable mere, String colonneMere, ClassMAPTable[] fille) {
    // 1. Create master
    // 2. Get master ID
    // 3. Set master ID in all details
    // 4. Create all details
}
```

#### Permission Check
```java
boolean estAutorise = ActionRole.estAutorise(this.getActionRole(), this.getuVue(), "update", mere);
if (estAutorise == false) {
    throw new Exception("Action de modification non autorise");
}
```

---

## 10. Data Access Layer

### 10.1 How Data Flows

```
JSP Page
    │
    │ Creates entity, calls CGenUtil
    ▼
CGenUtil
    │
    │ Uses reflection to build SQL
    ▼
ClassMAPTable
    │
    │ Executes SQL via JDBC
    ▼
UtilDB
    │
    │ Gets connection from JNDI
    ▼
Oracle Database
```

### 10.2 Query Building

```java
// CGenUtil.rechercher() builds SQL like:
// SELECT * FROM LOT WHERE ID = 'LOT0001' ORDER BY NOMLOT ASC

// Step 1: Get column metadata
Champ[] columns = ListeColonneTable.getFromListe(entity, connection);

// Step 2: Build WHERE clause
String where = CGenUtil.makeWhere(entity, connection);

// Step 3: Execute query
Statement stmt = connection.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName + " " + where + " " + apresWhere);

// Step 4: Map results to objects
Object[] results = new Object[count];
for (int i = 0; i < count; i++) {
    results[i] = entity.getClass().newInstance();
    for (Champ col : columns) {
        CGenUtil.setValChamp(results[i], col.getNomColonne(), rs.getObject(col.getNomColonne()));
    }
}
```

---

## 11. Database Schema

### 11.1 Ferme Domain Tables

#### LOT
```sql
CREATE TABLE "AKOHO"."LOT" (
    "ID" VARCHAR2(100),
    "NOMLOT" VARCHAR2(200),
    "REFERENCE" VARCHAR2(200),
    "DATEECLOSION" DATE,
    "DATEARRIVEE" DATE,
    "SOURCE" VARCHAR2(255),
    "IDFERME" VARCHAR2(100),
    "IDARTICLE" VARCHAR2(100),
    "IDORIGINE" VARCHAR2(100),
    "IDPROGRAMME" VARCHAR2(100),
    "ETAT" NUMBER,
    "IDSOUCHE" VARCHAR2(50),
    "IDCATEGORIELOT" VARCHAR2(50),
    "DATECLOTURE" DATE,
    CONSTRAINT "LOT_PK" PRIMARY KEY ("ID"),
    FOREIGN KEY ("IDSOUCHE") REFERENCES "AKOHO"."SOUCHE" ("ID"),
    FOREIGN KEY ("IDFERME") REFERENCES "AKOHO"."MAGASIN2" ("ID"),
    FOREIGN KEY ("IDCATEGORIELOT") REFERENCES "AKOHO"."CATEGORIELOT" ("ID"),
    FOREIGN KEY ("IDORIGINE") REFERENCES "AKOHO"."ORIGINELOT" ("ID"),
    FOREIGN KEY ("IDARTICLE") REFERENCES "AKOHO"."AS_INGREDIENTS" ("ID"),
    FOREIGN KEY ("IDPROGRAMME") REFERENCES "AKOHO"."PROGRAMMEZOOTECHNIQUE" ("ID")
);
```

#### RECEPTIONPOUSSINAEROPORT
```sql
CREATE TABLE "AKOHO"."RECEPTIONPOUSSINAEROPORT" (
    "ID" VARCHAR2(50),
    "IDLOT" VARCHAR2(50) NOT NULL,
    "DATY" DATE,
    "QTERECUS" NUMBER(10,2),
    "REMARQUE" VARCHAR2(500),
    "NBRCARTONMALE" NUMBER,
    "NBRCARTONFEMELLE" NUMBER,
    "ETAT" NUMBER,
    "HEUREDEPART" VARCHAR2(10),
    "HEUREARRIVE" VARCHAR2(10),
    PRIMARY KEY ("ID"),
    FOREIGN KEY ("IDLOT") REFERENCES "AKOHO"."LOT" ("ID")
);
```

#### RECEPTIONPOUSSINAEROPORTDETAIL
```sql
CREATE TABLE "AKOHO"."RECEPTIONPOUSSINAEROPORTDETAIL" (
    "ID" VARCHAR2(50),
    "IDMERE" VARCHAR2(50) NOT NULL,
    "IDQUALITEPOUSSIN" VARCHAR2(50),
    "IDSEXE" VARCHAR2(50),
    "QTE" NUMBER(10,2),
    PRIMARY KEY ("ID"),
    FOREIGN KEY ("IDSEXE") REFERENCES "AKOHO"."SEXE" ("ID"),
    FOREIGN KEY ("IDQUALITEPOUSSIN") REFERENCES "AKOHO"."QUALITEPOUSSIN" ("ID"),
    FOREIGN KEY ("IDMERE") REFERENCES "AKOHO"."RECEPTIONPOUSSINAEROPORT" ("ID")
);
```

#### TRANSFERTPOULET
```sql
CREATE TABLE "AKOHO"."TRANSFERTPOULET" (
    "ID" VARCHAR2(50),
    "DATY" DATE,
    "IDLOT" VARCHAR2(50) NOT NULL,
    "REMARQUE" VARCHAR2(500),
    "ETAT" NUMBER,
    PRIMARY KEY ("ID"),
    FOREIGN KEY ("IDLOT") REFERENCES "AKOHO"."LOT" ("ID")
);
```

#### TRIAGEBATIMENTPARQUET
```sql
CREATE TABLE "AKOHO"."TRIAGEBATIMENTPARQUET" (
    "ID" VARCHAR2(100),
    "DATY" DATE,
    "IDLOT" VARCHAR2(100),
    "DISPOMALE" NUMBER,
    "DISPOFEMELLE" NUMBER,
    "ETAT" NUMBER,
    CONSTRAINT "TRIAGEBATPARQ_PK" PRIMARY KEY ("ID"),
    FOREIGN KEY ("IDLOT") REFERENCES "AKOHO"."LOT" ("ID")
);
```

#### V_QTE_DISPO_LOT_SEXE_QUALITE (View)
```sql
CREATE OR REPLACE FORCE VIEW "AKOHO"."V_QTE_DISPO_LOT_SEXE_QUALITE" AS 
SELECT
    m.IDLOT AS id,
    d.IDSEXE AS idsexe,
    d.IDQUALITEPOUSSIN AS idqualite,
    SUM(d.QTE) AS qte
FROM TRIAGEBATIMENTPARQUETDETAIL d
JOIN TRIAGEBATIMENTPARQUET m ON d.IDMERE = m.ID
GROUP BY m.IDLOT, d.IDSEXE, d.IDQUALITEPOUSSIN;
```

### 11.2 Configuration Tables

| Table | Purpose | Key Columns |
|-------|---------|-------------|
| `SEXE` | Gender options | `ID`, `VAL` |
| `QUALITEPOUSSIN` | Chick quality | `ID`, `VAL` |
| `SOUCHE` | Chicken strains | `ID`, `VAL` |
| `ORIGINELOT` | Lot origins | `ID`, `VAL` |
| `CATEGORIELOT` | Lot categories | `ID`, `VAL` |
| `PROGRAMMEZOOTECHNIQUE` | Zootechnical programs | `ID`, `CODE`, `VERSION` |
| `BATIMENT` | Buildings | `ID`, `NOMBATIMENT`, `IDFERME` |
| `PARQUET` | Compartments | `ID`, `VAL` |
| `PARQUETBATIMENT` | Building-compartment links | `ID`, `IDBATIMENT`, `IDPARQUET` |
| `MAGASIN2` | Farms/sites | `ID`, `VAL` |
| `VEHICULE` | Vehicles | `ID`, `VAL` |

### 11.3 Sequences

| Sequence | Used By |
|----------|---------|
| `getseq_lot` | `LOT` |
| `GETSEQRECEPTIONPOUSSINAEROPORT` | `RECEPTIONPOUSSINAEROPORT` |
| `GETSEQTRANSFERTPOULET` | `TRANSFERTPOULET` |
| `getseq_triagebatimentparquet` | `TRIAGEBATIMENTPARQUET` |
| `getseq_programmezootechnique` | `PROGRAMMEZOOTECHNIQUE` |

---

## 12. State Machine

### 12.1 States

```
                    ┌─────────────┐
                    │   CREATED   │ (1)
                    │   CRÉÉ(E)   │
                    └──────┬──────┘
                           │
              ┌────────────┼────────────┐
              │            │            │
              ▼            ▼            ▼
       ┌──────────┐ ┌──────────┐ ┌──────────┐
       │ CANCELLED│ │ VALIDATED│ │ REJECTED │
       │ ANNULÉ(E)│ │ TRAITÉ(E)│ │ REJETÉ(E)│
       │   (0)    │ │   (11)   │ │   (10)   │
       └──────────┘ └────┬─────┘ └──────────┘
                          │
                          ▼
                   ┌──────────┐
                   │  CLOSED  │
                   │CLOTURÉ(E)│
                   │   (9)    │
                   └──────────┘
```

### 12.2 State Transition Rules

| From | To | Condition |
|------|-----|-----------|
| Created (1) | Validated (11) | User has validate permission |
| Created (1) | Cancelled (0) | User has cancel permission |
| Validated (11) | Closed (9) | User has close permission |
| Validated (11) | Cancelled (0) | User has cancel permission |
| Any | Rejected (10) | User has reject permission |

### 12.3 Read-Only Rule

Once an entity reaches state `11` (Validated) or higher, it becomes **read-only**:
```java
public int updateObject(String u, Connection c) throws Exception {
    if (this.getEtat() >= ConstanteEtat.getEtatValider()) {
        throw new Exception("Impossible de modifier un objet deja valide");
    }
    return this.updateToTableWithHisto(u, c);
}
```

---

## 13. Security & Permissions

### 13.1 Role Levels

| Level | Role | Permissions |
|-------|------|-------------|
| 4+ | Admin (DG) | Full access |
| 3 | Manager | Create, update, validate |
| 1-2 | Staff | Create only |

### 13.2 Permission Check

```java
// In UserEJBBean:
boolean estAutorise = ActionRole.estAutorise(
    this.getActionRole(),      // User's roles
    this.getuVue(),            // User info
    "update",                  // Action
    mere                       // Entity
);
if (estAutorise == false) {
    throw new Exception("Action non autorise");
}
```

### 13.3 ActionRole Table

```sql
CREATE TABLE "AKOHO"."ACTIONROLE" (
    "ID" VARCHAR2(50),
    "NOMTABLEOBJET" VARCHAR2(100),  -- Entity table name
    "ACTION" VARCHAR2(50),           -- create/update/validate/delete
    "ROLEMINIMUM" NUMBER,           -- Minimum role level
    "DIRECTION" VARCHAR2(100)        -- Department
);
```

---

## 14. Audit Trail

### 14.1 History Entity

**File:** `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/historique/MapHistorique.java`

```java
public class MapHistorique extends ClassMAPTable {
    private String id;
    private String nomTable;        // Affected table
    private String action;          // insert/update/delete
    private String idUtilisateur;    // Who did it
    private String idObjet;         // Which record
    private Date dateHistorique;    // When
    private String objet;           // Java class name
}
```

### 14.2 Automatic History

Every CREATE, UPDATE, DELETE automatically records:
```java
MapHistorique histo = new MapHistorique();
histo.setNomTable("LOT");
histo.setAction("Insertion par " + userId);
histo.setIdUtilisateur(userId);
histo.setIdObjet(lot.getId());
histo.setObjet("ferme.lot.Lot");
histo.insertToTable(connection);
```

---

## 15. Ferme Workflow — Complete Flow

### 15.1 Overview

```
┌─────────────────────────────────────────────────────────────────────────┐
│                        FERME WORKFLOW                                    │
│                                                                          │
│  PREREQUISITES (One-time setup)                                         │
│  ├── Create Souche (Strain)                                             │
│  ├── Create OrigineLot (Origin)                                         │
│  ├── Create QualitePoussin (Quality)                                    │
│  ├── Create ProgrammeZootechnique (Feeding program)                     │
│  ├── Create Batiment (Building)                                         │
│  ├── Create Parquet (Compartment)                                       │
│  └── Create Vehicule (Vehicle)                                          │
│                                                                          │
│  MAIN WORKFLOW (Per batch)                                              │
│  ├── STEP 1: Create Lot                                                 │
│  │   └── lot-saisie.jsp → ApresMultiple.jsp → LOT table                 │
│  │                                                                       │
│  ├── STEP 2: Receive Chickens at Airport                               │
│  │   └── receptionaeroport-saisie.jsp → ApresMultiple.jsp               │
│  │       → RECEPTIONPOUSSINAEROPORT + DETAIL tables                     │
│  │                                                                       │
│  ├── STEP 3: Transfer to Farm (Camion)                                  │
│  │   └── transfertpoulet-saisie.jsp → ApresMultiple.jsp                  │
│  │       → TRANSFERTPOULET + DETAIL tables                              │
│  │                                                                       │
│  └── STEP 4: Sort into Compartments                                     │
│      └── triagebatimentparquet-saisie.jsp → ApresMultiple.jsp           │
│          → TRIAGEBATIMENTPARQUET + DETAIL tables                        │
└─────────────────────────────────────────────────────────────────────────┘
```

### 15.2 Step 1: Create Lot

| Aspect | Details |
|--------|---------|
| **JSP** | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/pages/ferme/lot/lot-saisie.jsp` |
| **Entity** | `Lot.java` (`/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-ejb/src/java/ferme/lot/Lot.java`) |
| **Table** | `LOT` |
| **PK** | `getseq_lot` sequence, prefix `"LOT"` |
| **Parent** | `ClassEtat` |
| **Validations** | Name/reference unique, hatching date before arrival |

**Flow:**
```
User fills form
    ↓
POST to ApresMultiple.jsp
    ├── classe = "ferme.lot.Lot"
    ├── acte = "insert"
    └── bute = "ferme/lot/lot-fiche.jsp"
    ↓
ApresMultiple loads Lot class
    ↓
UserEJB.createObject(lot)
    ↓
Lot.createObject()
    ├── checkDates()
    └── checkNomLotEtReference()
    ↓
INSERT INTO LOT (id, nomlot, reference, dateeclosion, datearrivee, ...)
    ↓
Redirect to lot-fiche.jsp
```

### 15.3 Step 2: Receive Chickens at Airport

| Aspect | Details |
|--------|---------|
| **JSP** | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/pages/ferme/receptionaeroport/receptionaeroport-saisie.jsp` |
| **Master** | `ReceptionPoussinAeroport.java` (`/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-ejb/src/java/ferme/receptionaeroport/ReceptionPoussinAeroport.java`) |
| **Detail** | `ReceptionPoussinAeroportDetail.java` |
| **Tables** | `RECEPTIONPOUSSINAEROPORT`, `RECEPTIONPOUSSINAEROPORTDETAIL` |
| **PK** | `GETSEQRECEPTIONPOUSSINAEROPORT` sequence, prefix `"RPA"` |
| **Parent** | `ClassMere` |
| **Link** | `idMere` |
| **Validation** | Detail quantities sum to master `qteRecus` |

**Flow:**
```
User fills form (master + detail rows)
    ↓
POST to ApresMultiple.jsp
    ├── classe = "ferme.receptionaeroport.ReceptionPoussinAeroport"
    ├── classefille = "ferme.receptionaeroport.ReceptionPoussinAeroportDetail"
    ├── acte = "insert"
    ├── bute = "ferme/receptionaeroport/receptionaeroport-fiche.jsp"
    ├── colonneMere = "idMere"
    └── nombreLigne = 10
    ↓
PageInsertMultiple processes master + details
    ↓
UserEJB.createObjectMultiple(mere, fille)
    ↓
ReceptionPoussinAeroport.createObject()
    └── checkQteFille() — validates detail sum
    ↓
INSERT INTO RECEPTIONPOUSSINAEROPORT (id, idLot, daty, ...)
INSERT INTO RECEPTIONPOUSSINAEROPORTDETAIL (id, idMere, idSexe, qte, ...)
    ↓
Redirect to receptionaeroport-fiche.jsp
```

### 15.4 Step 3: Transfer to Farm

| Aspect | Details |
|--------|---------|
| **JSP** | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/pages/ferme/transfertpoulet/transfertpoulet-saisie.jsp` |
| **Master** | `TransfertPoulet.java` (`/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-ejb/src/java/ferme/transfertpoulet/TransfertPoulet.java`) |
| **Detail** | `TransfertPouletDetail.java` |
| **Tables** | `TRANSFERTPOULET`, `TRANSFERTPOULETDETAIL` |
| **PK** | `GETSEQTRANSFERTPOULET` sequence, prefix `"TRSP"` |
| **Parent** | `ClassMere` |
| **Link** | `idMere` |

### 15.5 Step 4: Sort into Compartments

| Aspect | Details |
|--------|---------|
| **JSP** | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/pages/ferme/triagebatiment/triagebatimentparquet-saisie.jsp` |
| **Master** | `TriageBatimentParquet.java` (`/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-ejb/src/java/ferme/triageBatiment/TriageBatimentParquet.java`) |
| **Detail** | `TriageBatimentParquetDetail.java` |
| **Tables** | `TRIAGEBATIMENTPARQUET`, `TRIAGEBATIMENTPARQUETDETAIL` |
| **PK** | `getseq_triagebatimentparquet` sequence, prefix `"TB"` |
| **Parent** | `ClassMere` |
| **Link** | `idmere` |
| **Availability View** | `v_qte_dispo_lot_sexe_qualite` |

**Note:** Quantity validation is currently **commented out** in `TriageBatimentParquet.createObject()`.

---

## 16. Calendar Systems

### 16.1 CaCalendrier — Revenue Calendar

| Aspect | Details |
|--------|---------|
| **File** | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-ejb/src/java/vente/CaCalendrier.java` |
| **Purpose** | Display CA (revenue) by day |
| **Data Source** | `CaJourDetailPourCalendrier` entity |
| **UI** | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/pages/vente/analyse/akoho-prev.jsp` |
| **Format** | Rows = days (1-31), Columns = dates, Cells = revenue |

### 16.2 PageCalendrier — Event Calendar

| Aspect | Details |
|--------|---------|
| **File** | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/affichage/PageCalendrier.java` |
| **Purpose** | Generic event calendar |
| **Data Source** | `Evenement` entity |
| **UI** | FullCalendar (JavaScript) |

### 16.3 Calendrier — Themed Calendar

| Aspect | Details |
|--------|---------|
| **File** | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/affichage/Calendrier.java` |
| **Purpose** | Event calendar with legends |
| **Data Source** | `CalendrierElement` objects |

---

## 17. Build & Deployment

### 17.1 Build Commands

```bash
# Main build
ant build

# Tomcat deployment
ant deploy-tomcat

# Jenkins deployment
ant deploy-jenkins
```

### 17.2 Build Files

| File | Purpose |
|------|---------|
| `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/build.xml` | Main Ant build |
| `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/build-tomcat.xml` | Tomcat build |
| `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/build-jenkins.xml` | Jenkins build |
| `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/Dockerfile` | Docker config |

### 17.3 Deployment

```
Docker Container
    └── Tomcat 9
        ├── arbiochem.war
        │   ├── WEB-INF/lib/apj-core.jar
        │   ├── WEB-INF/lib/apj-core2.jar
        │   ├── WEB-INF/lib/arbiochem-ejb.jar
        │   └── web pages, servlets, assets
        └── JNDI DataSource: java:VVFinances
            └── Oracle Database (AKOHO schema)
```

---

## 18. Development Guide

### 18.1 Adding a New Entity

1. **Create entity class** in `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-ejb/src/java/yourpackage/`
2. **Create JSP form** in `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/pages/yourpackage/your-entity-saisie.jsp`
3. **Create JSP list** in `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/pages/yourpackage/your-entity-liste.jsp`
4. **Create JSP detail** in `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/pages/yourpackage/your-entity-fiche.jsp`
5. **Add DB table** and sequence
6. **Build and deploy**

### 18.2 Adding a New Servlet

1. **Create servlet class** in `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/src/java/servlet/`
2. **Annotate with** `@WebServlet("/YourServlet")`
3. **Implement** `doGet()` and/or `doPost()`
4. **Build and deploy**

### 18.3 Common Patterns

**Autocomplete field:**
```java
pi.getFormu().getChamp("idLot").setPageAppelComplete("ferme.lot.Lot", "id", "LOT");
```

**Dropdown list:**
```java
TypeObjet sexe = new TypeObjet();
sexe.setNomTable("SEXE");
Liste sexeListe = new Liste("idSexe", sexe, "val", "id");
pi.getFormu().changerEnChamp(new Champ[]{sexeListe});
```

**Dependent dropdown:**
```java
Batiment batiment = new Batiment();
batiment.setNomTable("BATIMENT");
Liste batListe = new Liste("idbatiment", batiment, "nomBatiment", "id");
ParquetBatiment parquet = new ParquetBatiment();
Liste parqListe = new Liste("idparquet", parquet, "val", "id");
parqListe.setDeroulanteDependante(batListe, "idbatiment", "onchange");
```

---

## 19. Sprint Specifications

### 19.1 Sprint 1 — Aeroport → Camion → Repartition

**File:** `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/docs/sprint/sprint1.md`

```
Scenario: Aeroport → Camion → Repartition dans Compartiment

Step 1: Aeroport
- Bon de Commandes
- Bon de livraison
- Bon de Reception
- Record les morts et les vivants

Step 2: Trajet
A chaque arrive de trajet:
- bon de reception
- record les morts et les vivants

Step 3: Ferme: repartition dans les compartiments/parquets
- choisir le nombre, le sexe
- choisir l'indice de sante: 1 a 10
```

### 19.2 Sprint 2 — Prevision du nombre de poulets dans le calendrier

**File:** `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/docs/sprint/sprint2.md`

```
Prevision du nombre de poulets dans le calendrier:

On cherche a donne un nombre cumule de nombre de poulet dans une ferme tous les jours.
Quand on clique on a POUR CHAQUE LOT:
    - le nombre de poulet adulte
    - le nombre de poussin
    - le nombre de mort
    - le nombre total de poulet + poussin + mort dans le lot
    - le nombre total de poulet et de poussin dans la ferme

On peut trier par date max et min, par batiment, par lot, par compartiment/parquet.

On fait un suivie journalier de la mort/disparition des poulets

On sait:
- quand est ce que un poulet est capable de pondre
- quand est ce que un poulet va pondre
- la frequence de pondaison
- l'age ou la mort d'un poulet

On peut manipuler:
- la mort de poulet dans un lot: un poulet mort ne diminue pas le nombre de poulet
- la disparition de poulet dans un lot: des poulets disparues diminue le nombre

Logic:
Si le nombre de poulet adulte descend ou augmente, automatiquement par prevision,
le nombre de prevision change.

Affichage:
S'inspire de l'affichage de vente avec CA journalier: calendrier avec valeur
total par jour et quand on clique, on va dans une page qui affiche les details.
```

---

## 20. Troubleshooting

### 20.1 Common Errors

| Error | Cause | Solution |
|-------|-------|----------|
| `Table non existante` | Wrong table name | Check `getNomTable()` |
| `Impossible de modifier un objet deja valide` | Entity state >= 11 | Cannot edit validated records |
| `Connection closed` | Connection not closed | Check `finally` blocks |
| `Sequence does not exist` | Missing sequence | Create in DB |
| `NullPointerException` | Missing session `u` | Ensure logged in |
| `La quantité totale en détails différente` | Detail sum != master | Fix quantities |

### 20.2 Debugging

1. Check Tomcat logs for stack traces
2. Add `System.out.println()` for debugging
3. Check `HISTORIQUE` table for operations
4. Verify session has `UserEJB` as `"u"`
5. Verify JNDI `java:VVFinances` is configured

---

*Document generated: 2026-10-14*
*Project: Akoho — Chicken Farm Management System*
*Framework: APJ-Core + APJ-Core2 (Custom Java EE Framework)*
