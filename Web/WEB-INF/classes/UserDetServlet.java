/**
 * Social Network Project # 1
 * Mobile Web Course CPEN410
 * 
 * @author Luisel Muller
 * @author Ian Colon
 *
 * This document contans the methods and classes for the User profile details.
 * 
 */
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

    /**
     * Called by the servlet container to indicate to a servlet that the servlet is being placed into service.
     * @throws ServletException if there is a servlet-related problem.
     */
    public void init() throws ServletException
    {
        
    }

    /**
     * Handles HTTP GET requests. It forwards the request to the profile.jsp page.
     * 
     * @param request HttpServletRequest object that contains the request the client has made of the servlet
     * @param response HttpServletResponse object that contains the response the servlet sends to the client.
     * @throws ServletException  if there is a servlet-related problem
     * @throws IOException if there is an I/O problem
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        request.getRequestDispatcher("/socialnet/profile.jsp").forward(request, response);
    }

    /**
     * Updates the user information in the database based on the form values received in the request,
     * and sets the corresponding fields in the session attributes
     * 
     * @param request HttpServletRequest object that contains the request the client has made of the servlet
     * @param response HttpServletResponse object that contains the response the servlet sends to the client.
     * @throws ServletException if there is a servlet-related problem
     * @throws IOException if there is an I/O problem
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("USER DETAILS UPDATE REQUEST");
        // Get session attribute for user id
        HttpSession session = request.getSession();
        
        int userId = session.getAttribute("userUserId") == null ? Integer.parseInt(request.getParameter("userUserId")) : (int) session.getAttribute("userUserId");

        System.out.println("GETTING PARAMETERS");
        String firstName = request.getParameter("fname");
        String lastName = request.getParameter("lname");
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

        System.out.println("GOT PARAMETERS");
        // Update user info
        try
        {
            System.out.println("UPDATING INFO");
            ApplicationDBManager manager = new ApplicationDBManager();
            System.out.println("UPDATING USER INFO");
            manager.updateUserInfo(userId, dob, gender, firstName, lastName);
            System.out.println("UPDATING LOCATION");
            manager.updateUserLocation(userId, country, street, town, state);
            System.out.println("UPDATING EDUCATION");
            manager.updateUserEducation(userId, fieldOfStudy, degree, school);
            manager.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        System.out.println("SETTING SESSION ATTRIBUTES");
        // Set the fields in the form to the user data
        session.setAttribute("dob", dob);
        session.setAttribute("gender", gender);
        session.setAttribute("fname", firstName);
        session.setAttribute("lname", lastName);

        session.setAttribute("country", country);
        session.setAttribute("street", street);
        session.setAttribute("town", town);
        session.setAttribute("state", state);

        session.setAttribute("fieldOfStudy", fieldOfStudy);
        session.setAttribute("degree", degree);
        session.setAttribute("school", school);

        System.out.println("DONE");
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