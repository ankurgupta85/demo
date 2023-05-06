package com.example.demo.integration;

import com.example.demo.sensors.SensorDataRepository;
import com.example.demo.sensors.SensorDataService;
import com.example.demo.sensors.dto.ISensorData;
import com.example.demo.sensors.dto.SensorData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class Demo1ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @Autowired
    private SensorDataService sensorDataService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        sensorDataRepository.deleteAll();
    }


    @Test
    public void givenSensorObject_whenCreateSensorData_thenReturnSavedData() throws Exception{

        // given - precondition or setup
        SensorData sensorData = SensorData.builder().sensorId( "123" ).windSpeed( 67.22 ).humidity( 22.31 ).temperature( 11.14 ).build();

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform( post( "/v1/sensors" )
                                                         .contentType( MediaType.APPLICATION_JSON )
                                                         .content(objectMapper.writeValueAsString(sensorData)) );

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.sensorId",
                                    is(sensorData.getSensorId())))
                .andExpect(jsonPath("$.humidity",
                                    is(sensorData.getHumidity())))
                .andExpect(jsonPath("$.windSpeed",
                                    is(sensorData.getWindSpeed())))
                .andExpect(jsonPath("$.humidity",
                                    is(sensorData.getHumidity())))
                .andExpect(jsonPath("$.temperature",
                                    is(sensorData.getTemperature())))
                .andExpect(jsonPath("$.date",
                                    is( LocalDate.now().toString() ) ) );
    }

    @Test
    public void givenListOfSensorData_whenGetAllSensorAverage_thenReturnSensorList() throws Exception{
        // given - precondition or setup
        sensorDataService.saveSensorData(SensorData.builder().sensorId( "123" ).windSpeed( 67.22 ).humidity( 22.31 ).temperature( 11.14 ).build());
        sensorDataService.saveSensorData(SensorData.builder().sensorId( "123" ).windSpeed( 22.45 ).humidity( 44.21 ).temperature( 33.89 ).build());
        sensorDataService.saveSensorData(SensorData.builder().sensorId( "1231" ).windSpeed( 54.11 ).humidity( 31.12 ).temperature( 67.61 ).build());
        Iterable<SensorData> data = sensorDataRepository.findAll();
        String url = "/v1/sensors?fromDate=" + LocalDate.now() + "&toDate=" + LocalDate.now();
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get( url ));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                                    is(2)));
    }

    @Test
    public void givenListOfSensorData_whenGetSingleSensorAverage_thenReturnSensorData() throws Exception{
        // given - precondition or setup
        sensorDataService.saveSensorData(SensorData.builder().sensorId( "123" ).windSpeed( 67.22 ).humidity( 22.31 ).temperature( 11.14 ).build());
        sensorDataService.saveSensorData(SensorData.builder().sensorId( "123" ).windSpeed( 22.45 ).humidity( 44.21 ).temperature( 33.89 ).build());
        sensorDataService.saveSensorData(SensorData.builder().sensorId( "1231" ).windSpeed( 54.11 ).humidity( 31.12 ).temperature( 67.61 ).build());
        Iterable<SensorData> data = sensorDataRepository.findAll();
        String url = "/v1/sensors/123?fromDate=" + LocalDate.now() + "&toDate=" + LocalDate.now();
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get( url ));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.sensorId",
                                    is("123")))
                .andExpect(jsonPath("$.temperature",
                                    is((33.89+11.14)/2)));
    }

}
