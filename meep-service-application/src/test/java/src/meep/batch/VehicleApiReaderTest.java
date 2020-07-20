package src.meep.batch;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import src.meep.client.VehicleClient;
import src.meep.model.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


public class VehicleApiReaderTest {

    private VehicleApiReader sut;

    private VehicleClient vehicleClient;

    private int testCases = 20;

    @Autowired
    public VehicleApiReaderTest(){

        this.vehicleClient = Mockito.mock(VehicleClient.class);
        this.sut = new VehicleApiReader(vehicleClient);
    }



    @Test
    public void testWhenReadDataThenRead() throws JobExecutionException {

        // Given
        ArrayList<Vehicle> apiRetrievedVehicles = getVehicleArray(testCases);
        List<Vehicle> originalVehiclesData = (ArrayList<Vehicle>) apiRetrievedVehicles.clone(); // Need to clone it because read deletes member from the original as it goes
        when(vehicleClient.getVehicles()).thenReturn(ResponseEntity.ok(apiRetrievedVehicles));


        // When
        List<Vehicle> responseVehicles = new ArrayList<Vehicle>();
        for (int i = 0; i<testCases; i++){
            responseVehicles.add(sut.read());
        }

        // Then
        for (int i = 0; i<testCases; i++){
            assertEquals(responseVehicles.get(i), originalVehiclesData.get(i));
        }
        assertTrue(apiRetrievedVehicles.isEmpty());
        assertNull(sut.read());
    }


    @Test
    public void testWhenReadNoDataThenRead() throws JobExecutionException {

        ArrayList<Vehicle> vehicleArrayList = new ArrayList<>();

        // Given
        when(vehicleClient.getVehicles()).thenReturn(ResponseEntity.ok(vehicleArrayList));

        // When
        Vehicle responseVehicle = sut.read();

        // Then
        assertNull(responseVehicle);

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
