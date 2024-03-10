package dev.shann.mcuserservice;

import dev.shann.mcuserservice.DTO.CreateUserDTO;
import dev.shann.mcuserservice.model.Users;
import dev.shann.mcuserservice.repository.UserRepository;
import dev.shann.mcuserservice.service.UserService;
import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class McUserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    void shouldCreateNewUser(){
        when(userRepository.save(getUser())).thenReturn(getUser().toBuilder().id(1L).build());
        var user = userService.createUser(createUserDTO());
        assertThat(user).isNotNull();
        assertThat(user).isEqualTo(getUser().toBuilder().id(1L).build());
    }

    private Users getUser(){
        return Users.builder().email("shan.raj93@gmail.com").password("Test@123").build();
    }

    private CreateUserDTO createUserDTO(){
        return new CreateUserDTO(getUser());
    }
}
