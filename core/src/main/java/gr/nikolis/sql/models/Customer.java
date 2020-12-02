package gr.nikolis.sql.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity(name = "Customer")
@Table(name = "customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @OneToMany(mappedBy = "customer", orphanRemoval = true/*, fetch = FetchType.LAZY*/) //mappedBy is the property name of Phone class
    @JsonIgnoreProperties({"customer"})
    private final List<Phone> phones = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true) //mappedBy is the property name of CustomerCar class
    @JsonIgnoreProperties({"customer"}) //stop infinite json objects shows only first
    private final List<CustomerCar> cars = new ArrayList<>();

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones.clear();
        this.phones.addAll(phones);
    }

    public void addPhone(Phone phone) {
        getPhones().add(phone);
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setCars(List<CustomerCar> customerCars) {
        this.cars.clear();
        this.cars.addAll(customerCars);
    }

    public List<CustomerCar> getCars() {
        return cars;
    }

    public void addCar(Car car, Long driverLicenceNumber) {
        CustomerCar customerCar = new CustomerCar(this, car, driverLicenceNumber);
        getCars().add(customerCar);
        car.getCustomers().add(customerCar);
    }

    public void removeCar(Car car) {
        for (Iterator<CustomerCar> iterator = getCars().iterator(); iterator.hasNext();) {
            CustomerCar customerCar = iterator.next();

            if (customerCar.getCustomer().equals(this) && customerCar.getCar().equals(car)) {
                iterator.remove();
                customerCar.getCar().getCustomers().remove(customerCar);
                customerCar.setCustomer(null);
                customerCar.setCar(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Customer customer = (Customer) o;
        return Objects.equals(getFirstName(), customer.getFirstName()) && Objects.equals(getLastName(), customer.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }
}
