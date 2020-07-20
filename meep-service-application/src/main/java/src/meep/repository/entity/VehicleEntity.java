package src.meep.repository.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
@Data @Builder @AllArgsConstructor @NoArgsConstructor @ToString
public class VehicleEntity {



    @Id
    String id;

    String name;
    float x;
    float y;
    @Column(name="licence_plate")
    String licencePlate;
    int range;
    @Column(name="battery_level")
    int batteryLevel;
    int seats;
    int helmets;
    String model;
    String resourceImageId;
    boolean realTimeData;
    String resourceType;
    int companyZoneId;
    String batchUpdateId;

}
