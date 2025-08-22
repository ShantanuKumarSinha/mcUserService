package dev.shann.mcuserservice.repository;

import dev.shann.mcuserservice.entity.UserEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<U, L extends Number>
    extends UserRepositoryDefinition<UserEntity, Long> {
    @Override
    UserEntity save(UserEntity user);

    @Override
    Optional<UserEntity> findById(Long id);

}
