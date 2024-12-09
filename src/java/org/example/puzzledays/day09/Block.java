package org.example.puzzledays.day09;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private int freeSpace;
    private int capacity;
    private int startIndex;
    private List<Integer> content = new ArrayList<>();

    public Block(int startIndex, int capacity, List<Integer> content) {
        this.startIndex = startIndex;
        this.capacity = capacity;
        if (content != null) {
            this.content = List.copyOf(content);
        }
        this.freeSpace = capacity - this.content.size();
    }

    public void add(List<Integer> items) {
        if (content.size() + items.size() > capacity) {
            throw new RuntimeException("Will exceed space available in block " + startIndex);
        }
        this.content.addAll(items);
        freeSpace = capacity - this.content.size();
    }

    public int getFreeSpace() {
        return freeSpace;
    }

}
