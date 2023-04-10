/**
 * Social Network Project # 1
 * Mobile Web Course CPEN410
 * 
 * @author Luisel Muller
 * @author Ian Colon
 *
 * This document is the class of the servlet handling request of the Administrator (admin) details 
 * Handles Admin profile detail requests 
 * 
 */

import java.io.IOException;              //base class for exceptions thrown
import java.sql.SQLException;            //for sql error exceptions

import javax.servlet.ServletException;   //for servlet exceptions
import javax.servlet.http.*;             //for servlet classes running under HTTP
import javax.servlet.*;                  //for creating servlets
import java.util.ArrayList;              //for arrays

import ut.JAR.socialnet.*;               //location of the socialnet java files

public class AdminDetServlet extends HttpServlet
{
    private static final long serialVersionUID = 13L;

    /**
     * This function handles the GET requests from administrator details
     * 
     * @param request : HTTPServletRequest object for request from client adminprofile
     * @param response : HTTPServletResponse object for response of server to client from adminprofile
     * @throws ServletException : thrown when server errors may occur when requesting
     * @throws IOException : thrown when data transfer error may occur
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        request.getRequestDispatcher("/socialnet/adminprofile.jsp").forward(request, response);
    }

    /**
     * This function handles the POST requests from administrator to update details 
     * @param request : HTTPServletRequest object for request from client adminprofile
     * @param response : HTTPServletResponse object for response of server to client from adminprofile
     * @throws ServletException : thrown when server errors may occur when requesting
     * @throws IOException : thrown when data transfer error may occur
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Get session attribute for user id
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");

        // Get form values
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");

        // Update user info
        try
        {
            AdminDBManager manager = new AdminDBManager();
            manager.updateAdminInfo(userId, dob, gender);
            manager.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        // Set the fields in the form to the user data
        session.setAttribute("dob", dob);
        session.setAttribute("gender", gender);

        response.sendRedirect("/socialnet/adminprofile.jsp");
    }
}