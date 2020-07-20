package src.meep.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import src.meep.model.Vehicle;

import java.awt.geom.Point2D;
import java.util.List;


@FeignClient(name = "vehicleDataClient", url = "https://apidev.meep.me/tripplan/api")
public interface VehicleFeignClient {

    @GetMapping(path = "/v1/routers/lisboa/resources", name = "getVehicles")
    ResponseEntity<List<Vehicle>> getVehicles(@RequestParam Point2D.Double lowerLeftLatLong,
                                              @RequestParam Point2D.Double upperRightLatLong,
                                              @RequestParam int[] companyZoneIds);

}
