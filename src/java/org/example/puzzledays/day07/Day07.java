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

        solve(input, false);
        solve(input, true);
    }

    private static void solve(List<String> input, boolean useConcat) {
        long answer = 0;

        for (String line : input) {
            List<Long> numbers = getNumbers(line);
            Long result = numbers.get(0);
            numbers.remove(0);

            boolean possible = isPossibleEquation(result, numbers, useConcat);
            if (possible) {
                answer += result;
                System.out.println("\033[0;32m" + line + "\033[0m");
            } else {
                System.out.println(line);
            }
        }
        if (useConcat) {
            System.out.println("Answer to part two: " + answer);
        } else {
            System.out.println("Answer to part one: " + answer);
        }
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

    public static boolean isPossibleEquation(Long result, List<Long> operands, boolean useConcat) {
        List<int[]> possibilities;
        if (useConcat) {
            possibilities = generateConcatCombinations(operands.size() - 1);
        } else {
            possibilities = generateCombinations(operands.size() - 1);
        }

        for (int[] possibility : possibilities) {
            long total = operands.get(0);
            int next = 1;
            for (int operator : possibility) {
                if (operator == 0) {
                    total += operands.get(next);
                } else if (operator == 1) {
                    total *= operands.get(next);
                } else if (operator == 2) {
                    String concatit = "" + total + operands.get(next);
                    total = Long.parseLong(concatit);
                }
                next++;
            }
            if (total == result) {
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

    public static List<int[]> generateConcatCombinations(int numberOfOperators) {
        int combinations = (int) Math.pow(3, numberOfOperators);
        List<int[]> list = new ArrayList<>();
        // loop over every possible combination of 0 (is add) and 1 (is multiply) and 2 (is ||) for numberOfOperators
        for (int i = 0; i < combinations; i++) {
            String base3 = String.format("%" + numberOfOperators + "s", Integer.toString(i, 3)).replace(' ', '0');
            int[] operators = Arrays.stream(base3.split("")).toList().stream().mapToInt(Integer::parseInt).toArray();
            list.add(operators);
        }
        return list;
    }
}
