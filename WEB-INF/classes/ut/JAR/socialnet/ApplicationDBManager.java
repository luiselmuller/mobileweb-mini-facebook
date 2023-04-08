package ut.JAR.socialnet;

// Package for managing ResultSet objects
import java.sql.*;

import java.security.SecureRandom;
import java.util.Base64;

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

    public int addUser(String email, String password, String first_name, String last_name) throws SQLException
	{
		String hashingValue;
        String query = "INSERT INTO user (email, password, first_name, last_name, salt) VALUES (?, ?, ?, ?, ?)";

        // Generate salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String s = Base64.getEncoder().encodeToString(salt);

		hashingValue = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password + s);

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

        PreparedStatement pst = dbConn.prepareStatement(query);

        pst.setString(1, email);
        pst.setString(2, hashingValue);
        pst.setString(3, first_name);
        pst.setString(4, last_name);
        pst.setString(5, s);
 
		int i = pst.executeUpdate();

		System.out.println("Insertion result" + i);
        return i;
	}

    // TODO: Add user detail methods
    // dob, gender, pfp
    public int addUserInfo(String dob, String gender, String pfp) throws SQLException
    {
        return 0;
    }

    // country, street, town, state
    public int addUserLocation(String country, String street, String town, String state) throws SQLException
    {
        return 0;
    }

    // fos, degree, school
    public int addUserEducation(String fieldOfStudy, String degree, String school) throws SQLException
    {
        return 0;
    }

    // TODO: Selection methods to get user information as well as search ALSO ADMIN THINGS

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
