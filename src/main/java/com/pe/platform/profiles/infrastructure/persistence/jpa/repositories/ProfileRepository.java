package com.pe.platform.profiles.infrastructure.persistence.jpa.repositories;

import com.pe.platform.profiles.domain.model.aggregates.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByEmail(String email);
    List<Profile> findByRoleValue(String role);
}
