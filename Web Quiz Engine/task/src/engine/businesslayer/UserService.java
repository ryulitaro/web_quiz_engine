package engine.businesslayer;

import engine.exceptions.BadRequestException;
import engine.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder getEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder getEncoder) {
        this.userRepository = userRepository;
        this.getEncoder = getEncoder;
    }

    public void registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BadRequestException("already registered");
        }
        user.setPassword(getEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
