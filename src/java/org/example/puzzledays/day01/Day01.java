package org.example.puzzledays.day01;

import org.example.utils.FileScanner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Day01 {
    private static final List<Integer> listOne = new ArrayList<>();
    private static final List<Integer> listTwo = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> input = FileScanner.getPuzzleInput(1, false);

        partOne(input);
        partTwo(input);
    }

    private static void partOne(List<String> input) {
        parseInput(input, true);
        long answer = 0;
        for (int i = 0; i < listOne.size(); i++) {
            long difference = Math.abs(listOne.get(i) - listTwo.get(i));
            answer += difference;
        }
        System.out.println("Answer to part one: " + answer);
    }

    private static void partTwo(List<String> input) {
        parseInput(input, false);
        long answer = listOne.stream().mapToLong(number -> {
            long count = listTwo.stream().filter(x -> x.equals(number)).count();
            return number * count;
        }).sum();
        System.out.println("Answer to part two: " + answer);
    }

    private static void parseInput(List<String> input, boolean sortLists) {
        listOne.clear();
        listTwo.clear();

        for (String line : input) {
            String[] split = line.split("   ");
            listOne.add(Integer.valueOf(split[0]));
            listTwo.add(Integer.valueOf(split[1]));
        }

        if (sortLists) {
            listOne.sort(Integer::compareTo);
            listTwo.sort(Integer::compareTo);
        }
    }
}
