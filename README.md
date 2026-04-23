UOW ID - w2120165, IIT ID - 20240971

Smart Campus Sensor & Room Management API

Overview

This project is a RESTful API built using JAX-RS (Jersey) as part of the 5COSC022W Client-Server Architectures coursework at the University of Westminster. The API simulates a Smart Campus infrastructure, providing endpoints to manage Rooms and IoT Sensors deployed across campus buildings.

The system is built as a Java Web Application (.war) deployed on Apache Tomcat 9, allowing campus facilities managers and automated building systems to interact with campus data through a clean, resource-based REST interface.


Core Resources

Rooms — Physical campus rooms with capacity and assigned sensors
Sensors — IoT devices (CO2, Temperature, Occupancy) deployed in rooms
Sensor Readings — Historical measurement logs per sensor


Tech Stack

Language - Java 11
Framework - JAX-RS 2.x (Jersey 2.35) — javax.ws.rs.* namespace
Server - Apache Tomcat 9.0.117 (external servlet container)
Packaging - WAR file
JSON - Jackson (via jersey-media-json-jackson)
Build - Maven 3.x
Storage - In-memory ConcurrentHashMap (no database)
IDE - Apache NetBeans

Note that need to consider - This project uses Jersey 2.35 + Tomcat 9.x to maintain compatibility with the javax.ws.rs.* namespace. Do not upgrade to Jersey 3.x or Tomcat 10.x without updating all imports to jakarta.*.


How to Build and Run

Prerequisites

Java 11 or higher installed
Apache Maven 3.6+ installed
Apache Tomcat 9.x installed
Apache NetBeans IDE

Steps
1. Clone the repository:

Option 1 - First you have to click on team in netbeans then select Git after then select clone to clone from URL using Netbeans then add the github URL below:

https://github.com/goagoonewardena/smart-campus-api.git

After that you can select a specific destination folder to clone into and click Finish, So then you can the project.


Option 2 - You can also directly download my code as a zip file from github and unzip it and open in netbeans.


2. Build the project:

Option A — Using NetBeans:
Right-click project - Clean and Build
Wait for BUILD SUCCESS in the output window

3. Start Apache Tomcat FIRST:

The Apache Tomcat Should be downloaded from the official website under Binary Distributions - 64-bit Windows zip. After opening the project it should show under the projects area of netbeans and next to projects there is Services click it and there you could find Servers, right click on server and then add server (if you heven't add server as Tomcat), they ask to select a server then select Tomcat and then ask for the server location click browse then find the find the location of where the tomcat was downloaded in your computer, after finding the location of apache-tomcat-9.0.117-windows-x64, there should be a sub folder under this folder named something like apache-tomcat-9.0.117, then click on that sub folder and after that click Open, then if the window asks any username or password add any username or password if needed, then click finish. Then you could see the tomcat server located right under Servers in netbeans, right click on the tomcat server in netbeans and it should show start then click start, it would then show that the tomcat is running wait until it sucessfully finish running.


Note - Tomcat must be running BEFORE you run the project in NetBeans.


4. Run the project in NetBeans:

Right-click project - click Run
NetBeans will deploy the WAR file to the already running Tomcat server

Then after running it should direct to a web page with the expected output.

Note - Important rebuild order: Always Stop Tomcat → Clean and Build → Start Tomcat → Run project. Never rebuild while Tomcat is running.

