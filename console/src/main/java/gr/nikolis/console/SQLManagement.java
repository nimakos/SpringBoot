package gr.nikolis.console;

import gr.nikolis.sql.models.Car;
import gr.nikolis.sql.models.Customer;
import gr.nikolis.sql.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component //this creates the instance
public class SQLManagement {

    @Autowired
    private CustomerService customerService;

    @EventListener(ContextRefreshedEvent.class)
    @Transactional //in order to prevent session closing
    public void start() {
        set_manyToMany();
        //get_manyToMany();
    }

    private void get_manyToMany() {
        List<Customer> l =  customerService.getCustomers();
        //l.get(0).getCars();
    }

    private void set_manyToMany() {
        Car car = new Car();
        Car car2 = new Car();
        Customer customer = new Customer();

        car.setBrandName("Opel");
        car.setColor("black");
        car.setPlate("YHA8153");
        car.setLicenceNumber(3434344898L);

        car2.setBrandName("BMW");
        car2.setColor("Black");
        car2.setPlate("YZZ8987");
        car2.setLicenceNumber(234324L);

        customer.setFirstName("Vangelis");
        customer.setLastName("Nikolis");
        customer.setAge(38);

        customerService.insertCustomerFromConsole(customer, car, car2);
    }
}
