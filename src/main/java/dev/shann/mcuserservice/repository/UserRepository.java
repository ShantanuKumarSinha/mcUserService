package dev.shann.mcuserservice.repository;

import dev.shann.mcuserservice.model.Users;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Function;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Override
    <S extends Users> S save(S entity);

    @Override
    Optional<Users> findById(Long id);

    @Override
    <S extends Users, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);

    Optional<Users> findByEmail(String email);
}
