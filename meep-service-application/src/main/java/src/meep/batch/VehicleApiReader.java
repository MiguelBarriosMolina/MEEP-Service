package src.meep.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.item.ItemReader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import src.meep.model.Vehicle;
import src.meep.client.VehicleClient;

import java.util.List;


@Slf4j
public class VehicleApiReader implements ItemReader<Vehicle> {



    private List<Vehicle> vehicles;

    private VehicleClient vehicleClient;


    public VehicleApiReader(VehicleClient client){
        this.vehicleClient = client;
    }

    public Vehicle read() throws JobExecutionException {

        getVehicleDataIfNull();

        if(!vehicles.isEmpty()){
           return vehicles.remove(0);
        }else{
            vehicles = null;
        }
        return null;
    }

    private void getVehicleDataIfNull() throws JobExecutionException{

        if(vehicles == null) {
            ResponseEntity<List<Vehicle>> response = vehicleClient.getVehicles();
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                vehicles = response.getBody();
                log.info("Retrieved information of {} vehicles.", vehicles.size());
            } else {
                throw new JobExecutionException("Did not receive correct response from API");
            }
        }
    }

}
