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
        int userId = (int) session.getAttribute("userId");

        // Get form values
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");

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
            manager.updateUserInfo(userId, dob, gender);
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

        session.setAttribute("country", country);
        session.setAttribute("street", street);
        session.setAttribute("town", town);
        session.setAttribute("state", state);

        session.setAttribute("fieldOfStudy", fieldOfStudy);
        session.setAttribute("degree", degree);
        session.setAttribute("school", school);

        response.sendRedirect("/socialnet/profile.jsp");
    }




    





}