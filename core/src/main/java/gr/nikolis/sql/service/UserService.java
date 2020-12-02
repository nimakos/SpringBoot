package gr.nikolis.sql.service;

import gr.nikolis.sql.constants.Roles;
import gr.nikolis.sql.models.Role;
import gr.nikolis.sql.models.User;
import gr.nikolis.sql.repositories.RoleRepository;
import gr.nikolis.sql.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void initSave(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        switch (user.getFirstName().toUpperCase()) {
            case Roles.ADMIN_ROLE:
                roles.add(roleRepository.findByRoleName(Roles.ADMIN_ROLE));
                break;
            case Roles.MANAGER_ROLE:
                roles.add(roleRepository.findByRoleName(Roles.MANAGER_ROLE));
                break;
            case Roles.USER_ROLE:
            default:
                roles.add(roleRepository.findByRoleName(Roles.USER_ROLE));
        }
        user.setRoles(roles);
        user.setUsername(user.getFirstName());
        save(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User findByUserName(String username) {
        if (userRepository.findByUsername(username).isPresent())
            return userRepository.findByUsername(username).get();
        else return new User();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByConfirmationToken(String confirmationToken) {
        return userRepository.findByConfirmationToken(confirmationToken);
    }
}
