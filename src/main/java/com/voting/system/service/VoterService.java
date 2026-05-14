package com.voting.system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.voting.system.entity.Candidate;
import com.voting.system.entity.Voter;
import com.voting.system.repository.CandidateRepository;
import com.voting.system.repository.VoterRepository;

@Service
public class VoterService {

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    // REGISTER
    public Voter registerVoter(Voter voter) {

        voter.setPassword(
                passwordEncoder.encode(voter.getPassword())
        );

        voter.setHasVoted(false);

        return voterRepository.save(voter);
    }

    // GET ALL
    public List<Voter> getAllVoters() {
        return voterRepository.findAll();
    }

    // GET BY ID
    public Optional<Voter> getVoterById(Long id) {
        return voterRepository.findById(id);
    }

    // DELETE
    public void deleteVoter(Long id) {
        voterRepository.deleteById(id);
    }

    // ✅ LOGIN (FIXED FOR FRONTEND)
    public Voter loginVoter(String email, String password) {

        Optional<Voter> voterOptional =
                voterRepository.findByEmail(email);

        if (voterOptional.isPresent()) {

            Voter voter = voterOptional.get();

            if (passwordEncoder.matches(password, voter.getPassword())) {
                return voter;
            }
        }

        return null; // IMPORTANT FIX (DO NOT THROW ERROR)
    }

    // VOTE
    public String castVote(Long voterId, Long candidateId) {

        Voter voter = voterRepository.findById(voterId)
                .orElseThrow(() -> new RuntimeException("Voter Not Found"));

        if (voter.isHasVoted()) {
            return "You Have Already Voted";
        }

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate Not Found"));

        candidate.setVotes(candidate.getVotes() + 1);
        candidateRepository.save(candidate);

        voter.setHasVoted(true);
        voterRepository.save(voter);

        return "Vote Cast Successfully for " + candidate.getName();
    }
}