package com.voting.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voting.system.entity.Candidate;
import com.voting.system.service.CandidateService;

@RestController
@RequestMapping("/candidates")
@CrossOrigin("*")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    // 🟢 ADD CANDIDATE (ADMIN)
    @PostMapping("/add")
    public Candidate addCandidate(@RequestBody Candidate candidate) {
        return candidateService.addCandidate(candidate);
    }

    // 🟢 GET ALL CANDIDATES (PUBLIC OR JWT PROTECTED)
    @GetMapping
    public List<Candidate> getAllCandidates() {
        System.out.println("✔ GET /candidates HIT");
        return candidateService.getAllCandidates();
    }

    // 🔴 DELETE CANDIDATE (ADMIN)
    @DeleteMapping("/delete/{id}")
    public String deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return "Candidate deleted successfully";
    }

    // 🟡 VOTE CANDIDATE (VOTER)
    @PostMapping("/vote/{id}")
    public String voteCandidate(@PathVariable Long id) {

        Candidate candidate = candidateService.voteCandidate(id);

        return "Vote cast successfully for " + candidate.getName();
    }
}