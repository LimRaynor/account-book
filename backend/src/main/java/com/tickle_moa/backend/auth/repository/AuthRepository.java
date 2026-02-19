package com.tickle_moa.backend.auth.repository;

import com.tickle_moa.backend.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<RefreshToken, String> {
}
