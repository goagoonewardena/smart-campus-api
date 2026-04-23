# Smart Campus Sensor & Room Management API

**UOW_ID:** w2120165 | **IIT_ID:** 20240971

## Overview

This project is a RESTful API built using JAX-RS (Jersey 2.35) as part of the 5COSC022W Client-Server Architectures coursework at the University of Westminster. The API simulates a Smart Campus infrastructure, providing endpoints to manage Rooms and IoT Sensors deployed across campus buildings.

The system is built as a Java Web Application (.war) deployed on Apache Tomcat 9, allowing campus facilities managers and automated building systems to interact with campus data through a clean, resource-based REST interface.

## Core Resources

- **Rooms** — Physical campus rooms with capacity and assigned sensors
- **Sensors** — IoT devices (CO2, Temperature, Occupancy) deployed in rooms
- **Sensor Readings** — Historical measurement logs per sensor

## Tech Stack

| Technology | Details |
|-----------|---------|
| Language | Java 11 |
| Framework | JAX-RS 2.x (Jersey 2.35) — javax.ws.rs.* namespace |
| Server | Apache Tomcat 9.0.117 (external servlet container) |
| Packaging | WAR file |
| JSON | Jackson (via jersey-media-json-jackson) |
| Build | Maven 3.x |
| Storage | In-memory ConcurrentHashMap (no database) |
| IDE | Apache NetBeans |

> This project uses Jersey 2.35 + Tomcat 9.x to maintain compatibility with the javax.ws.rs.* namespace. Do not upgrade to Jersey 3.x or Tomcat 10.x without updating all imports to jakarta.*.

---

## How to Build and Run

### Prerequisites
- Java 11 or higher installed
- Apache Maven 3.6+ installed
- Apache Tomcat 9.x installed
- Apache NetBeans IDE

### Steps

**1. Clone the repository:**
```bash
git clone https://github.com/[YOUR-GITHUB-USERNAME]/smart-campus-api.git
cd smart-campus-api
```

**2. Build the project:**

Option A — Using NetBeans:
```
Right-click project → Clean and Build
Wait for BUILD SUCCESS in the output window
```

Option B — Using Maven terminal:
```bash
mvn clean package
```

This generates: `target/smart-campus-1.0.0.war`

**3. Start Apache Tomcat FIRST:**

Navigate to your Tomcat bin folder and run:
```
[TOMCAT_HOME]\bin\startup.bat
```

Wait until the Tomcat console shows:
```
Server startup in [XXXX] milliseconds
```

> Tomcat must be running BEFORE you run the project in NetBeans.

**4. Run the project in NetBeans:**
```
Right-click project → Run
NetBeans will deploy the WAR file to the already running Tomcat server
```

**5. Access the API:**
```
http://localhost:8081/smart-campus/api/v1/
```

> Important rebuild order: Always Stop Tomcat → Clean and Build → Start Tomcat → Run project. Never rebuild while Tomcat is running.

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/v1/ | Discovery endpoint — API metadata |
| GET | /api/v1/rooms | Get all rooms |
| POST | /api/v1/rooms | Create a new room |
| GET | /api/v1/rooms/{roomId} | Get room by ID |
| DELETE | /api/v1/rooms/{roomId} | Delete a room (blocked if sensors assigned) |
| GET | /api/v1/sensors | Get all sensors |
| GET | /api/v1/sensors?type={type} | Filter sensors by type |
| POST | /api/v1/sensors | Register a new sensor |
| GET | /api/v1/sensors/{sensorId} | Get sensor by ID |
| GET | /api/v1/sensors/{sensorId}/readings | Get reading history for sensor |
| POST | /api/v1/sensors/{sensorId}/readings | Add a new reading for sensor |

---

## Sample curl Commands

**1. Discovery Endpoint**
```bash
curl -X GET http://localhost:8081/smart-campus/api/v1/
```

**2. Create a Room**
```bash
curl -X POST http://localhost:8081/smart-campus/api/v1/rooms \
  -H "Content-Type: application/json" \
  -d '{"id":"LIB-301","name":"Library Quiet Study","capacity":50}'
```

