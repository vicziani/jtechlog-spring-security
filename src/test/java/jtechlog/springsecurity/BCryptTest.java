package jtechlog.springsecurity;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptTest {

    @Test
    public void testBCrypt() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("user"));
        System.out.println(passwordEncoder.encode("admin"));
    }
}
