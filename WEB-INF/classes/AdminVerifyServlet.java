import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.*;

import ut.JAR.socialnet.*;

public class AdminVerifyServlet extends HttpServlet 
{
    private static final long serialVersionUID = 6L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        // Forward to the signin page
        request.getRequestDispatcher("/socialnet/adminlogin.jsp").forward(request, response);
    }

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
