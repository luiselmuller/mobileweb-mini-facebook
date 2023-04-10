/**
 * Social Network Project # 1
 * Mobile Web Course CPEN410
 * 
 * @author Luisel Muller
 * @author Ian Colon
 *
 * This document is the class for the handling of HTTP requests related to updating the 
 * administrator user profile pictures.
 * The image (png, jpg or gif) is saved to the server file system while the path of the file is 
 * stored into the database.
 * 
 */

 import java.io.ByteArrayOutputStream;            //for binary data into byte arrays
 import java.io.FileOutputStream;                 //class for output stream bytes to a file
 import java.io.IOException;                      //base class for exceptions thrown
 import java.io.InputStream;                      //for reading bytes from byte stream
 import java.sql.SQLException;                    //for sql error exceptions
 
 import javax.servlet.ServletException;           //for servlet exceptions
 import javax.servlet.http.*;                     //for servlet classes running under HTTP
 import javax.servlet.*;                          //for creating servlets
 import javax.servlet.http.Part;                  //for file uploads from web
 import java.nio.file.*;                          //for file system I/O 
 import javax.servlet.http.HttpServletResponse;   //for handling possible unsupported media types
 
 import ut.JAR.socialnet.*;                       //location of the socialnet java files

public class AdminImageServlet extends HttpServlet
{
    private static final long serialVersionUID = 12L;

    /**
     * Initializing servlet
     * @throws ServletException
     */
    public void init() throws ServletException
    {
        
    }

     /**
     * This function handles the GET requests from administrator details profile page
     * 
     * @param request : HTTPServletRequest object for request from client adminprofile
     * @param response : HTTPServletResponse object for response of server to client from adminprofile
     * @throws ServletException : thrown when server errors may occur when requesting
     * @throws IOException : thrown when data transfer error may occur
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        request.getRequestDispatcher("/socialnet/adminprofile.jsp").forward(request, response);
    }

    /**
     * This function handles HTTP POST requests for updating or saving administrator user profile pictures.
     *  
     * @param request : HTTPServletRequest object for request from client adminprofile
     * @param response : HTTPServletResponse object for response of server to client from adminprofile
     * @throws ServletException : thrown when server errors may occur when requesting
     * @throws IOException : thrown when data transfer error may occur
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        AdminDBManager manager = new AdminDBManager();

        // Get session attribute for user id
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");

        Part file = request.getPart("pfp");

        String name = getFileName(file);
        String type = file.getContentType();

        if(type.equals("image/png") || type.equals("image/jpeg") || type.equals("image/gif"))
        {
            InputStream fileContent = file.getInputStream();

            // 
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int r;
            byte[] data = new byte[16384];
            while((r = fileContent.read(data, 0, data.length)) != -1)
            {
                buffer.write(data, 0, r);
            }
            buffer.flush();
            byte[] bytes = buffer.toByteArray();

            // Saving the file to the profile_picture directory
            String path = "D:/apache-tomcat-8.5.85/webapps/ROOT/socialnet/profile_pictures/" + userId + sanitizeFileName(name);
            FileOutputStream output = new FileOutputStream(path);
            output.write(bytes);
            output.close();
            
            // other method of saving the file with a tmp file: Files.copy(fileContent, Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
            
            String directory = "D:/apache-tomcat-8.5.85/webapps/ROOT/socialnet/";
            String relativePath = path.substring(directory.length());
            session.setAttribute("admin-pfp", relativePath);

            // Update user info
            try
            {
                manager.updateAdminImage(userId, path);
                manager.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            try
            {
                manager.updateAdminImage(userId, "D:/apache-tomcat-8.5.85/webapps/ROOT/socialnet/");
                manager.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        response.sendRedirect("/socialnet/adminprofile.jsp");
    }

    /**
     * This function replaces any character on input filenames that is not a letter, number, dot or hyphen.
     * @param name : The input filename to be sanitized.
     * @return : The sanitized filename.
     * @throws IllegalArgumentException : if the input filename is null.
     */
    private String sanitizeFileName(String name)
    {
        if(name == null) 
        {
            throw new IllegalArgumentException("Filename cannot be null.");
        }
        // Any character that isn't a letter, number, dot or hyphen gets replaced with under-score
        return name.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    /**
     * This method extracts the filename from the content-disposition header of the given file part.
     * 
     * @param file : The file part from which to extract the filename
     * @return : The filename extracted from the file part, or null if the header is null.
     */
    // Getting the filename for the file part
    private String getFileName(final Part file) 
    {
        String header = file.getHeader("content-disposition");
        if(header == null) return null;

        for(String part : header.split(";"))
        {
            if(part.trim().startsWith("filename"))
            {
                return part.substring(part.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}

