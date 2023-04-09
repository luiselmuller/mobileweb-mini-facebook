import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.*;

import ut.JAR.socialnet.*;

public class UserSearchServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        // Display the search form
        request.getRequestDispatcher("/socialnet/timeline.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    

    }
}
