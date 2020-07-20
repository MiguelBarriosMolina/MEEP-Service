package src.meep.batch;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.lang.NonNull;
import src.meep.VehicleService;
import src.meep.repository.entity.VehicleEntity;

import java.util.List;

@Slf4j
public class UpdateVehicleDataWriter implements ItemWriter<VehicleEntity>{


    private VehicleService service;


    public UpdateVehicleDataWriter(VehicleService service){
        this.service = service;
    }

    private String jobId;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        JobParameters jobParameters = stepExecution.getJobParameters();
        jobId = jobParameters.getString("JobID");
        if(jobId == null || jobId.isEmpty()){
            jobId = "initialization";
        }
    }

    public void write(@NonNull List<? extends VehicleEntity> vehicleList){
        service.updateVehicleEntityData((List<VehicleEntity>)vehicleList, jobId);
    }



}
