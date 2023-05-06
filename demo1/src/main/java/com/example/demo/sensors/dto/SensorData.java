package com.example.demo.sensors.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SensorData {

    @JsonProperty(required = true)
    private String sensorId;


    @JsonProperty(required = true)
    private double temperature;


    @JsonProperty(required = true)
    private double humidity;


    @JsonProperty(required = true)
    private double windSpeed;

    private String date;

    private Date createdAt;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public void setId( Long id ) {
        this.id = id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId( String sensorId ) {
        this.sensorId = sensorId;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature( double temperature ) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity( double humidity ) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed( double windSpeed ) {
        this.windSpeed = windSpeed;
    }

    public String getDate() {
        return date;
    }

    public void setDate( String date ) {
        this.date = date;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt( Date createdAt ) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }
}
