package org.example.puzzledays.day05;

import java.util.ArrayList;
import java.util.List;

public record PageRule(Integer x, Integer y) {

    public boolean isInOrder(List<Integer> booklet) {
        if (booklet.contains(x) && booklet.contains(y)) {
            return booklet.indexOf(x) < booklet.indexOf(y);
        }
        return true;
    }

    public List<Integer> orderBookletforRule(List<Integer> booklet) {
        if (booklet.contains(x) && booklet.contains(y)) {
            if (booklet.indexOf(x) < booklet.indexOf(y)) {
                return booklet;
            } else {
                List<Integer> newBooklet = new ArrayList<>(booklet);
                int indexX = newBooklet.indexOf(x);
                int indexY = newBooklet.indexOf(y);
                newBooklet.remove(indexY);
                newBooklet.add(indexX, y);
                return newBooklet;
            }
        }
        return booklet;
    }
}
