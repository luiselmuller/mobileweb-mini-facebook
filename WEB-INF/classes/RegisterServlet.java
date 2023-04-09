import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.*;

import ut.JAR.socialnet.*;

public class RegisterServlet extends HttpServlet 
{
    private static final long serialVersionUID = 1L;

    public void init() throws ServletException
    {
        // Initialize
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        // Forward to the signup page
        request.getRequestDispatcher("signup.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {

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
                response.sendRedirect("/socialnet/login.jsp");
            } 
            else 
            {
                // Close associated sessions
                HttpSession session = request.getSession();
                session.setAttribute("email", null);
                response.getWriter().println("Cannot add user");
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