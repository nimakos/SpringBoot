package gr.nikolis.sql.repositories;

import gr.nikolis.sql.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByFirstName(final String name);

    @Query("SELECT c FROM Customer c WHERE c.firstName = ?1 AND c.age = ?2")
    List<Customer> findByNameAndAge(final String name, final int age);

    @Transactional
    @Modifying
    @Query("DELETE FROM Customer c WHERE c.firstName = ?1")
    void deleteCustomer(final String name);
}
