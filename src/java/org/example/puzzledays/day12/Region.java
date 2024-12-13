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
        // don't look for edges, look for corners! inside/outside corners
        long tops = getContinuousEdgesOfDirection(columns, map, Direction.TOP);
        long rights = getContinuousEdgesOfDirection(columns, map, Direction.RIGHT);
        long bottoms = getContinuousEdgesOfDirection(columns, map, Direction.BOTTOM);
        long lefts = getContinuousEdgesOfDirection(columns, map, Direction.LEFT);

        long total = tops + lefts + bottoms + rights;
//        System.out.println(crop + ", tops: " + tops + ", rights: " + rights + ", bottoms: " + bottoms + ", lefts: " + lefts + " = " + total + " * " + area + " = " + (total * area));
        return total;
    }

    private long getContinuousEdgesOfDirection(int columns, List<String> map, Direction direction) {
        // Find all the "tops" of one direction (the most right, the most left etc.)
        List<Integer> topsOfDirection = indexes.stream().filter(i -> {
            try {
                int extremity = MatrixUtils.getNeighbourIndexFromCurrentIndex(i, columns, direction, map.size());
                return !map.get(extremity).equals(crop);
            } catch (IndexOutOfBoundsException e) {
                return true;
            }
        }).toList();
        long edges = 0;
        for (int item : topsOfDirection) {
            int result = checkForCorner(item, columns, map.size(), direction);
            switch (result) {
                case 0:
                case 1:
                    break;
                case 2:
                case 3: {
                    // check from left to right. If right corner, an edge is complete!
                    edges++;
                    break;
                }
            }
        }

        return edges;
    }

    private int checkForCorner(int index, int columns, int mapSize, Direction edgeDirection) {
        List<Integer> neighbours = MatrixUtils.getAllNeighboursFromCurrentIndex(index, columns, mapSize);
        Integer left = 0;
        Integer leftTop = 0;
        Integer right = 0;
        Integer rightTop = 0;
        // First mae it work for TOP, the "rotated" matrix
        if (edgeDirection.equals(Direction.TOP)) {
            left = neighbours.get(6);
            leftTop = neighbours.get(7);
            right = neighbours.get(2);
            rightTop = neighbours.get(1);
        }
        if (edgeDirection.equals(Direction.RIGHT)) {
            left = neighbours.get(0);
            leftTop = neighbours.get(1);
            right = neighbours.get(4);
            rightTop = neighbours.get(3);
        }
        if (edgeDirection.equals(Direction.BOTTOM)) {
            left = neighbours.get(2);
            leftTop = neighbours.get(3);
            right = neighbours.get(6);
            rightTop = neighbours.get(5);
        }
        if (edgeDirection.equals(Direction.LEFT)) {
            left = neighbours.get(4);
            leftTop = neighbours.get(5);
            right = neighbours.get(0);
            rightTop = neighbours.get(7);
        }
        boolean isLeftOuterCorner = (null == left || !indexes.contains(left)) && (null == leftTop || !indexes.contains(leftTop));
        boolean isLeftInnerCorner = leftTop != null && indexes.contains(leftTop);
        boolean isRightOuterCorner = (null == right || !indexes.contains(right)) && (null == rightTop || !indexes.contains(rightTop));
        boolean isRightInnerCorner = rightTop != null && indexes.contains(rightTop);
        if ((isLeftInnerCorner || isLeftOuterCorner) && (isRightInnerCorner || isRightOuterCorner)) {
            return 3;
        } else if ((isLeftInnerCorner || isLeftOuterCorner)) {
            return 1;
        } else if (isRightOuterCorner || isRightInnerCorner) {
            return 2;
        } else {
            return 0;
        }
    }

    public void add(int index) {
        indexes.add(index);
    }

    @Override
    public String toString() {
        return crop + ", area " + area + ", perimeter" + perimeter;
    }
}