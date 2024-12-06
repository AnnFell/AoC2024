package org.example.puzzledays.day06;

import org.example.utils.FileScanner;
import org.example.utils.MatrixUtils;
import org.example.utils.NeighbourLocation;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day06 {
    static List<String> boardList = new ArrayList<>();
    static List<String> positions;
    static int columns = 0;
    static int rows = 0;

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> input = FileScanner.getPuzzleInput(6, false);
        columns = input.get(0).length();
        rows = input.size();
        input.forEach(line -> boardList.addAll(List.of(line.split(""))));
        positions = new ArrayList<>(Collections.nCopies(boardList.size(), ""));

        partOne();
        partTwo(input);
    }

    private static void partOne() {
        int guardPosition = boardList.indexOf("^");
        positions.set(guardPosition, "X");
        NeighbourLocation direction = NeighbourLocation.TOP;

        while (true) {
            try {
                int nextIndex = MatrixUtils.getNeighbourIndexFromCurrentIndex(guardPosition, columns, direction, boardList.size());
                if (boardList.get(nextIndex).equals(".") || boardList.get(nextIndex).equals("^")) {
                    positions.set(nextIndex, "X");
                    guardPosition = nextIndex;
                } else {
                    int newPosition = doTurn(guardPosition, direction);
                    positions.set(newPosition, "X");
                    guardPosition = newPosition;
                    direction = makeARight(direction);
                }
//                printBoard(guardPosition);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Guard has left the grid");
                break;
            }
        }

        long answer = positions.stream().filter(x -> x.equals("X")).count();
        System.out.println("Answer to part one: " + answer);
    }

    private static void partTwo(List<String> input) {
        long answer = 0;
        System.out.println("Answer to part two: " + answer);
    }

    private static NeighbourLocation makeARight(NeighbourLocation direction) {
        return switch (direction) {
            case TOP -> NeighbourLocation.RIGHT;
            case LEFT -> NeighbourLocation.TOP;
            case BOTTOM -> NeighbourLocation.LEFT;
            case RIGHT -> NeighbourLocation.BOTTOM;
            default -> throw new RuntimeException();
        };
    }

    private static int doTurn(int guardPosition, NeighbourLocation currentDirection) {
        int projectedPosition = MatrixUtils.getNeighbourIndexFromCurrentIndex(guardPosition, columns, makeARight(currentDirection), boardList.size());
        if (!boardList.get(projectedPosition).equals("#")) {
            return projectedPosition;
        } else {
            System.out.println("Used?");
            return doTurn(guardPosition, makeARight(currentDirection));
        }
    }

    private static void printBoard(int currentPosition) {
        for (int i = 0; i < boardList.size(); i++) {
            if (i % columns == 0) {
                System.out.println();
            } else {
                if (i == currentPosition) {
                    System.out.print("\033[0;31m" +"O" + "\033[0m");
                }else if (positions.get(i).equals("X")) {
                    System.out.print(positions.get(i));
                } else {
                    System.out.print("\033[0;34m" +boardList.get(i) + "\033[0m");
                }
            }
        }
        System.out.println();
    }
}