**3. Get All Rooms**
```bash
curl -X GET http://localhost:8081/smart-campus/api/v1/rooms
```

**4. Register a Sensor**
```bash
curl -X POST http://localhost:8081/smart-campus/api/v1/sensors \
  -H "Content-Type: application/json" \
  -d '{"id":"TEMP-001","type":"Temperature","status":"ACTIVE","currentValue":22.5,"roomId":"LIB-301"}'
```

**5. Get All Sensors**
```bash
curl -X GET http://localhost:8081/smart-campus/api/v1/sensors
```

**6. Filter Sensors by Type**
```bash
curl -X GET "http://localhost:8081/smart-campus/api/v1/sensors?type=Temperature"
```

**7. Add a Sensor Reading**
```bash
curl -X POST http://localhost:8081/smart-campus/api/v1/sensors/TEMP-001/readings \
  -H "Content-Type: application/json" \
  -d '{"value":23.5}'
```

**8. Get Reading History**
```bash
curl -X GET http://localhost:8081/smart-campus/api/v1/sensors/TEMP-001/readings
```

**9. Delete an Empty Room**
```bash
curl -X DELETE http://localhost:8081/smart-campus/api/v1/rooms/LAB-102
```

**10. Attempt to Delete Occupied Room (409 Conflict)**
```bash
curl -X DELETE http://localhost:8081/smart-campus/api/v1/rooms/LIB-301
```

---

## Error Responses

| HTTP Status | Scenario |
|-------------|---------|
| 400 Bad Request | Missing required fields |
| 403 Forbidden | Posting reading to MAINTENANCE sensor |
| 404 Not Found | Resource does not exist |
| 409 Conflict | Deleting a room that still has sensors |
| 415 Unsupported Media Type | Wrong Content-Type sent |
| 422 Unprocessable Entity | Sensor registered with non-existent roomId |
| 500 Internal Server Error | Unexpected server error (no stack trace exposed) |

---

## Report — Question Answers

### Part 1 — Service Architecture & Setup

**Q1: Explain the default lifecycle of a JAX-RS Resource class. How does this impact in-memory data management?**

By default, JAX-RS creates a new instance of each Resource class for every incoming HTTP request, known as per-request scope. This means any instance variables declared inside the resource class are reset after each request and cannot retain data between calls. To safely share data across all requests, this project uses a dedicated CampusDataStore class with static ConcurrentHashMap fields. Since multiple HTTP requests can arrive simultaneously, ConcurrentHashMap is used instead of plain HashMap to prevent race conditions and data corruption when concurrent threads read and write at the same time.

**Q2: Why is HATEOAS considered a hallmark of advanced RESTful design? How does it benefit client developers?**

HATEOAS (Hypermedia As The Engine Of Application State) means API responses include links to related resources and available actions, not just data. This allows clients to navigate the entire API dynamically by following links embedded in responses rather than relying on hardcoded URLs or external documentation. If server-side paths change, clients that follow links dynamically do not break. This reduces tight coupling between client and server, makes the API self-documenting at runtime, and reduces errors from clients constructing incorrect URLs manually.

---

### Part 2 — Room Management

**Q3: What are the implications of returning only IDs versus full room objects in a list response?**

Returning only IDs minimises bandwidth and response size, which is useful when the client only needs to check existence or build a reference list. However, it forces the client to make additional requests to fetch details for each room individually, causing the N+1 problem where performance degrades as the dataset grows. Returning full room objects increases payload size but reduces round trips and client-side processing. For this campus management system where room objects are relatively small, returning full objects is the better default, with pagination added if the dataset grows significantly.

**Q4: Is the DELETE operation idempotent in your implementation?**

Yes, DELETE is idempotent in this implementation. Idempotency in REST means that making the same request multiple times produces the same server state as making it once. The first DELETE on a room removes it and returns HTTP 200. Any subsequent DELETE requests for the same room return HTTP 404 since the room no longer exists. While the response code differs between the first and subsequent calls, no additional unintended state change occurs after the first call. The server remains in the same final state regardless of how many times the DELETE is sent, which is consistent with the REST definition of idempotency.

