package fr.flst.jee.mmarie;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Maximilien on 01/10/2014.
 */
public class TestUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestUtils.class);
    public static final String FIXTURES_FILE = "data-dbunit.xml";

    private static Connection connection;
    private static IDataSet dbUnitDataset;

    public static Connection getJDBCConnection(){
        try{
            if (connection == null){
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
                connection = DriverManager.getConnection("jdbc:derby:memory:BookStoreDB;");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    public static void reloadDbUnitData() throws Exception {
        Connection connection = getJDBCConnection();

        String[] tables = {"AUTHORS", "MAILING_ADDRESSES"};
        for (String table : tables) {
            PreparedStatement stmt = connection
                    .prepareStatement("ALTER TABLE "+table+" ALTER COLUMN id RESTART WITH 1");
            stmt.execute();
            stmt.close();
        }


		/* load the first time only */
        if (dbUnitDataset == null){
            dbUnitDataset = new XmlDataSet(Thread.currentThread()
                    .getContextClassLoader().getResourceAsStream(FIXTURES_FILE));
        }

        // Clean the data from previous test and insert new data test.
        DatabaseOperation.CLEAN_INSERT.execute(new DatabaseConnection(connection), dbUnitDataset);

        connection.commit();
        closeJDBCConnection();
    }

    public static void closeJDBCConnection(){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("Impossible to close sql connection");
            }
            connection = null;
        }
    }
}
