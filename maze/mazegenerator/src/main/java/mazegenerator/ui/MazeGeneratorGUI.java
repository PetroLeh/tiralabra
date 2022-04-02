package mazegenerator.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mazegenerator.util.DFSGenerator;
import mazegenerator.util.Generator;
import mazegenerator.util.Maze;

public class MazeGeneratorGUI extends Application {

    private final int ROWS = 100;
    private final int COLUMNS = 200;
    private final int CELL_SIZE = 5;
    private final int WALL_SIZE = 2;
    
    private final int WINDOW_WIDTH = COLUMNS * (CELL_SIZE + WALL_SIZE);
    private final int WINDOW_HEIGHT = ROWS * (CELL_SIZE + WALL_SIZE);
    
    private Generator generator = new DFSGenerator();

    @Override
    public void start(Stage stage) {
        
        Maze maze = generator.initMaze(ROWS, COLUMNS);
        
        Pane pane = new Pane();
 
        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        pane.getChildren().addAll(canvas);
        
        drawMaze(maze, canvas);
        
        Scene scene = new Scene(pane);
        stage.setTitle(generatorInfo());
        stage.setScene(scene);
        stage.show();
    }
    
    private void drawMaze(Maze maze, Canvas canvas) {
        GraphicsContext dc = canvas.getGraphicsContext2D();
        
        dc.setFill(Color.BLACK);
        dc.fillRect(10, 10, 10, 10);
    }
    
    private void setGenerator(Generator g) {
        this.generator = g;
    }
    
    private String generatorInfo() {
        return this.generator.toString() + " - " + this.ROWS + "x" + this.COLUMNS;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
