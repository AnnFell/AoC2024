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
        //Loop over input
        int index = 0;
        int contentDigit = 0;
        Map<Integer, Block> memory = new HashMap<>();
        for (int i = 0; i < input.size()-1; i++) {
            // put filled block
            int digit = Integer.parseInt(input.get(i));
            List<Integer> content = new ArrayList<>(Collections.nCopies(digit, contentDigit));
            memory.putIfAbsent(index, new Block(index, digit, content));
            contentDigit++;
            index += digit;


            // put empty block
            int nextDigit = Integer.parseInt(input.get(++i));
            memory.putIfAbsent(index, new Block(index, nextDigit, null));
            index += nextDigit;
            if(i == input.size()) break;
        }

        long answer = 0;
        System.out.println("Answer to part one: " + answer);
    }

    private static void partTwo(List<String> input) {
        long answer = 0;
        System.out.println("Answer to part two: " + answer);
    }
}
