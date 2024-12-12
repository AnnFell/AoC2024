package org.example.puzzledays.day12;

import lombok.Getter;
import lombok.Setter;
import org.example.utils.Direction;
import org.example.utils.MatrixUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Region {
    private final String crop;
    private long area;
    private long perimeter;
    private final List<Integer> indexes = new ArrayList<>();

    public Region(String cropType) {
        this.crop = cropType;
    }

    public long getValue() {
        return area * perimeter;
    }

    public long getValueWithDiscount(int columns, List<String> map) {
        return area * getContinuousPerimeter(columns, map);
    }

    public long getContinuousPerimeter(int columns, List<String> map) {
        // Find al the "TOP" perimeters:
        // // Keep each index do not have same crop above them
        // // Take that size, then minus one for each that has a RIGHT of same crop
        long tops = getContinuousEdgesOfDirection(columns, map, Direction.TOP, Direction.RIGHT);
        long rights = getContinuousEdgesOfDirection(columns, map, Direction.RIGHT, Direction.TOP);
        long bottoms = getContinuousEdgesOfDirection(columns, map, Direction.BOTTOM, Direction.LEFT);
        long lefts = getContinuousEdgesOfDirection(columns, map, Direction.LEFT, Direction.BOTTOM);

        System.out.println(crop + ", tops: " + tops + ", rights: " + rights + ", bottoms: " + bottoms + ", lefts: " + lefts);
        return tops + lefts + bottoms + rights;
    }

    private long getContinuousEdgesOfDirection(int columns, List<String> map, Direction direction1, Direction direction2) {
        List<Integer> extremities = indexes.stream().filter(i -> {
            try {
                int extremity = MatrixUtils.getNeighbourIndexFromCurrentIndex(i, columns, direction1, map.size());
                return !map.get(extremity).equals(crop);
            } catch (IndexOutOfBoundsException e) {
                return true;
            }
        }).toList();
        long edges = extremities.size();
        for (int item : extremities) {
            try {
                int next = MatrixUtils.getNeighbourIndexFromCurrentIndex(item, columns, direction2, map.size());
                if (map.get(next).equals(crop)) {
                    edges--;
                }
            } catch (IndexOutOfBoundsException ignored) {
            }
        }

        return edges;
    }

    public void add(int index) {
        indexes.add(index);
    }

    @Override
    public String toString() {
        return crop + ", area " + area + ", perimeter" + perimeter;
    }
}