/**
 * Social Network Project # 1
 * Mobile Web Course CPEN410
 * 
 * @author Luisel Muller
 * @author Ian Colon
 *
 * This class is the Admin manager for the database info.
 */
package ut.JAR.socialnet;

// Package for managing ResultSet objects
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    /**
     * Updates the date of birth and gender for an administrator in the database
     * @parameters: userId - an integer representing the ID of the administrator to update
     *              dob - a String representing the new date of birth for the administrator
     *              gender - a String representing the new gender for the administrator
     * @returns:
     * @throws: SQLException
     */
    public void updateAdminInfo(int userId, String dob, String gender) throws SQLException
    {
        String updateQuery = "UPDATE admin SET dob = ?, gender = ? WHERE id = ?";
        PreparedStatement ust = dbConn.prepareStatement(updateQuery);
        ust.setString(1, dob);
        ust.setString(2, gender);
        ust.setInt(3, userId);
        ust.executeUpdate();
    }

    /**
     * Updates the profile picture for an administrator in the database
     * @parameters: userId - an integer representing the ID of the administrator to update
     *              pfp - a String representing the new file path for the administrator's profile picture
     * @returns:
     * @throws: SQLException
     */
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
            String relativePath = pfp == null ? "socialnet/profile_picture/default_pfp.png" : pfp.substring(directory.length());
    
            session.setAttribute("dob", dob);
            session.setAttribute("gender", gender);
            session.setAttribute("admin-pfp", relativePath);
            session.setAttribute("roleID", roleID);
        }
    }

    // Get all users
    public List<User> getAllUsers() throws SQLException
    {
        List<User> userList = new ArrayList<>();
        String tables = "user u";
        String fields = "u.id, u.first_name, u.last_name, u.email, u.dob, u.gender, u.profile_picture, l.country, l.state, l.town, l.street, e.degree, e.field_of_study, e.school";
        String locationJoin = "LEFT JOIN location l ON u.id = l.user_id";
        String educationJoin = "LEFT JOIN education e ON u.id = e.user_id";

        PreparedStatement st = dbConn.prepareStatement("SELECT " + fields + " FROM " + tables + " " + locationJoin + " " + educationJoin);
        ResultSet rs = st.executeQuery();
        
        while(rs.next())
        {
            int id = rs.getInt("u.id");
            Map<String, String> userInfo = new HashMap<>();

            ResultSetMetaData rsm = rs.getMetaData();
            int columnCount = rsm.getColumnCount();
            for(int i = 1; i <= columnCount; i++)
            {
                String colName = rsm.getColumnName(i);
                String colVal = rs.getString(colName);
                userInfo.put(colName, colVal);
            }

            userList.add(new User(id, userInfo));
        }

        rs.close();
        st.close();

        return userList;
    }

    public void deleteUser(int userId) throws SQLException
    {
        String query = "DELETE FROM user WHERE id = ?";
        PreparedStatement st = dbConn.prepareStatement(query);

        st.setInt(1, userId);
        st.executeUpdate();

        st.close();
    }

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
