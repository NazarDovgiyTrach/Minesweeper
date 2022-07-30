package com.home.game.minesweeper.model;

import java.util.*;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Field {
    private final Cell[][] cells;

    public Field(int height, int weight) {
        this.cells = new Cell[height][weight];
        fillCells();
    }


    public Cell getCell(int x, int y) {
        return Objects.requireNonNull(cells[x][y], "Cell cannot be null!");
    }

    public void populateBombs(int amount) {
        LinkedList<Cell> emptyCells = Arrays.stream(this.cells)
                .flatMap(Arrays::stream)
                .collect(Collectors.toCollection(LinkedList::new));

        Collections.shuffle(emptyCells);
        IntStream.range(0, amount).forEach(x -> emptyCells.pop().setBomb(true));
    }

    public List<Cell> getNeighboringCells(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();

        //top
        fillNeighborsList(cell, neighbors, x -> x >= 0, cell.getX() - 1);
        //center
        fillNeighborsList(cell, neighbors, x -> x >= 0, cell.getX());
        //bottom
        fillNeighborsList(cell, neighbors, x -> x < cells.length, cell.getX() + 1);

        return neighbors;
    }

    public void print() {
        for (int i = 0; i < cells.length; i++) {
            if (i == 0) {
                System.out.print("   " + i);
            } else {
                System.out.print(" " + i);
            }
        }
        System.out.println();
        int rowNum = 0;
        for (Cell[] row : cells) {
            System.out.print(rowNum++ + "| ");
            for (Cell cell : row) {
                if (cell != null && cell.isOpen()) {
                    if (cell.isBomb()) {
                        System.out.print("X ");
                    } else {
                        System.out.print(cell.getBombsNearby() + " ");
                    }
                } else System.out.print("? ");
            }
            System.out.println();
        }
    }

    private void fillNeighborsList(final Cell cell, List<Cell> neighbors, IntPredicate condition, int x) {
        if (condition.test(x)) {
            // left neighbor
            if (cell.getY() - 1 >= 0) {
                Cell leftCell = getCell(x, cell.getY() - 1);
                neighbors.add(leftCell);
            }

            // center neighbor
            if (x != cell.getX()) {
                Cell centerCell = getCell(x, cell.getY());
                neighbors.add(centerCell);
            }

            // right neighbor
            if (cell.getY() + 1 < cells[0].length) {
                Cell rightCell = getCell(x, cell.getY() + 1);
                neighbors.add(rightCell);
            }
        }
    }

    private void fillCells() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }
}
