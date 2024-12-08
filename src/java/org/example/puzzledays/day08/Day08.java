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

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> input = FileScanner.getPuzzleInput(8, true);
        columns = input.get(0).length();
        map = input.stream().map(line -> List.of(line.split(""))).flatMap(List::stream).toList();
        antiNodeMap = map.stream().map(s -> "").collect(Collectors.toCollection(ArrayList::new));

        // Antenna: have frequency (== character other than .)
        // They have anti-nodes, a point that is:
        // // 2 antennas of same frequency
        // // in line with the two antenna's
        // // equally spaced with the two antennas

        partOne();
        partTwo(input);
    }

    private static void partOne() {
//        Map<String, Long> test = map.stream().collect(Collectors.groupingBy(s -> s, Collectors.counting()));
//        System.out.println(test);

        Map<String, List<Integer>> occurrences = new HashMap<>();
        for (int i = 0; i < map.size(); i++) {
            if (!map.get(i).equals(".")) {
                occurrences.computeIfAbsent(map.get(i), k -> new ArrayList<>()).add(i);
            }
        }
        System.out.println(occurrences);

        for (Map.Entry<String, List<Integer>> entry : occurrences.entrySet()) {
            List<Integer> antennaIndex = entry.getValue();
            List<Integer> result = findAntiNodes(antennaIndex);
            printMap(result, map.get(antennaIndex.get(0)));
        }
        printMap(null, "");

        long answer = 0L;
        for (String s : antiNodeMap) {
            if (s.equals("#")) {
                answer++;
            }
        }
        System.out.println("Answer to part one: " + answer);
    }

    private static void partTwo(List<String> input) {
        long answer = 0;
        System.out.println("Answer to part two: " + answer);
    }

    public static List<Integer> findAntiNodes(List<Integer> antennaIndex) {
        List<Integer> check = new ArrayList<>();

        // hacky, sorry, for each frequency only 3 or 4 antennas
        for (int i = 1; i < antennaIndex.size(); i++) {
            check.addAll(markAntiNodesForIndexes(antennaIndex.get(0), antennaIndex.get(i)));
        }
        check.addAll(markAntiNodesForIndexes(antennaIndex.get(1), antennaIndex.get(2)));
        if (antennaIndex.size() == 4) {
            check.addAll(markAntiNodesForIndexes(antennaIndex.get(1), antennaIndex.get(3)));
            check.addAll(markAntiNodesForIndexes(antennaIndex.get(2), antennaIndex.get(3)));
        }
        return check;
    }

    public static List<Integer> markAntiNodesForIndexes(int first, int second) {
        List<Integer> antinodes = new ArrayList<>();

        Coordinate firstA = MatrixUtils.getCoordinateFromArrayIndex(first, columns);
        Coordinate secondA = MatrixUtils.getCoordinateFromArrayIndex(second, columns);
        int dX = firstA.column() - secondA.column();
        int dY = firstA.row() - secondA.row();

        int firstY = firstA.row() + dY;
        int firstX = firstA.column() + dX;
        if (firstY < map.size() && firstX < columns) {
            int index = (columns * firstY) + firstX;
            if (index > -1 && index < map.size()) {
                antiNodeMap.set(index, "#");
                antinodes.add(index);
            }
        }
        int secondY = secondA.row() - dY;
        int secondX = secondA.column() - dX;
        if (secondY < map.size() && secondX < columns) {
            int index = (columns * secondY) + secondX;
            if (index > -1 && index < map.size()) {
                antiNodeMap.set(index, "#");
                antinodes.add(index);
            }
        }
        return antinodes;
    }

    private static void printMap(List<Integer> antinodes, String currentFrequency) {
        for (int i = 0; i < map.size(); i++) {
            if (i % columns == 0) {
                System.out.println();
            }

            String antinode = antiNodeMap.get(i);
            String point = map.get(i);
            if (antinodes != null) {
                if (point.equals(".") && antinodes.contains(i)) {
                    System.out.print("\033[0;32m" + "#" + "\033[0m");
                } else if (point.equals(currentFrequency)) {
                    System.out.print("\033[0;31m" + point + "\033[0m");
                } else if (antinodes.contains(i)) {
                    System.out.print("\033[0;34m" + point + "\033[0m");
                } else {
                    System.out.print(point);
                }
            } else {
                if (point.equals(".") && !antinode.isEmpty()) {
                    System.out.print("\033[0;32m" + antinode + "\033[0m");
                } else if (!antinode.isEmpty()) {
                    System.out.print("\033[0;34m" + point + "\033[0m");
                } else {
                    System.out.print(point);
                }
            }
        }
        System.out.println("\n");
    }
}
