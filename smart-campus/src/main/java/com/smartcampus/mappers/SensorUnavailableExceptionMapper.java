/**
 * Name - Gethmi Goonewardena
 * IIT ID - 20240971
 * UOW ID - w2120165
 */

package com.smartcampus.mappers;

import com.smartcampus.exceptions.SensorUnavailableException;
import com.smartcampus.model.ApiError;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception Mapper for SensorUnavailableException.
 * Triggered when a client attempts to POST a new reading to a Sensor
 * that currently has a status of "MAINTENANCE".
 * Returns HTTP 403 Forbidden to indicate that the server understood
 * the request but refuses to process it due to the current state
 * of the sensor — a sensor under maintenance cannot accept new readings.
 */
@Provider
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {

    /**
     * Converts a SensorUnavailableException into a 403 JSON error response.
     * @param exception the exception thrown when posting a reading to a MAINTENANCE sensor
     * @return a JSON Response with HTTP status 403 and error details
     */
    @Override
    public Response toResponse(SensorUnavailableException exception) {
        
        // Build a structured JSON error response with 403 Forbidden status
        ApiError error = new ApiError(
                Response.Status.FORBIDDEN.getStatusCode(),
                "Forbidden",
                exception.getMessage(),
                System.currentTimeMillis()
        );

        // Return 403 Forbidden response with JSON body
        return Response.status(Response.Status.FORBIDDEN)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }
}
