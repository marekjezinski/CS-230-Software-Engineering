package CS230;
import CS230.items.*;
import CS230.map.Cell;
import CS230.map.Map;
import CS230.npc.*;
import CS230.saveload.ProfileFileManager;
import CS230.saveload.SaveGame;
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
 * @author Marek Jezinski
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

    // The canvas in the GUI.
    private Canvas canvas;
    private Image playerImage;
    private int playerX = 0;
    private int playerY = 0;
    private Player player1;
    private Timeline timerTimeline;
    private Timeline scoreColourChanger;
    private Timeline bombTimeline;
    private Loot currentGoal;
    //SmartThief
    public static Queue<int[]> path = new LinkedList<>();
    //used to iterate through all the directions of grid
    private Image SMImage =  new Image(getClass().
            getResource("map/smartThief.png").toURI().toString());
    private SmartThief sThief = new SmartThief(1,1,SMImage);
    private int[] smThiefCords = {0,0};
    private String username;
    ArrayList<Integer> reload;
    private boolean hasGameStarted = false;
    private Text timerText = new Text();
    private Text scoreText = new Text();
    private final Text messageOfDayText = new Text();
    private Label leaderboardText = new Label();
    SaveGame save = new SaveGame();
    private MediaPlayer player = new MediaPlayer(new Media(new File("gamemusic.mp3").toURI().toString()));
    private int currentLevelID = 0;
    private ArrayList<CS230.map.Map> levels = new ArrayList<>();
    CS230.map.Map currentLevel;
    CS230.map.Map level1 = new CS230.map.Map("textfiles/maps/L0.0.txt");
    CS230.map.Map level2 = new CS230.map.Map("textfiles/maps/L1.0.txt");
    CS230.map.Map level3 = new CS230.map.Map("textfiles/maps/L2.0.txt");
    CS230.map.Map level4 = new CS230.map.Map("textfiles/maps/L3.0.txt");
    CS230.map.Map level5 = new CS230.map.Map("textfiles/maps/L4.0.txt");
    CS230.map.Map level6 = new CS230.map.Map("textfiles/maps/L5.0.txt");
    private ProfileFileManager profiles;

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
        levels.add(level5);
        levels.add(level6);
        profiles = new ProfileFileManager(levels.size());

        this.currentLevel = levels.get(currentLevelID);

        // Load images. Note we use png images with a transparent background.
        playerImage = new Image(getClass().
                getResource("map/player.png").toURI().toString());


        //create player
        player1 = new Player(this.
                currentLevel.getPlayerX() * 2,
                this.currentLevel.getPlayerY() * 2,playerImage);

        // Build the GUI
        Pane root = buildGUI();

        // Create a scene from the GUI
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event));

        timerTimeline = new Timeline(new
                KeyFrame(Duration.millis(1000), event -> {
            timer();
            ArrayList<FlyingAssassin> fl = currentLevel.getFlyingAssassins();
            for (FlyingAssassin flyingAssassin : fl) {
                flyingAssassin.movement(currentLevel);
            }
            currentLevel.setFlyingAssassins(fl);
            if(currentLevel.isFlAsCollidedWPlayer()) {
                gameOver();
            }
            ArrayList<Thief> th = currentLevel.getThieves();
            for (Thief thief : th) {
                thief.movement(currentLevel);
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

            this.smThiefCords = path.poll();
            if (this.smThiefCords != null) {
                if(currentLevel.isLegalMovement
                        (this.smThiefCords[0],this.smThiefCords[1])) {
                    sThief.setX(this.smThiefCords[0]);
                    sThief.setY(this.smThiefCords[1]);
                }
            }

            //x + y co-ords of the nearest item
            int goalX = sPath.getPathGoalX();
            int goalY = sPath.getPathGoalY();
            // if a path is found which isn't 0,0
            // then move randomly through the level
            try {
                if (SmartThiefSearch.bfs( currentLevel, currentLevel.getTilesArray(),
                        sThief.getX(),sThief.getY(),sPath.
                                getPathGoalX(),
                        sPath.getPathGoalY()) && !(goalX==0 && goalY ==0)){
                    path = SmartThiefSearch.getQueue();
                } else {
                    if (currentLevel.getLoots().size() > 0) {
                        sThief.randomMovement(currentLevel);
                    }

                }
            } catch (Exception e) {

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
                                            getResource("map/bomb3.png").
                                            toURI().toString()));
                        }
                        else if (secToExplode == 2) {
                            bombs.get(i).setImg(new Image(getClass().
                                    getResource("map/bomb2.png").
                                    toURI().toString()));
                        }
                        else if (secToExplode == 1) {
                            bombs.get(i).setImg(new Image(getClass().
                                    getResource("map/bomb1.png").
                                    toURI().toString()));
                        }
                        else if (secToExplode == 0) {
                            bombs.get(i).setImg(new Image(getClass().
                                    getResource("map/bomb0.png").
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
                break;
        }
        if(currentLevel.isFlAsCollidedWPlayer()) {
            gameOver();
        }
        checkItems();
        this.leaderboardText.setText(profiles.getLeaderboardForLevelID(currentLevelID));
        profiles.updateMaxLvl(username, currentLevelID);
        profiles.updateMaxScore(username, currentLevel.getScore(), currentLevelID);
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
        currentLevel.setTimerLeft(currentLevel.getTimerLeft() + currentLevel.checkClocks(playerX , playerY));
        if(currentLevel.checkDoor(playerX, playerY)>0){
            if( currentLevel.getLoots().size() == 0 &&
                    currentLevel.checklever()) {
                currentLevel.setScore((int) (currentLevel.getScore() + ceil(currentLevel.getTimerLeft() / 3)));
                profiles.updateMaxScore(username, currentLevel.getScore(), currentLevelID);
                currentLevelID++;
                if(currentLevelID > levels.size() - 1) {
                    gameOver();
                } else {
                    currentLevel = levels.get(currentLevelID);
                    player1.setX(currentLevel.getPlayerX() * 2);
                    player1.setY(currentLevel.getPlayerY() * 2);
                }
            }
        }
        currentLevel.checkLoots(sThief.getX(), sThief.getY());
        currentLevel.setScore(currentLevel.getScore() + currentLevel.checkLoots(playerX, playerY ));
        scoreText.setText("Score: " + currentLevel.getScore());
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
        gc.drawImage(sThief.getImg(), sThief.getX() * 2
                * GRID_CELL_WIDTH, sThief.getY() * 2 *
                GRID_CELL_HEIGHT);

        gc.drawImage(player1.getCharImage(), player1.getX()
                * GRID_CELL_WIDTH, player1.getY() *
                GRID_CELL_HEIGHT);



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
     * Method that updates the timer
     */
    public void timer(){
        if (currentLevel.getTimerLeft() <= 6){
            timerText.setFill(Paint.valueOf("Red"));
        }
        if (currentLevel.getTimerLeft() > 6){
            timerText.setFill(Paint.valueOf("Black"));
        }
        if (currentLevel.getTimerLeft() > 0) {
            currentLevel.setTimerLeft(currentLevel.getTimerLeft() - 1);
            this.timerText.setText("Time remaining: " + currentLevel.getTimerLeft() + "| Level "
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
     * @throws  IOException
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

        VBox profileGUI = new VBox();
        profileGUI.setSpacing(5);
        profileGUI.setPadding(new Insets(10, 10, 10, 10));
        root.setLeft(profileGUI);

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
            profileGUI.getChildren().add(new Label(profilesUsername));
        }
        //create user
        createUser.setOnAction(e -> {
            if(!usernameIn.getText().equals("")) {
                profiles.addProfile(
                        usernameIn.getText().replaceAll("\\s",""));
                profileGUI.getChildren().removeAll(profileGUI.getChildren());
                for (String profilesUsername : profiles.getUsernames()) {
                    profileGUI.getChildren().add(new Label(profilesUsername));
                }
                usernameIn.clear();
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
                        profileGUI.getChildren().clear();
                        toolbar.getChildren().clear();
                        this.timerText.setText("");
                        this.timerText.setFont(Font.font("arial",20));
                        this.scoreText.setText("");
                        this.scoreText.setFont(Font.font("arial",20));
                        toolbar.getChildren().add(this.scoreText);
                        toolbar.getChildren().add(timerText);
                        Button sv = new Button("Save game");
                        sv.setOnAction(g -> {
                            save.update(currentLevel, currentLevel.getTimerLeft(), username);
                            gameOver();
                        });
                        toolbar.getChildren().add(sv);
                        VBox leaderboard = new VBox();
                        leaderboard.setSpacing(5);
                        leaderboard.setPadding(new Insets(10, 10, 10, 10));
                        leaderboard.getChildren().add(leaderboardText);
                        root.setLeft(leaderboard);
                        begin(lvl,false);
                    });
                    toolbar.getChildren().add(b);
                }
                if(save.doesUserSaveExists(String.valueOf(usernameIn.getText()))) {
                    Button b = new Button("Saved game");
                    b.setOnAction(f -> {
                        String fileName = String.format("textfiles/saves/%s.txt", username);
                        currentLevel = new Map(String.format(fileName));
                        profileGUI.getChildren().clear();
                        toolbar.getChildren().clear();
                        this.timerText.setText("");
                        this.timerText.setFont(Font.font("arial",20));
                        this.scoreText.setText("");
                        this.scoreText.setFont(Font.font("arial",20));
                        toolbar.getChildren().add(this.scoreText);
                        toolbar.getChildren().add(timerText);
                        Button sv = new Button("Save game");
                        sv.setOnAction(g -> {
                            save.update(currentLevel, currentLevel.getTimerLeft(), username);
                            gameOver();
                        });
                        toolbar.getChildren().add(sv);
                        VBox leaderboard = new VBox();
                        leaderboard.setSpacing(5);
                        leaderboard.setPadding(new Insets(10, 10, 10, 10));
                        leaderboard.getChildren().add(leaderboardText);
                        root.setLeft(leaderboard);
                        begin(currentLevel.getLevelID(), true);
                    });
                    toolbar.getChildren().add(b);
                }
            }
            usernameIn.clear();
        });

        removeUser.setOnAction(g -> {
            profiles.removeProfile(String.valueOf(usernameIn.getText()));
            profileGUI.getChildren().removeAll(profileGUI.getChildren());
            for (String profilesUsername : profiles.getUsernames()) {
                profileGUI.getChildren().add(new Label(profilesUsername));
            }
            usernameIn.clear();
        });




        // Finally, return the border pane we built up.
        return root;
    }

    /**
     * Method for starting the game with appropriate variables from save
     * @param levelIn
     */
    public void begin(int levelIn, boolean isGameLoaded){
        this.hasGameStarted = true;
        scoreColourChanger.play();
        this.player.play();

        this.currentLevelID = levelIn;
        if(isGameLoaded == false) {
            currentLevel = levels.get(currentLevelID);
        }
        timerTimeline.play();

        this.timerText.setText("Time remaining: " + currentLevel.getTimerLeft() + "| Level "
                + (this.currentLevelID + 1)
                + "| Loot remaining: " + (this.currentLevel.getLoots().size()));
        scoreText.setText("Score: " + currentLevel.getScore());
        this.leaderboardText.setText(profiles.getLeaderboardForLevelID(currentLevelID));

        player1.setX(currentLevel.getPlayerX() * 2);
        player1.setY(currentLevel.getPlayerY() * 2);
        currentLevel.setTimerLeft(currentLevel.getStartTimer());
        drawGame();
    }
    /**
     * main method for the game
     */
    public static void main(String[] args) {
        launch(args);
    }
}