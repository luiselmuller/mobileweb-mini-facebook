import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.*;
import java.util.ArrayList;
import java.util.List;

import ut.JAR.socialnet.*;

public class UserManagementServlet extends HttpServlet
{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        AdminDBManager manager = new AdminDBManager();
        
        if("delete".equals(request.getParameter("action")) && request.getParameter("userUserId") != null)
        {
            int userId = Integer.parseInt(request.getParameter("userUserId"));

            try
            {
                manager.deleteUser(userId);
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            finally
            {
                manager.close();
            }
            response.sendRedirect("/socialnet/usermanager.jsp");
        }
        else if("refresh".equals(request.getParameter("action")))
        {
            try 
            {
                List<User> userList = manager.getAllUsers();
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
                RequestDispatcher dispatcher = request.getRequestDispatcher("/socialnet/usermanager.jsp");
                dispatcher.include(request, response);
                
            }
        }
        
    }

}