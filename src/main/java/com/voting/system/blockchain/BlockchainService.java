package com.voting.system.blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BlockchainService {

    private final List<Block> chain = new ArrayList<>();

    // Constructor
    public BlockchainService() {
        chain.add(createGenesisBlock());
    }

    // Genesis Block
    private Block createGenesisBlock() {

        String data = "Genesis Block";

        String hash = generateHash(
                "0",
                data
        );

        return new Block(
                0,
                LocalDateTime.now(),
                data,
                "0",
                hash
        );
    }

    // Get latest block
    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    // Add new block
    public void addBlock(String data) {

        Block previousBlock = getLatestBlock();

        String previousHash =
                previousBlock.getHash();

        String newHash =
                generateHash(previousHash, data);

        Block newBlock = new Block(
                previousBlock.getIndex() + 1,
                LocalDateTime.now(),
                data,
                previousHash,
                newHash
        );

        chain.add(newBlock);
    }

    // Get complete blockchain
    public List<Block> getBlockchain() {
        return chain;
    }

    // Validate blockchain
    public boolean isChainValid() {

        for (int i = 1; i < chain.size(); i++) {

            Block currentBlock = chain.get(i);

            Block previousBlock = chain.get(i - 1);

            // Recalculate current hash
            String recalculatedHash =
                    generateHash(
                            currentBlock.getPreviousHash(),
                            currentBlock.getData()
                    );

            // Validate current hash
            if (!currentBlock.getHash()
                    .equals(recalculatedHash)) {

                return false;
            }

            // Validate previous hash link
            if (!currentBlock.getPreviousHash()
                    .equals(previousBlock.getHash())) {

                return false;
            }
        }

        return true;
    }

    // SHA-256 Hash Generator
    private String generateHash(
            String previousHash,
            String data
    ) {

        try {

            String text =
                    previousHash + data;

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hashBytes =
                    digest.digest(
                            text.getBytes(StandardCharsets.UTF_8)
                    );

            StringBuilder hexString =
                    new StringBuilder();

            for (byte b : hashBytes) {

                String hex =
                        Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error generating hash",
                    e
            );
        }
    }
}