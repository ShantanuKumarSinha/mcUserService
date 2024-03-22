package dev.shann.mcuserservice.DTO;


import dev.shann.mcuserservice.model.Users;

public record AuthenticateUserDTO(String email , String password) {
}
