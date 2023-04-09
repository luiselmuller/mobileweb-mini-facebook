package ut.JAR.socialnet;

// Package for managing ResultSet objects
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import javax.servlet.http.HttpSession;


/**
 *  This class manages the connection to the database and is accessed from the front end without showing how to
 *  access the database
 *  
 */
public class AdminDBManager
{
    // Object to access the database
    private MiniSQLConnector dbConn;

    /**
     *  Default Constructor
     *  Creates a new MiniSQLConnector object and opens the connection to the database
     *  @parameters:
     */
    public AdminDBManager()
    {
        // Create the connector object
        dbConn = new MiniSQLConnector();

        // Open the connection to the database
        dbConn.doConnection();
    }
    
    /****************************************************************************
     * 
     * ============================= AADMIN METHODS =============================
     * 
     ***************************************************************************/

    // dob, gender, pfp
    public void updateAdminInfo(int userId, String dob, String gender) throws SQLException
    {
        String updateQuery = "UPDATE admin SET dob = ?, gender = ? WHERE id = ?";
        PreparedStatement ust = dbConn.prepareStatement(updateQuery);
        ust.setString(1, dob);
        ust.setString(2, gender);
        ust.setInt(3, userId);
        ust.executeUpdate();
    }

    public void updateAdminImage(int userId, String pfp) throws SQLException
    {
        String updateQuery = "UPDATE admin SET profile_picture = ? WHERE id = ?";
        PreparedStatement ust = dbConn.prepareStatement(updateQuery);
        ust.setString(1, pfp);
        ust.setInt(2, userId);
        ust.executeUpdate();
    }


    public void getAdminInfo(int userId, HttpSession session) throws SQLException
    {
        String tables = "admin a";
        String fields = "a.id, a.role_id, a.dob, a.gender, a.profile_picture";
        String whereClause = "a.id = ?";

        PreparedStatement ps = dbConn.prepareStatement("SELECT " + fields + " FROM " + tables + " WHERE " + whereClause);

        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        if(rs.next())
        {
            String dob = rs.getString("dob");
            String gender = rs.getString("gender");
            String pfp = rs.getString("profile_picture");
            String roleID = rs.getString("role_id");

            // for the profile picture
            String directory = "D:/apache-tomcat-8.5.85/webapps/ROOT/socialnet/";
            String relativePath = pfp == null ? "socialnet/default_pfp.png" : pfp.substring(directory.length());
    
            session.setAttribute("dob", dob);
            session.setAttribute("gender", gender);
            session.setAttribute("pfp", relativePath);
            session.setAttribute("roleID", roleID);
        }
    }

    // TODO: Search for user
    // TODO: Admin CRUD methods probably separate into their own file AdminDBManager.java

    /**
     *  Method that closes the connection to the database
     *  @parameters:
     *  @returns:
     */
    public void close()
    {
        // Close the connection to the database
        dbConn.closeConnection();
    }

    /***********
		Debugging method
			This method creates an applicationDBManager object, retrieves all departments in the database, and close the connection to the database
			@parameters:
				args[]: String array 
			@returns:
	*/
	public static void main(String[] args)
	{
		
			
	}
}
