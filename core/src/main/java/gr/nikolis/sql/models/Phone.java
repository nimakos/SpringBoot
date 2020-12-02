package gr.nikolis.sql.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Phone")
@Table(name = "phone")
public class Phone implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "phone_number")
    private Long phoneNumber;

    // if I use lazy I don't get all the tree child's,
    // but i don't know to who customer belongs the phone if I query only phones
    @ManyToOne/*(fetch = FetchType.LAZY)*/
    @JoinColumn(name = "customer_id", nullable = false) //name is the Foreign key column name of table Phone
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("phones")
    private Customer customer;

    public Phone() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
