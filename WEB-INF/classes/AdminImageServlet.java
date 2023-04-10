import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.http.Part;
import java.nio.file.*;
import javax.servlet.http.HttpServletResponse;

import ut.JAR.socialnet.*;

public class AdminImageServlet extends HttpServlet
{
    private static final long serialVersionUID = 12L;

    public void init() throws ServletException
    {
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        request.getRequestDispatcher("/socialnet/adminprofile.jsp").forward(request, response);
    }

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

    private String sanitizeFileName(String name)
    {
        if(name == null) 
        {
            throw new IllegalArgumentException("Filename cannot be null.");
        }
        // Any character that isn't a letter, number, dot or hyphen gets replaced with under-score
        return name.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

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

