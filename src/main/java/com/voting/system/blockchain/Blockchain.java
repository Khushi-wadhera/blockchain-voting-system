package com.voting.system.blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private final List<Block> chain = new ArrayList<>();

    public Blockchain() {
        chain.add(createGenesisBlock());
    }

    private Block createGenesisBlock() {

        String hash = calculateHash("0", "Genesis Block");

        return new Block(
                0,
                LocalDateTime.now(),
                "Genesis Block",
                "0",
                hash
        );
    }

    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public void addBlock(String data) {

        Block previous = getLatestBlock();

        String hash = calculateHash(previous.getHash(), data);

        Block newBlock = new Block(
                previous.getIndex() + 1,
                LocalDateTime.now(),
                data,
                previous.getHash(),
                hash
        );

        chain.add(newBlock);
    }

    public List<Block> getChain() {
        return chain;
    }

    // VALIDATION (IMPORTANT FOR VIVA)
    public boolean isChainValid() {

        for (int i = 1; i < chain.size(); i++) {

            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

            String recalculatedHash = calculateHash(
                    current.getPreviousHash(),
                    current.getData()
            );

            if (!current.getHash().equals(recalculatedHash)) {
                return false;
            }

            if (!current.getPreviousHash().equals(previous.getHash())) {
                return false;
            }
        }

        return true;
    }

    // SHA-256 HASHING
    private String calculateHash(String previousHash, String data) {

        try {

            String text = previousHash + data + LocalDateTime.now();

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = digest.digest(text.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}