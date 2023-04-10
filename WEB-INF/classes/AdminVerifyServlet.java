/**
 * Social Network Project # 1
 * Mobile Web Course CPEN410
 * 
 * @author Luisel Muller
 * @author Ian Colon
 *
 * This document contans the methods and classes for the Admin Verifiyng.
 * 
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.*;

import ut.JAR.socialnet.*;

public class AdminVerifyServlet extends HttpServlet 
{
    private static final long serialVersionUID = 6L;

    /**
     * Handles the GET request and forwards the request to the admin login page
     * 
     * @param request HttpServletRequest object that contains the request the client has made of the servlet
     * @param response HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException if there is an error handling the request
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        // Forward to the signin page
        request.getRequestDispatcher("/socialnet/adminlogin.jsp").forward(request, response);
    }

    /**
     * Handles the POST request and authenticates the admin and sets session attributes accordingly
     * 
     * @param request HttpServletRequest object that contains the request the client has made of the servlet
     * @param response HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException if there is an error handling the request
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {

        // Retrieve variables
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Create the authenticator object
        AdminAuthenticator adminAuth = new AdminAuthenticator();
        AdminDBManager adminManager = new AdminDBManager();

        try 
        {
            // Authenticate the user
            int[] res = adminAuth.authenticate(email, password);

            // Verify the user
            if (res[0] > 0) 
            {
                // Set session attribute
                HttpSession session = request.getSession();
                session.setAttribute("roleId", res[1]);
                session.setAttribute("userId", res[0]);
                adminManager.getAdminInfo((int) session.getAttribute("userId"), session);
                response.sendRedirect("/socialnet/adminpanel.jsp");
            } 
            else 
            {
                // Close associated sessions
                HttpSession session = request.getSession();
                session.setAttribute("email", null);
                response.sendRedirect("/socialnet/index.jsp");
            }

            // Close database connection
            adminAuth.close();
        } 
        catch (Exception e) 
        {
            HttpSession session = request.getSession();
            session.setAttribute("email", null);
            response.sendRedirect("/socialnet/index.jsp");
            e.printStackTrace();
        } 
    }
}
