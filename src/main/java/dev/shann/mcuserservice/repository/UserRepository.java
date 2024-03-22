package dev.shann.mcuserservice.repository;

import dev.shann.mcuserservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Override
    UserEntity save(UserEntity users);

    @Override
    Optional<UserEntity> findById(Long id);


    Optional<UserEntity> findByEmail(String email);
}
