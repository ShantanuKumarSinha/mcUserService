package dev.shann.mcuserservice.model;


import lombok.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    Long id;
    String email;
    String password;
}
