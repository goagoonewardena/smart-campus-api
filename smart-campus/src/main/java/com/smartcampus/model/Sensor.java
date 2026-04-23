/**
 * Name - Gethmi Goonewardena
 * IIT ID - 20240971
 * UOW ID - w2120165
 */

package com.smartcampus.model;

/**
 * Represents an IoT Sensor deployed in a Smart Campus Room.
 * A Sensor is a core resource that belongs to a Room and
 * can record historical readings via SensorReading.
 * Sensors are stored in-memory using CampusDataStore.
 * Example JSON representation:
 * {
 *   "id": "TEMP-001",
 *   "type": "Temperature",
 *   "status": "ACTIVE",
 *   "currentValue": 23.5,
 *   "roomId": "LIB-301"
 * }
 */
public class Sensor {

    // Unique identifier for the sensor e.g. "TEMP-001", "CO2-001"
    private String id;
    
    // Category of the sensor e.g. "Temperature", "CO2"
    private String type;
    
    // Current operational state of the sensor
    // Valid values: "ACTIVE", "MAINTENANCE", "OFFLINE"
    // Sensors with "MAINTENANCE" status cannot accept new readings (returns 403)
    private String status;
    
    // The most recent measurement recorded by the sensor
    // Automatically updated when a new SensorReading is posted
    private double currentValue;
    
    // Foreign key linking this sensor to the Room it is deployed in
    // Must reference an existing Room ID — validated on creation (returns 422 if invalid)
    private String roomId;

    /**
     * Default no-argument constructor
     */
    public Sensor() {
    }

    /**
     * Constructor to create a fully populated Sensor.
     * @param id           unique sensor identifier e.g. "TEMP-001"
     * @param type         sensor category e.g. "Temperature", "CO2"
     * @param status       operational state e.g. "ACTIVE", "MAINTENANCE"
     * @param currentValue most recent measurement value
     * @param roomId       ID of the Room this sensor is deployed in
     */
    public Sensor(String id, String type, String status, double currentValue, String roomId) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.currentValue = currentValue;
        this.roomId = roomId;
    }

    /**
     * Returns the unique sensor identifier.
     * @return sensor ID e.g. "TEMP-001"
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique sensor identifier.
     * @param id unique sensor identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the sensor type/category.
     * @return sensor type e.g. "Temperature", "CO2"
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the sensor type/category.
     * @param type sensor category
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the current operational status of the sensor.
     * @return status e.g. "ACTIVE", "MAINTENANCE", "OFFLINE"
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the operational status of the sensor.
     * @param status operational state
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns the most recent measurement recorded by the sensor.
     * This value is automatically updated when a new reading is posted.
     * @return current measurement value e.g. 23.5
     */
    public double getCurrentValue() {
        return currentValue;
    }

    /**
     * Sets the most recent measurement value.
     * Called automatically when a new SensorReading is posted
     * to keep the sensor's currentValue in sync.
     * @param currentValue latest measurement value
     */
    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    /**
     * Returns the ID of the Room this sensor is deployed in.
     * @return room ID e.g. "LIB-301"
     */
    public String getRoomId() {
        return roomId;
    }

    /**
     * Sets the ID of the Room this sensor is deployed in.
     * @param roomId ID of the room
     */
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
