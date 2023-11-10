package io.collective.basic;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class Blockchain {
    private List<Block> chain;

    public Blockchain() {
        this.chain = new ArrayList<>();
    }

    public boolean isEmpty() {
        return this.chain.isEmpty();
    }

    public void add(Block block) {
        this.chain.add(block);
    }

    public int size() {
        return this.chain.size();
    }



    public boolean isValid() throws NoSuchAlgorithmException {
        if (this.isEmpty()) {
            return true;
        }

        Block previousBlock = null;
        for (Block currentBlock : this.chain) {
            // Recalculate the hash and check if it matches the stored hash
            String recalculatedHash = currentBlock.calculateHash(); // Now public in Block
            if (!recalculatedHash.equals(currentBlock.getHash())) {
                return false;
            }

            // Check if the block is mined
            if (!isMined(currentBlock)) {
                return false;
            }

            // Check previous hash consistency for all blocks except the first one
            if (previousBlock != null && !currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }

            previousBlock = currentBlock;
        }

        return true;
    }


    /// Supporting functions that you'll need.

    public static Block mine(Block block) throws NoSuchAlgorithmException {
        Block mined = new Block(block.getPreviousHash(), block.getTimestamp(), block.getNonce());
        while (!isMined(mined)) {
            mined = new Block(mined.getPreviousHash(), mined.getTimestamp(), mined.getNonce() + 1);
        }
        return mined;
    }

    public static boolean isMined(Block minedBlock) {
        return minedBlock.getHash().startsWith("00");
    }

}