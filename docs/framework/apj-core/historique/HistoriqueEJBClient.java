/*
 * Decompiled with CFR 0.152.
 */
package historique;

import historique.HistoriqueLocal;
import historique.HistoriqueRemote;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class HistoriqueEJBClient {
    public static HistoriqueRemote lookupHistoriqueEJBBeanRemote() {
        try {
            Properties properties = new Properties();
            properties.put("java.naming.factory.initial", "org.jboss.naming.remote.client.InitialContextFactory");
            properties.put("java.naming.provider.url", "remote://127.0.0.1:4447");
            properties.put("java.naming.security.principal", "cnaps");
            properties.put("java.naming.security.credentials", "cnaps123");
            properties.put("jboss.naming.client.ejb.context", true);
            InitialContext context = new InitialContext(properties);
            return (HistoriqueRemote)context.lookup("cnaps/cnaps-ejb/UserEJBBean!user.UserEJBRemote");
        }
        catch (NamingException ne) {
            throw new RuntimeException(ne);
        }
    }

    public static HistoriqueLocal lookupHistoriqueEJBBeanLocal() {
        try {
            InitialContext c = new InitialContext();
            return (HistoriqueLocal)c.lookup("java:module/HistoriqueBean!historique.HistoriqueLocal");
        }
        catch (NamingException ne) {
            throw new RuntimeException(ne);
        }
    }
}

