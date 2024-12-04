package org.example.puzzledays.day04;

import org.example.utils.FileScanner;
import org.example.utils.MatrixUtils;
import org.example.utils.NeighbourLocation;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day04 {
    private static int columns;
    private static final List<String> boardList = new ArrayList<>();
    private static List<String> state;

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> input = FileScanner.getPuzzleInput(4, false);
        columns = input.get(0).length();
        input.forEach(line -> boardList.addAll(List.of(line.split(""))));
        state = new ArrayList<>(Collections.nCopies(boardList.size(), ""));

        partOne();
        partTwo(input);
    }

    private static void partOne() {
        long answer = 0;
        for (int i = 0; i < boardList.size(); i++) {
            if (boardList.get(i).equals("X")) {
                for (NeighbourLocation direction : NeighbourLocation.values()) {
                    if (isXMAS(i, direction)) {
                        answer++;
                    }
                }
            }
        }
        printBoard();
        System.out.println("Answer to part one: " + answer);
    }

    private static void partTwo(List<String> input) {
        long answer = 0;
        System.out.println("Answer to part two: " + answer);
    }

    private static boolean isXMAS(int indexOfX, NeighbourLocation direction) {
        try {
            int m = MatrixUtils.getNeighborIndexFromCurrentIndex(indexOfX, columns, direction, boardList.size());
            int a = MatrixUtils.getNeighborIndexFromCurrentIndex(m, columns, direction, boardList.size());
            int s = MatrixUtils.getNeighborIndexFromCurrentIndex(a, columns, direction, boardList.size());
            boolean isM = boardList.get(m).equals("M");
            boolean isA = boardList.get(a).equals("A");
            boolean isS = boardList.get(s).equals("S");
            if (isM && isA && isS) {
                state.set(indexOfX, "X");
                state.set(m, ".");
                state.set(a, ".");
                state.set(s, ".");
            }
            return isM && isA && isS;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    private static void printBoard() {
        int row = 0;
        for (int i = 0; i < boardList.size(); i++) {
            if (state.get(i).equals("X")) {
                System.out.print("\033[0;32m" + boardList.get(i) + "\033[0m");
            } else if (state.get(i).equals(".")) {
                System.out.print("\033[0;34m" + boardList.get(i) + "\033[0m");
            } else {
//                                System.out.print("\033[0;31m" + boardList.get(i) + "\033[0m");
                System.out.print("\033[0;31m" + "." + "\033[0m");
            }
            row++;
            if (row == columns) {
                row = 0;
                System.out.println();
            }
        }
    }

}
