/**
 * Name - Gethmi Goonewardena
 * IIT ID - 20240971
 * UOW ID - w2120165
 */

package com.smartcampus.store;

import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CampusDataStore {

    private static final Map<String, Room> ROOMS = new ConcurrentHashMap<>();
    private static final Map<String, Sensor> SENSORS = new ConcurrentHashMap<>();
    private static final Map<String, List<SensorReading>> SENSOR_READINGS = new ConcurrentHashMap<>();

    private CampusDataStore() {
    }

    public static Map<String, Room> rooms() {
        return ROOMS;
    }

    public static Map<String, Sensor> sensors() {
        return SENSORS;
    }

    public static Map<String, List<SensorReading>> sensorReadings() {
        return SENSOR_READINGS;
    }

    public static List<SensorReading> getOrCreateReadings(String sensorId) {
        return SENSOR_READINGS.computeIfAbsent(sensorId, k -> new ArrayList<>());
    }
}
