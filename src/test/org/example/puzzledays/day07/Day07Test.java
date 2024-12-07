package org.example.puzzledays.day07;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Day07Test {

    @Test
    void generateCombinations() {
        List<int[]> result = Day07.generateCombinations(3);
        assertThat(result.size()).isEqualTo(8);
        assertThat(result.get(0)).isEqualTo(new int[]{0, 0, 0});
        assertThat(result.get(1)).isEqualTo(new int[]{0, 0, 1});
        assertThat(result.get(2)).isEqualTo(new int[]{0, 1, 0});
        assertThat(result.get(3)).isEqualTo(new int[]{0, 1, 1});
        assertThat(result.get(4)).isEqualTo(new int[]{1, 0, 0});
        assertThat(result.get(5)).isEqualTo(new int[]{1, 0, 1});
        assertThat(result.get(6)).isEqualTo(new int[]{1, 1, 0});
        assertThat(result.get(7)).isEqualTo(new int[]{1, 1, 1});
    }

    @Test
    void testIsImpossibleEquation_HappyPath() {
        List<Long> list = List.of(8L, 6L, 4L, 2L, 41L, 6L, 95L, 2L, 4L, 8L, 875L);
        boolean resultGreen = Day07.isPossibleEquation(38584000L, list, false);
        assertThat(resultGreen).isTrue();
    }

    @Test
    void testIsImpossibleEquation() {
//        5721882: 42 49 29 2 469 82
        List<Long> list = List.of(42L, 49L, 29L, 2L, 469L, 82L);
        boolean resultGreen = Day07.isPossibleEquation(5721882L, list, false);
        assertThat(resultGreen).isFalse();
    }

    @Test
    void generateConcatCombinations() {
        List<int[]> result = Day07.generateConcatCombinations(2);
        assertThat(result.size()).isEqualTo(9);
        assertThat(result.get(0)).isEqualTo(new int[]{0, 0});
        assertThat(result.get(1)).isEqualTo(new int[]{0, 1});
        assertThat(result.get(2)).isEqualTo(new int[]{0, 2});
        assertThat(result.get(3)).isEqualTo(new int[]{1, 0});
        assertThat(result.get(4)).isEqualTo(new int[]{1, 1});
        assertThat(result.get(5)).isEqualTo(new int[]{1, 2});
        assertThat(result.get(6)).isEqualTo(new int[]{2, 0});
        assertThat(result.get(7)).isEqualTo(new int[]{2, 1});
        assertThat(result.get(8)).isEqualTo(new int[]{2, 2});
    }
}