package com.auth.app.repository;

import com.auth.app.entity.AuthorityRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRoleRepository extends JpaRepository<AuthorityRole, Long> {
}
