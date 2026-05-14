package com.voting.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voting.system.blockchain.Blockchain;
import com.voting.system.entity.Candidate;
import com.voting.system.repository.CandidateRepository;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    // Blockchain instance
    private final Blockchain blockchain = new Blockchain();

    // Add candidate
    public Candidate addCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    // Get all candidates
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    // Delete candidate
    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }

    // Vote for candidate + BLOCKCHAIN INTEGRATION
    public Candidate voteCandidate(Long id) {

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        // 1. Increase vote count
        candidate.setVotes(candidate.getVotes() + 1);
        Candidate updatedCandidate = candidateRepository.save(candidate);

        // 2. Create vote data for blockchain
        String voteData = "VOTE -> Candidate: "
                + candidate.getName()
                + ", Party: "
                + candidate.getParty();

        // 3. Add block to blockchain
        blockchain.addBlock(voteData);

        System.out.println("Blockchain Block Added: " + voteData);

        return updatedCandidate;
    }

    // OPTIONAL: View blockchain (for admin panel later)
    public Blockchain getBlockchain() {
        return blockchain;
    }
}