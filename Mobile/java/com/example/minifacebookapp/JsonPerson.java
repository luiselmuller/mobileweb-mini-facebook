package com.example.minifacebookapp;

/****
 *  This class define an person object with the following attributes:
 *      id: identification number
 *      name: actual name
 *      userName: user name
 *      email: user's email address
 *
 *  This class implements the Serializable interface in order to be transferred between activities
 */


import org.json.JSONObject;

import java.io.Serializable;

public class JsonPerson implements Serializable {

    private String name;
    private String email;
    private String userName;
    private String id;


    /**
     *  Special constuctor using the actual values
     * @param id
     * @param name
     * @param email
     * @param userName
     */
    public JsonPerson(String id, String fname, String lname, String email, String userName)
    {
        this.id = id;
        this.name=name;
        this.email=email;
        this.userName=userName;
    }

    /**
     *  Special constructor using a Json file with the following syntax:
     *      {   "id"        ="value0",
     *          "userName"  ="value1",
     *          "name"      ="value2",
     *          "email"     ="value3"
     *      }
     * @param jsonFile
     */


    public JsonPerson(String jsonFile)
    {
        try{
            //Define a JSON object from the received data
            JSONObject jsonObj = new JSONObject(jsonFile);
            id = jsonObj.getString("id");
            name = jsonObj.getString("first_name");
            userName = jsonObj.getString("last_name");
            email = jsonObj.getString("email");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /******************************************************************8
     *  Observer methods
     *
     * @return
     */


    public String getName()
    {
        return  name;
    }

    public String getEmail( )
    {
        return email;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getId()
    {
        return id;
    }
}
