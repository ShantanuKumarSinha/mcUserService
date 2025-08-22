package dev.shann.mcuserservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mc_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String email;
  String password;
}
