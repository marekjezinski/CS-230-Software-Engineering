package CS230;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Sample application that demonstrates the use of JavaFX Canvas for a Game.
 * This class is intentionally not structured very well. This is just a starting point to show
 * how to draw an image on a canvas, respond to arrow key presses, use a tick method that is
 * called periodically, and use drag and drop.
 *
 * Do not build the whole application in one file. This file should probably remain very small.
 *
 * @author Liam O'Reilly
 */
public class Main extends Application {
    // The dimensions of the window
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;

    // The dimensions of the canvas
    private static final int CANVAS_WIDTH = 750;
    private static final int CANVAS_HEIGHT = 500;

    // The width and height (in pixels) of each cell that makes up the game.
    private static final int GRID_CELL_WIDTH = 25;
    private static final int GRID_CELL_HEIGHT = 25;

    // The canvas in the GUI. This needs to be a global variable
    // (in this setup) as we need to access it in different methods.
    // We could use FXML to place code in the controller instead.
    private Canvas canvas;

    // Loaded images
    private Image playerImage;
    private Image clock;
    private Image loot;
    private Image door;

    // X and Y coordinate of player on the grid.
    private int playerX = 0;
    private int playerY = 0;

    // Timeline which will cause tick method to be called periodically.
    private Timeline tickTimeline;
    private Timeline timerTimeline;



    private ArrayList<Integer> clockParams = new ArrayList<Integer>();
    private ArrayList<Integer> lootParams = new ArrayList<Integer>();
    private ArrayList<Integer> doorParams = new ArrayList<Integer>();

    private int timerLeft;

    private int score = 0;

    Map level = new Map("15x10.txt");

    Clock c = new Clock("15x10.txt");

    Loot l = new Loot("15x10.txt");

    Timer t = new Timer("15x10.txt");

    Door d = new Door("15x10.txt");


    private boolean hasGameStarted = false;
    private Text timerText = new Text();
    private Text scoreText = new Text();


