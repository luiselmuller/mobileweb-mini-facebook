/**
 * Social Network Project # 1
 * Mobile Web Course CPEN410
 * 
 * @author Luisel Muller
 * @author Ian Colon
 *
 * This is the connector to the database
 */
package ut.JAR.socialnet;

// Package for managing ResultSet objects
import java.sql.*;

/**
 * This class manages the connection to the social network database
 */
public class MiniSQLConnector
{
    // Database credentials
    private String DB_URL = "jdbc:mysql://localhost/socialnet";

    private String USER = "toplevel";
    private String PASS = "Sjn86@jab8$nKlqL2";

    // Connection Objects
    private Connection conn;
    
    // Statement object for queries
    private PreparedStatement pst;

    /**
     * Default Constructor
     * @parameters:
     * 
     */
    public MiniSQLConnector()
    {
        // Define connection objects as null
        conn = null;
        pst = null;
    }

    /**
     *  Method that creates a new connection object and opens a connection to the databse
     *  @parameters:
     *
     */
    public void doConnection()
    {
        try
        {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            
            System.out.println("Connecting to database...");

            // Open a connection using the database credentials
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to the database...");

            // Create statement object for performing queries
            pst = conn.prepareStatement("");
            System.out.println("Statement ok...");
        } 
        catch(SQLException sqlex)
        {
            sqlex.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method to close the connection to the database
     * @parameters:
     */
    public void closeConnection()
    {
        try
        {
            // Close the statement object
            pst.close();
            // Close the connection to the database
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
    *   Method to perform a selection query to the database
    *   @parameters:
    *		fields: list of fields to be projected from the tables
    *		tables: list of tables to be selected
    *		where: where clause
    *	@returns:
    *		ResulSet result containing the project tuples resulting from the query
    **/
    public ResultSet doSelect(String fields, String tables, String where)
    {
        // Create a ResultSet
        ResultSet result = null;

        // Create the selection statement
        String selectionStatement = "Select " + fields + " from " + tables +  " where " + where + ";";
        System.out.println(selectionStatement);

        try 
        {
            // Perform the query and catch the results in the result object
            result = pst.executeQuery(selectionStatement);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // Return results
            return result;
        }
    }
    
    /**
    *   Method to perform a selection query to the database
    *   @parameters:
    *		fields: list of fields to be projected from the tables
    *		tables: list of tables to be selected
    *	@returns:
    *		ResulSet result containing the project tuples resulting from the query
    **/
    public ResultSet doSelect(String fields, String tables)
    {
        // Create a ResultSet
        ResultSet result = null;

        // Create the selection statement
        String selectionStatement = "Select " + fields + " from " + tables + ";";
        System.out.println(selectionStatement);

        try 
        {
            // Perform the query and catch the results in the result object
            result = pst.executeQuery(selectionStatement);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // Return results
            return result;
        }
    }

    /**
    *   Method to perform a selection query to the database
    *   @parameters:
    *       fields: list of fields to be projected from the tables
    *       tables: list of tables to be selected
    *       where: where clause
    *       orderBy: order by condition
    *   @returns:
    *       ResulSet result containing the project tuples resulting from the query
    **/
    public ResultSet doSelect(String fields, String tables, String where, String orderBy)
    {
        
        //Create a ResulSet
        ResultSet result=null;
        
        //Create the selection statement 
        String selectionStatement = "Select" + fields + " from " + tables + " where " + where + " order by " + orderBy + ";";
        
        try
        {
            // Perform the query and catch results in the result object
            result = pst.executeQuery(selectionStatement);
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // Return results
            return result;
        }
    }

    /**
     *  Method to perform an insertion to the database
     *  @parameters:
     *      values: values to be inserted
     *      table: table to be updated
     *  @returns:
     *      boolean value: true: the insertion was ok, an error was generated
     */
    public boolean doInsert(String sql)
    {
        boolean res = false;

        System.out.println(sql);

        // Try to insert a record to the selected table
        try
        {
            res = pst.execute(sql);
            System.out.println("MiniSQLConnector insertion: " + res);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return res;
        }
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException
    {
        return conn.prepareStatement(sql);
    }

    // for user ids
    public PreparedStatement prepareStatement(String sql, int generatedKeys) throws SQLException
    {
        return conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }


    /**
     *  Debugging method
     *  Method that creates an applicationDBManager object, retrieves all departments in the database and closes the connection to the database
     *  @parameters:
     *      ars[]: String array
     *  @returns:
     */
    public static void main(String[] args)
    {
        System.out.println("Test");

        // Create a MiniSQLConnector object
        MiniSQLConnector conn = new MiniSQLConnector();

        // Declare fields, tables and where clause
        String fields, tables, whereClause;

        fields = "name";
        tables = "role";
        whereClause="id>-1";

        try
        {
            System.out.println("Connecting...");

            // Establish the database connection
            conn.doConnection();

            // Perform the query using the doSelect method with 3 parameters
            ResultSet res = conn.doSelect(fields, tables, whereClause);

            // Iterate over the ResultSet containing the fields in the table and count how many tuples were retrieved
            int count = 0;
            while(res.next())
            {
                count++;
            }
            
            // Print out the count result
            System.out.println("Counted " + count + " tuples");

            // Close the ResultSet
            res.close();

            // Close the database connection
            conn.closeConnection();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
    
