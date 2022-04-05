package mazegenerator.ui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import mazegenerator.util.Cell;
import mazegenerator.util.DFSGenerator;
import mazegenerator.util.Generator;
import mazegenerator.util.Maze;
import mazegenerator.util.Wall;

public class MazeGeneratorGUI extends Application {

    private Maze maze;

    private final int ROWS = 50;
    private final int COLUMNS = 70;
    private final int CELL_SIZE = 10;
    private final int WALL_SIZE = 2;

    private final int WINDOW_WIDTH = COLUMNS * (CELL_SIZE + WALL_SIZE) + WALL_SIZE;
    private final int WINDOW_HEIGHT = ROWS * (CELL_SIZE + WALL_SIZE) + WALL_SIZE;
    private final Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
    private final Label infoBar = new Label();
    private BorderPane pane = new BorderPane();
    
    private boolean generated = false;
    private int pathIndex = 0;
    private double anchorX;
    private double anchorY;

    private Generator generator = new DFSGenerator();

    @Override
    public void start(Stage stage) {

        HBox tools = tools();
        Circle pathPointer = new Circle(0, 0, 5);
        pathPointer.setFill(Color.LIME);
        pathPointer.setVisible(false);

        pane.setTop(tools);
        pane.setCenter(canvas);
        pane.getChildren().add(pathPointer);

        infoBar.setText("Click generate to generate a new maze...");

        pane.setPadding(new Insets(10, 10, 10, 10));
 
        new AnimationTimer() {
            public void handle(long now) {
                if (generated && pathIndex < generator.path().size()) {
                    pathPointer.setVisible(true);
                    Cell c = generator.path().get(pathIndex);
                    pathIndex++;
                    
                    double x = anchorX + c.column() * (CELL_SIZE + WALL_SIZE);
                    double y = anchorY + c.row() * (CELL_SIZE + WALL_SIZE);
                   
                    pane.getChildren().add(new Rectangle(x + WALL_SIZE + 1, y + WALL_SIZE + 1, CELL_SIZE - WALL_SIZE, CELL_SIZE - WALL_SIZE));
                    pathPointer.setTranslateX(x + CELL_SIZE / 2);
                    pathPointer.setTranslateY(y + CELL_SIZE / 2);
                    infoBar.setText("cell " + c.toString());
                }
            }
        }.start();

        Scene scene = new Scene(pane);
        stage.setTitle(generatorInfo());
        stage.setScene(scene);
        stage.show();
    }

    private void drawMaze(Maze maze) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        System.out.println("Drawing " + maze);

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        gc.setFill(Color.BLACK);
        for (Cell cell : maze.cells()) {
            drawWalls(gc, cell);
        }
    }

    private void drawWalls(GraphicsContext gc, Cell cell) {
        int r = cell.row();
        int c = cell.column();

        if (cell.hasWall(Wall.TOP)) {
            gc.fillRect(c * (CELL_SIZE + WALL_SIZE), r * (CELL_SIZE + WALL_SIZE), CELL_SIZE + WALL_SIZE, WALL_SIZE);
        }
        if (cell.hasWall(Wall.BOTTOM)) {
            gc.fillRect(c * (CELL_SIZE + WALL_SIZE), (r + 1) * (CELL_SIZE + WALL_SIZE), CELL_SIZE + WALL_SIZE, WALL_SIZE);
        }
        if (cell.hasWall(Wall.LEFT)) {
            gc.fillRect(c * (CELL_SIZE + WALL_SIZE), r * (CELL_SIZE + WALL_SIZE), WALL_SIZE, CELL_SIZE + WALL_SIZE);
        }
        if (cell.hasWall(Wall.RIGHT)) {
            gc.fillRect((c + 1) * (CELL_SIZE + WALL_SIZE), r * (CELL_SIZE + WALL_SIZE), WALL_SIZE, CELL_SIZE + WALL_SIZE);
        }
    }

    private HBox tools() {
        HBox t = new HBox();
        Button generateButton = new Button("generate");
        generateButton.setOnAction(e -> {
            this.maze = generator.generate(ROWS, COLUMNS);
            drawMaze(maze);
            anchorX = canvas.getLocalToParentTransform().getTx();
            anchorY = canvas.getLocalToParentTransform().getTy();
            generated = true;
            infoBar.setText("generated " + maze);
        });

        t.getChildren().addAll(generateButton, infoBar);
        t.setSpacing(20);

        return t;
    }

    private String generatorInfo() {
        return this.generator.toString() + " - " + this.ROWS + "x" + this.COLUMNS;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
