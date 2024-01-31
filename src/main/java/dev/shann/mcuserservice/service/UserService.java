package dev.shann.mcuserservice.service;

import dev.shann.mcuserservice.DTO.AuthenticateUserDTO;
import dev.shann.mcuserservice.DTO.CreateUserDTO;
import dev.shann.mcuserservice.model.Users;
import dev.shann.mcuserservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Users authenticate(AuthenticateUserDTO authenticateUserDTO){
        return userRepository.findByEmail(authenticateUserDTO.email()).orElseThrow(RuntimeException::new);
    }

    public Users createUser(CreateUserDTO createUserDTO){
        return userRepository.save(createUserDTO.users());
    }
}
