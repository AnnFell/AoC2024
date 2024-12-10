package org.example.puzzledays.day09;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private final int capacity;
    private final int startIndex;
    private List<Integer> content = new ArrayList<>();

    public Block(int startIndex, int capacity, List<Integer> content) {
        this.startIndex = startIndex;
        this.capacity = capacity;
        if (content != null) {
            this.content = new ArrayList<>(content);
        }
    }

    public void add(Integer item) {
        if (content.size() + 1 > capacity) {
            throw new RuntimeException("Will exceed space available in block " + startIndex);
        }
        this.content.add(item);
    }

    public Integer takeLastItem() {
        if (content.isEmpty()) {
            throw new RuntimeException("Block is empty");
        }
        Integer lastItem = content.get(content.size() - 1);
        content.remove(content.size() - 1);
        return lastItem;
    }

    public boolean isEmpty() {
        return content.isEmpty();
    }

    public boolean hasFreeSpace() {
        return capacity - content.size() > 0;
    }

    public long getChecksumOfBlock() {
        if (content.isEmpty()) {
            return 0;
        }
        long result = 0;
        long index = startIndex;
        for (Integer integer : content) {
            long calc = index * integer;
            result += calc;
            index++;
//            System.out.println(index + " * " + content.get(i) + " = " + calc);
        }
        return result;
    }

    public String getContentAsString() {
        return content.toString();
    }

    @Override
    public String toString() {
        return "[index " + startIndex + ", capacity " + capacity + ", content " + content + "]";
    }

    public boolean equals(Block other) {
        return this.startIndex == other.startIndex;
    }
}
