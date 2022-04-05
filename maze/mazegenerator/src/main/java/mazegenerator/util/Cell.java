package mazegenerator.util;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Cell {

    private int row, column;
    private boolean visited,
            rightWall, leftWall, topWall, bottomWall;

    private ArrayList<Cell> neighbours;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.visited = false;

        this.rightWall = true;
        this.leftWall = true;
        this.topWall = true;
        this.bottomWall = true;

        this.neighbours = new ArrayList<>();
    }

    public void setVisited() {
        this.visited = true;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void removeWall(Wall wall) {
        switch (wall) {
            case TOP:
                this.topWall = false;
                break;
            case BOTTOM:
                this.bottomWall = false;
                break;
            case LEFT:
                this.leftWall = false;
                break;
            case RIGHT:
                this.rightWall = false;
        }
    }

    public boolean hasWall(Wall wall) {
        switch (wall) {
            case TOP:
                return this.topWall;
            case BOTTOM:
                return this.bottomWall;
            case LEFT:
                return this.leftWall;
            case RIGHT:
                return this.rightWall;
        }
        return false;
    }

    public void addNeighbour(Cell cell) {
        this.neighbours.add(cell);
    }

    public ArrayList<Cell> neighbours() {
        return this.neighbours;
    }

    public ArrayList<Cell> getNonVisitedNeighbours() {
        return neighbours.stream()
                .filter(cell -> !cell.isVisited())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean hasNonVisitedNeighbour() {
        for (Cell n : this.neighbours) {
            if (!n.isVisited()) {
                return true;
            }
        }
        return false;
    }

    public int row() {
        return this.row;
    }

    public int column() {
        return this.column;
    }

    public String toString() {
        return "@ c: " + this.column + ", r: " + this.row;
    }

}
