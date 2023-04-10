/**
 * Social Network Project # 1
 * Mobile Web Course CPEN410
 * 
 * @author Luisel Muller
 * @author Ian Colon
 *
 * This document contans the methods and classes for the User search.
 * 
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.*;
import java.util.ArrayList;
import java.util.List;

import ut.JAR.socialnet.*;

public class UserSearchServlet extends HttpServlet
{

    /**
     * This method handles GET and POST requests related to user search.
     * @param request
     * @param response
     * @throws ServletException : if a servlet-specific error occurs.
     * @throws IOException :if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/socialnet/timeline.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles a POST request from a user search form and forwards the results to a JSP page for display.
     * 
     * @param request :HTTP servlet reques
     * @param response HTTP servlet response
     * @throws ServletException : if the servlet encounters an error while processing the request
     * @throws IOException :if an I/O error occurs while handling the request
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        ApplicationDBManager manager = new ApplicationDBManager();

        String category = request.getParameter("search-category");
        String parameter = request.getParameter("search");
        
        HttpSession session = request.getSession();
        session.setAttribute("category", category);

        try 
        {
            List<User> userList = manager.userSearch(category, parameter.replaceAll("[^a-zA-Z0-9.-]", "_"));
            request.setAttribute("userList", userList);
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        finally
        {
            
            manager.close();
            // Forward the request to the JSP page for display
            if("admin-search".equals(request.getParameter("action")))
            {
                request.getRequestDispatcher("/socialnet/adminpanel.jsp").forward(request, response);
            }
            else
            {
                request.getRequestDispatcher("/socialnet/timeline.jsp").forward(request, response);
            }
           
            
        }
        
    }
}