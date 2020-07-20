package src.meep.batch;

import org.springframework.batch.core.StepContribution;

import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import src.meep.VehicleService;


public class DeleteLeftoverVehiclesTasklet implements Tasklet {

    private VehicleService service;


    public DeleteLeftoverVehiclesTasklet(VehicleService service){
        this.service = service;
    }

    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext){
        String currentJobId = getCurrentJobId(chunkContext);
        service.deletePreviousJobsData(currentJobId);
        return RepeatStatus.FINISHED;
    }

    private String getCurrentJobId(ChunkContext chunkContext) {
        String currentJobId = (String) chunkContext.getStepContext().getJobParameters().get("JobID");
        if(currentJobId == null || currentJobId.isEmpty()){
            currentJobId = "initialization";
        }
        return currentJobId;
    }


}
