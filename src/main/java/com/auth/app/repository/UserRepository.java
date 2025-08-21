package com.auth.app.repository;

import com.auth.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    boolean existsByPhoneNumber(Long phoneNumber);

    List<User> findAllByUsernameIsNot(String username);
}
