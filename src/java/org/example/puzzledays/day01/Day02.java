package org.example.puzzledays.day01;

import org.example.utils.FileScanner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day02 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> input = FileScanner.getPuzzleInput(2, false);

        partOne(input);
        partTwo(input);
    }

    private static void partOne(List<String> input) {
        long answer = 0;
        for (String line : input) {
            List<Integer> split = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).boxed().toList();
            boolean allAsc = checker(split, true);
            boolean allDesc = checker(split, false);
            if (allAsc || allDesc) {
                answer++;
            }
        }
        System.out.println("Answer to part one: " + answer);
    }

    private static void partTwo(List<String> input) {
        long answer = 0;
        for (String line : input) {
            List<Integer> split = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).boxed().toList();
            boolean allAsc = checkerWithDampener(split, true);
            boolean allDesc = checkerWithDampener(split, false);
            if (allAsc || allDesc) {
                answer++;
            }
        }
        System.out.println("Answer to part two: " + answer);
    }

    private static boolean checkerWithDampener(List<Integer> entry, boolean asc) {
        for (int i = 0; i < entry.size() - 1; i++) {
            var difference = Math.abs(entry.get(i) - entry.get(i + 1));
            boolean compare = asc ? entry.get(i) < entry.get(i + 1) : entry.get(i) > entry.get(i + 1);
            boolean isSafe = (difference >= 1 && difference <= 3);

            if (!(compare && isSafe)) {
                List<Integer> withoutCurrent = new ArrayList<>(entry), withoutNext = new ArrayList<>(entry);
                withoutCurrent.remove(i);
                if (i < entry.size() - 1) {
                    withoutNext.remove(i + 1);
                }
                return checker(withoutCurrent, asc) || checker(withoutNext, asc);
            }
        }
        return true;
    }

    private static boolean checker(List<Integer> entry, boolean asc) {
        return IntStream
                .range(0, entry.size() - 1)
                .allMatch(i -> {
                    var difference = Math.abs(entry.get(i) - entry.get(i + 1));
                    boolean compare = asc ? entry.get(i) < entry.get(i + 1) : entry.get(i) > entry.get(i + 1);
                    return compare && (difference >= 1 && difference <= 3);
                });
    }
}
