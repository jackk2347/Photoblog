Photoblog (Spring Boot + JSP)
=============================

## Context / Overview
This project is a simple Photoblog web application built with:
- Java 17
- Spring Boot 3 (Spring MVC + Spring Security)
- JSP/JSTL views (server-side rendering)
- Spring Data JPA + H2 database
- Gradle build

The app allows users to register/login, upload photos (stored as BLOB in H2),
view photo list and photo details, and add comments. Admin users can manage
users and delete comments.


## How to Run

### Requirements
- JDK 17 installed

### Start the server
From the project root, run one of the following:

Windows (recommended):
  gradlew.bat bootRun

If using Gradle installed globally:
  gradle bootRun

### Open in browser
The application is configured to run on:
- Port: 8081
- Context path: /Photoblog

So the home page is:
  http://localhost:8081/Photoblog/

Notes:
- Visiting "/" redirects to "/photo/view".
- The login page is "/login".


## Default Accounts (from seed data)
Database seed data is in `src/main/resources/sql/data.sql`.

- Admin:
  - username: admin
  - password: adminpw
  - roles: ROLE_USER, ROLE_ADMIN

- Admin (role only):
  - username: admin1
  - password: adminpw
  - roles: ROLE_ADMIN

- Normal user:
  - username: user
  - password: userpw
  - roles: ROLE_USER

Password format: the project uses `{noop}` in seed data (plain text) for demo.


## Main Features
- Public:
  - View photo list
  - View a photo page (details + comments)
  - Register
  - Login

- Authenticated USER / ADMIN:
  - Upload one or multiple photos
  - View profile page
  - Add comments on photo pages
  - Logout

- ADMIN only:
  - User management (list users, create users, delete users)
  - Edit user profiles as admin
  - View comment history by user
  - Delete comments


## Security / Authorization (high level)
Authorization rules are configured in:
`src/main/java/hkmu/comps380f/config/SecurityConfig.java`

Examples:
- Admin-only routes include:
  - /user/createuser
  - /user/list
  - /user/profile/editprofile/admin/**
  - /deleteuser/**
  - /comment/**
  - /photo/deletecomment/**

- Upload is restricted to USER or ADMIN:
  - /photo/uploadphoto


## Database
H2 database is configured in:
`src/main/resources/application.properties`

- DB URL: jdbc:h2:./Data/myDB;AUTO_SERVER=TRUE
- Username: sa
- Password: password

Schema and seed data are always initialized at startup from:
- `src/main/resources/sql/schema.sql`
- `src/main/resources/sql/data.sql`

The DB files are stored under the `Data/` directory in the project root.


## Project Structure (repository layout)

.
|-- build.gradle
|-- settings.gradle
|-- gradlew
|-- gradlew.bat
|-- .gitignore
|-- README.txt
|-- Data.trace.db
|-- Data/
|   |-- myDB.trace.db
|
|-- gradle/
|   |-- wrapper/
|       |-- gradle-wrapper.properties
|
|-- src/
    |-- main/
    |   |-- java/
    |   |   |-- hkmu/comps380f/
    |   |       |-- CSAppApplication.java                 (Spring Boot entry)
    |   |       |-- config/
    |   |       |   |-- SecurityConfig.java
    |   |       |-- controller/
    |   |       |   |-- IndexController.java              ("/" + "/login")
    |   |       |   |-- PhotoBlogController.java          ("/photo/**")
    |   |       |   |-- UserManagementController.java     ("/user/**")
    |   |       |-- dao/
    |   |       |   |-- PhotoblogService.java
    |   |       |   |-- UserManagementService.java
    |   |       |   |-- UserService.java
    |   |       |   |-- CommentServcie.java
    |   |       |   |-- CommentRepository.java
    |   |       |   |-- PhotoRepository.java
    |   |       |   |-- UserRepository.java
    |   |       |   |-- UserRoleRepository.java
    |   |       |-- exception/
    |   |       |   |-- PhotoNotFound.java
    |   |       |   |-- CommentNotFound.java
    |   |       |   |-- UserNotFound.java
    |   |       |-- model/
    |   |       |   |-- Photo.java
    |   |       |   |-- Comment.java
    |   |       |   |-- Users.java
    |   |       |   |-- UserRole.java
    |   |       |-- view/
    |   |           |-- DownloadingView.java              (download photo bytes)
    |   |
    |   |-- resources/
    |   |   |-- application.properties
    |   |   |-- sql/
    |   |       |-- schema.sql
    |   |       |-- data.sql
    |   |
    |   |-- webapp/
    |       |-- WEB-INF/
    |           |-- jsp/
    |               |-- base.jspf
    |               |-- login.jsp
    |               |-- register.jsp
    |               |-- displayPhoto.jsp
    |               |-- photo.jsp
    |               |-- uploadphoto.jsp
    |               |-- profile.jsp
    |               |-- editprofile.jsp
    |               |-- editprofileforadmin.jsp
    |               |-- userlist.jsp
    |               |-- createuserbyadmin.jsp
    |               |-- commentHistory.jsp
    |               |-- error.jsp
    |
    |-- test/
        |-- java/
            |-- hkmu/comps380f/
                |-- CSAppApplicationTests.java


## Useful URLs (after server starts)
- Home (photo list): /Photoblog/
- Login: /Photoblog/login
- Register: /Photoblog/user/register
- Upload photo (requires login): /Photoblog/photo/uploadphoto
- User list (admin only): /Photoblog/user/list

