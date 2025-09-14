package PaooGame.CollisionChecker;

import PaooGame.Entity.Character;
import PaooGame.Game;
import PaooGame.Maps.Map;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;



public class Collision {
    private RefLinks refLink;

    public Collision(RefLinks refLink)
    {
        this.refLink = refLink;
    }

    public void checkTile(Character item)
    {
        int characterLeftX = item.GetX() + item.bounds.x;
        int characterRightX = item.GetX()  + item.bounds.x + item.bounds.width;
        int characterTopY = item.GetY() + item.bounds.y;
        int characterBottomY = item.GetY()  + item.bounds.y + item.bounds.height;


        int tileSize = Tile.TILE_WIDTH;

        int characterLeftCol = (int)(characterLeftX / (float)tileSize);
        int characterRightCol = (int)(characterRightX / (float)tileSize);
        int characterTopRow = (int)(characterTopY / (float)tileSize);
        int characterBottomRow = (int)(characterBottomY / (float)tileSize);

        Tile tileNum1, tileNum2;

        switch (item.getDirection())
        {
            case "up":
                characterTopRow = (characterTopY - item.GetSpeed()) / tileSize;
                tileNum1 = refLink.GetMap().GetCollisionTile(characterLeftCol, characterTopRow);
                tileNum2 = refLink.GetMap().GetCollisionTile(characterRightCol, characterTopRow);
                if (tileNum1.IsSolid() || tileNum2.IsSolid()) {
                    item.setCollisionOn(true);
                }
                break;
            case "down":
                characterBottomRow = (characterBottomY + item.GetSpeed()) / tileSize;
                tileNum1 = refLink.GetMap().GetCollisionTile(characterLeftCol, characterBottomRow);
                tileNum2 = refLink.GetMap().GetCollisionTile(characterRightCol, characterBottomRow);
                if (tileNum1.IsSolid() || tileNum2.IsSolid()) {
                    item.setCollisionOn(true);
                }
                break;
            case "left":
                characterLeftCol = (characterLeftX - item.GetSpeed()) / tileSize;
                tileNum1 = refLink.GetMap().GetCollisionTile(characterLeftCol, characterTopRow);
                tileNum2 = refLink.GetMap().GetCollisionTile(characterLeftCol, characterBottomRow);
                if (tileNum1.IsSolid() || tileNum2.IsSolid()) {
                    item.setCollisionOn(true);
                }
                break;
            case "right":
                characterRightCol = (characterRightX + item.GetSpeed()) / tileSize;
                tileNum1 = refLink.GetMap().GetCollisionTile(characterRightCol, characterTopRow);
                tileNum2 = refLink.GetMap().GetCollisionTile(characterRightCol, characterBottomRow);
                if (tileNum1.IsSolid() || tileNum2.IsSolid()) {
                    item.setCollisionOn(true);
                }
        }
    }

    public int checkItem(Character entity) {
        int index = 999;
        Map map = refLink.GetMap();

        for (int i = 0; i < map.items.length; ++i)
        {
            if (map.items[i] != null)
            {
                entity.bounds.x = entity.GetX() + entity.bounds.x;
                entity.bounds.y = entity.GetY() + entity.bounds.y;

                map.items[i].bounds.x = map.items[i].worldX
                                                    + map.items[i].bounds.x;
                map.items[i].bounds.y = map.items[i].worldY
                                                    + map.items[i].bounds.y;

                switch (entity.getDirection()){
                    case "up": entity.bounds.y -= entity.GetSpeed(); break;
                    case "down": entity.bounds.y += entity.GetSpeed(); break;
                    case "left": entity.bounds.x -= entity.GetSpeed(); break;
                    case "right": entity.bounds.x += entity.GetSpeed(); break;
                }
                if (entity.bounds.intersects(map.items[i].bounds)) {
                    index = i;
                }
                entity.SetDefaultMode();
                map.items[i].setDefaultMode();
            }
        }

        return index;
    }


    public int checkEntity(Character entity) {

        Character[] target = refLink.GetMap().monsters;
        int index = 999;

        for (int i = 0; i < target.length; ++i)
        {
            if (target[i] != null)
            {
                entity.bounds.x = entity.GetX() + entity.bounds.x;
                entity.bounds.y = entity.GetY() + entity.bounds.y;

                target[i].bounds.x = target[i].GetX()
                        + target[i].bounds.x;
                target[i].bounds.y = target[i].GetY()
                        + target[i].bounds.y;

                switch (entity.getDirection()){
                    case "up": entity.bounds.y -= entity.GetSpeed(); break;
                    case "down": entity.bounds.y += entity.GetSpeed(); break;
                    case "left": entity.bounds.x -= entity.GetSpeed(); break;
                    case "right": entity.bounds.x += entity.GetSpeed(); break;
                }
                if (entity.bounds.intersects(target[i].bounds)) {
                    if (target[i] != entity) {
                        index = i;
                        entity.setCollisionOn(true);
                    }
                }
                entity.SetDefaultMode();
                target[i].SetDefaultMode();
            }
        }

        return index;
    }


    public boolean checkFromEnemyToPlayer(Character entity) {
        boolean contactPlayer = false;

        Character hero = refLink.GetGame().playState.getHero();

        entity.bounds.x = entity.GetX() + entity.bounds.x;
        entity.bounds.y = entity.GetY() + entity.bounds.y;

        hero.bounds.x = hero.GetX()
                + hero.bounds.x;
        hero.bounds.y = hero.GetY()
                + hero.bounds.y;

        switch (entity.getDirection()){
            case "up": entity.bounds.y -= entity.GetSpeed(); break;
            case "down": entity.bounds.y += entity.GetSpeed(); break;
            case "left": entity.bounds.x -= entity.GetSpeed(); break;
            case "right": entity.bounds.x += entity.GetSpeed(); break;
        }
        if (entity.bounds.intersects(hero.bounds)) {

            entity.setCollisionOn(true);
            contactPlayer = true;
        }
        entity.SetDefaultMode();
        hero.SetDefaultMode();

        return contactPlayer;
    }
}
