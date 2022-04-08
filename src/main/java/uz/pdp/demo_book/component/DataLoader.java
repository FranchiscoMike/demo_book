package uz.pdp.demo_book.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.pdp.demo_book.entity.Role;
import uz.pdp.demo_book.entity.User;
import uz.pdp.demo_book.entity.enums.RoleEnum;
import uz.pdp.demo_book.repository.RoleRepository;
import uz.pdp.demo_book.repository.UserRepository;

import java.rmi.server.UnicastRemoteObject;

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Value("${spring.sql.init.mode}")
    private String mode; //always

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl; //create

    @Override
    public void run(String... args) throws Exception {

        if (mode.equals("always") && ddl.equals("create")){
            // avval role qo'shamiz
            Role admin = new Role(RoleEnum.ADMIN);
            Role user = new Role(RoleEnum.USER);
            Role superAdmin = new Role(RoleEnum.SUPER_ADMIN);

            roleRepository.save(user);
            roleRepository.save(admin);
            roleRepository.save(superAdmin);

            // yangi userlarni beramiz
            User mike = new User("mike", admin, "admin", "0706", true);
            User utkir = new User("utkir", superAdmin, "superAdmin", "0706", true);
            User doni = new User("doni", user, "user", "0706", true);

            userRepository.save(mike);
            userRepository.save(doni);
            userRepository.save(utkir);


        }
    }
}
