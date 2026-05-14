package com.voting.system.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voting.system.blockchain.BlockchainService;
import com.voting.system.entity.Voter;
import com.voting.system.service.VoterService;

@RestController
@RequestMapping("/voters")
@CrossOrigin("*")
public class VoterController {

    @Autowired
    private VoterService voterService;

    @Autowired
    private BlockchainService blockchainService;

    // REGISTER
    @PostMapping
    public Voter registerVoter(@RequestBody Voter voter) {

        return voterService.registerVoter(voter);
    }

    // GET ALL
    @GetMapping
    public List<Voter> getAllVoters() {
        return voterService.getAllVoters();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Optional<Voter> getVoterById(@PathVariable Long id) {
        return voterService.getVoterById(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteVoter(@PathVariable Long id) {

        voterService.deleteVoter(id);

        blockchainService.addBlock("VOTER_DELETED: ID " + id);

        return "Voter Deleted Successfully";
    }

    // VOTE (FIXED: STRICT ID USAGE)
    @PostMapping("/vote")
    public String castVote(@RequestBody VoteRequest request) {

        if (request.getVoterId() == null || request.getCandidateId() == null) {
            return "Invalid vote request";
        }

        String result = voterService.castVote(
                request.getVoterId(),
                request.getCandidateId()
        );

        blockchainService.addBlock(
                "VOTE_CASTED: Voter " + request.getVoterId() +
                " → Candidate " + request.getCandidateId()
        );

        return result;
    }

    // LOGIN (FIXED: RETURN ONLY SAFE DATA)
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        Voter voter = voterService.loginVoter(
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok(
                new LoginResponse(voter.getId(), voter.getName(), voter.getEmail())
        );
    }

    // ================= DTOs =================

    public static class VoteRequest {
        private Long voterId;
        private Long candidateId;

        public Long getVoterId() { return voterId; }
        public void setVoterId(Long voterId) { this.voterId = voterId; }

        public Long getCandidateId() { return candidateId; }
        public void setCandidateId(Long candidateId) { this.candidateId = candidateId; }
    }

    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    // 🔥 IMPORTANT FIX (NO PASSWORD, NO CONFUSION)
    public static class LoginResponse {
        private Long id;
        private String name;
        private String email;

        public LoginResponse(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public Long getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
    }
}