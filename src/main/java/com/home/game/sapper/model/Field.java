package com.home.game.sapper.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.IntPredicate;

public class Field {
    private final Cell[][] cells;
    private final Random random = new Random();

    public Field(int height, int weight) {
        this.cells = new Cell[height][weight];
    }


    public Cell getCell(int x, int y) {
        return Optional.ofNullable(cells[x][y]).orElseGet(() -> addNewCell(x, y));
    }


    public void populateBombs(int amount) {
        for (int i = 0; i < amount; ++i) {
            int x = random.nextInt(cells.length);
            int y = random.nextInt(cells[0].length);
            cells[x][y] = Cell.createBomb(x, y);
        }
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
        for(int i = 0; i <cells.length; i++){
            if(i == 0){
                System.out.print("   "+ i);
            }else {
                System.out.print(" "+ i);
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

    private Cell addNewCell(int x, int y) {
        Cell newCell = new Cell(x, y);
        cells[x][y] = newCell;
        return newCell;
    }
}
