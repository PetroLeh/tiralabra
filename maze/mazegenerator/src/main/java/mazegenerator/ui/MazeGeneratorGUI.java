package mazegenerator.ui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import mazegenerator.util.Cell;
import mazegenerator.util.DFSGenerator;
import mazegenerator.util.Generator;
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
    private BorderPane pane = new BorderPane();

    private boolean generated = false;
    private int pathIndex = 0;
    private double anchorX;
    private double anchorY;

    private Generator generator = new DFSGenerator();

    @Override
    public void start(Stage stage) {
     
        HBox tools = tools();

        pane.setTop(tools);
        pane.setCenter(canvas);
        Circle pathPointer = new Circle(0, 0, CELL_SIZE / 2);
        pathPointer.setFill(Color.LIME);
        pathPointer.setVisible(false);
        pane.getChildren().add(pathPointer);

        infoBar.setText("Click generate to generate a new maze...");

        pane.setPadding(new Insets(10, 10, 10, 10));

        GraphicsContext gc = canvas.getGraphicsContext2D();
        new AnimationTimer() {
            public void handle(long now) {
                if (generated && pathIndex < generator.creationPath().size()) {
                    pathPointer.setVisible(true);
                    Cell c = generator.creationPath().get(pathIndex);
                    pathIndex++;

                    double x = anchorX + c.column() * (CELL_SIZE + WALL_SIZE) + CELL_SIZE / 2;
                    double y = anchorY + c.row() * (CELL_SIZE + WALL_SIZE) + CELL_SIZE / 2;
                    pathPointer.setTranslateX(x);
                    pathPointer.setTranslateY(y);

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
        Button generateButton = new Button("generate");
        generateButton.setOnAction(e -> {
            this.maze = generator.generate(ROWS, COLUMNS);

            anchorX = canvas.getLocalToParentTransform().getTx();
            anchorY = canvas.getLocalToParentTransform().getTy();
            generated = true;
            cleanCanvas(canvas);
            infoBar.setText("generated " + maze);
        });

        t.getChildren().addAll(generateButton, infoBar);
        t.setSpacing(20);

        return t;
    }

    private void cleanCanvas(Canvas canvas) {
        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private String generatorInfo() {
        return this.generator.toString() + " - " + this.ROWS + "x" + this.COLUMNS;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
