package CS230.npc;

/*
* This class handles the direction the FlyingAssassin NPC faces in
* and all any changes that need to be made to it. Also handles sprite
* rotations that will occur with a change in direction.
* @author Wiktoria Bruzgo
* @version 1.0
* */
class Direction {
    private String facing;

    /*
    * Create a direction for the NPC to face in.
    * @param facing The direction the NPC is facing.
    * */
    public Direction(String facing){
        this.facing = facing;
    }

    /*
    * Updates the direction the NPC is facing.
    * @param newFacing direction after rotation.
    * */
    public void setFacing(String newFacing){
        this.facing = newFacing;
    }

    /*
    * Get the direction NPC is facing in.
    * @return The direction NPC is currently facing.
    * */
    public String getFacing(){
        return facing;
    }

    /*
    *Executes a rotation of 180 degrees on the NPC.
    * @param facing The current direction NPC is facing.
    * @exception IllegalDirectionException if direction given is not either
    * North (N), South (S), East (E), West (W).
    * @return The new direction the NPC is facing.
    * */
    public String execRotation(String facing){
        String newFacing;
        newFacing = switch (facing) {
            case "N" -> "S";
            case "S" -> "N";
            case "E" -> "W";
            case "W" -> "E";
            default -> throw new IllegalDirectionException("Direction" + facing +
                    "does not exist.\n Please use N-S-E-W for directions.");
        };
        return newFacing;
        //currently unfinished !! rotation not yet implemented
        //will most likely just change sprites rather than messing with javafx lol
    }

}