---

### Part 3 — Sensor Operations & Filtering

**Q5: What happens technically if a client sends data in a format other than application/json to a POST endpoint annotated with @Consumes(MediaType.APPLICATION_JSON)?**

JAX-RS handles the media type mismatch automatically before the resource method is ever invoked. If a client sends a request with a Content-Type of text/plain or application/xml instead of application/json, the JAX-RS runtime immediately returns an HTTP 415 Unsupported Media Type response. The resource method code is never executed. This built-in framework behaviour protects the API from receiving data it cannot deserialise into the expected Java objects and requires no additional validation code in the resource class.

**Q6: Why is the @QueryParam approach superior to path-based filtering (e.g. /sensors/type/CO2)?**

Query parameters are semantically designed for filtering, searching, and sorting collections. Using @QueryParam makes the filter optional — clients can call GET /sensors to retrieve all sensors, or GET /sensors?type=CO2 to filter by type. A path-based approach like /sensors/type/CO2 incorrectly implies that type/CO2 is a unique resource identifier rather than a filter, which is misleading. It also makes the filter mandatory and creates a proliferation of nested paths for multiple filter combinations. Query parameters are composable, optional, and clearly communicate search intent to both human developers and API tooling.

---

### Part 4 — Sub-Resources

**Q7: Discuss the architectural benefits of the Sub-Resource Locator pattern.**

The Sub-Resource Locator pattern allows a parent resource class to delegate handling of nested paths to a dedicated child resource class. In this implementation, SensorResource delegates all /sensors/{id}/readings requests to a separate SensorReadingResource class. This separation of concerns keeps each class focused on a single responsibility — SensorResource manages sensor lifecycle while SensorReadingResource manages historical reading data. In large APIs where every nested path is defined in one massive controller class, the code becomes unmanageable and difficult to test. Sub-resource locators allow different developers to work on different resource classes independently, improving maintainability, readability, and scalability of the codebase.

---

### Part 5 — Error Handling & Logging

**Q8: Why is HTTP 422 more semantically accurate than 404 when a referenced resource is missing inside a valid JSON payload?**

HTTP 404 Not Found means the requested URL endpoint does not exist on the server. HTTP 422 Unprocessable Entity means the server understood the request and the URL is valid, but it cannot process the instruction because the data relationships inside the payload are logically invalid. When a client POSTs a new sensor with a roomId that does not exist, the URL /api/v1/sensors is perfectly valid and accessible — the problem is the foreign key reference inside the request body. Returning 404 would mislead the client into thinking the /sensors endpoint itself was not found. A 422 precisely communicates that the request was received and parsed correctly, but the referenced resource dependency could not be resolved.

**Q9: From a cybersecurity standpoint, explain the risks of exposing internal Java stack traces to external API consumers.**

Exposing stack traces to external clients is a serious security vulnerability. Stack traces reveal internal class names, method signatures, and line numbers, which allow an attacker to map the application's internal structure. They expose the names and versions of third-party libraries in use, enabling the attacker to look up known CVEs for those specific versions. They can reveal server file paths and package structures. They may expose database query details or connection information in database-related exceptions. They also reveal application logic and control flow, which can be exploited for targeted injection or path traversal attacks. The GlobalExceptionMapper in this project ensures all unexpected errors return a safe generic HTTP 500 message to the client, while logging full error details server-side only where they are accessible to developers but never exposed externally.

**Q10: Why is it better to use JAX-RS filters for cross-cutting concerns like logging rather than manually inserting Logger.info() in every resource method?**

Filters implement the cross-cutting concern principle — logging applies equally to every endpoint and has nothing to do with business logic. Manually inserting Logger.info() in every resource method violates the DRY (Don't Repeat Yourself) principle, clutters business logic with infrastructure concerns, and risks missing endpoints where logging is forgotten. If logging requirements change, every method would need to be updated individually. JAX-RS filters centralise this behaviour in one place, apply automatically to all current and future endpoints without any changes to resource classes, and can be enabled or disabled globally. This makes the codebase cleaner, easier to maintain, and less error-prone.

---