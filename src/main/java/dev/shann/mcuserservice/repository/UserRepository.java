package dev.shann.mcuserservice.repository;

import dev.shann.mcuserservice.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long> {

  Optional<UserEntity> findByEmail(String email);
}
