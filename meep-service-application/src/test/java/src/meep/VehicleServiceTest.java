package src.meep;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import src.meep.repository.VehicleRepository;
import src.meep.repository.entity.VehicleEntity;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.Mockito.*;


@Slf4j
public class VehicleServiceTest {

    private int testCases = 20;



    @Test
    public void testWhenNewDataThenWrite(){
        VehicleRepository repository = Mockito.mock(VehicleRepository.class);
        VehicleService sut = new VehicleService(repository);

        when(repository.findAll()).thenReturn(new ArrayList<>());
        ArrayList<VehicleEntity> vehicles = getVehicleArray(testCases);

        sut.updateVehicleEntityData(vehicles, vehicles.get(0).getBatchUpdateId());

        for(int i = 0; i < testCases ; i++) {
            Mockito.verify(repository).save(vehicles.get(i));
        }


    }

    @Test
    public void testWhenUpdateDataThenWrite(){
        VehicleRepository repository = Mockito.mock(VehicleRepository.class);
        VehicleService sut = new VehicleService(repository);

        // given
        ArrayList<VehicleEntity> vehicles = getVehicleArray(testCases);
        when(repository.findAll()).thenReturn(vehicles);
        ArrayList<VehicleEntity> updatedVehicles = getUpdatedVehicleArray(vehicles, testCases);

        //when
        sut.updateVehicleEntityData(updatedVehicles, updatedVehicles.get(0).getBatchUpdateId());


        //then
        for(int i = 0; i < testCases ; i++) {
            Mockito.verify(repository).save(updatedVehicles.get(i));
        }
    }

    @Test
    public void testWhenDeleteDataThenDelete(){
        VehicleRepository repository = Mockito.mock(VehicleRepository.class);
        VehicleService sut = new VehicleService(repository);

        // given
        ArrayList<VehicleEntity> vehicles = getVehicleArray(testCases);
        when(repository.findAll()).thenReturn(vehicles);
        ArrayList<VehicleEntity> updatedVehicles = getUpdatedVehicleArray(vehicles, testCases/2);

        // when
        sut.deletePreviousJobsData("previousId");


        //then

        verify(repository).deleteByBatchUpdateIdNot("previousId");

    }




    private ArrayList<VehicleEntity> getUpdatedVehicleArray(ArrayList<VehicleEntity> existingVehicles, int arrayLength){
        ArrayList<VehicleEntity> updatedVehicles = getVehicleArray(arrayLength);

        for(int i = 0 ; i < arrayLength; i++ ){
            updatedVehicles.get(i).setId(existingVehicles.get(i).getId());
        }
        return updatedVehicles;
    }

    private ArrayList<VehicleEntity> getVehicleArray(int numberOfVehicles) {
        String jobId = String.valueOf(System.currentTimeMillis());
        ArrayList<VehicleEntity> vehicleArrayList = new ArrayList<>();
        for (int i = 0; i<numberOfVehicles ; i++) {
            vehicleArrayList.add(generateTestVehicleWithJobId(jobId));
        }
        return vehicleArrayList;
    }


    private VehicleEntity generateTestVehicleWithJobId(String jobId){
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
                .batchUpdateId(jobId)
                .build();
    }

}
