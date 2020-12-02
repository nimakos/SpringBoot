package gr.nikolis.controller;

import gr.nikolis.sql.models.Customer;
import gr.nikolis.sql.service.CustomerService;
import gr.nikolis.mappings.customer.CustomerMappings;
import gr.nikolis.mappings.customer.ViewNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//https://howtodoinjava.com/spring-boot2/crud-application-thymeleaf/

@Controller
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // http://localhost:8888/customer-list
    @GetMapping(CustomerMappings.CUSTOMER_LIST)
    public String customerList(Model model) {
        model.addAttribute("customers", customerService.getCustomers());
        return ViewNames.CUSTOMER_LIST;
    }

    // http://localhost:8888/add
    @GetMapping(CustomerMappings.ADD_CUSTOMER)
    public String addCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return ViewNames.ADD_EDIT_CUSTOMER;
    }

    @GetMapping(CustomerMappings.EDIT_CUSTOMER)
    public String editCustomer(Model model, @PathVariable("id") Long id) {
       Customer customer = customerService.getCustomerById(id);
       model.addAttribute("customer", customer);
       return ViewNames.ADD_EDIT_CUSTOMER;
    }

    @GetMapping(CustomerMappings.DELETE_CUSTOMER)
    public String deleteCustomer(Model model, @PathVariable("id") Long id) {
        customerService.deleteCustomer(id);
        return ViewNames.REDIRECT;
    }

    @PostMapping(CustomerMappings.CREATE_CUSTOMER)
    public String createCustomer(Customer customer) {
        customerService.addOrUpdateCustomer(customer.getId() != null ? customer.getId() : -1, customer);
        return ViewNames.REDIRECT;
    }
}
