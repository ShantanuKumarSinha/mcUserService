package dev.shann.mcuserservice;

import dev.shann.mcuserservice.DTO.AuthenticateUserDTO;
import dev.shann.mcuserservice.DTO.CreateUserDTO;
import dev.shann.mcuserservice.controller.UserController;
import dev.shann.mcuserservice.model.Users;
import dev.shann.mcuserservice.service.UserService;
import jakarta.persistence.Access;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    void shouldValidateAuthenticateUser() throws Exception {
        //when
        when(userService.authenticate(getAuthenticationDao())).thenReturn(getUser());


        var result = userController.authenticate(getAuthenticationDao());

        assertThat(result).isEqualTo(new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK));
    }

    @Test
    void shouldNotValidateAuthenticateUser() throws Exception {
        //when
        when(userService.authenticate(getAuthenticationDao())).thenReturn(null);


        var result = userController.authenticate(getAuthenticationDao());

        assertThat(result).isEqualTo(new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK));
    }

    @Test
    void shouldCreateNewUser(){
        when(userService.createUser(createUserDTO())).thenReturn(getUser());
        var result = userController.createUser(createUserDTO());
        assertThat(result).isEqualTo(ResponseEntity.ok().body(getUser()));
    }

    private AuthenticateUserDTO getAuthenticationDao(){
        return new AuthenticateUserDTO("test@test.com","Test@123");
    }
    private Users getUser(){
        return Users.builder().id(1L).email("test@test.com").password("Test@123").build();
    }

    private CreateUserDTO createUserDTO(){
        return new CreateUserDTO(Users.builder().id(1L).email("test@test.com").password("test@123").build());
    }

}
