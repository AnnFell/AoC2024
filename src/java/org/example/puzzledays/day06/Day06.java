package org.example.puzzledays.day06;

import org.example.utils.FileScanner;
import org.example.utils.MatrixUtils;
import org.example.utils.Direction;

import java.io.FileNotFoundException;
import java.util.*;

public class Day06 {
    static List<String> boardList = new ArrayList<>();
    static List<String> positions;
    static int columns = 0;
    static Set<Visited> visitedInDirection = new HashSet<>();


    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> input = FileScanner.getPuzzleInput(6, false);
        columns = input.get(0).length();
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
            visitedInDirection.clear();
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
        Direction direction = Direction.TOP;

        while (true) {
            try {
                int nextIndex = MatrixUtils.getNeighbourIndexFromCurrentIndex(guardPosition, columns, direction, gridList.size());
                if (gridList.get(nextIndex).equals("#") || gridList.get(nextIndex).equals("O")) {
                    // Obstacle encountered! mark turn
                    positions.set(guardPosition, "+");
                    // Take turn
                    boolean firstTurnSuccessful = canTurn(guardPosition, direction);
                    direction = getTurnDirection(direction);
                    if (!firstTurnSuccessful) {
                        direction = getTurnDirection(direction);
                    }
                    int newPosition = MatrixUtils.getNeighbourIndexFromCurrentIndex(guardPosition, columns, direction, boardList.size());
                    guardPosition = newPosition;
                    // Set new position symbol
                    String result = getNewPathStatus(positions.get(guardPosition), direction);
                    positions.set(newPosition, result);
                } else {
                    String result = getNewPathStatus(positions.get(nextIndex), direction);
                    positions.set(nextIndex, result);
                    guardPosition = nextIndex;
                }

                if (findLoop) {
                    Visited visited = new Visited(guardPosition, direction);
                    if (visitedInDirection.contains(visited)) {
//                        System.out.println("Is loop! Obstacle at " + gridList.indexOf("O"));
//                        printBoard(guardPosition, gridList);
                        return true;
                    } else {
                        visitedInDirection.add(visited);
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                // Guard has left the grid
                return false;
            }
        }
    }

    private static String getNewPathStatus(String currentGridSymbol, Direction direction) {
        return switch (currentGridSymbol) {
            case "":
            case "^": {
                if (direction == Direction.TOP || direction == Direction.BOTTOM) {
                    yield "|";
                } else {
                    yield "-";
                }
            }
            case "|": {
                if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                    yield "+";
                }
            }
            case "-": {
                if (direction == Direction.TOP || direction == Direction.BOTTOM) {
                    yield "+";
                }
            }
            case "+":
                yield "+";
            default:
                throw new RuntimeException();
        };
    }

    private static Direction getTurnDirection(Direction direction) {
        return switch (direction) {
            case TOP -> Direction.RIGHT;
            case LEFT -> Direction.TOP;
            case BOTTOM -> Direction.LEFT;
            case RIGHT -> Direction.BOTTOM;
            default -> throw new RuntimeException();
        };
    }

    private static boolean canTurn(int guardPosition, Direction currentDirection) {
        int projectedPosition = MatrixUtils.getNeighbourIndexFromCurrentIndex(guardPosition, columns, getTurnDirection(currentDirection), boardList.size());
        return !boardList.get(projectedPosition).equals("#");
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
