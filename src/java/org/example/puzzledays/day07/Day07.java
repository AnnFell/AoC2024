package org.example.puzzledays.day07;

import org.example.utils.FileScanner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day07 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> input = FileScanner.getPuzzleInput(7, false);

        partOne(input);
        partTwo(input);
    }

    private static void partOne(List<String> input) {
        long answer = 0;

        for (String line : input) {
            List<Long> numbers = getNumbers(line);
            Long result = numbers.get(0);
            numbers.remove(0);

            boolean possible = isPossibleEquation(result, numbers);
            if (possible) {
                answer += result;
                System.out.println("\033[0;32m" + line + "\033[0m");
            } else {
                System.out.println(line);
            }
        }
        System.out.println("Answer to part one: " + answer);
    }

    private static void partTwo(List<String> input) {
        long answer = 0;
        System.out.println("Answer to part two: " + answer);
    }

    private static List<Long> getNumbers(String line) {
        List<Long> numbers = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            numbers.add(Long.parseLong(matcher.group(1)));
        }
        return numbers;
    }

    public static boolean isPossibleEquation(Long result, List<Long> operands) {
        List<int[]> possibilities = generateCombinations(operands.size() - 1);
        for (int[] possibility : possibilities) {
            long total = operands.get(0);
            int next = 1;
            for (int operator : possibility) {
                if (operator == 0) {
                    total += operands.get(next);
                } else {
                    total *= operands.get(next);
                }
                next++;
            }
            if (total == result) {
//                System.out.println(Arrays.stream(possibility).mapToObj(String::valueOf).toList());
                return true;
            }
        }
        return false;
    }

    public static List<int[]> generateCombinations(int numberOfOperators) {
        int combinations = (int) Math.pow(2, numberOfOperators);
        List<int[]> list = new ArrayList<>();
        // loop over every possible combination of 0 (is add) and 1 (is multiply) for numberOfOperators
        for (int i = 0; i < combinations; i++) {
            String binary = String.format("%" + numberOfOperators + "s", Integer.toBinaryString(i)).replace(' ', '0');
            int[] operators = Arrays.stream(binary.split("")).toList().stream().mapToInt(Integer::parseInt).toArray();
            list.add(operators);
        }
        return list;
    }
}
