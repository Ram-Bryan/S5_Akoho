# AKOHO PROJECT — Complete Technical Guide

> **Purpose:** This document is the single source of truth for anyone joining the Akoho project. It covers architecture, file locations, data flow, framework internals, and step-by-step workflows.

---

## Table of Contents

1. [Project Overview](#1-project-overview)
2. [Technology Stack](#2-technology-stack)
3. [Directory Structure](#3-directory-structure)
4. [Architecture Deep Dive](#4-architecture-deep-dive)
5. [The APJ Framework](#5-the-apj-framework)
6. [MVC Mapping](#6-mvc-mapping)
7. [Data Access Layer](#7-data-access-layer)
8. [State Management](#8-state-management)
9. [Role-Based Access Control](#9-role-based-access-control)
10. [History / Audit Trail](#10-history--audit-trail)
11. [The Ferme Workflow (End-to-End)](#11-the-ferme-workflow-end-to-end)
12. [Calendar Systems](#12-calendar-systems)
13. [Build & Deployment](#13-build--deployment)
14. [How to Add a New Feature](#14-how-to-add-a-new-feature)
15. [Key Patterns & Conventions](#15-key-patterns--conventions)
16. [Database Schema (Key Tables)](#16-database-schema-key-tables)
17. [Troubleshooting](#17-troubleshooting)

---

## 1. Project Overview

**Akoho** is a Java EE 8 enterprise application for managing a chicken farm operation. It covers:
- Farm management (lots, buildings, compartments)
- Chicken lifecycle (airport reception, transfer, sorting)
- Sales, purchasing, and invoicing
- Payroll and HR
- Accounting and financial reporting
- Maintenance planning
- Inventory and stock management

The application runs on **Tomcat 9** with an **Oracle database** (accessed via JNDI DataSource).

---

## 2. Technology Stack

| Layer | Technology |
|-------|-----------|
| **Language** | Java 8 |
| **Server** | Tomcat 9 |
| **Database** | Oracle (via `ojdbc6-11.jar`) |
| **Framework** | Custom APJ Framework (apj-core.jar + apj-core2.jar) |
| **Presentation** | JSP + JavaScript (jQuery, Bootstrap, FullCalendar) |
| **Business Logic** | EJB (Enterprise JavaBeans) |
| **ORM** | Custom reflection-based ORM (not JPA/Hibernate) |
| **Build** | Ant (build.xml) |
| **Deployment** | Docker (Oracle EE), WildFly support |

---

## 3. Directory Structure

```
/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/
├── framework/                          # Framework source code (decompiled from JARs)
│   ├── apj-core/                       # Core framework (ORM + UI generation)
│   └── apj-core2/                      # Extended framework (notifications, imports, reporting)
├── arbiochem-ejb/                      # EJB Module (Business Logic + Entities)
│   └── src/java/
│       ├── ferme/                      # Farm management domain
│       ├── vente/                      # Sales domain
│       ├── paie/                       # Payroll domain
│       ├── user/                       # User/auth domain
│       ├── produits/                   # Products domain
│       ├── rapport/                    # Reports domain
│       └── ... (60+ domain packages)
├── arbiochem-war/                      # WAR Module (Presentation Layer)
│   ├── src/java/servlet/               # Controllers (Servlets)
│   └── web/
│       ├── index.jsp                   # Login page
│       ├── module.jsp                  # Main framework page (post-login)
│       ├── accueil.jsp                 # Home page
│       ├── WEB-INF/web.xml             # Servlet config
│       └── pages/                      # JSP pages organized by domain
│           ├── ferme/
│           ├── vente/
│           ├── paie/
│           └── ...
├── codebar-generator/                  # Barcode generator utility
├── docs/
│   ├── ai/                             # AI documentation (this file)
│   ├── prompt/                         # AI prompts (ROLE.md, CONTEXT.md)
│   └── sprint/                         # Sprint plans (sprint1.md)
├── build.xml                           # Main Ant build file
├── build-tomcat.xml                    # Tomcat-specific build
└── Dockerfile                          # Docker deployment config
```

---

## 4. Architecture Deep Dive

### 4.1 High-Level Architecture

```
┌─────────────────────────────────────────────────────────────────────────┐
│                        CLIENT (Browser)                                  │
│  HTML + CSS + JavaScript (jQuery, Bootstrap, FullCalendar)              │
└────────────────────────────────┬────────────────────────────────────────┘
                                 │ HTTP (GET/POST)
                                 ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                     PRESENTATION LAYER (WAR)                             │
│  ┌──────────────────────┐  ┌──────────────────────────────────────────┐ │
│  │   JSP Pages (View)   │  │   Servlets (Controller)                  │ │
│  │   /web/pages/        │  │   @WebServlet annotated                  │ │
│  │   - module.jsp       │  │   - ApresMultiple.jsp (main handler)     │ │
│  │   - *-saisie.jsp     │  │   - CompteServlet.java                   │ │
│  │   - *-liste.jsp      │  │   - DeviseServlet.java                   │ │
│  │   - *-fiche.jsp      │  │   - Reporting.java                       │ │
│  └──────────┬───────────┘  └──────────────────┬───────────────────────┘ │
│             │                                 │                          │
│             └──────────────┬──────────────────┘                          │
│                            ▼                                             │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │  APJ UI Framework (apj-core.jar)                                   │ │
│  │  PageRecherche — generates search/list HTML from entity metadata    │ │
│  │  PageInsert — generates insert/update HTML forms                   │ │
│  │  Formulaire — form generator (fields, buttons, autocomplete)       │ │
│  │  Champ / Liste — individual form field / dropdown                   │ │
│  │  TableauRecherche — data table with pagination                     │ │
│  └────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────┘
                                 │
                                 ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                   BUSINESS LOGIC LAYER (EJB)                             │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │  UserEJBBean (Stateful Session Bean)                               │ │
│  │  - Session management, authentication (testLogin)                  │ │
│  │  - CRUD operations (createObject, updateObject, deleteObject)      │ │
│  │  - Transaction management (manual commit/rollback)                 │ │
│  │  - History/audit trail (MapHistorique)                            │ │
│  │  - Role-based access control (ActionRole.estAutorise)             │ │
│  └────────────────────────────────────────────────────────────────────┘ │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │  Domain EJBs (60+ packages: ferme, vente, paie, produits, etc.)   │ │
│  │  Each contains entities extending ClassMAPTable / ClassMere        │ │
│  └────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────┘
                                 │
                                 ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                   DATA ACCESS LAYER (Framework)                          │
│  ┌────────────────────────────────────────────────────────────────────┐ │
│  │  ClassMAPTable (abstract base class for all entities)              │ │
│  │  - Uses reflection to map Java fields to DB columns               │ │
│  │  - Generates SQL INSERT/UPDATE/SELECT dynamically                   │ │
│  │  - Handles primary key generation via DB sequences                │ │
│  │                                                                    │ │
│  │  CGenUtil (static CRUD utility)                                   │ │
│  │  - rechercher() — builds SQL from entity metadata                 │ │
│  │  - rechercherPageMax() — paginated queries                        │ │
│  │  - makeWhere() — builds WHERE clauses                             │ │
│  │                                                                    │ │
│  │  UtilDB (database connection management)                           │ │
│  │  - JNDI DataSource lookup: java:VVFinances                        │ │
│  │  - Supports Oracle and PostgreSQL                                 │ │
│  │  - Connection counting via ConnectionProxy                        │ │
│  └────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────┘
                                 │
                                 ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                   DATABASE (Oracle via JNDI)                             │
│  DataSource: java:VVFinances                                            │
│  Schema: AKOHO                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### 4.2 Module Dependencies

```
arbiochem-ejb (EJB JAR)
    ├── depends on: apj-core.jar, apj-core2.jar
    └── contains: All entities, UserEJBBean, domain logic

arbiochem-war (WAR)
    ├── depends on: arbiochem-ejb.jar, apj-core.jar, apj-core2.jar
    └── contains: Servlets, JSPs, web assets

apj-core.jar
    ├── contains: ORM (ClassMAPTable, CGenUtil), UI (PageRecherche, Formulaire)
    └── source: /framework/apj-core/

apj-core2.jar
    ├── contains: Notifications, imports, reporting, menu
    └── source: /framework/apj-core2/
```

---

## 5. The APJ Framework

The APJ framework is a **custom in-house framework** that eliminates boilerplate by:
1. Generating SQL from Java entity metadata (reflection)
2. Generating HTML forms and tables from entity metadata
3. Handling CRUD operations, pagination, and validation automatically

### 5.1 ORM Layer (`bean` package)

#### `ClassMAPTable` — The Base Entity Class
**File:** `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/bean/ClassMAPTable.java`

```java
public abstract class ClassMAPTable implements Serializable {
    String nomTable;           // Maps Java object to DB table name
    Vector champ;             // Field definitions (auto-populated from class fields)
    String mode = "modif";    // "modif" or "select"
    boolean estHistorise;     // Whether to track history
    String nomProcedureSequence = "GetSeqExecutions";  // ID generation procedure
    int longuerClePrimaire = 6;
    
    // Key methods:
    // - getNomTable() — returns DB table name (abstract)
    // - getAttributIDName() — returns PK column name (abstract)
    // - getTuppleID() — returns PK value (abstract)
    // - makePK(Connection c) — generates PK via DB sequence
    // - createObject(String u, Connection c) — INSERT
    // - updateToTableWithHisto(u, c) — UPDATE with history
    // - deleteToTable(histo) — DELETE with history
}
```

**All entities extend `ClassMAPTable`.** The framework uses reflection to:
- Read class fields and map them to DB columns
- Generate SQL INSERT/UPDATE/SELECT statements
- Handle primary key generation via DB sequences

#### `CGenUtil` — The CRUD Utility
**File:** `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/bean/CGenUtil.java`

```java
public class CGenUtil {
    // Core search — builds SQL from entity metadata
    public static Object[] rechercher(ClassMAPTable e, String[] colInt, 
                                      String[] valInt, Connection c, String apresWhere);
    
    // Paginated search
    public static ResultatEtSomme rechercherPageMax(ClassMAPTable e, ..., int numPage, ...);
    
    // WHERE clause builder
    public static String makeWhere(ClassMAPTable e, Connection c);
    
    // Get field value via reflection
    public static Object getValeurFieldByMethod(Object obj, String fieldName);
}
```

#### `TypeObjet` — Dropdown Entity
**File:** `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/bean/TypeObjet.java`

```java
public class TypeObjet extends ClassMAPTable {
    private String id;
    private String val;      // Display value
    private String desce;    // Description
}
```

Used for dropdown lists in forms. Example: `TypeObjet("SEXE", ...)` maps to a `SEXE` table with options like "Mâle"/"Femelle".

### 5.2 UI Generation Layer (`affichage` package)

#### `PageRecherche` — List/Search Page Generator
**File:** `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/affichage/PageRecherche.java`

```java
public class PageRecherche extends Page {
    private ClassMAPTable[] liste;           // Data objects
    private ResultatEtSomme rs;              // Search results
    private int numPage = 1;                 // Current page
    private int npp = 0;                     // Items per page
    private String aWhere = "";              // SQL WHERE clause
    private static final int[] NPP_CHOIX = {25, 50, 100, 200};  // Page size options
    
    // Generates: search form + data table + pagination
    public void creerObjetPage(String[] libEntete, String[] colSomme);
}
```

#### `PageInsert` — Insert/Update Form Generator
**File:** `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/affichage/PageInsert.java`

```java
public class PageInsert extends Page {
    private ClassMAPTable critere;           // Entity for update mode
    
    // Constructor handles 3 modes:
    public PageInsert(ClassMAPTable o, HttpServletRequest req, UserEJB u) {
        if (req.getParameter("acte").equalsIgnoreCase("update")) {
            // Load existing data, populate form for editing
        } else if (req.getParameter("acte").equalsIgnoreCase("dupliquer")) {
            // Load data but clear ID (for duplication)
        } else {
            // New insert form
        }
    }
}
```

#### `PageInsertMultiple` — Master-Detail Form Generator
**File:** `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/affichage/PageInsertMultiple.java`

Used for entities with parent-child relationships (e.g., `ReceptionPoussinAeroport` + `ReceptionPoussinAeroportDetail`).

#### `Formulaire` — Form HTML Generator
**File:** `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/affichage/Formulaire.java`

Generates complete HTML forms including:
- Search fields
- Sort options
- Column selection
- Action buttons (Reset, Search, Save)
- Autocomplete fields
- Date pickers

#### `Champ` — Individual Form Field
**File:** `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/bean/Champ.java`

Represents a single form field (input, text, date, dropdown, etc.).

#### `Liste` — Dropdown List
**File:** `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/bean/Liste.java`

```java
public class Liste extends Champ {
    private String colAffiche;       // Column to display
    private String colValeur;        // Column for value
    private ClassMAPTable singleton; // Entity to load options from
    
    // Generates: <select> with <option> from DB data
}
```

### 5.3 Connection Layer

#### `UtilDB` — Database Connection Manager
**File:** `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/utilitaire/UtilDB.java`

```java
public class UtilDB {
    // JNDI DataSource lookup
    public UtilDB(String user, String passWOracle) {
        InitialContext jndiContext = UtilDB.getContext();
        DataSource ds = (DataSource)jndiContext.lookup("java:VVFinances");
        this.conn = ds.getConnection(user, passWOracle);
    }
    
    // Supports Oracle and PostgreSQL
    // Sets date format: "dd/mm/yyyy" for Oracle
}
```

**JNDI DataSource:** `java:VVFinances` (configured in Tomcat/WildFly)

### 5.4 Other Framework Classes

| Class | File | Purpose |
|-------|------|---------|
| `ClassMere` | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/bean/ClassMere.java` | Base for master-detail entities |
| `ClassFille` | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/bean/ClassFille.java` | Base for detail entities |
| `ClassEtat` | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/bean/ClassEtat.java` | Base for stateful entities |
| `ClassUser` | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/bean/ClassUser.java` | Base for user-linked entities |
| `ListeColonneTable` | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/bean/ListeColonneTable.java` | Column metadata reader |
| `ResultatEtSomme` | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/bean/ResultatEtSomme.java` | Paginated result container |

---

## 6. MVC Mapping

| MVC Component | Implementation | Location |
|---------------|----------------|----------|
| **Model** | `ClassMAPTable` subclasses (entities) + `UserEJBBean` (service) | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-ejb/src/java/` |
| **View** | JSP pages using `PageRecherche`/`PageInsert` | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/pages/` |
| **Controller** | `@WebServlet` annotated classes | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/src/java/servlet/` |
| **UI Framework** | `PageRecherche`, `PageInsert`, `Formulaire`, `Champ` | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/affichage/` |

### Key Files

| File | Path |
|------|------|
| Main login page | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/index.jsp` |
| Main framework page | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/pages/module.jsp` |
| Home page | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/pages/accueil.jsp` |
| Web config | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/WEB-INF/web.xml` |
| Main POST handler | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/src/java/servlet/ApresMultiple.java` |
| User EJB interface | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-ejb/src/java/user/UserEJB.java` |
| User EJB implementation | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-ejb/src/java/user/UserEJBBean.java` |

---

## 7. Data Access Layer

### 7.1 How to Query Data

**Step 1: Create an entity object**
```java
ComptaCompte cpt = new ComptaCompte();
cpt.setNomTable("COMPTE");  // Maps to DB table
```

**Step 2: Use CGenUtil to search**
```java
// Search all
ComptaCompte[] comptaComptes = (ComptaCompte[]) CGenUtil.rechercher(cpt, null, null, " ORDER BY COMPTE ASC");

// Search with criteria
ComptaCompte[] listCompta = (ComptaCompte[]) CGenUtil.rechercher(cpt, null, null, " AND COMPTE = '" + compte + "'");
```

**Step 3: Use EJB for complex operations**
```java
UserEJB u = (UserEJB) request.getSession().getAttribute("u");
u.createObject(cpt);  // Insert
u.updateObject(cpt);  // Update
u.deleteObject(cpt);  // Delete
```

### 7.2 How JSP Gets Data

```jsp
<%-- Pattern used in ALL list pages --%>
<%
    // 1. Create entity
    ProduitsTypeLibelle lv = new ProduitsTypeLibelle();
    lv.setNomTable("PRODUITSLIST_LIB");
    
    // 2. Create PageRecherche (framework handles search + display)
    PageRecherche pr = new PageRecherche(lv, request, listeCrt, listeInt, 3, libEntete, libEntete.length);
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    
    // 3. Generate HTML table from DB data
    pr.creerObjetPage(libEntete, colSomme);
    
    // 4. Output HTML
    out.println(pr.getTableau().getHtml());
%>
```

### 7.3 How JSP Gets Form Data

```jsp
<%-- Pattern used in ALL form pages --%>
<%
    // 1. Create entity
    Produits a = new Produits();
    
    // 2. Create PageInsert (framework generates form)
    PageInsert pi = new PageInsert(a, request, (user.UserEJB) session.getValue("u"));
    pi.setLien((String) session.getValue("lien"));
    
    // 3. Customize form fields
    pi.getFormu().getChamp("designation").setType("textarea");
    
    // 4. Add dropdown lists
    TypeObjet op = new TypeObjet();
    op.setNomTable("as_typeproduit");
    Liste liste = new Liste("typeproduit", op, "VAL", "id");
    pi.getFormu().changerEnChamp(new Champ[]{liste});
    
    // 5. Prepare and render
    pi.preparerDataFormu();
    pi.getFormu().makeHtmlInsertTabIndex();
    out.println(pi.getFormu().getHtmlInsert());
%>
```

---

## 8. State Management

### 8.1 State Constants
**File:** `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/utilitaire/ConstanteEtat.java`

| State Code | Constant | Meaning | Display |
|------------|----------|---------|---------|
| `1` | `etatCreer` | Created | CRÉÉ(E) — light blue |
| `11` | `etatValider` | Validated | TRAITÉ(E) — green |
| `0` | `etatAnnuler` | Cancelled | ANNULÉ(E) — orange |
| `9` | `etatCloture` | Closed | CLOTURÉ(E) — blue |
| `10` | `etatRejeter` | Rejected | REJETÉ(E) — red |

### 8.2 State Transitions

```
Created (1) ──→ Validated (11) ──→ Closed (9)
     │                │
     └──→ Cancelled (0) ←──┘
```

### 8.3 How Validation Works

**File:** `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/bean/ClassEtat.java`

```java
public Object validerObject(String u, Connection c) throws Exception {
    // If entity has roleValidation or is hierarchical, needs specific roles
    boolean estHierarchique = this.getIsHierarchique() || this.getRoleValidation() != null;
    
    // If state < 11 (not yet validated) and NOT hierarchical, auto-validate
    if (this.getEtat() > 0 && this.getEtat() < ConstanteEtat.getEtatValider() && !estHierarchique) {
        this.setEtat(constante.ConstanteEtat.getEtatValider());  // Set to 11
    }
    this.updateToTableWithHisto(u, c);
}
```

**Key rule:** Once an entity reaches state `11` (Validated), it becomes **read-only** and cannot be modified:
```java
public int updateObject(String u, Connection c) throws Exception {
    if (this.getEtat() >= ConstanteEtat.getEtatValider()) {
        throw new Exception("Impossible de modifier un objet deja valide");
    }
    return this.updateToTableWithHisto(u, c);
}
```

---

## 9. Role-Based Access Control

### 9.1 How It Works
**File:** `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core2/utilisateur/ActionRole.java`

```java
public class ActionRole extends ClassMAPTable {
    private String nomTableObjet;   // Which entity
    private String action;          // "create", "update", "validate", "delete"
    private int roleminimum;        // Minimum role level required
    private String direction;       // Which direction/department
}
```

### 9.2 Role Levels

| Level | Role | Permissions |
|-------|------|-------------|
| 4+ | Admin (DG) | Full access: create, update, validate, delete |
| 3 | Manager | Create, update, validate |
| 1-2 | Staff (Livreur, etc.) | Create only |

### 9.3 Permission Check

```java
// In UserEJBBean:
boolean estAutorise = ActionRole.estAutorise(this.getActionRole(), this.getuVue(), "update", mere);
if (estAutorise == false) {
    throw new Exception("Action de modification non autorise de " + this.getuVue().getLoginuser());
}
```

### 9.4 Admin Bypass

**As admin (rang >= 4), you can:**
- Create, update, validate, and delete any entity
- Bypass hierarchical validation rules
- Access all directions and departments

---

## 10. History / Audit Trail

### 10.1 How It Works
**File:** `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/historique/MapHistorique.java`

Every CREATE, UPDATE, and DELETE operation automatically records:
- `nomTable` — which table was affected
- `action` — "insert", "update", "delete"
- `idUtilisateur` — who did it
- `idObjet` — which record
- `dateHistorique` — when
- `objet` — Java class name

### 10.2 History Entity

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

## 11. The Ferme Workflow (End-to-End)

### 11.1 Overview

```
STEP 1: Create Lot
    ↓
STEP 2: Receive Chickens at Airport
    ↓
STEP 3: Transfer to Farm (Camion)
    ↓
STEP 4: Sort into Compartments
```

### 11.2 Step 1: Create Lot

| Aspect | Details |
|--------|---------|
| **JSP** | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/pages/ferme/lot/lot-saisie.jsp` |
| **Entity** | `Lot.java` (`/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-ejb/src/java/ferme/lot/Lot.java`) |
| **Table** | `LOT` |
| **PK** | Generated via `getseq_lot` procedure, prefix `"LOT"` |
| **Parent** | Extends `ClassEtat` |
| **Validations** | Name/reference unique, hatching date before arrival date |

**Fields:**
- `id` (auto-generated)
- `nomlot` (lot name)
- `reference` (reference)
- `dateeclosion` (hatching date)
- `datearrivee` (arrival date)
- `source` (source)
- `idferme` (farm, FK to MAGASIN2)
- `idarticle` (product, FK to AS_INGREDIENTS)
- `idorigine` (origin, FK to ORIGINELOT)
- `idprogramme` (zootechnical program, FK to PROGRAMMEZOOTECHNIQUE)
- `idsouche` (strain, FK to SOUCHE)
- `idcategorielot` (lot category, FK to CATEGORIELOT)
- `etat` (state)
- `dateCloture` (closure date)

**DB Tables:** `LOT` (INSERT), `HISTORIQUE` (audit)

### 11.3 Step 2: Receive Chickens at Airport

| Aspect | Details |
|--------|---------|
| **JSP** | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/pages/ferme/receptionaeroport/receptionaeroport-saisie.jsp` |
| **Master Entity** | `ReceptionPoussinAeroport.java` (`/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-ejb/src/java/ferme/receptionaeroport/ReceptionPoussinAeroport.java`) |
| **Detail Entity** | `ReceptionPoussinAeroportDetail.java` |
| **Tables** | `RECEPTIONPOUSSINAEROPORT` (master), `RECEPTIONPOUSSINAEROPORTDETAIL` (detail) |
| **PK** | Generated via `GETSEQRECEPTIONPOUSSINAEROPORT` procedure, prefix `"RPA"` |
| **Parent** | Extends `ClassMere` (master-detail) |
| **Link** | `idMere` |
| **Validation** | Detail quantities must sum to master `qteRecus` |

**Master Fields:**
- `id`, `idLot`, `daty`, `heureDepart`, `heureArrive`, `qteRecus`, `nbrCartonMale`, `nbrCartonFemelle`, `etat`, `remarque`

**Detail Fields:**
- `id`, `idMere`, `idSexe`, `qte`, `idQualitePoussin`

**DB Tables:** `RECEPTIONPOUSSINAEROPORT` (INSERT), `RECEPTIONPOUSSINAEROPORTDETAIL` (INSERT), `LOT` (read), `SEXE` (read), `QUALITEPOUSSIN` (read)

### 11.4 Step 3: Transfer to Farm

| Aspect | Details |
|--------|---------|
| **JSP** | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/pages/ferme/transfertpoulet/transfertpoulet-saisie.jsp` |
| **Master Entity** | `TransfertPoulet.java` (`/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-ejb/src/java/ferme/transfertpoulet/TransfertPoulet.java`) |
| **Detail Entity** | `TransfertPouletDetail.java` |
| **Tables** | `TRANSFERTPOULET` (master), `TRANSFERTPOULETDETAIL` (detail) |
| **PK** | Generated via `GETSEQTRANSFERTPOULET` procedure, prefix `"TRSP"` |
| **Parent** | Extends `ClassMere` |
| **Link** | `idMere` |

**Master Fields:**
- `id`, `daty`, `idLot`, `remarque`, `etat`

**Detail Fields:**
- `id`, `idMere`, `idSexe`, `qte`, `idFermeDepart`, `idBatimentDepart`, `idParquetDepart`, `idFermeArrive`, `idBatimentArrive`, `idParquetArrive`, `idControleur`, `idChauffeur`, `idVehicule`

**DB Tables:** `TRANSFERTPOULET` (INSERT), `TRANSFERTPOULETDETAIL` (INSERT), `MAGASIN2` (read), `BATIMENT` (read), `PARQUETBATIMENT` (read), `SEXE` (read), `VEHICULE` (read), `LOG_PERSONNEL` (read)

### 11.5 Step 4: Sort into Compartments

| Aspect | Details |
|--------|---------|
| **JSP** | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/pages/ferme/triagebatiment/triagebatimentparquet-saisie.jsp` |
| **Master Entity** | `TriageBatimentParquet.java` (`/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-ejb/src/java/ferme/triageBatiment/TriageBatimentParquet.java`) |
| **Detail Entity** | `TriageBatimentParquetDetail.java` |
| **Tables** | `TRIAGEBATIMENTPARQUET` (master), `TRIAGEBATIMENTPARQUETDETAIL` (detail) |
| **PK** | Generated via `getseq_triagebatimentparquet` procedure, prefix `"TB"` |
| **Parent** | Extends `ClassMere` |
| **Link** | `idmere` |
| **Availability View** | `v_qte_dispo_lot_sexe_qualite` |

**Master Fields:**
- `id`, `daty`, `idlot`, `dispomale`, `dispofemelle`, `etat`

**Detail Fields:**
- `id`, `idmere`, `idbatiment`, `idparquet`, `idsexe`, `idqualite`, `qte`

**DB Tables:** `TRIAGEBATIMENTPARQUET` (INSERT), `TRIAGEBATIMENTPARQUETDETAIL` (INSERT), `v_qte_dispo_lot_sexe_qualite` (SELECT), `LOT` (read), `BATIMENT` (read), `PARQUETBATIMENT` (read), `SEXE` (read), `QUALITEPOUSSIN` (read)

**Note:** The quantity validation in `TriageBatimentParquet.createObject()` is currently **commented out** — the system does NOT validate that sorted quantities match available quantities.

### 11.6 The Availability View

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

This view shows how many chickens of each sex/quality have been sorted into compartments for each lot.

---

## 12. Calendar Systems

There are **3 distinct calendar systems**:

### 12.1 CaCalendrier — Sales/Revenue Calendar

| Aspect | Details |
|--------|---------|
| **File** | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-ejb/src/java/vente/CaCalendrier.java` |
| **Purpose** | Display CA (revenue) by day in table format |
| **Data Source** | `CaJourDetailPourCalendrier` entity |
| **UI** | Custom HTML table in `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/pages/vente/analyse/akoho-prev.jsp` |
| **Format** | Rows = days (1-31), Columns = dates, Cells = revenue |

### 12.2 PageCalendrier — Generic Event Calendar

| Aspect | Details |
|--------|---------|
| **File** | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/affichage/PageCalendrier.java` |
| **Purpose** | Generic event calendar using FullCalendar JS |
| **Data Source** | `Evenement` entity |
| **UI** | FullCalendar (JavaScript) |
| **Used by** | Maintenance planning, work requests |

### 12.3 Calendrier + CalendrierElement

| Aspect | Details |
|--------|---------|
| **File** | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/affichage/Calendrier.java` |
| **Purpose** | Event calendar with legends and themes |
| **Data Source** | `CalendrierElement` objects |
| **Used by** | Payroll pointage, maintenance |

### 12.4 Other Calendars

| Calendar | File |
|----------|------|
| Payroll | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-ejb/src/java/paie/pointage/Calendrier.java` |
| Maintenance | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-ejb/src/java/maintenance/planning/CalendrierMaintenance.java` |
| Work Requests | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-ejb/src/java/maintenance/planning/CalendrierDemandeTravaux.java` |
| Calendar Servlet | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core2/web/servlet/CalendrierServlet.java` |
| Calendar Data Servlet | `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core2/web/servlet/CalendrierDataServlet.java` |

---

## 13. Build & Deployment

### 13.1 Build Files

| File | Purpose |
|------|---------|
| `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/build.xml` | Main Ant build file |
| `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/build-tomcat.xml` | Tomcat-specific build |
| `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/build-jenkins.xml` | Jenkins CI build |
| `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/Dockerfile` | Docker deployment |

### 13.2 Build Targets

```bash
ant build          # Main build (compiles EJB + WAR)
ant deploy-tomcat  # Deploy to Tomcat
ant deploy-jenkins # Jenkins deployment
```

### 13.3 Deployment Architecture

```
Docker Container (Oracle EE)
    └── Tomcat 9
        ├── arbiochem.war (deployed)
        │   ├── WEB-INF/lib/apj-core.jar
        │   ├── WEB-INF/lib/apj-core2.jar
        │   ├── WEB-INF/lib/arbiochem-ejb.jar
        │   └── web pages, servlets, assets
        └── JNDI DataSource: java:VVFinances
            └── Oracle Database (AKOHO schema)
```

---

## 14. How to Add a New Feature

### 14.1 Step-by-Step Pattern

```
STEP 1: Create Entity (Model)
    → In: arbiochem-ejb/src/java/yourpackage/YourEntity.java
    → Extends ClassMAPTable (or ClassMere for master-detail)
    → Define: nomTable, fields, getters/setters
    ↓
STEP 2: Create JSP View (List page)
    → In: arbiochem-war/web/pages/yourpackage/your-entity-liste.jsp
    → Use PageRecherche to generate list/table
    ↓
STEP 3: Create JSP View (Form page)
    → In: arbiochem-war/web/pages/yourpackage/your-entity-saisie.jsp
    → Use PageInsert to generate form
    → Add hidden fields: classe, acte, bute
    ↓
STEP 4: Create JSP View (Detail page)
    → In: arbiochem-war/web/pages/yourpackage/your-entity-fiche.jsp
    → Display saved data with links to edit/delete
    ↓
STEP 5: The controller is automatic
    → POST goes to ApresMultiple.jsp (generic handler)
    → Pass classe, classefille, acte as hidden fields
    → ApresMultiple calls UserEJB.createObject/updateObject
    ↓
STEP 6: Add validation (optional)
    → Override createObject() in your entity
    → Add checks before calling super.createObject()
    ↓
STEP 7: Build and deploy
    → ant build
    → Restart Tomcat
```

### 14.2 Template: Entity Class

```java
package yourpackage;
import bean.ClassMAPTable;
import java.sql.Connection;

public class YourEntity extends ClassMAPTable {
    private String id;
    private String name;
    // ... other fields
    
    public YourEntity() throws Exception {
        this.setNomTable("YOUR_TABLE");
    }
    
    @Override
    public void construirePK(Connection c) throws Exception {
        this.preparePk("PK", "GETSEQ_YOUR_ENTITY");
        this.setId(this.makePK(c));
    }
    
    @Override
    public String getTuppleID() { return id; }
    
    @Override
    public String getAttributIDName() { return "id"; }
    
    // Getters and setters...
}
```

### 14.3 Template: JSP Form Page

```jsp
<%@page import="yourpackage.YourEntity"%>
<%@page import="affichage.*"%>
<%
    YourEntity obj = new YourEntity();
    PageInsert pi = new PageInsert(obj, request, (user.UserEJB) session.getValue("u"));
    pi.setLien((String) session.getValue("lien"));
    pi.preparerDataFormu();
%>
<form action="<%=pi.getLien()%>?but=apresMultiple.jsp" method="post">
    <% out.println(pi.getFormu().getHtmlInsert()); %>
    <input name="acte" type="hidden" value="insert">
    <input name="bute" type="hidden" value="yourpackage/your-entity-fiche.jsp">
    <input name="classe" type="hidden" value="yourpackage.YourEntity">
</form>
```

### 14.4 Template: JSP List Page

```jsp
<%@page import="yourpackage.YourEntity"%>
<%@page import="affichage.*"%>
<%
    YourEntity obj = new YourEntity();
    String[] listeCrt = {"name"};
    String[] libEntete = {"id", "name"};
    
    PageRecherche pr = new PageRecherche(obj, request, listeCrt, null, 3, libEntete, libEntete.length);
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));
    pr.creerObjetPage(libEntete, null);
%>
<form action="<%=pr.getLien()%>?but=yourpackage/your-entity-liste.jsp" method="post">
    <% out.println(pr.getFormu().getHtmlEnsemble()); %>
</form>
<% out.println(pr.getTableau().getHtml()); %>
```

---

## 15. Key Patterns & Conventions

### 15.1 Naming Conventions

| Element | Convention | Example |
|---------|-----------|---------|
| Entity class | PascalCase | `Lot`, `ReceptionPoussinAeroport` |
| Table name | UPPER_SNAKE | `LOT`, `RECEPTIONPOUSSINAEROPORT` |
| JSP saisie | kebab-case | `lot-saisie.jsp` |
| JSP liste | kebab-case | `lot-liste.jsp` |
| JSP fiche | kebab-case | `lot-fiche.jsp` |
| Sequence | getseq_xxx | `getseq_lot` |
| PK prefix | Short code | `"LOT"`, `"RPA"`, `"TRSP"`, `"TB"` |
| Link column | idMere | `idMere` (in detail table) |

### 15.2 JSP Page Types

| Type | Suffix | Purpose |
|------|--------|---------|
| Form | `*-saisie.jsp` | Create/Edit form |
| List | `*-liste.jsp` | Search/List with pagination |
| Detail | `*-fiche.jsp` | View saved record |
| Post-action | `apres*.jsp` | Handle form submission |

### 15.3 Hidden Fields in Forms

Every form that submits to `ApresMultiple.jsp` must include:

```jsp
<input name="acte" type="hidden" value="insert">          <!-- insert/update/dupliquer -->
<input name="bute" type="hidden" value="path/to/fiche.jsp"> <!-- redirect after save -->
<input name="classe" type="hidden" value="package.ClassName"> <!-- entity class name -->
<!-- For master-detail: -->
<input name="classefille" type="hidden" value="package.DetailClassName">
<input name="nombreLigne" type="hidden" value="10">
<input name="colonneMere" type="hidden" value="idMere">
```

### 15.4 Session Attributes

| Attribute | Type | Purpose |
|-----------|------|---------|
| `u` | `UserEJB` | Current logged-in user (service bean) |
| `lien` | `String` | Current page URL for form actions |

### 15.5 Common JSP Patterns

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

**Dependent dropdown (cascading):**
```java
Batiment batiment = new Batiment();
batiment.setNomTable("BATIMENT");
Liste batListe = new Liste("idbatiment", batiment, "nomBatiment", "id");
ParquetBatiment parquet = new ParquetBatiment();
Liste parqListe = new Liste("idparquet", parquet, "val", "id");
parqListe.setDeroulanteDependante(batListe, "idbatiment", "onchange");
```

**Hide a field:**
```java
pi.getFormu().getChamp("etat").setVisible(false);
```

**Set field as readonly:**
```java
pi.getFormu().getChamp("idLot").setAutre("readonly");
```

---

## 16. Database Schema (Key Tables)

### 16.1 Ferme Domain Tables

| Table | Purpose | Key Columns |
|-------|---------|-------------|
| `LOT` | Chicken batches | `ID`, `NOMLOT`, `REFERENCE`, `DATEECLOSION`, `DATEARRIVEE`, `ETAT` |
| `RECEPTIONPOUSSINAEROPORT` | Airport reception master | `ID`, `IDLOT`, `DATY`, `QTERECUS`, `NBRCARTONMALE`, `NBRCARTONFEMELLE` |
| `RECEPTIONPOUSSINAEROPORTDETAIL` | Airport reception details | `ID`, `IDMERE`, `IDSEXE`, `QTE`, `IDQUALITEPOUSSIN` |
| `TRANSFERTPOULET` | Transfer master | `ID`, `DATY`, `IDLOT`, `REMARQUE`, `ETAT` |
| `TRANSFERTPOULETDETAIL` | Transfer details | `ID`, `IDMERE`, `IDSEXE`, `QTE`, `IDFERMEDEPART`, etc. |
| `TRIAGEBATIMENTPARQUET` | Sorting master | `ID`, `DATY`, `IDLOT`, `DISPOMALE`, `DISPOFEMELLE`, `ETAT` |
| `TRIAGEBATIMENTPARQUETDETAIL` | Sorting details | `ID`, `IDMERE`, `IDBATIMENT`, `IDPARQUET`, `IDSEXE`, `IDQUALITE`, `QTE` |

### 16.2 Configuration Tables

| Table | Purpose |
|-------|---------|
| `SEXE` | Male/Female options |
| `QUALITEPOUSSIN` | Chick quality levels |
| `SOUCHE` | Chicken strains/breeds |
| `ORIGINELOT` | Lot origins |
| `CATEGORIELOT` | Lot categories |
| `PROGRAMMEZOOTECHNIQUE` | Zootechnical programs |
| `BATIMENT` | Buildings |
| `PARQUET` | Compartments |
| `PARQUETBATIMENT` | Building-compartment links |
| `MAGASIN2` | Farms/sites |
| `VEHICULE` | Vehicles |

### 16.3 System Tables

| Table | Purpose |
|-------|---------|
| `UTILISATEUR` | User accounts |
| `ACTIONROLE` | Role permissions |
| `HISTORIQUE` | Audit trail |
| `CONFIGURATION` | System config |
| `NOTIFICATION` | Notifications |

### 16.4 Views

| View | Purpose |
|------|---------|
| `V_QTE_DISPO_LOT_SEXE_QUALITE` | Available chickens by lot/sex/quality |

### 16.5 Sequences

| Sequence | Used By |
|----------|---------|
| `getseq_lot` | `LOT` |
| `GETSEQRECEPTIONPOUSSINAEROPORT` | `RECEPTIONPOUSSINAEROPORT` |
| `GETSEQTRANSFERTPOULET` | `TRANSFERTPOULET` |
| `getseq_triagebatimentparquet` | `TRIAGEBATIMENTPARQUET` |
| `getseq_programmezootechnique` | `PROGRAMMEZOOTECHNIQUE` |

---

## 17. Troubleshooting

### 17.1 Common Issues

| Issue | Cause | Solution |
|-------|-------|----------|
| `Table non existante` | Entity's `getNomTable()` returns wrong name | Check table name in DB |
| `Impossible de modifier un objet deja valide` | Entity is in state >= 11 | Cannot edit validated records |
| `Connection closed` | Connection not properly closed | Check `finally` blocks |
| `Sequence does not exist` | Missing DB sequence | Create sequence in DB |
| ` NullPointerException` in JSP | Missing session attribute `u` | Ensure user is logged in |
| `La quantité totale en détails différente` | Detail sum != master quantity | Fix detail quantities |

### 17.2 Debugging Tips

1. **Check server logs:** Tomcat logs show full stack traces
2. **Add `System.out.println()`:** The framework uses stdout for debugging
3. **Check `HISTORIQUE` table:** See what operations were performed
4. **Verify session:** Ensure `UserEJB` is in session as `"u"`
5. **Check DB connection:** Verify JNDI `java:VVFinances` is configured

### 17.3 Key Files for Debugging

| File | Purpose |
|------|---------|
| `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/WEB-INF/web.xml` | Servlet configuration |
| `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/build.xml` | Build configuration |
| Tomcat `server.xml` | JNDI DataSource config |
| `/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/utilitaire/UtilDB.java` | Connection management |

---

## Appendix A: Complete File Index

### A.1 Framework Core (`/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core/`)

| Package | Key Files |
|---------|-----------|
| `bean/` | `ClassMAPTable.java`, `CGenUtil.java`, `TypeObjet.java`, `ClassMere.java`, `ClassFille.java`, `ClassEtat.java`, `ClassUser.java`, `ListeColonneTable.java`, `ResultatEtSomme.java`, `Champ.java`, `Liste.java` |
| `affichage/` | `PageRecherche.java`, `PageInsert.java`, `PageInsertMultiple.java`, `Formulaire.java`, `TableauRecherche.java`, `PageCalendrier.java`, `Calendrier.java`, `CalendrierElement.java` |
| `utilitaire/` | `UtilDB.java`, `Utilitaire.java`, `UtilitaireString.java`, `ConstanteEtat.java` |
| `historique/` | `MapHistorique.java`, `HistoriqueBean.java`, `HistoriqueLocal.java`, `HistoriqueRemote.java` |

### A.2 Framework Extended (`/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/framework/apj-core2/`)

| Package | Key Files |
|---------|-----------|
| `utilisateur/` | `ActionRole.java` |
| `notification/` | `NotificationService.java`, `NotificationMessage.java` |
| `web/servlet/` | `CalendrierServlet.java`, `CalendrierDataServlet.java` |
| `config/` | `CynthiaConf.java` |
| `importController/` | `ImportUtilServlet.java` |

### A.3 EJB Domain (`/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-ejb/src/java/`)

| Package | Purpose |
|---------|---------|
| `ferme/` | Farm management (lots, buildings, transfers, sorting) |
| `vente/` | Sales and revenue |
| `paie/` | Payroll and HR |
| `user/` | User management and authentication |
| `produits/` | Products and recipes |
| `rapport/` | Reports and scheduling |
| `pertegain/` | Profit/loss tracking |
| `paramCompta/` | Accounting parameters |
| `change/` | Exchange rates |
| `maintenance/` | Equipment maintenance |
| `previsionFerme/` | Farm forecasting |
| `prevision/` | Financial forecasting |

### A.4 WAR Servlets (`/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/src/java/servlet/`)

| Servlet | Purpose |
|---------|---------|
| `ApresMultiple.java` | Main POST handler (generic CRUD) |
| `CompteServlet.java` | Account management |
| `IngredientsRemiseServlet.java` | Discount API |
| `DeviseServlet.java` | Currency management |
| `EtatComptableServlet.java` | Accounting state |
| `Reporting.java` | Reports |
| `ApresTarif.java` | Post-tariff processing |
| `ApresMultiple.java` | Batch operations |

### A.5 WAR Pages (`/home/bryan/ITU/S5/Prog/Reprise_code/Akoho/projet-standard - Copie/arbiochem-war/web/pages/`)

| Directory | Purpose |
|-----------|---------|
| `ferme/` | Farm management pages |
| `vente/` | Sales pages |
| `paie/` | Payroll pages |
| `compta/` | Accounting pages |
| `produits/` | Products pages |
| `prevision/` | Forecasting pages |
| `maintenance/` | Maintenance pages |

---

*Document generated: 2026-10-14*
*Project: Akoho — Chicken Farm Management System*
*Framework: APJ-Core + APJ-Core2 (Custom Java EE Framework)*
