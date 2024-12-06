package org.example.puzzledays.day05;

import org.example.utils.FileScanner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day05 {
    static List<PageRule> orderRules = new ArrayList<>();
    static List<List<Integer>> booklets = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        Day05 puzzle = new Day05();

        ArrayList<String> input = FileScanner.getPuzzleInput(5, false);
        parseInput(input);

        puzzle.partOne();
        puzzle.partTwo();
    }

    private void partOne() {
        long answer = booklets.stream()
                .filter(booklet -> orderRules.stream().allMatch(pageRule -> pageRule.isInOrder(booklet)))
                .mapToLong(booklet -> {
                    int middleIndex = booklet.size() / 2;
                    return booklet.get(middleIndex);
                }).sum();

        System.out.println("Answer to part one: " + answer);
    }

    private void partTwo() {

        long answer = booklets.stream()
                .filter(booklet -> orderRules.stream().anyMatch(pageRule -> !pageRule.isInOrder(booklet)))
                .map(this::sortBooklet)
                .mapToLong(booklet -> {
                    int middleIndex = booklet.size() / 2;
                    return booklet.get(middleIndex);
                }).sum();
        System.out.println("Answer to part two: " + answer);
    }

    private static void parseInput(List<String> input) {
        boolean pageRulesDone = false;
        for (String line : input) {
            if (line.isBlank()) {
                pageRulesDone = true;
                continue;
            }
            if (pageRulesDone) {
                List<Integer> booklet = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).boxed().toList();
                booklets.add(booklet);
            } else {
                String[] xy = line.split("\\|");
                orderRules.add(new PageRule(Integer.valueOf(xy[0]), Integer.valueOf(xy[1])));
            }
        }
    }

    private List<Integer> sortBooklet(List<Integer> booklet) {
        List<Integer> mapped = booklet;
        for (PageRule pageRule : orderRules) {
            booklet = pageRule.orderBookletforRule(booklet);
        }
        boolean orderComplete = orderRules.stream().allMatch(pageRule -> pageRule.isInOrder(mapped));
        if (orderComplete) {
            return mapped;
        } else {
            return sortBooklet(booklet);
        }
    }
}
