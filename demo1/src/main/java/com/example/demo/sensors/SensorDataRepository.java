package com.example.demo.sensors;

import com.example.demo.sensors.dto.ISensorData;
import com.example.demo.sensors.dto.SensorData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorDataRepository extends CrudRepository<SensorData, Long> {

    @Query("SELECT sensorId as sensorId, avg(temperature) as temperature, avg(windSpeed) as windSpeed, avg(humidity) as humidity from SensorData where date>=?1 and date<=?2  group by sensorId")
    List<ISensorData> getAllSensorsAverageData(String fromDate, String toDate);

    @Query("SELECT sensorId as sensorId, avg(temperature) as temperature, avg(windSpeed) as windSpeed, avg(humidity) as humidity from SensorData where sensorId=?1 and date>=?2 and date<=?3")
    ISensorData getSensorAverageData(String sensorId, String fromDate, String toDate);

}
