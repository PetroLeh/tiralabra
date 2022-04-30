package mazegenerator.ui;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import mazegenerator.util.Cell;
import mazegenerator.util.DFSGenerator;
import mazegenerator.util.Generator;
import mazegenerator.util.KruskalGenerator;
import mazegenerator.util.Maze;
import mazegenerator.util.Wall;

public class MazeGeneratorGUI extends Application {

    private Maze maze;

    private final int ROWS = 50;
    private final int COLUMNS = 50;
    private final int CELL_SIZE = 10;
    private final int WALL_SIZE = 2;

    private final int WINDOW_WIDTH = COLUMNS * (CELL_SIZE + WALL_SIZE) + WALL_SIZE;
    private final int WINDOW_HEIGHT = ROWS * (CELL_SIZE + WALL_SIZE) + WALL_SIZE;

    private final Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
    private final Label infoBar = new Label();
    private final BorderPane pane = new BorderPane();

    private boolean generated = false;
    private int pathIndex = 0;

    private Generator dfsGenerator = new DFSGenerator();
    private Generator kruskalGenerator = new KruskalGenerator();
    private ArrayList<Cell> path;

    EventHandler generateMazeUsingDFS = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            maze = dfsGenerator.generate(ROWS, COLUMNS);
            path = dfsGenerator.creationPath();
            generated = true;
            cleanCanvas(canvas);
            infoBar.setText("generated " + maze);
        }
    };

    EventHandler generateMazeUsingKruskal = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            maze = kruskalGenerator.generate(ROWS, COLUMNS);
            path = kruskalGenerator.creationPath();
            generated = true;
            cleanCanvas(canvas);
            infoBar.setText("generated " + maze);
        }
    };

    @Override
    public void start(Stage stage) {
        HBox tools = tools();
        pane.setTop(tools);
        pane.setCenter(canvas);
        infoBar.setText("Click generate to generate a new maze...");
        pane.setPadding(new Insets(10, 10, 10, 10));

        GraphicsContext gc = canvas.getGraphicsContext2D();

        new AnimationTimer() {
            public void handle(long now) {
                if (generated && pathIndex < path.size()) {
                    Cell c = path.get(pathIndex);
                    pathIndex++;

                    drawWalls(gc, c);
                    infoBar.setText("cell " + c.toString());
                }
            }
        }.start();

        Scene scene = new Scene(pane);
        stage.setTitle(generatorInfo());
        stage.setScene(scene);
        stage.show();
    }

    private void drawWalls(GraphicsContext gc, Cell cell) {
        int r = cell.row();
        int c = cell.column();
        gc.setFill(Color.BLACK);
        if (cell.hasWall(Wall.BOTTOM)) {
            gc.fillRect(c * (CELL_SIZE + WALL_SIZE), (r + 1) * (CELL_SIZE + WALL_SIZE), CELL_SIZE + WALL_SIZE, WALL_SIZE);
        }
        if (cell.hasWall(Wall.LEFT)) {
            gc.fillRect(c * (CELL_SIZE + WALL_SIZE), r * (CELL_SIZE + WALL_SIZE), WALL_SIZE, CELL_SIZE + WALL_SIZE);
        }
    }

    private HBox tools() {
        HBox t = new HBox();
        t.setSpacing(20);
        Button dfsGenerateButton = new Button("generate (Depth First Search)");
        dfsGenerateButton.setOnAction(generateMazeUsingDFS);
        
        Button kruskalGenerateButton = new Button("generate (Kruskal)");
        kruskalGenerateButton.setOnAction(generateMazeUsingKruskal);
        
        t.getChildren().addAll(dfsGenerateButton, kruskalGenerateButton, infoBar);
        return t;
    }

    private void cleanCanvas(Canvas canvas) {
        pathIndex = 0;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private String generatorInfo() {
        return "Maze Generator" + " - " + this.ROWS + "x" + this.COLUMNS;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
