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


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/socialnet/timeline.jsp");
        dispatcher.forward(request, response);
    }

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