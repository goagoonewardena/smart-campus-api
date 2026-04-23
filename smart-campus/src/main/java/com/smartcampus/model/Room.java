/**
 * Name - Gethmi Goonewardena
 * IIT ID - 20240971
 * UOW ID - w2120165
 */

package com.smartcampus.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a physical Room in the Smart Campus system.
 * A Room is a core resource that can contain multiple Sensors.
 * Rooms are stored in-memory using CampusDataStore.
 * Example JSON representation:
 * {
 *   "id": "LIB-301",
 *   "name": "Library Quiet Study",
 *   "capacity": 50,
 *   "sensorIds": ["TEMP-001", "CO2-001"]
 * }
 */
public class Room {

    // Unique identifier for the room e.g. "LIB-301"
    private String id;
    
    // Human-readable name of the room e.g. "Library Quiet Study"
    private String name;
    
    // Maximum occupancy capacity of the room for safety regulations
    private int capacity;
    
    // List of sensor IDs deployed in this room
    // Automatically populated when a Sensor is registered with this roomId
    // A room with sensors cannot be deleted — returns 409 Conflict
    private List<String> sensorIds = new ArrayList<>();

    /**
     * Default no-argument constructor
     */
    public Room() {
    }

    /**
     * Constructor to create a Room with core details.
     * sensorIds list is initialized as empty by default.
     * @param id       unique room identifier e.g. "LIB-301"
     * @param name     human-readable room name
     * @param capacity maximum occupancy for safety regulations
     */
    public Room(String id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    /**
     * Returns the unique room identifier.
     * @return room ID e.g. "LIB-301"
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique room identifier.
     * @param id unique room identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the human-readable room name.
     * @return room name e.g. "Library Quiet Study"
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the human-readable room name.
     * @param name room name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the maximum occupancy capacity of the room.
     * @return capacity e.g. 50
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the maximum occupancy capacity of the room.
     * @param capacity maximum occupancy
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Returns the list of sensor IDs deployed in this room.
     * An empty list means the room has no sensors and can be safely deleted.
     * @return list of sensor ID strings
     */
    public List<String> getSensorIds() {
        return sensorIds;
    }

    /**
     * Sets the list of sensor IDs deployed in this room.
     * @param sensorIds list of sensor ID strings
     */
    public void setSensorIds(List<String> sensorIds) {
        this.sensorIds = sensorIds;
    }
}
