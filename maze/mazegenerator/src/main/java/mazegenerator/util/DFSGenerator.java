package mazegenerator.util;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class DFSGenerator implements Generator {

    private Stack<Cell> stack;
    private Cell current;
    private final Random random = new Random();
    private ArrayList<Cell> creationPath;       // not used in actual creation of the maze, but to trace
    // the path after the maze is created

    public DFSGenerator() {
        this.stack = new Stack<>();
        this.creationPath = new ArrayList<>();
        this.current = null;
    }

    /**
     * Returns an empty maze with no walls in it
     *
     * @param rows number of rows in the maze
     * @param columns number of columns in the maze
     * @return new instance of Maze
     */
    @Override
    public Maze initMaze(int rows, int columns) {
        System.out.println("initializing (" + rows + "x" + columns + " maze)...");
        return new Maze(rows, columns);
    }

    @Override
    public Maze generate(int rows, int columns) {
        System.out.println("Generating maze using " + toString());
        Maze maze = initMaze(rows, columns);
        this.current = maze.getCell(0, 0);
        current.setVisited();
        this.stack.push(current);

        System.out.println("generating...");
        long startTime = System.currentTimeMillis();
        int maxStackSize = 1;

        while (!stack.isEmpty()) {
            creationPath.add(current);
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

        // update the neighbour graph to match the generated maze
        maze.updateGraph();
        
        return maze;
    }

    @Override
    public ArrayList<Cell> creationPath() {
        return this.creationPath;
    }

    private Cell selectRandomNeighbour(Cell cell) {
        ArrayList<Cell> neighbours = cell.getNonVisitedNeighbours();
        int n = random.nextInt(neighbours.size());
        return neighbours.get(n);
    }

    @Override
    public String toString() {
        return "Depth-First Search Maze Generator";
    }
}
