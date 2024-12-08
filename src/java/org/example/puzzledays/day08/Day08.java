package org.example.puzzledays.day08;

import org.example.utils.FileScanner;
import org.example.utils.MatrixUtils;
import org.example.utils.models.Coordinate;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day08 {
    static List<String> map;
    static List<String> antiNodeMap;
    static int columns;
    static int rows;

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> input = FileScanner.getPuzzleInput(8, false);
        parseData(input);
        partOne();

        // parse data again = lazy reset
        parseData(input);
        partTwo();
    }

    private static void parseData(List<String> input) {
        columns = input.get(0).length();
        rows = input.size();
        map = input.stream().map(line -> List.of(line.split(""))).flatMap(List::stream).toList();
        antiNodeMap = map.stream().map(s -> "").collect(Collectors.toCollection(ArrayList::new));
    }

    private static void partOne() {
        Map<String, List<Integer>> occurrences = new HashMap<>();
        for (int i = 0; i < map.size(); i++) {
            if (!map.get(i).equals(".")) {
                occurrences.computeIfAbsent(map.get(i), k -> new ArrayList<>()).add(i);
            }
        }
        for (Map.Entry<String, List<Integer>> entry : occurrences.entrySet()) {
            List<Integer> antennaIndex = entry.getValue();
            findAntiNodes(antennaIndex, false);
        }
        printMap();

        long answer = 0L;
        for (String s : antiNodeMap) {
            if (s.equals("#")) {
                answer++;
            }
        }
        System.out.println("Answer to part one: " + answer);
    }

    private static void partTwo() {
        Map<String, List<Integer>> occurrences = new HashMap<>();
        for (int i = 0; i < map.size(); i++) {
            if (!map.get(i).equals(".")) {
                occurrences.computeIfAbsent(map.get(i), k -> new ArrayList<>()).add(i);
            }
        }
        for (Map.Entry<String, List<Integer>> entry : occurrences.entrySet()) {
            List<Integer> antennaIndex = entry.getValue();
            findAntiNodes(antennaIndex, true);
        }
        printMap();
        long answer = 0L;
        for (int i = 0; i < antiNodeMap.size(); i++) {
            if (antiNodeMap.get(i).equals("#") || !map.get(i).equals(".")) {
                answer++;
            }
        }
        System.out.println("Answer to part two: " + answer);
    }

    public static void markAntiNodesWithResonanceForIndexes(int first, int second, boolean useResonance) {
        Coordinate firstA = MatrixUtils.getCoordinateFromArrayIndex(first, columns);
        Coordinate secondA = MatrixUtils.getCoordinateFromArrayIndex(second, columns);
        int dX = firstA.columnX() - secondA.columnX();
        int dY = firstA.rowY() - secondA.rowY();

        boolean outOfFirstBounds = false;
        int newUpperY = firstA.rowY();
        int newUpperX = firstA.columnX();
        while (!outOfFirstBounds) {
            newUpperY = newUpperY + dY;
            newUpperX = newUpperX + dX;
            try {
                int index = MatrixUtils.getArrayIndexFromCoordinate(newUpperX, newUpperY, columns, rows);
                antiNodeMap.set(index, "#");
            } catch (IndexOutOfBoundsException e) {
                outOfFirstBounds = true;
            }
            if (!useResonance) break;
        }

        boolean outOfSecondBounds = false;
        int newLowerY = secondA.rowY();
        int newLowerX = secondA.columnX();
        while (!outOfSecondBounds) {
            newLowerY = newLowerY - dY;
            newLowerX = newLowerX - dX;
            try {
                int index = MatrixUtils.getArrayIndexFromCoordinate(newLowerX, newLowerY, columns, rows);
                antiNodeMap.set(index, "#");
            } catch (IndexOutOfBoundsException e) {
                outOfSecondBounds = true;
            }
            if (!useResonance) break;
        }
    }

    public static void findAntiNodes(List<Integer> antennaIndex, boolean useResonance) {
        // hacky, sorry, for each frequency only 3 or 4 antennas so this covers every pair
        for (int i = 1; i < antennaIndex.size(); i++) {
            markAntiNodesWithResonanceForIndexes(antennaIndex.get(0), antennaIndex.get(i), useResonance);
        }
        markAntiNodesWithResonanceForIndexes(antennaIndex.get(1), antennaIndex.get(2), useResonance);
        if (antennaIndex.size() == 4) {
            markAntiNodesWithResonanceForIndexes(antennaIndex.get(1), antennaIndex.get(3), useResonance);
            markAntiNodesWithResonanceForIndexes(antennaIndex.get(2), antennaIndex.get(3), useResonance);
        }
    }

    private static void printMap() {
        for (int i = 0; i < map.size(); i++) {
            if (i % columns == 0) {
                System.out.println();
            }

            String antinode = antiNodeMap.get(i);
            String point = map.get(i);

            if (point.equals(".") && !antinode.isEmpty()) {
                System.out.print("\033[0;32m" + antinode + "\033[0m");
            } else if (!antinode.isEmpty()) {
                System.out.print("\033[0;34m" + point + "\033[0m");
            } else {
                System.out.print(point);
            }

        }
        System.out.println("\n");
    }
}
