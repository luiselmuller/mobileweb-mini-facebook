package ut.JAR.socialnet;

import java.sql.*;

import org.apache.commons.codec.*;

// TODO: Make separate for admin
public class AdminAuthenticator
{
    private MiniSQLConnector dbConn;

    public AdminAuthenticator()
    {
        dbConn = new MiniSQLConnector();

        dbConn.doConnection();
    }

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

    public void close()
	{
		// Close the connection
		dbConn.closeConnection();
	}
}