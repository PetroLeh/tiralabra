
import mazegenerator.util.Cell;
import mazegenerator.util.Wall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class TestCell {
    

@Test
public void initializedCellHasWalls() {
    Cell c = new Cell(1, 1);
    
    assertTrue(c.hasWall(Wall.LEFT));
    assertTrue(c.hasWall(Wall.BOTTOM));
}

@Test
public void wallsGetRemoved() {
    Cell c = new Cell(1, 1);
    
    c.removeWall(Wall.LEFT);
    c.removeWall(Wall.BOTTOM);
    
    assertFalse(c.hasWall(Wall.LEFT));
    assertFalse(c.hasWall(Wall.BOTTOM));
}

@Test
public void addingNeighboursWorks() {
    Cell a = new Cell(1, 1);
    Cell b = new Cell(1, 2);
    Cell c = new Cell(4, 5);
    
    assertNotNull(a.neighbours());
    assertEquals(a.neighbours().size(), 0);
    
    a.addNeighbour(b);
    assertEquals(a.neighbours().size(), 1);
    assertEquals(a.neighbours().get(0), b);
    
    a.addNeighbour(c);  // Should not be added since they are not next to each other
    assertEquals(a.neighbours().size(), 1);    
}
    
}
