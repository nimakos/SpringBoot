package gr.nikolis.sql.service;

import gr.nikolis.sql.models.Car;
import gr.nikolis.sql.models.Customer;
import gr.nikolis.sql.models.CustomerCar;
import gr.nikolis.sql.models.Phone;
import gr.nikolis.sql.repositories.CarRepository;
import gr.nikolis.sql.repositories.CustomerCarRepository;
import gr.nikolis.sql.repositories.CustomerRepository;
import gr.nikolis.sql.repositories.PhoneRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CustomerCarRepository customerCarRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public PhoneRepository getPhoneRepository() {
        return phoneRepository;
    }

    public CarRepository getCarRepository() {
        return carRepository;
    }

    public CustomerCarRepository getCustomerCarRepository() {
        return customerCarRepository;
    }

    // == SELECT ==

    public List<Customer> getCustomers() {
        return getCustomerRepository().findAll();
    }

    public List<Customer> getCustomersById(List<Long> Ids) {
        return getCustomerRepository().findAllById(Ids);
    }

    public List<Customer> getCustomerByName(String name) {
        return getCustomerRepository().findByFirstName(name);
    }

    public List<Customer> getCustomerByNameAndAge(String name, int age) {
        return getCustomerRepository().findByNameAndAge(name, age);
    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> customer = getCustomerRepository().findById(id);
        return customer.orElse(null);
    }

    @Transactional
    public Customer getWithSession(long id) {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = builder.createQuery(Customer.class);
        Root<Customer> customer = criteriaQuery.from(Customer.class);
        criteriaQuery
                .select(customer)
                .where(builder.equal(customer.get("id"), id));
        return session.createQuery(criteriaQuery).getSingleResult();
    }

    @Transactional
    public List<Customer> getAllWithSession() {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = builder.createQuery(Customer.class);
        Root<Customer> root = criteriaQuery.from(Customer.class);
        criteriaQuery.select(root);
        return session.createQuery(criteriaQuery).getResultList();
    }

    // == INSERT ==

    @Transactional
    public Customer insertCustomer(Customer customer) {
        return getCustomerRepository().save(customer);
    }

    @Transactional
    public Customer insertFullCustomer(Customer customer) {
        List<CustomerCar> cars = new ArrayList<>(customer.getCars());
        customer.getCars().clear();

        for (CustomerCar customerCar : cars) {
            Car savedCar = getCarRepository().save(customerCar.getCar());
            customer.addCar(savedCar, customerCar.getDriverLicenceNumber());
        }

        Customer savedCustomer = getCustomerRepository().save(customer);

        List<Phone> phones = new ArrayList<>(customer.getPhones());
        customer.getPhones().clear();

        for (Phone phone : phones) {
            phone.setCustomer(savedCustomer);
            getPhoneRepository().save(phone);
            customer.addPhone(phone);
        }
        return savedCustomer;
    }

    @Transactional
    public void insertCustomerFromConsole(Customer customer, Car... cars) {
        for (Car car : cars) {
            getCarRepository().save(car);
            customer.addCar(car, 0L);
        }
        getCustomerRepository().save(customer);
    }

    public Customer addWithEntityManager(Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();

            entityManager.persist(customer);
            entityTransaction.commit();

        } catch (Exception ex) {
            if (entityTransaction != null)
                entityTransaction.rollback();
            log.error(ex.getMessage());
        } finally {
            entityManager.close();
        }
        return customer;
    }

    // == UPDATE ==

    @Transactional
    private Customer updateFullCustomer(long customerId, Customer customer) {
        Customer customerToUpdate = getCustomerRepository().getOne(customerId);
        customerToUpdate.setLastName(customer.getLastName());
        customerToUpdate.setFirstName(customer.getFirstName());
        customerToUpdate.setAge(customer.getAge());

        getPhoneRepository().deleteAll(customerToUpdate.getPhones());
        customerToUpdate.setPhones(customer.getPhones());

        for (Phone phone : customer.getPhones()) {
            phone.setCustomer(customerToUpdate);
            getPhoneRepository().save(phone);
        }

        customerToUpdate.getCars().clear();
        for (CustomerCar customerCar : customer.getCars()) {
            getCarRepository().delete(customerCar.getCar());
            Car savedCar = getCarRepository().save(customerCar.getCar());
            customerToUpdate.addCar(savedCar, customerCar.getDriverLicenceNumber());
        }
        return customerToUpdate;
    }

    /* Not working*/
    public Customer updateWithEntityManager(long id, Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = null;
        Customer customerToUpdate = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            customerToUpdate = entityManager.find(Customer.class, id);

            if (customerToUpdate != null) {
                for (CustomerCar customerCarFromDB : customerToUpdate.getCars()) {
                    for (CustomerCar customerCarNew : customer.getCars()) {
                        if (customerCarFromDB.getCar().equals(customerCarNew.getCar())) {
                            Car carToUpdate = customerCarNew.getCar();
                            carToUpdate.setId(customerCarFromDB.getCar().getId());
                            customerCarFromDB.setCar(carToUpdate);
                            break;
                        }
                    }
                }
                entityManager.merge(customerToUpdate);
            } else {
                entityManager.persist(customer);
            }
            entityTransaction.commit();

        } catch (Exception ex) {
            if (entityTransaction != null)
                entityTransaction.rollback();
            log.error(ex.getMessage());
        } finally {
            entityManager.close();
        }
        return customerToUpdate;
    }

    @Transactional
    public void updateWithSession(long id, Customer customerToUpdate) {
        Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        Customer customerInDb = session.load(Customer.class, id);

        for (CustomerCar customerCar : customerInDb.getCars()) {
            session.update(customerCar);
        }
        //...
        session.close();
    }

    @Transactional
    public Customer addOrUpdateCustomer(long customerId, Customer customer) {
        Optional<Customer> byId = getCustomerRepository().findById(customerId);
        if (byId.isPresent())
            return updateFullCustomer(customerId, customer);
        else
            return insertFullCustomer(customer);
    }

    @Transactional
    public Iterable<Customer> addOrUpdateCustomerList(long customerId, List<Customer> customerList) {
        List<Customer> result = new ArrayList<>();

        if (customerList == null)
            return new ArrayList<>();

        for (Customer customer : customerList)
            result.add(addOrUpdateCustomer(customerId, customer));

        return result;
    }

    // == DELETE ==

    @Transactional
    public void deleteCustomer(String name) {
        Iterable<Customer> customers = getCustomerByName(name);
        getCustomerRepository().deleteAll(customers); // with this way deletes any garbage found on Phone class
    }

    @Transactional
    public void deleteCustomer(long id) {
        getCustomerRepository().deleteById(id);
    }
}
