<%
    // Get the roleID from the session attribute
    Integer roleId = (Integer) session.getAttribute("roleId");

    if (roleId != null && roleId.intValue() == 1) {
        // allow admin to access admin panel
    } else {
        response.sendRedirect("index.jsp");
    }

%>

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
                            <a class="hover:cursor-pointer hover:text-primary active:text-primary-darker" href="usermanagement.jsp">
                                Manage Users
                            </a>
                        </li>
                        <li>
                            <div class="inline-block relative group z-50">
                                <button class="h-fit w-fit rounded-full inline-flex items-center">
                                    <img src="${sessionScope.pfp}" alt="profile picture" class="h-16 w-16 object-contain rounded-full">
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


        <div class="w-full h-full flex justify-center items-center p-5">
            <!-- Form container -->
            <div class="w-full h-full bg-gray-800 rounded-xl sm:rounded-r-xl flex flex-col justify-center items-center gap-2 p-5">
                <div class="w-full h-fit flex justify-center items-center gap-5">
                    <h2 class="text-2xl my-12 text-center font-bold">Edit your personal information</h2>
                    <button type="button" class="hover:scale-105 active:scale-100 text-primary">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path></svg>
                    </button>
                </div>
                <form id="info-form" class="mb-12 flex flex-col justify-center items-center gap-5 w-full" action="/admin/info/update" method="post"> 
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
                                        dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Select date" value="<%= session.getAttribute("dob") %>">
                                </div>
                            </div>

                            <!-- Gender -->
                            <div class="w-full">
                                <label for="gender" class="block text-sm font-medium text-gray-900 dark:text-white">Select a Gender</label>
                                <select required id="gender" name="gender" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 
                                    block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                    <option selected value="<%= session.getAttribute("gender") %>"><%= session.getAttribute("gender") %></option>
                                    <option value="male">Male</option>
                                    <option value="female">Female</option>
                                    <option value="other">Other</option>
                                    <option value="prefer not to say">Prefer not to say</option>
                                </select>
                            </div>
                        </div>
                        <button type="submit" name="info-update" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full 
                        sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Save</button>
                    </div>
                </form>

                <!-- Bottom form (file input) -->
                <form id="pfp-form" action="/admin/profile-picture/update" method="post" enctype="multipart/form-data" class="flex flex-col items-center justify-center gap-5 sm:max-w-[665px] w-full max-w-[250px]">
                    <!-- Profile Picture -->
                    <div class="flex items-center justify-center w-full">
                        <label for="pfp" class="flex flex-col items-center justify-center w-full h-44 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 
                            dark:hover:bg-bray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
                            <div class="flex flex-col items-center justify-center pt-5 pb-6">
                                <svg aria-hidden="true" class="w-10 h-10 mb-3 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"></path></svg>
                                <p class="mb-2 text-sm text-gray-500 dark:text-gray-400 text-center"><span class="font-semibold">Click or drag and drop</span> to upload a profile picture</p>
                                <p class="text-xs text-gray-500 dark:text-gray-400">PNG or JPG</p>
                                <p class="text-xs text-gray-500 dark:text-gray-400">Current image: <%= session.getAttribute("pfp") %></p>
                            </div>
                            <input id="pfp" name="pfp" type="file" class="hidden" accept="image/gif, image/jpeg, image/png" value="<%= session.getAttribute("pfp") %>"/>
                        </label>
                    </div> 

                    <button type="submit" name="image-upload" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full 
                    sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Upload Picture</button>
                </form>
            </div>
        </div>
        </div>


    </body>

</html>
