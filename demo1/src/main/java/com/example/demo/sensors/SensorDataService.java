package com.example.demo.sensors;

import com.example.demo.sensors.dto.ISensorData;
import com.example.demo.sensors.dto.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class SensorDataService {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    public SensorData saveSensorData( SensorData sensorData ){
        if(sensorData == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Input data is null");
        }
        if( StringUtils.isEmpty( sensorData.getSensorId() ) ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SensorId is null or empty");
        }
        sensorData.setCreatedAt( new Date() );
        if(sensorData.getDate() == null || sensorData.getDate().isEmpty()){
            sensorData.setDate( LocalDate.now().toString() );
        }
        return this.sensorDataRepository.save( sensorData );
    }

    public List<ISensorData> getAllSensorData(String fromDate, String toDate) {
        return this.sensorDataRepository.getAllSensorsAverageData(fromDate, toDate);
    }

    public ISensorData getSensorData( String sensorId, String fromDate, String toDate ) {
        return this.sensorDataRepository.getSensorAverageData(sensorId, fromDate, toDate);
    }
}
