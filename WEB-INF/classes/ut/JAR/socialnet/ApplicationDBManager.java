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
public class ApplicationDBManager
{
    // Object to access the database
    private MiniSQLConnector dbConn;

    /**
     *  Default Constructor
     *  Creates a new MiniSQLConnector object and opens the connection to the database
     *  @parameters:
     */
    public ApplicationDBManager()
    {
        // Create the connector object
        dbConn = new MiniSQLConnector();

        // Open the connection to the database
        dbConn.doConnection();
    }
    
    /****************************************************************************
     * 
     * ============================= USER METHODS =============================
     * 
     ***************************************************************************/

    /**
     * 
     * @param email
     * @param password
     * @param first_name
     * @param last_name
     * @return
     * @throws SQLException
     */
    public int addUser(String email, String password, String first_name, String last_name) throws SQLException
	{
        String query = "INSERT INTO user (email, password, first_name, last_name, salt) VALUES (?, ?, ?, ?, ?)";

        // Generate salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String s = Base64.getEncoder().encodeToString(salt);

		String hashingValue = DigestUtils.sha256Hex(password + s);

        // Check if user exists
        String check = "SELECT COUNT(*) AS count FROM user WHERE EMAIL = ?";
        PreparedStatement cqst = dbConn.prepareStatement(check);
        cqst.setString(1, email);
        ResultSet cr = cqst.executeQuery();
        cr.next();
        int count = cr.getInt("count");
        if(count > 0)
        {
            // User exists
            return -1;
        }

        PreparedStatement pst = dbConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        pst.setString(1, email);
        pst.setString(2, hashingValue);
        pst.setString(3, first_name);
        pst.setString(4, last_name);
        pst.setString(5, s);
 
		int i = pst.executeUpdate();

        ResultSet keys = pst.getGeneratedKeys();
        int userId = -1;
        if(keys.next())
        {
            userId = keys.getInt(1);
        }

		System.out.println("Insertion result" + i + " userId:" + userId);
        return userId;
	}

    // dob, gender, pfp
    public void updateUserInfo(int userId, String dob, String gender) throws SQLException
    {
        String updateQuery = "UPDATE user SET dob = ?, gender = ? WHERE id = ?";
        PreparedStatement ust = dbConn.prepareStatement(updateQuery);
        ust.setString(1, dob);
        ust.setString(2, gender);
        ust.setInt(3, userId);
        ust.executeUpdate();
    }

    public void updateUserImage(int userId, String pfp) throws SQLException
    {
        String updateQuery = "UPDATE user SET profile_picture = ? WHERE id = ?";
        PreparedStatement ust = dbConn.prepareStatement(updateQuery);
        ust.setString(1, pfp);
        ust.setInt(2, userId);
        ust.executeUpdate();
    }

    // country, street, town, state
    public void updateUserLocation(int userId, String country, String street, String town, String state) throws SQLException
    {
        String check = "SELECT * FROM location WHERE user_id = ?";
        PreparedStatement cst = dbConn.prepareStatement(check);
        cst.setInt(1, userId);
        ResultSet cr = cst.executeQuery();

        if(cr.next())
        {
            // update table
            int locId = cr.getInt("id");
            String updateQuery = "UPDATE location SET country = ?, street = ?, town = ?, state = ? WHERE id = ?";
            PreparedStatement ust = dbConn.prepareStatement(updateQuery);
            ust.setString(1, country);
            ust.setString(2, street);
            ust.setString(3, town);
            ust.setString(4, state);
            ust.setInt(5, locId);
            ust.executeUpdate();
        }
        else
        {
            // No location record, insert new one
            String insertQuery = "INSERT INTO location (user_id, country, street, town, state) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ist = dbConn.prepareStatement(insertQuery);
            ist.setInt(1, userId);
            ist.setString(2, country);
            ist.setString(3, street);
            ist.setString(4, town);
            ist.setString(5, state);
            ist.executeUpdate();
        }
    }

    // fos, degree, school
    public void updateUserEducation(int userId, String fieldOfStudy, String degree, String school) throws SQLException
    {
        String check = "SELECT * FROM education WHERE user_id = ?";
        PreparedStatement cst = dbConn.prepareStatement(check);
        cst.setInt(1, userId);
        ResultSet cr = cst.executeQuery();

        if(cr.next())
        {
            // update table
            int eduId = cr.getInt("id");
            String updateQuery = "UPDATE education SET field_of_study = ?, school = ?, degree = ? WHERE id = ?";
            PreparedStatement ust = dbConn.prepareStatement(updateQuery);
            ust.setString(1, fieldOfStudy);
            ust.setString(2, degree);
            ust.setString(3, school);
            ust.setInt(4, eduId);
            ust.executeUpdate();
        }
        else
        {
            // No location record, insert new one
            String insertQuery = "INSERT INTO education (user_id, field_of_study, school, degree) VALUES (?, ?, ?, ?)";
            PreparedStatement ist = dbConn.prepareStatement(insertQuery);
            ist.setInt(1, userId);
            ist.setString(2, fieldOfStudy);
            ist.setString(3, degree);
            ist.setString(4, school);
            ist.executeUpdate();
        }
    }

    public void getUserInfo(int userId, HttpSession session) throws SQLException
    {
        String tables = "user u JOIN location l ON u.id = l.user_id JOIN education e ON u.id = e.user_id";
        String fields = "u.id, u.role_id, u.dob, u.gender, u.profile_picture, l.country, l.street, l.town, l.state, e.field_of_study, e.degree, e.school";
        String whereClause = "u.id = ?";

        PreparedStatement ps = dbConn.prepareStatement("SELECT " + fields + " FROM " + tables + " WHERE " + whereClause);

        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        if(rs.next())
        {
            String dob = rs.getString("dob");
            String gender = rs.getString("gender");
            String pfp = rs.getString("profile_picture");
            String country = rs.getString("country");
            String street = rs.getString("street");
            String town = rs.getString("town");
            String state = rs.getString("state");
            String fieldOfStudy = rs.getString("field_of_study");
            String degree = rs.getString("degree");
            String school = rs.getString("school");

            // for the profile picture
            String directory = "D:/apache-tomcat-8.5.85/webapps/ROOT/socialnet/";
            String relativePath = pfp == null ? "socialnet/default_pfp.png" : pfp.substring(directory.length());
    
            session.setAttribute("dob", dob);
            session.setAttribute("gender", gender);
            session.setAttribute("pfp", relativePath);
            session.setAttribute("country", country);
            session.setAttribute("street", street);
            session.setAttribute("town", town);
            session.setAttribute("state", state);
            session.setAttribute("fieldOfStudy", fieldOfStudy);
            session.setAttribute("degree", degree);
            session.setAttribute("school", school);
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
		
		try
        {
			// Create a applicationDBManager object
			ApplicationDBManager manager = new ApplicationDBManager();
			System.out.println("Connecting...");
			System.out.println(manager.toString());
			
			// Call the listAllDepartment in order to retrieve all departments in the database
			//ResultSet res = manager.listAllRoles();
			
			// Iterate over the ResulSet containing all departments in the database, and count how many tuples were retrieved
			int count=0;
			// while (res.next())
            // {
			// 	count++;	
			// }
			// //Print the results count
			// System.out.println("Count:"  + count);
			
			// // Close the ResulSet
			// res.close();
			// // Close the database connection
			manager.close();
			
		} 
        catch(Exception e)
		{
			// Nothing to show!
			e.printStackTrace();
		}		
	}
}
