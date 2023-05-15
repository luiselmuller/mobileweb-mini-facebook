/**
 * Social Network Project # 1
 * Mobile Web Course CPEN410
 * 
 * @author Luisel Muller
 * @author Ian Colon
 *
 * This document contans the methods and classes for the User Registration.
 * 
 */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.*;

import ut.JAR.socialnet.*;

public class RegisterServlet extends HttpServlet 
{
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @throws ServletException if there is an error during initialization.
     */
    public void init() throws ServletException
    {
        // Initialize
    }

    /**
     * Handles GET requests and forwards the request to the signup page
     * 
     * @param request HttpServletRequest object that contains the request the client has made of the servlet
     * @param response HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException  if there is an error while processing the GET request
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        // Forward to the signup page
        request.getRequestDispatcher("signup.jsp").forward(request, response);
    }

    /**
     * Handles POST requests and retrieves user registration information from the request parameters, 
     * creates an account in the database using the ApplicationDBManager object, 
     * and sends a redirect response to either the login page or the admin panel
     * 
     * @param request HttpServletRequest object that contains the request the client has made of the servlet
     * @param response HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException  if there is an error while processing the GET request
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {

        String msg = "";
        // Retrieve variables
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstname = request.getParameter("fname");
        String lastname = request.getParameter("lname");

        // Create the manager object
        ApplicationDBManager manager = new ApplicationDBManager();

        try 
        {
            // Add the user
            int res = manager.addUser(email, password, firstname, lastname);

            // Verify if the user was added
            if (res > 0) 
            {
                if(request.getHeader("User-Agent").contains("Android"))
                {
                    msg = "ok";
                    response.getWriter().write(msg);
                } 
                else 
                {
                    if("admin-registration".equals(request.getParameter("action")))
                    {
                        response.sendRedirect("/socialnet/adminpanel.jsp");
                    }
                    else
                    {
                        response.sendRedirect("/socialnet/login.jsp");
                    }
                }
                
            } 
            else 
            {
                // Close associated sessions
                HttpSession session = request.getSession();
                session.setAttribute("email", null);
                
                if(request.getHeader("User-Agent").contains("Android"))
                {
                    msg = "not";
                    response.getWriter().write(msg);
                } 
                else {
                    response.getWriter().println("Cannot add user");
                }
            }

            // Close database connection
            manager.close();
        } 
        catch (Exception e) 
        {
            HttpSession session = request.getSession();
            session.setAttribute("email", null);
            response.getWriter().println("User already exists");
            e.printStackTrace();
        } 
    }
}