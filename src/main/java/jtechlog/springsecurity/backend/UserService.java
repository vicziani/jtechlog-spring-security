package jtechlog.springsecurity.backend;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
    
   public List<User> listUsers() {
        Object user = SecurityContextHolder.getContext()
               .getAuthentication().getPrincipal();
       logger.debug("Logged in user: {}", user);

       return userRepository.findAll(Sort.by("username"));
   }
   
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
   public void addUser(User user) {
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       userRepository.save(user);
   }

}
