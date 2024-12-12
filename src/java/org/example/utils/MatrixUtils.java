package org.example.utils;

import org.example.utils.models.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class MatrixUtils {

    /**
     * Retrieves all neighboring elements around a specified position in a 2D matrix.
     *
     * @param x      the x-coordinate (column index) of the element in the matrix
     * @param y      the y-coordinate (row index) of the element in the matrix
     * @param matrix the 2D matrix from which neighbors are to be retrieved
     * @param <T>    the type of elements stored in the matrix
     * @return a list containing the neighboring elements of the specified position;
     * elements that fall outside the matrix bounds will be stored as null
     */
    public static <T> List<T> getAllNeighbours(int x, int y, List<List<T>> matrix) {
        List<T> result = new ArrayList<>(8);

        result.add(getNeighbour(x, y, matrix, Direction.TOP_LEFT));
        result.add(getNeighbour(x, y, matrix, Direction.TOP));
        result.add(getNeighbour(x, y, matrix, Direction.TOP_RIGHT));
        result.add(getNeighbour(x, y, matrix, Direction.LEFT));
        result.add(getNeighbour(x, y, matrix, Direction.RIGHT));
        result.add(getNeighbour(x, y, matrix, Direction.BOTTOM_LEFT));
        result.add(getNeighbour(x, y, matrix, Direction.BOTTOM));
        result.add(getNeighbour(x, y, matrix, Direction.BOTTOM_RIGHT));

        return result;
    }

    /**
     * Retrieves a neighboring element from a 2D matrix based on a specified direction
     * from a given position. If the neighboring position is out of bounds, it returns null.
     *
     * @param x         the x-coordinate (column index) of the element in the matrix
     * @param y         the y-coordinate (row index) of the element in the matrix
     * @param matrix    the 2D matrix from which the neighbor is to be retrieved
     * @param direction the direction from the specified position to the neighboring element
     * @param <T>       the type of elements stored in the matrix
     * @return the neighboring element located in the specified direction from the given position,
     * or null if the neighbor is out of the matrix bounds
     */
    public static <T> T getNeighbour(int x, int y, List<List<T>> matrix, Direction direction) {
        int maxY = matrix.size() - 1;
        int maxX = matrix.get(0).size() - 1;

        return switch (direction) {
            case TOP_LEFT -> {
                if (x - 1 < 0 || y - 1 < 0) yield null;
                yield matrix.get(y - 1).get(x - 1);
            }
            case TOP -> {
                if (y - 1 < 0) yield null;
                yield matrix.get(y - 1).get(x);
            }
            case TOP_RIGHT -> {
                if (y - 1 < 0 || x + 1 > maxX) yield null;
                yield matrix.get(y - 1).get(x + 1);
            }
            case LEFT -> {
                if (x - 1 < 0) yield null;
                yield matrix.get(y).get(x - 1);
            }
            case RIGHT -> {
                if (x + 1 > maxX) yield null;
                yield matrix.get(y).get(x + 1);
            }
            case BOTTOM_LEFT -> {
                if (x - 1 < 0 || y + 1 > maxY) yield null;
                yield matrix.get(y + 1).get(x - 1);
            }
            case BOTTOM -> {
                if (y + 1 > maxY) yield null;
                yield matrix.get(y + 1).get(x);
            }
            case BOTTOM_RIGHT -> {
                if (x + 1 > maxX || y + 1 > maxY) yield null;
                yield matrix.get(y + 1).get(x + 1);
            }
        };
    }

//    /**
//     * Give back the indexes of the neighbours of a position in a matrix.
//     *
//     * @param x                 x-index of position
//     * @param y                 y-index of position
//     * @param matrixRowCount
//     * @param matrixColumnCount
//     * @return list of [x,y] index of existing neighbours
//     */
//    public static List<int[]> getIndexOfNeighbours(int x, int y, int matrixRowCount, int matrixColumnCount) {
//        List<int[]> result = new ArrayList<>();
//        return result;
//    }

    /**
     * Calculates the corresponding coordinate (row and column) in a 2D matrix
     * from a given array index based on the specified number of rows and columns.
     *
     * @param index   the linear index to be converted into a coordinate
     * @param columns the number of columns in the matrix (firstrow.length())
     * @return a Coordinate object representing the row and column corresponding to the given index (coordinates are 0 based)
     */
    public static Coordinate getCoordinateFromArrayIndex(int index, int columns) {
        int row = (int) Math.floor((double) index / columns);
        int column = index % columns;
        return new Coordinate(column, row);
    }

    /**
     * Calculates the index of an element in a 1D array representation of a 2D matrix
     * given its coordinates in the matrix.
     *
     * @param x       the x-coordinate (column index) of the element in the matrix
     * @param y       the y-coordinate (row index) of the element in the matrix
     * @param columns the number of columns in the matrix
     * @param rows    the number of rows in the matrix
     * @return the index of the element in the 1D array
     * @throws IndexOutOfBoundsException if the coordinates are out of the matrix bounds
     */
    public static int getArrayIndexFromCoordinate(int x, int y, int columns, int rows) {
        if (x < 0 || y < 0 || x > columns - 1 || y > rows - 1) {
            throw new IndexOutOfBoundsException();
        }
        return (y * columns) + x;
    }

    /**
     * Calculates the index of a neighboring cell in a 1D representation
     * of a 2D matrix based on a given current index, number of columns,
     * and specified direction using NeighbourLocation.
     *
     * @param index       the current index in the 1D array representation of the matrix
     * @param columns     the number of columns in the matrix
     * @param direction   the direction of the neighbor relative to the current index
     * @param arrayLength the length og the 1D array
     * @return the index of the neighboring cell in the 1D array representation
     */
    public static int getNeighbourIndexFromCurrentIndex(int index, int columns, Direction direction, int arrayLength) {
        if (index % columns == 0) {
            // index is on left edge
            if (direction == Direction.LEFT
                    || direction == Direction.TOP_LEFT
                    || direction == Direction.BOTTOM_LEFT) {
                throw new IndexOutOfBoundsException();
            }
        }
        if (index % columns == columns - 1) {
            // index is on right edge
            if (direction == Direction.RIGHT
                    || direction == Direction.TOP_RIGHT
                    || direction == Direction.BOTTOM_RIGHT) {
                throw new IndexOutOfBoundsException();
            }
        }
        int action = switch (direction) {
            case TOP_LEFT -> (-columns - 1);
            case TOP -> -columns;
            case TOP_RIGHT -> (-columns + 1);
            case LEFT -> -1;
            case RIGHT -> 1;
            case BOTTOM_LEFT -> (columns - 1);
            case BOTTOM -> columns;
            case BOTTOM_RIGHT -> columns + 1;
        };
        int result = index + action;
        if (result < 0) throw new IndexOutOfBoundsException();
        if (result >= arrayLength) throw new IndexOutOfBoundsException();
        return result;
    }

    public static List<Integer> getDirectNeighboursFromCurrentIndex(int index, int columns, int arrayLength) {
        List<Integer> nbours = new ArrayList<>();
        List<Direction> directions = List.of(Direction.TOP, Direction.RIGHT, Direction.BOTTOM, Direction.LEFT);
        for (Direction direction : directions) {
            try {
                int nbour = getNeighbourIndexFromCurrentIndex(index, columns, direction, arrayLength);
                nbours.add(nbour);
            } catch (IndexOutOfBoundsException e) {
                nbours.add(null);
            }
        }
        return nbours;
    }

}
