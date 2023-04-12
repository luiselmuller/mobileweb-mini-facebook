<%
    if(session.getAttribute("roleId") != null && ((Integer) session.getAttribute("roleId")) == 1)
    {
        response.sendRedirect("/socialnet/adminpanel.jsp");
    }
    if(session.getAttribute("email") == null)
    {
        response.sendRedirect("login.jsp");
    }
%>

<%@ page import="ut.JAR.socialnet.User" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1">
        <title>Hive | Timeline</title>
        <link href="favicon.ico" rel="icon" type="image/x-icon" />
        <link href="index.css" rel="stylesheet" type="text/css" />
        <!-- Tailwind Styling -->
        <script src="https://cdn.tailwindcss.com"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/flowbite/1.6.4/flowbite.min.css" rel="stylesheet" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/flowbite/1.6.4/datepicker.min.js"></script>
        <script>
            tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        primary: 'hsl(35.08,94.2%,72.94%)',
                        'primary-darker': 'hsl(26.2,100%,69.02%)',
                    },
                }
            }
            }
        </script>
    </head>

    <body class="bg-gray-900 text-gray-200 text-xl xl:px-38 lg:px-24 md:px-12 px-5 tracking-[1px] selection:bg-primary selection:text-gray-900">
        <%@ include file="navBar.jsp" %>

        <!-- Search -->
        <div class="w-full flex justify-center items-center">
            <form class="w-full" action="/search/users" method="post">
                <div class="flex">
                    <label for="search-dropdown" class="mb-2 text-sm font-medium text-gray-900 sr-only dark:text-white">Your Email</label>
                    <div class="inline-block relative group">
                        <label for="search-category" class="block text-sm font-medium text-gray-900 dark:text-white"></label>
                        <select id="search-category" name="search-category" class="bg-gray-50 border-t-2 border-l-2 border-b-2 border-gray-300 text-gray-900 text-sm rounded-l-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                            <option selected value='<%= session.getAttribute("category") == null ? "first_name" : session.getAttribute("category") %>'><%= session.getAttribute("category") %></option>
                            <option value="first_name">First Name</option>
                            <option value="last_name">Last Name</option>
                            <option value="gender">Gender</option>
                            <option value="country">Country</option>
                            <option value="street">Street</option>
                            <option value="town">Town</option>
                            <option value="state">State</option>
                            <option value="degree">Degree</option>
                            <option value="school">school</option>
                            <option value="field_of_study">Field of Study</option>
                            
                        </select>
                    </div>
                    <div class="relative w-full">
                        <input type="search" id="search" name="search" class="block p-2.5 w-full z-20 text-sm text-gray-900 bg-gray-50 rounded-r-lg border-2 border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-l-gray-700  dark:border-gray-600
                            dark:placeholder-gray-400 dark:text-white dark:focus:border-blue-500" placeholder="Search for users...">
                        <button type="submit" class="absolute top-0 right-0 p-2.5 text-sm font-medium text-white bg-blue-700 rounded-r-lg border border-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                            <svg aria-hidden="true" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path></svg>
                            <span class="sr-only">Search</span>
                        </button>
                    </div>
                </div>
            </form>
        </div>
        <table class="mt-10 w-full text-sm text-left text-gray-500 dark:text-gray-400">
            <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                <tr>
                    <th class="px-6 py-3">Picture</th>
                    <th class="px-6 py-3">ID</th>
                    <th class="px-6 py-3">First Name</th>
                    <th class="px-6 py-3">Last Name</th>
                    <th class="px-6 py-3">Date of Birth</th>
                    <th class="px-6 py-3">Gender</th>
                    <th class="px-6 py-3">Country</th>
                    <th class="px-6 py-3">State</th>
                    <th class="px-6 py-3">Town</th>
                    <th class="px-6 py-3">Street</th>
                    <th class="px-6 py-3">Degree</th>
                    <th class="px-6 py-3">Field of Study</th>
                    <th class="px-6 py-3">School</th>
                    <th class="px-6 py-3 text-center">Action</th>

                </tr>
            </thead>
            <tbody>
            <% List<User> userList = (List<User>) request.getAttribute("userList");
                if (request.getAttribute("userList") != null) {
                    for (User user : (List<User>) request.getAttribute("userList")) { %>
                        <tr id="user-<%= user.getId() %>" class="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
                            <td class="px-6 py-4">
                                <img id="user-<%= user.getId() %>" src='<%= user.getInfo("profile_picture") == null ? "" : user.getInfo("profile_picture").replace("D:/apache-tomcat-8.5.85/webapps/ROOT", "") %>' alt="profile picture"
                                    class="rounded-full h-12 w-12">
                            </td>
                            <td class="px-6 py-4"><%= user.getId() %></td>
                            <td class="px-6 py-4"><%= user.getInfo("first_name") %></td>
                            <td class="px-6 py-4"><%= user.getInfo("last_name") %></td>
                            <td class="px-6 py-4"><%= user.getInfo("dob") %></td>
                            <td class="px-6 py-4"><%= user.getInfo("gender") %></td>
                            <td class="px-6 py-4"><%= user.getInfo("country") %></td>
                            <td class="px-6 py-4"><%= user.getInfo("state") %></td>
                            <td class="px-6 py-4"><%= user.getInfo("town") %></td>
                            <td class="px-6 py-4"><%= user.getInfo("street") %></td>
                            <td class="px-6 py-4"><%= user.getInfo("school") %></td>
                            <td class="px-6 py-4"><%= user.getInfo("field_of_study") %></td>
                            <td class="px-6 py-4"><%= user.getInfo("degree") %></td>
                            <td class="px-6 py-4 flex justify-center gap-5 items-center">
                                <button class="text-blue-500 hover:text-blue-300 underline hover:cursor-pointer" onclick="">
                                    Follow
                                </button>
                                <button class="text-red-500 hover:text-red-300 underline hover:cursor-pointer" onclick="">
                                    Unfollow
                                </button>
                            </td>
                            
                        </tr>
                <%   }
                } else { %>
                        <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
                            <td class="px-6 py-4">No users found</td>
                        </tr>
                <% } %>
            </tbody>
        </table>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/flowbite/1.6.4/flowbite.min.js"></script>
    </body>

</html>
