package com.example.demo.sensors;

import com.example.demo.sensors.dto.ISensorData;
import com.example.demo.sensors.dto.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("v1/sensors")
public class SensorDataController {

    @Autowired
    private SensorDataService sensorDataService;

    @GetMapping()
    public List<ISensorData> getSensorsData( @RequestParam String fromDate, @RequestParam String toDate){
        return this.sensorDataService.getAllSensorData( fromDate , toDate );
    }

    @GetMapping("/{sensorId}")
    public ISensorData getSensorData( @PathVariable String sensorId, @RequestParam String fromDate, @RequestParam String toDate ){
        return this.sensorDataService.getSensorData(sensorId, fromDate, toDate);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public SensorData saveSensorData(@RequestBody() SensorData sensorData ) {
        return this.sensorDataService.saveSensorData( sensorData );
    }

}
