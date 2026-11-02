/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  user.UserEJB
 */
package affichage;

import affichage.PageInsert;
import bean.ClassMAPTable;
import javax.servlet.http.HttpServletRequest;
import user.UserEJB;

public class PageInsertPopup
extends PageInsert {
    protected String champReturn;
    protected String champUrl;

    @Override
    public String getChampReturn() {
        return this.champReturn;
    }

    @Override
    public void setChampReturn(String champReturn) {
        this.champReturn = champReturn;
    }

    @Override
    public String getChampUrl() {
        return this.champUrl;
    }

    @Override
    public void setChampUrl(String champUrl) {
        this.champUrl = champUrl;
    }

    public PageInsertPopup(ClassMAPTable o, HttpServletRequest req, UserEJB u) throws Exception {
        super(o, req, u);
    }

    @Override
    public void makeFormulaire() throws Exception {
        super.makeFormulaire();
        this.setChampReturn(this.getReq().getParameter("champReturn"));
        this.setChampUrl(this.getReq().getParameter("champUrl"));
    }

    @Override
    public String getHtmlAddOnPopup() {
        String rep = "";
        rep = rep + "<input type=\"hidden\" name=\"champReturn\" value=\"" + this.champReturn + "\" />";
        rep = rep + "<input type=\"hidden\" name=\"champUrl\" value=\"" + this.champUrl + "\" />";
        rep = rep + "<input type=\"hidden\" name=\"rajoutLien\" value=\"champReturn-champUrl" + this.getRajoutLienAPartirChampUrl() + "\" />";
        return rep;
    }

    private String getRajoutLienAPartirChampUrl() {
        String[] champs = this.champUrl.split(";");
        if (champs[0].compareToIgnoreCase(champs[1]) != 0) {
            return "-" + champs[1];
        }
        return "";
    }
}

