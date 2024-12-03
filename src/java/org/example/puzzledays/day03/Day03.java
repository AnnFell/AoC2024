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

        String regex = "mul\\(\\d{1,3},\\d{1,3}\\)";
        Pattern pattern = Pattern.compile(regex);
        String regexDigits = "(\\d{1,3}),(\\d{1,3})";
        Pattern patternDigits = Pattern.compile(regexDigits);

        for (String line : input) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                Matcher matcherDigits = patternDigits.matcher(matcher.group());
                if (matcherDigits.find()) {
                    long mul = Long.parseLong(matcherDigits.group(1)) * Long.parseLong(matcherDigits.group(2));
                    answer += mul;
                }
            }
        }

        System.out.println("Answer to part one: " + answer);
    }

    private static void partTwo(List<String> input) {
        long answer = 0;
        System.out.println("Answer to part two: " + answer);
    }
}
