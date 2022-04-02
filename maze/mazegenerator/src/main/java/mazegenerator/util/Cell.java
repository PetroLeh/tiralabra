package mazegenerator.util;

public class Cell {

    private int row, column;
    private boolean visited,
            rightWall, leftWall, topWall, bottomWall;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.visited = false;

        this.rightWall = true;
        this.leftWall = true;
        this.topWall = true;
        this.bottomWall = true;
    }

    @Override
    public String toString() {
        return "@ r: " + this.row + ", c: " + this.column;
    }

}
