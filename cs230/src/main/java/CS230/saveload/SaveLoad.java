package CS230.saveload;

public class SaveLoad {
    protected int score;
    private String gameState;
    private String playerName;

    /*
    game state is:
    all positions of NPCs + items + loot
    position of player
    time left on timer
    all states of gates
    all states of bombs
    (all positions in (x,y) coordinates)
    current score
    STORED IN TXT FILE
     */
    private void getGameState(){
        //fetch from Map once class is made
    }

    // applies game state to the game when profile is loaded up
    private void loadGame(String gameState){
        //game.getGameState(username)
    }

    // uploads all data from getGameState
    //functions as a setter kinda
    private void saveGame(String gameState){
        //game.getGameState(username)
        //upload to db
    }
}
