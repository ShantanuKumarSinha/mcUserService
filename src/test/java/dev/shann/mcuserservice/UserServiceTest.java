package dev.shann.mcuserservice;

import dev.shann.mcuserservice.dto.AuthenticateUserDTO;
import dev.shann.mcuserservice.dto.CreateUserDTO;
import dev.shann.mcuserservice.entity.UserEntity;
import dev.shann.mcuserservice.exceptions.EmailNotFoundException;
import dev.shann.mcuserservice.model.User;
import dev.shann.mcuserservice.repository.UserRepository;
import dev.shann.mcuserservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

  @Mock
  ModelMapper modelMapper;

    @Test
    void shouldCreateNewUser() {
        when(modelMapper.map(getUser(), UserEntity.class)).thenReturn(getUserEntity());
        when(userRepository.save(getUserEntity()))
                .thenReturn(getUserEntityWithId());
        when(modelMapper.map(getUserEntityWithId(), User.class))
                .thenReturn(getUserWithId());
        var user = userService.createUser(createUserDTO());
        assertThat(user).isNotNull();
        assertThat(user).isEqualTo(getUser().toBuilder().id(1L).build());
    }

    @Test
    void shouldNotBeAbleToCreateNewUser() {
    when(modelMapper.map(getUser(), UserEntity.class))
        .thenReturn(getUserEntity());
        when(userRepository.save(getUserEntity()))
                .thenReturn(null);
        when(modelMapper.map(null, User.class))
                .thenReturn(null);
        var user = userService.createUser(createUserDTO());
    assertThat(user).isNull();
    }

    @Test
    void shouldAuthenticate() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(getUserEntityWithId()));
        when(modelMapper.map(getUserEntityWithId(), User.class)).thenReturn(getUserWithId());
        var user = userService.authenticate(authenticateUserDTO());
        assertThat(user).isNotNull();
        assertThat(user).isEqualTo(getUser().toBuilder().id(1L).build());
    }

    @Test
    void shouldNotBeAbleToAuthenticate() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(any(UserEntity.class)));
        var authenticateUserDTO = authenticateUserDTO();
        var exception = assertThrows(EmailNotFoundException.class, () -> userService.authenticate(authenticateUserDTO));
        assertThat(exception).isNotNull().isInstanceOf(EmailNotFoundException.class);
    }

    private UserEntity getUserEntity() {
        return UserEntity.builder().email("shan.raj93@gmail.com").password("Test@123").build();
    }

    private UserEntity getUserEntityWithId() {
        return UserEntity.builder().id(1L).email("shan.raj93@gmail.com").password("Test@123").build();
    }

    private User getUser() {
        return User.builder().email("shan.raj93@gmail.com").password("Test@123").build();
    }

    private User getUserWithId() {
        return User.builder().id(1L).email("shan.raj93@gmail.com").password("Test@123").build();
    }
    private CreateUserDTO createUserDTO() {
        return new CreateUserDTO(getUser());
    }

    private AuthenticateUserDTO authenticateUserDTO() {
        return new AuthenticateUserDTO("test@test.com","Test@123");
    }
}
