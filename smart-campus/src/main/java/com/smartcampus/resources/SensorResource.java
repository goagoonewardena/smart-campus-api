package com.smartcampus.resources;

import com.smartcampus.exceptions.LinkedResourceNotFoundException;
import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.store.CampusDataStore;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/sensors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SensorResource {

    @GET
    public Response getSensors(@QueryParam("type") String type) {
        List<Sensor> allSensors = new ArrayList<>(CampusDataStore.sensors().values());
        if (type == null || type.trim().isEmpty()) {
            return Response.ok(allSensors).build();
        }

        String normalizedType = type.trim().toLowerCase(Locale.ROOT);
        List<Sensor> filtered = allSensors.stream()
                .filter(sensor -> sensor.getType() != null
                && sensor.getType().trim().toLowerCase(Locale.ROOT).equals(normalizedType))
                .collect(Collectors.toList());

        return Response.ok(filtered).build();
    }

    @POST
    public Response createSensor(Sensor sensor, @Context UriInfo uriInfo) {
        if (sensor == null || isBlank(sensor.getId()) || isBlank(sensor.getType()) || isBlank(sensor.getRoomId())) {
            throw new WebApplicationException("Sensor id, type and roomId are required.", Response.Status.BAD_REQUEST);
        }
        if (CampusDataStore.sensors().containsKey(sensor.getId())) {
            throw new WebApplicationException("Sensor with id '" + sensor.getId() + "' already exists.", Response.Status.CONFLICT);
        }

        Room room = CampusDataStore.rooms().get(sensor.getRoomId());
        if (room == null) {
            throw new LinkedResourceNotFoundException("Cannot create sensor: roomId '" + sensor.getRoomId() + "' does not exist.");
        }

        if (isBlank(sensor.getStatus())) {
            sensor.setStatus("ACTIVE");
        }

        CampusDataStore.sensors().put(sensor.getId(), sensor);
        if (room.getSensorIds() == null) {
            room.setSensorIds(new ArrayList<>());
        }
        if (!room.getSensorIds().contains(sensor.getId())) {
            room.getSensorIds().add(sensor.getId());
        }

        URI location = uriInfo.getAbsolutePathBuilder().path(sensor.getId()).build();
        return Response.created(location).entity(sensor).build();
    }

    @GET
    @Path("/{sensorId}")
    public Response getSensorById(@PathParam("sensorId") String sensorId) {
        Sensor sensor = CampusDataStore.sensors().get(sensorId);
        if (sensor == null) {
            throw new WebApplicationException("Sensor not found for id: " + sensorId, Response.Status.NOT_FOUND);
        }
        return Response.ok(sensor).build();
    }

    @Path("/{sensorId}/readings")
    public SensorReadingResource readings(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
