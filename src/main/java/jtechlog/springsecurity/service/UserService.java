package jtechlog.springsecurity.service;

import java.util.List;

/**
 * Felhasználókezelő funkciókat megvalósító szolgáltatás interfésze.
 */
public interface UserService {
   
   public List<User> listUsers();

   public void addUser(String name, String password, String roles);
}
