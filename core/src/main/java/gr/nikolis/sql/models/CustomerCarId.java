package gr.nikolis.sql.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CustomerCarId implements Serializable {

    @Column(name = "customer_id")
    @JsonIgnore
    private Long customerId;

    @Column(name = "car_id")
    @JsonIgnore
    private Long carId;

    public CustomerCarId() {
    }

    public CustomerCarId(Long customerId, Long carId) {
        this.customerId = customerId;
        this.carId = carId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getCarId() {
        return carId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, carId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        CustomerCarId that = (CustomerCarId) obj;
        return Objects.equals(this.getCustomerId(), that.getCustomerId()) && Objects.equals(this.getCarId(), that.getCarId());
    }
}
