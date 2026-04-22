package com.smartcampus.mappers;

import com.smartcampus.model.ApiError;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionMapper.class.getName());

    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof WebApplicationException) {
            WebApplicationException webException = (WebApplicationException) exception;
            String message = webException.getMessage() == null || webException.getMessage().trim().isEmpty()
                    ? "Request failed."
                    : webException.getMessage();

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

        LOGGER.log(Level.SEVERE, "Unhandled server error", exception);

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
