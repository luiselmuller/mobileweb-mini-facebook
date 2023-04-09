import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.*;
import java.util.ArrayList;

import ut.JAR.socialnet.*;

public class AdminDetServlet extends HttpServlet
{
    private static final long serialVersionUID = 13L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        request.getRequestDispatcher("/socialnet/adminprofile.jsp").forward(request, response);
    }

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