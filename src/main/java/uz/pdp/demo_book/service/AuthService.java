package uz.pdp.demo_book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.demo_book.entity.Role;
import uz.pdp.demo_book.entity.User;
import uz.pdp.demo_book.entity.enums.RoleEnum;
import uz.pdp.demo_book.oauth2.Provider;
import uz.pdp.demo_book.payload.RegisterDTO;
import uz.pdp.demo_book.repository.RoleRepository;
import uz.pdp.demo_book.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    public void processOAuthPostLogin(String username) {
        User existUser = userRepository.getUserByUsername(username);

        if (existUser == null) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setProvider(Provider.GOOGLE);
            newUser.setEnabled(true);

            userRepository.save(newUser);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UsernameNotFoundException("Bunday user aniqlanmadi");
    }

    public String register(RegisterDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setFullName(dto.getFullName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Optional<Role> byName = roleRepository.findByName(RoleEnum.USER.name()); //user
        Role role = byName.get();
        user.setRole(role);
        userRepository.save(user);
        return "Registered!";
    }
}
