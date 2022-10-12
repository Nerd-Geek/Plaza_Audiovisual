package org.example.repositories;

import org.example.model.Login;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, String> {
    Page<Login> findByUser_id(Pageable pageable, String userId);
    Optional<Login> findByToken(String token);
}
