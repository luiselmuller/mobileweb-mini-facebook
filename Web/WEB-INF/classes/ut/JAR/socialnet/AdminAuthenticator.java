/**
 * Social Network Project # 1
 * Mobile Web Course CPEN410
 * 
 * @author Luisel Muller
 * @author Ian Colon
 *
 * This class is the administrator class responsible of handling admin info from the database.
 */
package ut.JAR.socialnet;

import java.sql.*;

import org.apache.commons.codec.*;

// TODO: Make separate for admin
public class AdminAuthenticator
{
    private MiniSQLConnector dbConn;

    /**
    * Constructor for AdminAuthenticator class that creates a new instance of the MiniSQLConnector class and
    * establishes a connection to the database.
    */
    public AdminAuthenticator()
    {
        dbConn = new MiniSQLConnector();

        dbConn.doConnection();
    }

    /**
    * Method to authenticate the admin user by retrieving their information from the database and checking if their entered password matches their stored password after being hashed.
    * @param email The email address entered by the admin user
    * @param password The password entered by the admin user
    * @return an array of integers that represents the admin user's id and role id if the authentication was successful,
    * or an array containing the value 0 if the authentication failed (either due to incorrect password or non-existent user).
    * @throws SQLException if there is an error executing the SQL statement.
    */
    public int[] authenticate(String email, String password) throws SQLException
    {
        String tables = "admin";

        String fields = "id, email, salt, password, role_id";

        String whereClause = "admin.email = ?";

        PreparedStatement pst = dbConn.prepareStatement("SELECT " + fields + " FROM " + tables + " WHERE " + whereClause);

        pst.setString(1, email);

        ResultSet rs = pst.executeQuery();

        if(rs.next())
        {
            int roleId = rs.getInt("role_id");
            int userId = rs.getInt("id");
            String salt = rs.getString("salt");
            String hash = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password + salt);

            if(hash.equals(rs.getString("password")))
            {
                int[] user = {userId, roleId};
                return user;
            }
            else
            {
                int[] user = {0};
                // password error
                return user;
            }
        }
        else
        {
            int[] user = {0};
            // user not found
            return user;
        }
    }

    /**
     * Closes connection to the database
     */
    public void close()
	{
		// Close the connection
		dbConn.closeConnection();
	}
}