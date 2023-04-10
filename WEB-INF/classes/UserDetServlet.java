import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.*;
import java.util.ArrayList;

import ut.JAR.socialnet.*;

public class UserDetServlet extends HttpServlet
{
    private static final long serialVersionUID = 3L;

    public void init() throws ServletException
    {
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        request.getRequestDispatcher("/socialnet/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Get session attribute for user id
        HttpSession session = request.getSession();
        int userId = session.getAttribute("userUserId") == null ? Integer.parseInt(request.getParameter("userUserId")) : (int) session.getAttribute("userUserId");


        // Get form values
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");

        String firstName = request.getParameter("fname");
        String lastName = request.getParameter("lname");

        String country = request.getParameter("country");
        String street = request.getParameter("street");
        String town = request.getParameter("town");
        String state = request.getParameter("state");

        String fieldOfStudy = request.getParameter("fieldOfStudy");
        String degree = request.getParameter("degree");
        String school = request.getParameter("school");

        // Update user info
        try
        {
            ApplicationDBManager manager = new ApplicationDBManager();
            manager.updateUserInfo(userId, dob, gender, firstName, lastName);
            manager.updateUserLocation(userId, country, street, town, state);
            manager.updateUserEducation(userId, fieldOfStudy, degree, school);
            manager.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        // Set the fields in the form to the user data
        session.setAttribute("dob", dob);
        session.setAttribute("gender", gender);
        session.setAttribute("firstName", firstName);
        session.setAttribute("lastName", lastName);

        session.setAttribute("country", country);
        session.setAttribute("street", street);
        session.setAttribute("town", town);
        session.setAttribute("state", state);

        session.setAttribute("fieldOfStudy", fieldOfStudy);
        session.setAttribute("degree", degree);
        session.setAttribute("school", school);

        if("admin-modification".equals(request.getParameter("action")))
        {
            response.sendRedirect("/socialnet/usermanager.jsp");
        }
        else
        {
            response.sendRedirect("/socialnet/profile.jsp");
        }
        
    }




    





}