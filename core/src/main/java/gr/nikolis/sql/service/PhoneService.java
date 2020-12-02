package gr.nikolis.sql.service;

import gr.nikolis.handlers.ResourceNotFoundException;
import gr.nikolis.sql.models.Customer;
import gr.nikolis.sql.models.Phone;
import gr.nikolis.sql.repositories.CustomerRepository;
import gr.nikolis.sql.repositories.PhoneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PhoneService {
    private final PhoneRepository phoneRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public PhoneService(PhoneRepository phoneRepository, CustomerRepository customerRepository) {
        this.phoneRepository = phoneRepository;
        this.customerRepository = customerRepository;
    }

    public PhoneRepository getPhoneRepository() {
        return phoneRepository;
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    // == SELECT ==

    public List<Phone> getAllPhones() {
        return getPhoneRepository().findAll();
    }

    public Optional<Phone> getPhoneById(Long phoneId) {
        Optional<Phone> phone = getPhoneRepository().findById(phoneId);

        if (phone.isEmpty()) {
            log.info("Phone with id" + phoneId + " not found");
            throw new ResourceNotFoundException("Phone with id " + phoneId + " not found");
        } else
            return getPhoneRepository().findById(phoneId);
    }

    public Phone getPhoneById_2(Long phoneId) {
        return getPhoneRepository().findById(phoneId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Invalid ride id %s", phoneId)));
    }

    public Customer getCustomerByPhone(long phone) {
        return getPhoneRepository().findAll(phone).get(0);
    }

    public List<Customer> getAllCustomersByPhone(long phone) {
        return getPhoneRepository().findAll(phone);
    }

    // == INSERT ==

    public Phone createPhone(long customerId, Phone phone) {
        Optional<Customer> byId = getCustomerRepository().findById(customerId);
        if (byId.isEmpty()) {
            log.info("Customer with id" + customerId + " not found");
            //Create new Customer with the existing phone
            Customer customer = new Customer();
            List<Phone> phones = new ArrayList<>();
            phones.add(phone);
            customer.setPhones(phones);
            phone.setCustomer(customer);
            getCustomerRepository().save(customer);
        } else {
            Customer customer = byId.get();
            phone.setCustomer(customer);
        }
        return getPhoneRepository().save(phone);
    }
}
