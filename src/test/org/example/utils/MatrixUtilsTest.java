package org.example.utils;

import org.example.utils.models.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class MatrixUtilsTest {

    @Test
    void getAllNeighbours() {
        List<List<String>> matrix = List.of(List.of("1", "2", "3"), List.of("4", "x", "5"), List.of("6", "7", "8"));
        List<String> expected = List.of("1", "2", "3", "4", "5", "6", "7", "8");

        List<String> result = MatrixUtils.getAllNeighbours(1, 1, matrix);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getNeighbour() {
        List<List<String>> matrix = List.of(List.of("1", "2", "3"), List.of("4", "x", "5"), List.of("6", "7", "8"));

        String resultTopLeft = MatrixUtils.getNeighbour(1, 1, matrix, Direction.TOP_LEFT);
        assertThat(resultTopLeft).isEqualTo("1");

        String resultTop = MatrixUtils.getNeighbour(1, 1, matrix, Direction.TOP);
        assertThat(resultTop).isEqualTo("2");

        String resultTopRight = MatrixUtils.getNeighbour(1, 1, matrix, Direction.TOP_RIGHT);
        assertThat(resultTopRight).isEqualTo("3");

        String resultLeft = MatrixUtils.getNeighbour(1, 1, matrix, Direction.LEFT);
        assertThat(resultLeft).isEqualTo("4");

        String resultRight = MatrixUtils.getNeighbour(1, 1, matrix, Direction.RIGHT);
        assertThat(resultRight).isEqualTo("5");

        String resultBottomLeft = MatrixUtils.getNeighbour(1, 1, matrix, Direction.BOTTOM_LEFT);
        assertThat(resultBottomLeft).isEqualTo("6");

        String resultBottom = MatrixUtils.getNeighbour(1, 1, matrix, Direction.BOTTOM);
        assertThat(resultBottom).isEqualTo("7");

        String resultBottomRight = MatrixUtils.getNeighbour(1, 1, matrix, Direction.BOTTOM_RIGHT);
        assertThat(resultBottomRight).isEqualTo("8");
    }

    @Test
    void getCoordinateFromArrayIndex() {
//        List<List<String>> matrix = List.of(List.of("0", "1", "2"), List.of("3", "4", "5"), List.of("6", "7", "8"), List.of("9", "10", "11"));
//        List<String> matrixList = List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");

        Coordinate x00 = MatrixUtils.getCoordinateFromArrayIndex(0, 3);
        assertThat(x00).isEqualTo(new Coordinate(0, 0));

        Coordinate x01 = MatrixUtils.getCoordinateFromArrayIndex(1, 3);
        assertThat(x01).isEqualTo(new Coordinate(1, 0));

        Coordinate x02 = MatrixUtils.getCoordinateFromArrayIndex(2, 3);
        assertThat(x02).isEqualTo(new Coordinate(2, 0));

        Coordinate x10 = MatrixUtils.getCoordinateFromArrayIndex(3, 3);
        assertThat(x10).isEqualTo(new Coordinate(0, 1));

        Coordinate x11 = MatrixUtils.getCoordinateFromArrayIndex(4, 3);
        assertThat(x11).isEqualTo(new Coordinate(1, 1));

        Coordinate x12 = MatrixUtils.getCoordinateFromArrayIndex(5, 3);
        assertThat(x12).isEqualTo(new Coordinate(2, 1));

        Coordinate x30 = MatrixUtils.getCoordinateFromArrayIndex(9, 3);
        assertThat(x30).isEqualTo(new Coordinate(0, 3));

        Coordinate x31 = MatrixUtils.getCoordinateFromArrayIndex(10, 3);
        assertThat(x31).isEqualTo(new Coordinate(1, 3));

        Coordinate x32 = MatrixUtils.getCoordinateFromArrayIndex(11, 3);
        assertThat(x32).isEqualTo(new Coordinate(2, 3));
    }

    @Test
    void getNeighbourIndexFromCurrentIndex() {
        // 00 01 02
        // 03 04 05
        // 06 07 08
        int topLeft = MatrixUtils.getNeighbourIndexFromCurrentIndex(4, 3, Direction.TOP_LEFT, 9);
        assertThat(topLeft).isEqualTo(0);
        int top = MatrixUtils.getNeighbourIndexFromCurrentIndex(4, 3, Direction.TOP, 9);
        assertThat(top).isEqualTo(1);
        int topRight = MatrixUtils.getNeighbourIndexFromCurrentIndex(4, 3, Direction.TOP_RIGHT, 9);
        assertThat(topRight).isEqualTo(2);
        int left = MatrixUtils.getNeighbourIndexFromCurrentIndex(4, 3, Direction.LEFT, 9);
        assertThat(left).isEqualTo(3);
        int right = MatrixUtils.getNeighbourIndexFromCurrentIndex(4, 3, Direction.RIGHT, 9);
        assertThat(right).isEqualTo(5);
        int bottomLeft = MatrixUtils.getNeighbourIndexFromCurrentIndex(4, 3, Direction.BOTTOM_LEFT, 9);
        assertThat(bottomLeft).isEqualTo(6);
        int bottom = MatrixUtils.getNeighbourIndexFromCurrentIndex(4, 3, Direction.BOTTOM, 9);
        assertThat(bottom).isEqualTo(7);
        int bottomRight = MatrixUtils.getNeighbourIndexFromCurrentIndex(4, 3, Direction.BOTTOM_RIGHT, 9);
        assertThat(bottomRight).isEqualTo(8);

        // Left edge
        assertThatThrownBy(() -> MatrixUtils.getNeighbourIndexFromCurrentIndex(3, 3, Direction.TOP_LEFT, 9))
                .isInstanceOf(IndexOutOfBoundsException.class);
        assertThatThrownBy(() -> MatrixUtils.getNeighbourIndexFromCurrentIndex(3, 3, Direction.LEFT, 9))
                .isInstanceOf(IndexOutOfBoundsException.class);
        assertThatThrownBy(() -> MatrixUtils.getNeighbourIndexFromCurrentIndex(3, 3, Direction.BOTTOM_LEFT, 9))
                .isInstanceOf(IndexOutOfBoundsException.class);

        // Right edge
        assertThatThrownBy(() -> MatrixUtils.getNeighbourIndexFromCurrentIndex(5, 3, Direction.TOP_RIGHT, 9))
                .isInstanceOf(IndexOutOfBoundsException.class);
        assertThatThrownBy(() -> MatrixUtils.getNeighbourIndexFromCurrentIndex(5, 3, Direction.RIGHT, 9))
                .isInstanceOf(IndexOutOfBoundsException.class);
        assertThatThrownBy(() -> MatrixUtils.getNeighbourIndexFromCurrentIndex(5, 3, Direction.BOTTOM_RIGHT, 9))
                .isInstanceOf(IndexOutOfBoundsException.class);

        // Top edge
        assertThatThrownBy(() -> MatrixUtils.getNeighbourIndexFromCurrentIndex(0, 3, Direction.TOP, 9))
                .isInstanceOf(IndexOutOfBoundsException.class);
        assertThatThrownBy(() -> MatrixUtils.getNeighbourIndexFromCurrentIndex(0, 3, Direction.TOP_LEFT, 9))
                .isInstanceOf(IndexOutOfBoundsException.class);
        assertThatThrownBy(() -> MatrixUtils.getNeighbourIndexFromCurrentIndex(2, 3, Direction.TOP_RIGHT, 9))
                .isInstanceOf(IndexOutOfBoundsException.class);
        assertThatThrownBy(() -> MatrixUtils.getNeighbourIndexFromCurrentIndex(2, 3, Direction.RIGHT, 9))
                .isInstanceOf(IndexOutOfBoundsException.class);

        // Bottom edge
        assertThatThrownBy(() -> MatrixUtils.getNeighbourIndexFromCurrentIndex(6, 3, Direction.BOTTOM, 9))
                .isInstanceOf(IndexOutOfBoundsException.class);
        assertThatThrownBy(() -> MatrixUtils.getNeighbourIndexFromCurrentIndex(6, 3, Direction.BOTTOM_LEFT, 9))
                .isInstanceOf(IndexOutOfBoundsException.class);
        assertThatThrownBy(() -> MatrixUtils.getNeighbourIndexFromCurrentIndex(8, 3, Direction.BOTTOM_RIGHT, 9))
                .isInstanceOf(IndexOutOfBoundsException.class);
        assertThatThrownBy(() -> MatrixUtils.getNeighbourIndexFromCurrentIndex(8, 3, Direction.RIGHT, 9))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }


    @Test
    void getArrayIndexFromCoordinateBasic() {
        int index = MatrixUtils.getArrayIndexFromCoordinate(1, 2, 3, 3);
        assertThat(index).isEqualTo(7);
    }

    @Test
    void getArrayIndexFromCoordinateWithFirstXRow() {
        int index = MatrixUtils.getArrayIndexFromCoordinate(0, 2, 3, 3);
        assertThat(index).isEqualTo(6);
    }

    @Test
    void getArrayIndexFromCoordinateWithFirstYRow() {
        int index = MatrixUtils.getArrayIndexFromCoordinate(2, 0, 3, 3);
        assertThat(index).isEqualTo(2);
    }

    @Test
    void getArrayIndexFromCoordinateWhenXIsBiggerThanColumns() {
        assertThatThrownBy(() -> MatrixUtils.getArrayIndexFromCoordinate(3, 0, 3, 3))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void getArrayIndexFromCoordinateWhenYIsBiggerThanRows() {
        assertThatThrownBy(() -> MatrixUtils.getArrayIndexFromCoordinate(2, 3, 3, 3))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void getArrayIndexFromCoordinateWhenXIsNegative() {
        assertThatThrownBy(() -> MatrixUtils.getArrayIndexFromCoordinate(-1, 1, 3, 3))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void getArrayIndexFromCoordinateWhenYIsNegative() {
        assertThatThrownBy(() -> MatrixUtils.getArrayIndexFromCoordinate(1, -1, 3, 3))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

}