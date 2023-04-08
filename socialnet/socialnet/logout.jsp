<%@ page import="javax.servlet.http.HttpSession" %>
<%
    // Invalidate the session
    session.invalidate();

    // Redirect to the home page
    response.sendRedirect("index.jsp");
%>