-- socialnet.admin definition

CREATE TABLE `admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `dob` varchar(50) DEFAULT NULL,
  `gender` varchar(30) DEFAULT NULL,
  `profile_picture` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `admin_un` (`email`,`password`),
  KEY `admin_FK_2` (`role_id`),
  CONSTRAINT `admin_FK_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='List of administrators on the platform';

-- socialnet.education definition

CREATE TABLE `education` (
  `id` int NOT NULL AUTO_INCREMENT,
  `degree` varchar(50) DEFAULT NULL,
  `field_of_study` varchar(50) DEFAULT NULL,
  `school` varchar(50) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `education_FK_1` (`user_id`),
  CONSTRAINT `education_FK_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- socialnet.follow definition

CREATE TABLE `follow` (
  `id` int NOT NULL AUTO_INCREMENT,
  `follower_id` int NOT NULL,
  `following_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_follow_follower_id` (`follower_id`),
  UNIQUE KEY `unq_follow_following_id` (`following_id`),
  CONSTRAINT `follow_FK_2` FOREIGN KEY (`follower_id`) REFERENCES `user` (`id`),
  CONSTRAINT `follow_FK_3` FOREIGN KEY (`following_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- socialnet.location definition

CREATE TABLE `location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `country` varchar(50) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `location_FK_1` (`user_id`),
  CONSTRAINT `location_FK_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- socialnet.page definition

CREATE TABLE `page` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `page_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- socialnet.page_role definition

CREATE TABLE `page_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_access` tinyint(1) DEFAULT NULL,
  `role_id` int NOT NULL,
  `page_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_page_role_role_id` (`role_id`),
  UNIQUE KEY `unq_page_role_page_id` (`page_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- socialnet.`role` definition

CREATE TABLE `role` (
  `id` int NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- socialnet.`user` definition

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `dob` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0',
  `gender` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0',
  `password` varchar(255) DEFAULT NULL,
  `role_id` int DEFAULT '0',
  `profile_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_un` (`email`,`password`),
  KEY `user_FK_2` (`role_id`),
  CONSTRAINT `user_FK_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

