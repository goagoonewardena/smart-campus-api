/**
 * Name - Gethmi Goonewardena
 * IIT ID - 20240971
 * UOW ID - w2120165
 */

package com.smartcampus.filters;

import java.io.IOException;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * API Logging Filter for observability.
 * Implements both ContainerRequestFilter and ContainerResponseFilter
 * to log every incoming HTTP request and every outgoing HTTP response.
 * This filter runs automatically for all API endpoints without
 * any changes needed in individual resource classes.
 */
@Provider
public class ApiLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    // Logger instance for this filter class
    private static final Logger LOGGER = Logger.getLogger(ApiLoggingFilter.class.getName());

    /**
     * Logs incoming HTTP requests.
     * Captures the HTTP method (GET, POST, DELETE etc.)
     * and the full request URI before the request reaches the resource method.
     * @param requestContext contains the incoming request details
     * @throws IOException if an error occurs during filtering
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        LOGGER.info(() -> "Incoming request: "
                + requestContext.getMethod()
                + " "
                + requestContext.getUriInfo().getRequestUri());
    }

    /**
     * Logs outgoing HTTP responses.
     * Captures the HTTP status code and the original request method
     * and URI after the resource method has processed the request.
     * @param requestContext contains the original request details
     * @param responseContext contains the outgoing response details
     * @throws IOException if an error occurs during filtering
     */
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        LOGGER.info(() -> "Outgoing response: status="
                + responseContext.getStatus()
                + " for "
                + requestContext.getMethod()
                + " "
                + requestContext.getUriInfo().getRequestUri());
    }
}
