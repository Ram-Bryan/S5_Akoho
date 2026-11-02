/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  user.UserEJB
 */
package affichage;

import affichage.CalendrierElement;
import affichage.PageRecherche;
import bean.CGenUtil;
import bean.ClassMAPTable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import user.UserEJB;

public class Calendrier {
    private CalendrierElement[] elements;
    private String mockEventsData;
    private String titre;
    private LinkedHashMap<String, String> legendes = new LinkedHashMap();
    private String contextPath = "";

    public LinkedHashMap<String, String> getLegendes() {
        return this.legendes;
    }

    public void setLegendes(LinkedHashMap<String, String> legendes) {
        this.legendes = legendes;
    }

    public String getContextPath() {
        return this.contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Calendrier() {
    }

    public Calendrier(CalendrierElement[] elements) {
        this.elements = elements;
        if (elements.length != 0) {
            this.contextPath = elements[0].getReq().getContextPath();
        }
    }

    public String getMockEventsData() {
        return this.mockEventsData;
    }

    public void setMockEventsData(String mockEventsData) {
        this.mockEventsData = mockEventsData;
    }

    public CalendrierElement[] getElements() {
        return this.elements;
    }

    public void setElements(CalendrierElement[] elements) {
        this.elements = elements;
    }

    public void addLegende(String theme, String libelle) {
        this.legendes.put(theme, libelle);
    }

    public LinkedHashMap<String, String> getLegends() {
        return this.legendes;
    }

    private String getLegendesJson() {
        if (this.legendes.isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for (Map.Entry<String, String> entry : this.legendes.entrySet()) {
            if (!first) {
                sb.append(", ");
            }
            String libelle = entry.getValue().replace("\\", "\\\\").replace("\"", "\\\"");
            sb.append("\"").append(entry.getKey()).append("\": \"").append(libelle).append("\"");
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }

    private String getSousLegendesJson() {
        if (this.elements == null || this.elements.length == 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean firstGroup = true;
        for (CalendrierElement e : this.elements) {
            String coul;
            if (e.getColStatut() == null || e.getValStatut() == null || e.getAffStatut() == null || e.getCouleurStatut() == null || (coul = e.getCouleur()) == null || coul.isEmpty()) continue;
            if (!firstGroup) {
                sb.append(", ");
            }
            sb.append("\"").append(coul).append("\": [");
            String[] affStatut = e.getAffStatut();
            String[] couleurStatut = e.getCouleurStatut();
            for (int i = 0; i < affStatut.length; ++i) {
                if (i > 0) {
                    sb.append(", ");
                }
                String label = affStatut[i] == null ? "" : affStatut[i].replace("\\", "\\\\").replace("\"", "\\\"");
                String coulStatut = i < couleurStatut.length && couleurStatut[i] != null ? couleurStatut[i] : "#0284c7";
                sb.append("{\"label\": \"").append(label).append("\", \"couleur\": \"").append(coulStatut).append("\"}");
            }
            sb.append("]");
            firstGroup = false;
        }
        sb.append("}");
        return sb.toString();
    }

    public void setUtilisateur(UserEJB u) {
        for (CalendrierElement e : this.elements) {
            e.setUtilisateur(u);
            e.setNpp(Integer.MAX_VALUE);
        }
    }

    public void creerObjetPage() throws Exception {
        PageRecherche.creerObjetPage(this.elements);
        this.setMockEventsData();
    }

    public ClassMAPTable[] getData() {
        int taille = 0;
        for (CalendrierElement e : this.elements) {
            taille += e.getListe().length;
        }
        ClassMAPTable[] data = new ClassMAPTable[taille];
        int index = 0;
        for (CalendrierElement e : this.elements) {
            ClassMAPTable[] liste = e.getListe();
            System.arraycopy(liste, 0, data, index, liste.length);
            index += liste.length;
        }
        return data;
    }

    public static double heureEnDouble(String heure) {
        try {
            if (heure == null || !heure.matches("\\d{2}:\\d{2}")) {
                return 0.0;
            }
            String[] parties = heure.split(":");
            int heures = Integer.parseInt(parties[0]);
            int minutes = Integer.parseInt(parties[1]);
            if (heures > 23 || minutes > 59) {
                return 0.0;
            }
            return (double)heures + (double)minutes / 60.0;
        }
        catch (Exception e) {
            return 0.0;
        }
    }

    private static Object getValeurFieldByMethod(ClassMAPTable obj, String col) {
        try {
            Object val = CGenUtil.getValeurFieldByMethod(obj, col);
            return val == null || "".equals(val.toString()) ? null : val;
        }
        catch (Exception e) {
            return null;
        }
    }

    public void setMockEventsData() throws Exception {
        ClassMAPTable[] data = this.getData();
        if (data == null || data.length == 0) {
            this.mockEventsData = "[]";
            return;
        }
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        int indice = 0;
        for (CalendrierElement e : this.elements) {
            String colDate = e.getColDate();
            String colTitre = e.getColTitre();
            String colHeure = e.getColHeure();
            String colHeureFin = e.getColHeureFin();
            String nomTable = e.getNomTable();
            String nomClasse = e.getNomClasse();
            if (nomTable == null) {
                nomTable = e.getBase().getNomTable();
            }
            if (nomClasse == null) {
                nomClasse = e.getBase().getClassName();
            }
            int taille = indice + e.getListe().length;
            while (indice < taille) {
                ClassMAPTable obj = data[indice];
                Object idObj = Calendrier.getValeurFieldByMethod(obj, obj.getAttributIDName());
                Object titreObj = Calendrier.getValeurFieldByMethod(obj, colTitre);
                Object datyObj = Calendrier.getValeurFieldByMethod(obj, colDate);
                Object heureObj = colHeure != null ? Calendrier.getValeurFieldByMethod(obj, colHeure) : null;
                Object heurefinObj = colHeureFin != null ? Calendrier.getValeurFieldByMethod(obj, colHeureFin) : null;
                String id = idObj != null ? String.valueOf(idObj) : "";
                String titre = titreObj != null ? String.valueOf(titreObj) : "Sans titre";
                String statut = "";
                String statutLabel = "";
                if (e.getColStatut() != null && e.getValStatut() != null && e.getAffStatut() != null && e.getCouleurStatut() != null) {
                    Object colStatutObj = Calendrier.getValeurFieldByMethod(obj, e.getColStatut());
                    String valStatut = String.valueOf(colStatutObj);
                    for (int i = 0; i < e.getValStatut().length; ++i) {
                        if (!valStatut.equalsIgnoreCase(e.getValStatut()[i])) continue;
                        statut = e.getCouleurStatut()[i];
                        statutLabel = e.getAffStatut()[i].replace("\\", "\\\\").replace("\"", "\\\"");
                        break;
                    }
                }
                boolean hasHeure = heureObj != null && !heureObj.toString().isEmpty();
                String heure = hasHeure ? String.valueOf(heureObj) : "00:00";
                double startHourDouble = hasHeure ? Calendrier.heureEnDouble(heure) : -1.0;
                boolean allDay = !hasHeure;
                String dateStr = "";
                if (datyObj != null) {
                    dateStr = new SimpleDateFormat("yyyy-MM-dd").format((Date)datyObj);
                }
                boolean hasHeureFin = heurefinObj != null && !heurefinObj.toString().isEmpty();
                String heureFin = hasHeureFin ? String.valueOf(heurefinObj) : "";
                double endHourDouble = -1.0;
                if (hasHeureFin) {
                    endHourDouble = Calendrier.heureEnDouble(heureFin);
                } else if (hasHeure) {
                    endHourDouble = startHourDouble + 1.0;
                }
                String couleur = e.getCouleur() != null && !e.getCouleur().isEmpty() ? e.getCouleur() : "#0284c7";
                String lien = e.getLienClick() != null ? e.getLienClick() : "";
                String description = titre;
                titre = titre.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
                description = description.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
                lien = lien.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
                json.append("  {\n");
                json.append("    \"id\": \"").append(id).append("\",\n");
                json.append("    \"date\": \"").append(dateStr).append("\",\n");
                json.append("    \"title\": \"").append(titre).append("\",\n");
                json.append("    \"time\": \"").append(heure).append("\",\n");
                json.append("    \"endTime\": \"").append(heureFin).append("\",\n");
                json.append("    \"startHour\": ").append(startHourDouble).append(",\n");
                json.append("    \"endHour\": ").append(endHourDouble).append(",\n");
                json.append("    \"allDay\": ").append(allDay).append(",\n");
                json.append("    \"couleur\": \"").append(couleur).append("\",\n");
                json.append("    \"statut\": \"").append(statut).append("\",\n");
                json.append("    \"statutLabel\": \"").append(statutLabel).append("\",\n");
                json.append("    \"lien\": \"").append(lien).append("\",\n");
                json.append("    \"description\": \"").append(description).append("\",\n");
                json.append("    \"nomTable\": \"").append(nomTable).append("\",\n");
                json.append("    \"nomClasse\": \"").append(nomClasse).append("\",\n");
                json.append("    \"colDate\": \"").append(colDate).append("\",\n");
                json.append("    \"colHeure\": \"").append(colHeure != null ? colHeure : "").append("\",\n");
                json.append("    \"colHeureFin\": \"").append(colHeureFin != null ? colHeureFin : "").append("\",\n");
                json.append("    \"lienClick\": \"").append(lien).append("\"\n");
                json.append("  }");
                if (indice < data.length - 1) {
                    json.append(",");
                }
                json.append("\n");
                ++indice;
            }
        }
        json.append("]");
        this.mockEventsData = json.toString();
    }

    public String getHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"app-container\">\n");
        sb.append("    <header class=\"calendar-header\">\n");
        sb.append("        <div class=\"header-left\">\n");
        sb.append("            <h1 id=\"header-title\"></h1>\n");
        sb.append("            <p id=\"header-subtitle\"></p>\n");
        sb.append("        </div>\n");
        sb.append("        <div class=\"header-right\">\n");
        sb.append("            <div class=\"nav-arrows\">\n");
        sb.append("                <button class=\"icon-btn\" id=\"prevPeriodBtn\"><span class=\"material-symbols-rounded\">chevron_left</span></button>\n");
        sb.append("                <button class=\"icon-btn\" id=\"nextPeriodBtn\"><span class=\"material-symbols-rounded\">chevron_right</span></button>\n");
        sb.append("            </div>\n");
        sb.append("            <div class=\"view-toggles\">\n");
        sb.append("                <button class=\"toggle-btn\" data-view=\"day\">Jour</button>\n");
        sb.append("                <button class=\"toggle-btn active\" data-view=\"week\">Semaine</button>\n");
        sb.append("                <button class=\"toggle-btn\" data-view=\"month\">Mois</button>\n");
        sb.append("            </div>\n");
        sb.append("            <button class=\"today-btn\" id=\"goTodayBtn\">Aujourd'hui</button>\n");
        sb.append("            <div class=\"date-picker-wrapper\">\n");
        sb.append("                <button class=\"today-btn flex-btn\" id=\"datePickerBtn\">\n");
        sb.append("                    <span class=\"material-symbols-rounded\" style=\"font-size: 18px;\">calendar_month</span>\n");
        sb.append("                    <span id=\"datePickerText\"></span>\n");
        sb.append("                </button>\n");
        sb.append("                <div class=\"date-picker-popover\" id=\"datePickerPopover\">\n");
        sb.append("                    <div class=\"dp-header\">\n");
        sb.append("                        <button id=\"dpPrevMonth\"><span class=\"material-symbols-rounded\">chevron_left</span></button>\n");
        sb.append("                        <span id=\"dpMonthYear\"></span>\n");
        sb.append("                        <button id=\"dpNextMonth\"><span class=\"material-symbols-rounded\">chevron_right</span></button>\n");
        sb.append("                    </div>\n");
        sb.append("                    <div class=\"dp-weekdays\"><span>Lu</span><span>Ma</span><span>Me</span><span>Je</span><span>Ve</span><span>Sa</span><span>Di</span></div>\n");
        sb.append("                    <div class=\"dp-days-grid\" id=\"dpDaysGrid\"></div>\n");
        sb.append("                </div>\n");
        sb.append("            </div>\n");
        sb.append("        </div>\n");
        sb.append("    </header>\n");
        sb.append("    <div class=\"calendar-legend\" id=\"calendarLegend\"></div>\n");
        sb.append("    <div class=\"calendar-wrapper\" id=\"calendarWrapper\"></div>\n");
        sb.append("</div>\n");
        sb.append("<div class=\"modal-overlay\" id=\"eventModalOverlay\">\n");
        sb.append("    <div class=\"event-modal\">\n");
        sb.append("        <button class=\"modal-close-btn\" id=\"closeModalBtn\"><span class=\"material-symbols-rounded\">close</span></button>\n");
        sb.append("        <h2 class=\"modal-title\" id=\"modalTitle\"></h2>\n");
        sb.append("        <div class=\"modal-meta\">\n");
        sb.append("            <div class=\"meta-item\"><span class=\"meta-icon material-symbols-rounded\">event</span><span id=\"modalDate\"></span></div>\n");
        sb.append("            <div class=\"meta-item\"><span class=\"meta-icon material-symbols-rounded\">schedule</span><span id=\"modalTime\"></span></div>\n");
        sb.append("        </div>\n");
        sb.append("    </div>\n");
        sb.append("</div>\n");
        sb.append("<div class=\"dnd-toast\" id=\"dndToast\"></div>\n");
        sb.append("<div class=\"modal fade\" id=\"linkModal\" role=\"dialog\" aria-labelledby=\"linkModalLabel\" aria-hidden=\"true\">\n");
        sb.append("  <div style=\"width:60%;background:transparent;\" class=\"modal-dialog modal-dialog-centered\" role=\"dialog\">\n");
        sb.append("    <div style=\"border-radius: 16px;padding:15px;overflow-y:auto;height:80vh\" class=\"modal-content\">\n");
        sb.append("      <div class=\"modal-body\">\n");
        sb.append("       <div id=\"modalContent\"></div>\n");
        sb.append("    </div>\n");
        sb.append("   </div>\n");
        sb.append("  </div>\n");
        sb.append("</div>\n");
        return sb.toString();
    }

    public String getJs() {
        StringBuilder sb = new StringBuilder();
        sb.append("<style>\n");
        sb.append("  .allday-cell {\n");
        sb.append("      min-height: 38px;\n");
        sb.append("      border: 1px dashed transparent;\n");
        sb.append("      border-right: 1px solid var(--border-color);\n");
        sb.append("      transition: all 0.2s ease;\n");
        sb.append("  }\n");
        sb.append("  body.dnd-active .allday-cell {\n");
        sb.append("      border-color: #cbd5e1;\n");
        sb.append("      background-color: #f8fafc;\n");
        sb.append("  }\n");
        sb.append("  .allday-cell.dnd-over {\n");
        sb.append("      border-color: #0284c7 !important;\n");
        sb.append("      background-color: rgba(2, 132, 199, 0.1) !important;\n");
        sb.append("  }\n");
        sb.append("</style>\n\n");
        sb.append("<script>\n");
        sb.append("const CalendarConfig = {\n");
        sb.append("    nomTable:  '',\n");
        sb.append("    nomClasse: '',\n");
        sb.append("    legendes: ").append(this.getLegendesJson()).append(",\n");
        sb.append("    sousLegendes: ").append(this.getSousLegendesJson()).append("\n");
        sb.append("};\n\n");
        sb.append("const CalendarUtils = {\n");
        sb.append("    toYMD: (date) => `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`,\n");
        sb.append("    getMonday: (d) => { const date = new Date(d); const day = date.getDay(); const diff = date.getDate() - day + (day === 0 ? -6 : 1); return new Date(date.setDate(diff)); },\n");
        sb.append("    getWeekNumber: (d) => { const date = new Date(d.getTime()); date.setHours(0,0,0,0); date.setDate(date.getDate() + 3 - (date.getDay() + 6) % 7); const week1 = new Date(date.getFullYear(), 0, 4); return 1 + Math.round(((date.getTime() - week1.getTime()) / 86400000 - 3 + (week1.getDay() + 6) % 7) / 7); },\n");
        sb.append("    roundToHalfHour: (decimalHour) => Math.round(decimalHour * 2) / 2,\n");
        sb.append("    decimalToHHMM: (decimal) => {\n");
        sb.append("        const clamped = Math.max(0, Math.min(23.983, decimal));\n");
        sb.append("        const h = Math.floor(clamped);\n");
        sb.append("        const m = Math.round((clamped - h) * 60);\n");
        sb.append("        return `${String(h).padStart(2,'0')}:${String(m).padStart(2,'0')}`;\n");
        sb.append("    },\n");
        sb.append("    HHMMtoDecimal: (hhmm) => {\n");
        sb.append("        if (!hhmm || !hhmm.match(/^\\d{2}:\\d{2}$/)) return 0;\n");
        sb.append("        const [h, m] = hhmm.split(':').map(Number);\n");
        sb.append("        return h + m / 60;\n");
        sb.append("    }\n");
        sb.append("};\n\n");
        sb.append("function hexToRgba(hex, alpha) {\n");
        sb.append("    hex = hex.replace('#','');\n");
        sb.append("    if (hex.length === 3) hex = hex.split('').map(c=>c+c).join('');\n");
        sb.append("    const n = parseInt(hex,16);\n");
        sb.append("    return `rgba(${(n>>16)&255},${(n>>8)&255},${n&255},${alpha})`;\n");
        sb.append("}\n\n");
        sb.append("function isEventVisible(event) {\n");
        sb.append("    if (CalendarState.couleursDesactivees.has(event.couleur)) return false;\n");
        sb.append("    if (event.statut) {\n");
        sb.append("        const key = event.couleur + '::' + event.statut;\n");
        sb.append("        if (CalendarState.statutsDesactives.has(key)) return false;\n");
        sb.append("    }\n");
        sb.append("    return true;\n");
        sb.append("}\n\n");
        sb.append("const CalendarState = {\n");
        sb.append("    events: ").append(this.getMockEventsData()).append(",\n");
        sb.append("    couleursDesactivees: new Set(),\n");
        sb.append("    statutsDesactives: new Set(),\n");
        sb.append("    currentView: 'month',\n");
        sb.append("    selectedDate: new Date(),\n");
        sb.append("    dpDate: new Date(),\n");
        sb.append("    isSaving: false,\n");
        sb.append("    monthNames:['Janvier','F\\u00e9vrier','Mars','Avril','Mai','Juin','Juillet','Ao\\u00fbt','Septembre','Octobre','Novembre','D\\u00e9cembre'],\n");
        sb.append("    monthNamesShort:['Jan','F\\u00e9v','Mar','Avr','Mai','Juin','Juil','Ao\\u00fbt','Sep','Oct','Nov','D\\u00e9c'],\n");
        sb.append("    daysOfWeek:['Lundi','Mardi','Mercredi','Jeudi','Vendredi','Samedi','Dimanche'],\n");
        sb.append("    navigate: function(direction) {\n");
        sb.append("        if (this.currentView === 'day') this.selectedDate.setDate(this.selectedDate.getDate() + direction);\n");
        sb.append("        else if (this.currentView === 'week') this.selectedDate.setDate(this.selectedDate.getDate() + (direction * 7));\n");
        sb.append("        else if (this.currentView === 'month') {\n");
        sb.append("            const targetMonth = this.selectedDate.getMonth() + direction;\n");
        sb.append("            const expectedMonth = (targetMonth % 12 + 12) % 12;\n");
        sb.append("            this.selectedDate.setMonth(targetMonth);\n");
        sb.append("            if (this.selectedDate.getMonth() !== expectedMonth) this.selectedDate.setDate(0);\n");
        sb.append("        }\n");
        sb.append("    }\n");
        sb.append("};\n\n");
        sb.append("const DragDropManager = {\n");
        sb.append("    dragging: null,\n");
        sb.append("    ghost: null,\n");
        sb.append("    offsetY: 0,\n");
        sb.append("    lastOverCol: null,\n");
        sb.append("    timeIndicator: null,\n");
        sb.append("    resizing: null,\n");
        sb.append("    justDragged: false,\n");
        sb.append("\n");
        sb.append("    showToast: function(message, type) {\n");
        sb.append("        const toast = document.getElementById('dndToast');\n");
        sb.append("        toast.textContent = message;\n");
        sb.append("        toast.className = 'dnd-toast ' + (type || '');\n");
        sb.append("        void toast.offsetWidth;\n");
        sb.append("        toast.classList.add('show');\n");
        sb.append("        clearTimeout(this._toastTimer);\n");
        sb.append("        this._toastTimer = setTimeout(() => toast.classList.remove('show'), 3000);\n");
        sb.append("    },\n");
        sb.append("\n");
        sb.append("    createGhost: function(title, couleur) {\n");
        sb.append("        const ghost = document.createElement('div');\n");
        sb.append("        ghost.className = 'dnd-ghost';\n");
        sb.append("        ghost.style.backgroundColor = hexToRgba(couleur, 0.07);\n");
        sb.append("        ghost.style.color = couleur;\n");
        sb.append("        ghost.style.borderLeft = '3px solid ' + couleur;\n");
        sb.append("        ghost.textContent = title;\n");
        sb.append("        document.body.appendChild(ghost);\n");
        sb.append("        this.ghost = ghost;\n");
        sb.append("    },\n");
        sb.append("    removeGhost: function() {\n");
        sb.append("        if (this.ghost) { this.ghost.remove(); this.ghost = null; }\n");
        sb.append("    },\n");
        sb.append("\n");
        sb.append("    moveGhost: function(clientX, clientY) {\n");
        sb.append("        if (!this.ghost) return;\n");
        sb.append("        this.ghost.style.left = (clientX + 14) + 'px';\n");
        sb.append("        this.ghost.style.top  = (clientY - 10) + 'px';\n");
        sb.append("    },\n");
        sb.append("\n");
        sb.append("    removeTimeIndicator: function() {\n");
        sb.append("        if (this.timeIndicator) { this.timeIndicator.remove(); this.timeIndicator = null; }\n");
        sb.append("    },\n");
        sb.append("\n");
        sb.append("    clearDndOver: function() {\n");
        sb.append("        document.querySelectorAll('.dnd-over').forEach(el => el.classList.remove('dnd-over'));\n");
        sb.append("    },\n");
        sb.append("\n");
        sb.append("    saveToServer: function(eventObj, newDate, newHeure, newStartHour, onSuccess, onError) {\n");
        sb.append("        if (CalendarState.isSaving) return;\n");
        sb.append("        CalendarState.isSaving = true;\n");
        sb.append("        document.body.classList.add('calendar-loading');\n");
        sb.append("        const params = new URLSearchParams({\n");
        sb.append("            id:          eventObj.id,\n");
        sb.append("            nomTable:    eventObj.nomTable,\n");
        sb.append("            nomClasse:   eventObj.nomClasse,\n");
        sb.append("            colDate:     eventObj.colDate,\n");
        sb.append("            colHeure:    eventObj.colHeure,\n");
        sb.append("            daty:        newDate,\n");
        sb.append("            heure:       newHeure,\n");
        sb.append("            startHour:   String(newStartHour),\n");
        sb.append("            colHeureFin: eventObj.colHeureFin || '',\n");
        sb.append("            heureFin:    eventObj.endTime || ''\n");
        sb.append("        });\n");
        sb.append("        fetch('").append(this.contextPath).append("/calendrier', {\n");
        sb.append("            method: 'POST',\n");
        sb.append("            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },\n");
        sb.append("            body: params.toString()\n");
        sb.append("        })\n");
        sb.append("        .then(res => res.text().then(text => {\n");
        sb.append("            let data;\n");
        sb.append("            try {\n");
        sb.append("                data = JSON.parse(text);\n");
        sb.append("            } catch(parseErr) {\n");
        sb.append("                throw new Error('R\\u00e9ponse serveur invalide (HTTP ' + res.status + '): ' + text.substring(0, 200));\n");
        sb.append("            }\n");
        sb.append("            if (!res.ok) {\n");
        sb.append("                throw new Error(data.message || data.error || ('Erreur serveur HTTP ' + res.status));\n");
        sb.append("            }\n");
        sb.append("            return data;\n");
        sb.append("        }))\n");
        sb.append("        .then(data => {\n");
        sb.append("            CalendarState.isSaving = false;\n");
        sb.append("            document.body.classList.remove('calendar-loading');\n");
        sb.append("            onSuccess(data);\n");
        sb.append("        })\n");
        sb.append("        .catch(err => {\n");
        sb.append("            CalendarState.isSaving = false;\n");
        sb.append("            document.body.classList.remove('calendar-loading');\n");
        sb.append("            onError(err);\n");
        sb.append("        });\n");
        sb.append("    },\n");
        sb.append("\n");
        sb.append("    applyEventUpdate: function(eventId, newDate, newHeure, newStartHour) {\n");
        sb.append("        const evt = CalendarState.events.find(e => e.id === eventId);\n");
        sb.append("        if (!evt) return null;\n");
        sb.append("        const oldStart = parseFloat(evt.startHour);\n");
        sb.append("        const oldEnd   = parseFloat(evt.endHour);\n");
        sb.append("        const duree    = (oldEnd > oldStart && oldStart >= 0) ? (oldEnd - oldStart) : 1.0;\n");
        sb.append("        const newEndHour = newStartHour + duree;\n");
        sb.append("        if (newEndHour > 24) {\n");
        sb.append("            throw new Error('Impossible : le d\\u00e9placement ferait d\\u00e9passer 23h59.');\n");
        sb.append("        }\n");
        sb.append("        evt.date = newDate;\n");
        sb.append("        evt.time = newHeure;\n");
        sb.append("        evt.startHour = newStartHour;\n");
        sb.append("        evt.endHour = newEndHour;\n");
        sb.append("        evt.endTime = CalendarUtils.decimalToHHMM(newEndHour);\n");
        sb.append("        evt.originalDate = newDate;\n");
        sb.append("        return evt;\n");
        sb.append("    },\n");
        sb.append("\n");
        sb.append("    onDrop: function(newDate, newStartHour) {\n");
        sb.append("        const self = this;\n");
        sb.append("        const d = this.dragging;\n");
        sb.append("        if (!d) return;\n");
        sb.append("        \n");
        sb.append("        const evtCheck = CalendarState.events.find(e => e.id === d.eventId);\n");
        sb.append("        let roundedHour = CalendarUtils.roundToHalfHour(newStartHour);\n");
        sb.append("        \n");
        sb.append("        // Si l'\u00c3\u00a9v\u00c3\u00a9nement n'a pas de colonne heure, on force l'\u00c3\u00a9tat allDay (-1)\n");
        sb.append("        if (evtCheck && !evtCheck.colHeure) {\n");
        sb.append("            roundedHour = -1;\n");
        sb.append("        }\n");
        sb.append("        \n");
        sb.append("        const newHeure = CalendarUtils.decimalToHHMM(roundedHour);\n");
        sb.append("        if (newDate === d.originalDate && roundedHour === d.originalStartHour) {\n");
        sb.append("            d.element.classList.remove('dragging');\n");
        sb.append("            this.dragging = null;\n");
        sb.append("            return;\n");
        sb.append("        }\n");
        sb.append("        const confirmation = confirm('Confirmer le d\\u00e9placement de cet \\u00e9v\\u00e9nement au ' + newDate + ' \\u00e0 ' + newHeure + ' ?');\n");
        sb.append("        if (!confirmation) {\n");
        sb.append("            d.element.classList.remove('dragging');\n");
        sb.append("            this.dragging = null;\n");
        sb.append("            return;\n");
        sb.append("        }\n");
        sb.append("        let updatedEvent;\n");
        sb.append("        try {\n");
        sb.append("            updatedEvent = this.applyEventUpdate(d.eventId, newDate, newHeure, roundedHour);\n");
        sb.append("        } catch(ex) {\n");
        sb.append("            self.showToast(ex.message, 'error');\n");
        sb.append("            d.element.classList.remove('dragging');\n");
        sb.append("            this.dragging = null;\n");
        sb.append("            return;\n");
        sb.append("        }\n");
        sb.append("        if (!updatedEvent) {\n");
        sb.append("            d.element.classList.remove('dragging');\n");
        sb.append("            this.dragging = null;\n");
        sb.append("            return;\n");
        sb.append("        }\n");
        sb.append("        CalendarUI.renderView();\n");
        sb.append("        this.saveToServer(\n");
        sb.append("            updatedEvent, newDate, newHeure, roundedHour,\n");
        sb.append("            function(data) { self.showToast('\\u00c9v\\u00e8nement d\\u00e9plac\\u00e9 avec succ\\u00e8s', 'success'); },\n");
        sb.append("            function(err) {\n");
        sb.append("                console.error('DnD save error:', err);\n");
        sb.append("                var msg = (err && err.message) ? err.message : String(err);\n");
        sb.append("                self.applyEventUpdate(d.eventId, d.originalDate, d.originalHeure, d.originalStartHour);\n");
        sb.append("                CalendarUI.renderView();\n");
        sb.append("                self.showToast('\\u00c9chec: ' + msg, 'error');\n");
        sb.append("            }\n");
        sb.append("        );\n");
        sb.append("        this.dragging = null;\n");
        sb.append("    },\n");
        sb.append("\n");
        sb.append("    makeEventDraggable: function(el, eventObj, hourHeight, startHourOffset) {\n");
        sb.append("        const self = this;\n");
        sb.append("        el.setAttribute('draggable', 'false');\n");
        sb.append("        el.addEventListener('mousedown', function(e) {\n");
        sb.append("            if (CalendarState.isSaving) return;\n");
        sb.append("            if (e.button !== 0) return;\n");
        sb.append("            if (e.target.classList.contains('resize-handle')) return;\n");
        sb.append("            \n");
        sb.append("            e.preventDefault();\n");
        sb.append("            e.stopPropagation();\n");
        sb.append("            \n");
        sb.append("            const startX = e.clientX;\n");
        sb.append("            const startY = e.clientY;\n");
        sb.append("            let dragStarted = false;\n");
        sb.append("            \n");
        sb.append("            function onMouseMove(moveEvent) {\n");
        sb.append("                if (!dragStarted) {\n");
        sb.append("                    const dx = moveEvent.clientX - startX;\n");
        sb.append("                    const dy = moveEvent.clientY - startY;\n");
        sb.append("                    if (Math.abs(dx) > 5 || Math.abs(dy) > 5) {\n");
        sb.append("                        dragStarted = true;\n");
        sb.append("                        self.dragging = {\n");
        sb.append("                            eventId:           eventObj.id,\n");
        sb.append("                            originalDate:      eventObj.date,\n");
        sb.append("                            originalHeure:     eventObj.time,\n");
        sb.append("                            originalStartHour: parseFloat(eventObj.startHour),\n");
        sb.append("                            element:           el,\n");
        sb.append("                            hourHeight:        hourHeight || 80,\n");
        sb.append("                            startHourOffset:   startHourOffset || 0\n");
        sb.append("                        };\n");
        sb.append("                        self.offsetY = startY - el.getBoundingClientRect().top;\n");
        sb.append("                        el.classList.add('dragging');\n");
        sb.append("                        document.body.classList.add('dnd-active');\n");
        sb.append("                        self.createGhost(eventObj.title, eventObj.couleur || '#0284c7');\n");
        sb.append("                        self.moveGhost(moveEvent.clientX, moveEvent.clientY);\n");
        sb.append("                    }\n");
        sb.append("                } else {\n");
        sb.append("                    self.moveGhost(moveEvent.clientX, moveEvent.clientY);\n");
        sb.append("                }\n");
        sb.append("            }\n");
        sb.append("            \n");
        sb.append("            function onMouseUp() {\n");
        sb.append("                document.removeEventListener('mousemove', onMouseMove);\n");
        sb.append("                document.removeEventListener('mouseup', onMouseUp);\n");
        sb.append("                \n");
        sb.append("                if (!dragStarted) {\n");
        sb.append("                    const clickEvent = new MouseEvent('click', {\n");
        sb.append("                        bubbles: true,\n");
        sb.append("                        cancelable: true\n");
        sb.append("                    });\n");
        sb.append("                    el.dispatchEvent(clickEvent);\n");
        sb.append("                }\n");
        sb.append("            }\n");
        sb.append("            \n");
        sb.append("            document.addEventListener('mousemove', onMouseMove);\n");
        sb.append("            document.addEventListener('mouseup', onMouseUp);\n");
        sb.append("        });\n");
        sb.append("    },\n");
        sb.append("    makeEventResizable: function(el, eventObj, col, dateStr, startHourOffset, hourHeight) {\n");
        sb.append("        const self = this;\n");
        sb.append("        const handleTop = document.createElement('div');\n");
        sb.append("        handleTop.className = 'resize-handle resize-handle-top';\n");
        sb.append("        el.appendChild(handleTop);\n");
        sb.append("        const handleBottom = document.createElement('div');\n");
        sb.append("        handleBottom.className = 'resize-handle resize-handle-bottom';\n");
        sb.append("        el.appendChild(handleBottom);\n");
        sb.append("\n");
        sb.append("        function startResize(e, direction) {\n");
        sb.append("            if (CalendarState.isSaving) return;\n");
        sb.append("            if (e.button !== 0) return;\n");
        sb.append("            e.preventDefault();\n");
        sb.append("            e.stopPropagation();\n");
        sb.append("            const colRect = col.getBoundingClientRect();\n");
        sb.append("            self.resizing = {\n");
        sb.append("                eventId:       eventObj.id,\n");
        sb.append("                direction:     direction,\n");
        sb.append("                element:       el,\n");
        sb.append("                colEl:         col,\n");
        sb.append("                colRect:       colRect,\n");
        sb.append("                hourHeight:    hourHeight,\n");
        sb.append("                startHourOffset: startHourOffset,\n");
        sb.append("                origStartHour: parseFloat(eventObj.startHour),\n");
        sb.append("                origEndHour:   parseFloat(eventObj.endHour),\n");
        sb.append("                origTime:      eventObj.time,\n");
        sb.append("                origEndTime:   eventObj.endTime,\n");
        sb.append("                dateStr:       dateStr\n");
        sb.append("            };\n");
        sb.append("            document.body.style.cursor = 'ns-resize';\n");
        sb.append("            document.body.style.userSelect = 'none';\n");
        sb.append("        }\n");
        sb.append("        handleTop.addEventListener('mousedown', (e) => startResize(e, 'top'));\n");
        sb.append("        handleBottom.addEventListener('mousedown', (e) => startResize(e, 'bottom'));\n");
        sb.append("    },\n");
        sb.append("\n");
        sb.append("    bindMonthDrop: function(cell, dateStr) {\n");
        sb.append("        const self = this;\n");
        sb.append("        cell.addEventListener('dragover', function(e) { e.preventDefault(); });\n");
        sb.append("        cell.addEventListener('mouseover', function(e) {\n");
        sb.append("            if (!self.dragging) return;\n");
        sb.append("            self.clearDndOver();\n");
        sb.append("            cell.classList.add('dnd-over');\n");
        sb.append("        });\n");
        sb.append("    },\n");
        sb.append("\n");
        sb.append("    bindAlldayDrop: function(cell, dateStr) {\n");
        sb.append("        const self = this;\n");
        sb.append("        cell.addEventListener('dragover', function(e) { e.preventDefault(); });\n");
        sb.append("        cell.addEventListener('mouseover', function(e) {\n");
        sb.append("            if (!self.dragging) return;\n");
        sb.append("            self.clearDndOver();\n");
        sb.append("            cell.classList.add('dnd-over');\n");
        sb.append("        });\n");
        sb.append("    },\n");
        sb.append("\n");
        sb.append("    bindTimedDrop: function(col, dateStr, startHour, hourHeight) {\n");
        sb.append("        const self = this;\n");
        sb.append("        col.addEventListener('mouseover', function(e) {\n");
        sb.append("            if (!self.dragging) return;\n");
        sb.append("            self.clearDndOver();\n");
        sb.append("            col.classList.add('dnd-over');\n");
        sb.append("            self.lastOverCol = { col, dateStr, startHour, hourHeight };\n");
        sb.append("        });\n");
        sb.append("        col.addEventListener('mousemove', function(e) {\n");
        sb.append("            if (!self.dragging) return;\n");
        sb.append("            const rect    = col.getBoundingClientRect();\n");
        sb.append("            const relY    = e.clientY - rect.top + col.scrollTop;\n");
        sb.append("            const rawHour = startHour + (relY / hourHeight);\n");
        sb.append("            const rounded = CalendarUtils.roundToHalfHour(rawHour);\n");
        sb.append("            self.removeTimeIndicator();\n");
        sb.append("            const indicator = document.createElement('div');\n");
        sb.append("            indicator.className = 'dnd-time-indicator';\n");
        sb.append("            indicator.style.top = ((rounded - startHour) * hourHeight) + 'px';\n");
        sb.append("            indicator.setAttribute('data-time', CalendarUtils.decimalToHHMM(rounded));\n");
        sb.append("            col.appendChild(indicator);\n");
        sb.append("            self.timeIndicator = indicator;\n");
        sb.append("        });\n");
        sb.append("    },\n");
        sb.append("\n");
        sb.append("    init: function() {\n");
        sb.append("        const self = this;\n");
        sb.append("        document.addEventListener('mousemove', function(e) {\n");
        sb.append("            if (self.dragging) self.moveGhost(e.clientX, e.clientY);\n");
        sb.append("            if (self.resizing) {\n");
        sb.append("                const r = self.resizing;\n");
        sb.append("                const rect = r.colEl.getBoundingClientRect();\n");
        sb.append("                const relY = e.clientY - rect.top + r.colEl.scrollTop;\n");
        sb.append("                const rawHour = r.startHourOffset + (relY / r.hourHeight);\n");
        sb.append("                const rounded = CalendarUtils.roundToHalfHour(rawHour);\n");
        sb.append("                const evt = CalendarState.events.find(ev => ev.id === r.eventId);\n");
        sb.append("                if (!evt) return;\n");
        sb.append("                if (r.direction === 'bottom') {\n");
        sb.append("                    const newEnd = Math.min(24, Math.max(r.origStartHour + 0.25, rounded));\n");
        sb.append("                    const duration = newEnd - r.origStartHour;\n");
        sb.append("                    r.element.style.height = `${duration * r.hourHeight - 10}px`;\n");
        sb.append("                    const timeLabel = r.element.querySelector('.event-time');\n");
        sb.append("                    if (timeLabel) {\n");
        sb.append("                        timeLabel.textContent = `${evt.time} \\u2013 ${CalendarUtils.decimalToHHMM(newEnd)}`;\n");
        sb.append("                    }\n");
        sb.append("                    r.currentNewEndHour = newEnd;\n");
        sb.append("                } else {\n");
        sb.append("                    const newStart = Math.max(0, Math.min(r.origEndHour - 0.25, rounded));\n");
        sb.append("                    const duration = r.origEndHour - newStart;\n");
        sb.append("                    r.element.style.top = `${(newStart - r.startHourOffset) * r.hourHeight + 5}px`;\n");
        sb.append("                    r.element.style.height = `${duration * r.hourHeight - 10}px`;\n");
        sb.append("                    const timeLabel = r.element.querySelector('.event-time');\n");
        sb.append("                    if (timeLabel) {\n");
        sb.append("                        timeLabel.textContent = `${CalendarUtils.decimalToHHMM(newStart)} \\u2013 ${evt.endTime}`;\n");
        sb.append("                    }\n");
        sb.append("                    r.currentNewStartHour = newStart;\n");
        sb.append("                }\n");
        sb.append("            }\n");
        sb.append("        });\n");
        sb.append("        document.addEventListener('mouseup', function(e) {\n");
        sb.append("            if (self.resizing) {\n");
        sb.append("                const r = self.resizing;\n");
        sb.append("                const evt = CalendarState.events.find(ev => ev.id === r.eventId);\n");
        sb.append("                self.resizing = null;\n");
        sb.append("                document.body.style.cursor = '';\n");
        sb.append("                document.body.style.userSelect = '';\n");
        sb.append("                if (!evt) return;\n");
        sb.append("                \n");
        sb.append("                const finalStart = r.currentNewStartHour !== undefined ? r.currentNewStartHour : r.origStartHour;\n");
        sb.append("                const finalEnd   = r.currentNewEndHour !== undefined ? r.currentNewEndHour : r.origEndHour;\n");
        sb.append("                \n");
        sb.append("                if (finalStart === r.origStartHour && finalEnd === r.origEndHour) {\n");
        sb.append("                    const clickEvent = new MouseEvent('click', {\n");
        sb.append("                        bubbles: true,\n");
        sb.append("                        cancelable: true\n");
        sb.append("                    });\n");
        sb.append("                    r.element.dispatchEvent(clickEvent);\n");
        sb.append("                    return;\n");
        sb.append("                }\n");
        sb.append("                \n");
        sb.append("                const confirmation = confirm('Confirmer la modification des horaires de cet \\u00c9v\\u00e9nement ?');\n");
        sb.append("                if (!confirmation) {\n");
        sb.append("                    CalendarUI.renderView();\n");
        sb.append("                    return;\n");
        sb.append("                }\n");
        sb.append("                evt.startHour = finalStart;\n");
        sb.append("                evt.endHour   = finalEnd;\n");
        sb.append("                evt.time      = CalendarUtils.decimalToHHMM(finalStart);\n");
        sb.append("                evt.endTime   = CalendarUtils.decimalToHHMM(finalEnd);\n");
        sb.append("                CalendarUI.renderView();\n");
        sb.append("                self.saveToServer(\n");
        sb.append("                    evt, evt.date, evt.time, parseFloat(evt.startHour),\n");
        sb.append("                    function() { self.showToast('\\u00c9v\\u00e9nement mis \\u00e0 jour', 'success'); },\n");
        sb.append("                    function(err) {\n");
        sb.append("                        evt.startHour = r.origStartHour; evt.endHour = r.origEndHour;\n");
        sb.append("                        evt.time = r.origTime; evt.endTime = r.origEndTime;\n");
        sb.append("                        CalendarUI.renderView();\n");
        sb.append("                        self.showToast('\\u00c9chec: ' + (err.message || err), 'error');\n");
        sb.append("                    }\n");
        sb.append("                );\n");
        sb.append("                return;\n");
        sb.append("            }\n");
        sb.append("            if (!self.dragging) return;\n");
        sb.append("            \n");
        sb.append("            self.justDragged = true;\n");
        sb.append("            setTimeout(() => self.justDragged = false, 100);\n");
        sb.append("            \n");
        sb.append("            self.removeGhost();\n");
        sb.append("            document.body.classList.remove('dnd-active');\n");
        sb.append("            self.removeTimeIndicator();\n");
        sb.append("            const view = CalendarState.currentView;\n");
        sb.append("            if (view === 'month') {\n");
        sb.append("                const overCell = document.querySelector('.month-cell.dnd-over');\n");
        sb.append("                self.clearDndOver();\n");
        sb.append("                if (overCell && overCell.id && overCell.id.startsWith('month-day-')) {\n");
        sb.append("                    const newDate = overCell.id.replace('month-day-', '');\n");
        sb.append("                    self.onDrop(newDate, self.dragging.originalStartHour);\n");
        sb.append("                } else {\n");
        sb.append("                    self.dragging.element.classList.remove('dragging');\n");
        sb.append("                    self.dragging = null;\n");
        sb.append("                }\n");
        sb.append("            } else if (view === 'day' || view === 'week') {\n");
        sb.append("                const overInfo = self.lastOverCol;\n");
        sb.append("                const overAllDay = document.querySelector('.allday-cell.dnd-over');\n");
        sb.append("                self.clearDndOver();\n");
        sb.append("                if (overInfo) {\n");
        sb.append("                    const col  = overInfo.col;\n");
        sb.append("                    const rect = col.getBoundingClientRect();\n");
        sb.append("                    const relY = e.clientY - rect.top + col.scrollTop;\n");
        sb.append("                    const rawHour = overInfo.startHour + (relY / overInfo.hourHeight);\n");
        sb.append("                    self.lastOverCol = null;\n");
        sb.append("                    self.onDrop(overInfo.dateStr, rawHour);\n");
        sb.append("                } else if (overAllDay && overAllDay.dataset.date) {\n");
        sb.append("                    const newDate = overAllDay.dataset.date;\n");
        sb.append("                    self.onDrop(newDate, self.dragging.originalStartHour);\n");
        sb.append("                } else {\n");
        sb.append("                    self.dragging.element.classList.remove('dragging');\n");
        sb.append("                    self.dragging = null;\n");
        sb.append("                }\n");
        sb.append("            }\n");
        sb.append("        });\n");
        sb.append("        document.addEventListener('keydown', function(e) {\n");
        sb.append("            if (e.key === 'Escape') {\n");
        sb.append("                if (self.resizing) {\n");
        sb.append("                    const r = self.resizing;\n");
        sb.append("                    self.resizing = null;\n");
        sb.append("                    document.body.style.cursor = '';\n");
        sb.append("                    document.body.style.userSelect = '';\n");
        sb.append("                    const evt = CalendarState.events.find(ev => ev.id === r.eventId);\n");
        sb.append("                    if (evt) { evt.startHour = r.origStartHour; evt.endHour = r.origEndHour; evt.time = r.origTime; evt.endTime = r.origEndTime; }\n");
        sb.append("                    CalendarUI.renderView();\n");
        sb.append("                }\n");
        sb.append("                if (self.dragging) {\n");
        sb.append("                    self.removeGhost();\n");
        sb.append("                    document.body.classList.remove('dnd-active');\n");
        sb.append("                    self.removeTimeIndicator();\n");
        sb.append("                    self.clearDndOver();\n");
        sb.append("                    self.dragging.element.classList.remove('dragging');\n");
        sb.append("                    self.dragging = null;\n");
        sb.append("                    self.lastOverCol = null;\n");
        sb.append("                }\n");
        sb.append("            }\n");
        sb.append("        });\n");
        sb.append("    }\n");
        sb.append("};\n\n");
        sb.append("const ModalUI = {\n");
        sb.append("    openEvent: function(eventId) {\n");
        sb.append("        const event = CalendarState.events.find(e => e.id === eventId);\n");
        sb.append("        if (!event) return;\n");
        sb.append("        document.getElementById('modalTitle').innerText = event.title.replace('\\n', ' - ');\n");
        sb.append("        document.getElementById('modalDate').innerText = event.date;\n");
        sb.append("        document.getElementById('modalTime').innerText = event.time;\n");
        sb.append("        document.getElementById('eventModalOverlay').classList.add('active');\n");
        sb.append("    },\n");
        sb.append("    closeEvent: () => document.getElementById('eventModalOverlay').classList.remove('active')\n");
        sb.append("};\n\n");
        sb.append("const DatePickerUI = {\n");
        sb.append("    render: function() {\n");
        sb.append("        const year = CalendarState.dpDate.getFullYear();\n");
        sb.append("        const month = CalendarState.dpDate.getMonth();\n");
        sb.append("        document.getElementById('dpMonthYear').innerText = `${CalendarState.monthNames[month]} ${year}`;\n");
        sb.append("        const firstDay = new Date(year, month, 1);\n");
        sb.append("        const firstDayIndex = firstDay.getDay() === 0 ? 6 : firstDay.getDay() - 1;\n");
        sb.append("        const grid = document.getElementById('dpDaysGrid');\n");
        sb.append("        grid.innerHTML = '';\n");
        sb.append("        let startDate = new Date(firstDay); startDate.setDate(startDate.getDate() - firstDayIndex);\n");
        sb.append("        for (let i = 0; i < 42; i++) {\n");
        sb.append("            const currentDate = new Date(startDate); currentDate.setDate(startDate.getDate() + i);\n");
        sb.append("            const dayEl = document.createElement('div'); dayEl.className = 'dp-day'; dayEl.innerText = currentDate.getDate();\n");
        sb.append("            if (currentDate.getMonth() !== month) dayEl.classList.add('dp-other-month');\n");
        sb.append("            if (CalendarUtils.toYMD(currentDate) === CalendarUtils.toYMD(CalendarState.selectedDate)) dayEl.classList.add('dp-selected');\n");
        sb.append("            dayEl.addEventListener('click', () => {\n");
        sb.append("                CalendarState.selectedDate = new Date(currentDate);\n");
        sb.append("                document.getElementById('datePickerPopover').classList.remove('active');\n");
        sb.append("                CalendarUI.renderView();\n");
        sb.append("            });\n");
        sb.append("            grid.appendChild(dayEl);\n");
        sb.append("        }\n");
        sb.append("    }\n");
        sb.append("};\n\n");
        sb.append("const CalendarUI = {\n");
        sb.append("    updateHeader: function() {\n");
        sb.append("        const title = document.getElementById('header-title');\n");
        sb.append("        const subtitle = document.getElementById('header-subtitle');\n");
        sb.append("        const weekNum = CalendarUtils.getWeekNumber(CalendarState.selectedDate);\n");
        sb.append("        const month = CalendarState.monthNames[CalendarState.selectedDate.getMonth()];\n");
        sb.append("        const year = CalendarState.selectedDate.getFullYear();\n");
        sb.append("        if (CalendarState.currentView === 'day') {\n");
        sb.append("            title.innerHTML = `${String(CalendarState.selectedDate.getDate()).padStart(2,'0')} ${month} ${year} \\u2013 <span>Semaine ${weekNum}</span>`;\n");
        sb.append("            subtitle.innerText = `${CalendarState.daysOfWeek[(CalendarState.selectedDate.getDay() + 6) % 7]}`;\n");
        sb.append("        } else if (CalendarState.currentView === 'week') {\n");
        sb.append("            title.innerHTML = `${month} ${year} \\u2013 <span>Semaine ${weekNum}</span>`;\n");
        sb.append("            const monday = CalendarUtils.getMonday(CalendarState.selectedDate);\n");
        sb.append("            const sunday = new Date(monday); sunday.setDate(monday.getDate() + 6);\n");
        sb.append("            subtitle.innerText = `Lundi ${String(monday.getDate()).padStart(2,'0')} ${CalendarState.monthNamesShort[monday.getMonth()]} \\u2013 Dimanche ${String(sunday.getDate()).padStart(2,'0')} ${CalendarState.monthNamesShort[sunday.getMonth()]}.`;\n");
        sb.append("        } else if (CalendarState.currentView === 'month') {\n");
        sb.append("            title.innerHTML = `${month} ${year}`;\n");
        sb.append("            subtitle.innerText = `").append(this.getTitre()).append("`;\n");
        sb.append("        }\n");
        sb.append("        document.getElementById('datePickerText').innerText = `${String(CalendarState.selectedDate.getDate()).padStart(2,'0')} ${CalendarState.monthNamesShort[CalendarState.selectedDate.getMonth()]} ${year}`;\n");
        sb.append("    },\n");
        sb.append("    renderLegend: function() {\n");
        sb.append("        const container = document.getElementById('calendarLegend');\n");
        sb.append("        if (!container) return;\n");
        sb.append("        container.innerHTML = '';\n");
        sb.append("        const legendes = CalendarConfig.legendes;\n");
        sb.append("        if (!legendes || Object.keys(legendes).length === 0) { container.style.display = 'none'; return; }\n");
        sb.append("        container.style.display = 'flex';\n");
        sb.append("        Object.entries(legendes).forEach(([couleur, libelle]) => {\n");
        sb.append("            const group = document.createElement('div');\n");
        sb.append("            group.className = 'legend-group';\n");
        sb.append("            const item = document.createElement('div');\n");
        sb.append("            item.className = 'legend-item legend-toggle';\n");
        sb.append("            item.dataset.couleur = couleur;\n");
        sb.append("            const desactive = CalendarState.couleursDesactivees.has(couleur);\n");
        sb.append("            if (desactive) item.classList.add('legend-off');\n");
        sb.append("            const dot = document.createElement('span');\n");
        sb.append("            dot.className = 'legend-dot';\n");
        sb.append("            dot.style.backgroundColor = desactive ? '#d1d5db' : couleur;\n");
        sb.append("            const label = document.createElement('span');\n");
        sb.append("            label.textContent = libelle;\n");
        sb.append("            item.appendChild(dot);\n");
        sb.append("            item.appendChild(label);\n");
        sb.append("            item.addEventListener('click', () => {\n");
        sb.append("                if (CalendarState.couleursDesactivees.has(couleur)) CalendarState.couleursDesactivees.delete(couleur);\n");
        sb.append("                else CalendarState.couleursDesactivees.add(couleur);\n");
        sb.append("                CalendarUI.renderView();\n");
        sb.append("            });\n");
        sb.append("            group.appendChild(item);\n");
        sb.append("            const sousItems = CalendarConfig.sousLegendes[couleur];\n");
        sb.append("            if (sousItems && sousItems.length > 0) {\n");
        sb.append("                const subContainer = document.createElement('div');\n");
        sb.append("                subContainer.className = 'legend-subitems';\n");
        sb.append("                sousItems.forEach(sous => {\n");
        sb.append("                    const subKey = couleur + '::' + sous.couleur;\n");
        sb.append("                    const subItem = document.createElement('div');\n");
        sb.append("                    subItem.className = 'legend-subitem legend-toggle';\n");
        sb.append("                    const subDesactive = CalendarState.statutsDesactives.has(subKey);\n");
        sb.append("                    if (subDesactive) subItem.classList.add('legend-off');\n");
        sb.append("                    const subDot = document.createElement('span');\n");
        sb.append("                    subDot.className = 'legend-dot legend-dot-sm';\n");
        sb.append("                    subDot.style.backgroundColor = subDesactive ? '#d1d5db' : sous.couleur;\n");
        sb.append("                    const subLabel = document.createElement('span');\n");
        sb.append("                    subLabel.textContent = sous.label;\n");
        sb.append("                    subItem.appendChild(subDot);\n");
        sb.append("                    subItem.appendChild(subLabel);\n");
        sb.append("                    subItem.addEventListener('click', () => {\n");
        sb.append("                        if (CalendarState.statutsDesactives.has(subKey)) CalendarState.statutsDesactives.delete(subKey);\n");
        sb.append("                        else CalendarState.statutsDesactives.add(subKey);\n");
        sb.append("                        CalendarUI.renderView();\n");
        sb.append("                    });\n");
        sb.append("                    subContainer.appendChild(subItem);\n");
        sb.append("                });\n");
        sb.append("                group.appendChild(subContainer);\n");
        sb.append("            }\n");
        sb.append("            container.appendChild(group);\n");
        sb.append("        });\n");
        sb.append("    },\n");
        sb.append("    drawCurrentTimeLine: function(container, targetDateStr, startHour, endHour, hourHeight) {\n");
        sb.append("        const now = new Date();\n");
        sb.append("        if (CalendarUtils.toYMD(now) === targetDateStr) {\n");
        sb.append("            const timeInDecimal = now.getHours() + (now.getMinutes() / 60);\n");
        sb.append("            if (timeInDecimal >= startHour && timeInDecimal <= endHour + 1) {\n");
        sb.append("                const line = document.createElement('div');\n");
        sb.append("                line.className = 'current-time-indicator';\n");
        sb.append("                line.style.top = `${(timeInDecimal - startHour) * hourHeight}px`;\n");
        sb.append("                line.setAttribute('data-time', `${String(now.getHours()).padStart(2,'0')}:${String(now.getMinutes()).padStart(2,'0')}`);\n");
        sb.append("                container.appendChild(line);\n");
        sb.append("            }\n");
        sb.append("        }\n");
        sb.append("    },\n");
        sb.append("    computeOverlapLayout: function(events) {\n");
        sb.append("        const sorted = events.slice().sort((a, b) => parseFloat(a.startHour) - parseFloat(b.startHour));\n");
        sb.append("        const groups = [];\n");
        sb.append("        sorted.forEach(evt => {\n");
        sb.append("            const s = parseFloat(evt.startHour);\n");
        sb.append("            const e = parseFloat(evt.endHour) > s ? parseFloat(evt.endHour) : s + 1;\n");
        sb.append("            let placed = false;\n");
        sb.append("            for (const group of groups) {\n");
        sb.append("                if (s < group.maxEnd && e > group.minStart) {\n");
        sb.append("                    group.events.push(evt);\n");
        sb.append("                    group.maxEnd = Math.max(group.maxEnd, e);\n");
        sb.append("                    placed = true;\n");
        sb.append("                    break;\n");
        sb.append("                }\n");
        sb.append("            }\n");
        sb.append("            if (!placed) groups.push({ events: [evt], minStart: s, maxEnd: e });\n");
        sb.append("        });\n");
        sb.append("        const layout = {};\n");
        sb.append("        groups.forEach(group => {\n");
        sb.append("            const cols = [];\n");
        sb.append("            group.events.forEach(evt => {\n");
        sb.append("                const s = parseFloat(evt.startHour);\n");
        sb.append("                const e = parseFloat(evt.endHour) > s ? parseFloat(evt.endHour) : s + 1;\n");
        sb.append("                let colIdx = -1;\n");
        sb.append("                for (let i = 0; i < cols.length; i++) {\n");
        sb.append("                    if (s >= cols[i]) { colIdx = i; cols[i] = e; break; }\n");
        sb.append("                }\n");
        sb.append("                if (colIdx === -1) { colIdx = cols.length; cols.push(e); }\n");
        sb.append("                layout[evt.id] = { col: colIdx, totalCols: 0 };\n");
        sb.append("            });\n");
        sb.append("            const total = cols.length;\n");
        sb.append("            const colW = 100 / total;\n");
        sb.append("            group.events.forEach(evt => {\n");
        sb.append("                layout[evt.id].totalCols = total;\n");
        sb.append("                layout[evt.id].left  = (layout[evt.id].col * colW) + '%';\n");
        sb.append("                layout[evt.id].width = colW + '%';\n");
        sb.append("            });\n");
        sb.append("        });\n");
        sb.append("        return layout;\n");
        sb.append("    },\n\n");
        sb.append("    createEventCard: function(event, top, hourHeight, startHourOffset, col, dateStr, withResize) {\n");
        sb.append("        const div = document.createElement('div');\n");
        sb.append("        div.className = 'event-card';\n");
        sb.append("        const c = event.couleur || '#0284c7';\n");
        sb.append("        div.style.backgroundColor = hexToRgba(c, 0.07);\n");
        sb.append("        div.style.color = c;\n");
        sb.append("        div.style.borderLeft = '3px solid ' + c;\n");
        sb.append("        div.style.top = `${top + 5}px`;\n");
        sb.append("        if (event.endHour > 0 && event.startHour >= 0) {\n");
        sb.append("            const durationH = event.endHour - event.startHour;\n");
        sb.append("            if (durationH > 0) div.style.height = `${durationH * hourHeight - 10}px`;\n");
        sb.append("        }\n");
        sb.append("        const timeLabel = event.endTime ? `${event.time} \\u2013 ${event.endTime}` : event.time;\n");
        sb.append("        const statutTag = event.statut ? `<span class=\"statut-tag\" style=\"color:${event.statut};background-color:${hexToRgba(event.statut,0.15)}\">${event.statutLabel || ''}</span>` : '';\n");
        sb.append("        div.innerHTML = `<div class=\"event-card-inner\"><div class=\"event-card-body\"><span class=\"event-title\">${event.title.replace('\\n', '<br>')}</span><span class=\"event-time\">${timeLabel}</span></div>${statutTag}</div>`;\n");
        sb.append("        div.addEventListener('click', (e) => {\n");
        sb.append("            if (DragDropManager.dragging || DragDropManager.justDragged) return;\n");
        sb.append("            e.stopPropagation();\n");
        sb.append("            if (event.lienClick && event.lienClick !== '') {\n");
        sb.append("                ouvrirModal(e, 'moduleLeger.jsp?but=' + event.lienClick + '&id=' + event.id, 'modalContent');\n");
        sb.append("            } else {\n");
        sb.append("                ModalUI.openEvent(event.id);\n");
        sb.append("            }\n");
        sb.append("        });\n");
        sb.append("        DragDropManager.makeEventDraggable(div, event, hourHeight, startHourOffset);\n");
        sb.append("        if (withResize && col) {\n");
        sb.append("            DragDropManager.makeEventResizable(div, event, col, dateStr, startHourOffset, hourHeight);\n");
        sb.append("        }\n");
        sb.append("        return div;\n");
        sb.append("    },\n");
        sb.append("    renderAllDayBanner: function(grid, dates) {\n");
        sb.append("        const allDayEvents = CalendarState.events.filter(e => {\n");
        sb.append("            if (!isEventVisible(e)) return false;\n");
        sb.append("            if (!e.allDay) return false;\n");
        sb.append("            return dates.includes(e.date);\n");
        sb.append("        });\n");
        sb.append("        const emptyHdr = document.createElement('div');\n");
        sb.append("        emptyHdr.className = 'allday-label';\n");
        sb.append("        emptyHdr.textContent = 'Jour';\n");
        sb.append("        grid.appendChild(emptyHdr);\n");
        sb.append("        dates.forEach(dateStr => {\n");
        sb.append("            const cell = document.createElement('div');\n");
        sb.append("            cell.className = 'allday-cell';\n");
        sb.append("            cell.dataset.date = dateStr;\n");
        sb.append("            DragDropManager.bindAlldayDrop(cell, dateStr);\n");
        sb.append("            const dayEvents = allDayEvents.filter(e => e.date === dateStr);\n");
        sb.append("            dayEvents.forEach(ev => {\n");
        sb.append("                const bar = document.createElement('div');\n");
        sb.append("                bar.className = 'allday-event';\n");
        sb.append("                const c = ev.couleur || '#0284c7';\n");
        sb.append("                bar.style.backgroundColor = hexToRgba(c, 0.07);\n");
        sb.append("                bar.style.color = c;\n");
        sb.append("                bar.style.borderLeft = '3px solid ' + c;\n");
        sb.append("                const statutTag = ev.statut ? `<span class=\"statut-tag\" style=\"background-color:${ev.statut}\"></span>` : '';\n");
        sb.append("                const evStatutTag = ev.statut ? `<span class=\"statut-tag\" style=\"color:${ev.statut};background-color:${hexToRgba(ev.statut,0.15)}\">${ev.statutLabel || ''}</span>` : '';\n");
        sb.append("                bar.innerHTML = `<div class=\"event-card-inner\"><div class=\"event-card-body\"><span class=\"allday-title\">${ev.title.replace('\\n', ' ')}</span></div>${evStatutTag}</div>`;\n");
        sb.append("                bar.addEventListener('click', (e) => {\n");
        sb.append("                    if (DragDropManager.dragging || DragDropManager.justDragged) return;\n");
        sb.append("                    e.stopPropagation();\n");
        sb.append("                    if (ev.lienClick && ev.lienClick !== '') {\n");
        sb.append("                        ouvrirModal(e, 'moduleLeger.jsp?but=' + ev.lienClick + '&id=' + ev.id, 'modalContent');\n");
        sb.append("                    } else { ModalUI.openEvent(ev.id); }\n");
        sb.append("                });\n");
        sb.append("                DragDropManager.makeEventDraggable(bar, ev, 80, 0);\n");
        sb.append("                cell.appendChild(bar);\n");
        sb.append("            });\n");
        sb.append("            grid.appendChild(cell);\n");
        sb.append("        });\n");
        sb.append("    },\n");
        sb.append("    renderDay: function(wrapper) {\n");
        sb.append("        const startHour = 0; const endHour = 23; const hourHeight = 80;\n");
        sb.append("        const grid = document.createElement('div'); grid.className = 'calendar-grid day-view';\n");
        sb.append("        grid.innerHTML += `<div class=\"grid-header\">Heure</div><div class=\"grid-header\">${CalendarState.daysOfWeek[(CalendarState.selectedDate.getDay() + 6) % 7]} <span>${String(CalendarState.selectedDate.getDate()).padStart(2,'0')}</span></div>`;\n");
        sb.append("        const targetDateStr = CalendarUtils.toYMD(CalendarState.selectedDate);\n");
        sb.append("        this.renderAllDayBanner(grid, [targetDateStr]);\n");
        sb.append("        let timeCol = `<div class=\"time-column\">`;\n");
        sb.append("        for (let i = startHour; i <= endHour; i++) timeCol += `<div class=\"time-slot\">${String(i).padStart(2,'0')}:00</div>`;\n");
        sb.append("        timeCol += `</div><div class=\"day-column\" id=\"day-col\"></div>`;\n");
        sb.append("        grid.insertAdjacentHTML('beforeend', timeCol);\n");
        sb.append("        wrapper.appendChild(grid);\n");
        sb.append("        const dayColEl = document.getElementById('day-col');\n");
        sb.append("        this.drawCurrentTimeLine(dayColEl, targetDateStr, startHour, endHour, hourHeight);\n");
        sb.append("        DragDropManager.bindTimedDrop(dayColEl, targetDateStr, startHour, hourHeight);\n");
        sb.append("        const dayEvents = CalendarState.events.filter(e => e.date === targetDateStr && !e.allDay && isEventVisible(e));\n");
        sb.append("        const layout = this.computeOverlapLayout(dayEvents);\n");
        sb.append("        dayEvents.forEach(e => {\n");
        sb.append("            let trueStartHour = parseFloat(e.startHour);\n");
        sb.append("            if (trueStartHour < 0) {\n");
        sb.append("                const parts = e.time ? e.time.split(':') : ['0','0'];\n");
        sb.append("                trueStartHour = parseInt(parts[0]) + (parseInt(parts[1] || 0) / 60);\n");
        sb.append("            }\n");
        sb.append("            const card = this.createEventCard(e, (trueStartHour - startHour) * hourHeight, hourHeight, startHour, dayColEl, targetDateStr, true);\n");
        sb.append("            const pos = layout[e.id];\n");
        sb.append("            if (pos) { card.style.left = pos.left; card.style.width = `calc(${pos.width} - 8px)`; card.style.right = 'unset'; }\n");
        sb.append("            dayColEl.appendChild(card);\n");
        sb.append("        });\n");
        sb.append("    },\n");
        sb.append("    renderWeek: function(wrapper) {\n");
        sb.append("        const startHour = 0; const endHour = 23; const hourHeight = 80;\n");
        sb.append("        const grid = document.createElement('div'); grid.className = 'calendar-grid week-view';\n");
        sb.append("        let headerHTML = `<div class=\"grid-header\">Heure</div>`;\n");
        sb.append("        const monday = CalendarUtils.getMonday(CalendarState.selectedDate); const weekDates = [];\n");
        sb.append("        for (let i = 0; i < 7; i++) { const d = new Date(monday); d.setDate(monday.getDate() + i); weekDates.push(CalendarUtils.toYMD(d)); headerHTML += `<div class=\"grid-header\">${CalendarState.daysOfWeek[i]} <span>${String(d.getDate()).padStart(2,'0')}</span></div>`; }\n");
        sb.append("        grid.innerHTML += headerHTML;\n");
        sb.append("        this.renderAllDayBanner(grid, weekDates);\n");
        sb.append("        let timeCol = `<div class=\"time-column\">`;\n");
        sb.append("        for (let i = startHour; i <= endHour; i++) timeCol += `<div class=\"time-slot\">${String(i).padStart(2,'0')}:00</div>`;\n");
        sb.append("        timeCol += `</div>`;\n");
        sb.append("        let daysCol = ''; for (let i = 0; i < 7; i++) daysCol += `<div class=\"day-column\" id=\"week-day-${i}\"></div>`;\n");
        sb.append("        grid.insertAdjacentHTML('beforeend', timeCol + daysCol);\n");
        sb.append("        wrapper.appendChild(grid);\n");
        sb.append("        for (let i = 0; i < 7; i++) {\n");
        sb.append("            const col = document.getElementById(`week-day-${i}`);\n");
        sb.append("            this.drawCurrentTimeLine(col, weekDates[i], startHour, endHour, hourHeight);\n");
        sb.append("            DragDropManager.bindTimedDrop(col, weekDates[i], startHour, hourHeight);\n");
        sb.append("        }\n");
        sb.append("        for (let i = 0; i < 7; i++) {\n");
        sb.append("            const col = document.getElementById(`week-day-${i}`);\n");
        sb.append("            const dateStr = weekDates[i];\n");
        sb.append("            const dayEvents = CalendarState.events.filter(event => {\n");
        sb.append("                if (event.allDay) return false;\n");
        sb.append("                if (event.date !== dateStr) return false;\n");
        sb.append("                if (!isEventVisible(event)) return false;\n");
        sb.append("                return true;\n");
        sb.append("            });\n");
        sb.append("            const layout = this.computeOverlapLayout(dayEvents);\n");
        sb.append("            dayEvents.forEach(event => {\n");
        sb.append("                let trueStartHour = parseFloat(event.startHour);\n");
        sb.append("                if (trueStartHour < 0) {\n");
        sb.append("                    const parts = event.time ? event.time.split(':') : ['0','0'];\n");
        sb.append("                    trueStartHour = parseInt(parts[0]) + (parseInt(parts[1] || 0) / 60);\n");
        sb.append("                }\n");
        sb.append("                const card = this.createEventCard(event, (trueStartHour - startHour) * hourHeight, hourHeight, startHour, col, dateStr, true);\n");
        sb.append("                const pos = layout[event.id];\n");
        sb.append("                if (pos && pos.totalCols > 1) {\n");
        sb.append("                    card.style.left = pos.left;\n");
        sb.append("                    card.style.width = `calc(${pos.width} - 6px)`;\n");
        sb.append("                    card.style.right = 'unset';\n");
        sb.append("                } else {\n");
        sb.append("                    card.style.left = '4px'; card.style.right = '4px';\n");
        sb.append("                }\n");
        sb.append("                col.appendChild(card);\n");
        sb.append("            });\n");
        sb.append("        }\n");
        sb.append("    },\n");
        sb.append("    renderMonth: function(wrapper) {\n");
        sb.append("        const grid = document.createElement('div'); grid.className = 'month-view';\n");
        sb.append("        let headerHTML = `<div class=\"grid-header\">Semaine</div>`; CalendarState.daysOfWeek.forEach(d => headerHTML += `<div class=\"grid-header\">${d}</div>`); grid.innerHTML += headerHTML;\n");
        sb.append("        const targetMonth = CalendarState.selectedDate.getMonth(); const firstDay = new Date(CalendarState.selectedDate.getFullYear(), targetMonth, 1);\n");
        sb.append("        let currentGridDate = new Date(firstDay); currentGridDate.setDate(currentGridDate.getDate() - (firstDay.getDay() === 0 ? 6 : firstDay.getDay() - 1));\n");
        sb.append("        for (let row = 0; row < 6; row++) {\n");
        sb.append("            if (row === 5 && currentGridDate.getMonth() !== targetMonth) break;\n");
        sb.append("            grid.innerHTML += `<div class=\"month-cell week-col\">Semaine ${CalendarUtils.getWeekNumber(currentGridDate)}</div>`;\n");
        sb.append("            for (let col = 0; col < 7; col++) {\n");
        sb.append("                const dateStr = CalendarUtils.toYMD(currentGridDate);\n");
        sb.append("                grid.innerHTML += `<div class=\"month-cell ${currentGridDate.getMonth() !== targetMonth ? 'out-of-month' : ''} ${dateStr === CalendarUtils.toYMD(new Date()) ? 'is-today' : ''}\" id=\"month-day-${dateStr}\"><div class=\"date-number\">${currentGridDate.getDate()}</div></div>`;\n");
        sb.append("                currentGridDate.setDate(currentGridDate.getDate() + 1);\n");
        sb.append("            }\n");
        sb.append("        }\n");
        sb.append("        wrapper.appendChild(grid);\n");
        sb.append("        document.querySelectorAll('.month-cell[id^=\"month-day-\"]').forEach(cell => {\n");
        sb.append("            DragDropManager.bindMonthDrop(cell, cell.id.replace('month-day-', ''));\n");
        sb.append("        });\n");
        sb.append("        CalendarState.events.forEach(event => {\n");
        sb.append("            if (!isEventVisible(event)) return;\n");
        sb.append("            const cell = document.getElementById(`month-day-${event.date}`);\n");
        sb.append("            if (!cell) return;\n");
        sb.append("            const currentCount = cell.querySelectorAll('.month-event').length;\n");
        sb.append("            if (currentCount < 3) {\n");
        sb.append("                const div = document.createElement('div');\n");
        sb.append("                div.className = 'month-event';\n");
        sb.append("                const mc = event.couleur || '#0284c7';\n");
        sb.append("                div.style.backgroundColor = hexToRgba(mc, 0.07);\n");
        sb.append("                div.style.color = mc;\n");
        sb.append("                div.style.borderLeft = '3px solid ' + mc;\n");
        sb.append("                const timeDisplay = event.allDay ? '' : (event.time || '');\n");
        sb.append("                const mStatutTag = event.statut ? `<span class=\"statut-tag\" style=\"color:${event.statut};background-color:${hexToRgba(event.statut,0.15)}\">${event.statutLabel || ''}</span>` : '';\n");
        sb.append("                div.innerHTML = `<div class=\"event-card-inner\"><div class=\"event-card-body\"><span class=\"evt-title\">${event.title.replace('\\n',' ')}</span><span>${timeDisplay}</span></div>${mStatutTag}</div>`;\n");
        sb.append("                div.addEventListener('click', (e) => {\n");
        sb.append("                    if (DragDropManager.dragging || DragDropManager.justDragged) return;\n");
        sb.append("                    e.stopPropagation();\n");
        sb.append("                    if (event.lienClick && event.lienClick !== '') {\n");
        sb.append("                        ouvrirModal(e, 'moduleLeger.jsp?but=' + event.lienClick + '&id=' + event.id, 'modalContent');\n");
        sb.append("                    } else { ModalUI.openEvent(event.id); }\n");
        sb.append("                });\n");
        sb.append("                DragDropManager.makeEventDraggable(div, event, 80, 0);\n");
        sb.append("                const moreContainer = cell.querySelector('.more-events-container');\n");
        sb.append("                if (moreContainer) cell.insertBefore(div, moreContainer); else cell.appendChild(div);\n");
        sb.append("            } else if (currentCount >= 3 && !cell.querySelector('.more-events-container')) {\n");
        sb.append("                const hiddenEvents = CalendarState.events.filter(e => e.date === event.date && isEventVisible(e)).slice(3);\n");
        sb.append("                const targetDate = event.date;\n");
        sb.append("                const moreContainer = document.createElement('div'); moreContainer.className = 'more-events-container';\n");
        sb.append("                const tooltip = document.createElement('div'); tooltip.className = 'events-tooltip';\n");
        sb.append("                hiddenEvents.forEach(he => {\n");
        sb.append("                    const tipItem = document.createElement('div');\n");
        sb.append("                    tipItem.className = 'tooltip-event';\n");
        sb.append("                    const tc = he.couleur || '#0284c7';\n");
        sb.append("                    tipItem.style.backgroundColor = hexToRgba(tc, 0.07);\n");
        sb.append("                    tipItem.style.color = tc;\n");
        sb.append("                    tipItem.style.borderLeft = '3px solid ' + tc;\n");
        sb.append("                    const tipTag = he.statut ? `<span class=\"statut-tag\" style=\"color:${he.statut};background-color:${hexToRgba(he.statut,0.15)}\">${he.statutLabel || ''}</span>` : '';\n");
        sb.append("                    tipItem.innerHTML = `<div class=\"event-card-inner\"><div class=\"event-card-body\"><strong>${he.title.replace('\\n',' ')}</strong><br><span style='color:var(--text-muted)'>${he.time}</span></div>${tipTag}</div>`;\n");
        sb.append("                    tipItem.addEventListener('click', (e) => {\n");
        sb.append("                        if (DragDropManager.dragging || DragDropManager.justDragged) return;\n");
        sb.append("                        e.stopPropagation();\n");
        sb.append("                        if (he.lienClick && he.lienClick !== '') {\n");
        sb.append("                            window.location.href = he.lienClick + '?id=' + he.id;\n");
        sb.append("                        } else { ModalUI.openEvent(he.id); }\n");
        sb.append("                    });\n");
        sb.append("                    tooltip.appendChild(tipItem);\n");
        sb.append("                });\n");
        sb.append("                const moreBtn = document.createElement('div'); moreBtn.className = 'more-events';\n");
        sb.append("                moreBtn.innerText = `+${hiddenEvents.length} de plus...`;\n");
        sb.append("                moreBtn.addEventListener('click', (e) => { e.stopPropagation(); CalendarState.selectedDate = new Date(targetDate); CalendarController.changeView('day'); });\n");
        sb.append("                moreContainer.appendChild(tooltip); moreContainer.appendChild(moreBtn);\n");
        sb.append("                cell.appendChild(moreContainer);\n");
        sb.append("            }\n");
        sb.append("        });\n");
        sb.append("    },\n");
        sb.append("    renderView: function() {\n");
        sb.append("        const wrapper = document.getElementById('calendarWrapper'); wrapper.innerHTML = '';\n");
        sb.append("        this.updateHeader();\n");
        sb.append("        this.renderLegend();\n");
        sb.append("        DragDropManager.lastOverCol = null;\n");
        sb.append("        if (CalendarState.currentView === 'day') this.renderDay(wrapper);\n");
        sb.append("        else if (CalendarState.currentView === 'week') this.renderWeek(wrapper);\n");
        sb.append("        else if (CalendarState.currentView === 'month') this.renderMonth(wrapper);\n");
        sb.append("    }\n");
        sb.append("};\n\n");
        sb.append("const CalendarController = {\n");
        sb.append("    changeView: function(newView) {\n");
        sb.append("        CalendarState.currentView = newView;\n");
        sb.append("        document.querySelectorAll('.view-toggles .toggle-btn').forEach(b => b.classList.remove('active'));\n");
        sb.append("        document.querySelector(`.view-toggles .toggle-btn[data-view=\"${newView}\"]`).classList.add('active');\n");
        sb.append("        CalendarUI.renderView();\n");
        sb.append("    },\n");
        sb.append("    init: function() {\n");
        sb.append("        document.getElementById('prevPeriodBtn').addEventListener('click', () => { CalendarState.navigate(-1); CalendarUI.renderView(); });\n");
        sb.append("        document.getElementById('nextPeriodBtn').addEventListener('click', () => { CalendarState.navigate(1); CalendarUI.renderView(); });\n");
        sb.append("        document.getElementById('goTodayBtn').addEventListener('click', () => { CalendarState.selectedDate = new Date(); this.changeView(CalendarState.currentView); });\n");
        sb.append("        document.querySelectorAll('.view-toggles .toggle-btn').forEach(btn => btn.addEventListener('click', (e) => this.changeView(e.target.dataset.view)));\n");
        sb.append("        document.getElementById('closeModalBtn').addEventListener('click', ModalUI.closeEvent);\n");
        sb.append("        document.getElementById('eventModalOverlay').addEventListener('click', (e) => { if (e.target.id === 'eventModalOverlay') ModalUI.closeEvent(); });\n");
        sb.append("        const dpBtn = document.getElementById('datePickerBtn'); const dpPopover = document.getElementById('datePickerPopover');\n");
        sb.append("        dpBtn.addEventListener('click', (e) => { e.stopPropagation(); dpPopover.classList.toggle('active'); CalendarState.dpDate = new Date(CalendarState.selectedDate.getFullYear(), CalendarState.selectedDate.getMonth(), 1); DatePickerUI.render(); });\n");
        sb.append("        dpPopover.addEventListener('click', (e) => e.stopPropagation());\n");
        sb.append("        document.addEventListener('click', () => dpPopover.classList.remove('active'));\n");
        sb.append("        document.getElementById('dpPrevMonth').addEventListener('click', () => { CalendarState.dpDate.setMonth(CalendarState.dpDate.getMonth() - 1); DatePickerUI.render(); });\n");
        sb.append("        document.getElementById('dpNextMonth').addEventListener('click', () => { CalendarState.dpDate.setMonth(CalendarState.dpDate.getMonth() + 1); DatePickerUI.render(); });\n");
        sb.append("        const daty1Initial = document.getElementById('daty1')?.value?.trim() || '';\n");
        sb.append("        if (daty1Initial) {\n");
        sb.append("            const parts = daty1Initial.split('/');\n");
        sb.append("            if (parts.length === 3) {\n");
        sb.append("                const d = new Date(parseInt(parts[2]), parseInt(parts[1]) - 1, parseInt(parts[0]));\n");
        sb.append("                if (!isNaN(d.getTime())) CalendarState.selectedDate = d;\n");
        sb.append("            }\n");
        sb.append("        }\n");
        sb.append("        DragDropManager.init();\n");
        sb.append("        this.changeView(CalendarState.currentView);\n");
        sb.append("    }\n");
        sb.append("};\n\n");
        sb.append("document.addEventListener('DOMContentLoaded', () => CalendarController.init());\n");
        sb.append("</script>\n");
        return sb.toString();
    }
}

