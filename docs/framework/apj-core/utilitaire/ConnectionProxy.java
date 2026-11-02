/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionProxy {
    private static final Set<Connection> openConnections = Collections.synchronizedSet(new HashSet());
    private static final Map<Connection, Long> creationTime = new ConcurrentHashMap<Connection, Long>();
    private static final Map<Connection, Exception> creationStackTraces = new ConcurrentHashMap<Connection, Exception>();
    private static final long MAX_OPEN_TIME_MS = 60000L;

    private static boolean isReallyClosed(Connection conn) {
        try {
            return conn.isClosed();
        }
        catch (SQLException e) {
            return true;
        }
    }

    private static void removeConnection(Connection conn) {
        openConnections.remove(conn);
        creationTime.remove(conn);
        creationStackTraces.remove(conn);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void purgeClosedConnections() {
        Set<Connection> set = openConnections;
        synchronized (set) {
            Iterator<Connection> iterator = openConnections.iterator();
            while (iterator.hasNext()) {
                Connection connection = iterator.next();
                if (!ConnectionProxy.isReallyClosed(connection)) continue;
                iterator.remove();
                creationTime.remove(connection);
                creationStackTraces.remove(connection);
            }
        }
    }

    public static int getOpenConnectionCount() {
        ConnectionProxy.purgeClosedConnections();
        return openConnections.size();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void printOpenConnections() {
        ConnectionProxy.purgeClosedConnections();
        System.out.println("Connexions ouvertes : " + ConnectionProxy.getOpenConnectionCount());
        Set<Connection> set = openConnections;
        synchronized (set) {
            for (Connection connection : openConnections) {
                Exception creationStack = creationStackTraces.get(connection);
                System.out.println(" - " + ConnectionProxy.getOpeningLocation(creationStack));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int closeAllOpenConnections() {
        ArrayList<Connection> connectionsToClose;
        ConnectionProxy.purgeClosedConnections();
        Set<Connection> set = openConnections;
        synchronized (set) {
            connectionsToClose = new ArrayList<Connection>(openConnections);
        }
        int closedCount = 0;
        for (Connection connection : connectionsToClose) {
            try {
                if (ConnectionProxy.isReallyClosed(connection)) continue;
                connection.close();
                ++closedCount;
            }
            catch (Exception e) {
                System.err.println("Erreur fermeture connexion : " + e.getMessage());
                e.printStackTrace();
            }
        }
        ConnectionProxy.purgeClosedConnections();
        return closedCount;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Connection wrap(final Connection realConnection) {
        ConnectionProxy.purgeClosedConnections();
        if (!openConnections.isEmpty()) {
            Set<Connection> set = openConnections;
            synchronized (set) {
                for (Connection c : openConnections) {
                    if (ConnectionProxy.isReallyClosed(c)) continue;
                    System.err.println("Connexion d\u00c3\u00a9j\u00c3\u00a0 ouverte avant d'en ouvrir une autre !");
                    Exception creationStack = creationStackTraces.get(c);
                    if (creationStack == null) continue;
                    System.err.println("Ouverture depuis : " + ConnectionProxy.getOpeningLocation(creationStack));
                }
            }
        }
        Exception creationStack = new Exception("Cr\u00c3\u00a9\u00c3\u00a9e ici");
        Connection proxyConn = (Connection)Proxy.newProxyInstance(Connection.class.getClassLoader(), new Class[]{Connection.class}, new InvocationHandler(){
            private boolean closed = false;

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                if (methodName.equals("close")) {
                    if (!this.closed) {
                        realConnection.close();
                        this.closed = true;
                        ConnectionProxy.removeConnection((Connection)proxy);
                        System.out.println("Fermeture de la connexion");
                    } else {
                        System.out.println("Connexion d\u00c3\u00a9j\u00c3\u00a0 ferm\u00c3\u00a9e");
                    }
                    return null;
                }
                if (methodName.equals("isClosed")) {
                    return this.closed;
                }
                return method.invoke(realConnection, args);
            }
        });
        openConnections.add(proxyConn);
        creationTime.put(proxyConn, System.currentTimeMillis());
        creationStackTraces.put(proxyConn, creationStack);
        System.out.println("Ouverture de la connexion depuis : " + ConnectionProxy.getOpeningLocation(creationStack));
        return proxyConn;
    }

    private static String getOpeningLocation(Exception creationStack) {
        if (creationStack == null) {
            return "Lieu d'ouverture inconnu";
        }
        for (StackTraceElement element : creationStack.getStackTrace()) {
            String className = element.getClassName();
            if (className.startsWith("java.lang.reflect.") || className.startsWith("sun.reflect.") || className.startsWith("utilitaire.ConnectionProxy") || className.toLowerCase().contains("utildb") || className.toLowerCase().contains("cgenutil")) continue;
            return element.getClassName() + "." + element.getMethodName() + "(" + element.getFileName() + ":" + element.getLineNumber() + ")";
        }
        return "Lieu d'ouverture inconnu";
    }
}

