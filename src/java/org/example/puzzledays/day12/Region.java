package org.example.puzzledays.day12;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Region {
    private final String crop;
    private long area;
    private long perimeter;

    public Region(String cropType) {
        this.crop = cropType;
    }

    public long getValue() {
        return area * perimeter;
    }

    @Override
    public String toString() {
        return crop + ", area " + area + ", perimeter" + perimeter;
    }
}