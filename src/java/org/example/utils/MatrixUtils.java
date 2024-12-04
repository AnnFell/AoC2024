package org.example.utils;

import org.example.utils.models.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class MatrixUtils {

    /**
     * Give back values of all neighbours of a position in a matrix.
     *
     * @param x      x-index of position
     * @param y      y-index of position
     * @param matrix
     * @param <T>
     * @return values of neighbours as a list, index corresponds to these positions:
     * | 0 | 1 | 2 |
     * | 3 | X | 4 |
     * | 5 | 6 | 7 |
     * Value of neighbour is null when neighbour does not exist.
     */
    public static <T> List<T> getAllNeighbours(int x, int y, List<List<T>> matrix) {
        List<T> result = new ArrayList<>(8);

        result.add(getNeighbour(x, y, matrix, NeighbourLocation.TOP_LEFT));
        result.add(getNeighbour(x, y, matrix, NeighbourLocation.TOP));
        result.add(getNeighbour(x, y, matrix, NeighbourLocation.TOP_RIGHT));
        result.add(getNeighbour(x, y, matrix, NeighbourLocation.LEFT));
        result.add(getNeighbour(x, y, matrix, NeighbourLocation.RIGHT));
        result.add(getNeighbour(x, y, matrix, NeighbourLocation.BOTTOM_LEFT));
        result.add(getNeighbour(x, y, matrix, NeighbourLocation.BOTTOM));
        result.add(getNeighbour(x, y, matrix, NeighbourLocation.BOTTOM_RIGHT));

        return result;
    }

    /**
     * Give back a neighbour of a position in a matrix.
     *
     * @param x                 x-index of position
     * @param y                 y-index of position
     * @param matrix
     * @param neighbourLocation top-left, top, top-right, left, right, bottom-left, bottom, bottom-right
     * @param <T>
     * @return
     */
    public static <T> T getNeighbour(int x, int y, List<List<T>> matrix, NeighbourLocation neighbourLocation) {
        int maxY = matrix.size() - 1;
        int maxX = matrix.get(0).size() - 1;

        return switch (neighbourLocation) {
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

    /**
     * Give back the indexes of the neighbours of a position in a matrix.
     *
     * @param x                 x-index of position
     * @param y                 y-index of position
     * @param matrixRowCount
     * @param matrixColumnCount
     * @return list of [x,y] index of existing neighbours
     */
    public static List<int[]> getIndexOfNeighbours(int x, int y, int matrixRowCount, int matrixColumnCount) {
        List<int[]> result = new ArrayList<>();
        return result;
    }

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
        return new Coordinate(row, column);
    }

    /**
     * Calculates the index of a neighboring cell in a 1D representation
     * of a 2D matrix based on a given current index, number of columns,
     * and specified direction using NeighbourLocation.
     *
     * @param index the current index in the 1D array representation of the matrix
     * @param columns the number of columns in the matrix
     * @param direction the direction of the neighbor relative to the current index
     * @return the index of the neighboring cell in the 1D array representation
     */
    public static int getNeighborIndexFromCurrentIndex(int index, int columns, NeighbourLocation direction) {
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
        return index + action;
    }

}
