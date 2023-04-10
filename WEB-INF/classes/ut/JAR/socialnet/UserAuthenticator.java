/**
 * Social Network Project # 1
 * Mobile Web Course CPEN410
 * 
 * @author Luisel Muller
 * @author Ian Colon
 *
 * This document contans the methods and classes for the user authentication with DB.
 * The UserAuthenticator class responsible for authenticating user credentials and returning user role ID and user ID
 */

package ut.JAR.socialnet;

import java.sql.*;

import org.apache.commons.codec.*;

// TODO: Make separate for admin
public class UserAuthenticator
{
    private MiniSQLConnector dbConn;

    /**
     * Constructor that initializes a MiniSQLConnector object and connects to the database
     */
    public UserAuthenticator()
    {
        dbConn = new MiniSQLConnector();

        dbConn.doConnection();
    }

    /**
    * Authenticates user credentials and returns user role ID and user ID or 0 if authentication fails.
    *
    * @param email    the email of the user to authenticate.
    * @param password the password of the user to authenticate.
    * @return an integer array containing user ID and role ID if authentication is successful, or an array containing 0 otherwise.
    * @throws SQLException if a database access error occurs or this method is called on a closed connection.
    */
    public int[] authenticate(String email, String password) throws SQLException
    {
        String tables = "user";

        String fields = "id, email, salt, password, role_id";

        String whereClause = "user.email = ?";

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
     * Closes the connection to the database
     */
    public void close()
	{
		//Close the connection
		dbConn.closeConnection();
	}
}