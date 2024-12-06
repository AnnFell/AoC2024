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
        partTwo();
    }

    private static void partOne() {
        walkGrid(boardList, false);
        long answer = positions.stream().filter(s -> !s.isEmpty()).count();
        System.out.println("Answer to part one: " + answer);
    }

    private static void partTwo() {
        // Only check positions where guard has been in part one for possible object locations
        List<Integer> possibleObstacleIndexes = new ArrayList<>();
        for (int i = 0; i < boardList.size(); i++) {
            if (!positions.get(i).isEmpty()) {
                possibleObstacleIndexes.add(i);
            }
        }
        // possibleObstacleIndexes.addAll(List.of(14, 15, 16, 17, 18, 24, 28, 34, 38, 42, 43, 44, 45, 46, 48, 52, 54, 56, 58, 62, 63, 64, 65, 66, 67, 68, 71, 72, 73, 74, 75, 76, 77, 81, 82, 83, 84, 85, 86, 87, 97));

        long answer = 0;
        for (int obstacleIndex : possibleObstacleIndexes) {
            // Don't add obstacle to start position
            if (obstacleIndex == boardList.indexOf("^")) {
                continue;
            }

            // reset positions
            positions = new ArrayList<>(Collections.nCopies(boardList.size(), ""));
            // Add obstacle
            List<String> newBoard = new ArrayList<>(boardList);
            newBoard.set(obstacleIndex, "O");

            // try the grid walk
            boolean result = walkGrid(newBoard, true);
            if (result) {
                answer++;
            }
        }
        // answer to high
        System.out.println("Answer to part two: " + answer);
    }

    private static boolean walkGrid(List<String> gridList, boolean findLoop) {
        int guardPosition = gridList.indexOf("^");
        positions.set(guardPosition, "^");
        NeighbourLocation direction = NeighbourLocation.TOP;
        int foundPlusses = 0;

        while (true) {
            try {
                int nextIndex = MatrixUtils.getNeighbourIndexFromCurrentIndex(guardPosition, columns, direction, gridList.size());
                if (gridList.get(nextIndex).equals("#") || gridList.get(nextIndex).equals("O")) {
                    // Obstacle encountered! mark turn
                    positions.set(guardPosition, "+");
                    // Take turn and document new position + new direction
                    int newPosition = doTurn(guardPosition, direction);
                    guardPosition = newPosition;
                    direction = getTurnDirection(direction);
                    // Set new position symbol
                    String result = getNewPathStatus(positions.get(guardPosition), direction);
                    positions.set(newPosition, result);
                } else {
                    String result = getNewPathStatus(positions.get(nextIndex), direction);
                    positions.set(nextIndex, result);
                    guardPosition = nextIndex;
                }
                if (findLoop) {
                    if (positions.get(guardPosition).equals("+")) {
                        foundPlusses++;
                        if (foundPlusses > 5) {
                            System.out.println("Is loop! Obstacle at " + gridList.indexOf("O") );
                            printBoard(guardPosition, gridList);
                            return true;
                        }
                    } else {
                        foundPlusses = 0;
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                // Guard has left the grid
                return false;
            }
        }
    }

    private static String getNewPathStatus(String currentGridSymbol, NeighbourLocation direction) {
        return switch (currentGridSymbol) {
            case "":
            case "^": {
                if (direction == NeighbourLocation.TOP || direction == NeighbourLocation.BOTTOM) {
                    yield "|";
                } else {
                    yield "-";
                }
            }
            case "|": {
                if (direction == NeighbourLocation.LEFT || direction == NeighbourLocation.RIGHT) {
                    yield "+";
                }
            }
            case "-": {
                if (direction == NeighbourLocation.TOP || direction == NeighbourLocation.BOTTOM) {
                    yield "+";
                }
            }
            case "+":
                yield "+";
            default:
                throw new RuntimeException();
        };
    }

    private static NeighbourLocation getTurnDirection(NeighbourLocation direction) {
        return switch (direction) {
            case TOP -> NeighbourLocation.RIGHT;
            case LEFT -> NeighbourLocation.TOP;
            case BOTTOM -> NeighbourLocation.LEFT;
            case RIGHT -> NeighbourLocation.BOTTOM;
            default -> throw new RuntimeException();
        };
    }

    private static int doTurn(int guardPosition, NeighbourLocation currentDirection) {
        int projectedPosition = MatrixUtils.getNeighbourIndexFromCurrentIndex(guardPosition, columns, getTurnDirection(currentDirection), boardList.size());
        if (!boardList.get(projectedPosition).equals("#")) {
            return projectedPosition;
        } else {
            return doTurn(guardPosition, getTurnDirection(currentDirection));
        }
    }

    private static void printBoard(int currentPosition, List<String> board) {
        for (int i = 0; i < board.size(); i++) {
            if (i % columns == 0) {
                System.out.println();
            }

            if (i == currentPosition) {
                System.out.print("\033[0;31m" + "@" + "\033[0m");
            } else if (!positions.get(i).isEmpty()) {
                System.out.print(positions.get(i));
            } else {
                System.out.print("\033[0;34m" + board.get(i) + "\033[0m");
            }
        }
        System.out.println("\n");
    }
}
