/**
 * Name - Gethmi Goonewardena
 * IIT ID - 20240971
 * UOW ID - w2120165
 */

package com.smartcampus.resources;

import com.smartcampus.exceptions.RoomNotEmptyException;
import com.smartcampus.model.Room;
import com.smartcampus.store.CampusDataStore;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/rooms")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RoomResource {

    @GET
    public Response getAllRooms() {
        List<Room> rooms = new ArrayList<>(CampusDataStore.rooms().values());
        return Response.ok(rooms).build();
    }

    @POST
    public Response createRoom(Room room, @Context UriInfo uriInfo) {
        if (room == null || isBlank(room.getId()) || isBlank(room.getName())) {
            throw new WebApplicationException("Room id and name are required.", Response.Status.BAD_REQUEST);
        }
        if (CampusDataStore.rooms().containsKey(room.getId())) {
            throw new WebApplicationException("Room with id '" + room.getId() + "' already exists.", Response.Status.CONFLICT);
        }

        if (room.getSensorIds() == null) {
            room.setSensorIds(new ArrayList<>());
        }
        CampusDataStore.rooms().put(room.getId(), room);

        URI location = uriInfo.getAbsolutePathBuilder().path(room.getId()).build();
        return Response.created(location).entity(room).build();
    }

    @GET
    @Path("/{roomId}")
    public Response getRoomById(@PathParam("roomId") String roomId) {
        Room room = CampusDataStore.rooms().get(roomId);
        if (room == null) {
            throw new WebApplicationException("Room not found for id: " + roomId, Response.Status.NOT_FOUND);
        }
        return Response.ok(room).build();
    }
    @GET
    @Path("/crash")
    public Response crash() {
        String s = null;
        s.length(); // forces NullPointerException
        return Response.ok().build();
    }

    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Map<String, Room> rooms = CampusDataStore.rooms();
        Room room = rooms.get(roomId);
        if (room == null) {
            throw new WebApplicationException("Room not found for id: " + roomId, Response.Status.NOT_FOUND);
        }
        if (room.getSensorIds() != null && !room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Room '" + roomId + "' cannot be deleted because sensors are still assigned.");
        }
        rooms.remove(roomId);
        return Response.ok()
                .entity("{\"message\": \"Room " + roomId + " has been successfully deleted.\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
}

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
