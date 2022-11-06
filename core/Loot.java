public class Loot extends Item {
    private int points;
    private String lootType;
    //private Image img;

    public Loot(int points, String lootType) {
        this.points = points;
        this.lootType = lootType;
    }

    public void trigger() {
        // collects Loot IF is on the Tile that the Player/NPC is currently on
    }

    public void setImage(/*Image img*/) {
        /*this.img = img;*/
    }
}
