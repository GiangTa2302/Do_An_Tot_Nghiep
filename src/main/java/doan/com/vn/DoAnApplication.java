package doan.com.vn;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import doan.com.vn.entity.Role;
import doan.com.vn.entity.User;
import doan.com.vn.repository.UserRepository;
import doan.com.vn.utils.RoleName;

@SpringBootApplication
public class DoAnApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoAnApplication.class, args);
    }

    @Bean
    public PasswordEncoder getPaswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return new MyRunner();
    }

    public class MyRunner implements CommandLineRunner {
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Override
        public void run(String... args) throws Exception {
//            User user1 = new User("GiangTT6", "Giangta01");
//            Role roleStudent = new Role(RoleName.ADMIN);
//            
//            Set<Role> roles1 = new HashSet<Role>();
//            roles1.add(roleStudent);
//            
//            String encodePass = passwordEncoder.encode(user1.getPassword());
//            user1.setPassword(encodePass);
//            user1.setRoles(roles1);
//
//            userRepository.save(user1);

        }

    }
}
