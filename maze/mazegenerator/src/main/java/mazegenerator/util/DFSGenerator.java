package mazegenerator.util;

public class DFSGenerator implements Generator {

    public DFSGenerator() {

    }
    
    @Override
    public Maze initMaze(int rows, int columns) {
        return new Maze(rows, columns);
    }
    
    @Override
    public String toString() {
        return "Depth First Search Maze Generator";
    }
}
