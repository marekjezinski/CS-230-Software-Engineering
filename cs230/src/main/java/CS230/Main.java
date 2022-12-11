package CS230;
import CS230.items.*;
import CS230.npc.*;
import CS230.saveload.ProfileFileManager;
//import CS230.saveload.FileHandler;
import CS230.saveload.PlayerProfile;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.net.URISyntaxException;

import java.util.ArrayList;


import static java.lang.Math.ceil;

/**
 *Main method for the program based on the sample program provided
 *
 * @author Liam O'Reilly
 * @author Tom Stevens
 * @author Kam Leung
 */
//TODO: please put names here
public class Main extends Application {
    // The dimensions of the window
    private static final int WINDOW_WIDTH = 1200;
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
    private Player player1;

    // Timeline which will cause tick method to be called periodically.
    private Timeline tickTimeline;
    private Timeline timerTimeline;
    private Timeline scoreColourChanger;
    private Timeline bombTimeline;
    private Leaderboard l = new Leaderboard();
    private ProfileFileManager profiles = new ProfileFileManager();

    private int timerLeft;
    private Loot currentGoal;

    //SmartThief
    private int pathGoalX,pathGoalY;
    public static Queue<int[]> path = new LinkedList<>();
    //used to iterate through all the directions of grid
    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; 

    private Image SMImage =  new Image(getClass().
            getResource("smartThief.png").toURI().toString());
    //create smart thief at 0,0
    private SmartThief sThief = new SmartThief(1,1,SMImage);


    private int score = 0;
    private String username;
    private PlayerProfile p1Profile;

    private ArrayList<Item> items;
    ArrayList<Integer> reload;
    private boolean hasGameStarted = false;
    private Text timerText = new Text();
    private Text scoreText = new Text();
    private Text errorText = new Text();
    private Text messageOfDayText = new Text();

    private MessageOfTheDay m;
    private MediaPlayer player = new MediaPlayer(new
            Media(new File("gamemusic.mp3").
            toURI().toString()));

    private int currentLevelID = 0;
    private ArrayList<Map> levels = new ArrayList<>();
    Map currentLevel;
    //todo Change file name of map when NPC in mapreader is finished
    Map level1 = new Map("L0.0.txt");
    Map level2 = new Map("L1.0.txt");

    Map level3 = new Map("L2.0.txt");

    Map level4 = new Map("L3.0.txt");

    private boolean restartCheck = false;

    public Main() throws URISyntaxException {
    }

