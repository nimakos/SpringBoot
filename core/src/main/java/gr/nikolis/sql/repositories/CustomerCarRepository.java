package gr.nikolis.sql.repositories;

import gr.nikolis.sql.models.CustomerCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCarRepository extends JpaRepository<CustomerCar, Long> {

}
