/**
 * Name - Gethmi Goonewardena
 * IIT ID - 20240971
 * UOW ID - w2120165
 */
package com.smartcampus.model;

/**
 * Represents a JSON error response body.
 * Used by all Exception Mappers to return consistent error
 * responses across the entire API.
 * Example JSON output:
 * {
 *   "status": 409,
 *   "error": "Conflict",
 *   "message": "Room 'LIB-301' cannot be deleted because sensors are still assigned.",
 *   "timestamp": 1745456400000
 * }
 */
public class ApiError {

    // HTTP status code e.g. 400, 403, 404, 409, 422, 500
    private int status;
    
    // Short error type description e.g. "Conflict", "Forbidden", "Not Found"
    private String error;
    
    // Detailed human-readable error message explaining what went wrong
    private String message;
    
    // Epoch time in milliseconds when the error occurred
    private long timestamp;

    /**
     * Default no-argument constructor required for JSON serialization.
     */
    public ApiError() {
    }

    /**
     * Constructor to create a fully populated ApiError response.
     * @param status    HTTP status code
     * @param error     Short error type description
     * @param message   Detailed error message
     * @param timestamp Epoch time in milliseconds
     */
    public ApiError(int status, String error, String message, long timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }

    /**
     * Returns the HTTP status code.
     * @return status code e.g. 409
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the HTTP status code.
     * @param status HTTP status code
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Returns the short error type description.
     * @return error type e.g. "Conflict"
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the short error type description.
     * @param error error type description
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Returns the detailed error message.
     * @return human-readable error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the detailed error message.
     * @param message human-readable error message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the timestamp when the error occurred.
     * @return epoch time in milliseconds
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the error occurred.
     * @param timestamp epoch time in milliseconds
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
