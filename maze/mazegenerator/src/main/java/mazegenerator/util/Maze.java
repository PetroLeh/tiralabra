
package mazegenerator.util;

import java.util.Stack;

public class Maze {

    private int rows, columns;
    private Cell[][] grid;
    private Stack stack;
    private Cell current;
    
    public Maze(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new Cell[rows][columns];
        this.stack = new Stack();
        
        init();
    }
    
    public void init() {
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                grid[row][column] = new Cell(row, column);
            }
        }
        current = this.grid[0][0];
    }
    
    @Override
    public String toString() {
        return "Maze size of " + this.rows + " rows, " + this.columns + " columns.";
    }
}
