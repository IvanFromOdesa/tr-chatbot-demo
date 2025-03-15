package dev.ivank.trchatbotdemo.security.auth.dao;

import dev.ivank.trchatbotdemo.security.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
