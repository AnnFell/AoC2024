package org.example.puzzledays.day09;

import org.example.utils.FileScanner;

import java.io.FileNotFoundException;
import java.util.*;

public class Day09 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> input = FileScanner.getPuzzleInput(9, false);
        Day09 puzzle = new Day09();
        puzzle.partOne(List.of(input.get(0).split("")));
        partTwo(input);
    }

    private void partOne(List<String> input) {
        int index = 0;
        int fillerDigit = 0;
        List<Block> memory = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            int digit = Integer.parseInt(input.get(i));

            if (i % 2 == 0) {  // put filled block
                if (digit > 0) {
                    List<Integer> content = new ArrayList<>(Collections.nCopies(digit, fillerDigit));
                    memory.add(new Block(index, digit, content));
                }
                fillerDigit++;

            } else { // put empty block
                if (digit > 0) {
                    memory.add(new Block(index, digit, null));
                }
            }
            index += digit == 0 ? 1 : digit;
        }

        fragment(memory);

        long answer = memory.stream()
                .filter(b -> !b.isEmpty())
                .mapToLong(Block::getChecksumOfBlock)
                .sum();

        // 6428525615898 too high
        System.out.println("Answer to part one: " + answer);
    }

    private static void partTwo(List<String> input) {
        long answer = 0;
        System.out.println("Answer to part two: " + answer);
    }

    private void fragment(List<Block> memory) {
        for (int i = 0; i < memory.size(); i++) {
            Block block = memory.get(i);

            if (block.isEmpty()) {
                while (block.hasFreeSpace()) {
                    Block lastBlock = memory.get(memory.size() - 1);
                    if (lastBlock.equals(block)) {
                        // We're done!
                        return;
                    }
                    if (lastBlock.isEmpty()) {
                        memory.remove(lastBlock);
                        continue;
                    }
                    Integer lastDigit = lastBlock.takeLastItem();
                    block.add(lastDigit);
                }
            }
        }
    }
}
