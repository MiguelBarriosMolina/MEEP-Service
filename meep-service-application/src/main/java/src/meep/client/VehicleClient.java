package src.meep.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import src.meep.model.Vehicle;

import java.awt.geom.Point2D;
import java.util.List;

@Component
public class VehicleClient {


    @Value("${location.upper.x}")
    private double locationUpperRight_x;
    @Value("${location.upper.y}")
    private double locationUpperRight_y;
    @Value("${location.lower.x}")
    private double locationLowerLeft_x;
    @Value("${location.lower.y}")
    private double getLocationLowerLeft_y;
    @Value("${company-zone-ids}")
    private int[] companyZoneIds;

    private VehicleFeignClient feignClient;

    @Autowired
    public VehicleClient(VehicleFeignClient feignClientInjection){
        this.feignClient = feignClientInjection;
    }


    public ResponseEntity<List<Vehicle>> getVehicles(){
        Point2D.Double locationLowerLeft = new Point2D.Double(locationLowerLeft_x,getLocationLowerLeft_y);
        Point2D.Double locationUpperRight = new Point2D.Double(locationUpperRight_x,locationUpperRight_y);
        return feignClient.getVehicles(locationLowerLeft, locationUpperRight, companyZoneIds);
    }

}
