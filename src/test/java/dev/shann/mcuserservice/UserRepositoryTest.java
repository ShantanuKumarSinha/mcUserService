package dev.shann.mcuserservice;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import dev.shann.mcuserservice.entity.UserEntity;
import dev.shann.mcuserservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource(locations = "file:src/test/resources/application-test.properties")
class UserRepositoryTest {

  @Autowired private UserRepository userRepository;

  private UserEntity userEntity;

  @BeforeEach
  public void setUp() {
    userEntity = UserEntity.builder().email("test@test.com").password("Test@123").build();
    userEntity = userRepository.save(userEntity);
  }
  @Test
  void shouldCreateNewUser() {
    assertThat(userEntity).isNotNull();
    assertThat(userEntity.getId()).isPositive();
  }

  @Test
  void shouldAuthenticateUser(){
    var user = userRepository.findByEmail("test@test.com");
    assertThat(user).isPresent();
    assertThat(user.get())
            .isNotNull()
            .extracting(UserEntity::getId, UserEntity::getEmail, UserEntity::getPassword)
            .contains(1L, "test@test.com","Test@123");

  }

  @Test
  void shouldNotAuthenticateUser(){
    var user = userRepository.findByEmail("test2@test.com");
    assertThat(user).isNotPresent();
    assertThat(user).isEmpty();
  }
}
