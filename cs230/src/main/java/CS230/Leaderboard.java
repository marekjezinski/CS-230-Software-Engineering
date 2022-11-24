package CS230;
public class Leaderboard extends SaveLoad{

    private void addScore (String gameState) {
        // pull score from game state
        // if statement to determine if in top 10
        //use either recursion of for loop to define
    }

    private void addUser(){
        String username = null;
        String password = null;
        //collect inputs in gui
        //upload to txt file
    }

    private void getTopScores(){
        //fetch form txt file
    }

    private Object showTop10Scores(){
        return null;
    }

    /*
    what else would a leaderboard need to do?

    figure out the best way to change top scores

    maybe store top 10 in some array or file
    then add the new score, sort and then remove the last score.
    ^ first compare new score with the lowest score on the list
     */
}
