/**
 * Social Network Project # 1
 * Mobile Web Course CPEN410
 * 
 * @author Luisel Muller
 * @author Ian Colon
 *
 * This class is the User class that represents a user of the social network with its 
 * unique identifier and a map of user information
 */
package ut.JAR.socialnet;

import java.util.Map;


public class User {
    private int id;
    private Map<String, String> userInfo;

    /**
    * Constructs a new User object with the given ID and user information.
    * @param id the unique identifier of the user
    * @param userInfo a map of user information where keys are column names and values are column values
    */
    public User(int id, Map<String, String> userInfo)
    {
        this.id = id;
        this.userInfo = userInfo;
    }
    
    /**
    * Returns the unique identifier of the user.
    * @return the unique identifier of the user
    */
    public int getId()
    {
        return id;
    }


    /**
    * Returns the value of the specified column in the user information map.
    * @param column the name of the column to retrieve the value for
    * @return the value of the specified column in the user information map, or null if the column does not exist
    */
    public String getInfo(String column)
    {        
        return userInfo.get(column);
    }

    /**
    * Returns a map of all user information where keys are column names and values are column values.
    * @return a map of all user information
    */
    public Map<String, String> getAllInfo()
    {
        return userInfo;
    }
    
}
