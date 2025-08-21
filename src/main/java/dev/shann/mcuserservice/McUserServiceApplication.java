package dev.shann.mcuserservice;

import dev.shann.mcuserservice.interceptors.LoggerInterceptor;
import dev.shann.mcuserservice.repository.UserRepository;
import dev.shann.mcuserservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableDiscoveryClient
public class McUserServiceApplication implements WebMvcConfigurer {

  public static void main(String[] args) {
    SpringApplication.run(McUserServiceApplication.class, args);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(new LoggerInterceptor())
        .addPathPatterns("/**")
        .excludePathPatterns("/actuator/**", "/v3/api-docs/**", "/swagger-ui/**");
    ;
  }

  @Bean
  public UserService userService(UserRepository userRepository) {
    return new UserService(userRepository, new ModelMapper());
  }
}
