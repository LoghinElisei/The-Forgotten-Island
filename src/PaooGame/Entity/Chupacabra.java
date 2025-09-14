package PaooGame.Entity;

import PaooGame.Game;
import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.States.PlayState;
import PaooGame.States.State;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Chupacabra extends Character {
    private BufferedImage image;

    public Chupacabra(RefLinks refLink, int x, int y)
    {
        super(refLink, x, y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT);

        normalBounds.x = 50;
        normalBounds.y = 45;
        normalBounds.width = 32;
        normalBounds.height = 32 * 2;

        defaultBoundsX = normalBounds.x;
        defaultBoundsY = normalBounds.y;
        speed = 8;
        onPath = true;
    }


    @Override
    public void Draw(Graphics2D g) {
        State playState = refLink.GetGame().playState;
        int screenX = x - playState.getHero().x + playState.getHero().screenX;
        int screenY = y - playState.getHero().y + playState.getHero().screenY;

        if (x + Tile.TILE_WIDTH > playState.getHero().x - playState.getHero().screenX &&
                x - Tile.TILE_WIDTH < playState.getHero().x + playState.getHero().screenX &&
                y + Tile.TILE_WIDTH > playState.getHero().y - playState.getHero().screenY &&
                y - Tile.TILE_WIDTH < playState.getHero().y + playState.getHero().screenY) {
            switch (direction) {
                case "up":
                    switch (spriteNum) {
                        case 1: image = Assets.chupaUp1; break;
                        case 2: image = Assets.chupaUp2; break;
                        case 3: image = Assets.chupaUp3;
                    }
                    break;
                case "down":
                    switch (spriteNum) {
                        case 1: image = Assets.chupaDown1; break;
                        case 2: image = Assets.chupaDown2; break;
                        case 3: image = Assets.chupaDown3;
                    }
                    break;
                case "left":
                    switch (spriteNum) {
                        case 1: image = Assets.chupaLeft1; break;
                        case 2: image = Assets.chupaLeft2; break;
                        case 3: image = Assets.chupaLeft3;
                    }
                    break;
                case "right":
                    switch (spriteNum) {
                        case 1: image = Assets.chupaRight1; break;
                        case 2: image = Assets.chupaRight2; break;
                        case 3: image = Assets.chupaRight3;
                    }
            }

            g.drawImage(image, screenX, screenY, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);

            if (Game.debugState) {
                g.setColor(Color.BLUE);
                g.fillRect(screenX + bounds.x, screenY + bounds.y, bounds.width, bounds.height);
            }
        }
    }

    @Override
    protected void setAction(){
        if (followPlayer == false) {
            if (onPath) searchPath(goalCol, goalRow);
            else searchPath(startCol, startRow);
        } else {
            Character hero = refLink.GetGame().playState.getHero();
            int goalCol = (hero.x + hero.bounds.x) / Tile.TILE_HEIGHT;
            int goalRow = (hero.y + hero.bounds.y) / Tile.TILE_HEIGHT;
            searchPath(goalCol, goalRow);
        }
    }
}
