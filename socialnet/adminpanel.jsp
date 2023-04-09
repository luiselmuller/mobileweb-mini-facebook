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


        <!-- Search -->
        <div class="w-full flex justify-center items-center mx-12">
            <form class="w-full" action="/search/users" method="post">
                <div class="flex">
                    <label for="search-dropdown" class="mb-2 text-sm font-medium text-gray-900 sr-only dark:text-white">Your Email</label>
                    <div class="inline-block relative group">
                        <label for="search-category" class="block text-sm font-medium text-gray-900 dark:text-white"></label>
                        <select id="search-category" name="search-category" class="bg-gray-50 border-t-2 border-l-2 border-b-2 border-gray-300 text-gray-900 text-sm rounded-l-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                            <option selected value="first_name">Category</option>
                            <option value="first_name">First Name</option>
                            <option value="last_name">Last Name</option>
                            <option value="gender">Gender</option>
                            <option value="age">Age</option>
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
                            dark:placeholder-gray-400 dark:text-white dark:focus:border-blue-500" placeholder="Search for users..." required>
                        <button type="submit" class="absolute top-0 right-0 p-2.5 text-sm font-medium text-white bg-blue-700 rounded-r-lg border border-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                            <svg aria-hidden="true" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path></svg>
                            <span class="sr-only">Search</span>
                        </button>
                    </div>
                </div>
            </form>

        </div>


    </body>

</html>
