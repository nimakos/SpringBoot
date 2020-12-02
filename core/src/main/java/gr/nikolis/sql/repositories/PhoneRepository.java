package gr.nikolis.sql.repositories;

import gr.nikolis.sql.models.Customer;
import gr.nikolis.sql.models.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    @Query("SELECT c FROM Phone p JOIN p.customer c WHERE p.phoneNumber = ?1")
    List<Customer> findAll(final long phone);
}
