package mazegenerator.util;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class DFSGenerator implements Generator {

    private Stack<Cell> stack;
    private Cell current;
    private final Random random = new Random();
    private ArrayList<Cell> path;

    public DFSGenerator() {
        this.stack = new Stack<>();
        this.path = new ArrayList<>();
        this.current = null;
    }

    @Override
    public Maze initMaze(int rows, int columns) {
        System.out.println("initializing (" + rows + "x" + columns + " maze)...");
        return new Maze(rows, columns);
    }

    @Override
    public Maze generate(int rows, int columns) {
        System.out.println("Generating maze...");
        Maze maze = initMaze(rows, columns);
        this.current = maze.getCell(0, 0);
        current.setVisited();
        this.stack.push(current);

        System.out.println("generating...");
        long startTime = System.currentTimeMillis();
        int maxStackSize = 1;

        while (!stack.isEmpty()) {
            path.add(current);
            if (current.hasNonVisitedNeighbour()) {
                Cell nextCell = selectRandomNeighbour(current);
                maze.removeWall(current, nextCell);
                stack.push(nextCell);
                if (stack.size() > maxStackSize) {
                    maxStackSize = stack.size();
                }
                current = nextCell;
                current.setVisited();
            } else {
                current = stack.pop();
            }
        }

        long elapsed = System.currentTimeMillis() - startTime;

        System.out.println("ready");
        System.out.println("time elapsed: " + elapsed + " ms | maximum stack size: " + maxStackSize);

        return maze;
    }

    private Cell selectRandomNeighbour(Cell cell) {
        ArrayList<Cell> neighbours = cell.getNonVisitedNeighbours();
        if (neighbours.size() == 0) {
            System.out.println("Naapurien määrä ei toimi...");
        }
        int n = random.nextInt(neighbours.size());
        return neighbours.get(n);
    }
    
    public ArrayList<Cell> path() {
        return this.path;
    }

    @Override
    public String toString() {
        return "Depth First Search Maze Generator";
    }
}
