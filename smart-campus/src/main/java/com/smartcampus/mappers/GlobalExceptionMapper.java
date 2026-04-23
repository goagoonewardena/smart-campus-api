/**
 * Name - Gethmi Goonewardena
 * IIT ID - 20240971
 * UOW ID - w2120165
 */

package com.smartcampus.mappers;

import com.smartcampus.model.ApiError;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Global Exception Mapper — catches ALL exceptions thrown anywhere in the API.
 * Acts as a safety net to ensure no raw Java stack traces or default server
 * error pages are ever exposed to external API consumers.
 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

     // Logger instance to log unexpected server errors server-side only
    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionMapper.class.getName());

    /**
     * Converts any thrown exception into a clean JSON error response.
     * Ensures internal error details are never exposed to the client.
     * @param exception the exception that was thrown
     * @return a JSON Response containing status, error type, message and timestamp
     */
    @Override
    public Response toResponse(Throwable exception) {
        
        // Handle known JAX-RS exceptions (e.g. 404 Not Found, 400 Bad Request)
        if (exception instanceof WebApplicationException) {
            WebApplicationException webException = (WebApplicationException) exception;
            
            // Use a default message if the exception message is null or empty
            String message = webException.getMessage() == null || webException.getMessage().trim().isEmpty()
                    ? "Request failed."
                    : webException.getMessage();

            // Build a structured JSON error response using the original status code
            ApiError error = new ApiError(
                    webException.getResponse().getStatus(),
                    Response.Status.fromStatusCode(webException.getResponse().getStatus()).getReasonPhrase(),
                    message,
                    System.currentTimeMillis()
            );

            return Response.status(webException.getResponse().getStatus())
                    .type(MediaType.APPLICATION_JSON)
                    .entity(error)
                    .build();
        }

        // Handle all unexpected runtime errors (e.g. NullPointerException)
        // Log full error details server-side only — never expose to client
        LOGGER.log(Level.SEVERE, "Unhandled server error", exception);

        // Return a safe generic 500 response with no internal details
        ApiError error = new ApiError(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "Internal Server Error",
                "An unexpected error occurred. Please contact support.",
                System.currentTimeMillis()
        );

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }
}
