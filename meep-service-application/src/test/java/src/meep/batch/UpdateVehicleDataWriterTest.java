package src.meep.batch;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import src.meep.VehicleService;
import src.meep.repository.entity.VehicleEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class UpdateVehicleDataWriterTest {

    int testCases = 20;

    @Test
    public void testWhenWriteThenWrite() throws Exception{

        // Given
        VehicleService service = Mockito.mock(VehicleService.class);
        UpdateVehicleDataWriter sut = new UpdateVehicleDataWriter(service);
        List<VehicleEntity> data = getVehicleArray(testCases);


        // When
        sut.write(data);


        // Then
        Mockito.verify(service).updateVehicleEntityData(data, data.get(0).getBatchUpdateId());

    }

    private ArrayList<VehicleEntity> getVehicleArray(int numberOfVehicles) {
        ArrayList<VehicleEntity> vehicleArrayList = new ArrayList<>();
        for (int i = 0; i<numberOfVehicles ; i++) {
            vehicleArrayList.add(generateTestVehicle());
        }
        return vehicleArrayList;
    }

    private VehicleEntity generateTestVehicle(){
        return VehicleEntity.builder()
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
