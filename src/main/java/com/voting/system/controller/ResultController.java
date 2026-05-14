package com.voting.system.controller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voting.system.entity.Candidate;
import com.voting.system.service.CandidateService;

@RestController
@RequestMapping("/results")
@CrossOrigin("*")
public class ResultController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping
    public Map<String, Object> getResults() {

        List<Candidate> candidates = candidateService.getAllCandidates();

        // sort by votes (for leaderboard display)
        candidates.sort(Comparator.comparingInt(Candidate::getVotes).reversed());

        int totalVotes = candidates.stream()
                .mapToInt(Candidate::getVotes)
                .sum();

        // ===============================
        // 📊 LEADERBOARD (candidate-wise)
        // ===============================
        List<Map<String, Object>> leaderboard = candidates.stream()
                .map(c -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", c.getId());
                    map.put("name", c.getName());
                    map.put("party", c.getParty());
                    map.put("votes", c.getVotes());

                    double percentage = (totalVotes == 0)
                            ? 0.0
                            : (c.getVotes() * 100.0 / totalVotes);

                    map.put("percentage",
                            Math.round(percentage * 100.0) / 100.0
                    );

                    return map;
                })
                .toList();

        // ===============================
        // 🏆 PARTY-WISE WINNER LOGIC (NEW)
        // ===============================
        Map<String, Integer> partyVotes = new HashMap<>();

        for (Candidate c : candidates) {
            partyVotes.put(
                    c.getParty(),
                    partyVotes.getOrDefault(c.getParty(), 0) + c.getVotes()
            );
        }

        String winnerParty = null;
        int maxVotes = 0;

        for (Map.Entry<String, Integer> entry : partyVotes.entrySet()) {
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                winnerParty = entry.getKey();
            }
        }

        // ===============================
        // RESPONSE
        // ===============================
        Map<String, Object> response = new HashMap<>();

        response.put("totalVotes", totalVotes);
        response.put("leaderboard", leaderboard);
        response.put("electionStatus", "LIVE");

        // instead of candidate winner → PARTY winner
        response.put("partyWinner", winnerParty);
        response.put("partyWinnerVotes", maxVotes);

        return response;
    }
}