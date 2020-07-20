package src.meep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import src.meep.repository.entity.VehicleEntity;

import java.util.List;


@Component
public interface VehicleRepository extends JpaRepository<VehicleEntity, String> {

    List<VehicleEntity> deleteByBatchUpdateIdNot(String batch_update_id);
}
