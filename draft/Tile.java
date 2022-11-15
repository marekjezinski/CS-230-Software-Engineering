public class Tile {
    private String colour;
    private Character entity;
    private Item tileItem;
   
    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Character getEntity() {
        return entity;
    }

    public void setEntity(Character entity) {
        this.entity = entity;
    }

    public Item getTileItem() {
        return tileItem;
    }

    public void setTileItem(Item tileItem) {
        this.tileItem = tileItem;
    }

    public Tile() {
       
    }

    public Tile(String colour, Character entity, Item tileItem) {
        setColour(colour);
        setEntity(entity);
        setTileItem(tileItem);
    }
}
