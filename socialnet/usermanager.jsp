<%
    // Get the roleID from the session attribute
    Integer roleId = (Integer) session.getAttribute("roleId");

    if (roleId != null && roleId.intValue() == 1) 
    {
        // allow admin to access admin panel
    } else {
        response.sendRedirect("index.jsp");
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
        
        <nav class="h-36 w-full flex items-center justify-end">
            <!-- Logo -->
            <div class="mr-auto flex gap-4 items-center hover:cursor-default">
                <img src="assets/hive-icon.svg" alt="Hive Logo">
                <h2 class="uppercase text-4xl font-semibold tracking-[8px]">hive</h2>
            </div>

            <!-- Navigation Links -->
            <div class="sm:flex hidden">
                <!-- Navigation Links -->
                <div class="sm:flex hidden">
                    <ul class="flex justify-center items-center gap-10 flex-col sm:flex-row">
                        <li>
                            <a class="hover:cursor-pointer hover:text-primary active:text-primary-darker" href="adminpanel.jsp">
                                Search
                            </a>
                        </li>
                        <li>
                            <a class="hover:cursor-pointer hover:text-primary active:text-primary-darker" href="usermanager.jsp">
                                Manage Users
                            </a>
                        </li>
                        <li>
                            <div class="inline-block relative group z-50">
                                <button class="h-fit w-fit rounded-full inline-flex items-center">
                                    <img src='<%= session.getAttribute("admin-pfp") %>' alt="profile picture" class="h-16 w-16 object-contain rounded-full">
                                </button>
                                <ul class="absolute hidden text-gray-200 pt-1 group-hover:block rounded-md">
                                    <li class="">
                                        <a href="adminprofile.jsp" class="bg-gray-900 hover:bg-gray-400 py-2 px-4 block whitespace-no-wrap" href="#">
                                            Edit Profile
                                        </a>
                                    </li>
                                    <li class="">
                                        <a href="logout.jsp" class="bg-gray-900 hover:bg-gray-400 py-2 px-4 block whitespace-no-wrap" href="#">
                                            Logout
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        
                    </ul>
                </div>
            </div>
            

            <!-- Mobile Menu Toggle Button -->
            <div class="sm:hidden flex">
                <button type="button" class="h-[45px] w-[45px]" onclick="toggleMobileMenu()">
                    <img src="assets/menu-hamburger-svgrepo-com.svg" alt="hamburger menu icon">
                    <!-- Change the value of displayMobileMenu on click -->
                </button>
            </div>  
        </nav>


        <div class="w-full flex justify-center items-center flex-col">
            <div class="w-full flex justify-start m-2">
                <button onclick="location.href='/socialnet/usermanager?action=refresh'">
                    Refresh
                </button>
                
                <!-- Add -->
                <a data-modal-target="add-user-modal" data-modal-toggle="add-user-modal" class="ml-auto py-2 px-6 bg-blue-700 rounded-lg hover:bg-blue-500 hover:cursor-pointer">
                    Add User
                </a>
                <!-- Add User modal -->
                <div id="add-user-modal" tabindex="-1" aria-hidden="true" class="fixed top-0 left-0 right-0 z-50 hidden w-full p-4 overflow-x-hidden overflow-y-auto md:inset-0 h-[calc(100%-1rem)] md:h-full">
                    <div class="relative w-full h-full max-w-[1200px] md:h-auto">
                        <!-- Modal content -->
                        <div class="relative shadow w-full h-full bg-gray-800 rounded-xl sm:rounded-r-xl flex flex-col justify-center items-center gap-2 p-5">
                            <button type="button" class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-800 dark:hover:text-white" data-modal-hide="add-user-modal">
                                <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
                                <span class="sr-only">Close modal</span>
                            </button>
                            <!-- Form container -->
                            <form class="flex flex-col justify-center items-center gap-5 w-full" action="/register/signup?action=admin-registration" method="post">
                                <!-- Name -->
                                <div class="flex sm:flex-row flex-col w-full gap-2 justify-center items-center max-w-[550px]">
                                    <!-- First Name -->
                                    <div class="flex flex-col w-full">
                                        <label for="fname" class="block text-sm font-medium text-gray-900 dark:text-white">First Name</label>
                                        <input type="text" id="fname" name="fname" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 
                                            block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                                            placeholder="Default" required>
                                    </div>

                                    <!-- Last Name -->
                                    <div class="flex flex-col w-full">
                                        <label for="lname" class="block text-sm font-medium text-gray-900 dark:text-white">Last Name</label>
                                        <input type="text" id="lname" name="lname" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 
                                            block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                                            placeholder="Name" required>
                                    </div>
                                </div>
                                <!-- Email -->
                                <div class="flex flex-col max-w-[550px] w-full">
                                    <label for="email" class="block text-sm font-medium text-gray-900 dark:text-white">Email</label>
                                    <input type="email" id="email" name="email" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 
                                        block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                                        placeholder="example@email.com" required>
                                </div>
                                <!-- Password -->
                                <div class="flex flex-col max-w-[550px] w-full">
                                    <label for="password" class="block text-sm font-medium text-gray-900 dark:text-white">Password</label>
                                    <input type="password" id="password" name="password" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 
                                        block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                                        placeholder="password" required>
                                </div>

                                <div class="flex justify-center items-center gap-4 mt-8">
                                    <!-- Submit button -->
                                    <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full 
                                    sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Add User</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
            <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                <tr>
                    <th class="px-6 py-3">Picture</th>
                    <th class="px-6 py-3">ID</th>
                    <th class="px-6 py-3">First Name</th>
                    <th class="px-6 py-3">Last Name</th>
                    <th class="px-6 py-3">Email</th>
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
                                <td class="px-6 py-4"><%= user.getInfo("email") %></td>
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
                                    <button data-modal-target="auth-modal<%= user.getId() %>" data-modal-toggle="auth-modal<%= user.getId() %>" class="text-blue-500 hover:text-blue-300 underline hover:cursor-pointer">
                                        Edit
                                    </button>
                                    <button class="text-red-500 hover:text-red-300 underline hover:cursor-pointer"  onclick="location.href='/socialnet/usermanager/delete-user?action=delete&userUserId=<%=user.getId()%>'">
                                        Delete
                                    </button>
                                </td>

                                <!-- Modify User modal -->
                                <div id="auth-modal<%= user.getId() %>" tabindex="-1" aria-hidden="true" class="fixed top-0 left-0 right-0 z-50 hidden w-full p-4 overflow-x-hidden overflow-y-auto md:inset-0 h-[calc(100%-1rem)] md:h-full">
                                    <div class="relative w-full h-full max-w-[1200px] md:h-auto">
                                        <!-- Modal content -->
                                        <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                                            <button type="button" class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-800 dark:hover:text-white" data-modal-hide="auth-modal<%= user.getId() %>">
                                                <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
                                                <span class="sr-only">Close modal</span>
                                            </button>

                                            <div class="w-full h-full rounded-xl sm:rounded-r-xl flex flex-col justify-center items-center gap-2 p-5">
                                                <div class="w-full h-fit text-center">
                                                    <h2 class="text-2xl mt-16 text-center font-bold"><span class="text-primary">Edit User Information: </span> <%= user.getInfo("first_name") %> <%= user.getInfo("last_name") %></h2>
                                                </div>
                                                <form id="info-form" class="mb-12 flex flex-col justify-center items-center gap-5 w-full" action="/user/details/admin-update?action=admin-modification&userUserId=<%=user.getId()%>" method="post"> 
                                                    <div class="flex justify-center items-center gap-5 sm:max-w-[655px] w-full max-w-[250px]">
                                                        <div class="w-full">
                                                            <label for="fname" class="block text-sm font-medium text-gray-900 dark:text-white">First Name</label>
                                                            <input required type="text" id="fname" name="fname" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 
                                                                block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                                                                placeholder="Default" value="<%= user.getInfo("first_name") %>">
                                                        </div>
                                                        <div class="w-full">
                                                            <label for="lname" class="block text-sm font-medium text-gray-900 dark:text-white">Last name</label>
                                                            <input required type="text" id="lname" name="lname" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 
                                                                block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                                                                placeholder="Name" value="<%= user.getInfo("last_name") %>">
                                                        </div>
                            
                                                    </div>
                                                    <!-- Inputs -->
                                                    <div class="flex justify-center items-center gap-5 sm:flex-row flex-col">
                                                        <!-- Left col -->
                                                        <div class="flex justify-center items-start flex-col gap-2 w-full max-w-[250px]">
                                                            <!-- DOB -->
                                                            <div class="w-full">
                                                                <label for="dob" class="block text-sm font-medium text-gray-900 dark:text-white">Date of Birth</label>
                                                                <div class="relative max-w-sm">
                                                                    <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                                                                    <svg aria-hidden="true" class="w-5 h-5 text-gray-500 dark:text-gray-400" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M6 2a1 1 0 00-1 1v1H4a2 2 0 00-2 2v10a2 2 0 002 2h12a2 2 0 002-2V6a2 2 0 00-2-2h-1V3a1 1 0 10-2 0v1H7V3a1 1 0 00-1-1zm0 5a1 1 0 000 2h8a1 1 0 100-2H6z" clip-rule="evenodd"></path></svg>
                                                                    </div>
                                                                    <input required id="dob" name="dob" datepicker datepicker-autohide type="text" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm 
                                                                        rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 
                                                                        dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Select date" value="<%= user.getInfo("dob") %>">
                                                                </div>
                                                            </div>
                                
                                                            <!-- Gender -->
                                                            <div class="w-full">
                                                                <label for="gender" class="block text-sm font-medium text-gray-900 dark:text-white">Select a Gender</label>
                                                                <select required id="gender" name="gender" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 
                                                                    block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                                                    <option selected value="<%= user.getInfo("gender") %>"><%= user.getInfo("gender") %></option>
                                                                    <option value="male">Male</option>
                                                                    <option value="female">Female</option>
                                                                    <option value="other">Other</option>
                                                                    <option value="prefer not to say">Prefer not to say</option>
                                                                </select>
                                                            </div>
                                
                                                            <!-- Country -->
                                                            <div class="w-full">
                                                                <label for="country" class="block text-sm font-medium text-gray-900 dark:text-white">Select an Country</label>
                                                                <select required id="country" name="country" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full 
                                                                    p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                                                    <option selected value="<%= user.getInfo("country") %>"><%= user.getInfo("country") %></option>
                                                                    <option>select country</option>
                                                                    <option value="AF">Afghanistan</option>
                                                                    <option value="AX">Aland Islands</option>
                                                                    <option value="AL">Albania</option>
                                                                    <option value="DZ">Algeria</option>
                                                                    <option value="AS">American Samoa</option>
                                                                    <option value="AD">Andorra</option>
                                                                    <option value="AO">Angola</option>
                                                                    <option value="AI">Anguilla</option>
                                                                    <option value="AQ">Antarctica</option>
                                                                    <option value="AG">Antigua and Barbuda</option>
                                                                    <option value="AR">Argentina</option>
                                                                    <option value="AM">Armenia</option>
                                                                    <option value="AW">Aruba</option>
                                                                    <option value="AU">Australia</option>
                                                                    <option value="AT">Austria</option>
                                                                    <option value="AZ">Azerbaijan</option>
                                                                    <option value="BS">Bahamas</option>
                                                                    <option value="BH">Bahrain</option>
                                                                    <option value="BD">Bangladesh</option>
                                                                    <option value="BB">Barbados</option>
                                                                    <option value="BY">Belarus</option>
                                                                    <option value="BE">Belgium</option>
                                                                    <option value="BZ">Belize</option>
                                                                    <option value="BJ">Benin</option>
                                                                    <option value="BM">Bermuda</option>
                                                                    <option value="BT">Bhutan</option>
                                                                    <option value="BO">Bolivia</option>
                                                                    <option value="BQ">Bonaire, Sint Eustatius and Saba</option>
                                                                    <option value="BA">Bosnia and Herzegovina</option>
                                                                    <option value="BW">Botswana</option>
                                                                    <option value="BV">Bouvet Island</option>
                                                                    <option value="BR">Brazil</option>
                                                                    <option value="IO">British Indian Ocean Territory</option>
                                                                    <option value="BN">Brunei Darussalam</option>
                                                                    <option value="BG">Bulgaria</option>
                                                                    <option value="BF">Burkina Faso</option>
                                                                    <option value="BI">Burundi</option>
                                                                    <option value="KH">Cambodia</option>
                                                                    <option value="CM">Cameroon</option>
                                                                    <option value="CA">Canada</option>
                                                                    <option value="CV">Cape Verde</option>
                                                                    <option value="KY">Cayman Islands</option>
                                                                    <option value="CF">Central African Republic</option>
                                                                    <option value="TD">Chad</option>
                                                                    <option value="CL">Chile</option>
                                                                    <option value="CN">China</option>
                                                                    <option value="CX">Christmas Island</option>
                                                                    <option value="CC">Cocos (Keeling) Islands</option>
                                                                    <option value="CO">Colombia</option>
                                                                    <option value="KM">Comoros</option>
                                                                    <option value="CG">Congo</option>
                                                                    <option value="CD">Congo, Democratic Republic of the Congo</option>
                                                                    <option value="CK">Cook Islands</option>
                                                                    <option value="CR">Costa Rica</option>
                                                                    <option value="CI">Cote D'Ivoire</option>
                                                                    <option value="HR">Croatia</option>
                                                                    <option value="CU">Cuba</option>
                                                                    <option value="CW">Curacao</option>
                                                                    <option value="CY">Cyprus</option>
                                                                    <option value="CZ">Czech Republic</option>
                                                                    <option value="DK">Denmark</option>
                                                                    <option value="DJ">Djibouti</option>
                                                                    <option value="DM">Dominica</option>
                                                                    <option value="DO">Dominican Republic</option>
                                                                    <option value="EC">Ecuador</option>
                                                                    <option value="EG">Egypt</option>
                                                                    <option value="SV">El Salvador</option>
                                                                    <option value="GQ">Equatorial Guinea</option>
                                                                    <option value="ER">Eritrea</option>
                                                                    <option value="EE">Estonia</option>
                                                                    <option value="ET">Ethiopia</option>
                                                                    <option value="FK">Falkland Islands (Malvinas)</option>
                                                                    <option value="FO">Faroe Islands</option>
                                                                    <option value="FJ">Fiji</option>
                                                                    <option value="FI">Finland</option>
                                                                    <option value="FR">France</option>
                                                                    <option value="GF">French Guiana</option>
                                                                    <option value="PF">French Polynesia</option>
                                                                    <option value="TF">French Southern Territories</option>
                                                                    <option value="GA">Gabon</option>
                                                                    <option value="GM">Gambia</option>
                                                                    <option value="GE">Georgia</option>
                                                                    <option value="DE">Germany</option>
                                                                    <option value="GH">Ghana</option>
                                                                    <option value="GI">Gibraltar</option>
                                                                    <option value="GR">Greece</option>
                                                                    <option value="GL">Greenland</option>
                                                                    <option value="GD">Grenada</option>
                                                                    <option value="GP">Guadeloupe</option>
                                                                    <option value="GU">Guam</option>
                                                                    <option value="GT">Guatemala</option>
                                                                    <option value="GG">Guernsey</option>
                                                                    <option value="GN">Guinea</option>
                                                                    <option value="GW">Guinea-Bissau</option>
                                                                    <option value="GY">Guyana</option>
                                                                    <option value="HT">Haiti</option>
                                                                    <option value="HM">Heard Island and Mcdonald Islands</option>
                                                                    <option value="VA">Holy See (Vatican City State)</option>
                                                                    <option value="HN">Honduras</option>
                                                                    <option value="HK">Hong Kong</option>
                                                                    <option value="HU">Hungary</option>
                                                                    <option value="IS">Iceland</option>
                                                                    <option value="IN">India</option>
                                                                    <option value="ID">Indonesia</option>
                                                                    <option value="IR">Iran, Islamic Republic of</option>
                                                                    <option value="IQ">Iraq</option>
                                                                    <option value="IE">Ireland</option>
                                                                    <option value="IM">Isle of Man</option>
                                                                    <option value="IL">Israel</option>
                                                                    <option value="IT">Italy</option>
                                                                    <option value="JM">Jamaica</option>
                                                                    <option value="JP">Japan</option>
                                                                    <option value="JE">Jersey</option>
                                                                    <option value="JO">Jordan</option>
                                                                    <option value="KZ">Kazakhstan</option>
                                                                    <option value="KE">Kenya</option>
                                                                    <option value="KI">Kiribati</option>
                                                                    <option value="KP">Korea, Democratic People's Republic of</option>
                                                                    <option value="KR">Korea, Republic of</option>
                                                                    <option value="XK">Kosovo</option>
                                                                    <option value="KW">Kuwait</option>
                                                                    <option value="KG">Kyrgyzstan</option>
                                                                    <option value="LA">Lao People's Democratic Republic</option>
                                                                    <option value="LV">Latvia</option>
                                                                    <option value="LB">Lebanon</option>
                                                                    <option value="LS">Lesotho</option>
                                                                    <option value="LR">Liberia</option>
                                                                    <option value="LY">Libyan Arab Jamahiriya</option>
                                                                    <option value="LI">Liechtenstein</option>
                                                                    <option value="LT">Lithuania</option>
                                                                    <option value="LU">Luxembourg</option>
                                                                    <option value="MO">Macao</option>
                                                                    <option value="MK">Macedonia, the Former Yugoslav Republic of</option>
                                                                    <option value="MG">Madagascar</option>
                                                                    <option value="MW">Malawi</option>
                                                                    <option value="MY">Malaysia</option>
                                                                    <option value="MV">Maldives</option>
                                                                    <option value="ML">Mali</option>
                                                                    <option value="MT">Malta</option>
                                                                    <option value="MH">Marshall Islands</option>
                                                                    <option value="MQ">Martinique</option>
                                                                    <option value="MR">Mauritania</option>
                                                                    <option value="MU">Mauritius</option>
                                                                    <option value="YT">Mayotte</option>
                                                                    <option value="MX">Mexico</option>
                                                                    <option value="FM">Micronesia, Federated States of</option>
                                                                    <option value="MD">Moldova, Republic of</option>
                                                                    <option value="MC">Monaco</option>
                                                                    <option value="MN">Mongolia</option>
                                                                    <option value="ME">Montenegro</option>
                                                                    <option value="MS">Montserrat</option>
                                                                    <option value="MA">Morocco</option>
                                                                    <option value="MZ">Mozambique</option>
                                                                    <option value="MM">Myanmar</option>
                                                                    <option value="NA">Namibia</option>
                                                                    <option value="NR">Nauru</option>
                                                                    <option value="NP">Nepal</option>
                                                                    <option value="NL">Netherlands</option>
                                                                    <option value="AN">Netherlands Antilles</option>
                                                                    <option value="NC">New Caledonia</option>
                                                                    <option value="NZ">New Zealand</option>
                                                                    <option value="NI">Nicaragua</option>
                                                                    <option value="NE">Niger</option>
                                                                    <option value="NG">Nigeria</option>
                                                                    <option value="NU">Niue</option>
                                                                    <option value="NF">Norfolk Island</option>
                                                                    <option value="MP">Northern Mariana Islands</option>
                                                                    <option value="NO">Norway</option>
                                                                    <option value="OM">Oman</option>
                                                                    <option value="PK">Pakistan</option>
                                                                    <option value="PW">Palau</option>
                                                                    <option value="PS">Palestinian Territory, Occupied</option>
                                                                    <option value="PA">Panama</option>
                                                                    <option value="PG">Papua New Guinea</option>
                                                                    <option value="PY">Paraguay</option>
                                                                    <option value="PE">Peru</option>
                                                                    <option value="PH">Philippines</option>
                                                                    <option value="PN">Pitcairn</option>
                                                                    <option value="PL">Poland</option>
                                                                    <option value="PT">Portugal</option>
                                                                    <option value="PR">Puerto Rico</option>
                                                                    <option value="QA">Qatar</option>
                                                                    <option value="RE">Reunion</option>
                                                                    <option value="RO">Romania</option>
                                                                    <option value="RU">Russian Federation</option>
                                                                    <option value="RW">Rwanda</option>
                                                                    <option value="BL">Saint Barthelemy</option>
                                                                    <option value="SH">Saint Helena</option>
                                                                    <option value="KN">Saint Kitts and Nevis</option>
                                                                    <option value="LC">Saint Lucia</option>
                                                                    <option value="MF">Saint Martin</option>
                                                                    <option value="PM">Saint Pierre and Miquelon</option>
                                                                    <option value="VC">Saint Vincent and the Grenadines</option>
                                                                    <option value="WS">Samoa</option>
                                                                    <option value="SM">San Marino</option>
                                                                    <option value="ST">Sao Tome and Principe</option>
                                                                    <option value="SA">Saudi Arabia</option>
                                                                    <option value="SN">Senegal</option>
                                                                    <option value="RS">Serbia</option>
                                                                    <option value="CS">Serbia and Montenegro</option>
                                                                    <option value="SC">Seychelles</option>
                                                                    <option value="SL">Sierra Leone</option>
                                                                    <option value="SG">Singapore</option>
                                                                    <option value="SX">Sint Maarten</option>
                                                                    <option value="SK">Slovakia</option>
                                                                    <option value="SI">Slovenia</option>
                                                                    <option value="SB">Solomon Islands</option>
                                                                    <option value="SO">Somalia</option>
                                                                    <option value="ZA">South Africa</option>
                                                                    <option value="GS">South Georgia and the South Sandwich Islands</option>
                                                                    <option value="SS">South Sudan</option>
                                                                    <option value="ES">Spain</option>
                                                                    <option value="LK">Sri Lanka</option>
                                                                    <option value="SD">Sudan</option>
                                                                    <option value="SR">Suriname</option>
                                                                    <option value="SJ">Svalbard and Jan Mayen</option>
                                                                    <option value="SZ">Swaziland</option>
                                                                    <option value="SE">Sweden</option>
                                                                    <option value="CH">Switzerland</option>
                                                                    <option value="SY">Syrian Arab Republic</option>
                                                                    <option value="TW">Taiwan, Province of China</option>
                                                                    <option value="TJ">Tajikistan</option>
                                                                    <option value="TZ">Tanzania, United Republic of</option>
                                                                    <option value="TH">Thailand</option>
                                                                    <option value="TL">Timor-Leste</option>
                                                                    <option value="TG">Togo</option>
                                                                    <option value="TK">Tokelau</option>
                                                                    <option value="TO">Tonga</option>
                                                                    <option value="TT">Trinidad and Tobago</option>
                                                                    <option value="TN">Tunisia</option>
                                                                    <option value="TR">Turkey</option>
                                                                    <option value="TM">Turkmenistan</option>
                                                                    <option value="TC">Turks and Caicos Islands</option>
                                                                    <option value="TV">Tuvalu</option>
                                                                    <option value="UG">Uganda</option>
                                                                    <option value="UA">Ukraine</option>
                                                                    <option value="AE">United Arab Emirates</option>
                                                                    <option value="GB">United Kingdom</option>
                                                                    <option value="US">United States</option>
                                                                    <option value="UM">United States Minor Outlying Islands</option>
                                                                    <option value="UY">Uruguay</option>
                                                                    <option value="UZ">Uzbekistan</option>
                                                                    <option value="VU">Vanuatu</option>
                                                                    <option value="VE">Venezuela</option>
                                                                    <option value="VN">Viet Nam</option>
                                                                    <option value="VG">Virgin Islands, British</option>
                                                                    <option value="VI">Virgin Islands, U.s.</option>
                                                                    <option value="WF">Wallis and Futuna</option>
                                                                    <option value="EH">Western Sahara</option>
                                                                    <option value="YE">Yemen</option>
                                                                    <option value="ZM">Zambia</option>
                                                                    <option value="ZW">Zimbabwe</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                
                                                        <!-- Middle col -->
                                                        <div class="flex justify-center items-center flex-col gap-2 w-full max-w-[250px]">
                                                            <!-- Street -->
                                                            <div class="w-full">
                                                                <label for="street" class="block text-sm font-medium text-gray-900 dark:text-white">Street</label>
                                                                <input required type="text" id="street" name="street" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 
                                                                    block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                                                                    placeholder="Street Name" value="<%= user.getInfo("street") %>">
                                                            </div>
                                
                                                            <!-- Town -->
                                                            <div class="w-full">
                                                                <label for="town" class="block text-sm font-medium text-gray-900 dark:text-white">Town</label>
                                                                <input required type="text" id="town" name="town" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 
                                                                    block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                                                                    placeholder="Town Name" value="<%= user.getInfo("town") %>">
                                                            </div>
                                
                                                            <!-- State -->
                                                            <div class="w-full">
                                                                <label for="state" class="block text-sm font-medium text-gray-900 dark:text-white">State</label>
                                                                <input required type="text" id="state" name="state" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 
                                                                    block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                                                                    placeholder="State Name" value="<%= user.getInfo("state") %>">
                                                            </div>
                                                        </div>
                                                        
                                                        <!-- Right col -->
                                                        <div class="flex justify-center items-center flex-col gap-2 w-full max-w-[250px]">
                                                            <!-- Field of Study -->
                                                            <div class="w-full">
                                                                <label for="fieldOfStudy" class="block text-sm font-medium text-gray-900 dark:text-white">Field of Study</label>
                                                                <input required type="text" id="fieldOfStudy" name="fieldOfStudy" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 
                                                                    block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                                                                    placeholder="Engineering" value="<%= user.getInfo("field_of_study") %>">
                                                            </div>
                                
                                                            <!-- Degree -->
                                                            <div class="w-full">
                                                                <label for="degree" class="block text-sm font-medium text-gray-900 dark:text-white">Select a Degree</label>
                                                                <select required id="degree" name="degree" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 
                                                                    block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                                                    >
                                                                    <option selected value="<%= user.getInfo("degree") %>"><%= user.getInfo("degree") %></option>
                                                                    <option value="associate">Associate Degree</option>
                                                                    <option value="bachelor's">Bachelor's Degree</option>
                                                                    <option value="graduate">Graduate Degree</option>
                                                                    <option value="professional">Professional Degree</option>
                                                                    <option value="doctorate">Doctorate Degree</option>
                                                                    <option value="joint">Joint Degree</option>
                                                                </select>
                                                            </div>
                                
                                                            <!-- School -->
                                                            <div class="w-full">
                                                                <label for="school" class="block text-sm font-medium text-gray-900 dark:text-white">School</label>
                                                                <input required type="text" id="school" name="school" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 
                                                                    block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                                                                    placeholder="School Name" value="<%= user.getInfo("school") %>">
                                                            </div>
                                                        </div>
                                                    </div>
                                
                                                    <button type="submit" name="info-update" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full 
                                                    sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Save</button>
                                                </form>
                                
                                                <!-- Bottom form (file input) -->
                                                <form id="pfp-form" action="/user/profile-picture/admin-update?action=admin-modification&userUserId=<%=user.getId()%>" method="post" enctype="multipart/form-data" class="flex flex-col items-center justify-center gap-5 sm:max-w-[665px] w-full max-w-[250px]">
                                                    <!-- Profile Picture -->
                                                    <div class="flex items-center justify-center w-full">
                                                        <label for="user-pfp-<%=user.getId()%>" class="flex flex-col items-center justify-center w-full h-44 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 
                                                            dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
                                                            <div class="flex flex-col items-center justify-center pt-5 pb-6">
                                                                <svg aria-hidden="true" class="w-10 h-10 mb-3 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"></path></svg>
                                                                <p class="mb-2 text-sm text-gray-500 dark:text-gray-400 text-center"><span class="font-semibold">Click or drag and drop</span> to upload a profile picture</p>
                                                                <p class="text-xs text-gray-500 dark:text-gray-400">PNG or JPG</p>
                                                                <p class="text-xs text-gray-500 dark:text-gray-400">Current image: <%= user.getInfo("profile_picture") %></p>
                                                            </div>
                                                            <input id="user-pfp-<%=user.getId()%>" name="user-pfp" type="file" class="hidden" accept="image/gif, image/jpeg, image/png" value="<%= session.getAttribute("pfp") %>"/>
                                                        </label>
                                                    </div> 
                                
                                                    <button type="submit" name="image-upload" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full 
                                                    sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Upload Picture</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div> 
                                
                            </tr>
                    <%   }
                    } else { %>
                            <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
                                <td class="px-6 py-4">No users found</td>
                            </tr>
                    <% } %>
                </tbody>
            </table>

        </div>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/flowbite/1.6.4/flowbite.min.js"></script>

    </body>

</html>
