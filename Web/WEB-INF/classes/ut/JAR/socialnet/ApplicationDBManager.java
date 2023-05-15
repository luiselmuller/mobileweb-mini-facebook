package ut.JAR.socialnet;

// Package for managing ResultSet objects
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
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

        return userId;
	}

    // dob, gender, pfp, name, lname
    public void updateUserInfo(int userId, String dob, String gender, String fname, String lname) throws SQLException
    {
        String updateQuery = "UPDATE user SET dob = ?, gender = ?, first_name = ?, last_name = ? WHERE id = ?";
        PreparedStatement ust = dbConn.prepareStatement(updateQuery);
        ust.setString(1, dob);
        ust.setString(2, gender);
        ust.setString(3, fname);
        ust.setString(4, lname);
        ust.setInt(5, userId);
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
            ust.setString(2, school);
            ust.setString(3, degree);
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
            ist.setString(3, school);
            ist.setString(4, degree);
            ist.executeUpdate();
        }
    }

    public void getUserInfo(int userId, HttpSession session) throws SQLException
    {
        String tables = "user u JOIN location l ON u.id = l.user_id JOIN education e ON u.id = e.user_id";
        String fields = "u.id, u.first_name, u.last_name, u.role_id, u.dob, u.gender, u.profile_picture, l.country, l.street, l.town, l.state, e.field_of_study, e.degree, e.school";
        String whereClause = "u.id = ?";


        PreparedStatement ps = dbConn.prepareStatement("SELECT " + fields + " FROM " + tables + " WHERE " + whereClause);

        ps.setInt(1, userId);


        ResultSet rs = ps.executeQuery();


        if(rs.next())
        {
            String fname = rs.getString("first_name");
            String lname = rs.getString("last_name");
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
            String relativePath = pfp == null ? "socialnet/profile_picture/default_pfp.png" : pfp.substring(directory.length());
    
            session.setAttribute("fname", fname);
            session.setAttribute("lname", lname);
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

// Get all users
public List<User> userSearch(String searchQuery) throws SQLException {
    List<User> userList = new ArrayList<>();
    
    String tables = "user u";
    String fields = "u.id, u.first_name, u.last_name, u.dob, u.gender, u.profile_picture, l.country, l.state, l.town, l.street, e.degree, e.field_of_study, e.school";
    String locationJoin = "LEFT JOIN location l ON u.id = l.user_id";
    String educationJoin = "LEFT JOIN education e ON u.id = e.user_id";
    
    String query = "SELECT " + fields + " FROM " + tables + " " + locationJoin + " " + educationJoin;
    
    int searchAge = -1;
    try {
        searchAge = Integer.parseInt(searchQuery);
    } catch (NumberFormatException e) {
        // search query is not a valid age, continue without searching by age
    }
    
    if (searchQuery.isEmpty() && searchAge < 0) {
        query += " WHERE 1";
    } else {
        boolean hasWhere = false;
        
        if (!searchQuery.isEmpty() && searchAge < 0) {
            query += " WHERE u.first_name LIKE ? OR u.last_name LIKE ? OR CONCAT(u.first_name, ' ', u.last_name) LIKE ?";
            query += " OR l.country LIKE ? OR l.state LIKE ? OR l.town LIKE ? OR l.street LIKE ?";
            query += " OR e.school LIKE ? OR e.degree LIKE ? OR e.field_of_study LIKE ?";
            query += " OR u.gender LIKE ?";
            hasWhere = true;
        }
        
        if (searchAge >= 0) {
            if (hasWhere) {
                query += " AND ";
            } else {
                query += " WHERE ";
            }
            query += " WHERE DATEDIFF(CURDATE(), STR_TO_DATE(u.dob, '%m/%d/%Y')/365.25) = ?";

        }
        System.out.println("SEARCH QUERY: " + query);
    }
    
    PreparedStatement st = dbConn.prepareStatement(query);
    int paramIndex = 1;
    
    if (!searchQuery.isEmpty() && searchAge < 0) {
        for (int i = 1; i <= 10; i++) {
            st.setString(paramIndex++, "%" + searchQuery + "%");
        }
        st.setString(paramIndex++, searchQuery);
    }
    
    ResultSet rs = st.executeQuery();
    while (rs.next()) {
        int id = rs.getInt("u.id");
        Map<String, String> userInfo = new HashMap<>();
        
        ResultSetMetaData rsm = rs.getMetaData();
        int columnCount = rsm.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
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
