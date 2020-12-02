package gr.nikolis.sql.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Car")
@Table(name = "car")
@NaturalIdCache
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Car {

    @Id
    @GeneratedValue
    @Column(nullable = false, length = 10)
    private Long id;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "plate")
    @NaturalId
    private String plate;

    @Column(name = "color")
    private String color;

    @Column(name = "licence_number")
    private Long licenceNumber;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"car", "customer"})
    private List<CustomerCar> customers = new ArrayList<>();

    public void setLicenceNumber(Long licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public Long getLicenceNumber() {
        return this.licenceNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @JsonIgnore //does not show this objects at all
    public List<CustomerCar> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerCar> customers) {
        this.customers = customers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(getPlate(), car.getPlate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlate());
    }
}
