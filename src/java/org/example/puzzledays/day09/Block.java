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

    public void addAll(List<Integer> items) {
        if (content.size() + items.size() > capacity) {
            throw new RuntimeException("Will exceed space available in block " + startIndex);
        }
        this.content.addAll(items);
    }

    public Integer takeLastItem() {
        if (content.isEmpty()) {
            throw new RuntimeException("Block is empty");
        }
        return content.remove(content.size() - 1);
    }

    public List<Integer> takeAllItems() {
        List<Integer> result = new ArrayList<>(content);
        content.clear();
        return result;
    }

    public boolean isEmpty() {
        return content.isEmpty();
    }

    public boolean hasFreeSpace() {
        return capacity - content.size() > 0;
    }

    public int getFreeSpace() {
        return capacity - content.size();
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getCapacity() {
        return capacity;
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
        }
        return result;
    }

    @Override
    public String toString() {
        return "[index " + startIndex + ", capacity " + capacity + ", content " + content + "]";
    }

}
