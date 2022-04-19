/*
 * Petro Lehtonen
 */

import mazegenerator.util.Cell;
import mazegenerator.util.Maze;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author lehtonep
 */
public class TestMaze {
    
    Maze maze;
    
    @Before
    public void setUp() {
        maze = new Maze(10, 10);
    }
    
    @Test
    public void initializedMazeExists() {
        assertNotNull(maze);
        assertEquals(maze.rows(), 10);
        assertEquals(maze.columns(), 10);
    }
    
    @Test
    public void getCellReturnsCorrectCell() {
        Cell c = maze.getCell(1, 2);
        assertEquals(c.row(), 1);
        assertEquals(c.column(), 2);
    }
    
    @Test
    public void getCellReturnsNullIfOutOfBounds() {
        Cell c = maze.getCell(-1, 0);
        assertNull(c);
    }
    
}
