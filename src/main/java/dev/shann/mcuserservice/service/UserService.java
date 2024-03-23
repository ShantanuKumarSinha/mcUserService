package dev.shann.mcuserservice.service;

import dev.shann.mcuserservice.dto.AuthenticateUserDTO;
import dev.shann.mcuserservice.dto.CreateUserDTO;
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
        var userEntitiy = userRepository.findByEmail(authenticateUserDTO.email())
                .orElseThrow(EmailNotFoundException::new);
        return modelMapper.map(userEntitiy,Users.class);
    }

    public Users createUser(CreateUserDTO createUserDTO){
        var userEntity = modelMapper.map(createUserDTO.users(), UserEntity.class);
       return modelMapper.map(userRepository.save(userEntity),Users.class);
    }
}
