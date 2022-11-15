public class SaveLoad {
	protected int score
	private String gameState
	private String playerName 

	private String getGameState() {
		fetch game state from map
		store in temp variable current user
	}

	private void loadGame(String gameState) {
		game.getGameState(username)
		game.run()
	}

	private void saveGame(String gameState) {
		game.getGameState(currentUser)
		get username of current user
		upload data to db table
	} 
}
