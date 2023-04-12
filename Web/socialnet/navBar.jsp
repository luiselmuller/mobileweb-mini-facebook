<!-- Navigation for links that give info about the social media and login buttons -->
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
                    <a class="hover:cursor-pointer hover:text-primary active:text-primary-darker" href="/socialnet/timeline.jsp">
                        Search
                    </a>
                </li>
                <li>
                    <a class="hover:cursor-pointer hover:text-primary active:text-primary-darker" href="/socialnet/friends.jsp">
                        Friends
                    </a>
                </li>
                <li>
                    <div class="inline-block relative group z-50">
                        <button class="h-fit w-fit rounded-full inline-flex items-center">
                            <img src="${sessionScope.pfp}" alt="profile picture" class="h-16 w-16 object-contain rounded-full">
                        </button>
                        <ul class="absolute hidden text-gray-200 pt-1 group-hover:block rounded-md">
                            <li class="">
                                <a href="/socialnet/profile.jsp" class="bg-gray-900 hover:bg-gray-400 py-2 px-4 block whitespace-no-wrap" href="#">
                                    Edit Profile
                                </a>
                            </li>
                            <li class="">
                                <a href="/socialnet/logout.jsp" class="bg-gray-900 hover:bg-gray-400 py-2 px-4 block whitespace-no-wrap" href="#">
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
