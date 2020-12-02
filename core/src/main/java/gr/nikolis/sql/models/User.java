package gr.nikolis.sql.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "User")
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "email", nullable = false, unique = true)
    @Email(regexp = ".+@.+\\..+", message = "Please provide a valid Email address")
    @NotEmpty(message = "Please provide an e-mail")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Transient
    private String passwordConfirm;

    @Column(name = "first_name")
    @Size(message = "FirstName size must be between 5 and 30 ", min = 5, max = 30)
    @NotEmpty(message = "Please provide your first name")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Please provide your last name")
    private String lastName;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "disabled")
    private Boolean disabled;

    @Column(name = "account_expired")
    private Boolean accountExpired;

    @Column(name = "account_locked")
    private Boolean accountLocked;

    @Column(name = "credentials_expired")
    private Boolean credentialsExpired;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean isAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(Boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public Boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public Boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }
}

/*
*
    insert into testpersistence.role (role_name) values('USER');
    insert into testpersistence.role (role_name) values('ADMIN');
    insert into testpersistence.role (role_name) values('MANAGER');

    insert into testpersistence.user (user_name, password) values('user', 'user29071982');
    insert into testpersistence.user (user_name, password) values('admin', 'admin29071982');
    insert into testpersistence.user (user_name, password) values('manager', 'manager29071982');

    insert into testpersistence.user_role (user_id, role_id) values(1,1);
    insert into testpersistence.user_role (user_id, role_id) values(2,2);
    insert into testpersistence.user_role (user_id, role_id) values(3,3);
*
* */
