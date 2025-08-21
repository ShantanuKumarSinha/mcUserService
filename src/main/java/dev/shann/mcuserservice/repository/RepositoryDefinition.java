package dev.shann.mcuserservice.repository;

import dev.shann.mcuserservice.entity.UserEntity;

import java.util.Optional;

@org.springframework.data.repository.RepositoryDefinition(domainClass = UserEntity.class, idClass = Long.class)
public interface RepositoryDefinition<U, L extends Number> {
    UserEntity save(UserEntity user);

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByEmail(String email);

}
