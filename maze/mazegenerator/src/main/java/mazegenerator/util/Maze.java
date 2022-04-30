package mazegenerator.util;

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
        
        if (first.row() == second.row()) {              // Cells are on the same row so we need to
            if (first.column() < second.column()) {     // remove the left wall of the cell on the right
                second.removeWall(Wall.LEFT);
            } else {
                first.removeWall(Wall.LEFT);
            }
        } else {                                        // Cells are on the same column so we remove
            if (first.row() < second.row()) {           // the bottom wall of the cell on top
                first.removeWall(Wall.BOTTOM);
            } else {
                second.removeWall(Wall.BOTTOM);
            }
        }

    }
    
    public void clearNeighbours() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++)  {
                grid[row][column].clearNeighbours();
            }
        }
    }
    
    public void updateGraph() {
        clearNeighbours();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {

                if (row > 0) {
                    Cell c = getCell(row - 1, column);
                    if (!c.hasWall(Wall.BOTTOM)) {
                        getCell(row, column).addNeighbour(c);
                    }
                }
                if (row < rows - 1) {
                    Cell c = getCell(row, column);
                    if (!c.hasWall(Wall.BOTTOM)) {
                        c.addNeighbour(getCell(row + 1, column));
                    }
                }
                if (column > 0) {
                    Cell c = getCell(row, column);
                    if (!c.hasWall(Wall.LEFT)) {
                        c.addNeighbour(getCell(row, column - 1));
                    }
                }
                if (column < columns - 1) {
                    Cell c = getCell(row, column + 1);
                    if (!c.hasWall(Wall.LEFT)) {
                        getCell(row, column).addNeighbour(c);
                    }
                }
            }
        }
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
