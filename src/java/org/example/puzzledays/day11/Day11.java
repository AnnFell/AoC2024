package org.example.puzzledays.day11;

import org.example.utils.FileScanner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> input = FileScanner.getPuzzleInput(11, false);
        List<Long> stones = Arrays.stream(input.get(0).split(" ")).mapToLong(Long::parseLong).boxed().toList();
        System.out.println(stones);

        // 0? => replace by 1
        // even number of digits? => split in two. Don't keep leading zeroes
        // else? => multiply by 2024

        partOne(stones);
        partTwo(stones);
    }

    private static void partOne(List<Long> stones) {
        List<Long> blinked = new ArrayList<>(stones);
        for (int i = 0; i < 75; i++) {
            blinked = blink(blinked);
//            System.out.println(blinked);
        }
        long answer = blinked.size();
        System.out.println("Answer to part one: " + answer);
    }

    private static void partTwo(List<Long> stones) {
        long answer = 0;
        System.out.println("Answer to part two: " + answer);
    }

    private static List<Long> blink(List<Long> stones) {
        List<Long> newList = new ArrayList<>();
        for (int i = 0; i < stones.size(); i++) {
            if (stones.get(i) == 0) {
                newList.add(1L);
            } else if (("" + stones.get(i)).length() % 2 == 0) {
                String v = "" + stones.get(i);
                newList.add(Long.parseLong(v.substring(0, v.length() / 2)));
                newList.add(Long.parseLong(v.substring(v.length() / 2)));
            } else {
                newList.add(stones.get(i) * 2024);
            }
        }
        return newList;
    }
}
