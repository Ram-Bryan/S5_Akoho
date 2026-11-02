/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.commons.fileupload.FileItem
 *  org.apache.commons.fileupload.disk.DiskFileItem
 *  user.UserEJB
 */
package affichage;

import affichage.PageInsert;
import bean.CGenUtil;
import bean.ClassMAPTable;
import bean.ListeColonneTable;
import java.lang.reflect.Field;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import user.UserEJB;
import utilitaire.Utilitaire;

public class PageUpload
extends PageInsert {
    protected HashMap<String, String> valeurFromFileUpload;
    protected List<FileItem> fileItems;

    public PageUpload() {
    }

    public PageUpload(ClassMAPTable o, HttpServletRequest req, UserEJB u) throws Exception {
        this.setBase(o);
        this.setReq(req);
        this.setUtilisateur(u);
        this.makeFormulaire();
    }

    public PageUpload(ClassMAPTable o, HttpServletRequest req) throws Exception {
        this.setBase(o);
        this.setReq(req);
    }

    public PageUpload(DiskFileItem[] items) throws Exception {
        this.init(items);
        this.setBase(this.getInitiale());
    }

    public HashMap<String, String> getValeurFromFileUpload() {
        return this.valeurFromFileUpload;
    }

    public List<FileItem> getFileItems() {
        return this.fileItems;
    }

    protected ClassMAPTable getInitiale() throws Exception {
        String classe = this.getValeur("classe");
        return (ClassMAPTable)Class.forName(classe).newInstance();
    }

    public String getValeur(String nom) throws Exception {
        return this.valeurFromFileUpload.get(nom);
    }

    private void init(DiskFileItem[] items) throws Exception {
        this.valeurFromFileUpload = new HashMap();
        this.fileItems = new ArrayList<FileItem>();
        for (DiskFileItem item : items) {
            if (item.isFormField()) {
                this.valeurFromFileUpload.put(item.getFieldName(), item.getString("UTF-8"));
                continue;
            }
            this.fileItems.add((FileItem)item);
        }
    }

    @Override
    public ClassMAPTable getObjectAvecValeur() throws Exception {
        try {
            if (this.valeurFromFileUpload == null) {
                return super.getObjectAvecValeur();
            }
            Field[] tempChamp = ListeColonneTable.getFieldListeHeritage(this.getBase());
            for (int i = 0; i < tempChamp.length; ++i) {
                Field f = tempChamp[i];
                String valeur = this.getValeur(f.getName());
                if (valeur == null) continue;
                this.getBase().setMode("modif");
                if (f.getType().getName().compareToIgnoreCase("java.lang.String") == 0) {
                    CGenUtil.setValChamp(this.getBase(), f, valeur);
                }
                if (f.getType().getName().compareToIgnoreCase("java.sql.Date") == 0) {
                    CGenUtil.setValChamp(this.getBase(), f, Utilitaire.string_date("dd/MM/yyyy", valeur));
                }
                if (f.getType().getName().compareToIgnoreCase("java.lang.Double") == 0) {
                    CGenUtil.setValChamp(this.getBase(), f, new Double(Utilitaire.stringToDouble(valeur)));
                }
                if (f.getType().getName().compareToIgnoreCase("java.lang.Integer") == 0) {
                    if (valeur == null || valeur.compareToIgnoreCase("") == 0) {
                        CGenUtil.setValChamp(this.getBase(), f, new Integer(0));
                    } else {
                        CGenUtil.setValChamp(this.getBase(), f, new Integer(valeur));
                    }
                }
                if (f.getType().getName().compareToIgnoreCase("float") == 0) {
                    CGenUtil.setValChamp(this.getBase(), f, new Float(Utilitaire.stringToFloat(valeur)));
                }
                if (f.getType().getName().compareToIgnoreCase("double") == 0) {
                    CGenUtil.setValChamp(this.getBase(), f, new Double(Utilitaire.stringToDouble(valeur)));
                }
                if (f.getType().getName().compareToIgnoreCase("int") == 0) {
                    CGenUtil.setValChamp(this.getBase(), f, new Integer(Utilitaire.stringToInt(valeur)));
                }
                if (f.getType().getName().compareToIgnoreCase("java.sql.Time") != 0) continue;
                String[] val = valeur.split(":");
                CGenUtil.setValChamp(this.getBase(), f, new Time(Integer.parseInt(val[0]), Integer.parseInt(val[1]), Integer.parseInt(val[2])));
            }
            String nomTable = this.getValeur("nomtable");
            if (nomTable != null) {
                this.getBase().setNomTable(nomTable);
            }
            return this.getBase();
        }
        catch (NumberFormatException n) {
            throw new Exception("format de nombre invalide");
        }
        catch (Exception e) {
            throw e;
        }
    }
}

