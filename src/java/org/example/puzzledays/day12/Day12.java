package org.example.puzzledays.day12;

import org.example.utils.FileScanner;
import org.example.utils.MatrixUtils;

import java.io.FileNotFoundException;
import java.util.*;

public class Day12 {
    Set<Integer> visitedPoints = new HashSet<>();
    List<Region> regions = new ArrayList<>();
    static List<String> map;
    static int columns;
    static int rows;

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> input = FileScanner.getPuzzleInput(12, true);
        map = input.stream().map(line -> Arrays.asList(line.split(""))).flatMap(Collection::stream).toList();
        rows = input.size();
        columns = input.get(0).length();

        // Garden plot
        // Different types of crops have different letters
        // Can be planted in areas
        // Need to know: AREA and PERIMETER.
        // Can have holes with different crops, that count as "fence"/perimeter too.
        // price = area * perimeter.

        // Bulk discount
        // NUMBER OF SIDES = continuous edge.

        Day12 puzzle = new Day12();
        puzzle.solve(false);
        puzzle.solve(true);
    }

    private void solve(boolean partTwo) {
        for (int i = 0; i < map.size(); i++) {
            if (!visitedPoints.contains(i)) {
                Region region = new Region(map.get(i));
                regions.add(region);
                searchRegion(region, i);
            }
        }

        if (partTwo) {
            long answer = regions.stream().mapToLong(region -> region.getValueWithDiscount(columns, map)).sum();
            System.out.println("Answer to part two: " + answer);
        } else {
            long answer = regions.stream().mapToLong(Region::getValue).sum();
            System.out.println("Answer to part one: " + answer);
        }
    }

    private void searchRegion(Region region, int index) {
        if (visitedPoints.contains(index)) return;

        visitedPoints.add(index);
        region.setArea(region.getArea() + 1);
        region.add(index);

        String value = map.get(index);
        List<Integer> nbours = MatrixUtils.getDirectNeighboursFromCurrentIndex(index, columns, map.size());
        // calc perimeter: loop over neighbours: edge or other crop perimeter ++, same crop: search
        for (Integer nbour : nbours) {
            if (nbour == null || !map.get(nbour).equals(value)) {
                region.setPerimeter(region.getPerimeter() + 1);
            } else {
                searchRegion(region, nbour);
            }
        }
    }
}
