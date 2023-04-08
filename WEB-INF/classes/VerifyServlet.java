import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.*;

import ut.JAR.socialnet.*;

public class VerifyServlet extends HttpServlet 
{
    private static final long serialVersionUID = 2L;

    public void init() throws ServletException
    {
        // Initialize
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        // Forward to the signup page
        request.getRequestDispatcher("/socialnet/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {

        // Retrieve variables
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Create the authenticator object
        UserAuthenticator auth = new UserAuthenticator();

        try 
        {
            // Authenticate the user
            boolean res = auth.authenticate(email, password);

            // Verify the user
            if (res) 
            {
                // Set session attribute
                HttpSession session = request.getSession();
                session.setAttribute("email", email);
                response.sendRedirect("/socialnet/timeline.jsp");
            } 
            else 
            {
                // Close associated sessions
                HttpSession session = request.getSession();
                session.setAttribute("email", null);
                response.sendRedirect("/socialnet/login.jsp");
            }

            // Close database connection
            auth.close();
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
