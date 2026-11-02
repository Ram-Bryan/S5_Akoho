/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.TimeZone;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import utilitaire.ConnectionProxy;
import utilitaire.PropertiesLoader;

public class UtilDB {
    Connection conn = null;
    public static PropertiesLoader loader = PropertiesLoader.getInstance();
    public static Properties properties = loader.getProperties();
    static int COUNT_CONN = 0;
    public static final String INITIAL_CONTEXT_FACTORY = "org.jnp.interfaces.NamingContextFactory";
    public static final String PROVIDER_URL = "localhost:1099";

    public static InitialContext getContext() throws NamingException {
        Properties env = new Properties();
        env.put("java.naming.factory.initial", INITIAL_CONTEXT_FACTORY);
        env.put("java.naming.provider.url", PROVIDER_URL);
        env.put("java.naming.rmi.security.manager", "yes");
        env.put("java.naming.factory.url.pkgs", "org.jboss.naming");
        return new InitialContext(env);
    }

    public UtilDB(String user, String passWOracle) {
        try {
            InitialContext jndiContext = UtilDB.getContext();
            DataSource ds = (DataSource)jndiContext.lookup("java:VVFinances");
            this.conn = ds.getConnection(user, passWOracle);
        }
        catch (NamingException ne) {
            System.out.println("UtilDB Erreur Naming : ".concat(String.valueOf(String.valueOf(ne.getMessage()))));
        }
        catch (SQLException se) {
            System.out.println("UtilDB Erreur Connexion : ".concat(String.valueOf(String.valueOf(se.getMessage()))));
        }
    }

    public UtilDB() {
    }

    public static int getOpenConnectionCount() {
        return ConnectionProxy.getOpenConnectionCount();
    }

    public static void printOpenConnections() {
        ConnectionProxy.printOpenConnections();
    }

    public static int closeAllOpenConnections() {
        return ConnectionProxy.closeAllOpenConnections();
    }

    private void setDefaulteDateFormat(Connection con) throws Exception {
        if (con == null) {
            return;
        }
        try (Statement stmt = null;){
            stmt = con.createStatement();
            PropertiesLoader loader = PropertiesLoader.getInstance();
            Properties properties = loader.getProperties();
            String req = "";
            if (properties.getProperty("apj.connection.driver") == null || properties.getProperty("apj.connection.driver").contains("oracle")) {
                req = "ALTER SESSION SET NLS_DATE_FORMAT = 'dd/mm/yyyy'";
            } else if (properties.getProperty("apj.connection.driver").contains("postgresql")) {
                req = "SET datestyle TO DMY";
            }
            stmt.executeUpdate(req);
        }
    }

    public Connection GetConn() {
        Connection conn = null;
        try {
            String datasource = properties.getProperty("apj.datasource");
            if (datasource == null || datasource.isEmpty()) {
                TimeZone timeZone = TimeZone.getTimeZone("Asia/Baghdad");
                TimeZone.setDefault(timeZone);
                String driver = "oracle.jdbc.driver.OracleDriver";
                if (properties.getProperty("apj.connection.driver") != null) {
                    driver = properties.getProperty("apj.connection.driver");
                }
                Class.forName(driver);
                String url = properties.getProperty("apj.connection.url");
                String user = properties.getProperty("apj.connection.user");
                String password = properties.getProperty("apj.connection.password");
                conn = DriverManager.getConnection(url, user, password);
                this.setDefaulteDateFormat(conn);
            } else {
                InitialContext jndiContext = new InitialContext();
                DataSource ds = (DataSource)jndiContext.lookup("java:/" + datasource);
                conn = ds.getConnection();
            }
        }
        catch (Exception ne) {
            System.out.println("Erreur etablissement connection : " + ne.getMessage());
        }
        return conn;
    }

    public Connection GetConnFinance() {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection2 = conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.88.18:1521:orcl", "financemax", "financemax");
            return connection2;
        }
        catch (ClassNotFoundException var3) {
            ClassNotFoundException ex = var3;
            ex.printStackTrace();
        }
        catch (SQLException var4) {
            SQLException ex = var4;
            System.out.println("UtilDB Erreur Connexion : ".concat(String.valueOf(String.valueOf(ex.getMessage()))));
        }
        return null;
    }

    public void commitON() {
        try {
            this.conn.setAutoCommit(true);
        }
        catch (Exception e) {
            System.out.println(" ** Erreur commit on: ".concat(String.valueOf(String.valueOf(e.getMessage()))));
        }
    }

    public void commitOFF() {
        try {
            this.conn.setAutoCommit(false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close_connection() {
        try {
            if (this.conn != null) {
                this.conn.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void valider() {
        try {
            this.conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void annuler() {
        try {
            this.conn.rollback();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

