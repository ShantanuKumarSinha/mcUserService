package dev.shann.mcuserservice;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shann.mcuserservice.dto.AuthenticateUserDTO;
import dev.shann.mcuserservice.dto.CreateUserDTO;
import dev.shann.mcuserservice.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(classes = McUserServiceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(("classpath:application-test.properties"))
@ActiveProfiles("test")
@AutoConfigureRestDocs
class McUserServiceApplicationTests {

  @Autowired MockMvc mockMvc;

  @Autowired ObjectMapper objectMapper;

  @Test
  void shouldCreateUser() throws Exception {
    var jsonStringify =
        objectMapper.writeValueAsString(
            new CreateUserDTO(User.builder().email("test@test.com").password("Test@123").build()));
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("http://user-service/users")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonStringify);
    var result =
        mockMvc
            .perform(requestBuilder)
            .andExpect(status().isCreated())
            .andDo(document("create-user"))
            .andReturn();

    var user = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);

    assertSoftly(
        softly ->
            softly
                .assertThat(user)
                .extracting(User::getId, User::getEmail, User::getPassword)
                .contains(1L, "test@test.com", "Test@123"));
  }

  @Test
  @SqlGroup({
    @Sql(
        scripts = "classpath:test-data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  })
  void shouldNotCreateUserIfUserAlreadyExists() throws Exception {
    var jsonStringify =
        objectMapper.writeValueAsString(
            new CreateUserDTO(
                User.builder().email("test2@test.com").password("Test2@123").build()));
    var requestBuilder =
        MockMvcRequestBuilders.post("http://user-service/users")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonStringify);
    var result =
        mockMvc
            .perform(requestBuilder)
            .andExpect(status().isConflict())
            .andDo(document("can't-create-existing-user"))
            .andReturn();
    var responseBody = result.getResponse().getContentAsString();
    assertSoftly(softly -> softly.assertThat(responseBody).isEqualTo("User already exists"));
  }

  @Test
  @SqlGroup({
    // @Sql(scripts = "file:src/test/resources/test-data.sql", executionPhase =
    // Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(
        scripts = "classpath:test-data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
  })
  void shouldAuthenticateUser() throws Exception {

    var jsonStringify =
        objectMapper.writeValueAsString(new AuthenticateUserDTO("test2@test.com", "Test2@123"));

    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("http://user-service/users/authenticate")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonStringify);
    var result =
        mockMvc
            .perform(requestBuilder)
            .andExpect(status().isOk())
            .andDo(document("authenticate-user-success"))
            .andReturn();

    var responseBody =
        objectMapper.readValue(result.getResponse().getContentAsString(), Boolean.class);

    assertSoftly(softly -> softly.assertThat(responseBody).isTrue());
  }

  @Test
  void shouldNotBeAbleToAuthenticateUser() throws Exception {

    var jsonStringify =
        objectMapper.writeValueAsString(new AuthenticateUserDTO("test3@test.com", "Test3@123"));

    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("http://user-service/users/authenticate")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonStringify);
    var result =
        mockMvc
            .perform(requestBuilder)
            .andExpect(status().isNotFound())
            .andDo(document("authenticate-user-fail"))
            .andReturn();

    var responseBody = result.getResponse().getContentAsString();

    assertSoftly(softly -> softly.assertThat(responseBody).isEqualTo("Email Id Not Found"));
  }
}
