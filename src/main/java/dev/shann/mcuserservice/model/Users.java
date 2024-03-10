package dev.shann.mcuserservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.IdGeneratorType;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String email;
    String password;
}
