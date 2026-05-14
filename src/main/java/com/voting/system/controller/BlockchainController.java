package com.voting.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voting.system.blockchain.Block;
import com.voting.system.blockchain.BlockchainService;

@RestController
@RequestMapping("/blockchain")
@CrossOrigin("*")
public class BlockchainController {

    @Autowired
    private BlockchainService blockchainService;

    // 🔗 GET FULL BLOCKCHAIN
    @GetMapping("/chain")
    public List<Block> getChain() {

        return blockchainService.getBlockchain();
    }

    // ✅ VALIDATE BLOCKCHAIN
    @GetMapping("/validate")
    public String validateChain() {

        boolean valid =
                blockchainService.isChainValid();

        if (valid) {
            return "Blockchain is VALID ✅";
        }

        return "Blockchain is INVALID ❌";
    }

    // ℹ️ BLOCKCHAIN INFO
    @GetMapping("/info")
    public String info() {

        return "Blockchain running with SHA-256 hashing security";
    }
}