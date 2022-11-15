public class Leaderboard extends SaveLoad{
	inherits same objects as SaveLoad
	
	private void addScore() {
		game.getTopScores
		if score(current) is larger than the [top 10 scores] {
			add score to [top 10 scores]
		} else {
			add score to [general list]
		}
	}

	private void addUser() {
		input username 
		input password 
		upload to db 
	}
	
	private void getTopScores() {
		fetch [top 10 scores] from db 
	}
}
