package gr.nikolis.sql.repositories;

import gr.nikolis.sql.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
