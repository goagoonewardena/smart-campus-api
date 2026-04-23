/**
 * Name - Gethmi Goonewardena
 * IIT ID - 20240971
 * UOW ID - w2120165
 */

package com.smartcampus.model;

/**
 * Represents a single historical measurement reading from a Sensor.
 * SensorReadings are stored as a list per sensor in CampusDataStore
 * and form the historical log of measurements for each sensor.
 * When a new SensorReading is posted, the parent Sensor's currentValue
 * is automatically updated to reflect the latest measurement.
 */
public class SensorReading {

    // Unique identifier for this reading event
    // Auto-generated as a UUID if not provided by the client
    private String id;
    
    // Epoch time in milliseconds when the reading was captured
    // Auto-set to current system time if not provided by the client
    private long timestamp;
    
    // The actual measurement value recorded by the sensor hardware
    // e.g. 23.5 for temperature, 400.0 for CO2 levels
    private double value;

    /**
     * Default no-argument constructor
     */
    public SensorReading() {
    }

    /**
     * Constructor to create a fully populated SensorReading.
     * @param id        unique reading identifier (UUID recommended)
     * @param timestamp epoch time in milliseconds when reading was captured
     * @param value     actual measurement value recorded by the sensor
     */
    public SensorReading(String id, long timestamp, double value) {
        this.id = id;
        this.timestamp = timestamp;
        this.value = value;
    }

    /**
     * Returns the unique reading identifier.
     * @return reading ID as UUID string
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique reading identifier.
     * @param id unique reading identifier
     */
    public void setId(String id) {
        this.id = id;
    }
   
    /**
     * Returns the timestamp when the reading was captured.
     * @return epoch time in milliseconds e.g. 1745456400000
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the reading was captured.
     * @param timestamp epoch time in milliseconds
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the actual measurement value recorded by the sensor.
     * @return measurement value e.g. 23.5
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets the actual measurement value recorded by the sensor.
     * After this is set, the parent Sensor's currentValue is
     * updated automatically to keep data consistent.
     * @param value measurement value
     */
    public void setValue(double value) {
        this.value = value;
    }
}
