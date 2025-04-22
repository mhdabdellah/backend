package com.mhdabdellahi.backend.repository;

import com.mhdabdellahi.backend.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
