<%--
    Created by Luisel Muller
    Date: 3/16/2023
    Time: 9:29 AM
    This is a 'homepage' used to navigate through all projects
--%>
<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
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
                    clifford: '#da373d',
                }
                }
            }
            }
        </script>
    </head>

    <body class="bg-gray-800 lg:px-64 md:px-24 px-5 text-gray-200 text-xl">
        <nav class="w-full h-16 flex items-center justify-start">
            <!-- <ul class="w-full h-full flex flex-row justify-center items-center gap-10">
                <li>
                    <button type="button" class="hover:text-blue-500">Tomcat</button>
                </li>
                <li>
                    <button type="button" class="hover:text-blue-500">Projects</button>
                </li>
                <li>
                    <button type="button" class="hover:text-blue-500">About</button>
                </li>
            </ul> -->
        </nav>
        <section class="flex flex-col gap-10">
            <div>
                <h2 class="text-4xl pb-5">Projects</h2>
                <ul class="flex gap-10 flex-wrap">
                    <li>
                        <a class="bg-blue-500 rounded-lg py-2 w-fit px-4 text-center font-semibold hover:bg-blue-400
                        transition-all duration-50 ease-in-out hover:cursor-pointer" href="/socialnet">
                            Social Network
                        </a>
                    </li>
                </ul>
            </div>

            <div>
                <h2 class="text-4xl pb-5">Class Work</h2>
                <ul class="flex gap-10 flex-wrap">
                    <li>
                        <a class="bg-blue-500 rounded-lg py-2 w-fit px-4 text-center font-semibold hover:bg-blue-400
                            transition-all duration-50 ease-in-out hover:cursor-pointer" href="/cpen410">
                            cpen410
                        </a>
                    </li>
                </ul>
            </div>
        </section>
    </body>
</html>
