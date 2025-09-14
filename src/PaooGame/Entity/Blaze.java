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

public class Blaze extends Character {
    private BufferedImage image;

    public Blaze(RefLinks refLink, int x, int y)
    {
        super(refLink, x, y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT);

        normalBounds.x = 30;
        normalBounds.y = 30;
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
                        case 1: image = Assets.blazeUp1; break;
                        case 2: image = Assets.blazeUp2; break;
                        case 3: image = Assets.blazeUp3;
                    }
                    break;
                case "down":
                    switch (spriteNum) {
                        case 1: image = Assets.blazeDown1; break;
                        case 2: image = Assets.blazeDown2; break;
                        case 3: image = Assets.blazeDown3;
                    }
                    break;
                case "left":
                    switch (spriteNum) {
                        case 1: image = Assets.blazeLeft1; break;
                        case 2: image = Assets.blazeLeft2; break;
                        case 3: image = Assets.blazeLeft3;
                    }
                    break;
                case "right":
                    switch (spriteNum) {
                        case 1: image = Assets.blazeRight1; break;
                        case 2: image = Assets.blazeRight2; break;
                        case 3: image = Assets.blazeRight3;
                    }
            }

            g.drawImage(image, screenX, screenY, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_WIDTH, null);

            if (Game.debugState) {
                g.setColor(Color.BLUE);
                g.fillRect(screenX + bounds.x, screenY + bounds.y, bounds.width, bounds.height);
            }
        }
    }

    @Override
    protected void setAction(){
        if (onPath) searchPath(goalCol, goalRow);
        else searchPath(startCol, startRow);
    }
}
