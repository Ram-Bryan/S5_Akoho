/*
 * Decompiled with CFR 0.152.
 */
package affichage;

import affichage.DashCard;
import affichage.Formulaire;
import affichage.Page;

public class Dashboard
extends Page {
    private DashCard[] cards;
    private String htmlCards;
    private String htmlCorps;
    private String apres;
    private int colSpanCards = -1;
    private boolean sansRecap;

    public boolean isSansRecap() {
        return this.sansRecap;
    }

    public void setSansRecap(boolean sansRecap) {
        this.sansRecap = sansRecap;
    }

    public int getColSpanCards() {
        return this.colSpanCards;
    }

    public void setColSpanCards(int colSpanCards) {
        this.colSpanCards = colSpanCards;
    }

    public String getApres() {
        return this.apres;
    }

    public void setApres(String apres) {
        this.apres = apres;
    }

    public String getHtmlCorps() {
        return this.htmlCorps;
    }

    public void setHtmlCorps(String htmlCorps) {
        this.htmlCorps = htmlCorps;
    }

    public String getHtmlCards() {
        return this.htmlCards;
    }

    public void setHtmlCards(String htmlCards) {
        this.htmlCards = htmlCards;
    }

    public DashCard[] getCards() {
        return this.cards;
    }

    public void setCards(DashCard[] cards) {
        this.cards = cards;
    }

    public void creerObjetDashCards() throws Exception {
        for (DashCard card : this.getCards()) {
            card.setUtilisateur(this.getUtilisateur());
            card.setLien(this.getLien());
            card.setApres(this.getApres());
            card.creerObjetDashCard();
            card.setLienPage(card.getLienPage() + card.getFormu().getListeCritereString());
            if (this.getColSpanCards() != -1) {
                card.setColSpan(this.getColSpanCards());
            }
            if (!this.isSansRecap()) continue;
            card.setSansRecap(true);
        }
    }

    public void creerObjetPage() throws Exception {
        this.creerObjetDashCards();
        this.makeHtmlCards();
        this.setHtmlCorps("");
        this.makeHtml();
    }

    public Dashboard(DashCard[] cards) {
        this.cards = cards;
        Formulaire formu = null;
        for (DashCard card : cards) {
            if (formu == null && card.isAvecDate()) {
                formu = card.getFormu();
                continue;
            }
            if (formu == null || !card.isAvecDate()) continue;
            card.setFormu(formu);
        }
        this.setFormu(formu);
    }

    public void makeHtmlCards() throws Exception {
        StringBuilder html = new StringBuilder();
        for (DashCard card : this.getCards()) {
            html.append(card.getHtml());
        }
        this.setHtmlCards(html.toString());
    }

    @Override
    public void makeHtml() {
        String html = this.getHtmlCards() + this.getHtmlCorps();
        this.setHtml(html);
    }

    public String getHtmlFormu() throws Exception {
        String html = this.getFormu().getHtmlEnsemble();
        return this.cacherMotsCles(html);
    }

    private String cacherMotsCles(String html) {
        return html.replaceFirst("(<div[^>]*class=\"mots-cless\"[^>]*)", "$1 style=\"display:none\"");
    }
}

