package ut.JAR.socialnet;

import java.sql.*;

import org.apache.commons.codec.*;

// TODO: Make separate for admin
public class UserAuthenticator
{
    private MiniSQLConnector dbConn;

    public UserAuthenticator()
    {
        dbConn = new MiniSQLConnector();

        dbConn.doConnection();
    }

    public boolean authenticate(String email, String password) throws SQLException
    {
        String tables = "user";

        String fields = "email, salt, password";

        String whereClause = "user.email = ?";

        PreparedStatement pst = dbConn.prepareStatement("SELECT " + fields + " FROM " + tables + " WHERE " + whereClause);

        pst.setString(1, email);

        ResultSet rs = pst.executeQuery();

        if(rs.next())
        {
            String salt = rs.getString("salt");
            String hash = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password + salt);

            if(hash.equals(rs.getString("password")))
            {
                return true;
            }
            else
            {
                // password error
                return false;
            }
        }
        else
        {
            return false; // not found
        }
    }

    public void close()
	{
		//Close the connection
		dbConn.closeConnection();
	}
}