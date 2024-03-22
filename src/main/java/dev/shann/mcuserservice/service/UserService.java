package dev.shann.mcuserservice.service;

import dev.shann.mcuserservice.DTO.AuthenticateUserDTO;
import dev.shann.mcuserservice.DTO.CreateUserDTO;
import dev.shann.mcuserservice.entity.UserEntity;
import dev.shann.mcuserservice.exceptions.EmailNotFoundException;
import dev.shann.mcuserservice.model.Users;
import dev.shann.mcuserservice.repository.UserRepository;
import org.modelmapper.ModelMapper;


public class UserService {

    UserRepository userRepository;
    ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Users authenticate(AuthenticateUserDTO authenticateUserDTO){
        return modelMapper.map(userRepository.findByEmail(authenticateUserDTO.email())
                .orElseThrow(EmailNotFoundException::new),Users.class);
    }

    public Users createUser(CreateUserDTO createUserDTO){
        return modelMapper.map(userRepository
                .save(modelMapper.map(createUserDTO.users(), UserEntity.class)),Users.class);
    }
}
