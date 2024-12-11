package org.example.puzzledays.day11;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class Day11Test {

    @Test
    void blink() {
        List<Long> result = new ArrayList<>(List.of(0L));
        for (int i = 0; i < 20; i++) {
            result = Day11.blink(result);
            System.out.println(result);
            System.out.println(result.size());
        }
    }

    @Test
    void testBlink75() {
//        Day11.blink75(0L, 0);
    }
}