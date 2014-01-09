package jtechlog.springsecurity.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * A felhasználó kezelő funkciókat megvalósító szolgáltatás. JPA-t használ.
 * Megvalósítja a UserDetailsService interfészt is, ezért a authentication-provider 
 * user-service-ref attribútumban használható.
 */
@Repository("userService")
public class DefaultUserService implements UserDetailsService, UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        try {
            return (UserDetails) entityManager.createQuery("select u from User u where u.username = :username").setParameter("username", username).getSingleResult();
        } catch (NoResultException nre) {
             throw new UsernameNotFoundException("A felhasználó a megadott felhasználónévvel nem található: " + username, nre);
        }
    }
    
   @Override
   public List<User> listUsers() {
       return entityManager.createQuery("select u from User u", User.class).getResultList();
   }
   
   @Override
   @Transactional
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   public void addUser(String name, String password, String roles) {
       User user = new User(name, DigestUtils.md5Hex(password), roles);
       entityManager.persist(user);
   }

}
