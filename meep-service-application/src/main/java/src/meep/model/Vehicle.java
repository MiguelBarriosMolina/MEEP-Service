package src.meep.model;


import lombok.*;
import src.meep.repository.entity.VehicleEntity;


@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor @NoArgsConstructor
@ToString
public class Vehicle {

    String id;
    String name;
    float x;
    float y;
    String licencePlate;
    int range;
    int batteryLevel;
    int seats;
    int helmets;
    String model;
    String resourceImageId;
    boolean realTimeData;
    String resourceType;
    int companyZoneId;

    public VehicleEntity toDbEntity(){
        return VehicleEntity.builder()
                .id(id)
                .name(name)
                .x(x)
                .y(y)
                .licencePlate(licencePlate)
                .range(range)
                .batteryLevel(batteryLevel)
                .seats(seats)
                .helmets(helmets)
                .model(model)
                .resourceImageId(resourceImageId)
                .realTimeData(realTimeData)
                .resourceType(resourceType)
                .companyZoneId(companyZoneId)
                .build();
    }


    public VehicleEntity toDbEntityWithUpdateId(String updateId){
        return VehicleEntity.builder()
                .id(id)
                .name(name)
                .x(x)
                .y(y)
                .licencePlate(licencePlate)
                .range(range)
                .batteryLevel(batteryLevel)
                .seats(seats)
                .helmets(helmets)
                .model(model)
                .resourceImageId(resourceImageId)
                .realTimeData(realTimeData)
                .resourceType(resourceType)
                .companyZoneId(companyZoneId)
                .batchUpdateId(updateId)
                .build();
    }

}
