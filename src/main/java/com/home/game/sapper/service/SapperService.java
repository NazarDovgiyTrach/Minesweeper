package com.home.game.sapper.service;

import com.home.game.sapper.model.Cell;
import com.home.game.sapper.model.Field;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SapperService {

    private final Field field;
    private int opened;
    private int totalCells;
    private int bombs;

    public static SapperService init(int fieldHeight, int fieldWeight) {
        final int bombs = (int) ((fieldHeight * fieldWeight) * 0.2);

        Field field = new Field(fieldHeight, fieldWeight);
        field.populateBombs(bombs);
        SapperService sapperService = new SapperService(field);
        sapperService.setTotalCells(fieldHeight * fieldWeight);
        sapperService.setBombs(bombs);
        return sapperService;
    }

    public void open(int x, int y) throws GameOverException, GameWinException {
        Cell cell = field.getCell(x, y);
        if (cell.isOpen()) {
            return;
        }
        cell.setOpen(true);

        if (cell.isBomb()) {
            throw new GameOverException();
        }

        ++opened;
        if (allOpened()) {
            throw new GameWinException();
        }

        List<Cell> neighbors = field.getNeighboringCells(cell);
        int bombsNearby = bombsDetected(neighbors);
        cell.setBombsNearby(bombsNearby);

        if (bombsNearby == 0) {
            for (Cell neighbor : neighbors) {
                if(!neighbor.isOpen()){
                    open(neighbor.getX(), neighbor.getY());
                }
            }
        }
    }

    public void printField() {
        field.print();
    }

    private int bombsDetected(List<Cell> neighbors) {
        return (int) neighbors.stream()
                .filter(Cell::isBomb)
                .count();
    }

    private boolean allOpened() {
        return bombs == (totalCells - opened);
    }
}
