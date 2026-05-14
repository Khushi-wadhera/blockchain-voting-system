package com.voting.system.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voting.system.blockchain.Block;
import com.voting.system.blockchain.BlockchainService;
import com.voting.system.entity.Candidate;
import com.voting.system.service.CandidateService;
import com.voting.system.service.VoterService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminDashboardController {

    @Autowired
    private VoterService voterService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private BlockchainService blockchainService;

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardStats() {

        Map<String, Object> stats = new HashMap<>();

        // Total voters
        int totalVoters = voterService
                .getAllVoters()
                .size();

        // Total candidates
        int totalCandidates = candidateService
                .getAllCandidates()
                .size();

        // Blockchain data
        List<Block> blockchain =
                blockchainService.getBlockchain();

        int blockchainSize =
                blockchain.size();

        // Total votes
        int totalVotes =
                candidateService
                .getAllCandidates()
                .stream()
                .mapToInt(Candidate::getVotes)
                .sum();

        // Candidate results
        List<Candidate> candidates =
                candidateService.getAllCandidates();

        // Dashboard response
        stats.put("admin", "Election Commission");

        stats.put("systemStatus", "ACTIVE");

        stats.put("electionStatus", "Voting In Progress");

        stats.put("serverTime",
                LocalDateTime.now());

        stats.put("totalVoters",
                totalVoters);

        stats.put("totalCandidates",
                totalCandidates);

        stats.put("totalVotes",
                totalVotes);

        stats.put("blockchainSize",
                blockchainSize);

        stats.put("blockchainValid",
                true);

        stats.put("candidateResults",
                candidates);

        return stats;
    }
}