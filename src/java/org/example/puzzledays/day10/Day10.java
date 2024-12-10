package org.example.puzzledays.day10;

import org.example.utils.Direction;
import org.example.utils.FileScanner;
import org.example.utils.MatrixUtils;

import java.io.FileNotFoundException;
import java.util.*;

public class Day10 {
    static List<Integer> map;
    static int columns;
    static int rows;

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> input = FileScanner.getPuzzleInput(10, false);
        parseInput(input);

        // hiking trail:
        // // as long as possible
        // // starts at 0 (trailhead) and ends at 9
        // // steps are exactly 1
        // // movement only up, down, left, right (no diagonal)

        partOne(input);
        partTwo(input);
    }

    private static void partOne(List<String> input) {
        Map<Integer, Set<Integer>> trails = new HashMap<>();

        for (int i = 0; i < map.size(); i++) {
            Integer value = map.get(i);

            if (value == 0) { // if trailhead,
                // add it to map with key = index
                trails.computeIfAbsent(i, k -> new HashSet<>());
                followTrail(trails, i, i);
            }
        }

        long answer = trails.values().stream().mapToLong(Set::size).sum();
        System.out.println("Answer to part one: " + answer);
    }

    private static void followTrail(Map<Integer, Set<Integer>> trails, int trailhead, int index) {
        Direction[] directions = {Direction.TOP, Direction.BOTTOM, Direction.LEFT, Direction.RIGHT};
        Integer currentValue = map.get(index);

        for (Direction direction : directions) {
            try {
                int nextIndex = MatrixUtils.getNeighbourIndexFromCurrentIndex(index, columns, direction, map.size());
                Integer nextValue = map.get(nextIndex);
                if (nextValue == currentValue + 1) {
                    if (nextValue == 9) {
                        trails.get(trailhead).add(nextIndex);
                    } else {
                        followTrail(trails, trailhead, nextIndex);
                    }
                }
            } catch (IndexOutOfBoundsException e) {
            }
        }
    }

    private static void partTwo(List<String> input) {
        long answer = 0;
        System.out.println("Answer to part two: " + answer);
    }

    private static void parseInput(List<String> input) {
        columns = input.get(0).length();
        rows = input.size();
        map = input.stream().map(line -> List.of(line.split(""))).flatMap(List::stream).mapToInt(Integer::valueOf).boxed().toList();
    }
}
