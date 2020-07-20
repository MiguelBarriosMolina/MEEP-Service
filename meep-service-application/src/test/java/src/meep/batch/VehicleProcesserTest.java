package src.meep.batch;

import org.junit.jupiter.api.Test;
import src.meep.model.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleProcesserTest {


    private int testCases = 5;


    @Test
    public void testWhenProcessThenProcess(){

        // Given
        List<Vehicle> vehicleList = getVehicleArray(testCases);
        VehicleProcesser sut = new VehicleProcesser();


        // When // Then
        for (Vehicle vehicle: vehicleList) {
            assertEquals(vehicle.toDbEntity(), sut.process(vehicle));
        }

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
