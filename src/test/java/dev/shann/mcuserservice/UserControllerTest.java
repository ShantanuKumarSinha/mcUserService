package dev.shann.mcuserservice;

import dev.shann.mcuserservice.dto.AuthenticateUserDTO;
import dev.shann.mcuserservice.dto.CreateUserDTO;
import dev.shann.mcuserservice.controller.UserController;
import dev.shann.mcuserservice.model.User;
import dev.shann.mcuserservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    void shouldCreateNewUser(){
        when(userService.createUser(createUserDTO())).thenReturn(getUser());
        var response = userController.createUser(createUserDTO());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.hasBody()).isTrue();
        assertThat(response.getBody()).isEqualTo(getUser());
    }

    @Test
    void shouldNotBeAbleToCreateNewUser(){
        when(userService.createUser(createUserDTO())).thenReturn(any(User.class));
        var response = userController.createUser(createUserDTO());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.hasBody()).isFalse();
        assertThat(response.getBody()).isNull();
    }

    @Test
    void shouldBeAbleToAuthenticateUser(){
        when(userService.authenticate(authenticateUserDTO())).thenReturn(getUser());
        var response = userController.authenticate(authenticateUserDTO());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isTrue();
    }
    @Test
    void shouldNotBeAbleToAuthenticateUser(){
        when(userService.authenticate(authenticateUserDTO())).thenReturn(any(User.class));
        var response = userController.authenticate(authenticateUserDTO());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isFalse();
    }

    private User getUser() {
        return User.builder().id(1L).email("shan.raj93@gmail.com").password("Test@123").build();
    }
    private CreateUserDTO createUserDTO() {
        return  new CreateUserDTO (User.builder()
                .email("shan.raj93@gmail.com")
                .password("Test@123")
                .build());
    }

    private AuthenticateUserDTO authenticateUserDTO() {
        return  new AuthenticateUserDTO("test@test.com","Testing@123");
    }
}
