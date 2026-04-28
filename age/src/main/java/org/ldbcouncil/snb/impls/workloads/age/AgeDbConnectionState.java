package org.ldbcouncil.snb.impls.workloads.age;

import org.ldbcouncil.snb.driver.DbException;
import org.ldbcouncil.snb.impls.workloads.BaseDbConnectionState;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * Holds the JDBC connection to Apache AGE (PostgreSQL with AGE extension).
 * On construction, loads the AGE extension and sets the search_path for the session.
 *
 * Thread safety note: a single JDBC connection is not thread-safe. For benchmarks
 * with thread_count > 1 consider replacing this with a HikariCP connection pool
 * (pool size = thread_count). See README for details.
 */
public class AgeDbConnectionState extends BaseDbConnectionState<AgeQueryStore> {

    private final Connection connection;
    private final boolean printQueryNames;
    private final boolean printQueryStrings;
    private final boolean printQueryResults;

    public AgeDbConnectionState(Map<String, String> properties, AgeQueryStore queryStore)
            throws DbException {
        super(properties, queryStore);

        String endpoint = properties.getOrDefault("age_endpoint", "localhost:5432/ldbc");
        String user = properties.getOrDefault("age_user", "postgres");
        String password = properties.getOrDefault("age_password", "");

        printQueryNames = Boolean.parseBoolean(properties.getOrDefault("printQueryNames", "false"));
        printQueryStrings = Boolean.parseBoolean(properties.getOrDefault("printQueryStrings", "false"));
        printQueryResults = Boolean.parseBoolean(properties.getOrDefault("printQueryResults", "false"));

        // Build JDBC URL from endpoint (host:port/dbname format)
        String jdbcUrl = "jdbc:postgresql://" + endpoint;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcUrl, user, password);
            // Session-scoped AGE initialization
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("LOAD 'age'");
                stmt.execute("SET search_path = ag_catalog, public");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DbException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isPrintQueryNames() { return printQueryNames; }
    public boolean isPrintQueryStrings() { return printQueryStrings; }
    public boolean isPrintQueryResults() { return printQueryResults; }

    public void logQuery(String operationName, String queryString) {
        if (printQueryNames) System.out.println("[AGE] " + operationName);
        if (printQueryStrings) System.out.println("[AGE] " + queryString);
    }

    @Override
    public void close() throws IOException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
