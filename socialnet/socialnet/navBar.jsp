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
                            <a class="hover:cursor-pointer hover:text-primary active:text-primary-darker" href="timeline.jsp">
                                Home
                            </a>
                        </li>
                        <li>
                            <a class="hover:cursor-pointer hover:text-primary active:text-primary-darker" href="#">
                                Friends
                            </a>
                        </li>
                        <li>
                            <a class="hover:cursor-pointer hover:text-primary active:text-primary-darker flex justify-center items-center gap-4" href="profile.jsp">
                                <img src="" alt="profile picture">
                                Profile
                            </a>
                        </li>
                        <li>
                            <a class="hover:cursor-pointer hover:text-primary active:text-primary-darker" href="logout.jsp">
                                Log Out
                            </a>
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
