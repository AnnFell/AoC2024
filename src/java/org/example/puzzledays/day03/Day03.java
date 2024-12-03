package org.example.puzzledays.day03;

import org.example.utils.FileScanner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> input = FileScanner.getPuzzleInput(3, false);

        partOne(input);
        partTwo(input);
    }

    private static void partOne(List<String> input) {
        long answer = 0;

        String regex = "(mul\\((\\d+),(\\d+)\\))";
        Pattern pattern = Pattern.compile(regex);

        for (String line : input) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                    long mul = Long.parseLong(matcher.group(2)) * Long.parseLong(matcher.group(3));
                    answer += mul;
            }
        }
        System.out.println("Answer to part one: " + answer);
    }

    private static void partTwo(List<String> input) {
        long answer = 0;

        String regex = "(do\\(\\))|(don't\\(\\))|(mul\\((\\d+),(\\d+)\\))";
        Pattern pattern = Pattern.compile(regex);

        boolean enable = true;
        for (String line : input) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                if (matcher.group(1) != null) {
                    enable = true;
                } else if (matcher.group(2) != null) {
                    enable = false;
                } else if (enable && matcher.group(3) != null) {
                    long mul = Long.parseLong(matcher.group(4)) * Long.parseLong(matcher.group(5));
                    answer += mul;
                }
            }
        }
        System.out.println("Answer to part two: " + answer);
    }
}
