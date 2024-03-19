package dev.shann.mcuserservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateUserDTO {

    String email;
    String password;

}
