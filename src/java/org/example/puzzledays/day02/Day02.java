package org.example.puzzledays.day02;

import org.example.utils.FileScanner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day02 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> input = FileScanner.getPuzzleInput(2, false);
        List<List<Integer>> entries = input.stream()
                .map(line -> Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).boxed().toList())
                .toList();

        System.out.println("Answer to part one: " + solve(entries, false));
        System.out.println("Answer to part two: " + solve(entries, true));
    }

    private static long solve(List<List<Integer>> entries, boolean withDampener) {
        return entries.stream().filter(entry -> checker(entry, true, withDampener) || checker(entry, false, withDampener)).count();
    }

    private static boolean checker(List<Integer> entry, boolean asc, boolean dampenerOn) {
        return IntStream
                .range(0, entry.size() - 1)
                .allMatch(i -> {
                    var difference = Math.abs(entry.get(i) - entry.get(i + 1));
                    boolean isSafeStep = (difference >= 1 && difference <= 3);
                    boolean compare = asc ? entry.get(i) < entry.get(i + 1) : entry.get(i) > entry.get(i + 1);

                    if (dampenerOn && !(compare && isSafeStep)) {
                        List<Integer> withoutCurrent = new ArrayList<>(entry), withoutNext = new ArrayList<>(entry);
                        withoutCurrent.remove(i);
                        if (i < entry.size() - 1) {
                            withoutNext.remove(i + 1);
                        }
                        return checker(withoutCurrent, asc, false) || checker(withoutNext, asc, false);
                    }

                    return compare && isSafeStep;
                });
    }
}