    /**
     * Set up the new application.
     * @param primaryStage The stage that is to be used for the application.
     */
    public void start(Stage primaryStage) throws URISyntaxException, IOException {
        levels.add(level1);
        levels.add(level2);
        levels.add(level3);
        levels.add(level4);
        this.currentLevel = levels.get(currentLevelID);
        this.timerLeft = this.currentLevel.getTimerLeft();
        // Load images. Note we use png images with a transparent background.
        playerImage = new Image(getClass().
                getResource("player.png").toURI().toString());


        //create player
        player1 = new Player(this.
                currentLevel.getPlayerX() * 2,
                this.currentLevel.getPlayerY() * 2,playerImage);

        // Build the GUI
        Pane root = buildGUI();

        // Create a scene from the GUI
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Register an event handler for key presses.
        // This causes the processKeyEvent method to be called each time a key is pressed.
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event));

        // Register a tick method to be called periodically.
        // Make a new timeline with one keyframe
        // that triggers the tick method every half a second.
        //tickTimeline = new Timeline
        // (new KeyFrame(Duration.millis(1000), event -> tick()));
        // Loop the timeline forever
        //tickTimeline.setCycleCount(Animation.INDEFINITE);
        // We start the timeline upon a button press.



        timerTimeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            timer();
            ArrayList<FlyingAssassin> fl = currentLevel.getFlyingAssassins();
            for(int i = 0; i < fl.size(); i++) {
                fl.get(i).movement(currentLevel);
            }
            currentLevel.setFlyingAssassins(fl);
            if(currentLevel.isFlAsCollidedWPlayer()) {
                gameOver();
            }
            ArrayList<Thief> th = currentLevel.getThieves();
            for(int i = 0; i < th.size(); i++) {
                th.get(i).movement(currentLevel);
            }
            currentLevel.setThieves(th);
            for(int i = 0; i < currentLevel.getThieves().size(); i++) {
                Thief currentThief = currentLevel.getThieves().get(i);
                if (currentLevel.isBombTriggered(
                        currentThief.getX(), currentThief.getY())) {
                    bombTimeline.play();
                }
            }
            currentLevel.isFlCollidedWithNPC();
            //create path
            SmartThiefPath sPath = new SmartThiefPath(currentLevel);
            if(currentLevel.getLoots().size() > 0) {
                //search for nearest item
                currentGoal = sPath.findClosestLoot(currentLevel, sThief);
            }

            //x + y co-ords of the nearest item
            int goalX = sPath.getPathGoalX();
            int goalY = sPath.getPathGoalY();
            // if a path is found which isn't 0,0
            // then move randomly through the level
            if (SmartThiefSearch.bfs(currentLevel.getTilesArray(),
                    sThief.getX(),sThief.getY(),sPath.
                            getPathGoalX(),
                    sPath.getPathGoalY()) && !(goalX==0 && goalY ==0)){
                path = SmartThiefSearch.getQueue();
                //System.out.println("path X;
                // "+sPath.getPathGoalX()+" Y: "
                // + sPath.getPathGoalY());
                // currently treats a player like loot idk why
            } else {
                if (currentLevel.getLoots().size() > 0) {
                    System.out.println("No path found");//for debugging
                    sThief.randomMovement(currentLevel);

                }

            }



            drawGame();
        }));
        timerTimeline.setCycleCount(Animation.INDEFINITE);

        bombTimeline = new Timeline(new
                KeyFrame(Duration.millis(1000), event -> {
            ArrayList<Bomb> bombs = currentLevel.getBombs();
            for(int i = 0; i < bombs.size(); i++) {
                int secToExplode = bombs.get(i).getSecondsToExplode();
                if (secToExplode != -2) {
                    try {
                        if (secToExplode == 3) {
                            bombs.get(i).
                                    setImg(new Image(getClass().
                                            getResource("bomb3.png").
                                            toURI().toString()));
                        }
                        else if (secToExplode == 2) {
                            bombs.get(i).setImg(new Image(getClass().
                                    getResource("bomb2.png").
                                    toURI().toString()));
                        }
                        else if (secToExplode == 1) {
                            bombs.get(i).setImg(new Image(getClass().
                                    getResource("bomb1.png").
                                    toURI().toString()));
                        }
                        else if (secToExplode == 0) {
                            bombs.get(i).setImg(new Image(getClass().
                                    getResource("bomb0.png").
                                    toURI().toString()));
                            currentLevel.explodeBomb(i);

                        }
                        else if (secToExplode == -1) {
                            bombs.get(i).setX(-1);
                            bombs.get(i).setY(-1);
                        }
                    } catch (URISyntaxException e) {
                        System.err.println("Wrong bomb image!");
                        System.exit(1);
                    }
                    bombs.get(i).setSecondsToExplode(secToExplode - 1);
                }
                currentLevel.setBombs(bombs);
                drawGame();
            }
        }));
        bombTimeline.setCycleCount(Animation.INDEFINITE);

        scoreColourChanger = new Timeline(
                new KeyFrame(Duration.millis(500), event -> scoreColour()));
        scoreColourChanger.setCycleCount(Animation.INDEFINITE);

        // Display the scene on the stage
        drawGame();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Process a key event due to a key being pressed, e.g.,
     * to move the player.
     * @param event The key event that was pressed.
     */
    public void processKeyEvent(KeyEvent event) {
        // We change the behaviour depending on the actual key that
        // was pressed.
        switch (event.getCode()) {
            case RIGHT:
                // Right key was pressed. So move the player
                // right by one cell.
                if (player1.getX() < 28 && this.hasGameStarted) {
                    player1.setX(currentLevel.
                            moveRight(player1.getX(), player1.getY()));
                }
                break;

            case LEFT:
                // Left key was pressed. So move the player left by one cell.
                if (player1.getX() > 0 && this.hasGameStarted ) {
                    player1.setX(currentLevel.
                            moveLeft(player1.getX(), player1.getY()));
                }
                break;

            case UP:
                // Up key was pressed. So move the player up by one cell.
                if (player1.getY() > 0 && this.hasGameStarted) {
                    player1.setY(currentLevel.
                            moveUp(player1.getX(), player1.getY()));
                }
                break;

            case DOWN:
                // Down key was pressed. So move the player down by one cell.
                if (player1.getY() < 18 && this.hasGameStarted) {
                    player1.setY(currentLevel.
                            moveDown(player1.getX(), player1.getY()));
                }
                break;

            default:
                // Do nothing for all other keys.
                break;
        }
        if(currentLevel.isFlAsCollidedWPlayer()) {
            gameOver();
        }
        checkItems();
        profiles.updateMaxLvl(username, currentLevelID);
        profiles.updateMaxScore(username, this.score);
        // Redraw game as the player may have moved.
        drawGame();



        // Consume the event.
        // This means we mark it as dealt with.
        // his stops other GUI nodes (buttons etc.) responding to it.
        event.consume();
    }

    /**
     * Method for checking if any items are going to be picked up and if
     * the bomb is going to be activated.
     * It also autosaves the current progress
     */
    private void checkItems() {
        playerX = player1.getX() / 2;
        playerY = player1.getY() / 2;
        timerLeft += currentLevel.checkClocks(playerX , playerY);
        //TODO: implement door and level progression

        if(currentLevel.checkDoor(playerX, playerY)>0){
            if( currentLevel.getLoots().size() == 0 &&
                    currentLevel.checklever()) {
                currentLevelID++;

                currentLevel = levels.get(currentLevelID);
                player1.setX(currentLevel.getPlayerX());
                player1.setY(currentLevel.getPlayerY());
                timerLeft = currentLevel.getStartTimer();

                this.score = (int) (this.score + ceil(this.timerLeft / 3));
                System.out.println(this.score);
            }
        }
        currentLevel.checkLoots(sThief.getX(), sThief.getY());
        score += currentLevel.checkLoots(playerX, playerY );
        scoreText.setText("Score: " + this.score);
        scoreText.setFont(Font.font("arial",20));
        currentLevel.checkRLever(playerX , playerY );
        currentLevel.checkWLever(playerX , playerY );
        if (currentLevel.isBombTriggered(playerX , playerY)) {
            bombTimeline.play();
        }
        String bombinMap = "";
        for (int i = 0; i < this.currentLevel.getBombs().size(); i++) {
            bombinMap = bombinMap +
                    this.currentLevel.getBombs().get(i).getX() + " ";
        }
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
        Cell[][] cellsArray = currentLevel.getCellsArray();
        for (int y = 0; y < cellsArray[0].length; y++){
            for (int x = 0; x < cellsArray.length; x++) {
                gc.drawImage(cellsArray[x][y].getCellImage(),
                        x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
            }
        }

        Door door = currentLevel.getDoor();
        gc.drawImage(door.getImg(), door.getX()
                * GRID_CELL_WIDTH * 2, door.getY() * GRID_CELL_HEIGHT * 2);

        Gate rgate =  currentLevel.getRGate();
        gc.drawImage(rgate.getImg(), rgate.getX()
                * GRID_CELL_WIDTH * 2, rgate.getY() * GRID_CELL_HEIGHT * 2);

        Lever rlever =  currentLevel.getRLever();
        gc.drawImage(rlever.getImg(), rlever.getX()
                * GRID_CELL_WIDTH * 2, rlever.getY() * GRID_CELL_HEIGHT * 2);

        Gate wgate =  currentLevel.getWGate();
        gc.drawImage(wgate.getImg(), wgate.getX()
                * GRID_CELL_WIDTH * 2, wgate.getY() * GRID_CELL_HEIGHT * 2);

        Lever wlever =  currentLevel.getWLever();
        gc.drawImage(wlever.getImg(), wlever.getX()
                * GRID_CELL_WIDTH * 2, wlever.getY() * GRID_CELL_HEIGHT * 2);

        ArrayList<Bomb> bombs = currentLevel.getBombs();
        bombs.forEach(e ->  gc.drawImage(e.getImg(),
                e.getX() * GRID_CELL_WIDTH * 2, e.getY() * GRID_CELL_HEIGHT * 2));

        ArrayList<Clock> clocks = currentLevel.getClocks();
        clocks.forEach(e ->  gc.drawImage(e.getImg(),
                e.getX() * GRID_CELL_WIDTH * 2, e.getY() *
                        GRID_CELL_HEIGHT * 2));

        ArrayList<Loot> loots = currentLevel.getLoots();
        loots.forEach(e ->  gc.drawImage(e.getImg(),
                e.getX() * GRID_CELL_WIDTH * 2, e.getY() *
                        GRID_CELL_HEIGHT * 2));

        ArrayList<FlyingAssassin> flyingAssassins = currentLevel.getFlyingAssassins();
        flyingAssassins.forEach(e ->  gc.drawImage(e.getImg(),
                e.getX() * GRID_CELL_WIDTH * 2, e.getY() *
                        GRID_CELL_HEIGHT * 2));

        ArrayList<Thief> thieves = currentLevel.getThieves();
        thieves.forEach(e ->  gc.drawImage(e.getImg(),
                e.getX() * GRID_CELL_WIDTH * 2, e.getY() *
                        GRID_CELL_HEIGHT * 2));

        gc.drawImage(player1.getCharImage(), player1.getX()
                * GRID_CELL_WIDTH, player1.getY() *
                GRID_CELL_HEIGHT);

        int[] smThiefCoords = path.poll();
        if (smThiefCoords != null) {
            sThief.setX(smThiefCoords[0]);
            sThief.setY(smThiefCoords[1]);
            gc.drawImage(sThief.getImg(), smThiefCoords[0]*2
                    * GRID_CELL_WIDTH, smThiefCoords[1]*2 *
                    GRID_CELL_HEIGHT);
        } else {
            gc.drawImage(sThief.getImg(), sThief.getX()*2
                    * GRID_CELL_WIDTH, sThief.getY() * 2 *
                    GRID_CELL_HEIGHT);
        }

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

    /**
     * Method that updates the timer
     */
    public void timer(){
        if (this.timerLeft <= 6){
            timerText.setFill(Paint.valueOf("Red"));
        }
        if (this.timerLeft > 6){
            timerText.setFill(Paint.valueOf("Black"));
        }
        if (this.timerLeft > 0) {
            this.timerLeft = this.timerLeft - 1;
            this.timerText.setText("Time remaining: " + this.timerLeft + "| Level "
                    + (this.currentLevelID + 1)
                    + "| Loot remaining: " + (this.currentLevel.
                    getLoots().size()));
        }
        else {
            this.timerTimeline.stop();
            this.scoreColourChanger.stop();
            gameOver();
        }
    }

    /**
     * Method to stop the game when player loses
     */
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

    /**
     * Method to change the colour of the score
     */
    public void scoreColour(){
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        this.scoreText.setFill(Color.rgb(r,g,b));
    }

    /**
     * Create the GUI.
     * @return The panel that contains the created GUI.
     */
    private Pane buildGUI() throws IOException {
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

        VBox stats = new VBox();
        stats.setSpacing(5);
        stats.setPadding(new Insets(10, 10, 10, 10));
        root.setLeft(stats);

        // Create the toolbar content
        TextField usernameIn = new TextField();
        Button createUser = new Button("Create user");
        Button loadUser = new Button("Play!");
        Button removeUser = new Button("Remove user");
        toolbar.getChildren().addAll(usernameIn, loadUser, createUser, removeUser);
        MessageOfTheDay m = new MessageOfTheDay();
        this.messageOfDayText.setText(m.getMessage());
        this.messageOfDayText.setFont(Font.font("arial",10));
        toolbar.getChildren().addAll(this.messageOfDayText);
        for (String profilesUsername : profiles.getUsernames()) {
            stats.getChildren().add(new Label(profilesUsername));
        }
        //create user
        createUser.setOnAction(e -> {
            if(!usernameIn.getText().equals("")) {
                profiles.addProfile(
                        usernameIn.getText().replaceAll("\\s",""));
                stats.getChildren().removeAll(stats.getChildren());
                for (String profilesUsername : profiles.getUsernames()) {
                    stats.getChildren().add(new Label(profilesUsername));
                }
            }
        });
        //load user
        loadUser.setOnAction(e -> {
            if(profiles.isValidName(usernameIn.getText())) {
                this.username = usernameIn.getText();
                toolbar.getChildren().clear();
                toolbar.getChildren().add(new Label("Choose level: "));
                for(int i = 0; i <= profiles.getMaxLvl(username); i++) {
                    Button b = new Button(String.valueOf(i + 1));
                    final int lvl = i;
                    b.setOnAction(f -> {
                        stats.getChildren().clear();
                        toolbar.getChildren().clear();
                        this.timerText.setText("");
                        this.timerText.setFont(Font.font("arial",20));
                        this.scoreText.setText("");
                        this.scoreText.setFont(Font.font("arial",20));
                        toolbar.getChildren().add(this.scoreText);
                        toolbar.getChildren().add(timerText);
                        begin(0,lvl);
                    });
                    toolbar.getChildren().add(b);
                }
                //stats.getChildren().clear();
            }
        });

        removeUser.setOnAction(e -> {
            profiles.removeProfile(usernameIn.getText());
            stats.getChildren().removeAll(stats.getChildren());
            for (String profilesUsername : profiles.getUsernames()) {
                stats.getChildren().add(new Label(profilesUsername));
            }
        });




        // Finally, return the border pane we built up.
        return root;
    }

    /**
     * Method for starting the game with appropriate variables from save
     * @param scoreIn
     * @param levelIn
     */
    public void begin(int scoreIn, int levelIn){
        this.hasGameStarted = true;
        timerTimeline.play();
        scoreColourChanger.play();
        this.player.play();
        this.score = scoreIn;


        this.currentLevelID = levelIn;
        this.timerText.setText("Time remaining: " + this.timerLeft + "| Level "
                + (this.currentLevelID + 1)
                + "| Loot remaining: " + (this.currentLevel.getLoots().size()));
        scoreText.setText("Score: " + this.score);

        currentLevel = levels.get(currentLevelID);
        player1.setX(currentLevel.getPlayerX());
        player1.setY(currentLevel.getPlayerY());
        timerLeft = currentLevel.getStartTimer();

        if (this.restartCheck == true) {
            playerX = this.reload.get(2);
            playerY = this.reload.get(3);
            timerLeft = this.reload.get(4);
            if (this.reload.get(5) == -1) {
                this.currentLevel.getRGate().setX(-1);
                this.currentLevel.getRGate().setY(-1);
            }
            if (this.reload.get(6) == -1) {
                this.currentLevel.getWGate().setX(-1);
                this.currentLevel.getWGate().setY(-1);
            }
            if (this.reload.get(7) == -1){
                this.currentLevel.getRLever().setX(-1);
                this.currentLevel.getRLever().setY(-1);
            }
            if (this.reload.get(8) == -1){
                this.currentLevel.getWLever().setX(-1);
                this.currentLevel.getWLever().setY(-1);
            }

            int bombMover = 0;
            for (int i = 0; i < reload.size(); i++){
                System.out.println(reload.get(i));
            }
            for (int x = 9; x < this.reload.size(); x++){
                if(this.reload.get(x) == -1){
                    this.currentLevel.getBombs().get(bombMover).setX(-1);
                    this.currentLevel.getBombs().get(bombMover).setY(-1);
                    System.out.println("yes");
                    bombMover++;
                }
            }
        }
        drawGame();
    }
    /**
     * main
     */
    public static void main(String[] args) throws IOException {
        launch(args);
    }









}