package dev.shann.mcuserservice;

import dev.shann.mcuserservice.DTO.AuthenticateUserDTO;
import dev.shann.mcuserservice.DTO.CreateUserDTO;
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
import static org.mockito.Mockito.when;

   @ExtendWith(MockitoExtension.class)

    class McUserServiceTest {
        String email = "test@test.com";

        @InjectMocks
        private UserService userService;

        @Mock
        private UserRepository userRepository;



        @Test
        void shouldTestAuthentication(){
            //when
            when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(getUser()));
            var user =userService.authenticate(getAuthenticationDTO());
            assertThat(user).isNotNull()
                    .extracting(Users::getId, Users::getEmail, Users::getPassword).contains(getUser().getId(), getUser().getEmail(), getUser().getPassword());

        }

        @Test
        void shouldThrowRunTimeExceptionWhenUserNotFound(){
            when(userRepository.findByEmail(email)).thenReturn(null);
            var exception = assertThrows(RuntimeException.class, () -> userService.authenticate(getAuthenticationDTO()));
        }


        @Test
        void shouldCreateNewProduct(){
            //doReturn(getUser()).when(userRepository.save(createUserDTO().users()));
            when(userRepository.save(createUserDTO().users())).thenReturn(getUser());
            var user = userService.createUser(createUserDTO());
            assertThat(user).isNotNull().extracting( Users::getEmail, Users::getPassword)
                    .contains(getUser().getId(), getUser().getEmail(), getUser().getPassword());

        }

        AuthenticateUserDTO getAuthenticationDTO(){
            return new AuthenticateUserDTO("test@test.com", "Test@123");
        }

        Users getUser(){
            return Users.builder().id(1L).email(email).password("Test@123").build();
        }

        CreateUserDTO createUserDTO(){
            return new CreateUserDTO(Users.builder().id(1L).email("test@test.com").password("test@123").build());
        }

    }

