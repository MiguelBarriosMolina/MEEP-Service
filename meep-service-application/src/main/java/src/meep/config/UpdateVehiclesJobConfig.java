package src.meep.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import src.meep.VehicleService;
import src.meep.batch.DeleteLeftoverVehiclesTasklet;
import src.meep.batch.VehicleProcesser;
import src.meep.model.Vehicle;
import src.meep.batch.VehicleApiReader;
import src.meep.batch.UpdateVehicleDataWriter;
import src.meep.client.VehicleClient;
import src.meep.repository.entity.VehicleEntity;

import javax.sql.DataSource;


@Configuration
@EnableAutoConfiguration
public class UpdateVehiclesJobConfig {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehicleClient vehicleClient;

    @Bean
    public Job updateVehicleDataJob(JobBuilderFactory jobBuilders,
                            StepBuilderFactory stepBuilders) {
        return jobBuilders.get("updateVehicleDataJob")
                .start(updateVehicleDataStep(stepBuilders))
                .next(deleteLeftoverDataStep(stepBuilders))
                .build();
    }

    @Bean
    public Step updateVehicleDataStep(StepBuilderFactory stepBuilders) {
        return stepBuilders.get("updateVehicleDataStep")
                .<Vehicle, VehicleEntity>chunk(100)
                .reader(reader())
                .processor(processor())
                .writer(updateVehicleDataWriter())
                .build();
    }

    @Bean
    public Step deleteLeftoverDataStep(StepBuilderFactory stepBuilders) {
        return stepBuilders.get("deleteLeftoverDataStep")
                .tasklet(deleteLeftoverVehiclesWriter())
                .build();
    }

    @Bean
    public VehicleApiReader reader() {
        return new VehicleApiReader(vehicleClient);
    }

    @Bean
    public UpdateVehicleDataWriter updateVehicleDataWriter() {
        return new UpdateVehicleDataWriter(vehicleService);
    }

    @Bean
    public DeleteLeftoverVehiclesTasklet deleteLeftoverVehiclesWriter() {
        return new DeleteLeftoverVehiclesTasklet(vehicleService);
    }

    @Bean
    public VehicleProcesser processor() { return  new VehicleProcesser(); }


    @Bean(name = "transactionManager2")
    @Primary
    public PlatformTransactionManager vehicleTransactionManager(DataSource dataSource) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }


}
