package gr.nikolis.sql.service;

import gr.nikolis.sql.models.Role;
import gr.nikolis.sql.models.User;
import gr.nikolis.sql.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(userName);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Set<String> roleList = new HashSet<>();
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            for (Role role : user.getRoles()) {
                roleList.add(role.getRoleName());
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            }

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .disabled(user.isDisabled() != null ? user.isDisabled() : false)
                    .accountExpired(user.isAccountExpired() != null ? user.isAccountExpired() : false)
                    .accountLocked(user.isAccountLocked() != null ? user.isAccountLocked() : false)
                    .credentialsExpired(user.isCredentialsExpired() != null ? user.isCredentialsExpired() : false)
                    .roles(roleList.toArray(new String[0]))
                    .authorities(grantedAuthorities)
                    .build();
        } else
            throw new UsernameNotFoundException(String.format("User Name ' %s ' was not found", userName));
    }
}
