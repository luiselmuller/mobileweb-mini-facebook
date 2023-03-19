<%--
    Created by Luisel Muller
    Date: 3/16/2023
    Time: 9:29 AM
    Social media project
--%>
<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1">
        <title><%=request.getServletContext().getServerInfo() %></title>
        <link href="favicon.ico" rel="icon" type="image/x-icon" />
        <link href="index.css" rel="stylesheet" type="text/css" />
        <!-- Tailwind Styling -->
        <script src="https://cdn.tailwindcss.com"></script>
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
        <!-- Navigation for links that give info about the social media and login buttons -->
        <nav class="h-36 w-full flex items-center justify-end">
            <!-- Logo -->
            <div class="mr-auto flex gap-4 items-center hover:cursor-default">
                <img src="assets/hive-icon.svg" alt="Hive Logo">
                <h2 class="uppercase text-4xl font-semibold tracking-[8px]">hive</h2>
            </div>

            <!-- Navigation Links -->
            <jsp:include page="components/navigationLinks.html" />

            <!-- Mobile Menu -->
            <div class="sm:hidden flex">
                <button type="button" class="h-[45px] w-[45px]">
                    <img src="assets/menu-hamburger-svgrepo-com.svg" alt="hamburger menu icon">
                    <!-- Change the value of displayMobileMenu on click -->
                </button>
            </div>
            <!-- TODO: Mobile Navigation -->
            <div class="hidden">
                <jsp:include page="components/navigationLinks.html" />
            </div>
            
        </nav>
        <!-- Hero section -->
        <section id="hero" class="pt-0 sm:pt-[75px] flex flex-col xl:flex-row h-[900px] px-5 flex">
            <!-- Hero Left Container -->
            <div class="w-full h-[75%] flex flex-col gap-24 items-start justify-center">
                <h1 class="text-7xl font-semibold">
                    <span class=" text-primary-darker"> 
                        Buzz 
                    </span> 
                        Together!
                </h1>
                <p class="text-2xl max-w-[620px]">
                    <span class="underline decoration-primary">
                        Hive
                    </span> 
                        is the ultimate 
                    <span class="underline decoration-primary">
                        social network platform
                    </span> 
                        for building connections, 
                        collaborating on ideas, and exploring 
                    <span class="underline decoration-primary">
                        new opportunities
                    </span>.
                </p>
                <!-- TODO: Make it scroll to the about section -->
                <button type="button" class="h-16 w-44 shadow-md bg-gradient-to-br from-primary to-primary-darker text-gray-900 font-semibold 
                    rounded-xl transition-all duration-200 ease-in-out hover:bg-gradient-to-tl hover:scale-105 active:scale-100">
                    Read More
                </button>
            </div>

            <!-- Hero Right Container -->
            <div class="h-fit w-full flex justify-end items-center hidden xl:flex">
                <img class="h-[75%] w-[75%]" src="assets/hero-bg-img.png" alt="Hero background"/>
            </div>
        </section>

        <!-- About Section -->
        <section id="about" class="px-5 h-fit w-full flex flex-col gap-10">
            <h2 class="text-7xl font-semibold"> <span class="underline decoration-primary-darker">About</span> Hive</h2>
            <p class="text-2xl">
                At Hive, we believe that building meaningful connections and collaborating with others is essential to success. 
                That's why we've created a powerful social network platform that connects like-minded individuals and empowers them 
                to work together, share ideas, and build strong communities. Whether you're an entrepreneur, a creative professional, 
                or simply looking to connect with others who share your passions, Hive has everything you need to thrive.
                <br><br>
                Our platform is designed to be intuitive, user-friendly, and packed with features that make it easy to connect, 
                network, and collaborate. From our advanced search tools that help you find the right connections to our messaging 
                and group collaboration features that facilitate communication and teamwork, Hive is the ultimate social network for 
                building your personal brand, growing your network, and achieving your goals.
                <br><br>
                At Hive, we believe in the power of community and collaboration. Join us today and discover a world of opportunities, 
                connections, and growth.
            </p>
        </section>

        <!-- Combined contact and faq section -->
        <section class="px-5 h-[560px] w-full flex justify-center items-center lg:flex-row flex-col gap-10 my-44">
            <!-- Contact Section -->
            <div id="contact" class="h-fit p-10 w-fit bg-primary rounded-xl bg-gradient-to-tr from-primary-darker to-primary lg:mt-0 mt-72">
                <div class="w-full h-full flex flex-col justify-evenly items-center">
                    <div class="w-full flex justify-center">
                        <h2 class="text-gray-900 text-4xl font-semibold">Contact Us</h2>
                    </div>
                    <div class="flex justify-center w-full text-gray-900 mt-10">
                        <form class="flex flex-col justify-center w-full items-center gap-5">
                            <div class="flex flex-col gap-2">
                                <div class="flex flex-col">
                                    <label class="ml-1 font-semibold capitalize" for="name">First name</label>
                                    <input class="rounded-lg pl-2 shadow-lg" type="text" id="name" name="firstname" placeholder="Your name..">
                                </div>

                                <div class="flex flex-col">
                                    <label class="ml-1 font-semibold capitalize" for="lastName">Last Name</label>
                                    <input class="rounded-lg pl-2 shadow-lg" type="text" id="lastName" name="lastname" placeholder="Your last name...">
                                </div>
                            </div>

                            <div class="flex flex-col w-full">
                                <label class="ml-1 font-semibold capitalize" for="subject">Subject</label>
                                <textarea class="max-h-[150px] min-h-[150px] rounded-lg pl-2 shadow-lg" id="subject" name="subject" placeholder="Reason for contacting..."></textarea>
                            </div>

                            <button type="submit" class="mt-5 w-44 rounded-xl h-10 bg-gray-900 text-primary font-semibold shadow-md hover:scale-105 active:scale-100 transition-all duration-200 ease-in-out">Submit</button>
                        </form>
                    </div>
                </div>
            </div>

            <!-- FAQ Section -->
            <div id="FAQ" class="flex flex-col items-center gap-2 justify-center w-full h-full lg:mt-0 mt-12 lg:mb-0 mb-32">
                <h2 class="text-4xl text-primary">FAQ</h2>

                <!-- FAQ Cards -->
                <div class="max-w-[900px] w-full h-full mt-4 flex flex-col items-center gap-2">
                    <div class="h-fit min-h-[50px] w-full rounded-xl bg-gray-800">

                    </div>
                    <div class="h-fit min-h-[50px] w-full rounded-xl bg-gray-800">

                    </div>
                    <div class="h-fit min-h-[50px] w-full rounded-xl bg-gray-800">

                    </div>
                </div>
            </div>
        </section>
        
        <footer class="w-full flex justify-center items-center text-sm text-gray-300 py-2">
            copyright
        </footer>
        
    </body>

</html>
