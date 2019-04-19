package com.remote.doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.remote.doctor.domain.Discussion;

public interface DiscussionRepository extends JpaRepository<Discussion, Integer> {
}
