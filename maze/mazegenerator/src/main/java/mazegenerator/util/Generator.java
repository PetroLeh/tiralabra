

package mazegenerator.util;

import java.util.ArrayList;

public interface Generator {
    
    Maze initMaze(int rows, int columns);
    
    Maze generate(int rows, int columns);
    
    ArrayList<Cell> creationPath();
}
