package CS230;
import CS230.items.*;
import CS230.npc.SmartThief;
import CS230.npc.SmartThiefPath;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static java.lang.Math.ceil;

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

    // X and Y coordinate of player on the grid.
    private int playerX = 0;
    private int playerY = 0;

    // Timeline which will cause tick method to be called periodically.
    private Timeline tickTimeline;
    private Timeline timerTimeline;
    private Timeline scoreColourChanger;
    private Timeline bombTimeline;
    private Leaderboard l = new Leaderboard();
    private CurrentState c = new CurrentState();

    private int timerLeft;



    private int score = 0;
    private String username;

    private ArrayList<Item> items;
    private boolean hasGameStarted = false;
    private Text timerText = new Text();
    private Text scoreText = new Text();

    private MessageOfTheDay m;
    private String backgroundMusic = "gamemusic.mp3";
    private Media music = new Media(new File(this.backgroundMusic).toURI().toString());
    private MediaPlayer player = new MediaPlayer(this.music);


    private int currentLevelID = 0;
    private ArrayList<Map> levels = new ArrayList<Map>();
    Map currentLevel;
    //todo Change file name of map when NPC in mapreader is finished
    Map level1 = new Map("L0.0.txt");
    Map level2 = new Map("L1.0.txt");

    Map level3 = new Map("L2.0.txt");

    Map level4 = new Map("L0.0.txt");

    /**
     * Set up the new application.
     * @param primaryStage The stage that is to be used for the application.
     */
    public void start(Stage primaryStage) throws URISyntaxException {
        levels.add(level1);
        levels.add(level2);
        levels.add(level3);
        levels.add(level4);
        this.currentLevel = levels.get(currentLevelID);
        playerX = this.currentLevel.getPlayerStartX() * 2;
        playerY = this.currentLevel.getPlayerStartY() * 2;
        this.timerLeft = this.currentLevel.getTimerLeft();
        // Load images. Note we use png images with a transparent background.
        playerImage = new Image(getClass().getResource("player.png").toURI().toString());

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

        bombTimeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            ArrayList<Bomb> bombs = currentLevel.getBombs();
            for(int i = 0; i < bombs.size(); i++) {
                int secToExplode = bombs.get(i).getSecondsToExplode();
                if (secToExplode != -2) {
                    try {
                        if (secToExplode == 3) {
                            bombs.get(i).setImg(new Image(getClass().getResource("bomb3.png").toURI().toString()));
                            System.out.println("3");
                        }
                        else if (secToExplode == 2) {
                            bombs.get(i).setImg(new Image(getClass().getResource("bomb2.png").toURI().toString()));
                            System.out.println("2");
                        }
                        else if (secToExplode == 1) {
                            bombs.get(i).setImg(new Image(getClass().getResource("bomb1.png").toURI().toString()));
                            System.out.println("1");
                        }
                        else if (secToExplode == 0) {
                            bombs.get(i).setImg(new Image(getClass().getResource("bomb0.png").toURI().toString()));
                            System.out.println("0");
                            currentLevel.explodeBomb(i);

                        }
                        else if (secToExplode == -1) {
                            bombs.get(i).setX(-1);
                            bombs.get(i).setY(-1);
                        }
                    } catch (URISyntaxException e) {

                    }
                    bombs.get(i).setSecondsToExplode(secToExplode - 1);
                }
                currentLevel.setBombs(bombs);
                drawGame();
            }
        }));
        bombTimeline.setCycleCount(Animation.INDEFINITE);

        scoreColourChanger = new Timeline(new KeyFrame(Duration.millis(500), event -> scoreColour()));
        scoreColourChanger.setCycleCount(Animation.INDEFINITE);

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
                    playerX = currentLevel.moveRight(playerX, playerY);
                }
                break;

            case LEFT:
                // Left key was pressed. So move the player left by one cell.
                if (playerX > 0 && this.hasGameStarted == true) {
                    playerX = currentLevel.moveLeft(playerX, playerY);
                }
                break;

            case UP:
                // Up key was pressed. So move the player up by one cell.
                if (playerY > 0 && this.hasGameStarted == true) {
                    playerY = currentLevel.moveUp(playerX, playerY);
                }
                break;

            case DOWN:
                // Down key was pressed. So move the player down by one cell.
                if (playerY < 18 && this.hasGameStarted == true) {
                    playerY = currentLevel.moveDown(playerX, playerY);
                }
                break;

            default:
                // Do nothing for all other keys.
                break;
        }
        checkItems();
        // Redraw game as the player may have moved.
        drawGame();

        // Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc.) responding to it.
        event.consume();
    }

    /**
     * Method for checking if any items are going to be picked up and if
     * the bomb is going to be activated
     */
    private void checkItems() {
        timerLeft += currentLevel.checkClocks(playerX / 2, playerY / 2);
        //TODO: implement door and level progression

        if(currentLevel.checkDoor(playerX / 2, playerY / 2)>0){
            if( currentLevel.getLoots().size() == 0) {
                currentLevelID++;

                currentLevel = levels.get(currentLevelID);
                this.score = (int) (this.score + ceil(this.timerLeft / 3));
                c.levelSave(this.username, this.currentLevelID, this.score);
            }
        }
        score += currentLevel.checkLoots(playerX / 2, playerY / 2);
        scoreText.setText("Score: " + this.score);
        scoreText.setFont(Font.font("arial",20));
        currentLevel.checkRLever(playerX / 2, playerY / 2);
        currentLevel.checkWLever(playerX / 2, playerY / 2);
        if (currentLevel.isBombTriggered(playerX / 2, playerY / 2)) {
            bombTimeline.play();
        }
    }

    /**
     * Method for activating bomb explosions
     * @throws URISyntaxException
     */

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
        Cell[][] cellsArray = currentLevel.getCellsArray();
        for (int y = 0; y < cellsArray[0].length; y++){
            for (int x = 0; x < cellsArray.length; x++) {
                gc.drawImage(cellsArray[x][y].getCellImage(), x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
            }
        }

        Door door = currentLevel.getDoor();
        gc.drawImage(door.getImg(), door.getX() * GRID_CELL_WIDTH * 2, door.getY() * GRID_CELL_HEIGHT * 2);

        Gate rgate =  currentLevel.getRGate();
        gc.drawImage(rgate.getImg(), rgate.getX() * GRID_CELL_WIDTH * 2, rgate.getY() * GRID_CELL_HEIGHT * 2);

        Lever rlever =  currentLevel.getRLever();
        gc.drawImage(rlever.getImg(), rlever.getX() * GRID_CELL_WIDTH * 2, rlever.getY() * GRID_CELL_HEIGHT * 2);

        Gate wgate =  currentLevel.getWGate();
        gc.drawImage(wgate.getImg(), wgate.getX() * GRID_CELL_WIDTH * 2, wgate.getY() * GRID_CELL_HEIGHT * 2);

        Lever wlever =  currentLevel.getWLever();
        gc.drawImage(wlever.getImg(), wlever.getX() * GRID_CELL_WIDTH * 2, wlever.getY() * GRID_CELL_HEIGHT * 2);

        ArrayList<Bomb> bombs = currentLevel.getBombs();
        bombs.forEach(e ->  gc.drawImage(e.getImg(),
                e.getX() * GRID_CELL_WIDTH * 2, e.getY() * GRID_CELL_HEIGHT * 2));

        ArrayList<Clock> clocks = currentLevel.getClocks();
        clocks.forEach(e ->  gc.drawImage(e.getImg(),
                e.getX() * GRID_CELL_WIDTH * 2, e.getY() * GRID_CELL_HEIGHT * 2));

        ArrayList<Loot> loots = currentLevel.getLoots();
        loots.forEach(e ->  gc.drawImage(e.getImg(),
                e.getX() * GRID_CELL_WIDTH * 2, e.getY() * GRID_CELL_HEIGHT * 2));


        // Draw player at current location
        gc.drawImage(playerImage, playerX * GRID_CELL_WIDTH, playerY * GRID_CELL_HEIGHT);

        gc.setFill(Color.GRAY);
        //Draw lines in canvas
        for(int x = -1; x < CANVAS_WIDTH; x += 50) {
            gc.fillRect(x, 0, 3, canvas.getHeight());
        }
        for(int y = -1; y < CANVAS_HEIGHT; y += 50) {
            gc.fillRect(0, y, canvas.getWidth(), 3);
        }
    }

    /**
     * Reset the player's location and move them back to (0,0).
     */
    public void resetPlayerLocation() {
        playerX = 0;
        playerY = 0;
        drawGame();
    }

    public void resetGame() {
        //TODO: FIX issue of map not reloading items on it
        currentLevelID = 0;
        currentLevel = levels.get(0);

        resetPlayerLocation();
        score = 0;
        timerTimeline.stop();
        hasGameStarted = false;
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
        if (this.timerLeft > 6){
            timerText.setFill(Paint.valueOf("Black"));
        }
        if (this.timerLeft > 0) {
            this.timerLeft = this.timerLeft - 1;
            timerText.setText("Time remaining: " + this.timerLeft + " Level " + (currentLevelID + 1) + "Loot:" + ( currentLevel.getLoots().size()));
        }
        else {
            timerTimeline.stop();
            scoreColourChanger.stop();
            gameOver();
        }
    }
    public void gameOver(){
        System.out.println("GAME OVER!!!");
        System.out.println("You scored " + this.score + " points");
        l.addScore(username,score);
        System.out.println("---------------------------------");
        System.out.println("Leaderboard:");
        l.getTopScores();
        this.player.play();
        System.exit(0);
    }

    public void scoreColour(){
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        scoreText.setFill(Color.rgb(r,g,b));
    }



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
            resetGame();

        });

        Label labelUsername = new Label("Username");
        TextField usernameIn = new TextField();
        toolbar.getChildren().addAll(labelUsername,usernameIn);

        Button startButton = new Button("Start!");
        toolbar.getChildren().addAll(startButton);
        startButton.setOnAction(e -> {
            if(usernameIn.getText().equals("")){
                System.err.println("ERROR! Player name is required!");
            }
            else{
                this.username = usernameIn.getText();
                toolbar.getChildren().removeAll(labelUsername,usernameIn);
                boolean playedBefore = c.newPlayer(username);
                if (playedBefore == true) {
                    Label labelLevel = new Label("Level select:");
                    Button l1 = new Button("1");
                    Button l2 = new Button("2");
                    Button l3 = new Button("3");
                    Button l4 = new Button("4");
                    Button recent = new Button("Recent");
                    toolbar.getChildren().addAll(l1,l2,l3,recent);

                    l1.setOnAction(f -> {
                        toolbar.getChildren().removeAll(l1,l2,l3,recent);
                        begin(0,0);

                    });

                    l2.setOnAction(g -> {
                        if(c.checkLevel(username) > 0){
                            toolbar.getChildren().removeAll(l1,l2,l3,recent);
                            begin(0,1);
                        }
                        else {
                            System.err.println("ERROR! You haven't unlocked this yet");
                        }

                    });

                    l3.setOnAction(h -> {
                        if((c.checkLevel(username)) > 1){
                            toolbar.getChildren().removeAll(l1,l2,l3,recent);
                            begin(0,2);
                        }
                        else {
                            System.err.println("ERROR! You haven't unlocked this yet");
                        }

                    });
                    l4.setOnAction(g -> {
                        if(c.checkLevel(username) > 0){
                            toolbar.getChildren().removeAll(l1,l2,l3,recent);
                            begin(0,3);
                        }
                        else {
                            System.err.println("ERROR! You haven't unlocked this yet");
                        }

                    });

                    recent.setOnAction(i -> {
                        toolbar.getChildren().removeAll(l1,l2,l3,recent);
                        ArrayList<Integer> reload = c.levelLoad(username);
                        begin(reload.get(1),reload.get(0));

                    });
                }
                else {
                    begin(0,0);
                }


            }
        });

        timerText.setText("Time remaining: " + this.timerLeft );
        timerText.setFont(Font.font("arial",20));
        toolbar.getChildren().add(timerText);

        scoreText.setText("Score: " + this.score );
        scoreText.setFont(Font.font("arial",20));
        toolbar.getChildren().add(scoreText);



        // Finally, return the border pane we built up.
        return root;
    }

    public void begin(int scoreIn, int levelIn){
        this.hasGameStarted = true;
        timerTimeline.play();
        scoreColourChanger.play();
        this.player.play();
        this.score = scoreIn;
        this.currentLevelID = levelIn;
        timerText.setText("Time remaining: " + this.timerLeft + " Level " + (currentLevelID + 1));
        scoreText.setText("Score: " + this.score);
        currentLevel = levels.get(currentLevelID);
        drawGame();

    }

    public static void main(String[] args) throws IOException {
        //MessageOfTheDay m = new MessageOfTheDay();
        //m.getMessage();
        launch(args);

    }
}