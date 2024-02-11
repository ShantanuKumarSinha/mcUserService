package dev.shann.mcuserservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;


@Entity
@Getter
@Setter
@ToString
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String email;
    String password;
}
