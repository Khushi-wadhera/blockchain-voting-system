package com.voting.system.blockchain;

import java.time.LocalDateTime;

public class Block {

    private final int index;
    private final LocalDateTime timestamp;
    private final String data;
    private final String previousHash;
    private final String hash;

    public Block(int index, LocalDateTime timestamp, String data, String previousHash, String hash) {
        this.index = index;
        this.timestamp = timestamp;
        this.data = data;
        this.previousHash = previousHash;
        this.hash = hash;
    }

    public int getIndex() {
        return index;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getData() {
        return data;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }
}