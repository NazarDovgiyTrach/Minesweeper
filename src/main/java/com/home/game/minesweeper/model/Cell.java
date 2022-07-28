package com.home.game.minesweeper.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Cell {
    private int bombsNearby;
    private boolean open;
    private boolean bomb;
    private int x;
    private int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Cell createBomb(int x, int y){
        Cell cellInfo = new Cell(x, y);
        cellInfo.setBomb(true);
        return cellInfo;
    }
}
