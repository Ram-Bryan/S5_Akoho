/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  files.Log
 */
package bean;

import files.Log;
import java.sql.Connection;

public class ErreurDAO
extends Exception {
    public ErreurDAO(String msgErreur) {
        super(msgErreur);
    }

    public ErreurDAO(Connection c, String msgErreur) {
        super(msgErreur);
        try {
            c.rollback();
        }
        catch (Exception e) {
            Log.log((String)"Erreur annulation dans ErrerDAO.java ".concat(String.valueOf(String.valueOf(e.getMessage()))));
        }
    }
}

