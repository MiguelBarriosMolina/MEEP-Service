package src.meep;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import src.meep.repository.VehicleRepository;
import src.meep.repository.entity.VehicleEntity;


import java.util.List;


@Transactional
@Service
@Slf4j
public class VehicleService {


    private VehicleRepository repository;


    @Autowired
    public VehicleService(VehicleRepository repository){
        this.repository = repository;
    }



    public void updateVehicleEntityData(List<VehicleEntity> newVehicleData, String updateId){
        // Note: We can reduce the amount of save calls by saving only updated vehicles,
        // but that means retrieving previous data and checking every vehicle for updated data, it might be slower.
        // If we need to improve runtime we can look into this.
        for (VehicleEntity vehicle: newVehicleData) {
            repository.save(vehicle);
        }    }

    public void deletePreviousJobsData(String currentJobId){
        repository.deleteByBatchUpdateIdNot(currentJobId);
    }





}
