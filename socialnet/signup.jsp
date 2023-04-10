<!DOCTYPE html>
<%
    if(session.getAttribute("roleId") != null && ((Integer) session.getAttribute("roleId")) == 1)
    {
        response.sendRedirect("/socialnet/adminpanel.jsp");
    }
    else if(session.getAttribute("email") != null)
    {
        
    }
%>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1">
        <title>Hive | Sign Up</title>
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

    <body class="bg-gray-900 text-gray-200 text-xl xl:px-38 lg:px-24 md:px-12 px-5 tracking-[1px] selection:bg-primary selection:text-gray-900
    flex justify-center items-center w-screen h-screen">
        <!-- Container -->
        <div class="bg-gray-800 w-full max-w-[1200px] h-full max-h-[700px] rounded-xl flex sm:flex-row flex-col my-24">
            <!-- Inner container -->
            <div class="p-5 w-full h-full flex flex-col justify-start items-center bg-gray-800 rounded-xl">
                <!-- Logo -->
                <div class="mr-auto flex gap-4 items-center hover:cursor-default mb-10">
                    <a class="rounded-xl transition-all duration-200 ease-in-out hover:scale-105 active:scale-100 
                        hover:cursor-pointer flex justify-center items-center gap-5" href="index.jsp">
                        <img src="assets/hive-icon.svg" alt="Hive Logo">
                        <h2 class="uppercase text-4xl font-semibold tracking-[8px]">hive</h2>
                    </a>
                </div>

                <!-- Text under logo -->
                <h2 class="font-bold text-2xl">Register your Account</h2>
                <h2 class="font-light text-sm">
                    Already have an account? 
                    <a href="login.jsp" class="text-primary">
                        Sign In
                    </a>
                </h2>

                <!-- Form container -->
                <div class="w-full h-full bg-gray-800 rounded-xl sm:rounded-r-xl flex flex-col justify-center items-center gap-2 p-5">
                    <form class="flex flex-col justify-center items-center gap-5 w-full" action="/register/signup" method="post">
                        <!-- Name -->
                        <div class="flex sm:flex-row flex-col w-full gap-2 justify-center items-center max-w-[550px]">
                            <!-- First Name -->
                            <div class="flex flex-col w-full">
                                <label for="fname" class="block text-sm font-medium text-gray-900 dark:text-white">First Name</label>
                                <input type="text" id="fname" name="fname" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 
                                    block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                                    placeholder="Lilly" required>
                            </div>

                            <!-- Last Name -->
                            <div class="flex flex-col w-full">
                                <label for="lname" class="block text-sm font-medium text-gray-900 dark:text-white">Last Name</label>
                                <input type="text" id="lname" name="lname" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 
                                    block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                                    placeholder="Brown" required>
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

                        <!-- Submit button -->
                        <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full 
                        sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Sign Up</button>
                    </form>
                </div>
            </div>

            <!-- Right container -->
            
        </div>
    </body>

</html>
