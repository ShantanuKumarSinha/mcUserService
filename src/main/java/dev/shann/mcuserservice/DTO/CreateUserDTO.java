package dev.shann.mcuserservice.DTO;


import dev.shann.mcuserservice.model.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    private Users user;
}
