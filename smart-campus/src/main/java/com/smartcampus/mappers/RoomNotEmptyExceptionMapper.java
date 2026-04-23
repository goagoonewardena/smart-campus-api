/**
 * Name - Gethmi Goonewardena
 * IIT ID - 20240971
 * UOW ID - w2120165
 */

package com.smartcampus.mappers;

import com.smartcampus.exceptions.RoomNotEmptyException;
import com.smartcampus.model.ApiError;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception Mapper for RoomNotEmptyException.
 * Triggered when a client attempts to DELETE a Room that still has
 * active Sensors assigned to it.
 * Returns HTTP 409 Conflict to indicate a business logic constraint
 * violation — a room cannot be deleted while sensors are still linked
 * to it, in order to prevent data orphans in the system.
 */
@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {

    /**
     * Converts a RoomNotEmptyException into a 409 JSON error response.
     * @param exception the exception thrown when deleting an occupied room
     * @return a JSON Response with HTTP status 409 and error details
     */
    @Override
    public Response toResponse(RoomNotEmptyException exception) {
        
        // Build a structured JSON error response with 409 Conflict status
        ApiError error = new ApiError(
                Response.Status.CONFLICT.getStatusCode(),
                "Conflict",
                exception.getMessage(),
                System.currentTimeMillis()
        );

        // Return 409 Conflict response with JSON body
        return Response.status(Response.Status.CONFLICT)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }
}
