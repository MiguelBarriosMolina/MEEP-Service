package src.meep.batch;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.lang.Nullable;
import src.meep.model.Vehicle;

import org.springframework.batch.item.ItemProcessor;
import src.meep.repository.entity.VehicleEntity;

public class VehicleProcesser implements ItemProcessor<Vehicle, VehicleEntity> {

    private String jobId;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        JobParameters jobParameters = stepExecution.getJobParameters();
        jobId = jobParameters.getString("JobID");
        if(jobId == null || jobId.isEmpty()){
            jobId = "initialization";
        }
    }

    @Nullable
    public VehicleEntity process(Vehicle vehicle)  {
        return vehicle.toDbEntityWithUpdateId(jobId);
    }
}