    /**
     * Set up the new application.
     * @param primaryStage The stage that is to be used for the application.
     */
    public void start(Stage primaryStage) throws URISyntaxException {
        this.clockParams = c.clockSetter();
        this.lootParams = l.lootSetter();
        this.timerLeft = t.timerSetter();
        this.doorParams = d.doorSetter();
        // Load images. Note we use png images with a transparent background.
        playerImage = new Image(getClass().getResource("player.png").toURI().toString());

        clock = new Image(getClass().getResource("clock.png").toURI().toString());
        loot = new Image(getClass().getResource("placeholder.png").toURI().toString());
        door = new Image(getClass().getResource("door.png").toURI().toString());

        // Build the GUI
        Pane root = buildGUI();

        // Create a scene from the GUI
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Register an event handler for key presses.
        // This causes the processKeyEvent method to be called each time a key is pressed.
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event));

        // Register a tick method to be called periodically.
        // Make a new timeline with one keyframe that triggers the tick method every half a second.
        //tickTimeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> tick()));
        // Loop the timeline forever
        //tickTimeline.setCycleCount(Animation.INDEFINITE);
        // We start the timeline upon a button press.



        timerTimeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> timer()));
        timerTimeline.setCycleCount(Animation.INDEFINITE);

        // Display the scene on the stage
        drawGame();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Process a key event due to a key being pressed, e.g., to move the player.
     * @param event The key event that was pressed.
     */
    public void processKeyEvent(KeyEvent event) {
        // We change the behaviour depending on the actual key that was pressed.

        switch (event.getCode()) {
            case RIGHT:
                // Right key was pressed. So move the player right by one cell.
                if (playerX < 28 && this.hasGameStarted == true) {
                    playerX = level.moveRight(playerX, playerY);
                }
                break;

            case LEFT:
                // Left key was pressed. So move the player left by one cell.
                if (playerX > 0 && this.hasGameStarted == true) {
                    playerX = level.moveLeft(playerX, playerY);
                }
                break;

            case UP:
                // Up key was pressed. So move the player up by one cell.
                if (playerY > 0 && this.hasGameStarted == true) {
                    playerY = level.moveUp(playerX, playerY);
                }
                break;

            case DOWN:
                // Down key was pressed. So move the player down by one cell.
                if (playerY < 18 && this.hasGameStarted == true) {
                    playerY = level.moveDown(playerX, playerY);
                }
                break;

            default:
                // Do nothing for all other keys.
                break;
        }

        // Redraw game as the player may have moved.
        drawGame();

        // Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc.) responding to it.
        event.consume();
    }

    /**
     * Draw the game on the canvas.
     */
    public void drawGame() {
        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Set the background to gray.
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //Drawing cells on canvas
        Cell[][] cellsArray = level.getCellsArray();
        for (int y = 0; y < cellsArray[0].length; y++){
            for (int x = 0; x < cellsArray.length; x++) {
                gc.drawImage(cellsArray[x][y].getCellImage(), x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT,
                        GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
            }
        }




        for(int i = 0; i < clockParams.size(); i = i+2){
            gc.drawImage(clock, clockParams.get(i) * GRID_CELL_WIDTH, clockParams.get(i+1) * GRID_CELL_HEIGHT);
        }
        for(int i = 0; i < clockParams.size(); i = i+2){
            if (clockParams.get(i) == playerX){
                if (clockParams.get(i+1) == playerY){
                    clockParams.remove(i);
                    clockParams.remove(i);
                    this.timerLeft = this.timerLeft + 5;

                }
            }
        }

        for(int i = 0; i < lootParams.size(); i = i+2){
            gc.drawImage(loot, lootParams.get(i) * GRID_CELL_WIDTH, lootParams.get(i+1) * GRID_CELL_HEIGHT);
        }

        for(int i = 0; i < lootParams.size(); i = i+2){
            if (lootParams.get(i) == playerX){
                if (lootParams.get(i+1) == playerY){
                    lootParams.remove(i);
                    lootParams.remove(i);
                    this.score = this.score + 10;
                    scoreText.setText("Score: " + this.score);
                    scoreText.setFont(Font.font("arial",20));

                }
            }
        }

        for(int i = 0; i < doorParams.size(); i = i+2){
            gc.drawImage(door, doorParams.get(i) * GRID_CELL_WIDTH, doorParams.get(i+1) * GRID_CELL_HEIGHT);
        }


        // Draw player at current location
        gc.drawImage(playerImage, playerX * GRID_CELL_WIDTH, playerY * GRID_CELL_HEIGHT);
    }

    /**
     * Reset the player's location and move them back to (0,0).
     */
    public void resetPlayerLocation() {
        playerX = 0;
        playerY = 0;
        drawGame();
    }




    /**
     * This method is called periodically by the tick timeline
     * and would for, example move, perform logic in the game,
     * this might cause the bad guys to move (by e.g., looping
     * over them all and calling their own tick method).
     */
    public void tick() {
        // Here we move the player right one cell and teleport
        // them back to the left side when they reach the right side.
        playerX = playerX + 1;
        if (playerX > 11) {
            playerX = 0;
        }
        // We then redraw the whole canvas.
        drawGame();
    }

    public void timer(){
        if (this.timerLeft <= 6){
            timerText.setFill(Paint.valueOf("Red"));
        }
        if (this.timerLeft > 0) {
            this.timerLeft = this.timerLeft- 1;
            timerText.setText("Time remaining: " + this.timerLeft);
        }
        else {
            System.out.println("You ran out of time! GAME OVER!!!");
            System.out.println("You scored " + this.score + " points");
            System.exit(0);
        }
    }

    /**
     * React when an object is dragged onto the canvas.
     * @param event The drag event itself which contains data about the drag that occurred.
     */


    /**
     * Create the GUI.
     * @return The panel that contains the created GUI.
     */
    private Pane buildGUI() {
        // Create top-level panel that will hold all GUI nodes.
        BorderPane root = new BorderPane();

        // Create the canvas that we will draw on.
        // We store this as a global variable so other methods can access it.
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.setCenter(canvas);

        // Create a toolbar with some nice padding and spacing
        HBox toolbar = new HBox();
        toolbar.setSpacing(10);
        toolbar.setPadding(new Insets(10, 10, 10, 10));
        root.setTop(toolbar);

        // Create the toolbar content

        // Reset Player Location Button
        Button resetGameButton = new Button("Reset Game");
        toolbar.getChildren().add(resetGameButton);

        // Set up the behaviour of the button.
        resetGameButton.setOnAction(e -> {
            // We keep this method short and use a method for the bulk of the work.
            resetPlayerLocation();
        });






        // Tick Timeline buttons
        /*Button startTickTimelineButton = new Button("Start Ticks");
        Button stopTickTimelineButton = new Button("Stop Ticks");
        // We add both buttons at the same time to the timeline (we could have done this in two steps).
        toolbar.getChildren().addAll(startTickTimelineButton, stopTickTimelineButton);
        // Stop button is disabled by default
        stopTickTimelineButton.setDisable(true);

        // Set up the behaviour of the buttons.
        startTickTimelineButton.setOnAction(e -> {
            // Start the tick timeline and enable/disable buttons as appropriate.
            startTickTimelineButton.setDisable(true);
            tickTimeline.play();
            stopTickTimelineButton.setDisable(false);
        });

        stopTickTimelineButton.setOnAction(e -> {
            // Stop the tick timeline and enable/disable buttons as appropriate.
            stopTickTimelineButton.setDisable(true);
            tickTimeline.stop();
            startTickTimelineButton.setDisable(false);
        });*/






        Button startTimer = new Button("Start game");
        toolbar.getChildren().addAll(startTimer);
        startTimer.setOnAction(e -> {
            this.hasGameStarted = true;
            timerTimeline.play();
        });

        timerText.setText("Time remaining: " + this.timerLeft);
        timerText.setFont(Font.font("arial",20));
        toolbar.getChildren().add(timerText);

        scoreText.setText("Score: " + this.score);
        scoreText.setFont(Font.font("arial",20));
        toolbar.getChildren().add(scoreText);


        // Finally, return the border pane we built up.
        return root;
    }

    public static void main(String[] args) {
        launch(args);

    }
}