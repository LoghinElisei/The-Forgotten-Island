package PaooGame.Items;

import PaooGame.Entity.Entity;
import PaooGame.Game;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public static int MAX_ELEMENTS_ON_DISPLAY = 15;

    protected BufferedImage image;
    protected String name;
    protected int boundsDefaultX = Tile.TILE_WIDTH / 2;
    protected int boundsDefaultY = Tile.TILE_WIDTH / 2;
    public Rectangle bounds;

    public int worldX, worldY;



    public SuperObject(){
        this.bounds = new Rectangle(0, 0, boundsDefaultX, boundsDefaultY);
    }

    public void draw(Graphics2D g2, Entity entity){
        int screenX = worldX - entity.GetX() + entity.screenX;
        int screenY = worldY - entity.GetY() + entity.screenY;

        if (worldX + Tile.TILE_WIDTH > entity.GetX() - entity.screenX &&
            worldX - Tile.TILE_WIDTH < entity.GetX() + entity.screenX &&
            worldY + Tile.TILE_WIDTH > entity.GetY() - entity.screenY &&
            worldY - Tile.TILE_WIDTH < entity.GetY() + entity.screenY) {

            g2.drawImage(image, screenX, screenY, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
             if (Game.debugState) {
                 g2.setColor(Color.RED);
                 g2.fillRect(screenX, screenY, boundsDefaultX, boundsDefaultY);
             }
        }
    }

    public void setDefaultMode(){
        bounds.x = boundsDefaultX;
        bounds.y = boundsDefaultY;
    }

    public String getName() {
        return name;
    }
}
