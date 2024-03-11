package dev.shann.mcuserservice;

import dev.shann.mcuserservice.DTO.AuthenticateUserDTO;
import dev.shann.mcuserservice.DTO.CreateUserDTO;
import dev.shann.mcuserservice.exceptions.EmailNotFoundException;
import dev.shann.mcuserservice.model.Users;
import dev.shann.mcuserservice.repository.UserRepository;
import dev.shann.mcuserservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class McUserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    void shouldCreateNewUser() {
        when(userRepository.save(getUser())).thenReturn(getUser().toBuilder().id(1L).build());
        var user = userService.createUser(createUserDTO());
        assertThat(user).isNotNull();
        assertThat(user).isEqualTo(getUser().toBuilder().id(1L).build());
    }

    @Test
    void shouldNotBeAbleToCreateNewUser() {
        when(userRepository.save(getUser())).thenReturn(any(Users.class));
        var user = userService.createUser(createUserDTO());
        assertThat(user).isNull();
    }

    @Test
    void shouldAuthenticate() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(getUser().toBuilder().id(1L).build()));
        var user = userService.authenticate(authenticateUserDTO());
        assertThat(user).isNotNull();
        assertThat(user).isEqualTo(getUser().toBuilder().id(1L).build());
    }

    @Test
    void shouldNotBeAbleToAuthenticate() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(any(Users.class)));
        var exception = assertThrows(EmailNotFoundException.class, () -> userService.authenticate(authenticateUserDTO()));

        assertThat(exception).isNotNull().isInstanceOf(EmailNotFoundException.class);
    }

    private Users getUser() {
        return Users.builder().email("shan.raj93@gmail.com").password("Test@123").build();
    }

    private CreateUserDTO createUserDTO() {
        return new CreateUserDTO(getUser());
    }

    private AuthenticateUserDTO authenticateUserDTO() {
        return new AuthenticateUserDTO("test@test.com", "Testing@123");
    }
}
