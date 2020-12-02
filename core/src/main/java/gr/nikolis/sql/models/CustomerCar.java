package gr.nikolis.sql.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "CustomerCar")
@Table(name = "customer_car")
public class CustomerCar {

    @EmbeddedId
    private CustomerCarId id;

    @ManyToOne(/*fetch = FetchType.LAZY*/)
    @MapsId("customerId") //The property name of CustomerCarId class (The Embedded class)
    private Customer customer;

    @ManyToOne(/*fetch = FetchType.LAZY*/)
    @MapsId("carId") //The property name of CustomerCarId class (The Embedded class)
    private Car car;

    @Column(name = "driver_licence")
    private Long driverLicenceNumber;

    public CustomerCar() {
    }

    public CustomerCar(Customer customer, Car car, Long driverLicenceNumber) {
        this.customer = customer;
        this.car = car;
        this.driverLicenceNumber = driverLicenceNumber;
        this.id = new CustomerCarId(customer.getId(), car.getId());
    }

    @JsonIgnore
    public CustomerCarId getId() {
        return id;
    }

    public void setId(CustomerCarId id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Long getDriverLicenceNumber() {
        return driverLicenceNumber;
    }

    public void setDriverLicenceNumber(Long driverLicenceNumber) {
        this.driverLicenceNumber = driverLicenceNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, car);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        CustomerCar that = (CustomerCar) obj;
        return Objects.equals(this.getCustomer(), that.getCustomer()) &&  Objects.equals(this.getCar(), that.getCar());
    }
}
