package gr.nikolis.controller;

import gr.nikolis.sql.models.Customer;
import gr.nikolis.sql.models.Phone;
import gr.nikolis.sql.service.CustomerService;
import gr.nikolis.sql.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*A web service returns back a Json object to the client */
@RestController
@RequestMapping("/customers")
public class WebService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PhoneService phoneService;

    @RequestMapping(value = "/getAllCustomers", method = RequestMethod.GET)
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.insertCustomer(customer);
    }

    @RequestMapping(value = "/addFullCustomer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Customer createFullCustomer(@RequestBody Customer customer) {
        return customerService.insertFullCustomer(customer);
    }

    //Phone
    @RequestMapping(value = "/getAllPhones", method = RequestMethod.GET)
    public List<Phone> getPhones() {
        return phoneService.getAllPhones();
    }

    @RequestMapping(value = "/{customer_id}/phone", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Phone createPhone(@PathVariable(value = "customer_id") Long customer_id, @RequestBody Phone phone) {
        return phoneService.createPhone(customer_id, phone);
    }

    @RequestMapping(value = "/phone/{phone_id}", method = RequestMethod.GET)
    public Optional<Phone> getPhoneById(@PathVariable(value = "phone_id") long phone_id) {
        return phoneService.getPhoneById(phone_id);
    }

    @RequestMapping(value = "/phoneTest/{phone_id}", method = RequestMethod.GET)
    public Phone getPhone(@PathVariable(value = "phone_id") long phone_id) {
        return phoneService.getPhoneById_2(phone_id);
    }

    //Combinations
    @RequestMapping(value = "/getCustomerByPhone/{phone_number}", method = RequestMethod.GET)
    public Customer getCustomerByPhone(@PathVariable(value = "phone_number") long phone_number)  {
        return phoneService.getCustomerByPhone(phone_number);
    }

    @RequestMapping(value = "/getAllCustomersByPhoneNumber/{phone_number}", method = RequestMethod.GET)
    public List<Customer> getAllPhoneValues(@PathVariable(value = "phone_number") long phone_number)  {
        return phoneService.getAllCustomersByPhone(phone_number);
    }

    @RequestMapping(value = "/getCustomerByNameAndAge/{customer_name}/{customer_age}", method = RequestMethod.GET)
    public List<Customer> getCustomerValues(@PathVariable(value = "customer_name") String customer_name, @PathVariable(value = "customer_age") int customer_age) {
        return customerService.getCustomerByNameAndAge(customer_name, customer_age);
    }

    @RequestMapping(value = "/deleteCustomer/{customer_name}", method = RequestMethod.GET)
    public void deleteCustomer(@PathVariable(value = "customer_name") String customer_name) {
        customerService.deleteCustomer(customer_name);
    }

    @RequestMapping(value = "/updateCustomer/{customer_id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Customer updateCustomer(@PathVariable (value = "customer_id") int customer_id, @RequestBody Customer customer) {
        return customerService.addOrUpdateCustomer(customer_id, customer);
    }

    @RequestMapping(value = "/testAddCustomer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Customer testAddCustomer(@RequestBody Customer customer) {
        return customerService.addWithEntityManager(customer);
    }

    @RequestMapping(value = "/testUpdateCustomer/{customer_id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Customer testUpdateCustomer(@PathVariable (value = "customer_id") int customer_id, @RequestBody Customer customer) {
        return customerService.updateWithEntityManager(customer_id, customer);
    }
}
