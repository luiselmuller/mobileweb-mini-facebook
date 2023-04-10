package ut.JAR.socialnet;

import java.util.Map;

public class User {
    private int id;
    private Map<String, String> userInfo;

    public User(int id, Map<String, String> userInfo)
    {
        this.id = id;
        this.userInfo = userInfo;
    }
    
    public int getId()
    {
        return id;
    }

    public String getInfo(String column)
    {        
        return userInfo.get(column);
    }

    public Map<String, String> getAllInfo()
    {
        return userInfo;
    }
    
}
