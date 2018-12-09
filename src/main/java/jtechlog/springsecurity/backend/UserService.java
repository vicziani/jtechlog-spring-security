package jtechlog.springsecurity.backend;

import java.util.List;

import jtechlog.springsecurity.backend.User;
import jtechlog.springsecurity.backend.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService  implements UserDetailsService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
    
   public List<User> listUsers() {
       return userRepository.findAll();
   }
   
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   public void addUser(User user) {
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       userRepository.save(user);
   }

}
