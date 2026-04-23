/**
 * Name - Gethmi Goonewardena
 * IIT ID - 20240971
 * UOW ID - w2120165
 */

package com.smartcampus.resources;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class DiscoveryResource {

    @GET
    public Response discover() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("apiName", "Smart Campus Sensor & Room Management API");
        payload.put("version", "v1");
        payload.put("adminContact", "admin@smartcampus.ac.uk");

        Map<String, String> links = new LinkedHashMap<>();
        links.put("rooms", "/api/v1/rooms");
        links.put("sensors", "/api/v1/sensors");
        payload.put("resources", links);

        return Response.ok(payload).build();
    }

    @GET
    @Path("/")
    public Response discoverWithSlash() {
        return discover();
    }
}
