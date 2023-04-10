/**
 * Social Network Project # 1
 * Mobile Web Course CPEN410
 * 
 * @author Luisel Muller
 * @author Ian Colon
 *
 * This document contans the methods and classes for the VerifyServlet.
 * Handles Regular User login requests
 * 
 */

 import java.io.IOException;              //base class for exceptions thrown
 import javax.servlet.ServletException;   //for servlet exceptions
 import javax.servlet.http.*;             //for servlet classes running under HTTP
 import javax.servlet.*;                  //for creating servlets
 
 import ut.JAR.socialnet.*;  //location of the socialnet java files

 /**
 * This class is the VerifyServlet for User authentication.
 * This class is separated from the Administrator authentication.
 *  
 * uses methods: 
 *  doGet   : handles get HTTP requests from user login
 *  doPost  : handles post HTTP requests from user login
 *  
 */
public class VerifyServlet extends HttpServlet 
{
    private static final long serialVersionUID = 7L;

    /**
     * This funtion handles the HTTP GET requests made from login page
     * 
     * @param request : HTTPServletRequest object for request from client User login 
     * @param response : HTTPServletResponse object for response of server to client from User login
     * @throws ServletException : thrown when server errors may occur when requesting
     * @throws IOException : thrown when data transfer error may occur
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        // Forward to the signup page
        request.getRequestDispatcher("/socialnet/login.jsp").forward(request, response);
    }

    /**
     * This function handles doPost requests from login page.
     * It retrieves the email and password parameters
     * If session attributes and user authentication fails, redirects to timeline.jsp page
     * If an exception is thrown, redirects to login.jsp page
     * 
     * @param email : string variable to hold the email of user from login
     * @param password : string variable to hold the password of user from login
     * @param request : Request object to get email and password parameters send by the client
     * @param response : Response object send by the server to the client
     * @param auth : UserAuthentication object to authenticate user
     * @param manager : Object to handle database operations with the user information
     * @throws ServletException : if error occurrs while handling requests
     * @throws IOException : if error occurs while performing I/O requests/responses
     */
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
