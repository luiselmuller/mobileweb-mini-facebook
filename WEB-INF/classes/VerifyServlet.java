import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.*;

import ut.JAR.socialnet.*;

public class VerifyServlet extends HttpServlet 
{
    private static final long serialVersionUID = 7L;
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
        ApplicationDBManager manager = new ApplicationDBManager();

        try 
        {
            // Authenticate the user
            int[] res = auth.authenticate(email, password);

            // Verify the user
            if (res[0] > 0) 
            {
                // Set session attribute
                HttpSession session = request.getSession();
                session.setAttribute("email", email);
                session.setAttribute("userUserId", res[0]);
                session.setAttribute("roleId", res[1]);
                manager.getUserInfo((int) session.getAttribute("userUserId"), session);
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
