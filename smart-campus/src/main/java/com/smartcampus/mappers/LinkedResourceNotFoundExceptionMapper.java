/**
 * Name - Gethmi Goonewardena
 * IIT ID - 20240971
 * UOW ID - w2120165
 */

package com.smartcampus.mappers;

import com.smartcampus.exceptions.LinkedResourceNotFoundException;
import com.smartcampus.model.ApiError;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception Mapper for LinkedResourceNotFoundException.
 * Triggered when a client attempts to create a Sensor with a roomId
 * that does not exist in the system.
 */
@Provider
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException> {

    /**
     * Converts a LinkedResourceNotFoundException into a 422 JSON error response.
     * @param exception the exception thrown when a referenced resource is not found
     * @return a JSON Response with HTTP status 422 and error details
     */
    @Override
    public Response toResponse(LinkedResourceNotFoundException exception) {
        
        // Build a structured JSON error response with 422 status
        ApiError error = new ApiError(
                422,
                "Unprocessable Entity",
                exception.getMessage(),
                System.currentTimeMillis()
        );

        // Return 422 response with JSON body
        return Response.status(422)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }
}
