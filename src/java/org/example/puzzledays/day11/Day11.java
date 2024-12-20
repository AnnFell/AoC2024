package org.example.puzzledays.day11;

import org.example.utils.FileScanner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11 {
    public static long total = 0;

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
        for (int i = 0; i < 25; i++) {
            blinked = blink(blinked);
        }
        long answer = blinked.size();
        System.out.println("Answer to part one: " + answer);
    }

    private static void partTwo(List<Long> stones) {
        for (long stone : stones) {
            blink75(stone, 0);
        }
        System.out.println("Answer to part two: " + total);
    }

    public static List<Long> blink(List<Long> stones) {
        List<Long> newList = new ArrayList<>();
        for (long stone : stones) {
            newList.addAll(blinkAtStone(stone));
        }
        return newList;
    }

    public static void blink75(long stone, int iteration) {
        iteration++;
        List<Long> newList = blinkAtStone(stone);
        if (iteration < 75) {
            for (long newStone : newList) {
                blink75(newStone, iteration);
            }
        } else {
            total += newList.size();
        }
    }

    private static List<Long> blinkAtStone(long stone) {
        List<Long> newList = new ArrayList<>();
        if (stone == 0) {
            newList.add(1L);
        } else if (("" + stone).length() % 2 == 0) {
            String v = "" + stone;
            newList.add(Long.parseLong(v.substring(0, v.length() / 2)));
            newList.add(Long.parseLong(v.substring(v.length() / 2)));
        } else {
            newList.add(stone * 2024);
        }
        return newList;
    }
}
