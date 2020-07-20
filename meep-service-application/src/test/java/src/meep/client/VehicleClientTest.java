package src.meep.client;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import src.meep.model.Vehicle;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class VehicleClientTest {

    int testCases = 20;

    @Test
    public void testWhenGetVehiclesThenGetVehicles(){

        // Given
        VehicleFeignClient feignClient = Mockito.mock(VehicleFeignClient.class);
        VehicleClient sut = new VehicleClient(feignClient);
        ArrayList<Vehicle> vehicles = getVehicleArray(testCases);
        when(feignClient.getVehicles(any(), any(), any())).thenReturn(ResponseEntity.of(Optional.of(vehicles)));


        // When
        ResponseEntity<List<Vehicle>> response = sut.getVehicles();


        // Then
        assertEquals(ResponseEntity.of(Optional.of(vehicles)), response);


    }

    private ArrayList<Vehicle> getVehicleArray(int numberOfVehicles) {
        ArrayList<Vehicle> vehicleArrayList = new ArrayList<>();
        for (int i = 0; i<numberOfVehicles ; i++) {
            vehicleArrayList.add(generateTestVehicle());
        }
        return vehicleArrayList;
    }

    private Vehicle generateTestVehicle(){
        return Vehicle.builder()
                .id(UUID.randomUUID().toString())
                .batteryLevel(ThreadLocalRandom.current().nextInt(1, 101))
                .companyZoneId(ThreadLocalRandom.current().nextInt(1, 700))
                .helmets(ThreadLocalRandom.current().nextInt(1, 4))
                .licencePlate(UUID.randomUUID().toString())
                .realTimeData(ThreadLocalRandom.current().nextInt(1, 3)%2==0)
                .model(UUID.randomUUID().toString())
                .range(ThreadLocalRandom.current().nextInt(1, 101))
                .seats(ThreadLocalRandom.current().nextInt(1, 6))
                .build();
    }

}
