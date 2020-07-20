package src.meep.batch;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import src.meep.VehicleService;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeleteLeftoverVehiclesTaskletTest {



    @Test
    public void testWhenExecuteThenDelete(){

        // Given

        VehicleService vehicleService = Mockito.mock(VehicleService.class);
        DeleteLeftoverVehiclesTasklet sut = new DeleteLeftoverVehiclesTasklet(vehicleService);

        StepContribution stepContribution = Mockito.mock(StepContribution.class);
        ChunkContext chunkContext = Mockito.mock(ChunkContext.class);
        StepContext stepContext = Mockito.mock(StepContext.class);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("JobID", "job-id-value");

        when(chunkContext.getStepContext()).thenReturn(stepContext);
        when(stepContext.getJobParameters()).thenReturn(parameters);


        // When
        sut.execute(stepContribution, chunkContext);


        // then
        verify(vehicleService).deletePreviousJobsData("job-id-value");


    }

}
