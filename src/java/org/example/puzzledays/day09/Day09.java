package org.example.puzzledays.day09;

import org.example.utils.FileScanner;

import java.io.FileNotFoundException;
import java.util.*;

public class Day09 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> input = FileScanner.getPuzzleInput(9, true);
        Day09 puzzle = new Day09();
        puzzle.partOne(List.of(input.get(0).split("")));
        partTwo(input);
    }

    private void partOne(List<String> input) {
        int index = 0;
        int contentDigit = 0;
        List<Block> memory = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            // put filled block
            int digit = Integer.parseInt(input.get(i));
            if (digit > 0) {
                List<Integer> content = new ArrayList<>(Collections.nCopies(digit, contentDigit));
                memory.add(new Block(index, digit, content));
            }
            contentDigit++;
            index += digit == 0 ? 1 : digit;

            if (i == input.size() - 1) break;

            // put empty block
            i++;
            int nextDigit = Integer.parseInt(input.get(i));
            if (nextDigit > 0) {
                memory.add(new Block(index, nextDigit, null));
            }
            index += nextDigit == 0 ? 1 : nextDigit;
            if (i == input.size()) break;
        }

        defrag(memory);

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

    private void defrag(List<Block> memory) {
        List<Block> reverseMemory = new ArrayList<>(memory);
        Collections.sort(reverseMemory);

        for (Block block : memory) {
            if (block.isDefragged()) {
                System.out.println("Defrag complete");
                return;
            }
            block.setBeingDefragmented();
            if (block.isEmpty()) {
                // get last digit and add it
                // until block is full
                while (block.hasFreeSpace()) {
                    Integer lastDigit = getLastDigitInFileSystem(reverseMemory);
                    if (lastDigit == null) break;
                    block.add(lastDigit);
                }
            }
            block.setDefragged();
        }
    }

    private Integer getLastDigitInFileSystem(List<Block> reverseMemory) {
        // Get the first block of the reverse list
        // // if reverse memory block is empty, remove it and try next one.
        // // // if being defragmented: we are done, return null
        // // // otherwise, take off last item
        Block reverseBlock = reverseMemory.get(0);

        while (reverseBlock.isEmpty()) {
            reverseBlock.setDefragged();
            reverseMemory.remove(0);
            reverseBlock = reverseMemory.get(0);

        }
        if (reverseBlock.isBeingDefragmented()) return null;
        return reverseBlock.takeLastItem();
    }
}
