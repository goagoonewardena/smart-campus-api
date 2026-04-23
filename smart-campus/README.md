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
