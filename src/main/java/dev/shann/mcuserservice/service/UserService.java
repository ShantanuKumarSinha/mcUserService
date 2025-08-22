package dev.shann.mcuserservice.service;

import dev.shann.mcuserservice.dto.AuthenticateUserDTO;
import dev.shann.mcuserservice.dto.CreateUserDTO;
import dev.shann.mcuserservice.entity.UserEntity;
import dev.shann.mcuserservice.exceptions.EmailNotFoundException;
import dev.shann.mcuserservice.exceptions.ExistingUserException;
import dev.shann.mcuserservice.model.User;
import dev.shann.mcuserservice.repository.UserRepository;
import org.modelmapper.ModelMapper;

public class UserService {

  UserRepository userRepository;
  ModelMapper modelMapper;

  public UserService(UserRepository userRepository, ModelMapper modelMapper) {
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
  }

  public User authenticate(AuthenticateUserDTO authenticateUserDTO) {
    var userEntitiy =
        userRepository
            .findByEmail(authenticateUserDTO.email())
            .orElseThrow(EmailNotFoundException::new);
    return modelMapper.map(userEntitiy, User.class);
  }

  public User createUser(CreateUserDTO createUserDTO) {
    if (userRepository.findByEmail(createUserDTO.users().getEmail()).isPresent()) {
      throw new ExistingUserException();
    }
    var userEntity = modelMapper.map(createUserDTO.users(), UserEntity.class);
    return modelMapper.map(userRepository.save(userEntity), User.class);
  }
}
