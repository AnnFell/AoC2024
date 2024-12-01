package org.example.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class MatrixUtilsTest {

    @Test
    void getAllNeighbours() {
        List<List<String>> matrix = List.of(List.of("1","2","3"), List.of("4","x","5"), List.of("6","7","8"));
        List<String> expected = List.of("1","2","3","4","5","6","7","8");

        List<String> result = MatrixUtils.getAllNeighbours(1, 1, matrix);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getNeighbour() {
        List<List<String>> matrix = List.of(List.of("1","2","3"), List.of("4","x","5"), List.of("6","7","8"));

        String resultTopLeft = MatrixUtils.getNeighbour(1, 1, matrix, NeighbourLocation.TOP_LEFT);
        assertThat(resultTopLeft).isEqualTo("1");

        String resultTop = MatrixUtils.getNeighbour(1, 1, matrix, NeighbourLocation.TOP);
        assertThat(resultTop).isEqualTo("2");

        String resultTopRight = MatrixUtils.getNeighbour(1, 1, matrix, NeighbourLocation.TOP_RIGHT);
        assertThat(resultTopRight).isEqualTo("3");

        String resultLeft = MatrixUtils.getNeighbour(1, 1, matrix, NeighbourLocation.LEFT);
        assertThat(resultLeft).isEqualTo("4");

        String resultRight = MatrixUtils.getNeighbour(1, 1, matrix, NeighbourLocation.RIGHT);
        assertThat(resultRight).isEqualTo("5");

        String resultBottomLeft = MatrixUtils.getNeighbour(1, 1, matrix, NeighbourLocation.BOTTOM_LEFT);
        assertThat(resultBottomLeft).isEqualTo("6");

        String resultBottom = MatrixUtils.getNeighbour(1, 1, matrix, NeighbourLocation.BOTTOM);
        assertThat(resultBottom).isEqualTo("7");

        String resultBottomRight = MatrixUtils.getNeighbour(1, 1, matrix, NeighbourLocation.BOTTOM_RIGHT);
        assertThat(resultBottomRight).isEqualTo("8");
    }

    @Test
    void getIndexOfNeighbours() {

    }
}