package mazegenerator.util;

public class TestGenerator implements Generator {

    @Override
    public Maze initMaze(int rows, int columns) {
        return new Maze(rows, columns);
    }
    
    @Override
    public String toString() {
        return "test generator";
    }
}
