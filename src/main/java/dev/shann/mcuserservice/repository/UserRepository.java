package dev.shann.mcuserservice.repository;

import dev.shann.mcuserservice.entity.UserEntity;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long> {

  Optional<UserEntity> findByEmail(String email);
}
