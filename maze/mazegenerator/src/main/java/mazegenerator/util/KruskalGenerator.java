package mazegenerator.util;

import java.util.ArrayList;
import java.util.Random;

class Node {

    private int row, column;
    private Node parent;
    private ArrayList<Node> children;

    private int size;

    public Node(int row, int column) {
        this(row, column, null);
    }

    public Node(int row, int column, Node node) {
        this.row = row;
        this.column = column;
        this.parent = node;
        this.size = 1;
    }

    public Node getParent() {
        return this.parent;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public void addChild(Node node) {
        this.children.add(node);
        this.size += node.treeSize();
    }

    public Node getRoot(Node node) {
        if (this.isRoot()) {
            return this;
        }
        return getRoot(parent);
    }
    
    public ArrayList<Node> getChildren() {
        return this.children;
    }
    
    public boolean isConnectedWith(Node node) {
        return this.getParent() == node.getParent();
    }

    public int treeSize() {
        return this.size;
    }

    public int getColumn() {
        return this.column;
    }

    public int getRow() {
        return this.row;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ", size: " + this.size + ")";
    }
}

public class KruskalGenerator implements Generator {

    private Random r = new Random();

    private ArrayList<Node> randomNodes;
    private Node[][] nodes;
    private ArrayList<Cell> creationPath;       // not used in actual creation of the maze, but to trace
    // the path after the maze is created

    public KruskalGenerator() {
        this.creationPath = new ArrayList<>();
    }

    /**
     * Returns an empty maze with all walls
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

    private void initRandomNodes(int rows, int columns) {
        this.randomNodes = new ArrayList<>();
        ArrayList<Node> l = new ArrayList<>();
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                Node node = new Node(row, column);
                System.out.println(node);
                l.add(node);
                nodes[row][column] = node;
            }
        }

        while (!l.isEmpty()) {
            int i = r.nextInt(l.size());
            Node r = l.remove(i);
            System.out.println(r);
            this.randomNodes.add(r);
            
        }
    }

    public Maze generate(int rows, int columns) {
        System.out.println("Generating maze using " + toString());
        creationPath = new ArrayList<>();
        nodes = new Node[rows][columns];
        Maze maze = initMaze(rows, columns);
        initRandomNodes(rows, columns);
        
       // while (randomNodes.size() > 0) {
       //     maze = connectTrees(randomNodes.get(0), maze);
       //  }  
        
        return maze;
    }
    
    private Maze connectTrees(Node node, Maze maze) {

        return maze;
    }
    

    public ArrayList<Cell> creationPath() {
        return this.creationPath;
    }

    @Override
    public String toString() {
        return "Kruskal Maze Generator";
    }
}
