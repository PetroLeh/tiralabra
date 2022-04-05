package mazegenerator.util;

import java.util.ArrayList;

public class Maze {

    private int rows, columns;
    private Cell[][] grid;

    public Maze(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new Cell[rows][columns];

        init();
    }

    public void init() {
        // Initializing maze
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                grid[row][column] = new Cell(row, column);
            }
        }
        // Adding neighbour cells to all cells in maze
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                if (row < this.rows - 1) {
                    grid[row][column].addNeighbour(grid[row + 1][column]);
                }
                if (row > 0) {
                    grid[row][column].addNeighbour(grid[row - 1][column]);
                }
                if (column < this.columns - 1) {
                    grid[row][column].addNeighbour(grid[row][column + 1]);
                }
                if (column > 0) {
                    grid[row][column].addNeighbour(grid[row][column - 1]);
                }
            }
        }
    }

    public Cell getCell(int row, int column) {
        if (this.grid == null
                || row < 0
                || row >= this.rows
                || column < 0
                || column >= this.columns) {
            return null;
        }
        return this.grid[row][column];
    }

    public void removeWall(Cell first, Cell second) {
        if (first.row() == second.row()) {
            if (first.column() < second.column()) {
                first.removeWall(Wall.RIGHT);
                second.removeWall(Wall.LEFT);
            } else {
                first.removeWall(Wall.LEFT);
                second.removeWall(Wall.RIGHT);
            }
        } else {
            if (first.row() < second.row()) {
                first.removeWall(Wall.BOTTOM);
                second.removeWall(Wall.TOP);
            } else {
                first.removeWall(Wall.TOP);
                second.removeWall(Wall.BOTTOM);
            }
        }

    }

    public ArrayList<Cell> cells() {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                cells.add(grid[row][column]);
            }
        }
        return cells;
    }

    public int rows() {
        return this.rows;
    }

    public int columns() {
        return this.columns;
    }

    @Override
    public String toString() {
        return "Maze size of " + this.rows + " rows, " + this.columns + " columns.";
    }
}
