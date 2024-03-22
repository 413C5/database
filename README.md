# Database Management System for User Authentication
 
This project provides a basic application for user authentication and management using a relational database. Users can log in by providing their username and password. After three unsuccessful login attempts, the user is locked out and can only be unlocked by an administrator through password update.

An administrator has privileges to insert new users (ensuring uniqueness), delete existing users, update user credentials, and unlock locked-out accounts. The database keeps track of user login attempts, lockout status, and administrator privileges.


# How to Run this Project
- Ensure you have the latest version of NetBeans installed.
- Download and set up XAMPP.
- Add the provided SQL script in the console to create the necessary database, tables, and initial user data.
- If the mysql-connector is not recognized, manually add it (provided with the project).
- When configuring the project, select Tomcat as the server.

You can access phpMyAdmin and the localhost through the following links:

http://localhost/phpmyadmin

http://localhost:8080/

# About this Project
This project was developed in Java to demonstrate the utilization of servlets for user authentication, specifically interacting with an SQL database, using JDK 8u391.