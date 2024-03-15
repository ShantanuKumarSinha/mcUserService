package dev.shann.mcuserservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shann.mcuserservice.DTO.AuthenticateUserDTO;
import dev.shann.mcuserservice.DTO.CreateUserDTO;
import dev.shann.mcuserservice.controller.UserController;
import dev.shann.mcuserservice.model.Users;
import dev.shann.mcuserservice.repository.UserRepository;
import dev.shann.mcuserservice.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes= McuserserviceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "file:src/test/java/resources/application-test.properties")
@ActiveProfiles("test")
class McuserserviceApplicationTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UserController userController;

    @BeforeAll
    static void setup(){
        McuserserviceApplicationTests mcuserserviceApplicationTests = new McuserserviceApplicationTests();
        ClassLoader classLoader = mcuserserviceApplicationTests.getClass().getClassLoader();
        String sqlFile = "test-data.sql";
        classLoader.getResourceAsStream(sqlFile);
    }

    @Test
    void shouldCreateUser() throws Exception{
        var jsonStringify = objectMapper.writeValueAsString(
                new CreateUserDTO(Users.builder().email("test@test.com").password("Test@123").build()));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("http://user-service/users").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonStringify);
        var result = mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();

        var user = objectMapper.readValue(result.getResponse().getContentAsString(),Users.class);

        assertSoftly(softly -> softly.assertThat(user)
                .extracting(Users::getId,Users::getEmail,Users::getPassword)
                .contains(1L,"test@test.com","Test@123"));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void shouldAuthenticateUser() throws Exception {

        var jsonStringify = objectMapper.writeValueAsString(
                new AuthenticateUserDTO("test2@test.com","Test2@123"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("http://user-service/users/authenticate")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonStringify);
        var result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        var responseBody = objectMapper.readValue(result.getResponse().getContentAsString(),Boolean.class);

        assertSoftly(softly -> softly.assertThat(responseBody).isTrue());
    }

    @Test
    void shouldNotBeAbleToAuthenticateUser() throws Exception {

        var jsonStringify = objectMapper.writeValueAsString(
                new AuthenticateUserDTO("test3@test.com","Test3@123"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("http://user-service/users/authenticate")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonStringify);
        var result = mockMvc.perform(requestBuilder).andExpect(status().isNotFound()).andReturn();

        var responseBody = result.getResponse().getContentAsString();

        assertSoftly(softly -> softly.assertThat(responseBody).isEqualTo("Email Id Not Found"));
    }
}
