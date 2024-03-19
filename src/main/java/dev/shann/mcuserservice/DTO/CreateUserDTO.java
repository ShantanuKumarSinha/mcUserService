package dev.shann.mcuserservice.DTO;


import dev.shann.mcuserservice.model.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    private Users user;
}
