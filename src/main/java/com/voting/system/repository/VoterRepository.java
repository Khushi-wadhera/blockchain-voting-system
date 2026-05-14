package com.voting.system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.voting.system.entity.Voter;

@Repository
public interface VoterRepository
        extends JpaRepository<Voter, Long> {

    Optional<Voter> findByEmail(String email);
}